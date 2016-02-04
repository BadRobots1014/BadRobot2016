package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;

public class TestBed extends BadSubsystem {

	public static TestBed instance;
	
	SpeedController talon1, talon2;
	
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
	protected void initialize() {
		// TODO Auto-generated method stub
		talon1 = new BadCAN(RobotMap.talon1, RobotMap.encoderA, RobotMap.encoderB);
		talon2 = new CANTalon(RobotMap.talon2);
	}
	
	public void testSRX(double speed)
	{
		talon1.set(speed); talon2.set(speed);
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
	public String getConsoleIdentity() {
		// TODO Auto-generated method stub
		return "TestBed";
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
