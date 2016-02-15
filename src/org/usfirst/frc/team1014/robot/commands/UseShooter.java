package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * This class defines how the robot shooter will work in teleop.
 *
 */
public class UseShooter extends CommandBase
{
	private static final double ROTATION_SPEED_MULTIPLYER = 1d / 3d; // Value to multiply rotation value by to decrease sensitivity
	private static final double SERVO_PUSH_POS = .65;
	private static final double SERVO_DEFAULT_POS = .25;
	private static final double SHOOTER_SPEED_ADJUST_INTERVAL = .1;
	private static final double MAX_SHOOTER_SPEED = 1.0;
	private static final double MIN_SHOOTER_SPEED = .5;
	boolean usingShooter;
	double maxSpeed;
	double servoPos;

	public UseShooter()
	{
		requires((Subsystem) shooter);
	}

	protected void initialize()
	{
		usingShooter = false;
		maxSpeed = .5;
		servoPos = .5;

		shooter.shoot(0.0);
		shooter.rotate(0.0);
	}

	@Override
	public String getConsoleIdentity()
	{
		return "UseShooter";
	}
	
	/**
	 * when X is pressed, decreases speed. 
	 * when B is pressed, increases speed. 
	 * when RB is pressed, grabs ball
	 * else sets speed with right stick's y axis
	 * servo's position is moved to shoot ball when A is pressed
	 * else in original position
	 * LB turns light on, LT turns light off 
	 */
	@Override
	protected void execute()
	{
		// Adjust shooter max speed within min and max values
		if(ControlsManager.secondaryXboxController.isBButtonPressed() || ControlsManager.secondaryXboxController.isXButtonPressed())
		{
			if(ControlsManager.secondaryXboxController.isXButtonPressed() && maxSpeed > MIN_SHOOTER_SPEED)
				maxSpeed -= SHOOTER_SPEED_ADJUST_INTERVAL;
			else if(ControlsManager.secondaryXboxController.isBButtonPressed() && maxSpeed < MAX_SHOOTER_SPEED)
				maxSpeed += SHOOTER_SPEED_ADJUST_INTERVAL;
		}
		
		
		if(ControlsManager.secondaryXboxController.isRBButtonPressed())
		{
			// If RB button is pressed grab ball
			shooter.grabBall();
		}
		else
		{
			// If RB not pressed set shooter to position of right joystick Y
			shooter.setSpeeds(ControlsManager.secondaryXboxController.getRightStickY());
		}

		// Push servo out if A is pressed
		if(ControlsManager.secondaryXboxController.isAButtonPressed())
		{
			servoPos = SERVO_PUSH_POS;
		}
		else
		{
			servoPos = SERVO_DEFAULT_POS;
		}

		// Rotate shooter with left joystick Y
		shooter.rotate(ControlsManager.secondaryXboxController.getLeftStickY() * ROTATION_SPEED_MULTIPLYER); //Divide by double to prevent truncating value to 0

		// Direct control of ring light
		if(ControlsManager.secondaryXboxController.isLBButtonPressed())
		{
			shooter.ringLightOn();
		}
		if(ControlsManager.secondaryXboxController.getLeftTrigger() > 0.5f)
		{
			shooter.ringLightOff();
		}

		// Logger.logThis("Encoder RPM: " + shooter.getShootingRPM());

	}

	@Override
	protected boolean isFinished()
	{
		return false;
	}

	/**
	 * when finished, shooter is set back to neutral
	 */
	@Override
	protected void end()
	{
		shooter.shoot(0.0);
		shooter.rotate(0.0);
	}

	/**
	 * Called when another command requires the same subsystem or {@code cancel()} is called.
	 * Cleans up dependencies and logs the interrupt.
	 */
	@Override
	protected void interrupted()
	{
		Logger.logThis(getConsoleIdentity() + ": I've been interrupted!");
		end();
	}

}
