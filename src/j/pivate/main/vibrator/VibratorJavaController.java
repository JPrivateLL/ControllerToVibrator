package j.pivate.main.vibrator;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class VibratorJavaController extends Vibrator {

	Controller controller;

	public VibratorJavaController(final boolean[] types, final String name) {
		super(name, types);
		final Controller[] controllers = ControllerEnvironment
				.getDefaultEnvironment().getControllers();
		for (int i = 0; i < controllers.length; i++) {
			if (controllers[i].getRumblers().length > 0) {
				controller = controllers[i];
			}
		}
	}

	public Controller getController() {
		return controller;
	}

	@Override
	public boolean isConnected() {
		if (controller != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void release() {

	}

	@Override
	public void rumbleSub(float strength) {
		if (strength <= 0) {
			strength = 0;
		}
		if (strength > 1) {
			strength = 1;
		}
		for (int i = 0; i < controller.getRumblers().length; i++) {
			controller.getRumblers()[i].rumble(strength);
		}

	}

}
