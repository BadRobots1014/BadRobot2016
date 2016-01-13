package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.OI;

/**
 * This class defines how the robot drives through teleop.
 * 
 * @author Manu S.
 * 
 */
public class TeleDrive extends CommandBase
{

	@Override
	protected void initialize()
	{
		requires(driveTrain);
		driveTrain.tankDrive(0, 0);
	}

	@Override
	public String getConsoleIdentity()
	{
		return "TeleDrive";
	}

	@Override
	protected void execute()
	{
		driveTrain.tankDrive(-OI.priXboxController.getLeftStickY(), -OI.priXboxController.getRightStickY());
	}

	@Override
	protected boolean isFinished()
	{
		return false;
	}

	@Override
	protected void end()
	{
		driveTrain.tankDrive(0, 0);
	}

	@Override
	protected void interrupted()
	{

	}
}