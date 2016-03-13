package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.sensors.BadCAN;
import org.usfirst.frc.team1014.robot.subsystems.ShooterAndGrabber;
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
	private static final double ROTATION_SPEED_MULTIPLIER = 1d / 3d;
	private static final double SHOOTER_SPEED_ADJUST_INTERVAL = .1;
	private static final double MAX_SHOOTER_SPEED = 1.0;
	private static final double MIN_SHOOTER_SPEED = .5;

	private boolean isServoOut = false;
	private boolean ringLightOn = false;
	private boolean ringLightButtonPressed = false;

	public UseShooter()
	{
		requires((Subsystem) shooter);
	}

	protected void initialize()
	{
		shooter.shoot(0.0);
		shooter.rotate(0.0);
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
		if(shooter.limitSwitch.get())
		{
			//ShooterAndGrabber.shooterOffset = ((BadCAN) shooter.rotator).encoder.getDistance();
			shooter.resetEncoders();
		}
		// grabbing balls with speed moderation
		if(ControlsManager.secondaryXboxController.isRBButtonPressedPrimaryLayout())
		{
			shooter.grabBall();
		}
		else
		{
			shooter.setSpeeds(ControlsManager.secondaryXboxController.getLeftStickYPrimaryLayout());
		}
		shooter.shoot(-ControlsManager.secondaryXboxController.getLeftStickYPrimaryLayout());

		// servo control
		if(ControlsManager.secondaryXboxController.isAButtonPressedPrimaryLayout())
			isServoOut = true;
		else isServoOut = false;
		shooter.driveServo(isServoOut);

		// Rotate shooter with left joystick Y & Divide by double to prevent truncating value to 0
		shooter.rotate(ControlsManager.secondaryXboxController.getRightStickYPrimaryLayout() * ROTATION_SPEED_MULTIPLIER);
		
		Logger.logThis("Rotator Encoder: " + ((BadCAN) shooter.rotator).encoder.getDistance());

		// move to preset heights
		if(ControlsManager.secondaryXboxController.isXButtonPressedPrimaryLayout())
		{
			shooter.rotateTo(ShooterAndGrabber.SHOOTER_LOWEST_POS);
		}
		else if(ControlsManager.secondaryXboxController.isBButtonPressedPrimaryLayout())
		{
			shooter.rotateTo(ShooterAndGrabber.SHOOTER_HIGHEST_POS);
		}

		// switch layouts
		if(ControlsManager.secondaryXboxController.getLeftTriggerPrimaryLayout() > .5 || ControlsManager.secondaryXboxController.getLeftTriggerSecondaryLayout() > .5)
			ControlsManager.changeToSecondaryLayout(2);
		else ControlsManager.changeToPrimaryLayout(2);

		// Direct control of ring light
		if(ControlsManager.secondaryXboxController.isStartButtonPressedPrimaryLayout() && !this.ringLightButtonPressed)
		{
			if(!this.ringLightOn)
				shooter.ringLightOn();
			else shooter.ringLightOff();
			this.ringLightOn = !this.ringLightOn;
			this.ringLightButtonPressed = true;
		}
		else if(!ControlsManager.secondaryXboxController.isStartButtonPressedPrimaryLayout())
		{
			this.ringLightButtonPressed = false;
		}
		Logger.logThis("LIMIT_SWITCH: ------------------------- " + shooter.limitSwitch.get());

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
