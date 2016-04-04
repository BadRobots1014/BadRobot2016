package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;

public class TankDriveTime extends CommandBase
{
	double speedRight = 0;
	double speedLeft = 0;
	double time = 0;
	double timeEnd = Utility.getFPGATime();

	public TankDriveTime(double speedRight, double speedLeft, double time)
	{
		this.speedRight = speedRight;
		this.speedLeft = speedLeft;
		this.time = time * 1000000;
	}

	@Override
	protected void initialize()
	{
		timeEnd = Utility.getFPGATime() + time;

	}

	@Override
	public String getConsoleIdentity()
	{
		// TODO Auto-generated method stub
		return "TankDriveTime";
	}

	@Override
	protected void execute()
	{
		driveTrain.tankDrive(speedRight, speedLeft);

	}

	@Override
	protected boolean isFinished()
	{
		if(Utility.getFPGATime() > timeEnd)
			return true;
		return false;
	}

	@Override
	protected void end()
	{
		driveTrain.tankDrive(0, 0);

	}

}
