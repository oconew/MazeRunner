package pack;

import lejos.robotics.subsumption.Behavior;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;


public class DetectColour implements Behavior{
	// This behaviour will cause the robot to move forward along the line until it reaches a dead end or is surpressed by a turning behaviour.
	private MovePilot pilot;
	private SampleProvider sp;
	private float average;
	private boolean suppressed;
	
	public DetectColour(SampleProvider sp, float average) {
		this.sp = sp;
		this.average = average;
		suppressed = false;
	}

	// True when cs sees black
	@Override
	public boolean takeControl() {
		float[] sample = new float [1];
		sp.fetchSample(sample, 0); 
//		if black return true
		return (sample[0] < average + 0.05);
	}

	@Override
	public void action() {
		suppressed = false;
		pilot.forward();
		while(!suppressed) {
			
		}
		pilot.stop();
		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}
}




