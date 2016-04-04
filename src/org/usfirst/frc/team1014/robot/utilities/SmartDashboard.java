package org.usfirst.frc.team1014.robot.utilities;

import java.util.HashMap;

import org.usfirst.frc.team1014.robot.commands.DummyCommand;
import org.usfirst.frc.team1014.robot.commands.auto.AutoDrive;
import org.usfirst.frc.team1014.robot.commands.auto.AutonomousManager;
import org.usfirst.frc.team1014.robot.commands.auto.LowBarShoot;
import org.usfirst.frc.team1014.robot.commands.auto.LowBarStay;
import org.usfirst.frc.team1014.robot.commands.auto.SpyBotShootHigh;
import org.usfirst.frc.team1014.robot.commands.auto.SpyBotShootHighAndMove;
import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * Class that setups and manages the smart dashboard.
 */
public class SmartDashboard
{
	public static SmartDashboard smartDashboard;
	public static NetworkTable table;
	// private ArrayList<Command> commandClasses = CommandBase.commandClasses;
	private static String commandToRun = "";
	private static final String commandRunKey = "Command running: ";
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
	 * Initializes the smart dashboard.
	 */
	private void setup()
	{
		table.putString(commandRunKey, "");
		// table.putValue("defenseToCross", defenseToCross);
		// table.putValue("willShoot", willShoot);
		// table.putValue("lowScore", lowScore);
		// table.putValue("waitTime", waitTime);
		// table.putValue("defensePos", defensePos);
		// table.putValue("justCross", justCross);
		for(String key : table.getKeys())
			table.delete(key);
		for(String str : autoCommands.keySet())
		{
			table.putValue(str, false);
		}
		// for(Command command : commandClasses)
		// table.putBoolean(command.getName(), false);
	}

	/**
	 * Goes through the {@link NetworkTable} and adds commands to the scheduler.
	 */
	public Command poll()
	{
		// Puts the autonomous string on the Dashboard so the human can see

		for(String str : autoCommands.keySet())
		{
			if(table.getBoolean(str, false))
			{
				table.putString(commandRunKey, str);
				return autoCommands.get(str);
			}
		}
		byte switchState = AutonomousManager.pollSwitches();
		commandToRun = "DummyCommand";
		Command defaultCommand = new DummyCommand();
		// Add the A1 and A2 and A3 pins in ControlsManager and make sure to fill the CommandToRun
		// and
		// defaultCommand variables underneath.
		// Logger.logOnce("" + switchState);
		switch(switchState)
		{
			case 0:
				commandToRun = "GenericCross";
				defaultCommand = new AutoDrive(2, .9);
				break;
			case 1:
				commandToRun = "LowBarStay";
				defaultCommand = new LowBarStay();
				break;
			case 2:
				commandToRun = "LowBarShoot";
				defaultCommand = new LowBarShoot();
				break;
			case 3:
				commandToRun = "SpyBotShootHigh";
				defaultCommand = new SpyBotShootHigh();
				break;
			case 4:
				commandToRun = "ReachDefense";
				defaultCommand = new SpyBotShootHighAndMove();
				break;
			case 5:
				commandToRun = "ReachDefense";
				defaultCommand = new AutoDrive(.5, .5);
				break;
			case 6:
				commandToRun = "ReachDefense";
				defaultCommand = new AutoDrive(.5, .5);
				break;
			case 7:
				commandToRun = "ReachDefense";
				defaultCommand = new AutoDrive(.5, .5);
				break;
		}
		table.putString(commandRunKey, commandToRun);
		return defaultCommand;
		/*
		 * if((Boolean) table.getValue("genericCross", true)) { Robot.autonomousCommand =
		 * AutonomousManager.getInstance().getAutonomousCommand("Generic_Cross"); commandToRun =
		 * "genericCross"; } else if((Boolean) table.getValue("Lowbar_Stay", true)) {
		 * Robot.autonomousCommand =
		 * AutonomousManager.getInstance().getAutonomousCommand("Lowbar_Stay"); commandToRun =
		 * "lowBarStay"; } else if((Boolean) table.getValue("Lowbar_Shoot", true)) {
		 * Robot.autonomousCommand =
		 * AutonomousManager.getInstance().getAutonomousCommand("Lowbar_Shoot"); commandToRun =
		 * "lowBarShoot"; } else if((Boolean) table.getValue("Spy_Bot_Shoot", true)) {
		 * Robot.autonomousCommand =
		 * AutonomousManager.getInstance().getAutonomousCommand("Spy_Bot_Shoot"); commandToRun =
		 * "spyBotShoot"; } else { Robot.autonomousCommand =
		 * AutonomousManager.getInstance().getAutonomousCommand("Reach_Defense"); commandToRun =
		 * "Reach_Defense"; }
		 * 
		 * table.putString(commandRunKey, commandToRun);
		 */

	}

	/**
	 * Updates the smart dashboard and displays object tracking information.
	 */
	public void update()
	{
		ProcessedCam.getInstance().setHalfHeight(table.getNumber("IMAGE_HEIGHT", 240) / 2);
		ProcessedCam.getInstance().setHalfWidth(table.getNumber("IMAGE_WIDTH", 320) / 2);
		ProcessedCam.getInstance().setX(table.getNumber("OBJECT_TRACKING_X", 0.0));
		ProcessedCam.getInstance().setY(table.getNumber("OBJECT_TRACKING_Y", 0.0));
		ProcessedCam.getInstance().setTrackingScore(table.getNumber("OBJECT_TRACKING_SCORE", 0.0));
	}

	public void log(String key, String value)
	{
		table.putString(key, value);
	}
}
