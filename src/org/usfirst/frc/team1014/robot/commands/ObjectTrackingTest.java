package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;

public class ObjectTrackingTest extends CommandBase
{

	public ObjectTrackingTest()
	{
		requires(driveTrain);
	}
	@Override
	protected void initialize()
	{
		driveTrain.tankDrive(0, 0);
	}

	@Override
	public String getConsoleIdentity()
	{
		// TODO Auto-generated method stub
		return "ObjectTrackingTest";
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute()
	{
		driveTrain.tankDrive(ProcessedCam.getInstance().getX()/320, -ProcessedCam.getInstance().getX()/320);
		
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}
	

}
