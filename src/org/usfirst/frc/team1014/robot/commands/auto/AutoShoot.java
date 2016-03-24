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
	private double currentTime, endingTime, runTime;

	/**
	 * 
	 * @param time
	 *            - amount of time that you want it to run for
	 */
	public AutoShoot(Double time)
	{
		this.runTime = time * 1000000;
		requires((Subsystem) shooter);
	}

	/**
	 * resets shooter's speed starts a timer for current time in micro sec based on user input
	 */
	@Override
	protected void initialize()
	{
		endingTime = Utility.getFPGATime() + runTime;
		shooter.shoot(0);
	}

	@Override
	public String getConsoleIdentity()
	{
		return "Auto_Shoot";
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

		shooter.driveServo(currentTime >= endingTime - 1 * 1000000);
	}

	/**
	 * when timer is greater than time entered, it stops
	 */
	@Override
	protected boolean isFinished()
	{
		return currentTime >= endingTime;
	}
}
