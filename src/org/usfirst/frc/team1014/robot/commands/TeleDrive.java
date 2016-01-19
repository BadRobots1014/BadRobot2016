package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.OI;
import org.usfirst.frc.team1014.utilities.Logger;

/**
 * This class defines how the robot drives through teleop.
 * 
 * @author Manu S.
 * 
 */
public class TeleDrive extends CommandBase
{

	/**
	 * This method runs before the command is 
	 * executed to make sure everything is ready
	 * for it to be executed.
	 */
	@Override
	protected void initialize()
	{
		requires(driveTrain);
		driveTrain.tankDrive(0, 0);
	}

	/**
	 * This is really useless and doesn't really have
	 * much function, other than when we want to 
	 * log things.
	 */
	@Override
	public String getConsoleIdentity()
	{
		return "TeleDrive";
	}

	/**
	 * This is the method that gets called over and over
	 * again while the command is actually running.
	 */
	@Override
	protected void execute()
	{
		driveTrain.tankDrive(-OI.priXboxController.getLeftStickY(), -OI.priXboxController.getRightStickY());
		Logger.logThis(driveTrain.getLIDARDistance() + "");
	}

	/**
	 * Lets the system know when to stop this command
	 * and do some other one.
	 */
	@Override
	protected boolean isFinished()
	{
		return false;
	}

	/**
	 * What the robot should do once the command has
	 * finished executing.
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
		Logger.logThis(getConsoleIdentity() + " I've been interrupted!");
	}
}