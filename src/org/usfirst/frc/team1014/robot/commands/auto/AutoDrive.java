package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drives forward/backwards at a certain speed for a given amount of time.
 * 
 * @author Sam G.
 * 
 */
public class AutoDrive extends CommandBase
{

	private double driveTime;
	private double speed;
	private double startTime;
	private double passedTime;

	// private double targetAngle;

	public AutoDrive(double driveTime, double speed)
	{
		this.driveTime = driveTime;
		this.speed = speed;
		requires((Subsystem) driveTrain);
		passedTime = 0;
	}

	@Override
	protected void initialize()
	{
		startTime = Utility.getFPGATime();
		driveTrain.tankDrive(0, 0);
		// targetAngle = driveTrain.getAngle();
	}

	@Override
	public String getConsoleIdentity()
	{
		return null;
	}

	@Override
	protected void end()
	{
		driveTrain.tankDrive(0, 0);
	}

	@Override
	protected void execute()
	{
		passedTime = Utility.getFPGATime() - startTime;
		driveTrain.tankDrive(speed, speed);
		// driveTrain.driveStraight(speed, targetAngle);
	}

	@Override
	protected boolean isFinished()
	{
		return (passedTime / 1000000) > driveTime;
	}

}