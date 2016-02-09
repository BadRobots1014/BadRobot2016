package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.sensors.IMU;
import org.usfirst.frc.team1014.robot.sensors.LIDAR;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
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
	private CANTalon backLeft, frontLeft, backRight, frontRight;
	private LIDAR lidar;
	private Ultrasonic ultrasonic;

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
		Logger.log(Logger.Level.Debug, "0001", "out message");
		backLeft = new CANTalon(ControlsManager.BACK_LEFT_SRX);
		frontLeft = new CANTalon(ControlsManager.FRONT_LEFT_SRX);
		backRight = new CANTalon(ControlsManager.BACK_RIGHT_SRX);
		frontRight = new CANTalon(6);

		lidar = new LIDAR(Port.kMXP);
		ultrasonic = new Ultrasonic(ControlsManager.ULTRA_PING, ControlsManager.ULTRA_ECHO);
		ultrasonic.setEnabled(true);
		ultrasonic.setAutomaticMode(true);

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

	/*
	 * public void turnOnRingLight() { ringLight.set(.1); }
	 * 
	 * public void turnOffRingLight() { ringLight.set(0.0); }
	 */

	public double getLIDARDistance()
	{
		lidar.updateDistance();
		return lidar.getDistance();
	}

	public double getUltraDistance(boolean inInches)
	{
		if(inInches)
			return ultrasonic.getRangeInches();
		else return ultrasonic.getRangeMM();
	}

	public double getAngle()// return -180 - 180
	{
		return mxp.getYaw();
	}

	public double getAngle360() // returns 0 -360
	{
		if(mxp.getYaw() < 0)
			return mxp.getYaw() + 360;
		else return mxp.getYaw();
	}

	public void resetMXPAngle()
	{
		mxp.zeroYaw();
	}

	public IMU getMXP()
	{
		return mxp;
	}
	
	public double getEncoderRPM()
	{
		return frontRight.getSpeed();
	}

	public double getEncoderValue()
	{
		return frontRight.getPosition();
	}
	
	public double getRange()
	{
		return this.ultrasonic.getRangeInches();
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
