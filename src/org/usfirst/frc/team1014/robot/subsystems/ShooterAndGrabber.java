package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.sensors.BadCAN;
import org.usfirst.frc.team1014.robot.subsystems.LEDLights.LEDState;
import org.usfirst.frc.team1014.robot.utilities.Logger;
import org.usfirst.frc.team1014.robot.utilities.PID;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A {@link BadSubsystem} that controls the Shooter and Grabber.
 * 
 * @author - Manu S.
 */
public class ShooterAndGrabber extends BadSubsystem implements PIDSource, PIDOutput
{

	private static final double SERVO_STANDARD_POS = 0.9;
	private static final double SERVO_EXTENDED_POS = 0.1;
	// private static final double RING_LIGHT_ON_VALUE = .5;
	public static final double SHOOTER_LOWEST_POS = 30;
	public static final double SHOOTER_HIGHEST_POS = 2;
	public static final double SHOOTER_DEFAULT_SHOOTING_POS = 16;
	private static final double CUT_POWER_RPM_DROP = 400;
	public static final double DEFAULT_GRAB_SPEED = -0.5;
	public static double shooterOffset = 0;
	public boolean isLow = false;

	public DigitalInput limitSwitch;
	public static ShooterAndGrabber instance;
	private SpeedController left, right;
	public SpeedController rotator;

	private Relay ringLight;

	public DigitalInput opticalSensor;
	private boolean detectedTape = false;
	
	public Servo pusher;

	public boolean grabberSet = false;
	private double previousRPM = 0;
	private boolean grabbed = false;

	public static ShooterAndGrabber getInstance()
	{
		if(instance == null)
			instance = new ShooterAndGrabber();

		return instance;
	}

	@Override
	protected void initialize()
	{
		left = new CANTalon(ControlsManager.SHOOTER_LEFT);
		right = new CANTalon(ControlsManager.SHOOTER_RIGHT);
		rotator = new CANTalon(ControlsManager.SHOOTER_ROTATE);
		opticalSensor = new DigitalInput(ControlsManager.OPTICAL_SENSOR_PING);

		ringLight = new Relay(ControlsManager.RING_LIGHT);
		pusher = new Servo(ControlsManager.PUSHER);
		pusher.set(0);
		// limitSwitch = new DigitalInput(ControlsManager.LIMIT_SWITCH);
	}

	/**
	 * Sets the shooter speeds if user has control. <br />
	 * <p>
	 * More accurately if the grabber is enabled it looks for the point where the ball is caught and
	 * the RPM is lowered by a value greater than {@code BALL_CATCH_RPM_DECREASE}. When this is
	 * reached the {@code grabbed} boolean is set to true and the motors continue decreasing speed
	 * until they get to {@literal 0}. When the motors do fully stop {@code grabbed} is set to false
	 * and the grabber is reverted to user control mode. <br />
	 * <br />
	 * When the grabber is above the value of zero the {@code grabberSet} is set to true and the
	 * grabber will begin waiting for a ball to be caught.
	 * </p>
	 * 
	 * @param speed
	 */
	@Deprecated
	public void setSpeeds(double speed)
	{

		if(previousRPM - CUT_POWER_RPM_DROP < ((BadCAN) left).getRpm() && grabberSet == true)
		{
			grabbed = true;
			left.set(0);
			right.set(0);
		}

		// Stops motors if caught and motors still moving.
		if(grabbed && previousRPM > 0 && grabberSet)
		{
			left.set(0);
			right.set(0);
		}
		else if(grabbed && previousRPM <= 0 && grabberSet) // Resets catching system
		{
			grabbed = false;
			grabberSet = false;
		}
		else
		// Manual control of shooter
		{
			shoot(speed);

			// If grabber starts moving the grabberSet is enabled
			if(speed <= 0)
			{
				grabberSet = false;
			}
			else
			{
				grabberSet = true;
			}
		}
		previousRPM = ((BadCAN) left).getRpm();
	}

	// public boolean returnLimitValue()
	// {
	// return !limitSwitch.get();
	// }

	/**
	 * Sets grabberSet to true and continues the ball grabbing protocol detailed in {@code setSpeed}
	 * . Uses {@code grabSpeed} as the speed.
	 */
	@Deprecated
	public void grabBall(double speed)
	{
		grabberSet = true;
		if(previousRPM - CUT_POWER_RPM_DROP > ((BadCAN) left).getRpm())
		{
			grabbed = true;
			for(int i = 0; i < 1000; i++)
			{
				left.set(0);
				right.set(0);
			}
		}
		if(grabbed && previousRPM > 0)
		{
			left.set(0);
			right.set(0);
		}
		else if(grabbed && previousRPM <= 0)
		{
			grabbed = false;
			grabberSet = false;
		}
		else
		{
			shoot(speed);
		}
		previousRPM = ((BadCAN) left).getRpm();

	}

	/**
	 * Easy way to access the shooting RPM.
	 * 
	 * @return - the RPM of the shooting motors
	 */
	public double getShootingRPM()
	{
		return -((BadCAN) left).getRpm();
	}

	public double getRotatingRPM()
	{
		return 0;
		// return ((BadCAN) rotator).getRpm();
	}

	/**
	 * Sets the speed of the articulator and allows the shooter to rotate back and forth.
	 * 
	 * @param speed
	 *            - the speed at which the shooter articulates
	 */
	public void rotate(double speed)
	{
		// if(returnLimitValue() && speed > 0)
		// {
		// rotator.set(0);
		// ((BadCAN) rotator).encoder.reset();
		// }
		// else
		// {
		// rotator.set(speed);
		// }
		rotator.set(speed);
		this.detectedTape = this.pingOpticalSensor();
	}

