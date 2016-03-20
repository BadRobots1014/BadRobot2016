package org.usfirst.frc.team1014.robot.utilities;

import java.util.ArrayList;

import org.usfirst.frc.team1014.robot.Robot;
import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.commands.DummyCommand;
import org.usfirst.frc.team1014.robot.commands.auto.AutoDrive;
import org.usfirst.frc.team1014.robot.commands.auto.LowBarShoot;
import org.usfirst.frc.team1014.robot.commands.auto.LowBarStay;
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
	private static String commandToRun = "";
	private static final String commandRunKey = "Command running: ";

	private String[] defenseToCross = { "LowBar", "Portcullis", "SallyPort", "ChevalDeFrise", "Drawbridge", "Generic", "None" };
	private boolean willShoot = false;
	private boolean lowScore = true;
	private double[] defensePos = { 1.0, 2.0, 3.0, 4.0, 5.0 };
	private double waitTime = 0.0;
	private boolean justCross = true;
	private boolean genericCross = false;
	private boolean lowBarStay = false;
	private boolean lowBarShoot = false;

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
		// table.putValue("defenseToCross", defenseToCross);
		// table.putValue("willShoot", willShoot);
		// table.putValue("lowScore", lowScore);
		// table.putValue("waitTime", waitTime);
		// table.putValue("defensePos", defensePos);
		// table.putValue("justCross", justCross);
		table.putValue("genericCross", genericCross);
		table.putValue("lowBarStay", lowBarStay);
		table.putValue("lowBarShoot", lowBarShoot);
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
		// Puts the autonomous string on the Dashboard so the human can see
		if((Boolean) table.getValue("genericCross", true))
		{
			Robot.autonomousCommand = new AutoDrive(4, .9);
			commandToRun = "genericCross";
		}
		else if((Boolean) table.getValue("lowBarStay", true))
		{
			Robot.autonomousCommand = new LowBarStay();
			commandToRun = "lowBarStay";
		}
		else if((Boolean) table.getValue("lowBarShoot", true))
		{
			Robot.autonomousCommand = new LowBarShoot();
			commandToRun = "lowBarShoot";
		}
		else
		{
			Robot.autonomousCommand = new DummyCommand();
			commandToRun = "DummyCommand";
		}
		// else
		// {
		// String defenseValue = (String) table.getValue("defenseToCross", "None");
		// boolean willShootValue = (boolean) table.getValue("willShoot", false);
		// boolean lowScoreValue = (boolean) table.getValue("lowScore", true);
		// double waitTimeValue = (double) table.getValue("waitTime", 0.0);
		// int defensePosValue = (int) table.getValue("defensePos", 1);
		// boolean justCrossValue = (boolean) table.getValue("justCross", true);
		//
		// ((BadAutonomous) Robot.autonomousCommand).setVariables(willShootValue, lowScoreValue,
		// defensePosValue, BadAutonomous.Defense.valueOf(defenseValue), waitTimeValue,
		// justCrossValue);
		//
		// commandToRun = "I will wait " + waitTimeValue + " seconds before crossing the " +
		// defenseValue + " in the " + defensePosValue + " position and I will shoot: " +
		// willShootValue + ", in the low goal: " + lowScoreValue + ", I am planning on just
		// crossing the defense:" + justCrossValue;
		// }
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
