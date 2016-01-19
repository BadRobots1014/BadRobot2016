package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.RobotMap;
import org.usfirst.frc.team1014.robot.sensors.LIDAR;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/**
 * This class defines the drive train subsystem and the abilities to do things like drive.
 * 
 * @author Manu S.
 * 
 */
public class DriveTrain extends BadSubsystem
{
	private RobotDrive train;
	private static DriveTrain instance;
	private SpeedController backLeft, frontLeft, backRight, frontRight;
	private LIDAR lidar;

	public DriveTrain()
	{

	}

	/**
	 * returns the current instance of drive train. If none exists, then it creates a new instance.
	 * 
	 * @return instance of the DriveTrain
	 */
	public static DriveTrain getInstance()
	{
		if(instance == null)
			instance = new DriveTrain();
		return instance;
	}

	@Override
	protected void initialize()
	{
		backLeft = new Talon(RobotMap.backLeftSpeedController);
		frontLeft = new Talon(RobotMap.frontLeftSpeedController);
		backRight = new Talon(RobotMap.backRightSpeedController);
		frontRight = new Talon(RobotMap.frontRightSpeedController);
		
		lidar = new LIDAR(Port.kMXP);

		train = new RobotDrive(backLeft, frontLeft, backRight, frontRight);
		System.out.println("here");
	}

	public void tankDrive(double leftStickY, double rightStickY)
	{
		train.tankDrive(leftStickY, rightStickY);
	}

	public double getLIDARDistance()
	{
		lidar.updateDistance();
		return lidar.getDistance();
	}
	
	@Override
	public String getConsoleIdentity()
	{
		return "DriveTrain";
	}

	@Override
	protected void initDefaultCommand()
	{

	}
}
