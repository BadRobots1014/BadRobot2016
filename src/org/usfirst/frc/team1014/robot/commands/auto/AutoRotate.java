package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.sensors.BadTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Allows the shooter to rotate an amount of revolutions in autonomous
 * 
 * @author Tze Hei T.
 *
 */

public class AutoRotate extends CommandBase
{

	double revolutions;
	double currentRevolutions;
	double difference;
	boolean direction;

	/**
	 * @param revolutions
	 *            - amount of revolutions you want to turn
	 * @param direction
	 *            - false for down, true for up
	 */
	public AutoRotate(double revolutions, boolean direction)
	{
		this.direction = direction;
		this.revolutions = revolutions;
		requires((Subsystem) shooter);
	}

	@Override
	protected void initialize()
	{
		shooter.rotate(0);

	}

	@Override
	public String getConsoleIdentity()
	{

		return "AutoRotate";
	}

	@Override
	protected void end()
	{
		shooter.rotate(0);

	}

	/**
	 * Checks how far off the amount of revolutions is from the desired and turns the rotator in the
	 * direction that was entered
	 */
	@Override
	protected void execute()
	{
		currentRevolutions = ((BadTalon) shooter.rotator).get();
		difference = currentRevolutions - revolutions;

		if(direction == true)
		{
			shooter.rotate(1);
		}
		if(direction == false)
		{
			shooter.rotate(-1);
		}

	}

	@Override
	protected void interrupted()
	{
		System.out.println("AutoRotate was interrupted");

	}

	@Override
	protected boolean isFinished()
	{
		/**
		 * Stops when the amount of revolutions is reached or passed
		 */
		if(Math.abs(difference) <= 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
