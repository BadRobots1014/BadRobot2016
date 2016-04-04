package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author Sam G.
 * 
 */
public class AutoDrive extends CommandBase
{

	public double driveTime;
	public double speed;
	public double startTime;
	public double passedTime;
	public double targetAngle;

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
		targetAngle = driveTrain.getAngle();
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

	public static double deadzone(double d)
	{
		if(Math.abs(d) < .1 || d == 0)
			return 0;

		return (d / Math.abs(d)) * ((Math.abs(d) - .1) / (1 - .1));
	}

}