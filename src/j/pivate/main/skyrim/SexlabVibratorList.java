package j.pivate.main.skyrim;

import java.util.List;

import j.pivate.main.vibrator.Vibrator;

public class SexlabVibratorList {
	private List<Vibrator> vibrators;

	
	public SexlabVibratorList(List<Vibrator> vibrators){
		this.vibrators = vibrators;
	}
	
	public void rumble(float[] strength){
		for (Vibrator vibrator : vibrators) {
			float maxStrength = 0;
			for (int i = 0; i < strength.length; i++) {
				if(vibrator.getType()[i]){
					if(strength[i]>maxStrength){
						maxStrength = strength[i];
					}
				}
			}
			vibrator.rumble(maxStrength);
		}
	}
	
	public void stopAll(){
		for (Vibrator vibrator : vibrators) {
			vibrator.rumble(0);
		}
	}
	
	public void releaseAll(){
		for (Vibrator vibrator : vibrators) {
			vibrator.release();
		}
	}

	public String[] getName() {
		String[] s= new String[4];
		for (int i = 0; i < s.length; i++) {
			if(i<vibrators.size()){
				s[i]=vibrators.get(i).getName();
			}else{
				s[i]="none";
			}
			
		}
		return s;
	}

	public String getName(int i){
		return getName()[i];
	}
	
	public int size() {
		return vibrators.size();
	}

	public boolean isConnected(int i) {
		return vibrators.get(i).isConnected();
	}
}
