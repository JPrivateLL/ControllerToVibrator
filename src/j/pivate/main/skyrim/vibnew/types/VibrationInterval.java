package j.pivate.main.skyrim.vibnew.types;

import j.pivate.main.skyrim.vibnew.Vibration;

public class VibrationInterval extends Vibration {

	public VibrationInterval(int vibType, float strength, float minStrength, float interval, float time, float onTime, float startDelay, float amount) {
		super(vibType,1,  strength, minStrength, interval, time, onTime, startDelay, amount);
		
	}

	@Override
	protected float getRumbleStrengthOverride() {
		float i = getTimer();
		while (i > interval) {
			i -= interval;
		}
		if (i < onTime) {
			return strength;
		}else{
			return minStrength;
		}
	}

	@Override
	public void setTime(float time){
		//TODO check if amount or time is -1
		ad
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
	public boolean usableStrength() {
		return true;
	}
	@Override
	public boolean usableMinStrength() {
		return true;
	}
	@Override
	public boolean usableTime() {
		if(interval==0||amount==0)return true;
		return false;
	}
	@Override
	public boolean usableInterval() {
		if(time==0||amount==0)return true;
		return false;
	}
	@Override
	public boolean usableAmount() {
		if(interval==0||time==0)return true;
		return false;
	}
	@Override
	public boolean usableOnTime() {
		return true;
	}
	@Override
	public boolean usableStartDelay() {
		return true;
	}
	
}
