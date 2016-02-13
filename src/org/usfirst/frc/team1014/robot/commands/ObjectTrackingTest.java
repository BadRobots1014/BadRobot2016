package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;
import org.usfirst.frc.team1014.robot.utilities.Logger;

public class ObjectTrackingTest extends CommandBase
{
	Runnable run;
	Thread thread;

	boolean isFinishedRotate, isFinishedDrive;

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
				for(int i = 0; i < 50; i++)
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
		if(Math.abs(ProcessedCam.getInstance().getTrackingScore()) >= 90)
		{
			if(Math.abs(ProcessedCam.getInstance().getX()) > 10)
			{
				double speed = (ProcessedCam.getInstance().getX() / 160 > 0.4) ? ProcessedCam.getInstance().getX() / 160 : 0.4;
				driveTrain.tankDrive(-speed, speed);
			}
			else
			{
				driveTrain.tankDrive(0, 0);
				isFinishedDrive = true;
			}
			if(Math.abs(ProcessedCam.getInstance().getY()) > 10)
			{
				double speed = (ProcessedCam.getInstance().getY() / 120 > .1) ? ProcessedCam.getInstance().getY() / 120 : .1;
				shooter.rotate(speed);
			}
			else
			{
				shooter.rotate(0);
				isFinishedRotate = true;
			}
		}
		else
		{
			driveTrain.tankDrive(0, 0);
			shooter.shoot(0);
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
		if(isfinished)
		{
			if(thread == null)
			{
				thread = new Thread(run);
				thread.start();
			}
		}

		return isfinished;
	}

}
