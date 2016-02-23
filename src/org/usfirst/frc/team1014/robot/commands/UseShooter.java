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
	// Value to multiply rotation value by to decrease sensitivity
	private static final double ROTATION_SPEED_MULTIPLIER = 1d / 4d;
	private static final double SHOOTER_SPEED_ADJUST_INTERVAL = .1;
	private static final double MAX_SHOOTER_SPEED = 1.0;
	private static final double MIN_SHOOTER_SPEED = .5;

	private double maxSpeed;
	private boolean isServoOut = false;
	private boolean ringLightOn = false;
	private boolean ringLightButtonPressed = false;

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
		 if(ControlsManager.secondaryXboxController.isRBButtonPressed())
		 {
			 shooter.grabBall();
			 Logger.logThis("Shooter RPM: " + shooter.getShootingRPM());
		 }
		 else {
			 shooter.setSpeeds(ControlsManager.secondaryXboxController.getLeftStickY()); 
		 }
		shooter.shoot(ControlsManager.secondaryXboxController.getLeftStickY());

		if(ControlsManager.secondaryXboxController.isAButtonPressed())
			isServoOut = true;
		else
			isServoOut = false;
		shooter.driveServo(isServoOut);

		// Rotate shooter with left joystick Y & Divide by double to prevent truncating value to 0
		shooter.rotate(ControlsManager.secondaryXboxController.getRightStickY() * ROTATION_SPEED_MULTIPLIER);

		// Direct control of ring light
		if(ControlsManager.secondaryXboxController.isStartButtonPressed() && !this.ringLightButtonPressed)
		{
			if(!this.ringLightOn)
				shooter.ringLightOn();
			else
				shooter.ringLightOff();
			this.ringLightOn = !this.ringLightOn;
			this.ringLightButtonPressed = true;
		}
		else if(!ControlsManager.secondaryXboxController.isStartButtonPressed())
		{
			this.ringLightButtonPressed = false;
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
	 * Called when another command requires the same subsystem or {@code cancel()} is called. Cleans
	 * up dependencies and logs the interrupt.
	 */
	@Override
	protected void interrupted()
	{
		Logger.logThis(getConsoleIdentity() + ": I've been interrupted!");
		end();
	}
}
