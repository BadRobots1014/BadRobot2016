package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;
import org.usfirst.frc.team1014.robot.utilities.Logger;
import org.usfirst.frc.team1014.robot.utilities.Logger.Level;

public class FindTarget extends CommandBase
{
	private ProcessedCam cam = ProcessedCam.getInstance();
	private boolean isFinishedRotate = false, isFinishedDrive = false;
	private double minSpeedTurn = 0.37;
	private double maxSpeedTurn = 0.45;
	private double score = 90;
	private double deadzoneX = 3;
	private double deadzoneY = 3;
	private double downSpeedY = -.15;
	private double upSpeedY = .15;
	private boolean stillPressed = false;
	private boolean servoPos = false;

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
		shooter.driveServo(servoPos);
		new Runnable()
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
						e.printStackTrace();
					}
					shooter.ringLightOff();
					try
					{
						Thread.sleep(100);
					} catch(InterruptedException e)
					{
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

		shooter.setSpeeds(ControlsManager.secondaryXboxController.getRightStickY());

		if(!stillPressed)
		{
			if(ControlsManager.secondaryXboxController.isAButtonPressed())
			{
				servoPos = !servoPos;
				shooter.driveServo(servoPos);
				stillPressed = true;
			}
		}
		else
		{
			if(!ControlsManager.secondaryXboxController.isAButtonPressed())
				stillPressed = false;
		}

		if(Math.abs(cam.getTrackingScore()) >= score)
		{
			if(Math.abs(cam.getX()) > deadzoneX)
			{
				speed = Math.abs(cam.getX() / cam.getHalfWidth());
				if(speed < minSpeedTurn)
					speed = minSpeedTurn;
				else if(speed > maxSpeedTurn)
					speed = maxSpeedTurn;
				speed = cam.getX() > 0 ? speed : -speed;
				driveTrain.tankDrive(speed, -speed);
			}
			else
			{
				driveTrain.tankDrive(0, 0);
			}
			if(Math.abs(cam.getY()) > deadzoneY)
			{
				speed = cam.getY() > 0 ? downSpeedY : upSpeedY;
				shooter.rotate(speed);
			}
			else
			{
				speed = 0;
				shooter.rotate(speed);
			}
			Logger.log(Level.Debug, "6969", "" + speed);

		}
		else
		{
			driveTrain.tankDrive(0.0f, 0.0f);
			shooter.rotate(0.0f);
			isFinishedRotate = true;
			isFinishedDrive = true;
		}

		isfinished = isFinishedRotate && isFinishedDrive;
		isfinished = false;
	}

	@Override
	protected boolean isFinished()
	{
		return isfinished;
	}
}