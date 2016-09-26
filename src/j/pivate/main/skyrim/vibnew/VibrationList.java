package j.pivate.main.skyrim.vibnew;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VibrationList {

	public static void main(String[] arg){
		load();
		save();
	}
	
	private static BufferedReader reader;
	private static BufferedWriter writer;

	private static File file = new File("CustomVibrations.txt");

	private static List<VibrationGroup> list = new ArrayList<VibrationGroup>();
	
	public static VibrationGroup get(int nr) {
		if (nr > list.size() - 1)return null;
		lastGroup = list.get(nr);
		return list.get(nr);
	}


	public static VibrationGroup get(String name1){
		for (VibrationGroup v : list) {
			if(v.getName1().equals(name1)){
				return v;
			}
		}
		return null;
	}
	public static VibrationGroup get(String name1, String name2){
		for (VibrationGroup v : list) {
			if(v.getName1().equals(name1) && v.getName2().equals(name2)){
				return v;
			}
		}
		return null;
	}
	public static VibrationGroup get(String name1, String name2, String name3){
		for (VibrationGroup v : list) {
			if(v.getName1().equals(name1) && v.getName2().equals(name2) && v.getName3().equals(name3)){
				return v;
			}
		}
		return null;
	}
	
	public static VibrationGroup get(String name1, String name2, String name3, String name4) {
		for (VibrationGroup v : list) {
			if (v.getName1().equals(name1) && v.getName2().equals(name2) && v.getName3().equals(name3) && v.getName4().equals(name4)) {
				lastGroup = v;
				return v;
			}
		}
		
		// create if non existing
		VibrationGroup vg = new VibrationGroup(name1, name2, name3, name4);
		lastGroup = vg;
		list.add(vg);
		return vg;

		

	}

	private static VibrationGroup lastGroup;

	public static VibrationGroup getLast() {
		return lastGroup;
	}

	public static String[] getName1List() {
		List<String> names = new ArrayList<String>();
		for (VibrationGroup vg : list) {
			if(!names.contains(vg.getName1())){
				names.add(vg.getName1());
			}
		}
		return names.toArray(new String[0]);
	}
	
	public static String[] getName2List(String name1) {
		List<String> names = new ArrayList<String>();
		for (VibrationGroup vg : list) {
			if(vg.getName1().equals(name1)){
				if(!names.contains(vg.getName2())){
					names.add(vg.getName2());
				}
			}
		}
		return names.toArray(new String[0]);
	}
	
	public static String[] getName3List(String name1,String name2) {
		List<String> names = new ArrayList<String>();
		for (VibrationGroup vg : list) {
			if(vg.getName1().equals(name1)){
				if(vg.getName2().equals(name2)){
					if(!names.contains(vg.getName3())){
						names.add(vg.getName3());
					}
				}
			}
		}
		return names.toArray(new String[0]);
	}

	public static String[] getName4List(String name1,String name2, String name3) {
		List<String> names = new ArrayList<String>();
		for (VibrationGroup vg : list) {
			if(vg.getName1().equals(name1)){
				if(vg.getName2().equals(name2)){
					if(vg.getName3().equals(name3)){
						if(!names.contains(vg.getName4())){
							names.add(vg.getName4());
						}
					}
				}
			}
		}
		return names.toArray(new String[0]);
	}
	
	public static String getname1(int nr) {
		return get(nr).getName1();
	}

	public static String getname2(int nr) {
		return get(nr).getName2();
	}
	
	public static String getname3(int nr) {
		return get(nr).getName3();
	}

	public static String getname4(int nr) {
		return get(nr).getName4();
	}
	
	public static void load() {
		try {

			if (!file.exists()) {

				file.createNewFile();

			}
			try {
				reader = new BufferedReader(new FileReader(file));
			} catch (final FileNotFoundException e2) {
				e2.printStackTrace();
			}

			String name1="";
			String name2="";
			String name3="";
			String name4="";
			VibrationGroup vg = null;
			String[] tags={};
			String line = null;
			while (true) {
				line = reader.readLine();
				if (line == null)
					break;

				if (line.startsWith("!")) {
					//name1 (sexlab)
					line = line.substring(1, line.length());
					
					//out
					name1 = line;
				} else if (line.startsWith("@")) {
					//name2 (doggy)
					line = line.substring(1, line.length());
					line = line.replace("[", "");
					line = line.replace("]", "");
					String[] lines = line.split(", ");
					
					//out
					name2 = lines[0];
					tags = Arrays.copyOfRange(lines, 1, lines.length);
				} else if (line.startsWith("#")) {
					//name3 (stage)
					line = line.substring(1, line.length());

					//out
					name3 = line;
					
				} else if (line.startsWith("$")) {
					//name4 (pos)
					line = line.substring(1, line.length());
					
					//out
					name4 = line;
					vg = new VibrationGroup(name1, name2, name3, name4);
					vg.setTags(tags);
					list.add(vg);
				}else if(line.startsWith("%")){
					//vibrations
					line = line.substring(1, line.length());
					String[] lines = line.split(", ");
					int vibType = Integer.valueOf(lines[0]);
					int type = Integer.valueOf(lines[1]);
					float strength = Float.valueOf(lines[2]);
					float minStrength = Float.valueOf(lines[3]);
					float interval = Float.valueOf(lines[4]);
					float time = Float.valueOf(lines[5]);
					float onTime = Float.valueOf(lines[6]);
					float startDelay = Float.valueOf(lines[7]);
					float amount = Float.valueOf(lines[8]);
					
					vg.add(Vibration.create(vibType, type, strength,
							minStrength, interval, time, onTime, startDelay,
							amount));
				}
			}

			reader.close();

		} catch (final IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void save() {

		Collections.sort(list, new Comparator<VibrationGroup>() {
			@Override
			public int compare(VibrationGroup o1, VibrationGroup o2) {
				return o1.getName1().compareTo(o2.getName1());
			}
		});
		Collections.sort(list, new Comparator<VibrationGroup>() {
			@Override
			public int compare(VibrationGroup o1, VibrationGroup o2) {
				return o1.getName2().compareTo(o2.getName2());
			}
		});
		Collections.sort(list, new Comparator<VibrationGroup>() {
			@Override
			public int compare(VibrationGroup o1, VibrationGroup o2) {
				return o1.getName3().compareTo(o2.getName3());
			}
		});
		Collections.sort(list, new Comparator<VibrationGroup>() {
			@Override
			public int compare(VibrationGroup o1, VibrationGroup o2) {
				return o1.getName4().compareTo(o2.getName4());
			}
		});


		try {
			writer = new BufferedWriter(new FileWriter(file));

			// write commands
			writer.write("Do not change this manually!");
			writer.newLine();
			writer.write("Automaticly created file");
			writer.newLine();
			writer.newLine();
			
			// write all vibrations if needed
			if(list.size()!=0){
				String name1=null;
				String name2=null;
				String name3=null;
				String name4=null;
				for (VibrationGroup vg : list) {
					if(!vg.getName1().equals(name1)){
						name1 = vg.getName1();
						writer.write("!"+name1);
						writer.newLine();
					}
					if(!vg.getName2().equals(name2)){
						name2 = vg.getName2();
						writer.write("@"+name2+(vg.getTags()==null?"":(", "+Arrays.toString(vg.getTags()))));
						writer.newLine();
					}
					if(!vg.getName3().equals(name3)){
						name3 = vg.getName3();
						writer.write("#"+name3);
						writer.newLine();
					}
					if(!vg.getName4().equals(name4)){
						name4 = vg.getName4();
						writer.write("$"+name4);
						writer.newLine();
					}
					for (Vibration v : vg.getList()) {
						writer.write("%" + 
								v.getVibType() + ", "+ 
								v.getType() + ", " + 
								v.getStrength()	+ ", " + 
								v.getMinStrength() + ", "+ 
								v.getInterval() + ", " + 
								v.getTime()	+ ", " + 
								v.getOnTime() + ", " + 
								v.getStartDelay() + ", "+ 
								v.getAmount());
						writer.newLine();
					}
				}
				writer.newLine();
			}

			// close writer
			writer.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

}
