package j.pivate.main.skyrim;

import j.pivate.main.skyrim.vibnew.GUISkyrimAnimator;
import j.pivate.main.skyrim.vibnew.VibratorList;
import j.pivate.main.skyrim.vibnew.RunningVibrations;

public class passClass {

	public passClass() {
		mainThread = null;
		vibrationList = null;
		vibratorList = null;
		guiSkyrim = null;
		guiAnimation = null;
	}

	public static SexlabMainThread mainThread;
	public static RunningVibrations vibrationList;
	public static VibratorList vibratorList;
	public static GUISkyrim guiSkyrim;
	public static GUISkyrimAnimator guiAnimation;

}
