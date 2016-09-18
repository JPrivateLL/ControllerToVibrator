package j.pivate.main.skyrim.vibnew.types;

public class VibrationConstant extends Vibration {

	public VibrationConstant(String name, int stage, int pos, int vibType,
			float strength, float minStrength, float interval, float time,
			float onTime, float startDelay, float amount) {
		super(name, stage, pos, vibType, 0, strength, minStrength, interval,
				time, onTime, startDelay, amount);

	}

	@Override
	protected float getRumbleStrengthAbstract() {
		return strength;
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
		return true;
	}

	@Override
	public boolean requiresIntervalAbstract() {
		return false;
	}

	@Override
	public boolean requiresAmountAbstract() {
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
		return false;
	}

	@Override
	public boolean usableTimeAbstract() {
		return true;
	}

	@Override
	public boolean usableIntervalAbstract() {
		return false;
	}

	@Override
	public boolean usableAmountAbstract() {
		return false;
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
