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
 * This class is what defines the shooter (and grabber) subsystem and gives it a host of useful
 * methods it needs to perform its tasks.
 * 
 * @author Manu S.
 *
 */
public class ShooterAndGrabber extends BadSubsystem implements PIDSource, PIDOutput
{
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
	 * Sets the speed of the shooting motors such that they lose power if the RPM drops too low.
	 * 
	 * @param speed
	 *            - the raw speed that is being fed into the shooting motors
	 */
	public void setSpeeds(double speed)
	{
		if(previousRPM - rpmDrop > ((BadCAN) right).getRpm() && grabberSet == true)
		{
			grabbed = true;
			left.set(0);
			right.set(0);
		}
		if(grabbed && previousRPM > 0 && grabberSet)
		{
			left.set(0);
			right.set(0);
		}
		else if(grabbed && previousRPM <= 0 && grabberSet)
		{
			grabbed = false;
			grabberSet = false;
		}
		else
		{
			left.set(speed);
			right.set(-speed);
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
	 * This method runs the shooting motors in reverse so that the robot can grab the ball and cut
	 * power once it is in.
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
			left.set(grabSpeed);
			right.set(-grabSpeed);
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
		return ((BadCAN) right).getRpm();
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
	 * This shoots the ball with a certain speed.
	 * 
	 * @param speed
	 *            - the speed at which to shoot the ball
	 */
	public void shoot(double speed)
	{
		left.set(speed);
		right.set(-speed);
	}

	/**
	 * This grabs the ball with a certain speed.
	 * 
	 * @param speed
	 *            - the speed at which to suck the ball in
	 */
	public void grab(double speed)
	{
		left.set(-speed);
		right.set(speed);
	}

	/**
	 * Turns the ring light on. This method is for use while the light is wired to a speed
	 * controller.
	 */
	public void ringLightOn()
	{
		ringLight.set(.5);
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
	 * Moves the servo to a predefined position (in or out).
	 * 
	 * @param servoPos
	 *            - should the servo be pushed forward or not. True pushes it out and false pulls it
	 *            back in.
	 */
	public void driveServo(boolean servoPos)
	{
		if(servoPos)
		{
			pusher.set(.9);
		}
		else
		{
			pusher.set(.25);
		}
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

	}

}