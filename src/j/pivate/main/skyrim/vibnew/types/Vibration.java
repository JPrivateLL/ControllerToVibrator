package j.pivate.main.skyrim.vibnew.types;

public abstract class Vibration {

	public static final String[] TYPES = { "Constant", "Interval", "Sine", "Up",
			"Down", "Random" };
	public static final String[] VIBTYPES = { "Viginal", "Viginal (shock)",
			"Anal", "Anal (shock)", "Breasts", "Breasts (shock)", "Oral",
			"Oral (shock)", "Damage", "Damage (shock)", "Interaction",
			"Interaction (shock)" };

	protected String name;
	protected int stage;
	protected int pos;
	protected int vibType;
	protected int type;
	protected float strength;
	protected float minStrength;
	protected float interval;
	protected float time;
	protected float onTime;
	protected float startDelay;
	protected float amount;

	public Vibration(String name, int stage, int pos, int vibType, int type,
			float strength, float minStrength, float interval, float time,
			float onTime, float startDelay, float amount) {

		this.name = name;
		this.stage = stage;
		this.pos = pos;

		this.vibType = vibType;
		this.type = type;
		this.strength = strength;
		this.minStrength = minStrength;
		this.interval = interval;
		this.time = time;
		this.onTime = onTime;
		this.startDelay = startDelay;
		this.amount = amount;

		this.timer = 0;
	}

	public static Vibration create(String name, int stage, int pos, int vibType,
			int type, float strength, float minStrength, float interval,
			float time, float onTime, float startDelay, float amount) {
		switch (type) {
		case 0:
			return new VibrationConstant(name, stage, pos, vibType, strength,
					minStrength, interval, time, onTime, startDelay, amount);
		case 1:
			return new VibrationInterval(name, stage, pos, vibType, strength,
					minStrength, interval, time, onTime, startDelay, amount);
		case 2:
			return new VibrationSine(name, stage, pos, vibType, strength,
					minStrength, interval, time, onTime, startDelay, amount);
		case 3:
			return new VibrationUp(name, stage, pos, vibType, strength,
					minStrength, interval, time, onTime, startDelay, amount);
		case 4:
			return new VibrationDown(name, stage, pos, vibType, strength,
					minStrength, interval, time, onTime, startDelay, amount);
		case 5:
			return new VibrationRandom(name, stage, pos, vibType, strength,
					minStrength, interval, time, onTime, startDelay, amount);
		default:
			return null;
		}
	}

	protected abstract float getRumbleStrengthAbstract();

	public Vibration clone(String name, int stage, int pos) {
		return create(name, stage, pos, vibType, type, strength, minStrength,
				interval, time, onTime, startDelay, amount);
	}

	public Vibration clone(String name, int stage, int pos, int type) {
		return create(name, stage, pos, vibType, type, strength, minStrength,
				interval, time, onTime, startDelay, amount);
	}

	private float timer;

	protected float getTimer() {
		return timer;
	}

	private boolean remove = false;

	private void remove() {
		remove = true;

	}

	public boolean removeMe() {
		return remove;
	}

	public float getRumbleStrength() {
		// wait for it to start
		if (startDelay != 0) {
			return 0;
		}

		// run corresponding vibration
		return getRumbleStrengthAbstract();
	}

	public void update(float delta) {
		// wait for start delay
		if (startDelay - delta > 0) {
			startDelay -= delta;
			return;
		}

		// use last bit of delta witch wasn't used for start delay
		timer += startDelay;
		startDelay = 0;

		// add delta time
		timer += delta;

		// remove when time is up
		if (timer > time && time != -1) {
			remove();
		}
	}

	// get set and overrides

	public boolean requiresStrength() {
		return requiresStrengthAbstract() && strength == 0;
	}

	public boolean requiresMinStrength() {
		return requiresMinStrengthAbstract() && minStrength == 0;
	}

	public boolean requiresTime() {
		return requiresTimeAbstract() && time == 0;
	}

	public boolean requiresInterval() {
		return requiresIntervalAbstract() && interval == 0;
	}

	public boolean requiresAmount() {
		return requiresAmountAbstract() && amount == 0;
	}

	public boolean requiresOnTime() {
		return requiresOnTimeAbstract() && onTime == 0;
	}

