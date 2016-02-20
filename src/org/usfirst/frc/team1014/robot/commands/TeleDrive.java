package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class defines how the robot drives through teleop.
 * 
 * @author Manu S.
 * 
 */
public class TeleDrive extends CommandBase
{
	public double targetGyro;
	public boolean gyroSet;

	public TeleDrive()
	{
		requires((Subsystem) driveTrain);
		targetGyro = 0;
		gyroSet = false;
	}

	/**
	 * This method runs before the command is executed to make sure everything is ready for it to be
	 * executed.
	 */
	@Override
	protected void initialize()
	{
		driveTrain.tankDrive(0, 0);
		driveTrain.resetMXPAngle();
	}

	/**
	 * This is really useless and doesn't really have much function, other than when we want to log
	 * things.
	 */
	@Override
	public String getConsoleIdentity()
	{
		return "TeleDrive";
	}

	/**
	 * This is the method that gets called over and over again while the command is actually
	 * running.
	 */
	@Override
	protected void execute()
	{

		if(ControlsManager.primaryXboxController.isLBButtonPressed())
		{
			if(!gyroSet)
			{
				targetGyro = driveTrain.getAngle();
				gyroSet = true;
			}
			driveTrain.driveStraight(ControlsManager.primaryXboxController.getLeftStickY(), targetGyro);
			Logger.logThis("Correcting orientation");
		}

		if(ControlsManager.primaryXboxController.getLeftTrigger() < -.5)
		{
			driveTrain.setSpeed(-ControlsManager.primaryXboxController.getLeftStickY(), -ControlsManager.primaryXboxController.getRightStickY());
			gyroSet = false;
		}
		else
		{
			driveTrain.tankDrive(ControlsManager.primaryXboxController.getRightStickY(), ControlsManager.primaryXboxController.getLeftStickY());
			gyroSet = false;
		}
	}

	/**
	 * Lets the system know when to stop this command and do some other one.
	 */
	@Override
	protected boolean isFinished()
	{
		return false;
	}

	/**
	 * What the robot should do once the command has finished executing.
	 */
	@Override
	protected void end()
	{
		driveTrain.tankDrive(0, 0);
	}

	/**
	 * Not sure what this is used for.
	 */
	@Override
	protected void interrupted()
	{
		org.usfirst.frc.team1014.robot.utilities.Logger.logThis(getConsoleIdentity() + " I've been interrupted!");
	}
}
