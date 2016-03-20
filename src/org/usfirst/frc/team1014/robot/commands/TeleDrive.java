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
	 * @return the name of the class.
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
		if(ControlsManager.primaryXboxController.isLBButtonPressedPrimaryLayout())
		{
			if(!gyroSet)
			{
				targetGyro = driveTrain.getAngle();
				gyroSet = true;
			}
			driveTrain.driveStraight(-ControlsManager.primaryXboxController.getLeftStickYPrimaryLayout(), targetGyro);
		}
		else
		{
			driveTrain.tankDrive(-ControlsManager.primaryXboxController.getRightStickYPrimaryLayout(), -ControlsManager.primaryXboxController.getLeftStickYPrimaryLayout());

			gyroSet = false;
		}
		// Logger.logThis("back right encoders " + ((BadTalon)
		// driveTrain.backRight).encoder.getDistance());
		// Logger.logThis("back left encoders" + ((BadTalon)
		// driveTrain.backLeft).encoder.getDistance());
		// Logger.logThis("GyroCalue: " + driveTrain.getAngle());

		if(ControlsManager.primaryXboxController.getLeftTriggerPrimaryLayout() > .5 || ControlsManager.primaryXboxController.getLeftTriggerSecondaryLayout() > .5)
			ControlsManager.changeToSecondaryLayout(1);
		else ControlsManager.changeToPrimaryLayout(1);

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

	/**
	 * Called when another command requires the same subsystem or {@code cancel()} is called. Cleans
	 * up dependencies and logs the interrupt.
	 */
	@Override
	protected void interrupted()
	{
		Logger.logThis(getConsoleIdentity() + " I've been interrupted!");
		end();
	}
}
