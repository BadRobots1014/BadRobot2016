package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class ShooterAndGrabber extends BadSubsystem
{

	public static ShooterAndGrabber instance;

	private SpeedController left, right, rotator;
	private Relay ringLight;

	public static ShooterAndGrabber getInstance()
	{
		if(instance == null)
			instance = new ShooterAndGrabber();

		return instance;
	}

	@Override
	protected void initialize()
	{
		left = new Talon(ControlsManager.SHOOTER_LEFT);
		right = new Talon(ControlsManager.SHOOTER_RIGHT);
		rotator = new Talon(ControlsManager.SHOOTER_ROTATE);
		ringLight = new Relay(ControlsManager.RING_LIGHT);
	}

	public void rotate(double speed)
	{
		rotator.set(speed);
	}

	public void shoot(double speed)
	{
		left.set(speed);
		right.set(-speed);
	}

	public void grab(double speed)
	{
		left.set(-speed);
		right.set(speed);
	}

	public void ringLightOn()
	{
		ringLight.set(Relay.Value.kOn);
	}

	public void ringLightOff()
	{
		ringLight.set(Relay.Value.kOff);
	}

	public void killRingLight()
	{
		ringLight.free();
	}

	@Override
	public String getConsoleIdentity()
	{
		return "ShooterAndGrabber";
	}

	@Override
	protected void initDefaultCommand()
	{

	}

}
