package org.usfirst.frc.team1014.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into to a variable name.
 * This provides flexibility changing wiring, makes checking the wiring easier and significantly
 * reduces the number of magic numbers floating around.
 */
public class RobotMap
{
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;

	//PWM
	public static int backLeftSpeedController = 1;
	public static int frontLeftSpeedController = 4;
	public static int backRightSpeedController = 2;
	public static int frontRightSpeedController = 3;
	public static int ringLight = 0;
	public static int shooterLeft = 5;
	public static int shooterRight = 6;
	
	//DIO
	public static int ultraPing = 1;
	public static int ultraEcho = 2;
	public static int retroSensor = 0;
}
