package org.usfirst.frc.team1014.robot.controls;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1014.robot.controls.layouts.DriveButtonInvertLayout;
import org.usfirst.frc.team1014.robot.controls.layouts.LayoutTurk;
import org.usfirst.frc.team1014.robot.utilities.CustomEntry;
import org.usfirst.frc.team1014.robot.utilities.Logger;
import org.usfirst.frc.team1014.robot.utilities.Logger.Level;
import org.usfirst.frc.team1014.robot.utilities.SmartDashboard;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands
 * and command groups that allow control of the robot.
 * 
 * @author Ryan T.
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
	public static final int OPTICAL_SENSOR_PING = 8;

	public static final int LED_BIT1 = 4;

	public static final int A1SWITCH = 1;
	public static final int A2SWITCH = 2;
	public static final int A3SWITCH = 3;

	// Analog In
	public static final int MAXBOTIX_ULTRASONIC = 0;

	// CAN
	public static final int SHOOTER_LEFT = 1;
	public static final int SHOOTER_RIGHT = 2;
	public static final int SHOOTER_ROTATE = 3;
	public static final int SALLY_PORT_ARM = 4;

	// Relay
	public static final int RING_LIGHT = 0;

	// Layouts
	private static List<CustomEntry<String, ControllerLayout>> driverLayouts = new ArrayList<CustomEntry<String, ControllerLayout>>();
	private static List<CustomEntry<String, ControllerLayout>> shooterLayouts = new ArrayList<CustomEntry<String, ControllerLayout>>();
	
	public static DriverStation driverStation;
	
	// sets the default layouts for controllers
	public static final ControllerLayout DEFAULT0 = new ControllerLayout(0);
	public static final ControllerLayout DEFAULT1 = new ControllerLayout(1);
	
	// Controllers (driver = primary, shooter = secondary)
	public static ControllerLayout driver;
	public static ControllerLayout shooter;

	/**
	 * Initializes controls for the robot. Should only be called once when the robot first loads up.
	 */
	public static void init()
	{
		driverLayouts.add(new CustomEntry<String, ControllerLayout>("Default", new ControllerLayout(0)));
		driverLayouts.add(new CustomEntry<String, ControllerLayout>("ButtonsInverse", new DriveButtonInvertLayout(0)));

		shooterLayouts.add(new CustomEntry<String, ControllerLayout>("Default", new ControllerLayout(1)));
		shooterLayouts.add(new CustomEntry<String, ControllerLayout>("Turk", new LayoutTurk(1)));

		driverStation = DriverStation.getInstance();
		ControlsManager.updateControllers();
	}

	/**
	 * Sets the controller layout based on the values inputted from the Driver Station.
	 */
	public static void updateControllers()
	{
		String driverController = SmartDashboard.table.getString("Driver Controller", "Default");
		String shooterController = SmartDashboard.table.getString("Shooter Controller", "Default");

		boolean set = false;
		for(CustomEntry<String, ControllerLayout> entry : driverLayouts)
		{
			if(entry.getKey().equals(driverController))
			{
				driver = entry.getValue();
				set = true;
			}
		}
		if(!set)
		{
			Logger.log(Level.Error, "Controller", "Layout " + driverController + " could not be found for a driver controller. Switching to default.");
			driver = DEFAULT0;
		}

		set = false;
		for(CustomEntry<String, ControllerLayout> entry : shooterLayouts)
		{
			if(entry.getKey().equals(shooterController))
			{
				shooter = entry.getValue();
				set = true;
			}
		}
		if(!set)
		{
			Logger.log(Level.Error, "Controller", "Layout " + driverController + " could not be found for a shooter controller. Switching to default.");
			shooter = DEFAULT1;
		}
	}

	public static void changeToSecondaryLayout(int controller)
	{
		if(controller == 1)
			driver.controller.changeLayout(false);
		else if(controller == 2)
			shooter.controller.changeLayout(false);
	}

	public static void changeToPrimaryLayout(int controller)
	{
		if(controller == 1)
			driver.controller.changeLayout(true);
		else if(controller == 2)
			shooter.controller.changeLayout(true);
	}
}
