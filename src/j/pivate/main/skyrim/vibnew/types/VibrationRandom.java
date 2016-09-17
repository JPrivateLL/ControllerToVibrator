package j.pivate.main.skyrim.vibnew.types;

import j.pivate.main.skyrim.vibnew.Vibration;

public class VibrationRandom extends Vibration {

	public VibrationRandom(int vibType, float strength, float minStrength, float interval, float time, float onTime, float startDelay, float amount) {
		super(vibType,5,  strength, minStrength, interval, time, onTime, startDelay, amount);
		
	}

	@Override
	protected float getRumbleStrengthOverride() {
		float x = getTimer()*0.3f;
		double y = Math.sin(x)*Math.sin(x*3)*Math.sin(x*7)*Math.sin(x*23)*2;
		return (float)y;
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
		return true;
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
