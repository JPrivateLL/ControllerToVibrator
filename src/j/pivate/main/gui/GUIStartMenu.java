package j.pivate.main.gui;

import gnu.io.NoSuchPortException;
import j.pivate.main.Start;
import j.pivate.main.skyrim.SexlabMainThread;
import j.pivate.main.sound.GUIMicrofone;
import j.pivate.main.vibrator.Vibrator;
import j.pivate.main.vibrator.VibratorArduino;
import j.pivate.main.vibrator.VibratorJavaController;
import j.pivate.main.vibrator.VibratorTrance;
import j.pivate.main.vibrator.VibratorXbox;
import j.pivate.main.video.VideoRumble;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;

public class GUIStartMenu extends JFrame {

	public static GUIStartMenu frame;

	private static final long serialVersionUID = 1L;
	private static Preferences prefs;

	private static ControllerEnvironment createDefaultEnvironment()
			throws ReflectiveOperationException {

		// Find constructor (class is package private, so we can't access it
		// directly)
		@SuppressWarnings("unchecked")
		final Constructor<ControllerEnvironment> constructor = (Constructor<ControllerEnvironment>) Class
				.forName("net.java.games.input.DefaultControllerEnvironment")
				.getDeclaredConstructors()[0];

		// Constructor is package private, so we have to deactivate access
		// control checks
		constructor.setAccessible(true);

		// Create object with default constructor
		return constructor.newInstance();
	}

	public static boolean focusCheckDisabled() {
		return chckbxDisableFocusCheck.isSelected();
	}

	public static String getMyDocumentsFolderLocation() {

		if (myDocuments == null) {
			try {
				final Process p = Runtime
						.getRuntime()
						.exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
				p.waitFor();

				final InputStream in = p.getInputStream();
				final byte[] b = new byte[in.available()];
				in.read(b);
				in.close();

				myDocuments = new String(b);
				myDocuments = myDocuments.split("\\s\\s+")[4];

				myDocuments = myDocuments.replace("\\", "/");
			} catch (final Throwable t) {
				t.printStackTrace();
			}
		}
		return myDocuments;
	}

	public static Preferences getPrefs() {
		return prefs;
	}

	private JComboBox<String> controller1, controller2, controller3,
			controller4;
	private QuadCheckBox vaginal1, vaginal2, vaginal3, vaginal4;
	private QuadCheckBox anal1, anal2, anal3, anal4;
	private QuadCheckBox breasts1, breasts2, breasts3, breasts4;
	private QuadCheckBox oral1, oral2, oral3, oral4;
	private QuadCheckBox damage1, damage2, damage3, damage4;

	private QuadCheckBox interaction1, interaction2, interaction3,
			interaction4;
	private JTabbedPane tabbedPane;

	private JButton btnSelectPapyrusLog;
	private static JCheckBox chckbxDisableFocusCheck;
	private String lastBrowseredVideoLocation, lastBrowseredLogLocation;
	final static Color colorBackGround = new Color(25, 25, 25);
	final static Color colorHeader = new Color(100, 190, 215);
	final static Color colorBackGroundSide = new Color(31, 32, 35);
	final static Color colorBorderSide = new Color(51, 51, 51);

	final static Color colorBorderMain = new Color(57, 57, 57);

	final static Color colorTextWhite = new Color(255, 255, 255);
	private JPanel contentPane;
	private String[] connectedVibrators;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	final DefaultComboBoxModel model1 = new DefaultComboBoxModel(
			connectedVibrators());
	@SuppressWarnings({ "unchecked", "rawtypes" })
	final DefaultComboBoxModel model2 = new DefaultComboBoxModel(
			connectedVibrators());
	@SuppressWarnings({ "unchecked", "rawtypes" })
	final DefaultComboBoxModel model3 = new DefaultComboBoxModel(
			connectedVibrators());
	@SuppressWarnings({ "unchecked", "rawtypes" })
	final DefaultComboBoxModel model4 = new DefaultComboBoxModel(
			connectedVibrators());

	boolean runningCheck = true;
	public List<Vibrator> connectedVibratorsList;
	boolean runningCheckDigital = true;

	String name1 = null;

