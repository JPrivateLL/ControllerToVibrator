package j.pivate.main.skyrim.GUI;

import j.pivate.main.gui.GUIStartMenu;
import j.pivate.main.skyrim.vibnew.VibrationList;
import j.pivate.main.skyrim.SexlabMainThread;
import j.pivate.main.skyrim.passClass;
import j.pivate.main.skyrim.vibnew.VibrationGroup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.BevelBorder;

public class GUISkyrim extends JFrame {
	private static final long serialVersionUID = 1L;

	private boolean running = false;

	static Preferences prefs;
	private final static String version = "Controller Rumble For Skyrim mods";
	private JTextPane debugScreen;
	private JCheckBox check1, check3;
	private Document doc;
	private JLabel lineCounterLabel, SLAIndicatorLabel, RunningVibrationsLabel;
	private int lineCounter = 0;

	private final JRadioButtonMenuItem rbC1S25, rbC1S50, rbC1S75, rbC1S100,
			rbC1S125, rbC1S150, rbC1S175, rbC1S200;
	private final JRadioButtonMenuItem rbC2S25, rbC2S50, rbC2S75, rbC2S100,
			rbC2S125, rbC2S150, rbC2S175, rbC2S200;
	private final JRadioButtonMenuItem rbC3S25, rbC3S50, rbC3S75, rbC3S100,
			rbC3S125, rbC3S150, rbC3S175, rbC3S200;
	private final JRadioButtonMenuItem rbC4S25, rbC4S50, rbC4S75, rbC4S100,
			rbC4S125, rbC4S150, rbC4S175, rbC4S200;

	private SexlabMainThread mainThread;

	private String[] controllerName = { "none", "none", "none", "none" };

	private float strengthController1, strengthController2, strengthController3,
			strengthController4;

	private boolean testVibrateOn = false;

	public void addErrorToDebugScreen(final String text) {
		setColorDebugScreen(text, Color.RED);
	}

	public void addNotImportantTextToDebugScreen(final String text) {
		setColorDebugScreen(text, Color.LIGHT_GRAY);
	}

	public void addOneToLineCounter() {
		lineCounter++;
		setLineCounterTo(lineCounter);
	}

	public void addTextToDebugScreen(final String text) {
		setColorDebugScreen(text, Color.DARK_GRAY);
	}

	public void addWaringToDebugScreen(final String text) {
		setColorDebugScreen(text, new Color(255, 105, 10));
		System.out.println(text);
	}

	public void cleanDebugScreen() {
		try {
			doc.remove(0, doc.getLength());
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}

	public float getStrengthController(final int i) {
		if (i == 0) {
			return strengthController1;
		} else if (i == 1) {
			return strengthController2;
		} else if (i == 2) {
			return strengthController3;
		} else {
			return strengthController4;
		}
	}

	public void restartLineCounter() {
		lineCounter = 0;

		setLineCounterTo(lineCounter);
	}

	public void setCheckControllerConnected(final boolean set) {
		check1.setSelected(set);
	}

	public void setCheckGameFocus(final boolean set) {
		check3.setSelected(set);
	}

	private void setColorDebugScreen(final String text, final Color c) {
		final StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
				StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily,
				"Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment,
				StyleConstants.ALIGN_JUSTIFIED);

		final int len = debugScreen.getDocument().getLength();
		debugScreen.setCaretPosition(len);
		debugScreen.setCharacterAttributes(aset, false);
		final Style style = debugScreen.addStyle("I'm a Style", null);
		StyleConstants.setForeground(style, c);
		try {
			doc.insertString(doc.getLength(), text + "\n", style);
		} catch (final BadLocationException e) {
		}
	}

	private void setLineCounterTo(final int i) {
		lineCounterLabel.setText("Papyrus line: " + i);
	}

	public void setSLAIndicator(final int level) {
		if (level == -1) {
			SLAIndicatorLabel.setText("Aroused lvl:disabled");
		} else {
			SLAIndicatorLabel.setText("Aroused lvl:" + level);
		}

	}

	public void setStraponLabel(boolean b){
		lblStrapon.setText("Strapon: "+b);
	}
	
