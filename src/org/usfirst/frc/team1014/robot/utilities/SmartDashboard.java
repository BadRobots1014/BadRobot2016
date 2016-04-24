package org.usfirst.frc.team1014.robot.utilities;

import java.util.HashMap;

import org.usfirst.frc.team1014.robot.commands.auto.AutonomousManager;
import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * Class that setups and manages the smart dashboard. It also contains methods to set autonomous
 * commands as well as manage camera feed and vision tracking values.
 */
public class SmartDashboard
{
	public static SmartDashboard smartDashboard; // the instance of the Smart Dashboard
	public static NetworkTable table; // the NetworkTable object 
	private static String commandToRun = ""; // the current running command
	private static final String commandRunKey = "Command running: "; // a header to make things look nicer
	
	// all of the autonomous commands sorted by the number assigned to them in AutonomousManager
	private static HashMap<String, Command> autoCommands = new HashMap<String, Command>(); 
	private USBCamera camera;

	// private Defense[] defenseToCross = Defense.values();
	// private boolean willShoot = false;
	// private boolean lowScore = true;
	// private double[] defensePos = { 1.0, 2.0, 3.0, 4.0, 5.0 };
	// private double waitTime = 0.0;
	// private boolean justCross = true;

	public SmartDashboard()
	{
		// this is to set up the USB camera on the Java dashboard
		camera = new USBCamera("cam2");
		camera.openCamera();
		CameraServer.getInstance().startAutomaticCapture(camera);

		table = NetworkTable.getTable("SmartDashboard");
		setup();
	}

	/**
	 * @return a not null instance of {@code SmartDashboard}.
	 */
	public static SmartDashboard getInstance()
	{
		if(smartDashboard == null)
			smartDashboard = new SmartDashboard();
		return smartDashboard;
	}

	/**
	 * Initializes the smart dashboard by putting all the strings and other inputs on it for the
	 * humans to input.
	 */
	private void setup()
	{
		NetworkTable.globalDeleteAll();
		table.putString(commandRunKey, "");
		table.putString("Driver Controller", "Default");
		table.putString("Shooter Controller", "Default");
		// table.putValue("defenseToCross", defenseToCross);
		// table.putValue("willShoot", willShoot);
		// table.putValue("lowScore", lowScore);
		// table.putValue("waitTime", waitTime);
		// table.putValue("defensePos", defensePos);
		// table.putValue("justCross", justCross);
		for(String str : autoCommands.keySet())
		{
			table.putValue(str, false);
		}
		// for(Command command : commandClasses)
		// table.putBoolean(command.getName(), false);
	}

	/**
	 * Looks through the booleans added to the dashboard and if none of them are true, gets the
	 * values from the autonomous switches and uses that to select autonomous.
	 */
	public Command poll()
	{
		/*
		 * Runs through the various selectable autonomous commands on the dashboard, if any are
		 * enabled, then priority is given to that one over the autonomous panel switches
		 */
		for(String str : autoCommands.keySet())
		{
			if(table.getBoolean(str, false))
			{
				table.putString(commandRunKey, str);
				return autoCommands.get(str);
			}
		}

		// gets the sum of the switches
		byte switchState = AutonomousManager.pollSwitches();

		// pulls the appropriate Command from the list and returns it
		CustomEntry<String, Command> command = AutonomousManager.getInstance().getAutonomousCommand(switchState);
		Command defaultCommand = command.getValue();
		commandToRun = command.getKey();
		table.putString(commandRunKey, commandToRun);
		return defaultCommand;
	}

	/**
	 * Updates the smart dashboard and displays object tracking information. These values are placed
	 * on the dashboard from RoboRealm.
	 */
	public void updateVisionTracking()
	{
		ProcessedCam.getInstance().setHalfHeight(table.getNumber("IMAGE_HEIGHT", 240) / 2);
		ProcessedCam.getInstance().setHalfWidth(table.getNumber("IMAGE_WIDTH", 320) / 2);
		ProcessedCam.getInstance().setX(table.getNumber("OBJECT_TRACKING_X", 0.0));
		ProcessedCam.getInstance().setY(table.getNumber("OBJECT_TRACKING_Y", 0.0));
		ProcessedCam.getInstance().setTrackingScore(table.getNumber("OBJECT_TRACKING_SCORE", 0.0));
	}

	/**
	 * An experimental thing Turk was trying.
	 * 
	 * @param key
	 *            - the label of the message
	 * @param value
	 *            - the actual message you want
	 */
	public void log(String key, String value)
	{
		table.putString(key, value);
	}
}