	String type1 = null;
	String type2 = null;
	float strength = 0;
	boolean running2 = true;
	boolean skip2 = true;
	private static String myDocuments = null;
	private JTextField portNr;
	private JTextField frsPort;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GUIStartMenu() {
		frame = this;

		prefs = Preferences.userRoot().node(this.getClass().getName());

		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						GUIStartMenu.class
								.getResource("/com/sun/javafx/webkit/prism/resources/mediaPlayDisabled.png")));
		setTitle("Controller as vibrator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		this.setResizable(false);
		contentPane.setBackground(colorBackGroundSide);
		contentPane.setBorder(new EmptyBorder(0, 30, 0, 30));
		setBounds(100, 100, 500, 850);
		setContentPane(contentPane);
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		contentPane.setLayout(new BorderLayout(0, 0));

		final JPanel panel_main = new JPanel();
		panel_main.setBackground(colorBackGround);
		panel_main.setBorder(new CompoundBorder(new MatteBorder(0, 1, 0, 1,
				colorBorderSide), new EmptyBorder(20, 20, 20, 20)));
		contentPane.add(panel_main, BorderLayout.CENTER);
		panel_main.setLayout(new BorderLayout(0, 0));

		final JPanel panel_north = new JPanel();
		panel_main.add(panel_north, BorderLayout.NORTH);
		panel_north.setBorder(null);
		panel_north.setBackground(colorBackGround);
		panel_north.setLayout(new BoxLayout(panel_north, BoxLayout.Y_AXIS));
		final JPanel panel_controllers = new JPanel();
		panel_north.add(panel_controllers);
		panel_controllers.setBorder(null);
		panel_controllers.setBackground(colorBackGround);
		panel_controllers.setLayout(new BoxLayout(panel_controllers,
				BoxLayout.Y_AXIS));

		final JPanel panel_5 = new JPanel();
		final FlowLayout flowLayout_1 = (FlowLayout) panel_5.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_5.setBackground(colorHeader);
		panel_5.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null,
				null, null));
		panel_controllers.add(panel_5);

		final JLabel lblSelectControllersYou = new JLabel(
				"Select Controllers you want to use");
		lblSelectControllersYou.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectControllersYou.setForeground(colorTextWhite);
		lblSelectControllersYou.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(lblSelectControllersYou);

		final Component verticalStrut = Box.createVerticalStrut(4);
		panel_controllers.add(verticalStrut);

		final JPanel panel_6 = new JPanel();
		panel_6.setBackground(colorBorderMain);
		panel_6.setBorder(new EmptyBorder(1, 1, 1, 1));
		panel_controllers.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));

		final JPanel panel_controller1 = new JPanel();
		panel_6.add(panel_controller1);
		panel_controller1.setBorder(new EmptyBorder(5, 10, 5, 10));
		panel_controller1.setBackground(colorBackGround);
		panel_controller1.setLayout(new BoxLayout(panel_controller1,
				BoxLayout.X_AXIS));

		final JLabel lblController1 = new JLabel("Controller 1:");
		lblController1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblController1.setForeground(colorTextWhite);
		panel_controller1.add(lblController1);

		final Component horizontalStrut = Box.createHorizontalStrut(10);
		panel_controller1.add(horizontalStrut);

		controller1 = new JComboBox(model1);

		controller1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.put("controller1", controller1.getSelectedItem()
						.toString());

				if (controller1.getSelectedIndex() == 0) {
					vaginal1.setEnabled(false);
					anal1.setEnabled(false);
					breasts1.setEnabled(false);
					oral1.setEnabled(false);
					damage1.setEnabled(false);
					interaction1.setEnabled(false);
				} else {
					vaginal1.setEnabled(true);
					anal1.setEnabled(true);
					breasts1.setEnabled(true);
					oral1.setEnabled(true);
					damage1.setEnabled(true);
					interaction1.setEnabled(true);
				}
			}
		});
		panel_controller1.add(controller1);

		final Component verticalStrut_3 = Box.createVerticalStrut(1);
		panel_6.add(verticalStrut_3);

		final JPanel panel_controller2 = new JPanel();
		panel_6.add(panel_controller2);
		panel_controller2.setBorder(new EmptyBorder(5, 10, 5, 10));
		panel_controller2.setBackground(colorBackGround);
		panel_controller2.setLayout(new BoxLayout(panel_controller2,
				BoxLayout.X_AXIS));

		final JLabel lblController2 = new JLabel("Controller 2:");
		lblController2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblController2.setForeground(colorTextWhite);
		panel_controller2.add(lblController2);

		final Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		panel_controller2.add(horizontalStrut_1);

		controller2 = new JComboBox(model2);
		controller2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.put("controller2", controller2.getSelectedItem()
						.toString());

				if (controller2.getSelectedIndex() == 0) {
					vaginal2.setEnabled(false);
					anal2.setEnabled(false);
					breasts2.setEnabled(false);
					oral2.setEnabled(false);
					damage2.setEnabled(false);
					interaction2.setEnabled(false);
				} else {
					vaginal2.setEnabled(true);
					anal2.setEnabled(true);
					breasts2.setEnabled(true);
					oral2.setEnabled(true);
					damage2.setEnabled(true);
					interaction2.setEnabled(true);
				}
			}
		});
		panel_controller2.add(controller2);

		final Component verticalStrut_2 = Box.createVerticalStrut(1);
		panel_6.add(verticalStrut_2);

		final JPanel panel_controller3 = new JPanel();
		panel_6.add(panel_controller3);
		panel_controller3.setBorder(new EmptyBorder(5, 10, 5, 10));
		panel_controller3.setBackground(colorBackGround);
		panel_controller3.setLayout(new BoxLayout(panel_controller3,
				BoxLayout.X_AXIS));

		final JLabel lblController3 = new JLabel("Controller 3:");
		lblController3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblController3.setForeground(colorTextWhite);
		panel_controller3.add(lblController3);

		final Component horizontalStrut_2 = Box.createHorizontalStrut(10);
		panel_controller3.add(horizontalStrut_2);

		controller3 = new JComboBox(model3);
		controller3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.put("controller3", controller3.getSelectedItem()
						.toString());

				if (controller3.getSelectedIndex() == 0) {
					vaginal3.setEnabled(false);
					anal3.setEnabled(false);
					breasts3.setEnabled(false);
					oral3.setEnabled(false);
					damage3.setEnabled(false);
					interaction3.setEnabled(false);
				} else {
					vaginal3.setEnabled(true);
					anal3.setEnabled(true);
					breasts3.setEnabled(true);
					oral3.setEnabled(true);
					damage3.setEnabled(true);
					interaction3.setEnabled(true);
				}
			}
		});
		panel_controller3.add(controller3);

		final Component verticalStrut_1 = Box.createVerticalStrut(1);
		panel_6.add(verticalStrut_1);

		final JPanel panel_controller4 = new JPanel();
		panel_6.add(panel_controller4);
		panel_controller4.setBorder(new EmptyBorder(5, 10, 5, 10));
		panel_controller4.setBackground(colorBackGround);
		panel_controller4.setLayout(new BoxLayout(panel_controller4,
				BoxLayout.X_AXIS));

		final JLabel lblController4 = new JLabel("Controller 4:");
		lblController4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblController4.setForeground(colorTextWhite);
		panel_controller4.add(lblController4);

		final Component horizontalStrut_3 = Box.createHorizontalStrut(10);
		panel_controller4.add(horizontalStrut_3);

		controller4 = new JComboBox(model4);
		controller4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.put("controller4", controller4.getSelectedItem()
						.toString());

				if (controller4.getSelectedIndex() == 0) {
					vaginal4.setEnabled(false);
					anal4.setEnabled(false);
					breasts4.setEnabled(false);
					oral4.setEnabled(false);
					damage4.setEnabled(false);
					interaction4.setEnabled(false);
				} else {
					vaginal4.setEnabled(true);
					anal4.setEnabled(true);
					breasts4.setEnabled(true);
					oral4.setEnabled(true);
					damage4.setEnabled(true);
					interaction4.setEnabled(true);
				}
			}
		});
		panel_controller4.add(controller4);

		final Component verticalStrut_4 = Box.createVerticalStrut(13);
		panel_north.add(verticalStrut_4);

		final JPanel panel_2 = new JPanel();
		panel_north.add(panel_2);
		panel_2.setBackground(colorBackGround);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

		final JPanel panel_1 = new JPanel();
		final FlowLayout flowLayout_2 = (FlowLayout) panel_1.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null,
				null, null));
		panel_1.setBackground(colorHeader);
		panel_2.add(panel_1);

		final JLabel lblOptions = new JLabel("Controller options");
		lblOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblOptions.setForeground(colorTextWhite);
		lblOptions.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(lblOptions);

		final Component verticalStrut_5 = Box.createVerticalStrut(4);
		panel_2.add(verticalStrut_5);

		final JPanel panel_10 = new JPanel();
		panel_10.setBackground(colorBackGround);
		panel_10.setBorder(new LineBorder(colorBorderMain));
		panel_2.add(panel_10);
		panel_10.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

		final JPanel panel_3 = new JPanel();
		panel_3.setBackground(colorBackGround);
		final FlowLayout flowLayout_3 = (FlowLayout) panel_3.getLayout();
		flowLayout_3.setVgap(0);
		flowLayout_3.setHgap(0);
		panel_10.add(panel_3);

		final JButton btnRefresh = new JButton("Refresh controllerlist");
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final String[] j = connectedVibrators(true);

				for (int i = model1.getSize() - 1; i >= 1; i--) {
					model1.removeElementAt(i);
				}
				for (int i = model2.getSize() - 1; i >= 1; i--) {
					model2.removeElementAt(i);
				}
				for (int i = model3.getSize() - 1; i >= 1; i--) {
					model3.removeElementAt(i);
				}
				for (int i = model4.getSize() - 1; i >= 1; i--) {
					model4.removeElementAt(i);
				}

				for (int i = 1; i < j.length; i++) {
					model1.addElement(j[i]);
					model2.addElement(j[i]);
					model3.addElement(j[i]);
					model4.addElement(j[i]);

				}
			}
		});
		panel_3.add(btnRefresh);

		final Component verticalStrut_7 = Box.createVerticalStrut(13);
		panel_north.add(verticalStrut_7);

		final JPanel panel_11 = new JPanel();
		final FlowLayout flowLayout_5 = (FlowLayout) panel_11.getLayout();
		flowLayout_5.setAlignment(FlowLayout.LEFT);
		panel_north.add(panel_11);
		panel_11.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null,
				null, null));
		panel_11.setBackground(new Color(100, 190, 215));

		final JLabel lblProgramOptions = new JLabel("Program options");
		lblProgramOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgramOptions.setForeground(colorTextWhite);
		lblProgramOptions.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_11.add(lblProgramOptions);

		final Component verticalStrut_6 = Box.createVerticalStrut(4);
		panel_north.add(verticalStrut_6);

		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		tabbedPane.setUI(new CustomTabbedPaneUI());
		tabbedPane.setForeground(colorTextWhite);
		panel_main.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setBackground(colorBackGround);
		final JPanel Skyrim = new JPanel();
		Skyrim.setBackground(colorBackGround);
		tabbedPane.addTab("Skyrim", null, Skyrim, null);
		Skyrim.setLayout(new BorderLayout(0, 0));

		final JPanel panel_14 = new JPanel();
		Skyrim.add(panel_14, BorderLayout.CENTER);
		panel_14.setLayout(new BoxLayout(panel_14, BoxLayout.Y_AXIS));

		final JPanel panel_19 = new JPanel();
		panel_14.add(panel_19);
		panel_19.setBackground(colorBackGround);
		panel_19.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		final JTextPane txtpnToUseThis = new JTextPane();
		panel_19.add(txtpnToUseThis);
		txtpnToUseThis.setForeground(colorTextWhite);
		txtpnToUseThis.setBackground(colorBackGround);
		txtpnToUseThis.setEditable(false);
		txtpnToUseThis
				.setText("Make sure you have read the install/use instructions on the forum!\r\nStart by selecting the controllers you want to use above.");

		final JPanel panel_8 = new JPanel();
		panel_8.setBackground(colorBackGround);
		panel_19.add(panel_8);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.Y_AXIS));

		final JPanel panel_26 = new JPanel();
		panel_26.setBackground(colorBackGround);
		panel_8.add(panel_26);
		panel_26.setLayout(new BoxLayout(panel_26, BoxLayout.X_AXIS));

		final QuadCheckBox rdbtnTest = new QuadCheckBox("None", 0);
		rdbtnTest.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				rdbtnTest.setSelectionState(0);
			}
		});
		rdbtnTest.setForeground(colorTextWhite);
		rdbtnTest.setBackground(colorBackGround);
		panel_26.add(rdbtnTest);

		final QuadCheckBox qdchckbxSex = new QuadCheckBox("Sex", 1);
		qdchckbxSex.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				qdchckbxSex.setSelectionState(1);
			}
		});
		qdchckbxSex.setForeground(colorTextWhite);
		qdchckbxSex.setBackground(colorBackGround);
		qdchckbxSex.setText("Sex");
		panel_26.add(qdchckbxSex);

		final QuadCheckBox qdchckbxShock = new QuadCheckBox("Shock", 2);
		qdchckbxShock.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				qdchckbxShock.setSelectionState(2);
			}
		});
		qdchckbxShock.setForeground(colorTextWhite);
		qdchckbxShock.setBackground(colorBackGround);
		panel_26.add(qdchckbxShock);

		final QuadCheckBox qdchckbxBoth = new QuadCheckBox("Both", 3);
		qdchckbxBoth.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				qdchckbxBoth.setSelectionState(3);
			}
		});
		qdchckbxBoth.setForeground(colorTextWhite);
		qdchckbxBoth.setBackground(colorBackGround);
		panel_26.add(qdchckbxBoth);

		final JPanel panel_20 = new JPanel();
		panel_8.add(panel_20);
		panel_20.setLayout(new BoxLayout(panel_20, BoxLayout.Y_AXIS));

		final JPanel panel_9 = new JPanel();
		panel_20.add(panel_9);
		panel_9.setBackground(colorBackGround);
		panel_9.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		final JLabel lblController = new JLabel("Controller:");
		lblController.setForeground(colorTextWhite);
		lblController.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_9.add(lblController);

		final JPanel panel_18 = new JPanel();
		panel_9.add(panel_18);
		final GridBagLayout gbl_panel_18 = new GridBagLayout();
		gbl_panel_18.columnWidths = new int[] { 32, 32, 32, 32, 0 };
		gbl_panel_18.rowHeights = new int[] { 21, 0 };
		gbl_panel_18.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_18.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_18.setLayout(gbl_panel_18);

		final JLabel lblNewLabel = new JLabel("1");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_18.add(lblNewLabel, gbc_lblNewLabel);

		final JLabel label_2 = new JLabel("2");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.fill = GridBagConstraints.BOTH;
		gbc_label_2.insets = new Insets(0, 0, 0, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 0;
		panel_18.add(label_2, gbc_label_2);

		final JLabel label_3 = new JLabel("3");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.fill = GridBagConstraints.BOTH;
		gbc_label_3.insets = new Insets(0, 0, 0, 5);
		gbc_label_3.gridx = 2;
		gbc_label_3.gridy = 0;
		panel_18.add(label_3, gbc_label_3);

		final JLabel label_4 = new JLabel("4");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.fill = GridBagConstraints.BOTH;
		gbc_label_4.gridx = 3;
		gbc_label_4.gridy = 0;
		panel_18.add(label_4, gbc_label_4);

		final JPanel panel_12 = new JPanel();
		panel_20.add(panel_12);
		panel_12.setBackground(colorBackGround);
		panel_12.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		final JLabel lblVaginal = new JLabel("Vaginal:");
		lblVaginal.setForeground(colorTextWhite);
		lblVaginal.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_12.add(lblVaginal);

		final JPanel panel_13 = new JPanel();
		panel_12.add(panel_13);
		final GridBagLayout gbl_panel_13 = new GridBagLayout();
		gbl_panel_13.columnWidths = new int[] { 32, 32, 32, 32, 0 };
		gbl_panel_13.rowHeights = new int[] { 21, 0 };
		gbl_panel_13.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_13.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_13.setLayout(gbl_panel_13);

		vaginal1 = new QuadCheckBox("");
		vaginal1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {

				prefs.putInt("vaginal1", vaginal1.getSelectionState() == 3 ? 0
						: vaginal1.getSelectionState() + 1);
			}
		});
		vaginal1.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.fill = GridBagConstraints.BOTH;
		gbc_checkBox.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox.gridx = 0;
		gbc_checkBox.gridy = 0;
		panel_13.add(vaginal1, gbc_checkBox);

		vaginal2 = new QuadCheckBox("");
		vaginal2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("vaginal2", vaginal2.getSelectionState() == 3 ? 0
						: vaginal2.getSelectionState() + 1);
			}
		});
		vaginal2.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
		gbc_checkBox_1.fill = GridBagConstraints.BOTH;
		gbc_checkBox_1.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_1.gridx = 1;
		gbc_checkBox_1.gridy = 0;
		panel_13.add(vaginal2, gbc_checkBox_1);

		vaginal3 = new QuadCheckBox("");
		vaginal3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("vaginal3", vaginal3.getSelectionState() == 3 ? 0
						: vaginal3.getSelectionState() + 1);
			}
		});
		vaginal3.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_2 = new GridBagConstraints();
		gbc_checkBox_2.fill = GridBagConstraints.BOTH;
		gbc_checkBox_2.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_2.gridx = 2;
		gbc_checkBox_2.gridy = 0;
		panel_13.add(vaginal3, gbc_checkBox_2);

		vaginal4 = new QuadCheckBox("");
		vaginal4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("vaginal4", vaginal4.getSelectionState() == 3 ? 0
						: vaginal4.getSelectionState() + 1);
			}
		});
		vaginal4.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_3 = new GridBagConstraints();
		gbc_checkBox_3.fill = GridBagConstraints.BOTH;
		gbc_checkBox_3.gridx = 3;
		gbc_checkBox_3.gridy = 0;
		panel_13.add(vaginal4, gbc_checkBox_3);

		final JPanel panel_50 = new JPanel();
		panel_50.setBackground(colorBackGround);
		panel_20.add(panel_50);
		panel_50.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		final JLabel lblAnal = new JLabel("Anal:");
		lblAnal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAnal.setForeground(colorTextWhite);
		panel_50.add(lblAnal);

		final JPanel panel_15 = new JPanel();
		panel_50.add(panel_15);
		final GridBagLayout gbl_panel_15 = new GridBagLayout();
		gbl_panel_15.columnWidths = new int[] { 32, 32, 32, 32, 0 };
		gbl_panel_15.rowHeights = new int[] { 21, 0 };
		gbl_panel_15.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_15.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_15.setLayout(gbl_panel_15);

		anal1 = new QuadCheckBox("");
		anal1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("anal1", anal1.getSelectionState() == 3 ? 0
						: anal1.getSelectionState() + 1);
			}
		});
		anal1.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_4 = new GridBagConstraints();
		gbc_checkBox_4.fill = GridBagConstraints.BOTH;
		gbc_checkBox_4.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_4.gridx = 0;
		gbc_checkBox_4.gridy = 0;
		panel_15.add(anal1, gbc_checkBox_4);

		anal2 = new QuadCheckBox("");
		anal2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("anal2", anal2.getSelectionState() == 3 ? 0
						: anal2.getSelectionState() + 1);
			}
		});
		anal2.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_5 = new GridBagConstraints();
		gbc_checkBox_5.fill = GridBagConstraints.BOTH;
		gbc_checkBox_5.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_5.gridx = 1;
		gbc_checkBox_5.gridy = 0;
		panel_15.add(anal2, gbc_checkBox_5);

		anal3 = new QuadCheckBox("");
		anal3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("anal3", anal3.getSelectionState() == 3 ? 0
						: anal3.getSelectionState() + 1);
			}
		});
		anal3.setHorizontalAlignment(SwingConstants.CENTER);
		anal3.setForeground(colorTextWhite);
		final GridBagConstraints gbc_checkBox_6 = new GridBagConstraints();
		gbc_checkBox_6.fill = GridBagConstraints.BOTH;
		gbc_checkBox_6.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_6.gridx = 2;
		gbc_checkBox_6.gridy = 0;
		panel_15.add(anal3, gbc_checkBox_6);

		anal4 = new QuadCheckBox("");
		anal4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("anal4", anal4.getSelectionState() == 3 ? 0
						: anal4.getSelectionState() + 1);
			}
		});
		anal4.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_7 = new GridBagConstraints();
		gbc_checkBox_7.fill = GridBagConstraints.BOTH;
		gbc_checkBox_7.gridx = 3;
		gbc_checkBox_7.gridy = 0;
		panel_15.add(anal4, gbc_checkBox_7);

		final JPanel panel_21 = new JPanel();
		panel_21.setBackground(colorBackGround);
		panel_20.add(panel_21);
		panel_21.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		final JLabel lblNipplePiercing = new JLabel("breasts:");
		lblNipplePiercing.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNipplePiercing.setForeground(colorTextWhite);
		panel_21.add(lblNipplePiercing);

		final JPanel panel_22 = new JPanel();
		panel_21.add(panel_22);
		final GridBagLayout gbl_panel_22 = new GridBagLayout();
		gbl_panel_22.columnWidths = new int[] { 32, 32, 32, 32, 0 };
		gbl_panel_22.rowHeights = new int[] { 21, 0 };
		gbl_panel_22.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_22.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_22.setLayout(gbl_panel_22);

		breasts1 = new QuadCheckBox("");
		breasts1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("breasts1", breasts1.getSelectionState() == 3 ? 0
						: breasts1.getSelectionState() + 1);
			}
		});
		breasts1.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_12 = new GridBagConstraints();
		gbc_checkBox_12.fill = GridBagConstraints.BOTH;
		gbc_checkBox_12.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_12.gridx = 0;
		gbc_checkBox_12.gridy = 0;
		panel_22.add(breasts1, gbc_checkBox_12);

		breasts2 = new QuadCheckBox("");
		breasts2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("breasts2", breasts2.getSelectionState() == 3 ? 0
						: breasts2.getSelectionState() + 1);
			}
		});
		breasts2.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_13 = new GridBagConstraints();
		gbc_checkBox_13.fill = GridBagConstraints.BOTH;
		gbc_checkBox_13.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_13.gridx = 1;
		gbc_checkBox_13.gridy = 0;
		panel_22.add(breasts2, gbc_checkBox_13);

		breasts3 = new QuadCheckBox("");
		breasts3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("breasts3", breasts3.getSelectionState() == 3 ? 0
						: breasts3.getSelectionState() + 1);
			}
		});
		breasts3.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_14 = new GridBagConstraints();
		gbc_checkBox_14.fill = GridBagConstraints.BOTH;
		gbc_checkBox_14.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_14.gridx = 2;
		gbc_checkBox_14.gridy = 0;
		panel_22.add(breasts3, gbc_checkBox_14);

		breasts4 = new QuadCheckBox("");
		breasts4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("breasts4", breasts4.getSelectionState() == 3 ? 0
						: breasts4.getSelectionState() + 1);
			}
		});
		breasts4.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_15 = new GridBagConstraints();
		gbc_checkBox_15.fill = GridBagConstraints.BOTH;
		gbc_checkBox_15.gridx = 3;
		gbc_checkBox_15.gridy = 0;
		panel_22.add(breasts4, gbc_checkBox_15);

		final JPanel panel_23 = new JPanel();
		panel_23.setBackground(colorBackGround);
		panel_20.add(panel_23);
		panel_23.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		final JLabel lblOral_1 = new JLabel("Oral:");
		lblOral_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOral_1.setForeground(colorTextWhite);
		panel_23.add(lblOral_1);

		final JPanel panel_24 = new JPanel();
		panel_23.add(panel_24);
		final GridBagLayout gbl_panel_24 = new GridBagLayout();
		gbl_panel_24.columnWidths = new int[] { 32, 32, 32, 32, 0 };
		gbl_panel_24.rowHeights = new int[] { 21, 0 };
		gbl_panel_24.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_24.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_24.setLayout(gbl_panel_24);

		oral1 = new QuadCheckBox("");
		oral1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("oral1", oral1.getSelectionState() == 3 ? 0
						: oral1.getSelectionState() + 1);
			}
		});
		oral1.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_16 = new GridBagConstraints();
		gbc_checkBox_16.fill = GridBagConstraints.BOTH;
		gbc_checkBox_16.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_16.gridx = 0;
		gbc_checkBox_16.gridy = 0;
		panel_24.add(oral1, gbc_checkBox_16);

		oral2 = new QuadCheckBox("");
		oral2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("oral2", oral2.getSelectionState() == 3 ? 0
						: oral2.getSelectionState() + 1);
			}
		});
		oral2.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_17 = new GridBagConstraints();
		gbc_checkBox_17.fill = GridBagConstraints.BOTH;
		gbc_checkBox_17.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_17.gridx = 1;
		gbc_checkBox_17.gridy = 0;
		panel_24.add(oral2, gbc_checkBox_17);

		oral3 = new QuadCheckBox("");
		oral3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("oral3", oral3.getSelectionState() == 3 ? 0
						: oral3.getSelectionState() + 1);
			}
		});
		oral3.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_18 = new GridBagConstraints();
		gbc_checkBox_18.fill = GridBagConstraints.BOTH;
		gbc_checkBox_18.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_18.gridx = 2;
		gbc_checkBox_18.gridy = 0;
		panel_24.add(oral3, gbc_checkBox_18);

		oral4 = new QuadCheckBox("");
		oral4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("oral4", oral4.getSelectionState() == 3 ? 0
						: oral4.getSelectionState() + 1);
			}
		});
		oral4.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_19 = new GridBagConstraints();
		gbc_checkBox_19.fill = GridBagConstraints.BOTH;
		gbc_checkBox_19.gridx = 3;
		gbc_checkBox_19.gridy = 0;
		panel_24.add(oral4, gbc_checkBox_19);

		final JPanel panel_25 = new JPanel();
		panel_25.setBackground(colorBackGround);
		panel_20.add(panel_25);
		panel_25.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		final JPanel panel_27 = new JPanel();
		panel_27.setBackground(colorBackGround);
		panel_20.add(panel_27);
		panel_27.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		final JLabel lblDamage = new JLabel("Damage:");
		lblDamage.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDamage.setForeground(colorTextWhite);
		panel_27.add(lblDamage);

		final JPanel panel_28 = new JPanel();
		panel_27.add(panel_28);
		final GridBagLayout gbl_panel_28 = new GridBagLayout();
		gbl_panel_28.columnWidths = new int[] { 32, 32, 32, 32, 0 };
		gbl_panel_28.rowHeights = new int[] { 21, 0 };
		gbl_panel_28.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_28.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_28.setLayout(gbl_panel_28);

		damage1 = new QuadCheckBox("");
		damage1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("damage1", damage1.getSelectionState() == 3 ? 0
						: damage1.getSelectionState() + 1);
			}
		});
		damage1.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_24 = new GridBagConstraints();
		gbc_checkBox_24.fill = GridBagConstraints.BOTH;
		gbc_checkBox_24.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_24.gridx = 0;
		gbc_checkBox_24.gridy = 0;
		panel_28.add(damage1, gbc_checkBox_24);

		damage2 = new QuadCheckBox("");
		damage2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("damage2", damage2.getSelectionState() == 3 ? 0
						: damage2.getSelectionState() + 1);
			}
		});
		damage2.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_25 = new GridBagConstraints();
		gbc_checkBox_25.fill = GridBagConstraints.BOTH;
		gbc_checkBox_25.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_25.gridx = 1;
		gbc_checkBox_25.gridy = 0;
		panel_28.add(damage2, gbc_checkBox_25);

		damage3 = new QuadCheckBox("");
		damage3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("damage3", damage3.getSelectionState() == 3 ? 0
						: damage3.getSelectionState() + 1);
			}
		});
		damage3.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_26 = new GridBagConstraints();
		gbc_checkBox_26.fill = GridBagConstraints.BOTH;
		gbc_checkBox_26.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_26.gridx = 2;
		gbc_checkBox_26.gridy = 0;
		panel_28.add(damage3, gbc_checkBox_26);

		damage4 = new QuadCheckBox("");
		damage4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("damage4", damage4.getSelectionState() == 3 ? 0
						: damage4.getSelectionState() + 1);
			}
		});
		damage4.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_27 = new GridBagConstraints();
		gbc_checkBox_27.fill = GridBagConstraints.BOTH;
		gbc_checkBox_27.gridx = 3;
		gbc_checkBox_27.gridy = 0;
		panel_28.add(damage4, gbc_checkBox_27);

		// /

		//

		final JPanel panel_501 = new JPanel();
		panel_501.setBackground(colorBackGround);
		panel_20.add(panel_501);
		panel_501.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		final JLabel lblInteraction = new JLabel("Interaction:");
		lblInteraction.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInteraction.setForeground(colorTextWhite);
		panel_501.add(lblInteraction);

		final JPanel panel_151 = new JPanel();
		panel_501.add(panel_151);
		final GridBagLayout gbl_panel_151 = new GridBagLayout();
		gbl_panel_151.columnWidths = new int[] { 32, 32, 32, 32, 0 };
		gbl_panel_151.rowHeights = new int[] { 21, 0 };
		gbl_panel_151.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_151.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_151.setLayout(gbl_panel_151);

		interaction1 = new QuadCheckBox("");
		interaction1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("interaction1",
						interaction1.getSelectionState() == 3 ? 0
								: interaction1.getSelectionState() + 1);
			}
		});
		interaction1.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox14 = new GridBagConstraints();
		gbc_checkBox14.fill = GridBagConstraints.BOTH;
		gbc_checkBox14.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox14.gridx = 0;
		gbc_checkBox14.gridy = 0;
		panel_151.add(interaction1, gbc_checkBox14);

		interaction2 = new QuadCheckBox("");
		interaction2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("interaction2",
						interaction2.getSelectionState() == 3 ? 0
								: interaction2.getSelectionState() + 1);
			}
		});
		interaction2.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_112 = new GridBagConstraints();
		gbc_checkBox_112.fill = GridBagConstraints.BOTH;
		gbc_checkBox_112.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_112.gridx = 1;
		gbc_checkBox_112.gridy = 0;
		panel_151.add(interaction2, gbc_checkBox_112);

		interaction3 = new QuadCheckBox("");
		interaction3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("interaction3",
						interaction3.getSelectionState() == 3 ? 0
								: interaction3.getSelectionState() + 1);
			}
		});
		interaction3.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_223 = new GridBagConstraints();
		gbc_checkBox_223.fill = GridBagConstraints.BOTH;
		gbc_checkBox_223.insets = new Insets(0, 0, 0, 5);
		gbc_checkBox_223.gridx = 2;
		gbc_checkBox_223.gridy = 0;
		panel_151.add(interaction3, gbc_checkBox_223);

		interaction4 = new QuadCheckBox("");
		interaction4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putInt("interaction4",
						interaction4.getSelectionState() == 3 ? 0
								: interaction4.getSelectionState() + 1);
			}
		});
		interaction4.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_checkBox_323 = new GridBagConstraints();
		gbc_checkBox_323.fill = GridBagConstraints.BOTH;
		gbc_checkBox_323.gridx = 3;
		gbc_checkBox_323.gridy = 0;
		panel_151.add(interaction4, gbc_checkBox_323);

		final JTextPane txtpnAdadad = new JTextPane();
		txtpnAdadad
				.setText("Now select when the controller needs\nto vibrate. This can be done by select-\ning the checkboxes on the left.");
		txtpnAdadad.setForeground(Color.WHITE);
		txtpnAdadad.setEditable(false);
		txtpnAdadad.setBackground(new Color(25, 25, 25));
		panel_19.add(txtpnAdadad);

		final JTextPane textPane = new JTextPane();
		textPane.setText("If the log location was not found\nyou can select it manualy. Make\nsure the log was created by the\ngame first, the log is created\nwhen a save is loaded and\nmy mod is used.");
		textPane.setForeground(Color.WHITE);
		textPane.setEditable(false);
		textPane.setBackground(new Color(25, 25, 25));
		panel_19.add(textPane);

		btnSelectPapyrusLog = new JButton("Not selected!");
		panel_19.add(btnSelectPapyrusLog);
		btnSelectPapyrusLog.setAlignmentY(Component.TOP_ALIGNMENT);

		chckbxDisableFocusCheck = new JCheckBox("Disable focus check");
		panel_19.add(chckbxDisableFocusCheck);
		chckbxDisableFocusCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				prefs.putBoolean("chckbxDisableFocusCheck",
						chckbxDisableFocusCheck.isSelected());

			}
		});
		chckbxDisableFocusCheck.setForeground(colorTextWhite);
		chckbxDisableFocusCheck.setBackground(colorBackGround);

		final JTextPane txtpnEnableThisOption = new JTextPane();
		txtpnEnableThisOption
				.setText("Enable this option if you have problems with\r\nfocusing, this can happen if you play\r\nborderless fullscreen. Better not to use!");
		txtpnEnableThisOption.setForeground(Color.WHITE);
		txtpnEnableThisOption.setEditable(false);
		txtpnEnableThisOption.setBackground(new Color(25, 25, 25));
		panel_19.add(txtpnEnableThisOption);

		final JTextPane txtpnTheSettingsAre = new JTextPane();
		txtpnTheSettingsAre
				.setText("The settings are saved next time you can just hit start.");
		txtpnTheSettingsAre.setForeground(Color.WHITE);
		txtpnTheSettingsAre.setEditable(false);
		txtpnTheSettingsAre.setBackground(new Color(25, 25, 25));
		panel_19.add(txtpnTheSettingsAre);
		btnSelectPapyrusLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				while (true) {
					JOptionPane
							.showMessageDialog(
									null,
									"Selected \"Controller Rumble.0\" inside:\"..\\Documents\\My Games\\Skyrim\\Logs\\Script\\User\"",
									"Error", JOptionPane.INFORMATION_MESSAGE);
					final JFileChooser chooser = new JFileChooser(
							lastBrowseredLogLocation);
					final int choice = chooser.showOpenDialog(null);
					if (choice != JFileChooser.APPROVE_OPTION) {
						if (!lastBrowseredLogLocation
								.contains("Controller Rumble.0.log")) {
							btnSelectPapyrusLog
									.setText("Papyrus location(not selected)");
						}
						return;
					}

					final String name = chooser.getSelectedFile().getName();
					final String path = chooser.getSelectedFile().getPath();
					if (name.equals("Controller Rumble.0.log")) {
						prefs.put("lastBrowseredLogLocation", path);
						lastBrowseredLogLocation = path;
						btnSelectPapyrusLog
								.setText("Papyrus location(selected)");
						return;
					} else {
						JOptionPane.showMessageDialog(null, "Wrong file");
					}
				}
			}
		});

		final JButton btnStartSkyrimProgram = new JButton("Start reading log!");
		Skyrim.add(btnStartSkyrimProgram, BorderLayout.SOUTH);
		btnStartSkyrimProgram.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
			
				if (btnSelectPapyrusLog.getText().contains("not")) {
					JOptionPane.showMessageDialog(null,
							"No log location selected", "error",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				// start skyrim frame
				try {
					new SexlabMainThread(connectedVibratorsList(),lastBrowseredLogLocation);
					frame.setVisible(false);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});

		// //

		final JPanel Manual = new JPanel();
		Manual.setBackground(colorBackGround);
		tabbedPane.addTab("Manual", null, Manual, null);
		Manual.setLayout(new BorderLayout(0, 0));

		final JButton btnStartManualyVibration = new JButton(
				"Start Manualy Vibration");
		btnStartManualyVibration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				if (type1 == null) {
					JOptionPane
							.showMessageDialog(
									null,
									"No input device found click the button Select first!",
									"Error", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				connectedVibratorsList = connectedVibratorsList();
				running2 = true;
				skip2 = true;
				new Thread(new Runnable() {
					@Override
					public void run() {
						while (running2) {
							final Controller[] controllers = ControllerEnvironment
									.getDefaultEnvironment().getControllers();
							for (int i = 0; i < controllers.length; i++) {
								if (skip2) {
									controllers[i].poll();
									final net.java.games.input.EventQueue queue = controllers[i]
											.getEventQueue();
									final net.java.games.input.Event event = new Event();
									while (queue.getNextEvent(event)) {
										final net.java.games.input.Component comp = event
												.getComponent();
										comp.getName();
									}

								}
								
								
								controllers[i].poll();
								final net.java.games.input.EventQueue queue = controllers[i]
										.getEventQueue();
								final net.java.games.input.Event event = new Event();
								while (queue.getNextEvent(event) && !skip2) {
									final net.java.games.input.Component comp = event
											.getComponent();

									if (controllers[i].getName().equals(name1)) {
										if (comp.getName().endsWith(type1)) {
											if (comp.isAnalog()) {
												strength = event.getValue();
												if (strength < 0)
													strength = -strength;
												if (strength < 0.01)
													strength = 0;
												for (int j = 0; j < connectedVibratorsList.size(); j++) {
													connectedVibratorsList.get(j).rumble(strength);
													System.out
															.println(strength);
												}
											} else {
												if (event.getValue() == 1) {
													strength += 0.1;
													if (strength > 1)
														strength = 1;
													for (int j = 0; j < connectedVibratorsList
															.size(); j++) {
														connectedVibratorsList.get(j).rumble(
																strength);
													}
												}
											}
										} else if (type2 != null) {
											if (comp.getName().endsWith(type2)) {
												if (!comp.isAnalog()
														&& event.getValue() == 1) {
													strength -= 0.1;
													if (strength < 0.01)
														strength = 0;
													for (int j = 0; j < connectedVibratorsList
															.size(); j++) {
														connectedVibratorsList.get(j).rumble(
																strength);
													}
												}

											}
										}
									}
								}
							}
							skip2 = false;
							try {
								Thread.sleep(20);
							} catch (final InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();

				JOptionPane.showMessageDialog(null,
						"Click/move the buttons you have set", "error",
						JOptionPane.INFORMATION_MESSAGE);
				running2 = false;

				for (int j = 0; j < connectedVibratorsList.size(); j++) {
					connectedVibratorsList.get(j).rumble(0);
					connectedVibratorsList.get(j).release();
				}
			}
		});
		Manual.add(btnStartManualyVibration, BorderLayout.SOUTH);

		final JPanel panel_30 = new JPanel();
		panel_30.setBorder(new EmptyBorder(5, 10, 0, 10));
		panel_30.setBackground(colorBackGround);
		Manual.add(panel_30, BorderLayout.NORTH);
		panel_30.setLayout(new BoxLayout(panel_30, BoxLayout.Y_AXIS));

		final Component verticalStrut_9 = Box.createVerticalStrut(6);
		panel_30.add(verticalStrut_9);

		final JPanel panel_31 = new JPanel();
		panel_31.setBackground(colorBackGround);
		final FlowLayout flowLayout_6 = (FlowLayout) panel_31.getLayout();
		flowLayout_6.setHgap(0);
		flowLayout_6.setVgap(0);
		flowLayout_6.setAlignment(FlowLayout.LEFT);
		panel_30.add(panel_31);

		final JLabel lblSelectAController = new JLabel(
				"Select a input to control the speed: ");
		panel_31.add(lblSelectAController);
		lblSelectAController.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSelectAController.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectAController.setForeground(colorTextWhite);

		final JButton btnNewButton = new JButton("Select");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {

				running2 = false;
				name1 = null;
				type1 = null;
				type2 = null;
				runningCheckDigital = true;
				new Thread(new Runnable() {
					@Override
					public void run() {
						int skip = 10;
						while (runningCheck) {
							skip--;
							final Controller[] controllersTmp = ControllerEnvironment
									.getDefaultEnvironment().getControllers();
							final List<Controller> controllers = new ArrayList();

							for (int i = 0; i < controllersTmp.length; i++) {
								if (!controllersTmp[i].getClass()
										.getSimpleName().equals("RawMouse")) {
									controllers.add(controllersTmp[i]);
								}
							}
							if (controllers.size() == 0) {
								JOptionPane.showMessageDialog(null,
										"No input devices found!", "Error",
										JOptionPane.PLAIN_MESSAGE);
								contentPane.setVisible(true);
								break;
							}
							for (int i = 0; i < controllers.size(); i++) {
								controllers.get(i).poll();
								final net.java.games.input.EventQueue queue = controllers
										.get(i).getEventQueue();
								final net.java.games.input.Event event = new Event();
								while (queue.getNextEvent(event)) {
									final StringBuffer buffer = new StringBuffer(
											controllers.get(i).getName());
									buffer.append(" at ");
									buffer.append(event.getNanos())
											.append(", ");
									final net.java.games.input.Component comp = event
											.getComponent();
									buffer.append(comp.getName()).append(
											" changed to ");
									float value = event.getValue();
									if (comp.isAnalog()) {
										buffer.append(value);
									} else {
										if (value == 1.0f) {
											buffer.append("On");
										} else {
											buffer.append("Off");
										}
									}
									if (skip < 0) {
										if (comp.isAnalog()) {
											if (runningCheckDigital) {
												if (value < -0.5 && value > -1) {
													value = -value;
												}
												if (value > 0.5 && value < 1) {
													name1 = controllers.get(i)
															.getName();
													type1 = comp.getName();
													runningCheck = false;
													break;
												}
											}
										} else {
											if (runningCheckDigital) {
												runningCheckDigital = false;
												name1 = controllers.get(i)
														.getName();
												type1 = comp.getName();
											} else {
												if (!comp.getName().equals(
														type1)) {
													System.out.println(buffer);
													runningCheck = false;
													type2 = comp.getName();
													break;
												}
											}
										}
									}
								}
							}
							try {
								Thread.sleep(20);
							} catch (final InterruptedException e) {
								e.printStackTrace();
							}
						}
						if (type1 != null) {
							String out;
							if (type2 != null) {
								out = name1 + "\nOn button: " + " " + type1
										+ "\nOff button: " + type2;
							} else {
								out = name1 + "\nTrigger/Stick: " + " " + type1;
							}

							JOptionPane.showMessageDialog(null, out,
									"Input devices found",
									JOptionPane.PLAIN_MESSAGE);
							contentPane.setVisible(true);
						}
					}
				}).start();
				contentPane.setVisible(false);
				JOptionPane
						.showMessageDialog(
								null,
								"Click 2 bottons(first strength up, second strength down) or move 1 trigger/stick on your controller.\nA message will popup when you did this, you can close this window.",
								"Select controller", JOptionPane.PLAIN_MESSAGE);
				contentPane.setVisible(true);
				runningCheck = false;
			}
		});
		panel_31.add(btnNewButton);

		final JPanel panel_29 = new JPanel();
		panel_30.add(panel_29);
		panel_29.setBorder(null);
		panel_29.setBackground(colorBackGround);
		panel_29.setLayout(new BoxLayout(panel_29, BoxLayout.X_AXIS));

		final Component horizontalStrut_6 = Box.createHorizontalStrut(18);
		panel_29.add(horizontalStrut_6);

		final JPanel Video = new JPanel();
		Video.setBackground(colorBackGround);
		tabbedPane.addTab("Sound/Video", null, Video, null);
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				prefs.putInt("tabbedPane", tabbedPane.getSelectedIndex());
			}
		});
		Video.setLayout(new BorderLayout(0, 0));

		final JButton btnStartSoundTo = new JButton("Video Demo!");
		btnStartSoundTo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				loadPrefs();
				JFileChooser chooser = new JFileChooser(
						lastBrowseredVideoLocation);
				int choice = chooser.showOpenDialog(null);

				if (choice != JFileChooser.APPROVE_OPTION)
					return;
				prefs.put("lastBrowseredVideoLocation", chooser
						.getSelectedFile().getPath());

				try {
					System.out.println(chooser.getSelectedFile());
					new VideoRumble(connectedVibratorsList(), chooser.getSelectedFile());
					//frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnStartSoundTo.setForeground(Color.RED);
		Video.add(btnStartSoundTo, BorderLayout.SOUTH);

		final JTextPane txtpnThisIsStill = new JTextPane();
		txtpnThisIsStill.setBackground(colorBackGround);
		txtpnThisIsStill.setForeground(colorTextWhite);
		txtpnThisIsStill.setEditable(false);
		txtpnThisIsStill
				.setText("With this you can play every video you like with vibrations. To add vibrations to a video you just play it, then while watching hold down A on the controller to start recording and use the right trigger to change strength, to remove a vibration you just hold A. Now when you rewind the video it will play the recorded vibration.\n\nYou do need to have VLC player installed and you need a xbox controller or emulator, the controller is used to record vibration you still can play videos without.\n\nthere is a demo video in the program folder");
		Video.add(txtpnThisIsStill, BorderLayout.CENTER);
		
		JPanel Microfone = new JPanel();
		tabbedPane.addTab("Microfone", null, Microfone, null);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GUIMicrofone(connectedVibratorsList());

			}
		});
		Microfone.add(btnStart);

		final JPanel Settings = new JPanel();
		Settings.setForeground(colorTextWhite);
		Settings.setBackground(colorBackGround);
		tabbedPane.addTab("Settings", null, Settings, null);
		Settings.setLayout(new BorderLayout(0, 0));

		final JPanel panel_7 = new JPanel();
		Settings.add(panel_7, BorderLayout.NORTH);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(new Color(25, 25, 25));
		panel_7.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblForceResistentSensor = new JLabel("Force Resistent Sensor pin:");
		lblForceResistentSensor.setForeground(Color.WHITE);
		lblForceResistentSensor.setBackground(Color.WHITE);
		panel.add(lblForceResistentSensor);
		
		frsPort = new JTextField();
		frsPort.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				Integer number = null;
				try {  
					number = Integer.parseInt(frsPort.getText());  
				}catch(NumberFormatException nfe){  
					  frsPort.setText(Integer.toString(prefs.getInt("fsrAnalogPin", 0)));
					  JOptionPane.showMessageDialog(frame,
							    "invalid input(not a number)");
					  return;
				}  
				prefs.putInt("fsrAnalogPin", number);
			}
		});
		frsPort.setText("0");
		frsPort.setColumns(10);
		panel.add(frsPort);

		final JPanel panel_16 = new JPanel();
		panel_16.setBackground(colorBackGround);
		panel_16.setForeground(colorTextWhite);
		final FlowLayout flowLayout_8 = (FlowLayout) panel_16.getLayout();
		flowLayout_8.setAlignment(FlowLayout.LEFT);
		panel_7.add(panel_16);

		final JLabel lblArduinoComPort = new JLabel("Arduino Com port:");
		lblArduinoComPort.setForeground(colorTextWhite);
		lblArduinoComPort.setBackground(colorTextWhite);
		panel_16.add(lblArduinoComPort);

		portNr = new JTextField();
		portNr.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent arg0) {
				prefs.put("portNr", portNr.getText());
			}
		});
		panel_16.add(portNr);
		portNr.setText("Com7");
		portNr.setColumns(10);

		final JLabel lblLikelyToBe = new JLabel("Likely to be COM3 or higher");
		lblLikelyToBe.setForeground(colorTextWhite);
		panel_16.add(lblLikelyToBe);

		final JPanel panel_17 = new JPanel();
		panel_17.setBackground(colorBackGround);
		panel_17.setForeground(colorTextWhite);
		panel_7.add(panel_17);
		panel_17.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		final JLabel lblSetMinimumStrength = new JLabel("Set minimum strength");
		lblSetMinimumStrength.setForeground(colorTextWhite);
		panel_17.add(lblSetMinimumStrength);

		final JPanel panel_4 = new JPanel();
		final FlowLayout flowLayout = (FlowLayout) panel_4.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_main.add(panel_4, BorderLayout.SOUTH);
		panel_4.setBackground(colorBackGround);
		panel_4.setBorder(new CompoundBorder(new EmptyBorder(14, 0, 0, 0),
				new LineBorder(new Color(57, 57, 57))));
		
		final JLabel lblMadeByJprivate = new JLabel("Version:" + Start.version
				+ " Made by Mr. Private (www.loverslab.com) 2015 " + (Start.isDebugging() ? "DEBUG MODE!":"")
				);
		lblMadeByJprivate.setForeground(colorHeader);
		panel_4.add(lblMadeByJprivate);

		loadPrefs();

		final File f = new File(lastBrowseredLogLocation);
		if (lastBrowseredLogLocation.contains("Controller Rumble.0.log")
				&& f.exists()) {

			btnSelectPapyrusLog.setText("Papyrus location(found)");
		} else {
			btnSelectPapyrusLog.setText("Papyrus location(not found)");
		}

	}
	private List<Vibrator> connectedVibratorsList(){
		
		//get selected controllers
		String[] selectedControllers = {
				connectedVibrators[controller1.getSelectedIndex()],
				connectedVibrators[controller2.getSelectedIndex()],
				connectedVibrators[controller3.getSelectedIndex()],
				connectedVibrators[controller4.getSelectedIndex()] 
		};
		
		//get selected types
		final int[][] selectedTypes = 
			{{
				vaginal1.getSelectionState(),
				anal1.getSelectionState(),
				breasts1.getSelectionState(),
				oral1.getSelectionState(),
				damage1.getSelectionState(),
				interaction1.getSelectionState()
			},{		
				vaginal2.getSelectionState(),
				anal2.getSelectionState(),
				breasts2.getSelectionState(),
				oral2.getSelectionState(),
				damage2.getSelectionState(),
				interaction2.getSelectionState()
			},{	
				vaginal3.getSelectionState(),
				anal3.getSelectionState(),
				breasts3.getSelectionState(),
				oral3.getSelectionState(),
				damage3.getSelectionState(),
				interaction3.getSelectionState() 
			},{						
				vaginal4.getSelectionState(),
				anal4.getSelectionState(),
				breasts4.getSelectionState(),
				oral4.getSelectionState(),
				damage4.getSelectionState(),
				interaction4.getSelectionState() 
			}
		};
		
		
		List<Vibrator> v = new ArrayList<Vibrator>();
		boolean noneSelected = true;
		for (int i = 0; i < selectedControllers.length; i++) {
			String sc = selectedControllers[i];
			// check if something is selected
			if (!sc.contains("None")) {
				noneSelected = false;
			} else {
				continue;
			}

			// check 2 the same controllers
			for (int j = 0; j < selectedControllers.length; j++) {
				if (sc == selectedControllers[j] && i != j
						&& !sc.contains("None")) {
					JOptionPane.showMessageDialog(null,
							"2 the same controllers selected!",
							"Error", JOptionPane.INFORMATION_MESSAGE);
					return null;
				}
			}

			boolean[] typeList = new boolean[12];
			
			//getType for skyrim
			if (selectedTypes[i][0] == 1 || selectedTypes[i][0] == 3) {typeList[0] = true;}
			if (selectedTypes[i][0] == 2 || selectedTypes[i][0] == 3) {typeList[1] = true;}
			if (selectedTypes[i][1] == 1 || selectedTypes[i][1] == 3) {typeList[2] = true;}
			if (selectedTypes[i][1] == 2 || selectedTypes[i][1] == 3) {typeList[3] = true;}
			if (selectedTypes[i][2] == 1 || selectedTypes[i][2] == 3) {typeList[4] = true;}
			if (selectedTypes[i][2] == 2 || selectedTypes[i][2] == 3) {typeList[5] = true;}
			if (selectedTypes[i][3] == 1 || selectedTypes[i][3] == 3) {typeList[6] = true;}
			if (selectedTypes[i][3] == 2 || selectedTypes[i][3] == 3) {typeList[7] = true;}
			if (selectedTypes[i][4] == 1 || selectedTypes[i][4] == 3) {typeList[8] = true;}
			if (selectedTypes[i][4] == 2 || selectedTypes[i][4] == 3) {typeList[9] = true;}
			if (selectedTypes[i][5] == 1 || selectedTypes[i][5] == 3) {typeList[10] = true;}
			if (selectedTypes[i][5] == 2 || selectedTypes[i][5] == 3) {typeList[11] = true;}

			
			//create the vibrator and add it to the list
			if (sc.contains("Trance Vibrator")) {
				v.add(new VibratorTrance(typeList));
			} else if (sc.contains("Xbox")) {
				v.add(new VibratorXbox(Integer.parseInt(sc.substring(sc
						.length() - 1)), typeList));
			} else if (sc.contains("Arduino")) {
				try {
					int pin = Integer.parseInt(sc.substring(sc
							.indexOf("pin ") + 4));
					v.add(new VibratorArduino(typeList, pin));
				} catch (IllegalArgumentException | NoSuchPortException e) {
					JOptionPane.showMessageDialog(
							null,
							"Can't connect to Arduino, "
									+ e.getMessage(), "Error",
							JOptionPane.INFORMATION_MESSAGE);
					return null;
				}
			} else {
				v.add(new VibratorJavaController(typeList, sc));
			}

		}

		if (noneSelected) {
			JOptionPane.showMessageDialog(null,
					"No controller selected!", "error",
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		
		return v;
	}
	
	private String[] connectedVibrators() {
		return connectedVibrators(false);
	}

	private String[] connectedVibrators(final boolean update) {

		if (connectedVibrators == null || update) {
			Controller[] cs = null;
			try {
				cs = createDefaultEnvironment().getControllers();
			} catch (final ReflectiveOperationException e) {
				e.printStackTrace();
			}

			final ArrayList<String> controllerSupported = new ArrayList<String>();
			controllerSupported.add("None");

			for (int i = 0; i < cs.length; i++) {
				if (cs[i].getRumblers().length > 0) {
					controllerSupported.add(cs[i].getName());
				}
			}

			final boolean[] b = VibratorXbox.ConnectedControllers();
			for (int i = 0; i < b.length; i++) {
				if (b[i]) {
					controllerSupported.add("Xbox or Xbox emulator nr:"
							+ (i + 1));
				}
			}

			controllerSupported
					.add("Trance Vibrator (always shows up, no check)");

			controllerSupported.add("Arduino pin 3");
			controllerSupported.add("Arduino pin 5");
			controllerSupported.add("Arduino pin 6");
			controllerSupported.add("Arduino pin 9");
			controllerSupported.add("Arduino pin 10");
			controllerSupported.add("Arduino pin 11");

			final String[] array = new String[controllerSupported.size()];
			int i = 0;
			for (final String s : controllerSupported) {
				array[i++] = s;
			}
			connectedVibrators = array;
		}
		return connectedVibrators;
	}

	private void loadPrefs() {

		prefs.getBoolean("ardiunoTest", false);
		
		final String i1 = prefs.get("controller1", "none");
		final String i2 = prefs.get("controller2", "none");
		final String i3 = prefs.get("controller3", "none");
		final String i4 = prefs.get("controller4", "none");

		controller1.setSelectedIndex(0);
		controller2.setSelectedIndex(0);
		controller3.setSelectedIndex(0);
		controller4.setSelectedIndex(0);

		controller1.setSelectedItem(i1);
		controller2.setSelectedItem(i2);
		controller3.setSelectedItem(i3);
		controller4.setSelectedItem(i4);

		vaginal1.setSelectionState(prefs.getInt("vaginal1", 3));
		vaginal2.setSelectionState(prefs.getInt("vaginal2", 0));
		vaginal3.setSelectionState(prefs.getInt("vaginal3", 0));
		vaginal4.setSelectionState(prefs.getInt("vaginal4", 0));

		anal1.setSelectionState(prefs.getInt("anal1", 3));
		anal2.setSelectionState(prefs.getInt("anal2", 0));
		anal3.setSelectionState(prefs.getInt("anal3", 0));
		anal4.setSelectionState(prefs.getInt("anal4", 0));

		breasts1.setSelectionState(prefs.getInt("breasts1", 3));
		breasts2.setSelectionState(prefs.getInt("breasts2", 0));
		breasts3.setSelectionState(prefs.getInt("breasts3", 0));
		breasts4.setSelectionState(prefs.getInt("breasts4", 0));

		oral1.setSelectionState(prefs.getInt("oral1", 0));
		oral2.setSelectionState(prefs.getInt("oral2", 0));
		oral3.setSelectionState(prefs.getInt("oral3", 0));
		oral4.setSelectionState(prefs.getInt("oral4", 0));

		damage1.setSelectionState(prefs.getInt("damage1", 0));
		damage2.setSelectionState(prefs.getInt("damage2", 0));
		damage3.setSelectionState(prefs.getInt("damage3", 0));
		damage4.setSelectionState(prefs.getInt("damage4", 0));

		interaction1.setSelectionState(prefs.getInt("interaction1", 0));
		interaction2.setSelectionState(prefs.getInt("interaction2", 0));
		interaction3.setSelectionState(prefs.getInt("interaction3", 0));
		interaction4.setSelectionState(prefs.getInt("interaction4", 0));

		chckbxDisableFocusCheck.setSelected(prefs.getBoolean(
				"chckbxDisableFocusCheck", false));

		tabbedPane.setSelectedIndex(prefs.getInt("tabbedPane", 0));

		lastBrowseredVideoLocation = (prefs.get("lastBrowseredVideoLocation",
				"C:\\"));
		lastBrowseredLogLocation = (prefs
				.get("lastBrowseredLogLocation",
						getMyDocumentsFolderLocation()
								+ "\\My Games\\Skyrim\\Logs\\Script\\User\\Controller Rumble.0.log"));
		portNr.setText(prefs.get("portNr", "COM7"));
		frsPort.setText(Integer.toString(prefs.getInt("fsrAnalogPin", 0)));
	}

	public static void staticSetVisible(boolean b){
		frame.setVisible(b);
	}
	
	public static int getFsrAnalogPin(){
		return prefs.getInt("fsrAnalogPin", 0);
		
	}
	
	public static String getArduinoPort(){
		return prefs.get("portNr", "COM7");
	}
	
}
