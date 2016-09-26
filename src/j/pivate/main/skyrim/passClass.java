package j.pivate.main.skyrim;

import j.pivate.main.skyrim.vibnew.VibratorList;
import j.pivate.main.skyrim.GUI.GUISkyrim;
import j.pivate.main.skyrim.GUI.GUISkyrimAnimator;
import j.pivate.main.skyrim.vibnew.RunningVibrations;

public class passClass {

	public passClass() {
		mainThread = null;
		runningVibrations = null;
		vibratorList = null;
		guiSkyrim = null;
		guiAnimation = null;
	}

	public static SexlabMainThread mainThread;
	public static RunningVibrations runningVibrations;
	public static VibratorList vibratorList;
	public static GUISkyrim guiSkyrim;
	public static GUISkyrimAnimator guiAnimation;

}
