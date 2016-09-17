package j.pivate.main.skyrim.vibnew;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import j.pivate.main.skyrim.vibnew.types.VibrationConstant;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.beans.PropertyChangeEvent;

public class GUISkyrimAnimator extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JComboBox<String> cboxName;
	private JComboBox<Integer> cboxPos;
	private JComboBox<Integer> cboxStage;
	private JComboBox<String> cboxVibTypes;
	private JComboBox<String> cboxTypes;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUISkyrimAnimator frame = new GUISkyrimAnimator(new CustomVibrations(), new VibrationGroup(0, 0));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	private CustomVibrations cvl;
	private VibrationGroup vg;
	private VibrationSet vs;
	
	public GUISkyrimAnimator(CustomVibrations cvl1, VibrationGroup vg1) {
		this.cvl = cvl1;
		this.vs = cvl.getSet(0);
		this.vg = vs.getGroup(0, 0);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 5, 0, 0));
		
		cboxName = new JComboBox<String>();
		

		cboxName.setModel(new DefaultComboBoxModel<String>(cvl.getNameList()));
		panel.add(cboxName);
		
		cboxStage = new JComboBox<Integer>();
		panel.add(cboxStage);
		
		cboxPos = new JComboBox<Integer>();
		panel.add(cboxPos);
		

		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnRemove = new JButton("Remove");
		

		panel_1.add(btnRemove);
		
		JButton btnNew = new JButton("New");

		
		JButton btnCopy = new JButton("Copy");

		panel_1.add(btnCopy);
		panel_1.add(btnNew);
		
		JLabel LBLTAGS = new JLabel("TAGS");
		contentPane.add(LBLTAGS, BorderLayout.SOUTH);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		
		
		model = new DefaultTableModel(
			new Object[][] {},
			new String[] {"Vibrate", "Type", "Strength", "Min Strength", "Time", "On Time", "Interval", "StartDelay", "Amount"}
		){
			public boolean isCellEditable(int row,int col){
				return usable.get(row, col);
			}
		};
		table = new JTable(model){
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		        Component comp = super.prepareRenderer(renderer, row, col);
		        TableModel value = getModel();
		        
		        
		        if(!value.isCellEditable(row, col)){
		        	comp.setBackground(Color.lightGray);
		        }else if(required.get(row,col)){
		        	comp.setBackground(Color.red);
		        }else{
		        	comp.setBackground(Color.white);
		        }
		        return comp;
		    }
		};
		table.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				updateUserInput();
			}
		});
		table.getTableHeader().setReorderingAllowed(false);
		
		//add combo box 0
		TableColumn vibTypeColumn = table.getColumnModel().getColumn(0);
		cboxVibTypes = new JComboBox<String>();
		
		for (String vibTypes : Vibration.VIBTYPES) {
			cboxVibTypes.addItem(vibTypes);
		}
		vibTypeColumn.setCellEditor(new DefaultCellEditor(cboxVibTypes));
		
		//add combo box 1
		TableColumn typeColumn = table.getColumnModel().getColumn(1);
		cboxTypes = new JComboBox<String>();
		for (String types : Vibration.TYPES) {
			cboxTypes.addItem(types);
		}
		typeColumn.setCellEditor(new DefaultCellEditor(cboxTypes));
		


		
		panel_2.add(new JScrollPane(table));
		
		cboxName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vg = cvl.getGroup(cboxName.getSelectedIndex(), 0, 0);
				updateTable();
			}
		});
		cboxStage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vg = cvl.getGroup(cboxName.getSelectedIndex(), cboxStage.getSelectedIndex(), 0);
				updateTable();
			}
		});
		cboxPos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vg = cvl.getGroup(cboxName.getSelectedIndex(), cboxStage.getSelectedIndex(), cboxPos.getSelectedIndex());
				updateTable();
			}
		});
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()<=model.getRowCount()&&table.getSelectedRow()!=-1){
					vg.remove(table.getSelectedRow());
					updateTable();
				}
			}
		});
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vg.add(new VibrationConstant());
				updateTable();
			}
		});
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vg.add(vg.get(cboxPos.getSelectedIndex()).clone());
				updateTable();
			}
		});
		
		
		
		//load selected vibration


		
		//cboxPos.setSelectedIndex(sg.getPos());
		//cboxStage.setSelectedIndex(sg.getStage());
		
		updateTable();
	}


	


	boolean nonUser = false;
	private void updateUserInput(){
		if(nonUser){
			return;
		}
			
		System.out.println("updateUserInput");
		
		
		//get all user inputs
		for (int row = 0; row < model.getRowCount(); row++) {
			
			int vibType =  			Arrays.asList(Vibration.VIBTYPES).indexOf(model.getValueAt(row, 0));
			int type =  			Arrays.asList(Vibration.TYPES).indexOf(model.getValueAt(row, 1));
			float strength = 0;		try{strength = Float.valueOf((String) model.getValueAt(row, 2));}catch(NumberFormatException e){}
			float minStrength = 0;	try{minStrength = Float.valueOf((String) model.getValueAt(row, 3));}catch(NumberFormatException e){}
			float time = 0;			try{time = Float.valueOf((String) model.getValueAt(row, 4));}catch(NumberFormatException e){}
			float onTime = 0;		try{onTime = Float.valueOf((String) model.getValueAt(row, 5));}catch(NumberFormatException e){}
			float interval = 0;		try{interval = Float.valueOf((String) model.getValueAt(row, 6));}catch(NumberFormatException e){}
			float startDelay = 0;	try{startDelay = Float.valueOf((String) model.getValueAt(row, 7));}catch(NumberFormatException e){}
			float amount = 0;		try{amount = Float.valueOf((String) model.getValueAt(row, 8));}catch(NumberFormatException e){}

			
			//set all user inputs
			if(vg.get(row).getType()!=type){
				vg.set(row, vg.get(row).clone(type));
			}
			//vg.get(row).setType(type);
			vg.get(row).setVibType(vibType);
			vg.get(row).setStrength(strength/100f);
			vg.get(row).setMinStrength(minStrength/100f);
			vg.get(row).setTime(time);
			vg.get(row).setOnTime(onTime);
			vg.get(row).setInterval(interval);
			vg.get(row).setStartDelay(startDelay);
			vg.get(row).setAmount(amount);
		}
	
		//updated fields with updated vibration
		updateTable();
	}
	
	private void updateTable(){
		nonUser=true;
		System.out.println("updateFieldInput");
		
		//set stages
		cboxStage.removeAllItems();
		for (int i = 0; i < vs.getStageSize(); i++) {
			cboxStage.addItem(i);
		}
		aa --  vg turns null bug!
		cboxStage.setSelectedIndex(vg.getStage());
		
		
		//set positions
		cboxPos.removeAllItems();
		for (int i = 0; i < vs.getPosSize(); i++) {
			cboxPos.addItem(i);
		}
		
		cboxPos.setSelectedIndex(vg.getPos());
		
		
		
		
		
		
		
		//remove old one
		while(model.getRowCount()>0){
			model.removeRow(model.getRowCount()-1);
		}
		for (int i = 0; i < vg.size(); i++) {
			String vibType = Vibration.VIBTYPES[vg.get(i).getVibType()];
			String type = Vibration.TYPES[vg.get(i).getType()];
			float strength = vg.get(i).getStrength();
			model.addRow(new Object[]{vibType, type, strength});
		}
		
		required.reset();
		usable.reset();
		
		for (int row = 0; row < model.getRowCount(); row++) {
			
			//set all colors
			
			required.set(row, 0, false);
			required.set(row, 1, false);
			required.set(row, 2, 						vg.get(row).requiresStrength());
			required.set(row, 3, 						vg.get(row).requiresMinStrength());
			required.set(row, 4, 						vg.get(row).requiresTime());
			required.set(row, 5, 						vg.get(row).requiresOnTime());
			required.set(row, 6, 						vg.get(row).requiresInterval());
			required.set(row, 7, 						vg.get(row).requiresAmount());
			required.set(row, 8, 						vg.get(row).requiresStartDelay());
			
			
			//set all inputs
			
			usable.set(row, 0, true);
			usable.set(row, 1, true);
			usable.set(row, 2, 							vg.get(row).usableStrength());
			usable.set(row, 3, 							vg.get(row).usableMinStrength());
			usable.set(row, 4, 							vg.get(row).usableTime());
			usable.set(row, 5, 							vg.get(row).usableOnTime());
			usable.set(row, 6, 							vg.get(row).usableInterval());
			usable.set(row, 7, 							vg.get(row).usableStartDelay());
			usable.set(row, 8, 							vg.get(row).usableAmount());
			
			
			//update values
			
			model.setValueAt(Vibration.VIBTYPES[(vg.get(row).getVibType())],							row, 0);
			model.setValueAt(Vibration.TYPES[(vg.get(row).getType())],									row, 1);
			model.setValueAt((vg.get(row).getStrength()==0?"":vg.get(row).getStrength()*100f+""), 		row, 2);
			model.setValueAt((vg.get(row).getMinStrength()==0?"":vg.get(row).getMinStrength()*100f+""), row, 3);
			model.setValueAt((vg.get(row).getTime()==0?"":vg.get(row).getTime()+""), 					row, 4);
			model.setValueAt((vg.get(row).getOnTime()==0?"":vg.get(row).getOnTime()+""), 				row, 5);
			model.setValueAt((vg.get(row).getInterval()==0?"":vg.get(row).getInterval()+""), 			row, 6);
			model.setValueAt((vg.get(row).getStartDelay()==0?"":vg.get(row).getStartDelay()+""), 		row, 7);
			model.setValueAt((vg.get(row).getAmount()==0?"":vg.get(row).getAmount()+""), 				row, 8);
		}
		nonUser=false;
	}
	
	celList usable = new celList();
	celList required = new celList();

	private class celList{
		
		private List<boolean[]> list;
		celList(){
			list = new ArrayList<boolean[]>();
		}
		void set(int row, int col,boolean b){
			//extend list if to small
			while(row > list.size()-1){
				boolean[] newB = new boolean[table.getModel().getColumnCount()];
				list.add(newB);
			}
			list.get(row)[col] = b;
		}
		boolean get(int row, int col){
			
			if(row>list.size()||list.size()==0)return false;
			return list.get(row)[col];
		}
		void reset(){
			list = new ArrayList<boolean[]>();
		}
	}
	
}


