package j.pivate.main.skyrim.vibration.types;

public class VibrationNone extends Vibration {

	protected VibrationNone(String name, boolean[] vibType, int pos, int stage,
			int number, float strength, float minStrength,
			float interval, float time, float onTime, float startDelay,
			float amount, boolean plugRequired) {
		super(name, vibType, pos, stage, number, "None", strength, minStrength, interval,
				time, onTime, startDelay, amount, plugRequired);
	}


	@Override
	protected float getRumbleStrengthOverride() {
		
		return 0;
	}

	@Override
	public boolean requiresTypeAbstract() {
		return true;
	}

	@Override
	public boolean requiresVibTypeAbstract() {
		
		return false;
	}

	@Override
	public boolean requiresStrengthAbstract() {
		
		return false;
	}

	@Override
	public boolean requiresMinStrengthAbstract() {
		
		return false;
	}

	@Override
	public boolean requiresTimeAbstract() {
		
		return false;
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
	public boolean requiresPlugRequiredAbstract() {
		
		return false;
	}

	@Override
	public boolean usableType() {
		
		return true;
	}

	@Override
	public boolean usableVibType() {
		
		return false;
	}

	@Override
	public boolean usableStrength() {
		
		return false;
	}

	@Override
	public boolean usableMinStrength() {
		
		return false;
	}

	@Override
	public boolean usableTime() {
		
		return false;
	}

	@Override
	public boolean usableInterval() {
		
		return false;
	}

	@Override
	public boolean usableAmount() {
		
		return false;
	}

	@Override
	public boolean usableOnTime() {
		
		return false;
	}

	@Override
	public boolean usableStartDelay() {
		
		return false;
	}

	@Override
	public boolean usablePlugRequired() {
		
		return false;
	}

}
