package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.command.Subsystem;

public class MoveToLowerRetroTape extends CommandBase
{

	boolean isFinished = false;

	public MoveToLowerRetroTape()
	{
		requires((Subsystem) shooter);
	}

	@Override
	protected void end()
	{
		shooter.rotate(0.0);

	}

	@Override
	protected void execute()
	{
		if(shooter.pingOpticalSensor() && !shooter.detectedTape)
		{
			shooter.rotate(0.0);
			isFinished = true;
		}
		else
		{
			shooter.rotate(-0.7);
			isFinished = false;
		}
		shooter.detectedTape = shooter.pingOpticalSensor();

	}

	@Override
	protected void initialize()
	{
		shooter.detectedTape = false;

	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return isFinished;
	}

	@Override
	public String getConsoleIdentity()
	{
		// TODO Auto-generated method stub
		return "MoveToLowerRetroTape";
	}

}
