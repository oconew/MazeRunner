package pack;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

// not as behaviour, need light average in main class

class Calibrate {
	private boolean hasCalibrated = false;
	private SampleProvider sp; //redmode

	public Calibrate(SampleProvider sp) {
		this.sp = sp;
	}

	// Calibrates a sensor 
	public float action() {
		
		float LIGHT_AVERAGE = 0;
		
		// Can only be calibrated one time
		if (!hasCalibrated) {
			// Wait until suppress method is called
			hasCalibrated = true;
			LCD.drawString("Calibrating Color Sensor...", 1,2);
			
			float[] sample = new float[1];
		    float currentLightLevel, minLightLevel, maxLightLevel;
		    maxLightLevel = Float.MIN_VALUE;
			 minLightLevel = Float.MAX_VALUE;
			while (!Button.ENTER.isDown()) {
				sp.fetchSample(sample, 0);
			     currentLightLevel = sample[0];
				 
				if (currentLightLevel > maxLightLevel) {
			        maxLightLevel = currentLightLevel;
			    }

			    if (currentLightLevel < minLightLevel) {
			        minLightLevel = currentLightLevel;
			 
			    }
			    LCD.clear();
			    LCD.drawString(String.valueOf(currentLightLevel), 1, 1);
			      
				LIGHT_AVERAGE = ((maxLightLevel + minLightLevel) / 2.0f + LIGHT_AVERAGE) / 2.0f;
			        
			    // Adding a delay to control the loop speed
			    Delay.msDelay(500);
			}
	
			LCD.clear();
			LCD.drawString(String.valueOf(maxLightLevel), 2, 2);
			LCD.drawString(String.valueOf(minLightLevel), 2, 3);
			LCD.drawString(String.valueOf(LIGHT_AVERAGE), 2, 4);
		}
		return LIGHT_AVERAGE;
	}	
}

