package j.pivate.main.vibrator;

public abstract class Vibrator {
	protected final String name;
	private boolean[] types;
	private boolean muted = false;

	public Vibrator(String name, boolean[] types) throws ArithmeticException {
		this.name = name;
		this.types = types;
	}

	public Vibrator(String name) throws ArithmeticException {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean[] getType() {
		return types;

	}

	public abstract boolean isConnected();

	public void mute() {
		muted = true;
	}

	public abstract void release();

	public void rumble(float strenght) {
		if (muted) {
			rumbleSub(0);
		} else {
			if (strenght < 0f) {
				strenght = 0f;
			} else if (strenght > 1f) {
				strenght = 1f;
			}
			rumbleSub(strenght);
		}
	}

	// Strength float 0f-1.0f
	protected abstract void rumbleSub(float strenght);

	public void unMute() {
		muted = false;
	}
}
