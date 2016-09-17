package j.pivate.main.skyrim.vibnew;

import java.util.ArrayList;
import java.util.List;

public class VibrationGroup {
private List<Vibration> list = new ArrayList<Vibration>();
	
	private int pos, stage;
	

	public VibrationGroup(int stage, int pos){
		this.pos = pos;
		this.stage  = stage;
	}
	
	public void add(Vibration v){
		list.add(v);
	}
	
	public Vibration get(int i){
		return list.get(i);
	}

	public void set(int index, Vibration vib){
		list.set(index, vib);
	}
	
	public int size(){
		return list.size();
	}
	
	public int getPos(){
		return pos;
	}
	
	public int getStage(){
		return stage;
	}

	
	public void remove(int i) {
		list.remove(i);
		
	}


	public void swap(int from, int to) {
		int size = list.size();
	 	
		if(from<0||from>size)return;
		if(to<0||to>size)return;
		
		Vibration VFrom = list.get(from);
		Vibration VTo = list.get(to);
		
		list.set(from, VTo);
		list.set(to, VFrom);
	}
}
