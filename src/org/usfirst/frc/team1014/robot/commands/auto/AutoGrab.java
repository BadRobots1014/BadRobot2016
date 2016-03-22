package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * The robot will automatically grab the ball after being initialized
 * 
 * @author Vaarun N.
 * 
 */
public class AutoGrab extends CommandBase
{
	// Initialize variables
	double currentTime, endingTime, time;

	/**
	 * basic constructor
	 * 
	 * @param time
	 *            - amount of time the grabber should be run
	 * 
	 */
	public AutoGrab(double time)
	{
		this.time = time * 1000000;
		requires((Subsystem) shooter);
	}

	/**
	 * resets grabber's rotation starts a timer for current time in micro sec, sets an ending time
	 * based on user input
	 */
	@Override
	protected void initialize()
	{
		shooter.grab(0);
		currentTime = Utility.getFPGATime();
		endingTime = currentTime + time;
	}

	@Override
	public String getConsoleIdentity()
	{
		return "Auto_Grab";
	}

	@Override
	protected void end()
	{
		shooter.grab(0);

	}

	@Override
	protected void execute()
	{
		shooter.grab(0.7);
		currentTime = Utility.getFPGATime();
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