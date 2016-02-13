package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.sensors.BadCAN;
import org.usfirst.frc.team1014.robot.sensors.BadTalon;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class ShooterAndGrabber extends BadSubsystem implements PIDSource, PIDOutput {
	public static ShooterAndGrabber instance;
	private SpeedController left, right, rotator;
	private SpeedController ringLight;
	public Servo pusher;
	public boolean grabberSet = false;
	private double previousRPM = 0;
	private boolean grabbed = false;
	private double grabSpeed = 0.5;

	public static ShooterAndGrabber getInstance() {
		if (instance == null)
			instance = new ShooterAndGrabber();

		return instance;
	}

	@Override
	protected void initialize() {
		left = new BadCAN(ControlsManager.SHOOTER_LEFT, ControlsManager.SHOOTER_LEFT_ENCODER_A,
				ControlsManager.SHOOTER_LEFT_ENCODER_B);
		right = new BadCAN(ControlsManager.SHOOTER_RIGHT, ControlsManager.SHOOTER_RIGHT_ENCODER_A,
				ControlsManager.SHOOTER_RIGHT_ENCODER_B);
		rotator = new BadCAN(ControlsManager.SHOOTER_ROTATE, ControlsManager.ARTICULATOR_ENCODER_A,
				ControlsManager.ARTICULATOR_ENCODER_B);
		ringLight = new Talon(ControlsManager.RING_LIGHT);
		pusher = new Servo(ControlsManager.PUSHER);
		pusher.set(0);
	}

	public void setSpeeds(double speed) {
		if (previousRPM - 400 > ((BadCAN) left).getRpm() && grabberSet == true) {
			grabbed = true;
			left.set(0);
			right.set(0);
		}
		if (grabbed && previousRPM > 0 && grabberSet) {
			left.set(0);
			right.set(0);
		} else if (grabbed && previousRPM <= 0 && grabberSet) {
			grabbed = false;
			grabberSet = false;
		} else {
			left.set(speed);
			right.set(-speed);
			if (speed <= 0) {
				grabberSet = false;
			} else {
				grabberSet = true;
			}
		}
		previousRPM = ((BadCAN) left).getRpm();
		Logger.logThis("previousRPM = " + previousRPM);

	}

	public void grabBall() {
		grabberSet = true;
		if (previousRPM - 400 > ((BadCAN) left).getRpm()) {
			grabbed = true;
			left.set(0);
			right.set(0);
		}
		if (grabbed && previousRPM > 0) {
			left.set(0);
			right.set(0);
		} else if (grabbed && previousRPM <= 0) {
			grabbed = false;
			grabberSet = false;
		} else {
			left.set(grabSpeed);
			right.set(-grabSpeed);
		}
		previousRPM = ((BadCAN) left).getRpm();
		Logger.logThis("previousRPM = " + previousRPM);

	}

	public double getShootingRPM() {
		return ((BadCAN) left).getRpm();
	}

	public void rotate(double speed) {
		rotator.set(speed);
	}

	public void shoot(double speed) {
		left.set(speed);
		right.set(-speed);
	}

	public void grab(double speed) {
		left.set(-speed);
		right.set(speed);
	}

	public void ringLightOn() {
		ringLight.set(.5);
	}

	public void ringLightOff() {
		ringLight.set(0);
	}

	public void driveServo(double power) {
		if (power > 0.650) {
			power = 0.650;
		} else if (power < .25) {
			power = .25;
		}
		pusher.set(power);
	}

	@Override
	public String getConsoleIdentity() {
		return "ShooterAndGrabber";
	}

	@Override
	protected void initDefaultCommand() {

	}

	@Override
	public void pidWrite(double arg0) {
		left.set(arg0);
		right.set(arg0);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		return ((BadCAN) left).getRpm();
	}

	@Override
	public void setPIDSourceType(PIDSourceType arg0) {
		// TODO Auto-generated method stub

	}

}
