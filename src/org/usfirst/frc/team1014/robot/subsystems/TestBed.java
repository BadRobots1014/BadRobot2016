package org.usfirst.frc.team1014.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;

public class TestBed extends BadSubsystem
{

	public static TestBed instance;

	private CANTalon talon2;

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
		// talon1 = new BadCAN(ControlsManager.BACK_LEFT_SRX, ControlsManager.encoderA,
		// ControlsManager.encoderB);
		talon2 = new CANTalon(6);
	}

	public void testSRX(double speed)
	{
		// talon1.set(speed);
		talon2.set(speed);
	}

	public double getEncoderRPM()
	{
		return talon2.getSpeed();
	}

	public double getEncoderValue()
	{
		return talon2.getPosition();
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