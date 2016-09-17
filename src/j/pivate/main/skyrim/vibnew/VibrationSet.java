package j.pivate.main.skyrim.vibnew;

import java.util.ArrayList;
import java.util.List;


public class VibrationSet {
	private List<VibrationGroup> list  = new ArrayList<VibrationGroup>();
	
	private String name;
	private String[] tags;
	
	public VibrationSet(String name, String[] tags){
		this.name = name;
		this.tags = tags;
	}
	
	
	public String getName(){
		return name;
	}
	
	public String[] getTags(){
		return tags;
	}

	
	//returns biggest pos 
	public int getPosSize(){
		int i =0;
		for (VibrationGroup vg : list) {
			if(vg.getPos()>i){
				i = vg.getPos();
			}
		}
		return i+1;
	}
	
	//returns biggest stage
	public int getStageSize(){
		int i =0;
		for (VibrationGroup vg : list) {
			if(vg.getStage()>i){
				i = vg.getStage();
			}
		}
		return i+1;
	}

	public VibrationGroup getGroup(int stage, int pos) {
		for ( VibrationGroup  vg : list) {
			if(vg.getPos()==pos&&vg.getStage()==stage){
				lastGroup = vg;
				return vg;
			}
		}
		//create if null
		VibrationGroup  vg = new VibrationGroup(stage,pos);
		lastGroup = vg;
		list.add(vg);
		return vg;
		
	}

	private VibrationGroup lastGroup;
	public VibrationGroup getLastGroup(){
		return lastGroup;
	}


	public void addExisting(VibrationGroup vg) {
		list.add(vg);
	}
}
