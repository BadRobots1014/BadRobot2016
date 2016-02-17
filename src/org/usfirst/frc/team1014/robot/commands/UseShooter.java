package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

public class UseShooter extends CommandBase
{
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
		if(ControlsManager.secondaryXboxController.isBButtonPressed() || ControlsManager.secondaryXboxController.isXButtonPressed())
		{
			if(ControlsManager.secondaryXboxController.isXButtonPressed() && maxSpeed > .5)
				maxSpeed -= .1;
			else if(ControlsManager.secondaryXboxController.isBButtonPressed() && maxSpeed < 1.0)
				maxSpeed += .1;
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

		shooter.rotate(ControlsManager.secondaryXboxController.getLeftStickY() / 3);
		if(ControlsManager.secondaryXboxController.isLBButtonPressed())
		{
			shooter.ringLightOn();
		}
		if(ControlsManager.secondaryXboxController.getLeftTrigger() > 0.5f)
		{
			shooter.ringLightOff();
		}

	}

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

	@Override
	protected void interrupted()
	{
		Logger.logThis(getConsoleIdentity() + ": I've been interrupted!");
	}

}
