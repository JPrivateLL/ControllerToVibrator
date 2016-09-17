package j.pivate.main.skyrim.vibration.types;

public class VibrationRandom extends Vibration {

	protected VibrationRandom(String name, boolean[] vibType, int pos,int stage, int number, float strength,float minStrength, float interval, float time, float onTime,float startDelay, float amount, boolean plugRequired) {
		super(name, vibType, pos, stage, number, "Random", strength, minStrength, interval,	time, onTime, startDelay, amount, plugRequired);
		
	}

	@Override
	protected float getRumbleStrengthOverride() {
		float x = getTimer()*0.3f;
		double y = Math.sin(x)*Math.sin(x*3)*Math.sin(x*7)*Math.sin(x*23)*2;
		return (float)y;
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
	@Override
	public boolean usablePlugRequired() {
		return true;
	}
}
