package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;

import edu.wpi.first.wpilibj.Utility;

/**
 * This command is how the robot tracks the high goal. Firstly, it turns on the LED ring to activate
 * the retroreflective tape on the target then it begins its vision tracking. The robot turns so
 * that the target is in the center of the camera's field horizontally then moves the shooter up and
 * down until the target is centered.
 * 
 * @author Subash C.
 * @edit Manu S.
 *
 */
public class FindTarget extends CommandBase
{
	// the camera object
	private ProcessedCam cam = ProcessedCam.getInstance();

	// are the different parts of the tracking done or not
	private boolean isFinishedRotate = false, isFinishedDrive = false;

	// the position (in or out) for the servo
	private boolean isServoOut = false;

	// has the time to stop the entire tracking been set
	private boolean timeSet = false;

	// has the target out of view too long
	private boolean stopTimeSet = false;
	
	// has the shooting time been set yet
	private boolean shootingTimeSet = false;

	// the counter for the jerking bit
	private double countsNotMoving = 0;

	// the position the target is at
	private double previousCamX = 0;

	// the current time
	private double time = Utility.getFPGATime();
	
	// is the target lost from the view of the camera
	private boolean lostTarget = false;

	// the speed at which the robot turns
	private final double TURN_SPEED = 0.335;

	// minimum score to track
	private final double MIN_TRACKING_SCORE = 90;

	// deadzones for the x and y
	private final double DEADZONE_X = 3;
	private final double DEADZONE_Y = 5;

	// how fast the shooter articulates
	private final double ROTATE_SPEED = -.16;

	// how long the robot has to wait before its done
	private final double WAIT_TIME = 2 * 1000000;

	// how much of a push the robot gets when it stalls
	private final double JERK_SPEED = .3;

	public FindTarget()
	{
		requires(driveTrain);
		requires(shooter);
	}

	@Override
	protected void initialize()
	{
		driveTrain.tankDrive(0, 0);
		shooter.rotate(0.0);
		shooter.ringLightOn();
		shooter.driveServo(isServoOut);
		isFinished = false;
	}

	@Override
	public String getConsoleIdentity()
	{
		return "Find_Target";
	}

	@Override
	protected void end()
	{
		driveTrain.tankDrive(0.0f, 0.0f);
		shooter.shoot(0);
		shooter.rotate(0);
	}

	@Override
	protected void execute()
	{
		// how fast the robot will move
		double moveSpeed;

		shooter.ringLightOn();

		// allows the driver to shoot the ball at any time
		shooter.shoot(-ControlsManager.secondaryXboxController.getRightStickYPrimaryLayout());

		// moves the cam servo at any time
		isServoOut = ControlsManager.secondaryXboxController.isAButtonPressedPrimaryLayout();

		shooter.driveServo(isServoOut);

		// if the robot can see the object and track it ...
		if(Math.abs(cam.getTrackingScore()) >= MIN_TRACKING_SCORE)
		{
			lostTarget = false;
			stopTimeSet = false;
			// if the target is not in the acceptable range ...
			if(Math.abs(cam.getX()) > DEADZONE_X)
			{
				moveSpeed = Math.abs(cam.getX() / cam.getHalfWidth());

				// if the move speed is not the same as the ideal turning speed
				if(Math.abs(moveSpeed) != TURN_SPEED)
					moveSpeed = TURN_SPEED; // ... set it equal to it

				// make move speed negative or position based on the target's location
				moveSpeed = cam.getX() > 0 ? moveSpeed : -moveSpeed;

				// make sure the robot is actually moving
				if(previousCamX == cam.getX())
					countsNotMoving++;
				else
					countsNotMoving = 0;

				// if the robot isn't moving ...
				if(countsNotMoving > 50)
				{
					// .. make it
					countsNotMoving = 0;
					moveSpeed += moveSpeed < 0 ? -JERK_SPEED : JERK_SPEED;
				}

				// move at the final speed
				driveTrain.tankDrive(moveSpeed, -moveSpeed);

				// reset the previous camera
				previousCamX = cam.getX();

				// the robot isn't done driving
				isFinishedDrive = false;

				// don't track the time
				timeSet = false;
			}
			else
			// otherwise ...
			{
				// ... stop moving and finish driving
				driveTrain.tankDrive(0, 0);
				isFinishedDrive = true;
			}

			// if the target is not in the acceptable range ...
			if(Math.abs(cam.getY()) > DEADZONE_Y)
			{
				// set the direction of the shooter's movement
				moveSpeed = cam.getY() > 0 ? ROTATE_SPEED : -ROTATE_SPEED;

				// move it at that speed
				shooter.rotate(moveSpeed);

				// rotating isn't done
				isFinishedRotate = false;

				// don't track the time
				timeSet = false;
			}
			else
			// otherwise ...
			{
				// stop moving
				shooter.rotate(0);

				// and rotating is done
				isFinishedRotate = true;
			}
		}
		// if the robot lost the object ...
		else
		{
			// kill the driving
			driveTrain.tankDrive(0.0f, 0.0f);
			shooter.rotate(0.0f);

			// if stop time hasn't been set ...
			if(!stopTimeSet)
			{
				// set it
				time = Utility.getFPGATime() + WAIT_TIME;
				stopTimeSet = true;
			}

			// if time has expired ...
			if(Utility.getFPGATime() > time && stopTimeSet)
			{
				// ... we're done here
				lostTarget = true;
			}
			else
			// otherwise ...
			{
				// ... keep going
				stopTimeSet = false;
				lostTarget = false;
			}
		}
	}

	@Override
	public boolean isFinished()
	{
		if(lostTarget)
		{
			return true;
		}
		else
		{
			// if the time has been set...
			if(isFinishedDrive && isFinishedRotate && timeSet)
			{
				// check it and if the time has expired...
				if(Utility.getFPGATime() > time)
				{
					double startShootingTime = 0;
					
					// if shooting time hasn't been set ...
					if(!shootingTimeSet)
					{
						// set it
						startShootingTime = Utility.getFPGATime();
						shootingTimeSet = true;
					}
					else
					{
						// if time isn't up ...
						if(Utility.getFPGATime() < startShootingTime + WAIT_TIME)
						{
							// rev up the shooter
							shooter.shoot(1.0);
						}
						// otherwise ...
						else
						{
							// shoot that ball
							shooter.driveServo(true);
						}
					}
					
					// ...we're done here
					isFinished = true;
					return true;
				}
				return false;
			}
			else
			{
				if(!timeSet)
				{
					// set it
					time = Utility.getFPGATime() + WAIT_TIME;
					timeSet = true;
				}
				return false;
			}
		}
	}

}
