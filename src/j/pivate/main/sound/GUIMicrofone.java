package j.pivate.main.sound;

import j.pivate.main.vibrator.Vibrator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import java.util.List;

public class GUIMicrofone extends JFrame {
	public static int calculateRMSLevel(final byte[] audioData) {
		// audioData might be buffered data read from a data line
		long lSum = 0;
		for (int i = 0; i < audioData.length; i++) {
			lSum = lSum + audioData[i];
		}

		final double dAvg = lSum / audioData.length;

		double sumMeanSquare = 0d;
		for (int j = 0; j < audioData.length; j++) {
			sumMeanSquare = sumMeanSquare + Math.pow(audioData[j] - dAvg, 2d);
		}

		final double averageMeanSquare = sumMeanSquare / audioData.length;
		return (int) (Math.pow(averageMeanSquare, 0.5d) + 0.5);
	}

	public boolean running = true;

	private JSlider s1, s2;
	private Plotter plotter1, plotter2;
	private final List<Vibrator> vibrators;

	public GUIMicrofone(List<Vibrator> v) {
		super("Sound To Rumble");
		System.out.println(v.size());
		this.vibrators = v;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				for (Vibrator vibrator : v) {
					vibrator.rumble(0f);
				}
				System.exit(0);
			}
		});
		setSize(300, 150);
		setMaximumSize(new Dimension(200, 200));
		setMinimumSize(new Dimension(200, 200));

		setVisible(true);

		final Container pane = getContentPane();
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));

		final JPanel SidePanel = new JPanel(new GridLayout(2, 0));
		SidePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		pane.add(SidePanel);

		s1 = new JSlider(SwingConstants.VERTICAL, 0, 2000, 100);
		s1.setToolTipText("Multiplier");
		s1.setLocation(200, 0);
		s1.setMajorTickSpacing(20);
		SidePanel.add(s1, BorderLayout.WEST);

		s2 = new JSlider(SwingConstants.VERTICAL, 0, 100, 1);
		s2.setToolTipText("Min sound to vibrate(remove static noise)");
		s2.setLocation(200, 0);
		s2.setMajorTickSpacing(1);

		SidePanel.add(s2, BorderLayout.WEST);
		
		JSlider s3 = new JSlider();
		s3.setMajorTickSpacing(1);
		s3.setValue(0);
		s3.setOrientation(SwingConstants.VERTICAL);
		SidePanel.add(s3);

		plotter1 = new Plotter(this);
		getContentPane().add(plotter1);
		plotter1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		plotter2 = new Plotter(this);
		getContentPane().add(plotter2);
		plotter2.setLayout(null);
		plotter2.paint(getGraphics());
		plotter1.paint(getGraphics());

		pack();

		JOptionPane.showMessageDialog(null,
				"This is a demo and works only with microfone. Blow in the microfone and see what happens!\n\n"
						+ "There is a way to use this with speakers so your controllers rumbles when sound is played(watching...).\n"
						+ "Instal(inside rar) virtual audio cable, Then follow the instructions on the picture(almost to easy).\n"
						+ "This is a workaround because Java can't record sound from playback devices only from record devices.",
				"WIP", JOptionPane.INFORMATION_MESSAGE);

		new Thread() {
			public void run() {
				AudioFormat fmt = new AudioFormat(44100f, 16, 1, true, false);
	            final int bufferByteSize = 2048;

	            TargetDataLine line;
	            try {
	                line = AudioSystem.getTargetDataLine(fmt);
	                line.open(fmt, bufferByteSize);
	            } catch(LineUnavailableException e) {
	                System.err.println(e);
	                return;
	            }

	            byte[] buf = new byte[bufferByteSize];
	            float[] samples = new float[bufferByteSize / 2];

	            float lastPeak = 0f;

	            line.start();
	            int skip = 0;
	            float lastRMS = 0;
	            int timer = 0;
	            for(int b; (b = line.read(buf, 0, buf.length)) > -1;) {

	                // convert bytes to samples here
	                for(int i = 0, s = 0; i < b;) {
	                    int sample = 0;

	                    sample |= buf[i++] & 0xFF; // (reverse these two lines
	                    sample |= buf[i++] << 8;   //  if the format is big endian)

	                    // normalize to range of +/-1.0f
	                    samples[s++] = sample / 32768f;
	                }

	                float rms = 0f;
	                float peak = 0f;
	                for(float sample : samples) {

	                    float abs = Math.abs(sample);
	                    if(abs > peak) {
	                        peak = abs;
	                    }

	                    rms += sample * sample;
	                }

	                rms = (float)Math.sqrt(rms / samples.length);
	                
	                if(lastPeak > peak) {
	                    peak = lastPeak * 0.875f;
	                }

	                lastPeak = peak;
	                
	                
	                
	                
	                //my code
	                if(lastRMS>peak){
	                	if(timer>0){
		                	timer-=1;
		                	peak=lastRMS;
	                	}else{
	                		lastRMS=peak;
	                	}
	                }else{
	                	timer = s2.getValue();
	                	lastRMS = peak;
	                }
	                peak-=((float)s3.getValue())/1000f;
	                rms-=((float)s3.getValue())/1000f;	
	                peak*=((float)s1.getValue())/100f;
	                rms*=((float)s1.getValue())/100f;
	                plotter1.setNewAmp((int) (rms*100f));
					plotter1.repaint();
					plotter2.setNewAmp((int) (peak*100f));
					plotter2.repaint();
					
	                if(skip==0){
						skip=10;
						
						for (Vibrator v : vibrators) {
							v.rumble(peak);
						}
					}else{
						skip--;
					}
				}
			}
		}.start();
	}
}