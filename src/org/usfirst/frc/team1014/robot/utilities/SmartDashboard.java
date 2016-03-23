package org.usfirst.frc.team1014.robot.utilities;

import java.util.ArrayList;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Class that setups and manages the smart dashboard.
 */
public class SmartDashboard
{
	public static SmartDashboard smartDashboard;
	public static NetworkTable table;
	private ArrayList<Command> commandClasses = CommandBase.commandClasses;
	private static String commandToRun;
	private static final String commandRunKey = "Command running: ";
	private String[] crossDefense = { "L", "P", "C", "S", "D" };
	private double[] defensePos = { 1.0, 2.0, 3.0, 4.0, 5.0 };
	private boolean[] goingForLow = { true, false };
	private boolean[] willShoot = { true, false };
	private double waitTimeInSec = 0;

	public SmartDashboard()
	{
		table = NetworkTable.getTable("SmartDashboard");
		setup();
	}

	/**
	 * @return a not null instance of {@code SmartDashboard}.
	 */
	public static SmartDashboard getInstance()
	{
		if(smartDashboard == null)
		{
			smartDashboard = new SmartDashboard();
		}
		return smartDashboard;
	}

	/**
	 * Initializes the smart dashboard.
	 */
	private void setup()
	{
		table.putString(commandRunKey, "");
		table.putValue("WillShoot", willShoot);
		table.putValue("LowScore", goingForLow);
		table.putValue("Position", defensePos);
		table.putValue("CrossDefense", crossDefense);
		table.putValue("waitTimeInSec", waitTimeInSec);

		table.putBoolean("Low Bar Auto", false);
		table.putBoolean("Generic Auto", false);

		// for(Command command : commandClasses)
		// table.putBoolean(command.getName(), false);
	}

	/**
	 * Goes through the {@link NetworkTable} and adds commands to the scheduler.
	 */
	public void poll()
	{
		// for(Command command : commandClasses)
		// {
		// if(table.getBoolean(command.getName(), false))eckout test
		// {
		// Scheduler.getInstance().add(command);
		// commandToRun = command.getName();
		// table.putString(commandRunKey, commandToRun);
		// break;
		// }
		// }

		// Gets values from the Smart Dashboard for autonomous
		boolean willShootValue = (boolean) table.getValue("WillShoot", true);
		boolean lowScoreValue = (boolean) table.getValue("LowScore", true);
		int position = (int) table.getValue("Position", 1.0);
		String defense = (String) table.getValue("CrossDefense", "L");
		double waitTime = (double) table.getValue("waitTimeInSec", 0);
//		Robot.autonomousCommand.setVariables(willShootValue, lowScoreValue, position, defense, waitTime);
		// Puts the autonomous string on the Dashboard so the human can see
		commandToRun = "BadAutonomous: Shooting In Auto " + willShootValue + ", Going for High Goal " + lowScoreValue + ", Over Defense " + position + ", Which Defense To Cross " + defense + ", with a wait of " + waitTime;

		table.putString(commandRunKey, commandToRun);
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
}
