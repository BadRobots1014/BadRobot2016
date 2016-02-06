package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;

public class TestBed extends BadSubsystem
{

	public static TestBed instance;

	private SpeedController talon1, talon2;

	public TestBed()
	{

	}

	public static TestBed getInstance()
	{
		if(instance == null)
			instance = new TestBed();

		return instance;
	}

	@Override
	protected void initialize()
	{
		talon1 = new BadCAN(ControlsManager.BACK_LEFT_SRX, ControlsManager.encoderA, ControlsManager.encoderB);
		talon2 = new CANTalon(ControlsManager.BACK_RIGHT_SRX);
	}

	public void testSRX(double speed)
	{
		talon1.set(speed);
		talon2.set(speed);
	}

	public double getEncoderRPM()
	{
		return ((BadCAN) talon1).getRpm();
	}

	public double getEncoderValue()
	{
		return ((BadCAN) talon1).encoder.getDistance();
	}

	@Override
	public String getConsoleIdentity()
	{
		return "TestBed";
	}

	@Override
	protected void initDefaultCommand()
	{

	}
}