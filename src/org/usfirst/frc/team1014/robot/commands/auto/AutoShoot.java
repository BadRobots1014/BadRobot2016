package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author Vaarun N.
 * 
 */
public class AutoShoot extends CommandBase
{

	double speed;
	double time;

	public AutoShoot(double speed)
	{
		this.speed = speed;
		requires((Subsystem) shooter);
	}

	@Override
	protected void initialize()
	{
		shooter.shoot(0);

	}

	@Override
	public String getConsoleIdentity()
	{

		return "Shooter";
	}

	@Override
	protected void end()
	{
		shooter.shoot(0);

	}

	@Override
	protected void execute()
	{
		shooter.shoot(speed);
		time = Utility.getFPGATime();

	}

	@Override
	protected void interrupted()
	{
		System.out.print("Shooter was interrupted");

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