	public boolean rotateTo(double position)
	{
		double difference = ((BadCAN) rotator).getDistance() - position;
		double rotateSpeed = 0;

		Logger.log("Rotating difference:", "" + difference);

		if(Math.abs(difference) > 1)
		{
			rotateSpeed = PID.trigScale(difference, SHOOTER_HIGHEST_POS, SHOOTER_LOWEST_POS, .6);

			if(Math.abs(rotateSpeed) > .5)
				rotateSpeed = .5 * rotateSpeed / Math.abs(rotateSpeed);
			if(Math.abs(rotateSpeed) < .3)
				rotateSpeed = .3 * rotateSpeed / Math.abs(rotateSpeed);
		}
		else
		{
			return true;
		}

		rotate(rotateSpeed);
		return false;
	}

	/**
	 * This method moves the shooter to the nearest level below its current position.
	 */
	public void moveToLowerRetroTape()
	{
		if(this.pingOpticalSensor() && this.detectedTape)
		{
			this.rotate(-0.5);
		}
		else if(this.pingOpticalSensor() && !this.detectedTape)
		{
			this.rotate(0.0);
		}
		else
		{
			this.rotate(-0.5);
		}
//		if(lastOpticalValue)
//		{
//			if(pingOpticalSensor())
//			{
//				// rotate down quickly if the sensor still sees the tape
//				rotate(-.4);
//			}
//			else
//			{
//				if(pingOpticalSensor())
//				{
//					// kill it
//					rotate(0);
//				}
//				else
//				{
//					// keep rotating because it isn't there yet
//					rotate(-.6);
//				}
//			}
//		}
//		else
//		{
//			if(pingOpticalSensor())
//			{
//				rotate(0);
//			}
//			else
//			{
//				rotate(-.4);
//			}
//		}
	}
	
	/**
	 * This is the same thing as above, but goes up.
	 */
	public void moveToHigherRetroTape()
	{
		if(this.pingOpticalSensor() && this.detectedTape)
		{
			this.rotate(-0.5);
		}
		else if(this.pingOpticalSensor() && !this.detectedTape)
		{
			this.rotate(0.0);
		}
		else
		{
			this.rotate(-0.5);
		}
		
//		if(lastOpticalValue)
//		{
//			if(pingOpticalSensor())
//			{
//				// rotate up quickly if the sensor still sees the tape
//				rotate(.6);
//			}
//			else
//			{
//				if(pingOpticalSensor())
//				{
//					// kill it because we found it
//					rotate(0);
//				}
//				else
//				{
//					// keep rotating because it isn't there yet
//					rotate(.4);
//				}
//			}
//		}
//		else
//		{
//			if(pingOpticalSensor())
//			{
//				rotate(0);
//			}
//			else
//			{
//				rotate(.4);
//			}
//		}
//		
//		lastOpticalValue = pingOpticalSensor();
	}

	public void resetEncoders()
	{
		((BadCAN) rotator).encoder.reset();
	}

	public boolean pingOpticalSensor()
	{
		//Logger.logOnce("Optical Sensor: " + opticalSensor.get());
		return !opticalSensor.get();
	}

	public double getHighestPosWithOffset()
	{
		return(SHOOTER_HIGHEST_POS + shooterOffset);
	}

	public double getLowestPosWithOffset()
	{
		return(SHOOTER_LOWEST_POS + shooterOffset);
	}

	public double getDefaultPosWithOffset()
	{
		return(SHOOTER_DEFAULT_SHOOTING_POS + shooterOffset);
	}

	/**
	 * Sets the speed of the shooters. Automatically inverts the proper motor to keep them moving in
	 * the same direction.
	 * 
	 * @param speed
	 *            to set the shooter to
	 */
	public void shoot(double speed)
	{
		left.set(-speed);
		right.set(speed);

		// set lights to gather or shoot mode
		if(CommandBase.lights != null)
		{
			if(speed < 0)
				CommandBase.lights.setLights(LEDState.kGATHER);
			else CommandBase.lights.setLights(LEDState.kSHOOT);
		}
	}

	/**
	 * Sets the grabber speed, the exact opposite of {@code shoot()}.
	 * Deprecated, use {@code shoot()} instead with a negative number.
	 * 
	 * @param speed - the speed at which to grab the ball
	 * @deprecated
	 */
	public void grab(double speed)
	{
		shoot(-speed); // Reusing methods to prevent code repetition

	}

	/**
	 * Turns the ring light on. This method is for use while the light is wired to a speed
	 * controller.
	 */
	public void ringLightOn()
	{
		ringLight.set(Relay.Value.kForward);
	}

	/**
	 * Turns the ring light off. This method is only for use while the light is wired to a speed
	 * controller.
	 */
	public void ringLightOff()
	{
		ringLight.set(Relay.Value.kOff);
	}

	/**
	 * Sets the location of the servo motor. If {@code servoPos} is true the value is set to
	 * {@code SERVO_EXTENDED_POS}. If it is false it is set to {@code SERVO_STANDARD_POS}.
	 * 
	 * @param servoPos
	 *            value to set servo
	 */
	public void driveServo(boolean servoPos)
	{
		if(servoPos)
			pusher.set(SERVO_EXTENDED_POS);
		else pusher.set(SERVO_STANDARD_POS);
	}

	@Override
	public String getConsoleIdentity()
	{
		return "Shooter_And_Grabber";
	}

	@Override
	protected void initDefaultCommand()
	{

	}

	@Override
	public void pidWrite(double arg0)
	{
		left.set(arg0);
		right.set(arg0);
	}

	@Override
	public PIDSourceType getPIDSourceType()
	{
		return null;
	}

	@Override
	public double pidGet()
	{
		return ((BadCAN) right).getRpm();
	}

	@Override
	public void setPIDSourceType(PIDSourceType arg0)
	{

	}

}
