package j.pivate.main.skyrim;

import j.pivate.main.gui.GUIStartMenu;
import j.pivate.main.skyrim.vibnew.VibrationList;
import j.pivate.main.skyrim.vibnew.VibratorList;
import j.pivate.main.skyrim.vibnew.VibrationGroup;
import j.pivate.main.skyrim.vibnew.RunningVibrations;
import j.pivate.main.skyrim.vibnew.Vibration;
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

public class SexlabMainThread extends passClass {

	private String line;

	public SexlabMainThread(final List<Vibrator> vibators,
			final String logLocation) {
		mainThread = this;
		vibratorList = new VibratorList(vibators);
		vibrationList = new RunningVibrations();
		this.papyrusLocation = logLocation;

		new Thread() {
			public void run() {
				guiSkyrim = new GUISkyrim(mainThread);
			}
		}.start();

		while (guiSkyrim == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		controllerConnectedMessage = new boolean[vibators.size()];

		// try to find papyrus file
		papyrusFile = null;
		try {
			papyrusFile = new FileInputStream(logLocation);
		} catch (final FileNotFoundException e1) {
			closeWithError(
					"No Papyrus log found, make sure you turned it on and that the game has atleased started ones(incl loading save).");
		}

		// load sexlab animations
		VibrationList.load();

		papyrusReader = new BufferedReader(new InputStreamReader(papyrusFile));
		line = readNextLine();
		startupTime = line;

		guiSkyrim.addTextToDebugScreen("Skipping old code");
		while (readNextLine() != null)
			;

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
				} // read line
				line = line.toLowerCase();
				if (!oldLog.equals(line)) {
					startupTime = line;
					papyrusReader = newReader;
					vibratorList.stopAll();
					removeAllVibrations();
					guiSkyrim.restartLineCounter();
					guiSkyrim.cleanDebugScreen();

					guiSkyrim.addOneToLineCounter();
					guiSkyrim.addWaringToDebugScreen("New log found:");
					guiSkyrim.addTextToDebugScreen(line);
					return true;
				}
				return false;
			}

