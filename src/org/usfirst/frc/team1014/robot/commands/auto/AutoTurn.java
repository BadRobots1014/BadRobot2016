package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author Tze Hei T.
 * 
 */
public class AutoTurn extends CommandBase
{

	public double degree, difference, proportion, sign;
	public static int rotationCoefficient = 60;

	/**
	 * Constructor
	 * 
	 * @param degree
	 *            - how far the robot needs to turn. Positive values turn right. Negative values
	 *            turn left *
	 */
	public AutoTurn(double degree)
	{
		this.degree = degree;
		requires((Subsystem) driveTrain);
		driveTrain.resetMXPAngle();
		sign = Math.abs(degree) / degree; // Gets the sign of degree
	}

	@Override
	protected void initialize()
	{
		driveTrain.resetMXPAngle();
		driveTrain.tankDrive(0, 0);
	}

	@Override
	public String getConsoleIdentity()
	{
		return "Auto_Turn";
	}

	@Override
	protected void end()
	{
		driveTrain.tankDrive(0, 0);

	}

	@Override
	protected void execute()
	{
		difference = driveTrain.getAngle() - degree;

		Logger.log("Difference", "" + difference);

		if(sign < 0)
		{
			Logger.log("Turning left", "" + rotation());
			driveTrain.tankDrive(.4, -.4);
		}
		else if(sign > 0)
		{
			Logger.log("Turning right", "" + rotation());
			driveTrain.tankDrive(-.4, .4);
		}
	}

	/**
	 * 
	 * @return - scales the speed for the drivetrain
	 */
	public double rotation()
	{
		return difference / 120;// PID.turnSpeedScale(difference, -Math.PI, Math.PI);
	}

	@Override
	protected boolean isFinished()
	{
		// Stops if the difference is in the range 0
		return Math.abs(difference) < 5;
	}

}
