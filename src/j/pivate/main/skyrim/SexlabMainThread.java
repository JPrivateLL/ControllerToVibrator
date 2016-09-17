package j.pivate.main.skyrim;

import j.pivate.main.gui.GUIStartMenu;
import j.pivate.main.skyrim.vibration.VibrationList;
import j.pivate.main.skyrim.vibration.VibrationSet;
import j.pivate.main.skyrim.vibration.types.Vibration;
import j.pivate.main.vibrator.Vibrator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JOptionPane;

import com.sun.jna.Native;
import com.sun.jna.PointerType;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;

public class SexlabMainThread{

	
	private GUISkyrim gui;
	private SexlabVibratorList vibratorList;
	private VibrationList vibrationList;
	private SexlabMainThread mainThread;
	private String line;
	
	public SexlabMainThread(final List<Vibrator> vibators, final String logLocation) {
		this.mainThread = this;
		this.vibratorList = new SexlabVibratorList(vibators);
		this.vibrationList = new VibrationList();
		this.papyrusLocation = logLocation;
		
		new Thread()
		{
		    public void run() {
		    	gui = new GUISkyrim(mainThread);
		    }
		}.start();
		
		while(gui==null){try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}}
			

		controllerConnectedMessage = new boolean[vibators.size()];
		
		//try to find papyrus file
		papyrusFile = null;
		try {papyrusFile = new FileInputStream(logLocation);} catch (final FileNotFoundException e1) {
			closeWithError("No Papyrus log found, make sure you turned it on and that the game has atleased started ones(incl loading save).");
		}
		
		//load sexlab animations
		try{Vibration.load();}catch(IndexOutOfBoundsException | IOException e){
			closeWithError("Can't load vibration.txt file, Error message:\n\""+e.getMessage()+"\"");
		}
		
		
		papyrusReader = new BufferedReader(new InputStreamReader(papyrusFile));
		line = readNextLine();
		startupTime = line;

		gui.addTextToDebugScreen("Skipping old code");
		while (readNextLine() != null);

