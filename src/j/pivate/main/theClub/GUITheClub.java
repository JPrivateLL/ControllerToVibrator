package j.pivate.main.theClub;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import j.pivate.main.vibrator.Vibrator;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JProgressBar;

public class GUITheClub extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public GUITheClub(List<Vibrator> v) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		pbV1 = new JProgressBar();
		panel.add(pbV1);
		
		pbA1 = new JProgressBar();
		panel.add(pbA1);
		
		pbM1 = new JProgressBar();
		panel.add(pbM1);
		
		pbE1 = new JProgressBar();
		panel.add(pbE1);
		
		verticalStrut = Box.createVerticalStrut(20);
		panel.add(verticalStrut);
		
		pbV2 = new JProgressBar();
		panel.add(pbV2);
		
		pbA2 = new JProgressBar();
		panel.add(pbA2);
		
		pbM2 = new JProgressBar();
		panel.add(pbM2);
		
		pbE2 = new JProgressBar();
		panel.add(pbE2);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_1);
		
		pbV3 = new JProgressBar();
		panel.add(pbV3);
		
		pbA3 = new JProgressBar();
		panel.add(pbA3);
		
		pbM3 = new JProgressBar();
		panel.add(pbM3);
		
		pbE3 = new JProgressBar();
		panel.add(pbE3);
		
		lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel);
		new theClub(v, this);
		
	}
	private Component verticalStrut;
	private Component verticalStrut_1;
	public JProgressBar pbE3;
	public JProgressBar pbE2;
	public JProgressBar pbE1;
	public JProgressBar pbM1;
	public JProgressBar pbA1;
	public JProgressBar pbV1;
	public JProgressBar pbM2;
	public JProgressBar pbA2;
	public JProgressBar pbV2;
	public JProgressBar pbM3;
	public JProgressBar pbA3;
	public JProgressBar pbV3;
	public JLabel lblNewLabel;
	
}