	public boolean requiresStartDelay() {
		return requiresStartDelayAbstract() && startDelay == 0;
	}

	public abstract boolean requiresStrengthAbstract();

	public abstract boolean requiresMinStrengthAbstract();

	public abstract boolean requiresTimeAbstract();

	public abstract boolean requiresIntervalAbstract();

	public abstract boolean requiresAmountAbstract();

	public abstract boolean requiresOnTimeAbstract();

	public abstract boolean requiresStartDelayAbstract();

	public boolean usableStrength() {
		return usableStrengthAbstract();
	}

	public boolean usableMinStrength() {
		if (strength == 0)
			return false;
		return usableMinStrengthAbstract();
	}

	public boolean usableTime() {
		return usableTimeAbstract();
	}

	public boolean usableInterval() {
		return usableIntervalAbstract();
	}

	public boolean usableAmount() {
		return usableAmountAbstract();
	}

	public boolean usableOnTime() {
		return usableOnTimeAbstract();
	}

	public boolean usableStartDelay() {
		return usableStartDelayAbstract();
	}

	public abstract boolean usableStrengthAbstract();

	public abstract boolean usableMinStrengthAbstract();

	public abstract boolean usableTimeAbstract();

	public abstract boolean usableIntervalAbstract();

	public abstract boolean usableAmountAbstract();

	public abstract boolean usableOnTimeAbstract();

	public abstract boolean usableStartDelayAbstract();

	public void setType(int type) {
		this.type = type;

		// check if inputs are not needed anymore
		setVibType(getVibType());

		setStrength(getStrength());
		setMinStrength(getMinStrength());

		setTime(getTime());
		setInterval(getInterval());
		setAmount(getAmount());

		setOnTime(getOnTime());
		setStartDelay(getStartDelay());
	}

	public void setVibType(int vibType) {
		this.vibType = vibType;
	}

	public void setStrength(float strength) {
		if (usableStrength()) {
			if (strength > 1)
				strength = 1f;
			if (strength < 0)
				strength = 0f;

			this.strength = strength;
		} else {
			this.strength = 0;
		}
		setMinStrength(getMinStrength()); // update min strength
	}

	public void setMinStrength(float minStrength) {
		if (usableMinStrength()) {
			if (minStrength > strength)
				minStrength = strength;
			if (minStrength < 0)
				minStrength = 0f;

			this.minStrength = minStrength;
		} else {
			this.minStrength = 0;
		}
	}

	public void setTime(float time) {
		if (usableTime()) {
			if (time < 0 && time != -1)
				time = 0;
			this.time = time;
		} else {
			this.time = 0;
		}
	}

	public void setInterval(float interval) {
		if (usableInterval()) {
			this.interval = interval;
		} else {
			this.interval = 0;
		}
	}

	public void setAmount(float amount) {
		if (usableAmount()) {
			this.amount = amount;
		} else {

			this.amount = amount;
		}
	}

	public void setOnTime(float onTime) {
		if (usableOnTime()) {
			if (onTime < 0)
				onTime = 0;
			if (interval != 0) {
				if (onTime > interval) {
					onTime = interval;
				}
			} else if (amount == 0 || time == 0) {
				onTime = 0;
			} else if (onTime > (time / amount)) {
				onTime = time / amount;
			}
			this.onTime = onTime;
		} else {
			this.onTime = 0;
		}
	}

	public void setStartDelay(float startDelay) {
		if (usableStartDelay()) {
			if (startDelay < 0)
				startDelay = 0;
			this.startDelay = startDelay;
		} else {
			this.startDelay = 0;
		}

	}

	public int getType() {
		return type;
	}

	public int getVibType() {
		return vibType;
	}

	public float getStrength() {
		return strength;
	}

	public float getMinStrength() {
		return minStrength;
	}

	public float getTime() {
		return time;
	}

	public float getInterval() {
		return interval;
	}

	public float getAmount() {
		return amount;
	}

	public float getOnTime() {
		return onTime;
	}

	public float getStartDelay() {
		return startDelay;
	}

	public String getName() {
		return name;
	}

	public int getStage() {
		return stage;
	}

	public int getPos() {
		return pos;
	}

}
