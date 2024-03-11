package pack;

import lejos.robotics.subsumption.Behavior;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;


public class TurnAround implements Behavior{
	private MovePilot p;
	private SampleProvider sp;
	private boolean suppressed;
	
	public TurnAround(MovePilot p, SampleProvider sp) {
		this.p = p;
		this.sp = sp;
		suppressed = false;
	}

	// True when red detected, meaning dead end reached
	@Override
	public boolean takeControl() {
		float[] sample = new float [3];
		sp.fetchSample(sample, 0); 
		
		return (sample[0] > 1 ); //??? amount of red light
	}

	@Override
	public void action() {
		suppressed = false;
		
		while(!suppressed) {}
		
		p.rotate(180);
		
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}
}





