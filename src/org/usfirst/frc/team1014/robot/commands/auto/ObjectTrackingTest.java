package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.Utility;

public class ObjectTrackingTest extends CommandBase
{
	ProcessedCam cam = ProcessedCam.getInstance();
	Runnable run;
	Thread thread;
	double time = Utility.getFPGATime();
	boolean isFinishedRotate = true, isFinishedDrive = false;
	boolean timeSet = false;
	double waitTime = 5 * 1000000;
	double minSpeed = 0.4;
	double maxSpeed = 0.4;
	double score = 90;
	double deadzone = 5;

	public ObjectTrackingTest()
	{
		requires(driveTrain);
		requires(shooter);
	}

	@Override
	protected void initialize()
	{
		driveTrain.tankDrive(0, 0);
		shooter.ringLightOn();
		run = new Runnable()
		{

			@Override
			public void run()
			{
				for(int i = 0; i < 10; i++)
				{
					shooter.ringLightOn();
					try
					{
						Thread.sleep(100);
					} catch(InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					shooter.ringLightOff();
					try
					{
						Thread.sleep(100);
					} catch(InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				shooter.ringLightOn();

			}

		};
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
					driveTrain.tankDrive(speed, -speed);
				else driveTrain.tankDrive(-speed, speed);
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
