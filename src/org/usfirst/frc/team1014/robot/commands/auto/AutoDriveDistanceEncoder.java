package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author Manu S. 
 * 
 */
public class AutoDriveDistanceEncoder extends CommandBase
{

	public double speed, currentRotations;
	public double zeroAngle;
	public double targetRotations;

	/**
	 * Constructor
	 * 
	 * @param speed
	 *            - speed the robot will run at
	 * @param distance
	 *            - the distance from something it will stop at
	 */
	public AutoDriveDistanceEncoder(double speed, double numRotations)
	{
		this.targetRotations = numRotations;
		this.speed = speed;
		requires((Subsystem) driveTrain);
	}

	@Override
	protected void initialize()
	{
		driveTrain.tankDrive(0, 0);
		zeroAngle = driveTrain.getAngle();
		currentRotations = driveTrain.getDriveEncoderDistance();
	}

	@Override
	public String getConsoleIdentity()
	{
		return "DriveForwardDistance";
	}

	@Override
	protected void end()
	{
		driveTrain.tankDrive(0, 0);

	}

	@Override
	protected void execute()
	{
		currentRotations = driveTrain.getDriveEncoderDistance(); // Gets the rotations
		driveTrain.driveStraight(speed, zeroAngle);
	}

	@Override
	protected void interrupted()
	{
		System.out.println("DriveForwardDistanceEncoder was interrupted");

	}
	
	/**
	 * stops when distance is less than desired distance
	 */
	@Override
	protected boolean isFinished()
	{
		if(currentRotations < targetRotations)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}