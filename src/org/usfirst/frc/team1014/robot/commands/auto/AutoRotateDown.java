package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.sensors.BadTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Rotates the shooter all the way down
 * 
 * @author Tze Hei T.
 *
 */
public class AutoRotateDown extends CommandBase
{

	double currentRevolution;
	int place = 0; // this is all the way down (guess, need to find actual value)

	public AutoRotateDown()
	{
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

		return "AutoRotateDown";
	}

	@Override
	protected void end()
	{
		shooter.rotate(0);

	}

	/**
	 * Checks the current value of the encoder and rotates the shooter down
	 */
	@Override
	protected void execute()
	{
		currentRevolution = ((BadTalon) shooter.rotator).get();
		shooter.rotate(-1);

	}

	@Override
	protected void interrupted()
	{
		System.out.println("AutoRotateDown was interrupted");

	}

	@Override
	protected boolean isFinished()
	{
		/**
		 * Stops once the encoder position is less than the value of the encoder that is all the way
		 * down
		 */
		if(currentRevolution <= place)
		{
			return true;
		}
		return false;
	}

}
