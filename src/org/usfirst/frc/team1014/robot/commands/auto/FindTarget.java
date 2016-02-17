package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;

import edu.wpi.first.wpilibj.Utility;

public class FindTarget extends CommandBase
{
	private ProcessedCam cam = ProcessedCam.getInstance();
	private boolean isFinishedRotate = false, isFinishedDrive = false;
	private boolean stillPressed = false;
	private boolean servoPos = false;
	private boolean timeSet = false;
	private boolean stopTimeSet = false;
	private double counter = 0;
	private double previousCamX = 0;
	private double time = Utility.getFPGATime();

	private final double minSpeedTurn = 0.48;
	private final double maxSpeedTurn = 0.48;
	private final double score = 90;
	private final double deadzoneX = 3;
	private final double deadzoneY = 5;
	private final double downSpeedY = -.15;
	private final double upSpeedY = .15;
	private final double waitTime = 2 * 1000000;
	private final double jerkSpeed = .3;

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
			stopTimeSet = false;
			if(Math.abs(cam.getX()) > deadzoneX)
			{
				speed = Math.abs(cam.getX() / cam.getHalfWidth());
				if(speed < minSpeedTurn)
					speed = minSpeedTurn;
				else if(speed > maxSpeedTurn)
					speed = maxSpeedTurn;
				speed = cam.getX() > 0 ? speed : -speed;
				if(previousCamX == cam.getX())
					counter++;
				else counter = 0;
				if(counter > 50)
				{
					counter = 0;
					speed += speed < 0 ? -jerkSpeed : jerkSpeed;
				}
				driveTrain.tankDrive(speed, -speed);
				previousCamX = cam.getX();
				isFinishedDrive = false;
				timeSet = false;
			}
			else
			{
				driveTrain.tankDrive(0, 0);
				isFinishedDrive = true;
			}

			if(Math.abs(cam.getY()) > deadzoneY)
			{
				speed = cam.getY() > 0 ? downSpeedY : upSpeedY;
				shooter.rotate(speed);
				isFinishedRotate = false;
				timeSet = false;
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
			shooter.rotate(0.0f);
			if(!stopTimeSet)
			{
				time = Utility.getFPGATime() + waitTime;
				stopTimeSet = true;
			}

			if(Utility.getFPGATime() > time && stopTimeSet)
			{
				isFinished = true;
			}
			else
			{
				isFinished = false;
			}
		}
	}

	@Override
	protected boolean isFinished()
	{
		if(!isFinished)
		{
			if(isFinishedDrive && isFinishedRotate && !timeSet)
			{
				time = Utility.getFPGATime() + waitTime;
				timeSet = true;
			}
			else if(isFinishedDrive && isFinishedRotate && timeSet)
			{
				if(Utility.getFPGATime() > time)
				{
					isFinished = true;
				}
			}
		}
		return isFinished;
	}
}