	public void setEquipedLabel(String[] items) {
		lblEquiped.setText("Equiped: vaginal:" + items[0] + ", piercing:"
				+ items[1] + ", anal:" + items[2] + ", breasts:" + items[3]);
	}

	public void setRunningVibrations(int amount) {
		RunningVibrationsLabel.setText("Running Vibrations:" + amount);
	}

	public boolean testVibrate() {
		return testVibrateOn;
	}

	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	private final ButtonGroup buttonGroup_2 = new ButtonGroup();

	private final ButtonGroup buttonGroup_3 = new ButtonGroup();

	private final ButtonGroup buttonGroup_4 = new ButtonGroup();
	private JPanel panel_1;
	private JLabel lblStrapon;
	private JLabel lblEquiped;
	private JPanel panel_2;
	private JLabel lblMadeByJprivate;
	private JPanel panel_16;
	private JPanel panel_17;
	private JLabel lblTimer;
	private JPanel panel_18;

	public GUISkyrim(SexlabMainThread skyrimMainThread) {
		super(version);
		// everything done from now on is non user input
		nonUser = false;

		this.mainThread = skyrimMainThread;

		prefs = GUIStartMenu.getPrefs();
		if (running) {
			throw new RuntimeException("GUI already loaded, clicked to fast?");
		}
		running = true;

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(GUISkyrim.class.getResource(
						"/com/sun/javafx/webkit/prism/resources/mediaPlayDisabled.png")));
		final Container pane = getContentPane();

