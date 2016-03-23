package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoDriveServo extends CommandBase
{
	boolean servoIn;
	boolean isServoIn;
	
	public AutoDriveServo(boolean servoIn)
	{
		requires((Subsystem) shooter);
		this.servoIn = servoIn;
	}
	
	@Override
	protected void end()
	{
		shooter.driveServo(false);
	}

	@Override
	protected void execute()
	{
		if(servoIn)
		{
			shooter.driveServo(false);
			isServoIn = true;
		}
		else
		{
			shooter.driveServo(true);
			isServoIn = false;
		}
	}

	@Override
	protected void initialize()
	{
		shooter.driveServo(false);
	}

	@Override
	protected void interrupted()
	{
		Logger.logThis(getConsoleIdentity() + ": I've been interrupted!"); 
	}

	@Override
	protected boolean isFinished()
	{
		if((isServoIn && servoIn) || (!isServoIn && !servoIn))
		{
			return true;
		}
		else
			return false;
	}

	@Override
	public String getConsoleIdentity()
	{
		return null;
	}

}
