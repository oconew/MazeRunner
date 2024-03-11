package pack;

import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class AdjustToLine implements Behavior{
	private MovePilot pilot;
	private SampleProvider sp;
	private float average;
	private boolean suppressed;
	
	public AdjustToLine(MovePilot p, SampleProvider sp, float average) {
		this.pilot = p;
		this.sp = sp;
		this.average = average;
		suppressed = false;
	}

	// True when cs sees white
	@Override
	public boolean takeControl() {
		float[] sample = new float [1];
		sp.fetchSample(sample, 0); 
		
		return (sample[0] > average + 0.05);
	}

	@Override
	public void action() {
		suppressed = false;
		
		while(!suppressed) {
			float[] sample = new float [1];
			sp.fetchSample(sample, 0); 
			float firstSample = sample[0];
			
			pilot.rotate(10);
			sp.fetchSample(sample, 0); 
			
			if (firstSample == sample[0]) {
				pilot.rotateRight(); // trundle behaviour takes over to move robot forward 
			} else {
				pilot.rotateLeft();
			}
			
		} 
		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}
}
