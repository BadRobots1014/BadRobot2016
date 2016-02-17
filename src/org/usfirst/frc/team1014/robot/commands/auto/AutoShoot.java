package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * The robot will automatically shoot a ball
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
	 * 			- amount of time that you want it to run for
	 */
	public AutoShoot(double time)
	{
		this.time = time * 1000000;
		requires((Subsystem) shooter);
	}
	/**
	 * resets shooter's speed
	 * starts a timer for current time in micro sec based on user input
	 */
	@Override
	protected void initialize()
	{
		shooter.shoot(0);
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

	/**
	 * when timer is greater than time entered, it stops
	 */
	@Override
	protected boolean isFinished()
	{
		if(currentTime >= endingTime)
			return true;
		else
			return false;
	}
}
