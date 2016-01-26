package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

public class RotateShooter extends CommandBase
{

	public RotateShooter()
	{
		requires((Subsystem) shooter);
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		shooter.rotate(0.0);
	}

	@Override
	public String getConsoleIdentity()
	{
		// TODO Auto-generated method stub
		return "RotateShooter";
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		shooter.rotate(0.0);
	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		shooter.rotate(oi.secXboxController.getRightStickY());

	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		Logger.logThis(getConsoleIdentity() + ": I was interrupted!");
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
