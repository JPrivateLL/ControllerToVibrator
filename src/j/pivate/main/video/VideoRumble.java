package j.pivate.main.video;

import j.pivate.main.vibrator.Vibrator;
import j.pivate.main.video.recorder.recorder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import ch.aplu.xboxcontroller.XboxController;
import ch.aplu.xboxcontroller.XboxControllerAdapter;

public class VideoRumble {
	
	public EmbeddedMediaPlayerComponent mediaPlayerComponent;
	
	private final static int DEFAULTUPDATEDELAY = 30; //33.33FPS
	
	private XboxController xc;
	private BufferedReader reader;
	private BufferedWriter writer;
	private File file,file2;
	private boolean running = true;
	private List<Float[]> vibration;
	private float inputStrength = 0;
	private boolean aPressed = false;
	private boolean bPressed = false;
	private boolean xPressed = false;
	private boolean yPressed = false;
	private List<Vibrator> vibrators;
	
	private GUIVideo gui;
	


	public void changeTime(float time) {
		mediaPlayerComponent.getMediaPlayer().setTime((long) (mediaPlayerComponent.getMediaPlayer().getLength() * time));
	}

	public void loadVideo() {
		System.out.println("loading video: "+file.getAbsolutePath());
		mediaPlayerComponent.getMediaPlayer().playMedia(file.getAbsolutePath());
		mediaPlayerComponent.getMediaPlayer().start();
		
		gui.videoLoaded();
		vibration = new ArrayList<Float[]>();
		System.out.println(mediaPlayerComponent.getMediaPlayer().getLength()+" TEST");
		try {
			reader = new BufferedReader(new FileReader(file2));
		} catch (final FileNotFoundException e2) {
			e2.printStackTrace();
		}
		for (int i = 0; i < Math.round(mediaPlayerComponent.getMediaPlayer().getLength()/DEFAULTUPDATEDELAY) + 50; i++) {
			String line = null;
			try {
				line = reader.readLine();
			} catch (final IOException e1) {
				e1.printStackTrace();
			}
			if (line == null || line.equals("")) {
				Float[] f = {0f,0f,0f,0f};
				vibration.add(f);
				
			} else {
				try {
					String[] spl = line.split(" ");
					
					Float[] f = {0f,0f,0f,0f};
					f[0] = Float.parseFloat(spl[0]);
					f[1] = Float.parseFloat(spl[1]);
					f[2] = Float.parseFloat(spl[2]);
					f[3] = Float.parseFloat(spl[3]);
					
					vibration.add(f);
					
				} catch (final NumberFormatException e) {
					Float[] f = {0f,0f,0f,0f};
					vibration.add(f);
				}
			}

		}
		try {
			reader.close();
		} catch (final IOException e2) {
			e2.printStackTrace();
		}
		

	}

	public void pauseMedia() {
		mediaPlayerComponent.getMediaPlayer().pause();
	}

	public void stop() {
		running = false;	
		stopAllVibrators();
		mediaPlayerComponent.getMediaPlayer().stop();
	}

	private void stopAllVibrators() {
		for (int i = 0; i < vibrators.size(); i++) {
			vibrators.get(i).rumble(0);
		}
	}
	
	private Thread timer;
	public VideoRumble(List<Vibrator> v, File file) {
		System.out.println("\nLoading VLCJ:");
		new NativeDiscovery().discover();
		
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		mediaPlayerComponent.getMediaPlayer().setRepeat(false);
		mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventListener() {
			
			@Override
			public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
				
				
			}
			
			@Override
			public void titleChanged(MediaPlayer mediaPlayer, int newTitle) {
				
				
			}
			
			@Override
			public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
				
				
			}
			
			@Override
			public void subItemPlayed(MediaPlayer mediaPlayer, int subItemIndex) {
				
				
			}
			
			@Override
			public void subItemFinished(MediaPlayer mediaPlayer, int subItemIndex) {
				
				
			}
			
			@Override
			public void stopped(MediaPlayer mediaPlayer) {
				
				
			}
			
			@Override
			public void snapshotTaken(MediaPlayer mediaPlayer, String filename) {
				
				
			}
			
			@Override
			public void seekableChanged(MediaPlayer mediaPlayer, int newSeekable) {
				
				
			}
			
			@Override
			public void scrambledChanged(MediaPlayer mediaPlayer, int newScrambled) {
				
				
			}
			
			@Override
			public void positionChanged(MediaPlayer mediaPlayer, float newPosition) {
	
			}
			
			@Override
			public void playing(MediaPlayer mediaPlayer) {
				
				
			}
			
			@Override
			public void paused(MediaPlayer mediaPlayer) {
				
				
			}
			
			@Override
			public void pausableChanged(MediaPlayer mediaPlayer, int newPausable) {
				
				
			}
			
			@Override
			public void opening(MediaPlayer mediaPlayer) {
				
				
			}
			
			@Override
			public void newMedia(MediaPlayer mediaPlayer) {
				
				
			}
			
			@Override
			public void mediaSubItemTreeAdded(MediaPlayer mediaPlayer,
					libvlc_media_t item) {
				
				
			}
			
			@Override
			public void mediaSubItemAdded(MediaPlayer mediaPlayer,
					libvlc_media_t subItem) {
				
				
			}
			
			@Override
			public void mediaStateChanged(MediaPlayer mediaPlayer, int newState) {
				
				
			}
			
			@Override
			public void mediaParsedChanged(MediaPlayer mediaPlayer, int newStatus) {
				
				
			}
			
			@Override
			public void mediaMetaChanged(MediaPlayer mediaPlayer, int metaType) {
				
				
			}
			
			@Override
			public void mediaFreed(MediaPlayer mediaPlayer) {
				
				
			}
			
