package j.pivate.main.skyrim.vibnew.types;

import j.pivate.main.skyrim.vibnew.Vibration;

public class VibrationDown extends Vibration {

	
	
	
	public VibrationDown(int vibType, float strength, float minStrength, float interval, float time, float onTime, float startDelay, float amount) {
		super(vibType,4,  strength, minStrength, interval, time, onTime, startDelay, amount);
		
	}

	@Override
	protected float getRumbleStrengthOverride() {
		float i = getTimer();
		while (i > interval) {
			i -= interval;
		}
		i = i / interval;// 1-0
		i = (minStrength - strength) * i + strength;

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
		if(interval==0&&amount==0)return true;
		return false;
	}
	@Override
	public boolean requiresIntervalAbstract() {
		if(time==0&&amount==0)return true;
		return false;
	}
	@Override
	public boolean requiresAmountAbstract() {
		if(interval==0&&time==0)return true;
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
		return true;
	}
	@Override
	public boolean usableAmount() {
		return true;
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
