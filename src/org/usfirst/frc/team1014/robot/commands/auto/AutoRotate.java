package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.sensors.BadCAN;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author Tze Hei T.
 *
 */

public class AutoRotate extends CommandBase
{

	double revolutions;
	double currentRevolutions;
	double difference;

	/**
	 * Constructor
	 * 
	 * @param revolutions
	 *            - amount of revolutions you want to turn
	 */
	public AutoRotate(Double revolutions)
	{
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

		return "Auto_Rotate";
	}

	@Override
	protected void end()
	{
		shooter.rotate(0);

	}

	@Override
	protected void execute()
	{
		currentRevolutions = ((BadCAN) shooter.rotator).encoder.getDistance();
		difference = revolutions - currentRevolutions;
		shooter.rotateTo(revolutions);
	}

	@Override
	protected boolean isFinished()
	{
		return Math.abs(difference) <= 3;
	}
}
