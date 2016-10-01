package j.pivate.main.skyrim.vibnew;

import java.util.ArrayList;
import java.util.List;

public class RunningVibrations {
	private final List<VibrationGroup> list = new ArrayList<VibrationGroup>();

	public void update(float delta) {	
		// reverse loop, to make sure removed items don't crash the loop.
		for (int i = list.size() - 1; i >= 0; i--) {
			VibrationGroup vg = list.get(i);
			List<Vibration> list2 = vg.getList();
			for (int j = list2.size() - 1; j >= 0; j--) {
				Vibration v = list2.get(j);
				v.update(delta);
				if (v.removeMe())list2.remove(v);
			}
			if(list2.size()==0)list.remove(i);
		}
	}
	
	public float[] getStrength() {
		float[] strengthMax = new float[Vibration.VIBTYPES.length];
		for (VibrationGroup vg : list) {
			for(Vibration v : vg.getList()){
				int type = v.getVibType();
				float strength = v.getRumbleStrength();
				if (strengthMax[type] < strength) {
					strengthMax[type] = strength;
				}
			}
		}

		return strengthMax;

	}

	public void removeAll() {
		list.clear();
	}

	public int size() {
		return list.size();
	}

	public void add(VibrationGroup vib) {
		list.add(vib);
	}

	public void remove(String name1, String name2, String name3, String name4) {
		// reverse loop, to make sure removed items don't crash the loop.
		for (int i = list.size() - 1; i >= 0; i--) {
			VibrationGroup vg = list.get(i);
			if (vg.getName1().equals(name1) && vg.getName2().equals(name2) && vg.getName3().equals(name3) && vg.getName4().equals(name4)) {
				list.remove(vg);
			}
		}
	}
}
