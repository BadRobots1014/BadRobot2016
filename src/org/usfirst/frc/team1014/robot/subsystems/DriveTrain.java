package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.sensors.BadUltrasonic;
import org.usfirst.frc.team1014.robot.sensors.IMU;
import org.usfirst.frc.team1014.robot.sensors.LIDAR;
import org.usfirst.frc.team1014.robot.utilities.Logger;
import org.usfirst.frc.team1014.robot.utilities.PID;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;

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
	private Ultrasonic ultrasonic;
	private BadUltrasonic maxbotix;
	private IMU mxp;
	private SerialPort serialPort;

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
		backLeft = new Talon(ControlsManager.BACK_LEFT_SPEED_CONTROLLER);
		frontLeft = new Talon(ControlsManager.FRONT_LEFT_SPEED_CONTROLLER);
		backRight = new Talon(ControlsManager.BACK_RIGHT_SPEED_CONTROLLER);
		frontRight = new Talon(ControlsManager.FRONT_RIGHT_SPEED_CONTROLLER);

		lidar = new LIDAR(Port.kMXP);

		// ultrasonic = new Ultrasonic(RobotMap.ultraPing, RobotMap.ultraEcho);
		// ultrasonic.setEnabled(true); ultrasonic.setAutomaticMode(true);

		maxbotix = new BadUltrasonic(ControlsManager.MAXBOTIX_ULTRASONIC);

		// mxp stuff
		serialPort = new SerialPort(57600, SerialPort.Port.kMXP);

		byte update_rate_hz = 127;
		mxp = new IMU(serialPort, update_rate_hz);
		Timer.delay(0.3);
		mxp.zeroYaw();

		train = new RobotDrive(backLeft, frontLeft, backRight, frontRight);
	}

	public void tankDrive(double leftStickY, double rightStickY)
	{
		train.tankDrive(leftStickY, rightStickY);
	}


	/**
	 * This method allows the robot to go straight with just two parameters. The robot first
	 * calculates how far off it is from the target angle, then checks if that is large enough to
	 * act on. It then uses the proportional part of PID to calculate how fast it needs to turn to
	 * correct its angle. Finally, it turns the robot to come back to the proper angle. If the robot
	 * was never off in the first place, then it just drives forward at uniform speed.
	 * 
	 * @param moveSpeed
	 *            - the speed at which to move if the angle is OK
	 * @param targetGyro
	 *            - the angle the robot wants to correct to
	 */

	public void driveStraight(double moveSpeed, double targetGyro)
  	{
		double difference180 = targetGyro - getAngle();
		
//		double difference360 = difference180 - 360;
//		double realDifference = 0;
//		
		double turnSpeed = 0;
//		if(Math.abs(difference360) < Math.abs(difference180))
//		{
//			realDifference = difference360;
//		}
//		else
//		{
//			realDifference = difference180;
//		}
		
		if(Math.abs(difference180) > 5)
		{
			turnSpeed = moveSpeed * PID.trigScale(Math.toRadians(difference180), -Math.PI, Math.PI);
			
			if(Math.abs(turnSpeed) > 1)
				turnSpeed = 1 * turnSpeed / Math.abs(turnSpeed);
			
			tankDrive(-turnSpeed, turnSpeed);
		}
		else
		{
			tankDrive(moveSpeed, moveSpeed);
		}
	}

	public double getLIDARDistance()
	{
		lidar.updateDistance();
		return lidar.getDistance();
	}


	/**
	 * This method returns the distance to the nearest object in inches from the Maxbotix sensor.
	 * 
	 * @return - the distance to the nearest object in inches
	 */
	public double getMaxbotixDistance()
	{
		return maxbotix.getDistance();
	}

	public double getUltraDistance(boolean inInches)
	{
		if(inInches)
			return ultrasonic.getRangeInches();
		else
			return ultrasonic.getRangeMM();
	}

	public double getAngle()// return -180 - 180
	{
		return (double) mxp.getYaw();
	}

	public double getAngle360() // returns 0 -360
	{
		if(mxp.getYaw() < 0)
			return mxp.getYaw() + 360;
		else
			return mxp.getYaw();
	}

	public void resetMXPAngle()
	{
		mxp.zeroYaw();
	}

	public IMU getMXP()
	{
		return mxp;
	}

	@Override
	public String getConsoleIdentity()
	{
		return "DriveTrain";
	}

	public float getRoll() {
		return mxp.getRoll();
	}
	
	
	@Override
	protected void initDefaultCommand()
	{

	}
}
