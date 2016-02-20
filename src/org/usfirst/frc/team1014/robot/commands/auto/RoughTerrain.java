package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author Vaarun N.
 * Robot goes over the rough terrain obstacle and corrects itself when imbalanced
 */
public class RoughTerrain extends CommandBase

{
	/**
	 * initialize variables
	 */
	double roll;
	double currentTime;

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
	 * starts timer in microseconds 
	 * robot drives forward 
	 * gets the amount the robot has rolled 
	 * if the absolute value of the roll is too high then robot will correct it
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

			if(roll < 0)
				driveTrain.tankDrive(1, 0);
		}
	}

	@Override
	protected void interrupted()
	{
		System.out.println("Rough terrain was interrupted ");

	}

	/**
	 * when timer reaches 5secs, robot stops.
	 */
	@Override
	protected boolean isFinished()
	{
		if(currentTime >= 5000000)
			return true;
		else return false;
	}

}
