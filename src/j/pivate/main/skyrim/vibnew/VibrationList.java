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

import j.pivate.main.skyrim.vibnew.types.Vibration;

public class VibrationList {

	private static BufferedReader reader;
	private static BufferedWriter writer;

	private static File file = new File("CustomVibrations.txt");

	private static List<VibrationSet> list = new ArrayList<VibrationSet>();

	public static VibrationSet getSet(int nr) {
		if (nr > list.size() - 1)
			return null;
		lastSet = list.get(nr);
		return list.get(nr);
	}

	public static VibrationSet getSet(String name, String[] tags) {
		for (VibrationSet vs : list) {
			if (vs.getName().equals(name)) {
				lastSet = vs;
				return vs;
			}
		}

		VibrationSet vs = new VibrationSet(name, tags);
		lastSet = vs;
		list.add(vs);
		return vs;

		// create if non existing

	}

	private static VibrationSet lastSet;

	public static VibrationSet getLastSet() {
		return lastSet;
	}

	public static String[] getNameList() {
		String[] names = new String[list.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = list.get(i).getName();
		}
		return names;
	}

	public static int getPoss(int nr) {
		return list.get(nr).getPosSize();
	}

	public static int getStages(int nr) {
		return list.get(nr).getStageSize();
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

			String line = null;
			VibrationSet vs = null;
			VibrationGroup vg = null;

			while (true) {
				line = reader.readLine();
				if (line == null)
					break;

				if (line.startsWith("!")) {
					line = line.substring(1, line.length());
					line = line.replace("[", "");
					line = line.replace("]", "");
					String[] lines = line.split(", ");
					String name = lines[0];
					String[] tags = Arrays.copyOfRange(lines, 1, lines.length);
					vs = new VibrationSet(name, tags);
					list.add(vs);
				} else if (line.startsWith("@")) {
					line = line.substring(1, line.length());
					String[] lines = line.split(", ");
					int pos = Integer.valueOf(lines[0]);
					int stage = Integer.valueOf(lines[1]);
					vg = new VibrationGroup(stage, pos);
					vs.addExisting(vg);
				} else if (line.startsWith("#")) {
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
					vg.add(Vibration.create(null, 0, 0, vibType, type, strength,
							minStrength, interval, time, onTime, startDelay,
							amount));
				}

			}

			reader.close();

		} catch (final IOException e1) {
			e1.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void save() {

		Collections.sort(list, new Comparator() {
			@Override
			public int compare(Object softDrinkOne, Object softDrinkTwo) {
				return ((VibrationSet) softDrinkOne).getName()
						.compareTo((((VibrationSet) softDrinkTwo).getName()));
			}
		});

		try {
			writer = new BufferedWriter(new FileWriter(file));

			// write commands
			writer.write(
					"; use vibration editor in the program do not change this manually.");
			writer.newLine();
			writer.write("; ! = name, tags");
			writer.newLine();
			writer.write("; @ = stage, place");
			writer.newLine();
			writer.write(
					"; # = vibration type, strength, minimum strength, interval, time, ON time(for interval), start delay, amount");
			writer.newLine();
			writer.newLine();

			// write all vibrations
			for (VibrationSet vSet : list) {
				writer.write("!" + vSet.getName() + ", "
						+ Arrays.toString(vSet.getTags()));
				writer.newLine();
				for (int pos = 0; pos < vSet.getPosSize(); pos++) {
					for (int stage = 0; stage < vSet.getStageSize(); stage++) {
						VibrationGroup vGroup = vSet.getGroup(stage, pos);
						if (vGroup == null)
							continue;
						writer.write("@" + vGroup.getPos() + ", "
								+ vGroup.getStage());
						writer.newLine();
						for (int i = 0; i < vGroup.size(); i++) {
							Vibration vib = vGroup.get(i);
							writer.write("#" + vib.getVibType() + ", "
									+ vib.getType() + ", " + vib.getStrength()
									+ ", " + vib.getMinStrength() + ", "
									+ vib.getInterval() + ", " + vib.getTime()
									+ ", " + vib.getOnTime() + ", "
									+ vib.getStartDelay() + ", "
									+ vib.getAmount());
							writer.newLine();
						}
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
