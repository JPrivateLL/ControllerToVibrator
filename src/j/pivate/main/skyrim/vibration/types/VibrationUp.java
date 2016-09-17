package j.pivate.main.skyrim.vibration.types;

public class VibrationUp extends Vibration {

	
	
	
	protected VibrationUp(String name, boolean[] vibType, int pos,int stage, int number, float strength,float minStrength, float interval, float time, float onTime,float startDelay, float amount, boolean plugRequired) {
		super(name, vibType, pos, stage, number, "Up", strength, minStrength, interval,	time, onTime, startDelay, amount, plugRequired);
		
	}

	@Override
	protected float getRumbleStrengthOverride() {
		float i = getTimer();
		while (i > interval) {
			i -= interval;
		}
		i = i / interval;// 1-0
		i = (strength - minStrength) * i + minStrength;

		return i;
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
	@Override
	public boolean usablePlugRequired() {
		return true;
	}
}
