package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class Shooter extends BadSubsystem {

	public static Shooter instance;
	
	private SpeedController left, right;
	
	public Shooter getInstance()
	{
		if(instance == null)
			instance = new Shooter();
		
		return instance;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		left = new Talon(RobotMap.shooterLeft);
		right = new Talon(RobotMap.shooterRight);
	}

	public void shoot(double speed)
	{
		left.set(speed);
		right.set(-speed);
	}
	
	@Override
	public String getConsoleIdentity() {
		// TODO Auto-generated method stub
		return "Shooter";
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
