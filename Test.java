package pack;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Test {

	public static void main(String[] args) {
		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S1);
		SampleProvider sp = cs.getRGBMode();
		float[] sample = new float[3];
		
		while (!Button.ENTER.isDown()) {
			sp.fetchSample(sample, 0);
			LCD.drawString(("red: " + sample[0]), 2, 2);
			LCD.drawString(("green: " + sample[1]), 2, 3);
			LCD.drawString(("blue: " + sample[2]), 2, 4);
			 
			
		        
		    // Adding a delay to control the loop speed
		    Delay.msDelay(500);
		}


	}

}
