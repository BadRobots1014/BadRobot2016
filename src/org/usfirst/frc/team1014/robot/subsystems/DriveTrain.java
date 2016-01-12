package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.Robot;
import org.usfirst.frc.team1014.robot.Robot.Level;
import org.usfirst.frc.team1014.robot.RobotMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/**
 * This class defines the drive train subsystem and the abilities to do things
 * like drive.
 * 
 * @author Manu S.
 *
 */
public class DriveTrain extends BadSubsystem {

	private RobotDrive train;
	
	private SpeedController backLeft, frontLeft, backRight, frontRight;

	public DriveTrain() {

	}

	@Override
	protected void initialize() {

		Robot.log(Level.Debug, "0001", "out message");
		backLeft = new Talon(RobotMap.backLeftSpeedController);
		frontLeft = new Talon(RobotMap.frontLeftSpeedController);
		backRight = new Talon(RobotMap.backRightSpeedController);
		frontRight = new Talon(RobotMap.frontRightSpeedController);

		train = new RobotDrive(backLeft, frontLeft, backRight, frontRight);
	}

	public void tankDrive(double leftStickY, double rightStickY) {
		train.tankDrive(leftStickY, rightStickY);
	}

	@Override
	public String getConsoleIdentity() {
		return "DriveTrain";
	}

	@Override
	protected void initDefaultCommand() {

	}

}
