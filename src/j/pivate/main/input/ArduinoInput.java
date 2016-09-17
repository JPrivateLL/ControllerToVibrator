package j.pivate.main.input;

import gnu.io.NoSuchPortException;
import j.pivate.main.Arduino;

public class ArduinoInput {
	
	public ArduinoInput() throws IllegalArgumentException, NoSuchPortException {
		Arduino.init();
		
		new Thread()
		{
		    public void run() {
		        while(true){
		        	try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}   
		        }
		    }
		}.start();
		
		
	}
	
}
