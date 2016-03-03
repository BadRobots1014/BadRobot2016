package org.usfirst.frc.team1014.robot.utilities;

import java.util.ArrayList;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
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
		for(Command command : commandClasses)
			table.putBoolean(command.getName(), false);
	}

	/**
	 * Goes through the {@link NetworkTable} and adds commands to the scheduler.
	 */
	public void poll()
	{
		for(Command command : commandClasses)
		{
			if(table.getBoolean(command.getName(), false))
			{
				Scheduler.getInstance().add(command);
				commandToRun = command.getName();
				table.putString(commandRunKey, commandToRun);
				break;
			}
		}
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
