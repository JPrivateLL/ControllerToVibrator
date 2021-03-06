package j.pivate.main.skyrim.vibnew;

import java.util.ArrayList;
import java.util.List;


public class VibrationList {
	private final List<Vibration> vibrationList = new ArrayList<Vibration>();
	
	
	public void update(float delta){
		//reverse loop, to make sure removed items don't crash the loop.
		for (int i = vibrationList.size() - 1; i >= 0; i--) {
			Vibration vibration = vibrationList.get(i);
			vibration.update(delta);
			if(vibration.removeMe()){
				vibrationList.remove(vibration);
			}
		}
		
	}
	
	public float[] getStrength(){
		float[] strengthMax = new float[Vibration.VIBTYPES.length];
		for (Vibration vibration : vibrationList) {
			int type = vibration.getVibType();
			float strength = vibration.getRumbleStrength();
			if(strengthMax[type]<strength){
				strengthMax[type] = strength;
			}
		}
		
		return strengthMax;
		
	}	
	public void removeAll(){
		vibrationList.clear();
	}

	public int size() {
		return vibrationList.size();
	}

	public void add(Vibration vib){
		vibrationList.add(vib);
	}


	public void remove(String name) {
		//reverse loop, to make sure removed items don't crash the loop.
		for (int i = vibrationList.size() - 1; i >= 0; i--) {
			Vibration vibration = vibrationList.get(i);
			if(vibration.getName().equals(name)){
				vibrationList.remove(vibration);
			}
		}
	}

}
