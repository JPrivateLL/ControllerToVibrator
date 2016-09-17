package j.pivate.main.vibrator;

import ch.aplu.xboxcontroller.XboxController;

public class VibratorXbox extends Vibrator {
	public static boolean[] ConnectedControllers() {
		final boolean[] re = new boolean[4];

		for (int i = 0; i < re.length; i++) {
			if (new XboxController(i + 1).isConnected()) {
				re[i] = true;
			}
		}
		return re;
	}

	int number;

	private final XboxController xc;

	public VibratorXbox(int number, boolean[] types) {
		super("Xbox Controller " + number, types);
		this.xc = new XboxController(number);
		this.number = number;
	}

	public XboxController getXC() {
		return xc;
	}

	@Override
	public boolean isConnected() {
		return xc.isConnected();
	}

	@Override
	public void release() {
		if (number > 1)
			number -= 1;
	}

	@Override
	public void rumbleSub(final float strength) {
		final int minimumVibration = 8000;
		int leftRumble, rightRumble;
		float i = strength;

		if (i > 1f)
			i = 1f;
		if (i <= 0) {
			leftRumble = 0;
			rightRumble = 0;
		} else {
			leftRumble = (int) (80000 * i * i) - minimumVibration;
			rightRumble = (int) ((65535 - minimumVibration) * i)
					+ minimumVibration;
		}
		if (leftRumble < minimumVibration) {
			leftRumble = 0;
		}
		if (rightRumble < minimumVibration) {
			rightRumble = 0;
		}
		if (leftRumble > 65535) {
			leftRumble = 65535;
		}
		if (rightRumble > 65535) {
			rightRumble = 65535;
		}
		xc.vibrate(leftRumble, rightRumble);
	}
}
