package j.pivate.main.skyrim.vibnew.types;

import j.pivate.main.skyrim.vibnew.Vibration;

public class VibrationConstant extends Vibration {

	public VibrationConstant(int vibType, float strength, float minStrength, float interval, float time, float onTime, float startDelay, float amount) {
		super(vibType, 0,  strength, minStrength, interval, time, onTime, startDelay, amount);
		
	}
	public VibrationConstant() {
		super(0,0,  0, 0, 0, 0, 0, 0, 0);
	}


	@Override
	protected float getRumbleStrengthOverride() {
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
	public boolean usableStrength() {
		return true;
	}
	@Override
	public boolean usableMinStrength() {
		return false;
	}
	@Override
	public boolean usableTime() {
		return true;
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
		return true;
	}





}
