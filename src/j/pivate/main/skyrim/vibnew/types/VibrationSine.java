package j.pivate.main.skyrim.vibnew.types;

public class VibrationSine extends Vibration {

	public VibrationSine(String name, int stage, int pos, int vibType,
			float strength, float minStrength, float interval, float time,
			float onTime, float startDelay, float amount) {
		super(name, stage, pos, vibType, 2, strength, minStrength, interval,
				time, onTime, startDelay, amount);

	}

	@Override
	protected float getRumbleStrengthAbstract() {
		float i = getTimer();
		while (i > interval) {
			i -= interval;
		}
		i = i / interval;
		i = (float) Math.sin(i * Math.PI);
		i = (strength - minStrength) * i + minStrength;
		return i;
	}

	@Override
	public boolean requiresStrengthAbstract() {
		return true;
	}

	@Override
	public boolean requiresMinStrengthAbstract() {
		return false;
	}

	@Override
	public boolean requiresTimeAbstract() {
		if (interval == 0 && amount == 0)
			return true;
		return false;
	}

	@Override
	public boolean requiresIntervalAbstract() {
		if (time == 0 && amount == 0)
			return true;
		return false;
	}

	@Override
	public boolean requiresAmountAbstract() {
		if (interval == 0 && time == 0)
			return true;
		return false;
	}

	@Override
	public boolean requiresOnTimeAbstract() {
		return false;
	}

	@Override
	public boolean requiresStartDelayAbstract() {
		return false;
	}

	@Override
	public boolean usableStrengthAbstract() {
		return true;
	}

	@Override
	public boolean usableMinStrengthAbstract() {
		return true;
	}

	@Override
	public boolean usableTimeAbstract() {
		return true;
	}

	@Override
	public boolean usableIntervalAbstract() {
		return true;
	}

	@Override
	public boolean usableAmountAbstract() {
		return true;
	}

	@Override
	public boolean usableOnTimeAbstract() {
		return false;
	}

	@Override
	public boolean usableStartDelayAbstract() {
		return true;
	}
}
