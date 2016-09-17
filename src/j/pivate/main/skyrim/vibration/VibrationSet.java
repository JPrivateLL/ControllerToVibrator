package j.pivate.main.skyrim.vibration;

import java.util.ArrayList;
import java.util.List;

import j.pivate.main.skyrim.vibration.types.Vibration;

public class VibrationSet {
	
	List<Vibration> list;
	
	public VibrationSet(){
		list = new ArrayList<Vibration>();
	}
	
	public void add(Vibration sv){
		list.add(sv);
	}
	
	public Vibration get(int i){
		return list.get(i);
	}
	
	public String getName(){
		return list.get(0).getName();
	}
	
	public int posSize(){
		return list.size();
	}
	
	public int stageSize(){
		return list.size();
	}
	
	@Override
	public String toString() {
		return list.get(0).getName()+" pos:"+list.get(0).getPos()+" stage:"+list.get(0).getStage();
	}
}
