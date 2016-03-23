package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.sensors.BadCAN;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoRotateDown extends CommandBase
{
	int stopCounter;
	double stoppedEncoderValue;
	
	public AutoRotateDown()
	{
		requires((Subsystem) shooter);
		shooter.rotate(0);
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		shooter.rotate(0);
	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		shooter.rotate(-.3);
		
		if(Math.abs(((BadCAN)shooter.rotator).getDistance() - stoppedEncoderValue) < 1)
		{
			stopCounter++;
		}
		else
		{
			stopCounter = 0;
		}
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		stopCounter = 0;
		stoppedEncoderValue = 0;
		
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		Logger.logThis(getConsoleIdentity() + ": I've been interrupted!");
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		if(stopCounter > 50)
		{
			return true;
		}
		else
			return false;
	}

	@Override
	public String getConsoleIdentity()
	{
		// TODO Auto-generated method stub
		return "AutoRotateDown";
	}

}
