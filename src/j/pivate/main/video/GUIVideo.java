package j.pivate.main.video;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import j.pivate.main.gui.GUIStartMenu;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class GUIVideo extends JFrame {

	private JPanel contentPane;

	private static String version = "Controller Rumble for movies V1.0";
	private JFrame frame;
	private JSlider scrollBar;
	private JProgressBar progressBar_0, progressBar_1, progressBar_2,
			progressBar_3;

	private final VideoRumble videoThread;
	public boolean record1 = false;
	public boolean record2 = false;
	public boolean record3 = false;
	public boolean record4 = false;
	public static float GUIStrength = 1;
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;

	public GUIVideo(EmbeddedMediaPlayerComponent component,
			VideoRumble thread) {
		super(version);
		this.mediaPlayerComponent = component;
		this.frame = this;
		this.videoThread = thread;
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent arg0) {
				videoThread.pauseMedia();
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {

				videoThread.stop();

				GUIStartMenu.staticSetVisible(true);
				frame.setVisible(false);
			}
		});

		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		final JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		setVisible(true);

		panel.setLayout(new CardLayout(0, 0));
		panel.add(this.mediaPlayerComponent);

		final JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		scrollBar = new JSlider();
		scrollBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (!updateSlider) {
					videoThread.changeTime((float) scrollBar.getValue()
							/ (float) scrollBar.getMaximum());
				}
			}
		});

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		progressBar_0 = new JProgressBar();
		progressBar_0.setOrientation(SwingConstants.VERTICAL);
		panel_2.add(progressBar_0);

		progressBar_1 = new JProgressBar();
		progressBar_1.setOrientation(SwingConstants.VERTICAL);
		panel_2.add(progressBar_1);

		progressBar_2 = new JProgressBar();
		progressBar_2.setOrientation(SwingConstants.VERTICAL);
		panel_2.add(progressBar_2);

		progressBar_3 = new JProgressBar();
		progressBar_3.setOrientation(SwingConstants.VERTICAL);
		panel_2.add(progressBar_3);

		JScrollBar scrollBar_1 = new JScrollBar();
		panel_2.add(scrollBar_1);
		scrollBar_1.setValue(5);
		scrollBar_1.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent evt) {
				float speed = 1f - (float) evt.getValue() / 10f;
				speed += 0.5f;
				videoThread.mediaPlayerComponent.getMediaPlayer()
						.setRate(speed);
			}
		});
		scrollBar_1.setMaximum(20);

		JScrollBar scrollBar_2 = new JScrollBar();
		scrollBar_2.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent evt) {
				float speed = 1f - (float) evt.getValue() / 10f;
				GUIStrength = speed;
			}
		});
		scrollBar_2.setValue(5);
		scrollBar_2.setMaximum(20);
		panel_2.add(scrollBar_2);

		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));

		JButton button_1 = new JButton("rec1");
		panel_3.add(button_1);
		button_1.setToolTipText(
				"Hold down left mouse and move mouse up/down/left/right to add vibrations");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.out.println("press");
				record1 = true;
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				System.out.println("released");
				record1 = false;
			}
		});

		JButton button_2 = new JButton("rec2");
		panel_3.add(button_2);
		button_2.setToolTipText(
				"Hold down left mouse and move mouse up/down/left/right to add vibrations");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.out.println("press");
				record2 = true;
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				System.out.println("released");
				record2 = false;
			}
		});

		JButton button_3 = new JButton("rec3");
		panel_3.add(button_3);
		button_3.setToolTipText(
				"Hold down left mouse and move mouse up/down/left/right to add vibrations");
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.out.println("press");
				record3 = true;
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				System.out.println("released");
				record3 = false;
			}
		});

		JButton button_4 = new JButton("rec4");
		panel_3.add(button_4);
		button_4.setToolTipText(
				"Hold down left mouse and move mouse up/down/left/right to add vibrations");
		button_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.out.println("press");
				record4 = true;
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				System.out.println("released");
				record4 = false;
			}
		});

		final JButton button_back = new JButton("<<");
		button_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				mediaPlayerComponent.getMediaPlayer().skip(-60000);
				updateSlider();
			}
		});
		panel_1.add(button_back);

		panel_1.add(scrollBar);
		scrollBar.setOrientation(Adjustable.HORIZONTAL);

		final JButton button = new JButton(">>");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				mediaPlayerComponent.getMediaPlayer().skip(60000);
				updateSlider();
			}
		});
		panel_1.add(button);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut);

		final JButton btnStartstop = new JButton("start/stop");
		panel_1.add(btnStartstop);
		btnStartstop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent arg0) {
				if (videoThread.mediaPlayerComponent.getMediaPlayer()
						.isPlaying()) {
					videoThread.mediaPlayerComponent.getMediaPlayer().pause();
				} else {
					videoThread.mediaPlayerComponent.getMediaPlayer().play();
				}

			}
		});

		try {
			Thread.sleep(10);
		} catch (final InterruptedException e3) {
			e3.printStackTrace();
		}
	}

	public void videoLoaded() {
		System.out.println(
				videoThread.mediaPlayerComponent.getMediaPlayer().getLength()
						/ 500);

		updateSlider = true;
		scrollBar.setMaximum((int) videoThread.mediaPlayerComponent
				.getMediaPlayer().getLength() / 500);
		updateSlider = false;
	}

	public boolean updateSlider = false;

	public void updateSlider() {
		// TODO
		updateSlider = true;
		scrollBar.setValue((int) (videoThread.mediaPlayerComponent
				.getMediaPlayer().getTime() / 500));
		updateSlider = false;
	}

	public void changeStrength(int i0, int i1, int i2, int i3) {
		progressBar_0.setValue(i0);
		progressBar_1.setValue(i1);
		progressBar_2.setValue(i2);
		progressBar_3.setValue(i3);
	}
}