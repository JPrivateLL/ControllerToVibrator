package j.pivate.main.theClub;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import j.pivate.main.CheatEngine;
import j.pivate.main.vibrator.Vibrator;

public class theClub extends CheatEngine{

	private final int[] oVaginaDepth1 = {0x2AF8F4,0x17c,0x8,0x14,0x54,0x74};
	private final int[] oAnalDepth1 = {0x2AF8F4,0x17c,0x8,0x14,0x58,0x74};
	private final int[] oMouthDepth1 = {0x2AF8F4,0x17c,0x8,0x14,0x5c,0x74};
	private final int[] oExcitement1 = {0x002AF8F4,0x17c,0xc4,0x4,0xfc,0x120};
	private final int[] oOrgasm1 = {0x002AF8F4,0x17c,0xc4,0x4,0xfc,0x194};
	
	private final int[] oVaginaDepth2 = {0x2AF8F4,0x17c,0x8,0x14,0xa4,0x74};
	private final int[] oAnalDepth2 = {0x2AF8F4,0x17c,0x8,0x14,0xa8,0x74};
	private final int[] oMouthDepth2 = {0x2AF8F4,0x17c,0x8,0x14,0xac,0x74};
	private final int[] oExcitement2 = {0x002AF8F4,0x17c,0xc4,0x8,0xfc,0x120};
	private final int[] oOrgasm2 = {0x002AF8F4,0x17c,0xc4,0x8,0xfc,0x194};
	
	
	private final int[] oVaginaDepth3 = {0x2AF8F4,0x17c,0x8,0x14,0xf4,0x74};
	private final int[] oAnalDepth3 = {0x2AF8F4,0x17c,0x8,0x14,0xf8,0x74};
	private final int[] oMouthDepth3 = {0x2AF8F4,0x17c,0x8,0x14,0xfc,0x74};
	private final int[] oExcitement3 = {0x002AF8F4,0x17c,0xc4,0xc,0xfc,0x120};
	private final int[] oOrgasm3 = {0x002AF8F4,0x17c,0xc4,0xc,0xfc,0x194};
	
	GUITheClub frame;
	
	public theClub(List<Vibrator> v, GUITheClub gui){
		super("The Klub 17","TK17-114.001.exe");
		frame= gui;

		new Thread() {
			public void run() {
				int aVaginaDepth1 = getAddress(oVaginaDepth1);
			 	int aAnalDepth1 = getAddress(oAnalDepth1);
			 	int aMouthDepth1 = getAddress(oMouthDepth1);
			 	int aExcitement1 = getAddress(oExcitement1);
			 	int aOrgasm1 = getAddress(oOrgasm1);

			 	int aVaginaDepth2 = getAddress(oVaginaDepth2);
			 	int aAnalDepth2 = getAddress(oAnalDepth2);
			 	int aMouthDepth2 = getAddress(oMouthDepth2);
			 	int aExcitement2 = getAddress(oExcitement2);
			 	int aOrgasm2 = getAddress(oOrgasm2);
			 	
			 	int aVaginaDepth3 = getAddress(oVaginaDepth3);
			 	int aAnalDepth3 = getAddress(oAnalDepth3);
			 	int aMouthDepth3 = getAddress(oMouthDepth3);
			 	int aExcitement3 = getAddress(oExcitement3);
			 	int aOrgasm3 = getAddress(oOrgasm3);
		        
			 	
		        float dL=0;
		        float dLL=0;
		        
		        List<Float> list = new ArrayList<Float>();
		        list.add(0f);
		        list.add(0f);
		        list.add(0f);
		        
		        int check = 0;
		        float vd2L = 0f;
		        while(true){
		        	//get new depth
		        	float vd1 = (float)readMemory(aVaginaDepth1).getFloat(0);
		        	float ad1 = (float)readMemory(aAnalDepth1).getFloat(0);
		        	float md1 = (float)readMemory(aMouthDepth1).getFloat(0);
		        	float ex1 = (float)readMemory(aExcitement1).getFloat(0);
		        	boolean og1 = (float)readMemory(aOrgasm1).getFloat(0)==-2 ? true:false;
		        	
		        	float vd2 = (float)readMemory(aVaginaDepth2).getFloat(0);
		        	float ad2 = (float)readMemory(aAnalDepth2).getFloat(0);
		        	float md2 = (float)readMemory(aMouthDepth2).getFloat(0);
		        	float ex2 = (float)readMemory(aExcitement2).getFloat(0);
		        	boolean og2 = (float)readMemory(aOrgasm2).getFloat(0)==-2 ? true:false;
		        	
		        	float vd3 = (float)readMemory(aVaginaDepth3).getFloat(0);
		        	float ad3 = (float)readMemory(aAnalDepth3).getFloat(0);
		        	float md3 = (float)readMemory(aMouthDepth3).getFloat(0);
		        	float ex3 = (float)readMemory(aExcitement3).getFloat(0);
		        	boolean og3 = (float)readMemory(aOrgasm3).getFloat(0)==-2 ? true:false;
		        	
		        	frame.pbV1.setValue((int)(vd1*100));
		        	frame.pbA1.setValue((int)(ad1*100));
		        	frame.pbM1.setValue((int)(md1*100));
		        	frame.pbE1.setValue((int)(ex1*100));
		        	
		        	frame.pbV2.setValue((int)(vd2*100));
		        	frame.pbA2.setValue((int)(ad2*100));
		        	frame.pbM2.setValue((int)(md2*100));
		        	frame.pbE2.setValue((int)(ex2*100));
		        	
		        	frame.pbV3.setValue((int)(vd3*100));
		        	frame.pbA3.setValue((int)(ad3*100));
		        	frame.pbM3.setValue((int)(md3*100));
		        	frame.pbE3.setValue((int)(ex3*100));
		        	
		        	//remove some zeros (bug)
		        	if(vd2==0&&check<3){
		        		check++;
		        		vd2 = vd2L;
		        	}else{
		        		check=0;
		        	}
		        	vd2L=vd2;
		        	//calculate delat
		        	float delta = dL-vd2;
		        	dL = vd2;
		        	if(delta<0)delta*=-1;
		        	
		        	list.remove(0);
		        	list.add(delta);
		        	
		        	List<Float> sortedList = new ArrayList<>(list);
		        	Collections.sort(sortedList);
		        	
		        	float strength = sortedList.get(1);
		        	
		        	strength*=500*(vd2+0.2)*ex2;
		        	if(og2){
		        		strength=0.5f+(strength/2);
		        	}

		        	//System.out.println(strength);
		        	frame.pbE3.setValue((int)(strength*100));
		        	
		        	

		        	for (Vibrator vibrator : v) {
		        		vibrator.rumble(strength);
					}
		            try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
			}
		}.start();
		
	 	
    	
	}
	

	

    


}
