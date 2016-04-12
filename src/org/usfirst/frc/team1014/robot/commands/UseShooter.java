package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.subsystems.ShooterAndGrabber;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

//github.com/BadRobots1014/BadRobot2016.git

/**
 * 
 * This class defines how the robot shooter will work in teleop.
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

	// private boolean shooterUpRunning = false;
	// private double startShooterUpTime = 0;
	// private double shooterUpRunLength = 0.4 * 1000000;
	// private boolean shooterDownRunning = false;
	// private double startShooterDownTime = 0;
	// private double shooterDownRunLength = 1.6 * 1000000;

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

	/**
	 * when X is pressed, decreases speed. when B is pressed, increases speed. when RB is pressed,
	 * grabs ball else sets speed with right stick's y axis servo's position is moved to shoot ball
	 * when A is pressed else in original position LB turns light on, LT turns light off
	 */
	@Override
	protected void execute()
	{
		// servo control
		isServoOut = ControlsManager.shooter.getAdjustLeft_Servo(1);
		shooter.driveServo(isServoOut);

		// Rotate shooter with left joystick Y & Divide by double to prevent truncating value to 0
		if(Math.abs(ControlsManager.shooter.getRightDrive_Articulator(1) * ROTATION_SPEED_MULTIPLIER) > .15)
		{
			shooterUp = false;
			shooterDown = false;
			shooter.rotate(ControlsManager.shooter.getRightDrive_Articulator(1) * ROTATION_SPEED_MULTIPLIER);
		}
		else
		{
			if(ControlsManager.shooter.getAdjustBackward_ArticulatorUp(1))
			{
				shooterUp = true;
				shooterDown = false;
			}
			else if(ControlsManager.shooter.getAdjustForward_ArticulatorDown(1))
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
		if(ControlsManager.shooter.get_AutoGrab(1))
		{
			startShootTime = 0;
			isAutoShooting = false;
			shooter.shoot(ShooterAndGrabber.DEFAULT_GRAB_SPEED);
		}
		else if(ControlsManager.shooter.getAdjustRight_AutoShoot(1))
		{
			startShootTime = Utility.getFPGATime();
			isAutoShooting = true;
		}
		else if(Math.abs(ControlsManager.shooter.getLeftDrive_Shooter(1)) > 0)
		{
			startShootTime = 0;
			isAutoShooting = false;
			shooter.shoot(-ControlsManager.shooter.getLeftDrive_Shooter(1));
		}
		else
		{
			if(!this.isAutoShooting)
				shooter.shoot(0);
		}

		if(shooterUp)
			shooterUp = !shooter.moveToHigherRetroTape();
		else if(shooterDown)
			shooterDown = !shooter.moveToLowerRetroTape();

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
		if(ControlsManager.shooter.get_RingLight(1) && !this.ringLightButtonPressed)
		{
			if(!this.ringLightOn)
				shooter.ringLightOn();
			else
				shooter.ringLightOff();
			this.ringLightOn = !this.ringLightOn;
			this.ringLightButtonPressed = true;
		}
		else if(!ControlsManager.shooter.get_RingLight(1))
		{
			this.ringLightButtonPressed = false;
		}

		shooter.detectedTape = shooter.pingOpticalSensor();
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