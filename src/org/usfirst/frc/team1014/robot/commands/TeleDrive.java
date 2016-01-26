package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.utilities.Logger;
import org.usfirst.frc.team1014.robot.utilities.Logger.Level;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class defines how the robot drives through teleop.
 * 
 * @author Manu S.
 * 
 */
public class TeleDrive extends CommandBase
{
	public TeleDrive()
	{
		Logger.log(Level.Debug, "TeleDrive", "Initializing the Teleop Drive");
		requires((Subsystem)driveTrain);
	}
	/**
	 * This method runs before the command is 
	 * executed to make sure everything is ready
	 * for it to be executed.
	 */
	@Override
	protected void initialize()
	{
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
	protected void execute() {
		// TODO Auto-generated method stub
		driveTrain.tankDrive(-oi.priXboxController.getLeftStickY(), -oi.priXboxController.getRightStickY());
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
		org.usfirst.frc.team1014.robot.utilities.Logger.logThis(getConsoleIdentity() + " I've been interrupted!");
	}
}