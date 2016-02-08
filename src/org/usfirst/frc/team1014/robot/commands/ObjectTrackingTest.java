package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;

public class ObjectTrackingTest extends CommandBase
{
	Runnable run;
	Thread thread;

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
				for(int i = 0;i<50;i++)
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
				double speed = (ProcessedCam.getInstance().getX()/160 > 0.1) ? ProcessedCam.getInstance().getX()/160 : 0.1;
				driveTrain.tankDrive(speed, -speed);
			}
			else
			{
				if(thread == null)
				{
					thread = new Thread(run);
					thread.start();
				}
				driveTrain.tankDrive(0, 0);
				isfinished = true;
			}
		}
		else
		{
			driveTrain.tankDrive(0, 0);
		}
				
		
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished()
	{
		return isfinished;
	}
	

}
