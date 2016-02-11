package org.usfirst.frc.team1014.robot.controls;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class ControlsManager {
	// Button Mapping
	public static final int BACK_LEFT_SPEED_CONTROLLER = 1;
	public static final int FRONT_LEFT_SPEED_CONTROLLER = 5;
	public static final int BACK_RIGHT_SPEED_CONTROLLER = 2;
	public static final int FRONT_RIGHT_SPEED_CONTROLLER = 6;

	public static final int SHOOTER_LEFT = 3;
	public static final int SHOOTER_RIGHT = 4;
	public static final int SHOOTER_ROTATE = 7;
	public static final int RING_LIGHT = 9;

	public static final int PUSHER = 8;
	
	//DIO
	public static final int SHOOTER_ENCODER_A = 8;
	public static final int SHOOTER_ENCODER_B = 9;
	
	// Analog In
	public static final int MAXBOTIX_ULTRASONIC = 0;

	public static DriverStation driverStation;
	public static XboxController primaryXboxController, secondaryXboxController;

	public static void init() {
		driverStation = DriverStation.getInstance();
		primaryXboxController = new XboxController(0);
		secondaryXboxController = new XboxController(1);
	}

	/**
	 * 
	 * @param number
	 *            of the controller to get the layout of (1 = primary, 2 =
	 *            secondary)
	 * @return number of the layout that should be used with the current
	 *         controller ( 0 = Default, 1&2 = Alternate layouts)
	 */
	public static int getLayout(int controller) {
		if (controller == 1) {
			if (primaryXboxController.isLBButtonPressed())
				return 1;
			else if (primaryXboxController.isRBButtonPressed())
				return 2;
			else
				return 0;
		} else {
			if (secondaryXboxController.isLBButtonPressed())
				return 1;
			else if (secondaryXboxController.isRBButtonPressed())
				return 2;
			else
				return 0;
		}
	}
}
