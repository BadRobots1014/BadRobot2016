package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * The robot will automatically
 * 
 * @author Vaarun N.
 * 
 */
public class AutoShoot extends CommandBase
{
	// Initialize variables
	double currentTime, endingTime, time;

	/**
	 * 
	 * @param time
	 * 
	 */
	public AutoShoot(double time)
	{
		this.time = time * 1000000;
		requires((Subsystem) shooter);
	}

	@Override
	protected void initialize()
	{
		shooter.shoot(0);
		currentTime = Utility.getFPGATime();
		endingTime = currentTime + time;
	}

	@Override
	public String getConsoleIdentity()
	{
		return "Auto Shoot";
	}

	@Override
	protected void end()
	{
		shooter.shoot(0);

	}

	@Override
	protected void execute()
	{
		shooter.shoot(1);
		currentTime = Utility.getFPGATime();
	}

	@Override
	protected void interrupted()
	{
		System.out.print("Shooter was interrupted");
	}

	@Override
	protected boolean isFinished()
	{
		if(currentTime >= endingTime)
		{
			shooter.driveServo(true);
			return true;
		}
		else
			return false;
	}
}