			@Override
			public void mediaDurationChanged(MediaPlayer mediaPlayer, long newDuration) {
				
				
			}
			
			@Override
			public void mediaChanged(MediaPlayer mediaPlayer, libvlc_media_t media,
					String mrl) {
				
				
			}
			
			@Override
			public void lengthChanged(MediaPlayer mediaPlayer, long newLength) {
				
				
			}
			
			@Override
			public void forward(MediaPlayer mediaPlayer) {
				
				
			}
			
			@Override
			public void finished(MediaPlayer mediaPlayer) {

			}
			
			@Override
			public void error(MediaPlayer mediaPlayer) {
				
				
			}
			
			@Override
			public void endOfSubItems(MediaPlayer mediaPlayer) {
				
				
			}
			
			@Override
			public void elementaryStreamSelected(MediaPlayer mediaPlayer, int type,int id) {
				
				
			}
			
			@Override
			public void elementaryStreamDeleted(MediaPlayer mediaPlayer, int type,int id) {
				
				
			}
			
			@Override
			public void elementaryStreamAdded(MediaPlayer mediaPlayer, int type, int id) {
				
				
			}
			
			@Override
			public void buffering(MediaPlayer mediaPlayer, float newCache) {
				
				
			}
			
			@Override
			public void backward(MediaPlayer mediaPlayer) {
				
			}
		});
		
		this.vibrators = v;
		if(file == null){
			JFileChooser chooser = new JFileChooser();
			int choice = chooser.showOpenDialog(null);
			if (choice != JFileChooser.APPROVE_OPTION){
				System.exit(0);
			}

			this.file = chooser.getSelectedFile();
		}else{
			this.file = file;
		}

		this.file2 = new File("video saves\\" + this.file.getName() + ".txt");
		if (!file2.exists()) {
			try {
				file2.createNewFile();
			} catch (final IOException e1) {
				e1.printStackTrace();
			}
		}
		
		mediaPlayerComponent.validate();

		this.gui = new GUIVideo(mediaPlayerComponent,this);	
		
		
		this.loadVideo();
		this.xc = new XboxController();
		if (!xc.isConnected()) {
			JOptionPane.showMessageDialog(null,
					"Xbox controller not connected.", "No recording device",
					JOptionPane.WARNING_MESSAGE);
		} else {
			xc.addXboxControllerListener(new XboxControllerAdapter() {
				@Override
				public void buttonA(final boolean pressed) {
					aPressed = pressed;
				}
				@Override
				public void buttonB(final boolean pressed) {
					bPressed = pressed;
				}
				@Override
				public void buttonX(final boolean pressed) {
					xPressed = pressed;
				}
				@Override
				public void buttonY(final boolean pressed) {
					yPressed = pressed;
				}

				@Override
				public void rightTrigger(final double value) {
					inputStrength = (float)(value * value);
				}
			});
		}

		timer = new Thread() {
			@Override
			public void run() {
				recorder r = new recorder();				
				int timeOld = 0;
				int timesUpdatedBetween = 0;
				while (running) {
					if(mediaPlayerComponent.getMediaPlayer().isPlaying()){
						int time = (int)mediaPlayerComponent.getMediaPlayer().getTime();
						int delta  = time - timeOld;
						timeOld = time;
						
						if(time>mediaPlayerComponent.getMediaPlayer().getLength()){
							continue;
						}
						
						
						//sync delta is 0 most of the time because getTime() is not accurate.
						if(delta!=0){
							timesUpdatedBetween = 0;
						}
						
						//create extra frames to fix getTime() bug (showing the same time for x seconds).
						int currentFrame = time/DEFAULTUPDATEDELAY+timesUpdatedBetween;
						timesUpdatedBetween++;
						
						
						//record button pressed	
						Float[] f = vibration.get(currentFrame);
						if (aPressed) {
							f[0] = inputStrength;
						}if(bPressed){
							f[1] = inputStrength;
						}if(xPressed){
							f[2] = inputStrength;
						}if(yPressed){
							f[3] = inputStrength;
						}
						
						if(gui.record1){
							f[0] = r.getStrength();
							
						}if(gui.record2){
							f[1] = r.getStrength();
							
						}if(gui.record3){
							f[2] = r.getStrength();
							
						}if(gui.record4){
							f[3] = r.getStrength();
							
						}
						vibration.set(currentFrame, f);
						
						
						for (int i = 0; i < vibrators.size(); i++) {
							vibrators.get(i).rumble(vibration.get(currentFrame)[i]*GUIVideo.GUIStrength	);
						}
						gui.changeStrength(
								(int)(vibration.get(currentFrame)[0]*100),
								(int)(vibration.get(currentFrame)[1]*100),
								(int)(vibration.get(currentFrame)[2]*100),
								(int)(vibration.get(currentFrame)[3]*100));
						gui.updateSlider();
						
					}else{
						stopAllVibrators();
					}
					
					try {
						if(mediaPlayerComponent.getMediaPlayer().getRate()!=0){
							Thread.sleep((int)(DEFAULTUPDATEDELAY/mediaPlayerComponent.getMediaPlayer().getRate()));
						}else{
							System.out.println("test");
							Thread.sleep(100);
						}
					} catch (final InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				
				// Write
				try {
					writer = new BufferedWriter(new FileWriter(file2));
				} catch (final IOException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < vibration.size(); i++) {
					try {
						writer.write(String.valueOf(vibration.get(i)[0]+" "+vibration.get(i)[1]+" "+vibration.get(i)[2]+" "+vibration.get(i)[3]));
						writer.newLine();
					} catch (final IOException e) {
						e.printStackTrace();
					}

				}
				try {
					writer.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
				System.out.println("Video Thread closed");
			}
		};

		timer.start();
	}
}
