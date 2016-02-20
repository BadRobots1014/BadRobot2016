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
	private static final double SHOOTER_SPEED_ADJUST_INTERVAL = .1;
	private static final double MAX_SHOOTER_SPEED = 1.0;
	private static final double MIN_SHOOTER_SPEED = .5;
	
	double maxSpeed;
	boolean stillPressed = false;
	boolean isServoOut = false;

	public UseShooter()
	{
		requires((Subsystem) shooter);
	}

	protected void initialize()
	{
		maxSpeed = .5;
		shooter.shoot(0.0);
		shooter.rotate(0.0);
		Logger.logThis("new shooter init");
		shooter.driveServo(isServoOut);
	}

	@Override
	public String getConsoleIdentity()
	{
		return "UseShooter";
	}

	/**
	 * when X is pressed, decreases speed. when B is pressed, increases speed. when RB is pressed,
	 * grabs ball else sets speed with right stick's y axis servo's position is moved to shoot ball
	 * when A is pressed else in original position LB turns light on, LT turns light off
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
			shooter.grabBall();
		}
		else
		{
			shooter.setSpeeds(ControlsManager.secondaryXboxController.getRightStickY());
		}
		
		if(ControlsManager.secondaryXboxController.isAButtonPressed())
			isServoOut = true;
		else
			isServoOut = false;
		
		shooter.driveServo(isServoOut);

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

	}

	/**
	 * @param speed
	 * @return the speed multiplied by {@code maxSpeed}
	 */
	public double scaleSpeed(double speed)
	{
		return speed * maxSpeed;
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
