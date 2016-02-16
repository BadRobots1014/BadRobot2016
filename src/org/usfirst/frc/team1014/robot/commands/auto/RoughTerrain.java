package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author Vaarun N. Robot goes over the rough terrain obstacle and corrects itself when imbalanced
 */
public class RoughTerrain extends CommandBase
{
	/**
	 * initialize variables
	 */
	private double roll;
	private double currentTime;

	/**
	 * basic constructor
	 */
	public RoughTerrain()
	{
		requires((Subsystem) driveTrain);
	}

	/**
	 * resets drive at start
	 */
	@Override
	protected void initialize()
	{
		driveTrain.tankDrive(0, 0);
	}

	@Override
	public String getConsoleIdentity()
	{
		return "RoughTerrain";
	}

	/**
	 * sets drive to zero at end
	 */
	@Override
	protected void end()
	{
		driveTrain.tankDrive(0, 0);
	}

	/**
	 * starts timer in microseconds robot drives forward gets the amount the robot has rolled if the
	 * absolute value of the roll is too high then robot will correct it
	 */
	@Override
	protected void execute()
	{
		currentTime = Utility.getFPGATime();
		roll = driveTrain.getRoll();
		driveTrain.tankDrive(1, 1);
		if(Math.abs(roll) > 20)
		{
			if(roll > 0)
				driveTrain.tankDrive(0, 1);
			else if(roll < 0)
				driveTrain.tankDrive(1, 0);
		}
	}

	/**
	 * when timer reaches 5secs, robot stops.
	 */
	@Override
	protected boolean isFinished()
	{
		return currentTime >= 5000000;
	}

}
