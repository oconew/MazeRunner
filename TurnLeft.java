package pack;

import lejos.robotics.subsumption.Behavior;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;


public class TurnLeft implements Behavior{
	private MovePilot pilot;
	private SampleProvider sp;
	private float average;
	private boolean suppressed;
	
	public TurnLeft(MovePilot p, SampleProvider sp, float average) {
		this.pilot = p;
		this.sp = sp;
		this.average = average;
		suppressed = false;
	}

	// True when cs sees (colour)
	@Override
	public boolean takeControl() {
		float[] sample = new float [3];
		sp.fetchSample(sample, 0); 
		
		return (sample[2] > 0.8 ); //??? amount of green light
	}

	// Turn left, if no path, turn right
	@Override
	public void action() {
		suppressed = false;
	
		while(!suppressed) {
			pilot.rotate(-90);
			
			float[] sample = new float [1]; 
			sp.fetchSample(sample, 0); 
			
			// check if there is no black infront
			if (sample[0] > average + 0.05) pilot.rotate(180);
			// add to stack, decision made
		}
		
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}
}





