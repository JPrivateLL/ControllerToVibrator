package j.pivate.main.skyrim.vibnew.types;

public class VibrationInterval extends Vibration {

	public VibrationInterval(String name, int stage, int pos, int vibType,
			float strength, float minStrength, float interval, float time,
			float onTime, float startDelay, float amount) {
		super(name, stage, pos, vibType, 1, strength, minStrength, interval,
				time, onTime, startDelay, amount);

	}

	@Override
	protected float getRumbleStrengthAbstract() {
		float i = getTimer();
		while (i > interval) {
			i -= interval;
		}
		if (i < onTime) {
			return strength;
		} else {
			return minStrength;
		}
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
		return usableTime();
	}

	@Override
	public boolean requiresIntervalAbstract() {
		return usableInterval();
	}

	@Override
	public boolean requiresAmountAbstract() {
		return usableAmount();
	}

	@Override
	public boolean requiresOnTimeAbstract() {
		return true;
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
		if (amount == -1)
			return false;
		if (interval == 0 || amount == 0)
			return true;
		return false;
	}

	@Override
	public boolean usableIntervalAbstract() {
		if (time == 0 || amount == 0)
			return true;
		return false;
	}

	@Override
	public boolean usableAmountAbstract() {
		if (time == -1)
			return false;
		if (interval == 0 || time == 0)
			return true;
		return false;
	}

	@Override
	public boolean usableOnTimeAbstract() {
		return true;
	}

	@Override
	public boolean usableStartDelayAbstract() {
		return true;
	}

}
