package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;

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
	 * @return the name of the class.
	 */
	@Override
	public String getConsoleIdentity()
	{
		return "Tele-Drive";
	}

	/**
	 * This is the method that gets called over and over again while the command is actually
	 * running.
	 */
	@Override
	protected void execute()
	{
		if(ControlsManager.primaryXboxController.isLBButtonPressedPrimaryLayout())
		{
			if(!gyroSet)
			{
				targetGyro = driveTrain.getAngle();
				gyroSet = true;
			}
			// TODO: Change so we are not inverting
			driveTrain.driveStraight(-ControlsManager.primaryXboxController.getLeftStickYPrimaryLayout(), targetGyro);
		}
		else
		{
			driveTrain.tankDrive(-ControlsManager.primaryXboxController.getRightStickYPrimaryLayout(), -ControlsManager.primaryXboxController.getLeftStickYPrimaryLayout());

			gyroSet = false;
		}

		if(ControlsManager.primaryXboxController.getLeftTriggerPrimaryLayout() > .5 || ControlsManager.primaryXboxController.getLeftTriggerSecondaryLayout() > .5)
			ControlsManager.changeToSecondaryLayout(1);
		else
			ControlsManager.changeToPrimaryLayout(1);

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
	 * Removes loose ends and exits command properly.
	 */
	@Override
	protected void end()
	{
		driveTrain.tankDrive(0, 0);
	}
}
