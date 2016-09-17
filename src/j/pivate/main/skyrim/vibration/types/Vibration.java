package j.pivate.main.skyrim.vibration.types;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import j.pivate.main.skyrim.SexlabMainThread;
import j.pivate.main.skyrim.vibration.VibrationSet;

public abstract class Vibration{

	static List<Vibration> existingVibrations = new ArrayList<Vibration>();
	
	private static String findInLine(String toFind, String line){
		line = line.toLowerCase();
		toFind = toFind.toLowerCase();
		
		toFind = toFind+"=";
		if(!line.contains(toFind)){return "";}
		String txt = null;
		
		
		txt = line.substring(line.indexOf(toFind)+toFind.length());

		if(txt.startsWith("\"")){
			txt = txt.split("\"")[1];
		}else if(txt.contains(" ")){
			txt = txt.split(" ")[0];
		}
			
		return txt;
	}
	public static void load() throws IndexOutOfBoundsException, IOException{
		System.out.println("loading SexLab_Animations.txt");
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader("Skyrim\\SexLab_Animations.txt"));
		int lineNr = 0;
		while(true){
			
			String line = reader.readLine();
			if(line==null){
				break;
			}
			lineNr++;
			
			line = line.toLowerCase();
			
			
			
			//get name
			String name = findInLine("name", line);

			//get vib type
			String vib = findInLine("vib", line);
			boolean[] vibType = new boolean[12];
			try{
				String[] vibTypeSet = vib.split(",");
				for(int i=0; i<vibTypeSet.length; i++){
					int y = -1;
					try{y = Integer.valueOf(vibTypeSet[i])-1;}catch(NumberFormatException e){break;}
					if(y>vibType.length){
						throw new IndexOutOfBoundsException("line:"+lineNr+" no valide Vib="+y+" needs to be 1-12");
					}else{
						vibType[y] = true;
					}
				}
			}catch(NullPointerException e){}
			
			
			
			
			//get pos
			String posString = findInLine("pos", line);
			int pos = 0;
			try{pos = Integer.parseInt(posString);}catch(NumberFormatException|NullPointerException e){
				reader.close();
				throw new IndexOutOfBoundsException("line:"+lineNr+" no valide pos="+pos);
			}

		
			//get stage
			String stageString = findInLine("stage", line);
			int stage = 0;
			try{stage = Integer.parseInt(stageString);}catch(NumberFormatException|NullPointerException e){
				reader.close();
				throw new IndexOutOfBoundsException("line:"+lineNr+" no valide stage="+stage);
			}
			
			//get number
			String numberString = findInLine("number", line);
			int number = 0;
			try{number = Integer.parseInt(numberString);}catch(NumberFormatException|NullPointerException e){
				reader.close();
				throw new IndexOutOfBoundsException("line:"+lineNr+" no valide number="+number);
			}
			
			//get type
			String type = findInLine("type", line);
			boolean typeValide = false;
			for (int i = 0; i < TYPES.length; i++) {
				if(TYPES[i].toString().toLowerCase().equals(type.toLowerCase())){
					typeValide = true;
				}
			}
			if(!typeValide){
				reader.close();
				throw new IndexOutOfBoundsException("line:"+lineNr+" no valide type="+type);
			}
			type = type.substring(0, 1).toUpperCase() + type.substring(1);
			
			//get strength
			String strengthString = findInLine("strength", line);
			float strength = 0;
			try{strength = Float.parseFloat(strengthString);}catch(NumberFormatException|NullPointerException e){
				if(!strengthString.equals("")){
					reader.close();
					throw new IndexOutOfBoundsException("line:"+lineNr+" no valide strength="+strength);
				}
			}

			//get min strength
			String minStrengthString = findInLine("minStrength", line);
			float minStrength = 0;
			try{minStrength = Float.parseFloat(minStrengthString);}catch(NumberFormatException|NullPointerException e){
				if(!minStrengthString.equals("")){
					reader.close();
					throw new IndexOutOfBoundsException("line:"+lineNr+" no valide minStrength="+minStrengthString);
				}
			}
			
			//get interval	
			String intervalString = findInLine("interval", line);
			float interval = 0;
			try{interval = Float.parseFloat(intervalString);}catch(NumberFormatException e){}catch(NullPointerException e){
				if(!intervalString.equals("")){
					reader.close();
					throw new IndexOutOfBoundsException("line:"+lineNr+" no valide interval="+intervalString);
				}
			}
		
			//get time
			String timeString = findInLine("time", line);
			float time = 0;
			try{time = Float.parseFloat(timeString);}catch(NumberFormatException e){}catch(NullPointerException e){
				if(!timeString.equals("")){
					reader.close();
					throw new IndexOutOfBoundsException("line:"+lineNr+" no valide time="+timeString);
				}
			}
			
			//get on time
			String onTimeString =  findInLine("onTime", line);
			float onTime = 0;
			try{onTime = Float.parseFloat(onTimeString);}catch(NumberFormatException e){}catch(NullPointerException e){
				if(!onTimeString.equals("")){
					reader.close();
					throw new IndexOutOfBoundsException("line:"+lineNr+" no valide onTime="+onTimeString);
				}
			}
			//get start delay
			String startDelayString = findInLine("startDelay", line);
			float startDelay = 0;
			try{startDelay = Float.parseFloat(startDelayString);}catch(NumberFormatException e){}catch(NullPointerException e){
				if(!startDelayString.equals("")){
					reader.close();
					throw new IndexOutOfBoundsException("line:"+lineNr+" no valide startDelay="+startDelayString);
				}
			}
			
			//get amount
			String amountString = findInLine("amount", line);
			float amount = 0;
			try{amount = Float.parseFloat(amountString);}catch(NumberFormatException e){}catch(NullPointerException e){
				if(!amountString.equals("")){
					reader.close();
					throw new IndexOutOfBoundsException("line:"+lineNr+" no valide amount="+amountString);
				}
			}
				
			//get amount
			String plugRequiresString = findInLine("plugRequires", line);
			Boolean plugRequires = null;
			try{plugRequires = Boolean.parseBoolean(plugRequiresString);}catch(NumberFormatException e){}catch(NullPointerException e){
				if(!plugRequiresString.equals("")){
					reader.close();
					throw new IndexOutOfBoundsException("line:"+lineNr+" no valide PlugRequired="+plugRequiresString);
				}
			}
			
			for (Vibration sv : existingVibrations) {
				if(sv.name.equals(name)&&sv.pos==pos&&sv.stage==stage&&sv.number==number){
					//already loaded skipping
					reader.close();
					return;
					
				}
			}
			
			existingVibrations.add(Vibration.create(name, vibType, pos, stage, number, type, strength, minStrength, interval, time, onTime, startDelay, amount, plugRequires));
		
		}
		
		
		