		// custom close
		final WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				mainThread.terminate();
				System.exit(0);
			}
		};
		this.addWindowListener(exitListener);

		final JPanel topPanel = new JPanel(new GridLayout(2, 0));
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		pane.add(topPanel, BorderLayout.PAGE_START);

		// debugScreen = new JTextArea("Debug Screen Loaded", 20, 80);
		debugScreen = new JTextPane();
		doc = debugScreen.getDocument();
		addTextToDebugScreen("Debug Screen Loaded!");
		debugScreen.setEditable(false);
		// debugScreen.setLineWrap(true);

		final DefaultCaret caret = (DefaultCaret) debugScreen.getCaret();

		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		panel_1 = new JPanel();
		panel_1.setBorder(
				new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		getContentPane().add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BorderLayout(0, 0));

		panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		panel_18 = new JPanel();
		panel_2.add(panel_18, BorderLayout.SOUTH);
		panel_18.setLayout(new MigLayout("", "[]", "[][][][][]"));

		RunningVibrationsLabel = new JLabel("Running Vibrations:--");
		panel_18.add(RunningVibrationsLabel, "cell 0 0");
		
		lblStrapon = new JLabel("Strapon: --");
		panel_18.add(lblStrapon, "cell 0 1");

		lblEquiped = new JLabel("Equiped:--");
		panel_18.add(lblEquiped, "cell 0 2");

		SLAIndicatorLabel = new JLabel("Arousel lvl:--");
		panel_18.add(SLAIndicatorLabel, "cell 0 3");

		lblTimer = new JLabel("Timer:--");
		panel_18.add(lblTimer, "cell 0 4");

		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VibrationGroup lg = VibrationList.getLast();
				if (lg == null)	lg = VibrationList.get(0);
				if (lg != null){
					passClass.guiSkyrim.setVisible(false);
					JFrame csa = new GUISkyrimAnimator(lg);
					csa.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null,
						"No vibrations to edit, try running some animations first.");
				}
			}
		});
		panel_2.add(btnEdit, BorderLayout.CENTER);

		pane.add(new JScrollPane(debugScreen), BorderLayout.CENTER);

		final JPanel checkBoxesPanel = new JPanel(new GridLayout(0, 2));
		final Border checkBoxesBorder = BorderFactory
				.createTitledBorder("All checkboxes need to be checked!");
		checkBoxesPanel.setBorder(checkBoxesBorder);
		check1 = new JCheckBox("Controller is connected");
		check1.setToolTipText("Checks if controller is connected");
		check1.setForeground(Color.BLACK);
		check1.setEnabled(false);
		checkBoxesPanel.add(check1);
		check3 = new JCheckBox("Game has focus");
		check3.setToolTipText("check if skyrim is the sellected window");
		check3.setForeground(Color.BLACK);
		check3.setEnabled(false);
		checkBoxesPanel.add(check3);
		topPanel.add(checkBoxesPanel);

		final JButton b2 = new JButton("Stop All");
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				mainThread.removeAllVibrations();
			}
		});

		final JMenuBar menuBar = new JMenuBar();
		topPanel.add(menuBar);

		final JMenu mnNewMenu = new JMenu("Strength");
		menuBar.add(mnNewMenu);

		controllerName = this.mainThread.getVibratorList().getName();

		final JMenu mnController1 = new JMenu(controllerName[0]);

		if (controllerName[0].equals("none")) {
			mnController1.setVisible(false);
		}
		mnNewMenu.add(mnController1);

		rbC1S25 = new JRadioButtonMenuItem("25%");
		buttonGroup_1.add(rbC1S25);
		mnController1.add(rbC1S25);

		rbC1S50 = new JRadioButtonMenuItem("50%");
		buttonGroup_1.add(rbC1S50);
		mnController1.add(rbC1S50);

		rbC1S75 = new JRadioButtonMenuItem("75%");
		buttonGroup_1.add(rbC1S75);
		mnController1.add(rbC1S75);

		rbC1S100 = new JRadioButtonMenuItem("100%");
		buttonGroup_1.add(rbC1S100);
		mnController1.add(rbC1S100);

		rbC1S125 = new JRadioButtonMenuItem("125%");
		buttonGroup_1.add(rbC1S125);
		mnController1.add(rbC1S125);

		rbC1S150 = new JRadioButtonMenuItem("150%");
		buttonGroup_1.add(rbC1S150);
		mnController1.add(rbC1S150);

		rbC1S175 = new JRadioButtonMenuItem("175%");
		buttonGroup_1.add(rbC1S175);
		mnController1.add(rbC1S175);

		rbC1S200 = new JRadioButtonMenuItem("200%");
		buttonGroup_1.add(rbC1S200);
		mnController1.add(rbC1S200);

		final float oldStrengthController1 = prefs.getFloat(controllerName[0],
				1.0f);
		if (oldStrengthController1 == 0.25f) {
			rbC1S25.setSelected(true);
			strengthController1 = 0.25f;
		} else if (oldStrengthController1 == 0.50f) {
			rbC1S50.setSelected(true);
			strengthController1 = 0.50f;
		} else if (oldStrengthController1 == 0.75f) {
			rbC1S75.setSelected(true);
			strengthController1 = 0.75f;
		} else if (oldStrengthController1 == 1.00f) {
			rbC1S100.setSelected(true);
			strengthController1 = 1.00f;
		} else if (oldStrengthController1 == 1.25f) {
			rbC1S125.setSelected(true);
			strengthController1 = 1.25f;
		} else if (oldStrengthController1 == 1.50f) {
			rbC1S150.setSelected(true);
			strengthController1 = 1.50f;
		} else if (oldStrengthController1 == 1.75f) {
			rbC1S175.setSelected(true);
			strengthController1 = 1.75f;
		} else if (oldStrengthController1 == 2.00f) {
			rbC1S200.setSelected(true);
			strengthController1 = 2.00f;
		}

		mnController1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(100);
						} catch (final InterruptedException e) {
							e.printStackTrace();
						}
						if (rbC1S25.isSelected()) {
							strengthController1 = 0.25f;
						} else if (rbC1S50.isSelected()) {
							strengthController1 = 0.50f;
						} else if (rbC1S75.isSelected()) {
							strengthController1 = 0.75f;
						} else if (rbC1S100.isSelected()) {
							strengthController1 = 1.00f;
						} else if (rbC1S125.isSelected()) {
							strengthController1 = 1.25f;
						} else if (rbC1S150.isSelected()) {
							strengthController1 = 1.50f;
						} else if (rbC1S175.isSelected()) {
							strengthController1 = 1.75f;
						} else if (rbC1S200.isSelected()) {
							strengthController1 = 2.00f;
						}

						prefs.putFloat(controllerName[0], strengthController1);
					}
				}.start();
			}
		});

		// strength controller 2

		final JMenu mnController2 = new JMenu(controllerName[1]);
		if (controllerName[1].equals("none")) {
			mnController2.setVisible(false);
		}
		mnNewMenu.add(mnController2);

		rbC2S25 = new JRadioButtonMenuItem("25%");
		buttonGroup_2.add(rbC2S25);
		mnController2.add(rbC2S25);

		rbC2S50 = new JRadioButtonMenuItem("50%");
		buttonGroup_2.add(rbC2S50);
		mnController2.add(rbC2S50);

		rbC2S75 = new JRadioButtonMenuItem("75%");
		buttonGroup_2.add(rbC2S75);
		mnController2.add(rbC2S75);

		rbC2S100 = new JRadioButtonMenuItem("100%");
		buttonGroup_2.add(rbC2S100);
		mnController2.add(rbC2S100);

		rbC2S125 = new JRadioButtonMenuItem("125%");
		buttonGroup_2.add(rbC2S125);
		mnController2.add(rbC2S125);

		rbC2S150 = new JRadioButtonMenuItem("150%");
		buttonGroup_2.add(rbC2S150);
		mnController2.add(rbC2S150);

		rbC2S175 = new JRadioButtonMenuItem("175%");
		buttonGroup_2.add(rbC2S175);
		mnController2.add(rbC2S175);

		rbC2S200 = new JRadioButtonMenuItem("200%");
		buttonGroup_2.add(rbC2S200);
		mnController2.add(rbC2S200);

		final float oldStrengthController2 = prefs.getFloat(controllerName[1],
				1.0f);
		if (oldStrengthController2 == 0.25f) {
			rbC2S25.setSelected(true);
			strengthController2 = 0.25f;
		} else if (oldStrengthController2 == 0.50f) {
			rbC2S50.setSelected(true);
			strengthController2 = 0.50f;
		} else if (oldStrengthController2 == 0.75f) {
			rbC2S75.setSelected(true);
			strengthController2 = 0.75f;
		} else if (oldStrengthController2 == 1.00f) {
			rbC2S100.setSelected(true);
			strengthController2 = 1.00f;
		} else if (oldStrengthController2 == 1.25f) {
			rbC2S125.setSelected(true);
			strengthController2 = 1.25f;
		} else if (oldStrengthController2 == 1.50f) {
			rbC2S150.setSelected(true);
			strengthController2 = 1.50f;
		} else if (oldStrengthController2 == 1.75f) {
			rbC2S175.setSelected(true);
			strengthController2 = 1.75f;
		} else if (oldStrengthController2 == 2.00f) {
			rbC2S200.setSelected(true);
			strengthController2 = 2.00f;
		}

		mnController2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(100);
						} catch (final InterruptedException e) {
							e.printStackTrace();
						}
						if (rbC2S25.isSelected()) {
							strengthController2 = 0.25f;
						} else if (rbC2S50.isSelected()) {
							strengthController2 = 0.50f;
						} else if (rbC2S75.isSelected()) {
							strengthController2 = 0.75f;
						} else if (rbC2S100.isSelected()) {
							strengthController2 = 1.00f;
						} else if (rbC2S125.isSelected()) {
							strengthController2 = 1.25f;
						} else if (rbC2S150.isSelected()) {
							strengthController2 = 1.50f;
						} else if (rbC2S175.isSelected()) {
							strengthController2 = 1.75f;
						} else if (rbC2S200.isSelected()) {
							strengthController2 = 2.00f;
						}

						prefs.putFloat(controllerName[1], strengthController2);
					}
				}.start();
			}
		});

		// controller 3

		final JMenu mnController3 = new JMenu(controllerName[2]);
		if (controllerName[2].equals("none")) {
			mnController3.setVisible(false);
		}
		mnNewMenu.add(mnController3);

		rbC3S25 = new JRadioButtonMenuItem("25%");
		buttonGroup_3.add(rbC3S25);
		mnController3.add(rbC3S25);

		rbC3S50 = new JRadioButtonMenuItem("50%");
		buttonGroup_3.add(rbC3S50);
		mnController3.add(rbC3S50);

		rbC3S75 = new JRadioButtonMenuItem("75%");
		buttonGroup_3.add(rbC3S75);
		mnController3.add(rbC3S75);

		rbC3S100 = new JRadioButtonMenuItem("100%");
		buttonGroup_3.add(rbC3S100);
		mnController3.add(rbC3S100);

		rbC3S125 = new JRadioButtonMenuItem("125%");
		buttonGroup_3.add(rbC3S125);
		mnController3.add(rbC3S125);

		rbC3S150 = new JRadioButtonMenuItem("150%");
		buttonGroup_3.add(rbC3S150);
		mnController3.add(rbC3S150);

		rbC3S175 = new JRadioButtonMenuItem("175%");
		buttonGroup_3.add(rbC3S175);
		mnController3.add(rbC3S175);

		rbC3S200 = new JRadioButtonMenuItem("200%");
		buttonGroup_3.add(rbC3S200);
		mnController3.add(rbC3S200);

		final float oldStrengthController3 = prefs.getFloat(controllerName[2],
				1.0f);
		if (oldStrengthController3 == 0.25f) {
			rbC3S25.setSelected(true);
			strengthController3 = 0.25f;
		} else if (oldStrengthController3 == 0.50f) {
			rbC3S50.setSelected(true);
			strengthController3 = 0.50f;
		} else if (oldStrengthController3 == 0.75f) {
			rbC3S75.setSelected(true);
			strengthController3 = 0.75f;
		} else if (oldStrengthController3 == 1.00f) {
			rbC3S100.setSelected(true);
			strengthController3 = 1.00f;
		} else if (oldStrengthController3 == 1.25f) {
			rbC3S125.setSelected(true);
			strengthController3 = 1.25f;
		} else if (oldStrengthController3 == 1.50f) {
			rbC3S150.setSelected(true);
			strengthController3 = 1.50f;
		} else if (oldStrengthController3 == 1.75f) {
			rbC3S175.setSelected(true);
			strengthController3 = 1.75f;
		} else if (oldStrengthController3 == 2.00f) {
			rbC3S200.setSelected(true);
			strengthController3 = 2.00f;
		}

		mnController3.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(100);
						} catch (final InterruptedException e) {
							e.printStackTrace();
						}
						if (rbC3S25.isSelected()) {
							strengthController3 = 0.25f;
						} else if (rbC3S50.isSelected()) {
							strengthController3 = 0.50f;
						} else if (rbC3S75.isSelected()) {
							strengthController3 = 0.75f;
						} else if (rbC3S100.isSelected()) {
							strengthController3 = 1.00f;
						} else if (rbC3S125.isSelected()) {
							strengthController3 = 1.25f;
						} else if (rbC3S150.isSelected()) {
							strengthController3 = 1.50f;
						} else if (rbC3S175.isSelected()) {
							strengthController3 = 1.75f;
						} else if (rbC3S200.isSelected()) {
							strengthController3 = 2.00f;
						}

						prefs.putFloat(controllerName[2], strengthController3);
					}
				}.start();
			}
		});

		// controller 4

		final JMenu mnController4 = new JMenu(controllerName[3]);
		if (controllerName[3].equals("none")) {
			mnController4.setVisible(false);
		}
		mnNewMenu.add(mnController4);

		rbC4S25 = new JRadioButtonMenuItem("25%");
		buttonGroup_4.add(rbC4S25);
		mnController4.add(rbC4S25);

		rbC4S50 = new JRadioButtonMenuItem("50%");
		buttonGroup_4.add(rbC4S50);
		mnController4.add(rbC4S50);

		rbC4S75 = new JRadioButtonMenuItem("75%");
		buttonGroup_4.add(rbC4S75);
		mnController4.add(rbC4S75);

		rbC4S100 = new JRadioButtonMenuItem("100%");
		buttonGroup_4.add(rbC4S100);
		mnController4.add(rbC4S100);

		rbC4S125 = new JRadioButtonMenuItem("125%");
		buttonGroup_4.add(rbC4S125);
		mnController4.add(rbC4S125);

		rbC4S150 = new JRadioButtonMenuItem("150%");
		buttonGroup_4.add(rbC4S150);
		mnController4.add(rbC4S150);

		rbC4S175 = new JRadioButtonMenuItem("175%");
		buttonGroup_4.add(rbC4S175);
		mnController4.add(rbC4S175);

		rbC4S200 = new JRadioButtonMenuItem("200%");
		buttonGroup_4.add(rbC4S200);
		mnController4.add(rbC4S200);
		topPanel.add(b2);

		final float oldStrengthController4 = prefs.getFloat(controllerName[3],
				1.0f);
		if (oldStrengthController4 == 0.25f) {
			rbC4S25.setSelected(true);
			strengthController4 = 0.25f;
		} else if (oldStrengthController4 == 0.50f) {
			rbC4S50.setSelected(true);
			strengthController4 = 0.50f;
		} else if (oldStrengthController4 == 0.75f) {
			rbC4S75.setSelected(true);
			strengthController4 = 0.75f;
		} else if (oldStrengthController4 == 1.00f) {
			rbC4S100.setSelected(true);
			strengthController4 = 1.00f;
		} else if (oldStrengthController4 == 1.25f) {
			rbC4S125.setSelected(true);
			strengthController4 = 1.25f;
		} else if (oldStrengthController4 == 1.50f) {
			rbC4S150.setSelected(true);
			strengthController4 = 1.50f;
		} else if (oldStrengthController4 == 1.75f) {
			rbC4S175.setSelected(true);
			strengthController4 = 1.75f;
		} else if (oldStrengthController4 == 2.00f) {
			rbC4S200.setSelected(true);
			strengthController4 = 2.00f;
		}

		mnController4.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(100);
						} catch (final InterruptedException e) {
							e.printStackTrace();
						}
						if (rbC4S25.isSelected()) {
							strengthController4 = 0.25f;
						} else if (rbC4S50.isSelected()) {
							strengthController4 = 0.50f;
						} else if (rbC4S75.isSelected()) {
							strengthController4 = 0.75f;
						} else if (rbC4S100.isSelected()) {
							strengthController4 = 1.00f;
						} else if (rbC4S125.isSelected()) {
							strengthController4 = 1.25f;
						} else if (rbC4S150.isSelected()) {
							strengthController4 = 1.50f;
						} else if (rbC4S175.isSelected()) {
							strengthController4 = 1.75f;
						} else if (rbC4S200.isSelected()) {
							strengthController4 = 2.00f;
						}

						prefs.putFloat(controllerName[3], strengthController4);
					}
				}.start();
			}

		});

		final JButton testVibrate = new JButton("MODDERS ONLY Test Vibrate");
		topPanel.add(testVibrate);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		panel_16 = new JPanel();
		panel.add(panel_16, BorderLayout.WEST);
		panel_16.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lineCounterLabel = new JLabel("Line Counter");
		lineCounterLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_16.add(lineCounterLabel);

		panel_17 = new JPanel();
		FlowLayout flowLayout_9 = (FlowLayout) panel_17.getLayout();
		flowLayout_9.setAlignment(FlowLayout.RIGHT);
		panel.add(panel_17, BorderLayout.EAST);

		lblMadeByJprivate = new JLabel("Made by J.Private (www.loverslab.com)");
		panel_17.add(lblMadeByJprivate);
		lblMadeByJprivate.setHorizontalAlignment(SwingConstants.RIGHT);
		testVibrate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				testVibrateOn = true;

				new Thread(new Runnable() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(null,
								"Reading Papyrus log, click Ok to stop. \nTo use it close the game write something in papyruslog, save it and click the button test vibrate again.",
								"Vibrator Testing For Modders",
								JOptionPane.PLAIN_MESSAGE);

						testVibrateOn = false;
					}
				}).start();
			}
		});

		System.out.println("Skyrim Gui loaded");
		setPreferredSize(new Dimension(620, 420));
		pack();

		this.setVisible(true);
		nonUser = false;
	}

	boolean nonUser = false;
	private JButton btnEdit;

	public void setTimer(float time) {
		lblTimer.setText("Timer:" + time);
	}

}
