package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.RobotMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/**
 * This class defines the drive train subsystem and the 
 * abilities to do things like drive.
 * @author Manu S.
 *
 */
public class DriveTrain extends BadSubsystem {

	private static DriveTrain instance;
	RobotDrive train;
	
	SpeedController backLeft, frontLeft, backRight, frontRight;
	
	public static DriveTrain getInstance()
	{
		if(instance == null)
			instance = new DriveTrain();
		
		return instance;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		backLeft = new Talon(RobotMap.backLeftSpeedController);
		frontLeft = new Talon(RobotMap.frontLeftSpeedController);
		backRight = new Talon(RobotMap.backRightSpeedController);
		frontRight = new Talon(RobotMap.frontRightSpeedController);
		
		train = new RobotDrive(backLeft, frontLeft, backRight, frontRight);
	}

	public void tankDrive(double leftStickY, double rightStickY)
	{
		train.tankDrive(leftStickY, rightStickY);
	}
	
	@Override
	public String getConsoleIdentity() {
		// TODO Auto-generated method stub
		return "DriveTrain";
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
