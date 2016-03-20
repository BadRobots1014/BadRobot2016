package org.usfirst.frc.team1014.robot.controls;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands
 * and command groups that allow control of the robot.
 */
public class ControlsManager
{
	// PWM

	public static final int BACK_LEFT_SPEED_CONTROLLER = 1;
	public static final int FRONT_LEFT_SPEED_CONTROLLER = 5;
	public static final int BACK_RIGHT_SPEED_CONTROLLER = 2;
	public static final int FRONT_RIGHT_SPEED_CONTROLLER = 6;

	public static final int PUSHER = 8;

	// DIO

	// public static final int FRONT_RIGHT_ENCODER_A = 10;
	// public static final int FRONT_RIGHT_ENCODER_B = 11;
	// public static final int FRONT_LEFT_ENCODER_A = 4;
	// public static final int FRONT_LEFT_ENCODER_B = 5;
	public static final int BACK_RIGHT_ENCODER_A = 2;
	public static final int BACK_RIGHT_ENCODER_B = 3;
	public static final int BACK_LEFT_ENCODER_A = 0;
	public static final int BACK_LEFT_ENCODER_B = 1;

	public static final int SHOOTER_LEFT_ENCODER_A = 8;
	public static final int SHOOTER_LEFT_ENCODER_B = 9;
	public static final int ARTICULATOR_ENCODER_A = 6;
	public static final int ARTICULATOR_ENCODER_B = 7;

	public static final int LIMIT_SWITCH = 4;

	// LED Lights
	public static final int LED_BIT1 = 4;
	public static final int LED_BIT2 = 5;

	// Analog In
	public static final int MAXBOTIX_ULTRASONIC = 0;

	// CAN
	public static final int SHOOTER_LEFT = 1;
	public static final int SHOOTER_RIGHT = 2;
	public static final int SHOOTER_ROTATE = 3;
	public static final int SALLY_PORT_ARM = 4;

	// Relay

	public static final int RING_LIGHT = 0;

	public static DriverStation driverStation;
	public static XboxController primaryXboxController, secondaryXboxController;

	/**
	 * Initializes controls for the robot. Should only be called once when the robot first loads up.
	 */
	public static void init()
	{
		driverStation = DriverStation.getInstance();
		primaryXboxController = new XboxController(0, true);
		secondaryXboxController = new XboxController(1, true);
	}

	public static void changeToSecondaryLayout(int controller)
	{
		if(controller == 1)
			primaryXboxController.changeLayout(false);
		else if(controller == 2)
			secondaryXboxController.changeLayout(false);
	}

	public static void changeToPrimaryLayout(int controller)
	{
		if(controller == 1)
			primaryXboxController.changeLayout(true);
		else if(controller == 2)
			secondaryXboxController.changeLayout(true);
	}
}
