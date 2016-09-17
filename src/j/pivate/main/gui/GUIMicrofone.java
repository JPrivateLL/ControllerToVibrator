package j.pivate.main.gui;


import j.pivate.main.Arduino;
import j.pivate.main.sound.Plotter;
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
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.List;

@SuppressWarnings("serial")
public class GUIMicrofone extends JFrame{
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
	private Plotter plotter1,plotter2;
	private final List<Vibrator> vibrators;
	public GUIMicrofone(List<Vibrator> v) {
		super("Sound To Rumble");
		System.out.println(v.size());
		this.vibrators = v;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {

				running = false;
				
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

		s1 = new JSlider(SwingConstants.VERTICAL, 0, 200, 100);
		s1.setToolTipText("Multiplier");
		s1.setLocation(200, 0);
		s1.setMajorTickSpacing(10);
		s1.setPaintTicks(true);
		s1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				System.out.println("Slider1: " + s1.getValue());
			}
		});
		SidePanel.add(s1, BorderLayout.WEST);

		s2 = new JSlider(SwingConstants.VERTICAL, 0, 10, 1);
		s2.setToolTipText("Min sound to vibrate(remove static noise)");
		s2.setLocation(200, 0);
		s2.setMajorTickSpacing(1);
		s2.setPaintTicks(true);
		s2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				System.out.println("Slider2: " + s2.getValue());
			}
		});

		SidePanel.add(s2, BorderLayout.WEST);
		
		plotter1 = new Plotter(this);
		getContentPane().add(plotter1);
		plotter1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		plotter2 = new Plotter(this);
		getContentPane().add(plotter2);
		plotter2.setLayout(null);
		plotter2.paint(getGraphics());
		plotter1.paint(getGraphics());

		pack();
		
		
		
		JOptionPane
		.showMessageDialog(
				null,
				"This is a demo and works only with microfone. Blow in the microfone and see what happens!\n\n"
						+ "There is a way to use this with speakers so your controllers rumbles when sound is played(watching...).\n"
						+ "Instal(inside rar) virtual audio cable, Then follow the instructions on the picture(almost to easy).\n"
						+ "This is a workaround because Java can't record sound from playback devices only from record devices.",
				"WIP", JOptionPane.INFORMATION_MESSAGE);
		
		

				
				
				new Thread()
				{
				    public void run() {
				        
				    	
						// Open a TargetDataLine for getting microphone input & sound level
						TargetDataLine line = null;
						final AudioFormat format = new AudioFormat(8000.0F, 8, 1, true, true);
						final DataLine.Info info = new DataLine.Info(TargetDataLine.class,
								format); // format is an AudioFormat object
						if (!AudioSystem.isLineSupported(info)) {
							JOptionPane.showMessageDialog(null,
									"The audio line is not supported", "ERROR",
									JOptionPane.ERROR_MESSAGE);
							System.exit(1);
						}
						// Obtain and open the line.
						try {
							line = (TargetDataLine) AudioSystem.getLine(info);
							line.open(format, 1000);
							line.start();
						} catch (final LineUnavailableException ex) {
							JOptionPane.showMessageDialog(null,
									"The TargetDataLine is Unavailable.", "ERROR",
									JOptionPane.ERROR_MESSAGE);
							System.exit(1);
						}

						while (running) {

							final byte[] bytes = new byte[2];
							line.read(bytes, 0, bytes.length);
							int i = calculateRMSLevel(bytes);// 0-100
							i *= s1.getValue();
							i /= 100;

							

							i -= s2.getValue();

							if (i < 0) {
								i = 0;
							}
							plotter1.setNewAmp(i);
							plotter1.repaint();

							plotter2.setNewAmp(Arduino.getFSRStrength()/10);
							plotter2.repaint();
							
							if (i > 100) {
								i = 100;
							}
							for (Vibrator v: vibrators) {
								v.rumble(i);
							}
							;
						}
						line.close();
						System.exit(0);

				    	
				    	
				    }
				}.start();
	}

}