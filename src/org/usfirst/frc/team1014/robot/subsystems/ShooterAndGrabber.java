package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.sensors.BadCAN;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/**
 * A {@link BadSubsystem} that controls the Shooter and Grabber.
 * 
 * @author - Manu S.
 */
public class ShooterAndGrabber extends BadSubsystem implements PIDSource, PIDOutput
{

	private static final double SERVO_STANDARD_POS = 0.25;
	private static final double SERVO_EXTENDED_POS = 0.9;
	private static final double RING_LIGHT_ON_VALUE = .5;

	public static ShooterAndGrabber instance;
	private SpeedController left, right;
	public SpeedController rotator;
	private SpeedController ringLight;
	public Servo pusher;
	public boolean grabberSet = false;
	private double previousRPM = 0;
	private boolean grabbed = false;
	private double grabSpeed = 0.5;
	private double rpmDrop = 400;

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
		right = new BadCAN(ControlsManager.SHOOTER_RIGHT, ControlsManager.SHOOTER_RIGHT_ENCODER_A, ControlsManager.SHOOTER_RIGHT_ENCODER_B);
		rotator = new BadCAN(ControlsManager.SHOOTER_ROTATE, ControlsManager.ARTICULATOR_ENCODER_A, ControlsManager.ARTICULATOR_ENCODER_B);

		ringLight = new Talon(ControlsManager.RING_LIGHT);
		pusher = new Servo(ControlsManager.PUSHER);
		pusher.set(0);
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
	public void setSpeeds(double speed)
	{
		if(previousRPM - rpmDrop > ((BadCAN) right).getRpm() && grabberSet == true)
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
		previousRPM = ((BadCAN) right).getRpm();
	}

	/**
	 * Sets grabberSet to true and continues the ball grabbing protocol detailed in {@code setSpeed}
	 * . Uses {@code grabSpeed} as the speed.
	 */
	public void grabBall()
	{
		grabberSet = true;
		if(previousRPM - rpmDrop > ((BadCAN) right).getRpm())
		{
			grabbed = true;
			for(int i = 0; i < 1000; i ++)
			{
				Logger.logThis("cutting power");
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
			shoot(grabSpeed);
		}
		previousRPM = ((BadCAN) right).getRpm();

	}

	/**
	 * Easy way to access the shooting RPM.
	 * 
	 * @return - the RPM of the shooting motors
	 */
	public double getShootingRPM()
	{
		return -((BadCAN) right).getRpm();
	}

	/**
	 * Sets the speed of the articulator and allows the shooter to rotate back and forth.
	 * 
	 * @param speed
	 *            - the speed at which the shooter articulates
	 */
	public void rotate(double speed)
	{
		rotator.set(speed);
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
		left.set(speed);
		right.set(-speed);
	}

	/**
	 * Sets the grabber speed, the exact opposite of {@code} shoot()}.
	 * 
	 * @param speed
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
		ringLight.set(RING_LIGHT_ON_VALUE);
	}

	/**
	 * Turns the ring light off. This method is only for use while the light is wired to a speed
	 * controller.
	 */
	public void ringLightOff()
	{
		ringLight.set(0);
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
		else
			pusher.set(SERVO_STANDARD_POS);
	}

	@Override
	public String getConsoleIdentity()
	{
		return "ShooterAndGrabber";
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
