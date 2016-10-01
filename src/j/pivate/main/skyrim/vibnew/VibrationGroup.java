package j.pivate.main.skyrim.vibnew;

import java.util.ArrayList;
import java.util.List;

public class VibrationGroup {
	private List<Vibration> list = new ArrayList<Vibration>();

	private String name1;
	private String name2;
	private String name3;
	private String name4;
	private String[] tags;
	
	public VibrationGroup(String name1, String name2, String name3, String name4) {
		this.name1 = name1;
		this.name2 = name2;
		this.name3 = name3;
		this.name4 = name4;
	}


	public VibrationGroup clone(){
		//clone 
		VibrationGroup vg = new VibrationGroup(this.getName1(),this.getName2(),this.getName3(),this.getName4());
		vg.setTags(this.tags);
		return vg;
	}
	
	public void add(Vibration v) {
		list.add(v);
	}

	public Vibration get(int i) {
		return list.get(i);
	}

	public void set(int index, Vibration vib) {
		list.set(index, vib);
	}

	public int size() {
		return list.size();
	}

	public void remove(int i) {
		list.remove(i);

	}

	public void swap(int from, int to) {
		int size = list.size();

		if (from < 0 || from > size)
			return;
		if (to < 0 || to > size)
			return;

		Vibration VFrom = list.get(from);
		Vibration VTo = list.get(to);

		list.set(from, VTo);
		list.set(to, VFrom);
	}

	public String getName1() {
		return name1;
	}

	public String getName2() {
		return name2;
	}

	public String getName3() {
		return name3;
	}
	
	public String getName4() {
		return name4;
	}

	public String[] getTags(){
		return tags;
	}

	public void setTags(String[] tags){
		this.tags=tags;
	}

	public List<Vibration> getList() {
		return list;
	}
}
