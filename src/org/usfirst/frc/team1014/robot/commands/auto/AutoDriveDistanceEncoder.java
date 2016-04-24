package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drives the robot forward at a certain speed for a certain distance.
 * 
 * @author Manu S.
 * 
 */
public class AutoDriveDistanceEncoder extends CommandBase
{

	private double speed, currentRotations;
	private double zeroAngle;
	private double targetRotations;
	private double difference;
	private double numRotations;

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
		this.numRotations = numRotations;
		this.speed = speed;
		requires((Subsystem) driveTrain);
	}

	@Override
	protected void initialize()
	{
		driveTrain.tankDrive(0, 0);
		zeroAngle = driveTrain.getAngle();
		currentRotations = driveTrain.getDriveEncoderDistance();
		this.targetRotations = driveTrain.getDriveEncoderDistance() - (Math.abs(speed) / speed) * numRotations;
	}

	@Override
	public String getConsoleIdentity()
	{
		return "Drive_Forward_Distance";
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
		difference = currentRotations - targetRotations;

		if(Math.abs(difference) < .4)
			driveTrain.driveStraight(.4, zeroAngle);
		else 
			driveTrain.driveStraight(speed, zeroAngle);
	}

	/**
	 * stops when distance is less than desired distance
	 */
	@Override
	protected boolean isFinished()
	{
		return Math.abs(difference) < .01;
	}
}