			@Override
			public void run() {
				boolean restartForTesting = true;
				while (running) {
					boolean b = true;
					if (guiSkyrim.testVibrate()) {
						if (restartForTesting) {
							String line = "";
							restartForTesting = false;
							FileInputStream file2 = null;
							try {
								file2 = new FileInputStream(papyrusLocation);
							} catch (final FileNotFoundException e2) {
							}
							papyrusReader = new BufferedReader(
									new InputStreamReader(file2));
							try {
								line = papyrusReader.readLine();
							} catch (final IOException e1) {
								e1.printStackTrace();
							} // read line
							startupTime = line;

							guiSkyrim.restartLineCounter();
							guiSkyrim.cleanDebugScreen();

							guiSkyrim.addOneToLineCounter();
							guiSkyrim.addWaringToDebugScreen(
									"Restarted log reader for testing:");
							guiSkyrim.addTextToDebugScreen(line);
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

						if (pause) {
							// b = false;
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
						delta = (updateTime - startTime) / 1000f;

						if (delta >= 0.1) {
							vibrationList.update(delta);

							float[] strength = vibrationList.getStrength();

							for (int i = 0; i < strength.length; i++) {
								if (mute) {
									strength[i] = 0;
								} else {

									strength[i] *= guiSkyrim
											.getStrengthController(i);
									if (sla != -1f) {
										strength[i] *= (sla + 100f) / 200f;
									}
									if (strength[i] > 1f) {
										strength[i] = 1f;
									}
								}
							}

							vibratorList.rumble(strength);

							// check how many vibrations are running
							int runnningVibrations = vibrationList.size();
							guiSkyrim.setRunningVibrations(runnningVibrations);

							startTime = updateTime;
						}

						line = readNextLine();
						if (line != null) {
							line = line.toLowerCase();
							lineCheck(line);
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

				guiSkyrim.addErrorToDebugScreen("Program stopped!");
				System.out.println("main stopped");
				vibratorList.releaseAll();
				;
			}
		}.start();
	}

	public VibratorList getVibratorList() {
		return vibratorList;
	}

	private void closeWithError(String error) {
		VibrationList.save();
		vibratorList.releaseAll();
		JOptionPane.showMessageDialog(null, error, "Error",
				JOptionPane.ERROR_MESSAGE);
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
		vibrationList.removeAll();
		vibratorList.stopAll();
		guiSkyrim.addWaringToDebugScreen("Removed all running vibrations");
	}

	public void terminate() {
		guiSkyrim.addWaringToDebugScreen(
				"stopping Program (breaking out of loop)");
		VibrationList.save();
		running = false;

	}

	private static String findInLine(String toFind, String line) {
		toFind = toFind + "=";
		if (!line.contains(toFind)) {
			return null;
		}
		String txt = null;

		txt = line.substring(line.indexOf(toFind) + toFind.length());

		if (txt.startsWith("'")) {
			txt = txt.split("'")[1];
		} else if (txt.contains(" ")) {
			txt = txt.split(" ")[0];
		}

		return txt;
	}

	private boolean mute = false;
	private boolean pause = false;
	private static boolean[] equipedItems = new boolean[4];
	private String[] equipedItemsName = new String[4];

	long timer = System.currentTimeMillis();

	private void lineCheck(String line) {
		if (line.contains("jnsla=")) {
			sla = Integer.valueOf(findInLine("jnsla", line));
			guiSkyrim.setSLAIndicator(sla);
			line = line.substring(0, (line.indexOf("jnsla=")));
		}
		if (line.contains("jnstart")) {
			// get name
			String name1 = findInLine("name1", line);
			String name2 = findInLine("name2", line);
			String name3 = findInLine("name3", line);
			String name4 = findInLine("name4", line);
			if (name1 == null)return;
			if (name2 == null)return;
			if (name3 == null)name3="";
			if (name4 == null)name4="";
			
			
			
			// get tags
			String tag = findInLine("tags", line.replace(" ", ""));
			String[] tags = null;
			if (tag != null) {
				tag = tag.replace("\"", "");
				tag = tag.substring(1, tag.length() - 1);
				tags = tag.split(",");
			}
			
			boolean strapon = Boolean.getBoolean(findInLine("strapon", line));
			
			// get stage
			Integer stage = null;
			try {
				stage = Integer.parseInt(findInLine("stage", line));
			} catch (NumberFormatException e) {
				stage = 0;
			}

			// get pos
			Integer pos = null;
			try {
				pos = Integer.parseInt(findInLine("pos", line));
			} catch (NumberFormatException e) {
				pos = 0;
			}


			// get group
			VibrationGroup vg = VibrationList.get(name1, name2, name3, name4);

			if (passClass.guiAnimation != null) {
				passClass.guiAnimation.updateVibration();
			}
			// add vibrations to running ones
			for (int i = 0; i < vg.size(); i++) {
				Vibration v = vg.get(i);
				vibrationList.add(v.clone(vs.getName(), vg.getStage(), vg.getPos()));
			}
			passClass.guiSkyrim.setStraponLabel(strapon);
			
		} else if (line.contains("jnstop")) {
			String name = findInLine("name", line);
			if (name != null)
				vibrationList.remove(name);
			return;
		} else if (line.contains("jnsexlab endall")) {
			vibrationList.removeAll();
			return;
		} else if (line.contains("jnequip ")) {
			equipedItemsName = findInLine("worn", line).split(",");
			for (int i = 0; i < equipedItemsName.length; i++) {
				equipedItems[i] = equipedItemsName[i] != "none" ? true : false;
			}
			guiSkyrim.setEquipedLabel(equipedItemsName);
		} else if (line.contains("jnpause")) {
			pause = true;
		} else if (line.contains("jnunpause")) {
			pause = false;
		} else if (line.contains("jnmute")) {
			mute = true;
		} else if (line.contains("jnunmute")) {
			mute = false;
		} else if (line.contains("jntimer")) {
			long t = System.currentTimeMillis();
			float delta = (t - timer) / 1000f;
			guiSkyrim.setTimer(delta);
			timer = t;
		} else if (line.contains("jnCheckDebug ")) {
			line = line.substring(line.indexOf("debug:") + "debug:".length(),
					line.length());
			guiSkyrim.addWaringToDebugScreen(line);
		} else if (line.contains("jnstopall")) {
			removeAllVibrations();
		} else if (line.contains("loading game...")) {
			removeAllVibrations();
			guiSkyrim.cleanDebugScreen();
			guiSkyrim.addTextToDebugScreen(
					"save game loaded(remove running vibrations)");
		} else if (line.contains("log closed")) {
			removeAllVibrations();
		}

	}

	public static boolean[] getEquipedItems() {
		return equipedItems;
	}

	private String readNextLine() {
		String line = null;
		try {
			line = papyrusReader.readLine();
		} catch (final IOException e1) {
			e1.printStackTrace();
		} // read line
		if (line != null) {
			guiSkyrim.addOneToLineCounter();

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
				guiSkyrim.addWaringToDebugScreen("Skyrim in focus");
			}
			guiSkyrim.setCheckGameFocus(true);
			return true;
		} else {
			if (!focusMessage) {
				focusMessage = true;
				guiSkyrim.addWaringToDebugScreen("Lost Focus of Skyrim");
			}
			guiSkyrim.setCheckGameFocus(false);
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
					guiSkyrim.addWaringToDebugScreen(
							vibratorList.getName(i) + " connected!");
				}
				guiSkyrim.setCheckControllerConnected(true);
			} else {
				test = false;
				if (!controllerConnectedMessage[i]) {
					controllerConnectedMessage[i] = true;
					guiSkyrim.addWaringToDebugScreen(
							vibratorList.getName(i) + " disconnected!");
				}
				guiSkyrim.setCheckControllerConnected(false);
			}
		}
		return test;

	}

}