		reader.close();
	}
	public static void save(){
		
		//clean list(reverse list to prevent crashing)
		for (int i = existingVibrations.size() - 1; i >= 0; i--) {	
			Vibration vib = existingVibrations.get(i);
			if(!vib.isValide()){
				existingVibrations.remove(vib);
			}
		}
		
		System.out.println("Saving "+existingVibrations.size()+" vibrations to SexLab_Animations.txt");

		//sort list
		Collections.sort(existingVibrations, (a, b) -> a.number - b.number);
		Collections.sort(existingVibrations, (a, b) -> a.pos - b.pos);
		Collections.sort(existingVibrations, (a, b) -> a.name.compareToIgnoreCase(b.name));
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("Skyrim\\SexLab_Animations.txt"));
			
			for (Vibration sv : existingVibrations) {
				String vibType = "";
				for (int i = 0; i < sv.vibType.length; i++) {
					if(sv.vibType[i]){
						vibType+= i+1;
						vibType+=",";
					}
				}
				if(vibType.endsWith(",")){vibType = vibType.substring(0, vibType.length()-1);}
				
				String cbuf = (
					"Name=\""+sv.name+"\""+
					" Pos="+sv.pos+
					" Stage="+sv.stage+
					" Number="+sv.number+
					" Vib="+vibType+
					" Type="+sv.type
				);
				if(sv.strength!=0){
					cbuf+=" Strength="+ sv.strength;
				}
				if(sv.minStrength!=0){
					cbuf+=" MinStrength="+ sv.minStrength;
				}
				if(sv.interval!=0){
					cbuf+=" Interval="+ sv.interval;
				}
				if(sv.time!=0){
					cbuf+=" Time="+ sv.time;
				}
				if(sv.onTime!=0){
					cbuf+=" OnTime="+ sv.onTime;
				}
				if(sv.startDelay!=0){
					cbuf+=" StartDelay="+ sv.startDelay;
				}
				if(sv.amount!=0){
					cbuf+=" Amount="+ sv.amount;
				}
				if(sv.getPlugRequired()){
					cbuf+=" plugRequires=True";
				}
				
				writer.write(cbuf+"\n");
			}
			
			writer.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}


		
		
		
	}

	public static VibrationSet get(String name, int pos, int stage) {
		VibrationSet vl = new VibrationSet();
		//create 4 separate vibrations which fire on same input
		number:
		for (int number = 1; number <= 4; number++) {
				
			System.out.println(existingVibrations.size());
			//use existing
			for (Vibration sv : existingVibrations) {
				if(sv.name.equals(name)&&sv.pos==pos&&sv.stage==stage&&sv.number==number){
					vl.add(sv);
					continue number;
				}
			}
			
			
			//make new one
			boolean[] vibType = new boolean[VIBTYPES.length];
			String type = TYPES[0];
			float strength = 0;
			float minStrength = 0;
			float interval = 0;
			float time = 0;
			float onTime = 0;
			float startDelay = 0;
			float amount = 0;
			boolean plugRequires = false;
			
			//check if previous animation exist so we can copy values
			for (Vibration sv : existingVibrations) {
				if(sv.name.equals(name)&&sv.pos==pos&&sv.stage==stage-1&&sv.number==number){
					vibType = sv.getVibType();
					type = sv.getType();
					strength = sv.getStrength();
					minStrength = sv.getMinStrength();
					interval = sv.getInterval();
					time = sv.getTime();
					onTime = sv.getOnTime();
					amount = sv.getAmount();
					plugRequires = sv.getPlugRequired();
				}
			}
			
			Vibration v = Vibration.create(name, vibType, pos, stage, number, type, strength, minStrength, interval, time, onTime, startDelay, amount, plugRequires);
			existingVibrations.add(v);
			System.out.println(type);
			vl.add(v);
		}
		return vl;
	}
	
	protected final String name;
	protected boolean[] vibType;
	protected final int pos;
	protected final int stage;
	protected final int number;
	
	protected String type;
	protected float strength;
	protected float minStrength;
	protected float interval;
	protected float time;
	protected float onTime;
	protected float startDelay;
	protected float amount;	
	protected boolean plugRequired;
	
	private float timer;
	public float getTimer(){return timer;}
	
	public static final String[] TYPES = {"None", "Constant", "Interval", "Sine", "Up", "Down", "Random"};
	public static final String[] VIBTYPES = {"Viginal", "Viginal (shock)", "Anal", "Anal (shock)", "Breasts", "Breasts (shock)", "Oral", "Oral (shock)", "Damage", "Damage (shock)", "Interaction", "Interaction (shock)"};
	
	protected Vibration(String name, boolean[] vibType, int pos, int stage,int number, String type, float strength, float minStrength, 
			float interval, float time, float onTime, float startDelay, float amount, boolean plugRequired){
		this.name = name;
		this.vibType = vibType;
		this.pos = pos;
		this.stage = stage;
		this.number = number;
		
		this.type = type;
		this.strength = strength;
		this.minStrength = minStrength;
		this.interval = interval;
		this.time = time;
		this.onTime = onTime;
		this.startDelay = startDelay;
		this.amount = amount;

		this.plugRequired = plugRequired;
		
		this.timer = 0;
	}

	private static Vibration create(String name, boolean[] vibType, int pos, int stage,int number, String type, float strength, float minStrength, 
			float interval, float time, float onTime, float startDelay, float amount, boolean plugRequired){
		Vibration v = null;
		if(type.equals("Constant")) v = new VibrationConstant(name, vibType, pos, stage, number, strength, minStrength,interval,time,onTime,startDelay, amount, plugRequired);
		else if(type.equals("Interval")) v = new VibrationInterval(name, vibType, pos, stage, number, strength, minStrength,interval,time,onTime,startDelay, amount, plugRequired);
		else if(type.equals("Up")) 		v = new VibrationUp(name, vibType, pos, stage, number, strength, minStrength,interval,time,onTime,startDelay, amount, plugRequired);
		else if(type.equals("Down")) 	v = new VibrationDown(name, vibType, pos, stage, number, strength, minStrength,interval,time,onTime,startDelay, amount, plugRequired);
		else if(type.equals("Sine")) 	v = new VibrationSine(name, vibType, pos, stage, number, strength, minStrength,interval,time,onTime,startDelay, amount, plugRequired);
		else if(type.equals("Random"))	v = new VibrationRandom(name, vibType, pos, stage, number, strength, minStrength,interval,time,onTime,startDelay, amount, plugRequired);
		else if(type.equals("None"))	v = new VibrationNone(name, vibType, pos, stage, number, strength, minStrength,interval,time,onTime,startDelay, amount, plugRequired);
		else{System.out.println("unknown type:"+type);}
		return v;
	}
		
	private boolean remove = false;
	private void remove() {
		remove = true;
		
	}
	public boolean removeMe(){
		return remove;
	}

	private static boolean muted = false;
	public static void setMuted(Boolean b){
		muted = b;
	}
	
	public void update(float delta){
		//wait for start delay
				if(startDelay-delta>0){
					startDelay-=delta;
					return;
				}
				
				//use last bit of delta witch wasn't used for start delay 
				timer += startDelay;
				startDelay = 0;
				
				//add delta time
				timer += delta;
				
				//remove when time is up
				if(timer>time&&time!=-1){remove();}
	}
	
	public float getRumbleStrength(){			
		//wait for it to start
		if(startDelay!=0){
			return 0;
		}
		
		//check if vibrations are muted
		if(muted){return 0;}
		
		//run corresponding vibration
		return getRumbleStrengthOverride();
	}
	
	protected abstract float getRumbleStrengthOverride();
	
	public boolean[] getVibTypeWithPlug() {
		if(plugRequired){
			boolean[]out = new boolean[12];	
									//toy									//piercing
			out[0] = vibType[0]&&(SexlabMainThread.getEquipedItems()[0] || SexlabMainThread.getEquipedItems()[1]);
			out[1] = vibType[1]&&(SexlabMainThread.getEquipedItems()[0] || SexlabMainThread.getEquipedItems()[1]);
			
			out[2] = vibType[2]&&SexlabMainThread.getEquipedItems()[2];
			out[3] = vibType[3]&&SexlabMainThread.getEquipedItems()[2];
			
			out[4] = vibType[4]&&SexlabMainThread.getEquipedItems()[3];
			out[5] = vibType[5]&&SexlabMainThread.getEquipedItems()[3];
			
			return out;
		}else{
			return vibType;
		}
	}
	
	public boolean isValide(){
		return !requiresType()&&
				!requiresVibType()&&
				!requiresStrength()&&
				!requiresMinStrength()&&
				!requiresTime()&&
				!requiresInterval()&&
				!requiresAmount()&&
				!requiresOnTime()&&
				!requiresStartDelay()&&
				!requiresPlugRequired();
	}

	public boolean requiresType(){
		return requiresTypeAbstract()&&type.equals(TYPES[0]);
	}
	public boolean requiresVibType(){
		boolean someThingSelected = false;
		for (int i = 0; i < vibType.length; i++) {
			if(vibType[i])someThingSelected = true;
		}
		return requiresVibTypeAbstract()&&!someThingSelected;
	}
	public boolean requiresStrength(){
		return requiresStrengthAbstract() && strength==0;
	}
	public boolean requiresMinStrength(){
		return requiresMinStrengthAbstract() && minStrength==0;
	}
	public boolean requiresTime(){
		return requiresTimeAbstract() && time==0;
	}
	public boolean requiresInterval(){
		return requiresIntervalAbstract() && interval==0;
	}
	public boolean requiresAmount(){
		return requiresAmountAbstract() && amount==0;
	}
	public boolean requiresOnTime(){
		return requiresOnTimeAbstract() && onTime==0;
	}
	public boolean requiresStartDelay(){
		return requiresStartDelayAbstract() && startDelay==0;
	}
	public boolean requiresPlugRequired(){
		return requiresPlugRequiredAbstract() && plugRequired==false;
	}
	
	public abstract boolean requiresTypeAbstract();
	public abstract boolean requiresVibTypeAbstract();
	public abstract boolean requiresStrengthAbstract();
	public abstract boolean requiresMinStrengthAbstract();
	public abstract boolean requiresTimeAbstract();
	public abstract boolean requiresIntervalAbstract();
	public abstract boolean requiresAmountAbstract();
	public abstract boolean requiresOnTimeAbstract();
	public abstract boolean requiresStartDelayAbstract();
	public abstract boolean requiresPlugRequiredAbstract();
	
	public abstract boolean usableType();
	public abstract boolean usableVibType();
	public abstract boolean usableStrength();
	public abstract boolean usableMinStrength();
	public abstract boolean usableTime();
	public abstract boolean usableInterval();
	public abstract boolean usableAmount();
	public abstract boolean usableOnTime();
	public abstract boolean usableStartDelay();
	public abstract boolean usablePlugRequired();
	
	public void setType(String type){
		this.type = type;
		
		//check if inputs are not needed anymore
		setVibType(getVibType());
		
		setStrength(getStrength());
		setMinStrength(getMinStrength());
		
		setTime(getTime());
		setInterval(getInterval());
		setAmount(getAmount());
		
		setOnTime(getOnTime());
		setStartDelay(getStartDelay());
		
		setPlugRequired(getPlugRequired());
	}
	public void setVibType(boolean [] vibType){
		if(usableVibType()){
			this.vibType = vibType;
		}else{
			this.vibType = new boolean[12];
		}
	}
	public void setStrength(float strength){
		if(usableStrength()){
			if(strength>1)strength=1f;
			if(strength<0)strength=0f;
			
			this.strength = strength;
		}else{
			this.strength = 0;
		}
		setMinStrength(getMinStrength()); //update min strength
	}
	public void setMinStrength(float minStrength){
		if(usableMinStrength()){
			if(minStrength>strength)minStrength=strength;
			if(minStrength<0)minStrength=0f;
			
			this.minStrength = minStrength;
		}else{
			this.minStrength = 0;
		}
	}
	public void setTime(float time){
		if(usableTime()){
			if(time<0&&time!=-1)time=0;
			this.time = time;
		}else{
			this.time = 0;
		}
	}
	public void setInterval(float interval){
		if(usableInterval()){
			this.interval = interval;
		}else{
			this.interval = 0;
		}
	}
	public void setAmount(float amount){
		if(usableAmount()){
			this.amount = amount;
		}else{

			this.amount = amount;
		}
	}
	public void setOnTime(float onTime){
		if(usableOnTime()){
			if(onTime<0)onTime=0;
			if(interval!=0){
				if(onTime>interval){
					onTime=interval;
				}
			}else if(amount==0||time==0){
				onTime=0;
			}else if(onTime>(time/amount)){
				onTime = time/amount;
			}
			this.onTime = onTime;
		}else{
			this.onTime = 0;
		}
	}
	public void setStartDelay(float startDelay){
		if(usableStartDelay()){
			if(startDelay<0)startDelay=0;
			this.startDelay = startDelay;
		}else{
			this.startDelay = 0;
		}
		
	}
	public void setPlugRequired(boolean plugRequired){
		if(usablePlugRequired()){
			this.plugRequired = plugRequired;
		}else{
			this.plugRequired = false;
		}
	}
	
	public String getType(){
		return type;
	}
	public boolean [] getVibType(){
		return vibType;
	}
	public float getStrength(){
		return strength;
	}
	public float getMinStrength(){
		return minStrength;
	}
	public float getTime(){
		return time;
	}
	public float getInterval(){
		return interval;
	}
	public float getAmount(){
		return amount;
	}
	public float getOnTime(){
		return onTime;
	}
	public float getStartDelay(){
		return startDelay;
	}
	public boolean getPlugRequired(){
		return plugRequired;
	}
	
	public String getName(){
		return name;
	}
	public int getPos(){
		return pos;
	}
	public int getStage(){
		return stage;
	}
	public int getNumber(){
		return number;
	}

	
}
