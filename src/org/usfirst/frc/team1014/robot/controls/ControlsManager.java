package org.usfirst.frc.team1014.robot.controls;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands
 * and command groups that allow control of the robot.
 */
public class ControlsManager
{
	//Button Mapping
	public static final int BACK_LEFT_SPEED_CONTROLLER = 1;
	public static final int FRONT_LEFT_SPEED_CONTROLLER = 5;
	public static final int BACK_RIGHT_SPEED_CONTROLLER = 2;
	public static final int FRONT_RIGHT_SPEED_CONTROLLER = 6;
	
	public static final int SHOOTER_LEFT = 3;
	public static final int SHOOTER_RIGHT = 4;
	public static final int SHOOTER_ROTATE = 7;
	public static final int RING_LIGHT = 9;
	
	public static final int PUSHER = 8;

	// DIO
	public static final int ULTRA_PING = 1;
	public static final int ULTRA_ECHO = 2;
	public static final int RETRO_SENSOR = 0;

	public static DriverStation driverStation;
	public static XboxController primaryXboxController, secondaryXboxController;

	public static void init()
	{
		driverStation = DriverStation.getInstance();
		primaryXboxController = new XboxController(0);
		secondaryXboxController = new XboxController(1);
	}
}