		new Thread() {
			private boolean checkForNewLog(final String oldLog) {
				String line = "";
				FileInputStream file2 = null;
				try {
					file2 = new FileInputStream(papyrusLocation);
				} catch (final FileNotFoundException e2) {
				}
				final BufferedReader newReader = new BufferedReader(
						new InputStreamReader(file2));
				try {
					line = newReader.readLine();
				} catch (final IOException e1) {
					e1.printStackTrace();
				}// read line
				line = line.toLowerCase();
				if (!oldLog.equals(line)) {
					startupTime = line;
					papyrusReader = newReader;
					vibratorList.stopAll();
					removeAllVibrations();
					gui.restartLineCounter();
					gui.cleanDebugScreen();

					gui.addOneToLineCounter();
					gui.addWaringToDebugScreen("New log found:");
					gui.addTextToDebugScreen(line);
					return true;
				}
				return false;
			}

			@Override
			public void run() {
				boolean restartForTesting = true;
				while (running) {
					boolean b = true;
					if (gui.testVibrate()) {
						if (restartForTesting) {
							String line = "";
							restartForTesting = false;
							FileInputStream file2 = null;
							try {
								file2 = new FileInputStream(papyrusLocation);
							} catch (final FileNotFoundException e2) {
							}
							papyrusReader = new BufferedReader(new InputStreamReader(
									file2));
							try {
								line = papyrusReader.readLine();
							} catch (final IOException e1) {
								e1.printStackTrace();
							}// read line
							startupTime = line;

							gui.restartLineCounter();
							gui.cleanDebugScreen();

							gui.addOneToLineCounter();
							gui.addWaringToDebugScreen("Restarted log reader for testing:");
							gui.addTextToDebugScreen(line);
						}
					} else {
						if (!restartForTesting) {
							restartForTesting = true;
							removeAllVibrations();
						}

						if (!skyrimHasFocus()) {
							// stop vibration for the time being.
							b = false;
							vibratorList.stopAll();
						} else {
							checkForNewLog(startupTime);
						}
						
						if(pause){
							//b = false;
						}
						
						isControllerConnected();
					}
					updateCheck = b;

					try {
						Thread.sleep(500);
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
			
			long startTime = System.currentTimeMillis();
			long updateTime = startTime;
			float delta = 0;
			while (running) {
				
				if (updateCheck) {
					updateTime = System.currentTimeMillis();
					delta = (updateTime - startTime)/ 1000f;
	
					if (delta >= 0.1) {
						vibrationList.update(delta);	
						
						float[] strength = vibrationList.getStrength();
						
						for (int i = 0; i < strength.length; i++) {
							if(mute){
								strength[i] = 0;
							}else{
								
								strength[i] *= gui.getStrengthController(i); 
								if (sla != -1f) {
									strength[i] *= (sla+100f)/200f;
								}
								if(strength[i]>1f){
									strength[i] = 1f;
								}
							}
						}
						
						vibratorList.rumble(strength);
	
						//check how many vibrations are running
						int runnningVibrations = vibrationList.size();
						gui.setRunningVibrations(runnningVibrations);
							
						startTime = updateTime;
					}
	
					
					
					line = readNextLine();
					if (line != null) {
						line = line.toLowerCase();
						if (line.contains("jnsla=")) {
							sla = Integer.valueOf(findInLine("jnsla", line));
							gui.setSLAIndicator(sla);
							line = line.substring(0, (line.indexOf("jnsla=")));
						}
						
						if (line.contains("jnsexlab ")){lineCheckSexlab(line);}
						else if (line.contains("jnequip ")){lineCheckEquiped(line);}
						else if (line.contains("jnpause")){lineCheckPause(line);}
						else if (line.contains("jnunpause")){lineCheckUnPause(line);}
						else if (line.contains("jnmute")){lineCheckMute(line);}
						else if (line.contains("jnunmute")){lineCheckUnMute(line);}
						else if (line.contains("jntimer")){lineCheckTimer(line);}
						else if (line.contains("jnCheckDebug ")){lineCheckDebug(line);}
						else if (line.contains("jnstopall")){lineCheckStopAll(line);}
						
						else if (line.contains("loading game...")) {
							removeAllVibrations();
							gui.cleanDebugScreen();
							gui
									.addTextToDebugScreen("save game loaded(remove running vibrations)");
						} else if (line.contains("log closed")) {
							removeAllVibrations();
						}
					} else {
						try {
							Thread.sleep(1);
						} catch (final InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else {
					try {
						Thread.sleep(200);
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}
				}
	
			}
	
			try {
				papyrusFile.close();
				papyrusReader.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
	
			gui.addErrorToDebugScreen("Program stopped!");
			System.out.println("main stopped");
			vibratorList.releaseAll();;
			}
		}.start();
	}


	
	
	
	public SexlabVibratorList getVibratorList(){
		return vibratorList;
	}
	
	
	
	
	private void closeWithError(String error){
		Vibration.save();
		this.vibratorList.releaseAll();
		JOptionPane.showMessageDialog(null,error,"Error", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}
	
	
	
	
	
	private boolean running = true;

	public Thread thread2 = null;

	private String papyrusLocation;
	private FileInputStream papyrusFile;
	private BufferedReader papyrusReader;
	private String startupTime = null;
	private boolean updateCheck = false;


	private int sla = -1;

	public void removeAllVibrations() {
		this.vibrationList.removeAll();
		this.vibratorList.stopAll();
		gui.addWaringToDebugScreen("Removed all running vibrations");
	}

	public void terminate() {
		gui.addWaringToDebugScreen("stopping Program (breaking out of loop)");
		Vibration.save();
		running = false;
		
	}




	private static String findInLine(String toFind, String line){
		toFind = toFind+"=";
		if(!line.contains(toFind)){return null;}
		String txt = null;
		

		txt = line.substring(line.indexOf(toFind)+toFind.length());

		if(txt.startsWith("'")){
			txt = txt.split("'")[1];
		}else if(txt.contains(" ")){
			txt = txt.split(" ")[0];
		}
			
		return txt;
	}
	private void lineCheckSexlab(String line){
		//get name
		String name = findInLine("name", line);
		if(name==null)return;
		
		//get stage
		Integer stage = null;
		try{stage = Integer.parseInt(findInLine("stage", line));}catch(NumberFormatException e){stage = 0;}
		
		//get pos
		Integer pos = null;
		try{pos = Integer.parseInt(findInLine("pos", line));}catch(NumberFormatException e){pos = 0;}
		
		if(line.contains("jnsexlab end")){
			vibrationList.remove(name);
			
		}
		
		if(line.contains("jnsexlab endall")){
			vibrationList.removeAll();
		}
		
		VibrationSet vibSet = Vibration.get(name, pos, stage);
		for (int i = 0; i < vibSet.size(); i++) {
			vibrationList.add(vibSet.get(i));
		}
		gui.addSexlabAnimation(vibSet);
		
		
		
	}

	private static boolean[] equipedItems= new boolean[4];
	private String[] equipedItemsName = new String[4];
	private void lineCheckEquiped(String line){
		System.out.println("test");
		equipedItemsName = findInLine("worn",line).split(",");
		for (int i = 0; i < equipedItemsName.length; i++) {
			equipedItems[i]=equipedItemsName[i]!="none"? true : false;
		}
		gui.setEquipedLabel(equipedItemsName);
	}
	public static boolean[] getEquipedItems(){
		return equipedItems;
	}
	
	long timer = System.currentTimeMillis();
	private void lineCheckTimer(String line){
		long a = System.currentTimeMillis();
		float delta = (a - timer)/1000f;
		gui.setTimer(delta);
		timer = a;
	System.out.println("test");
	}
	
	private void lineCheckDebug(String line){
		line = line.substring(line.indexOf("debug:")+"debug:".length(),line.length());
		gui.addWaringToDebugScreen(line);
	}
	
	private void lineCheckStopAll(String line){
		removeAllVibrations();
	}
	
	private boolean mute = false;
	private void lineCheckMute(String line){
		mute = true;
	}	
	private void lineCheckUnMute(String line){
		mute = false;
	}
	
	private boolean pause = false;
	private void lineCheckPause(String line){
		pause = true;
	}
	private void lineCheckUnPause(String line){
		pause = false;
	}
	
	private String readNextLine() {
		String line = null;
		try {
			line = papyrusReader.readLine();
		} catch (final IOException e1) {
			e1.printStackTrace();
		}// read line
		if (line != null) {
			gui.addOneToLineCounter();

			line = line.toLowerCase();
		}
		return line;
	}

	public interface User32 extends StdCallLibrary {
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

		HWND GetForegroundWindow();

		int GetWindowTextA(PointerType hWnd, byte[] lpString, int nMaxCount);
	}
	
	private boolean focusMessage = true;
	private boolean skyrimHasFocus() {
		boolean skyrimRunning = false;

		if (!GUIStartMenu.focusCheckDisabled()) {
			final byte[] windowText = new byte[512];
			final PointerType hwnd = User32.INSTANCE.GetForegroundWindow();
			User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
			if (Native.toString(windowText).equals("Skyrim")) {
				skyrimRunning = true;
			}
		} else {
			skyrimRunning = true;
		}
		if (skyrimRunning) {
			if (focusMessage) {
				focusMessage = false;
				gui.addWaringToDebugScreen("Skyrim in focus");
			}
			gui.setCheckGameFocus(true);
			return true;
		} else {
			if (!focusMessage) {
				focusMessage = true;
				gui.addWaringToDebugScreen("Lost Focus of Skyrim");
			}
			gui.setCheckGameFocus(false);
			return false;
		}
	}

	private final boolean[] controllerConnectedMessage;
	private boolean isControllerConnected() {
		boolean test = true;
		for (int i = 0; i < vibratorList.size(); i++) {
			if (vibratorList.isConnected(i)) {
				if (controllerConnectedMessage[i]) {
					controllerConnectedMessage[i] = false;
					gui.addWaringToDebugScreen(vibratorList.getName(i)
							+ " connected!");
				}
				gui.setCheckControllerConnected(true);
			} else {
				test = false;
				if (!controllerConnectedMessage[i]) {
					controllerConnectedMessage[i] = true;
					gui.addWaringToDebugScreen(vibratorList.getName(i)
							+ " disconnected!");
				}
				gui.setCheckControllerConnected(false);
			}
		}
		return test;

	}


}
