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
	double minSpeed = 0.4;
	double maxSpeed = 0.5;
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
		return "FindTarget";
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
				if(Math.abs(cam.getX() / cam.getHalfWidth()) < minSpeed)
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
					for(int i = 0; i < 50; i++)
						driveTrain.tankDrive(1, -1);

					driveTrain.tankDrive(speed, -speed);
				}
				else
				{
					for(int i = 0; i < 50; i++)
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
			
			if(Math.abs(cam.getY()) > 10)
			{
				double rotateSpeed = 0;
				if(Math.abs(cam.getY() / 120) < .1)
				{
					if(cam.getY() / 120 < 0)
						rotateSpeed = .1;
					else
						rotateSpeed = -.1;
				}
				else if(Math.abs(cam.getY() / 120) > .5)
				{
					if(cam.getY() / 120 < 0)
						rotateSpeed = .5;
					else
						rotateSpeed = -.5;
				}
				else
				{
					rotateSpeed = -cam.getY() / 120;
				}

				shooter.rotate(rotateSpeed);
			}
			else
			{
				shooter.rotate(0);
				isFinishedRotate = true;
			}
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
