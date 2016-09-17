package j.pivate.main.vibrator;

import gnu.io.NoSuchPortException;
import j.pivate.main.Arduino;
import j.pivate.main.gui.GUIStartMenu;

import org.sintef.jarduino.PWMPin;

public class VibratorArduino extends Vibrator {
	private PWMPin pin;

	private final int minStrength = 57;

	public VibratorArduino(final boolean[] types,final int pinNumber) throws IllegalArgumentException, NoSuchPortException {
		super("Arduino pin " + pinNumber, types);
		Arduino.init();
		switch (pinNumber) {
		case 3:
			pin = PWMPin.PWM_PIN_3;
			break;
		case 5:
			pin = PWMPin.PWM_PIN_5;
			break;
		case 6:
			pin = PWMPin.PWM_PIN_6;
			break;
		case 9:
			pin = PWMPin.PWM_PIN_9;
			break;
		case 10:
			pin = PWMPin.PWM_PIN_10;
			break;
		case 11:
			pin = PWMPin.PWM_PIN_11;
			break;
		default:
			throw new IllegalArgumentException("pin invalide");
		}
	
		
	}

	@Override
	public boolean isConnected() {
		return true;

	}

	@Override
	public void release() {

	}

	@Override
	public void rumbleSub(float strength) {
		if (strength > 1)
			strength = 1;
		else if (strength <= 0)
			strength = 0;
		else
			strength = (strength * (255 - minStrength)) + minStrength;
		
		if(pin == PWMPin.PWM_PIN_5){
			System.out.println("testaa"+strength);
			strength/=4;
		}
		if(pin == PWMPin.PWM_PIN_3){
			System.out.println("testaa"+strength);
			strength/=2;
		}
		
		Arduino.setPWM(pin, (int) strength);
	}

}
