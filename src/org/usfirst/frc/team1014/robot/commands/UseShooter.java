package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.Robot;
import org.usfirst.frc.team1014.robot.controls.ControllerLayout;
import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.subsystems.ShooterAndGrabber;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class defines how the robot shooter will work in teleop.
 * 
 * @author Manu S.
 * 
 */
public class UseShooter extends CommandBase
{
	// Value to multiply rotation value by to decrease sensitivity
	private static final double ROTATION_SPEED_MULTIPLIER = .7;

	// private static final double SHOOTER_SPEED_ADJUST_INTERVAL = .1;
	// private static final double MAX_SHOOTER_SPEED = 1.0;
	// private static final double MIN_SHOOTER_SPEED = .5;

	private boolean isServoOut = false;

	private boolean ringLightOn = false;
	private boolean ringLightButtonPressed = false;

	private boolean isAutoShooting = false;
	private double startShootTime = 0;

	private boolean shooterUp = false;
	private boolean shooterDown = false;

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
		return "Use_Shooter";
	}

	@Override
	protected void execute()
	{
		// servo control
		isServoOut = ControlsManager.shooter.getButtonValue(1, ControllerLayout.servo);
		shooter.driveServo(isServoOut);

		// Rotate shooter with left joystick Y & Divide by double to prevent truncating value to 0
		if(Math.abs(ControlsManager.shooter.getStickValue(1, ControllerLayout.articulator) * ROTATION_SPEED_MULTIPLIER) > .15)
		{
			shooterUp = false;
			shooterDown = false;
			shooter.rotate(ControlsManager.shooter.getStickValue(1, ControllerLayout.articulator) * ROTATION_SPEED_MULTIPLIER);
		}
		else
		{
			if(ControlsManager.shooter.getButtonValue(1, ControllerLayout.articulatorUp))
			{
				shooterUp = true;
				shooterDown = false;
			}
			else if(ControlsManager.shooter.getButtonValue(1, ControllerLayout.articulatorDown))
			{
				shooterDown = true;
				shooterUp = false;
			}
			else if(!shooterDown && !shooterUp)
			{
				shooter.rotate(0.0);
			}
		}

		// Auto grabber, Auto shooter and manual shooter
		if(ControlsManager.shooter.getButtonValue(1, ControllerLayout.autoGrab))
		{
			startShootTime = 0;
			isAutoShooting = false;
			shooter.shoot(ShooterAndGrabber.DEFAULT_GRAB_SPEED);
		}
		else if(ControlsManager.shooter.getButtonValue(1, ControllerLayout.autoShoot))
		{
			startShootTime = Utility.getFPGATime();
			isAutoShooting = true;
		}
		else if(Math.abs(ControlsManager.shooter.getStickValue(1, ControllerLayout.shooter)) > 0)
		{
			startShootTime = 0;
			isAutoShooting = false;
			shooter.shoot(-ControlsManager.shooter.getStickValue(1, ControllerLayout.shooter));
		}
		else
		{
			if(!this.isAutoShooting)
				shooter.shoot(0);
		}

		// move the shooter to the appropriate spot
		if(shooterUp)
			shooterUp = !shooter.moveToHigherRetroTape();
		else if(shooterDown)
			shooterDown = !shooter.moveToLowerRetroTape();

		// autonomous shooting for 3 seconds and release within 1
		if(isAutoShooting)
		{
			shooter.shoot(0.85);
			if(Utility.getFPGATime() - startShootTime > 3 * 1000000)
			{
				shooter.shoot(0);
				shooter.driveServo(false);
				isAutoShooting = false;
			}
			else if(Utility.getFPGATime() - startShootTime > 1 * 1000000)
			{
				shooter.driveServo(true);
			}
		}

		// switch layouts
		if(ControlsManager.shooter.getLayoutChange())
			ControlsManager.changeToSecondaryLayout(2);
		else
			ControlsManager.changeToPrimaryLayout(2);

		// Direct control of ring light
		if(ControlsManager.shooter.getButtonValue(1, ControllerLayout.ringLight) && !this.ringLightButtonPressed)
		{
			if(!this.ringLightOn)
				// LEDLights.getInstance().pulse();
				shooter.ringLightOn();
			else
				shooter.ringLightOff();

			this.ringLightOn = !this.ringLightOn;
			this.ringLightButtonPressed = true;
		}
		else if(!ControlsManager.shooter.getButtonValue(1, ControllerLayout.ringLight))
		{
			this.ringLightButtonPressed = false;
		}

		// updates the retroreflective boolean
		shooter.detectedTape = shooter.pingOpticalSensor();

		if(ControlsManager.shooter.getButtonValue(1, ControllerLayout.underVoltClear) || ControlsManager.shooter.getButtonValue(2, ControllerLayout.underVoltClear))
			Robot.lowVoltage = false;
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
}