package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;
import org.usfirst.frc.team1014.robot.utilities.Logger;
import org.usfirst.frc.team1014.robot.utilities.Logger.Level;

import edu.wpi.first.wpilibj.Utility;

public class FindTarget extends CommandBase
{
	ProcessedCam cam = ProcessedCam.getInstance();
	Runnable run;
	Thread thread;
	double time = Utility.getFPGATime();
	boolean isFinishedRotate = true, isFinishedDrive = false;
	boolean timeSet = false;
	double waitTime = 5 * 1000000;
	double minSpeed = 0.3;
	double maxSpeed = 0.4;
	double score = 90;
	double deadzone = 5;

	public FindTarget()
	{
		requires(driveTrain);
		requires(shooter);
	}

	@Override
	protected void initialize()
	{
		driveTrain.tankDrive(0, 0);
		shooter.ringLightOn();
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
		driveTrain.tankDrive(0.0f, 0.0f);

	}

	@Override
	protected void execute()
	{
		double speed;
		Logger.log(Level.Debug, "Hi", cam.getTrackingScore() + "");
		if(Math.abs(cam.getTrackingScore()) >= score)
		{

			if(Math.abs(cam.getX()) > deadzone)
			{
				if(Math.abs(cam.getX() / cam.getHalfHeight()) < minSpeed)
				{
					speed = minSpeed;
				}
				else if(Math.abs(cam.getX() / cam.getHalfWidth()) > maxSpeed)
				{
					speed = maxSpeed;
				}
				else
				{
					speed = Math.abs(cam.getX() / cam.getHalfWidth());
				}
				if(cam.getX() > 0)
				{
					for(int i = 0; i < 50; i ++)
						driveTrain.tankDrive(.70, -.70);
					
					driveTrain.tankDrive(speed, -speed);
				}
				else
				{
					for(int i = 0; i < 50; i ++)
						driveTrain.tankDrive(-1, 1);
					
					driveTrain.tankDrive(-speed, speed);
				}
				timeSet = false;
			}
			else
			{
				if(!timeSet)
				{
					time = Utility.getFPGATime() + waitTime;
					driveTrain.tankDrive(0.0f, 0.0f);
					timeSet = true;
				}
				else
				{
					if(Utility.getFPGATime() < waitTime)
					{
						driveTrain.tankDrive(0.0f, 0.0f);
					}
					else
					{
						isFinishedDrive = true;
					}
				}

			}
			/*
			 * if(Math.abs(ProcessedCam.getInstance().getY()) > 10) { double speed =
			 * (ProcessedCam.getInstance().getY()/120 > .1) ? ProcessedCam.getInstance().getY()/120
			 * : .1; shooter.rotate(speed); } else { shooter.rotate(0); isFinishedRotate = true; }
			 */
		}
		else
		{
			driveTrain.tankDrive(0.0f, 0.0f);
			shooter.shoot(0.0f);
			isFinishedRotate = true;
			isFinishedDrive = true;
		}

		isfinished = isFinishedRotate && isFinishedDrive;

	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		Logger.logThis(getConsoleIdentity() + ": I've been interrupted!!!");
	}

	@Override
	protected boolean isFinished()
	{
		return isfinished;
	}

}