package j.pivate.main.skyrim.vibration.types;

public class VibrationInterval extends Vibration {

	protected VibrationInterval(String name, boolean[] vibType, int pos,int stage, int number, float strength,float minStrength, float interval, float time, float onTime,float startDelay, float amount, boolean plugRequired) {
		super(name, vibType, pos, stage, number, "Interval", strength, minStrength, interval,	time, onTime, startDelay, amount, plugRequired);
		
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
	public boolean requiresTypeAbstract() {
		return true;
	}
	@Override
	public boolean requiresVibTypeAbstract() {
		return true;
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
	public boolean requiresPlugRequiredAbstract() {
		return false;
	}

	@Override
	public boolean usableType() {
		return true;
	}
	@Override
	public boolean usableVibType() {
		return true;
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
	@Override
	public boolean usablePlugRequired() {
		return true;
	}
	
}
