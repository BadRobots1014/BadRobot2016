package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author Vaarun N.
 * 
 */
public class AutoGrab extends CommandBase
{

	double speed;
	double time;

	public AutoGrab(double speed)
	{
		this.speed = speed;
		requires((Subsystem) shooter);
	}

	@Override
	protected void initialize()
	{
		shooter.grab(0);
	}

	@Override
	public String getConsoleIdentity()
	{
		return "Grab";
	}

	@Override
	protected void end()
	{
		shooter.grab(0);

	}

	@Override
	protected void execute()
	{
		shooter.grab(speed);
		time = Utility.getFPGATime();
	}

	@Override
	protected void interrupted()
	{
		System.out.print("Grabber was interrupted");
	}

	@Override
	protected boolean isFinished()
	{
		if(time - 1000 < 0)
		{
			return true;
		}
		else
		{
			return false;
		}

	}
}