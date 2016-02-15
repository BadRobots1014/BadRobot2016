package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;
import org.usfirst.frc.team1014.robot.utilities.Logger;

/**
 * A command that demos the object tracking code.
 */
public class ObjectTrackingTest extends CommandBase
{
	private static final int RING_LIGHT_BLINK_DELAY = 100; // Delat between each ring light toggle

	private static final int RING_LIGHT_BLINK_COUNTS = 50; // Times to blink light
	
	Runnable run;
	Thread thread;

	boolean isFinishedRotate, isFinishedDrive;

	/**
	 * Flashes the ring light {code RING_LIGHT_BLINK_COUNTS} times with a {@code RING_LIGHT_BLINK_DELAY} millisecond delay between each toggle.
	 * Total running time should be {@literal 2 * RING_LIGHT_BLINK_COUNTS * RING_LIGHT_BLINK_DELAY} in milliseconds.
	 * <br /><br />
	 * <b>This method should only be called from a new {@link Thread} created in {@code initialize()}.</b>
	 */
	private static void blinkRingLight() {
		for(int i = 0; i < RING_LIGHT_BLINK_COUNTS; i++)
		{
			shooter.ringLightOn();
			try
			{
				Thread.sleep(RING_LIGHT_BLINK_DELAY);
			} catch(InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			shooter.ringLightOff();
			try
			{
				Thread.sleep(RING_LIGHT_BLINK_DELAY);
			} catch(InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ObjectTrackingTest()
	{
		requires(driveTrain);
		requires(shooter);
	}

	@Override
	/**
	 * {@inheritDoc}
	 * Creates a new {@link Thread} that toggles the light.
	 */
	protected void initialize()
	{
		driveTrain.tankDrive(0, 0);
		shooter.ringLightOn();
		run = (ObjectTrackingTest::blinkRingLight);
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
		Logger.logThis(getConsoleIdentity() + " interupted");
		end();
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
