package pack;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.hardware.sensor.EV3ColorSensor;

public class Driver {
	
	final static float WHEEL_DIAMETER = 51; // The diameter (mm) of the wheels
	final static float AXLE_LENGTH = 80; // The distance (mm) your two driven wheels
	final static float ANGULAR_SPEED = 30; // How fast around corners (degrees/sec)
	final static float LINEAR_SPEED = 10; // How fast in a straight line (mm/sec)

	 public static void main(String[] args) {
		 MovePilot pilot = getPilot(MotorPort.A, MotorPort.B, WHEEL_DIAMETER, AXLE_LENGTH / 2); // CHANGE INTS
		 pilot.setLinearSpeed(LINEAR_SPEED);
		 
		 EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S1);
		 SampleProvider redMode = cs.getRedMode();
		 SampleProvider rgbMode = cs.getRGBMode();
		 
		 Calibrate calibrator = new Calibrate(redMode);
		 float average = calibrator.action();
		 average = 0.1f;
		 
		 Behavior trundle = new Trundle(pilot, redMode, average); // forward when sees black
		 Behavior turn = new TurnLeft(pilot, redMode, average); // turn left/right when no black
		 Behavior turnAround = new TurnAround(pilot, rgbMode); // 180 turn when sees (red?)
		 
		 Behavior[] behaviors = new Behavior[] {trundle, turn};// change order
		 Arbitrator ab = new Arbitrator(behaviors); 
		 ab.go();  // Blocking call so never returns
		 Button.waitForAnyEvent();
		 ab.stop();
		 
		 cs.close();
	 }	 
		  
	 private static MovePilot getPilot(Port left, Port right, float diam, float offset) {
		 BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(left);
		 Wheel wL = WheeledChassis.modelWheel(mL, diam).offset(-1 * offset);
		 BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(right);
		 Wheel wR = WheeledChassis.modelWheel(mR, diam).offset(offset);
		 
		 Wheel[] wheels = new Wheel[] {wR, wL};
		 Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
		 
		 return new MovePilot(chassis);
	}

}
