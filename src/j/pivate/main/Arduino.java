package j.pivate.main;

import gnu.io.NoSuchPortException;
import j.pivate.main.gui.GUIStartMenu;

import org.sintef.jarduino.AnalogPin;
import org.sintef.jarduino.DigitalState;
import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.PWMPin;
import org.sintef.jarduino.PinMode;

public final class Arduino extends JArduino {
	PinMode outPut = PinMode.OUTPUT;
	DigitalState high = DigitalState.HIGH;
	DigitalState low = DigitalState.LOW;
	static Arduino arduino = null;
	private Arduino(final String port) throws NullPointerException,NoSuchPortException {
		super(port);
		
		try {
			this.ping();

		} catch (final NullPointerException e) {
			throw new IllegalArgumentException("port invalide");
		}
	}
	
	public static void init() throws NullPointerException,NoSuchPortException{
		if(arduino==null){
			arduino = new Arduino(GUIStartMenu.getArduinoPort());
		}
	}
	
	@Override
	protected void loop() {

	}
	@Override
	protected void setup() {
		
	}
	
	public static void setPWM(final PWMPin pin, final int i) {
		arduino.analogWrite(pin, (byte) i);
	}

	public static Short getFSRStrength(){
		AnalogPin[] pinList = {AnalogPin.A_0,AnalogPin.A_1,AnalogPin.A_2,AnalogPin.A_3,AnalogPin.A_4,AnalogPin.A_5};
		AnalogPin pin = pinList[GUIStartMenu.getFsrAnalogPin()];
		return arduino.analogRead(pin);
	}
	


}
