package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.RobotMap;

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
		// TODO Auto-generated method stub
		left = new Talon(RobotMap.shooterLeft);
		right = new Talon(RobotMap.shooterRight);
		rotator = new Talon(RobotMap.shooterRotator);
		ringLight = new Relay(RobotMap.ringLight);
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
		// TODO Auto-generated method stub
		return "ShooterAndGrabber";
	}

	@Override
	protected void initDefaultCommand()
	{
		// TODO Auto-generated method stub

	}

}
