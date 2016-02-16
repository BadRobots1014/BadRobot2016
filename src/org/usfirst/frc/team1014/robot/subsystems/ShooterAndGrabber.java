package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.sensors.BadTalon;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/**
 * A {@link BadSubsystem} that controls the Shooter and Grabber.
 */
public class ShooterAndGrabber extends BadSubsystem implements PIDSource, PIDOutput
{
	
	private static final double SERVO_STANDARD_POS = 0.9;

	private static final double SERVO_EXTENDED_POS = .25;

	private static final double RING_LIGHT_ON_VALUE = .5;

	public static ShooterAndGrabber instance;
	private BadTalon left;
	public SpeedController right, rotator;
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

		left = new BadTalon(ControlsManager.SHOOTER_LEFT, ControlsManager.SHOOTER_ENCODER_A, ControlsManager.SHOOTER_ENCODER_B);
		right = new Talon(ControlsManager.SHOOTER_RIGHT);
		rotator = new Talon(ControlsManager.SHOOTER_ROTATE);
		ringLight = new Talon(ControlsManager.RING_LIGHT);
		pusher = new Servo(ControlsManager.PUSHER);
		pusher.set(0);
	}

	/**
	 * Sets the shooter speeds if user has control.
	 * <br />
	 * <p>
	 * More accurately if the grabber is enabled it looks for
	 * the point where the ball is caught and the RPM is lowered
	 * by a value greater than {@code BALL_CATCH_RPM_DECREASE}.
	 * When this is reached the {@code grabbed} boolean is set to
	 * true and the motors continue decreasing speed until they get
	 * to {@literal 0}. When the motors do fully stop {@code grabbed}
	 * is set to false and the grabber is reverted to user control
	 * mode.
	 * <br /><br />
	 * When the grabber is above the value of zero the {@code grabberSet}
	 * is set to true and the grabber will begin waiting for a ball to be
	 * caught.
	 * </p>
	 * @param speed
	 */
	public void setSpeeds(double speed)
	{
		// Catches point where motor speed goes down and ball is caught
		if(previousRPM - rpmDrop > ((BadTalon) left).getRpm() && grabberSet)
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
		else if(grabbed && previousRPM <= 0 && grabberSet)  // Resets catching system
		{
			grabbed = false;
			grabberSet = false;
		}
		else // Manual control of shooter
		{
			shoot(speed);
			
			// If grabber starts moving the grabberSet is enabled
			if(speed <= 0)
				grabberSet = false;
			else
				grabberSet = true;
		}
		previousRPM = ((BadTalon) left).getRpm();
	}

	/**
	 * Sets grabberSet to true and continues the ball grabbing
	 * protocol detailed in {@code setSpeed}. Uses {@code grabSpeed}
	 * as the speed.
	 */
	public void grabBall()
	{
		grabberSet = true;
		if(previousRPM - rpmDrop > ((BadTalon) left).getRpm())
		{
			grabbed = true;
			left.set(0);
			right.set(0);
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
		previousRPM = ((BadTalon) left).getRpm();

	}

	
	/**
	 * @return the current RPM of the left motor
	 */
	public double getShootingRPM()
	{
		return ((BadTalon) left).getRpm();
	}

	/**
	 * @param speed the speed to set the rotation motor to
	 */
	public void rotate(double speed)
	{
		rotator.set(speed);
	}

	/**
	 * Sets the speed of the shooters.
	 * Automatically inverts the proper motor to
	 * keep them moving in the same direction.
	 * @param speed to set the shooter to
	 */
	public void shoot(double speed)
	{
		left.set(speed);
		right.set(-speed);
	}

	/**
	 * Sets the grabber speed, the
	 * exact opposite of {@code} shoot()}.
	 * @param speed
	 */
	public void grab(double speed)
	{
		shoot(-speed); // Reusing methods to prevent code repetition
	}

	/**
	 * Sets the ring light to its on value.
	 */
	public void ringLightOn()
	{
		ringLight.set(RING_LIGHT_ON_VALUE);
	}

	/**
	 * Sets the ring light to its off value.
	 */
	public void ringLightOff()
	{
		ringLight.set(0);
	}

	/**
	 * Sets the location of the servo motor.
	 * If {@code servoPos} is true the value
	 * is set to {@code SERVO_EXTENDED_POS}.
	 * If it is false it is set to {@code SERVO_STANDARD_POS}.
	 * @param servoPos value to set servo
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
		return ((BadTalon) left).getRpm();
	}

	@Override
	public void setPIDSourceType(PIDSourceType arg0)
	{

	}
}