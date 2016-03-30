package org.usfirst.frc.team1014.robot.utilities;

import org.usfirst.frc.team1014.robot.Robot;
import org.usfirst.frc.team1014.robot.commands.auto.AutonomousManager;
import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

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

	// private Defense[] defenseToCross = Defense.values();
	// private boolean willShoot = false;
	// private boolean lowScore = true;
	// private double[] defensePos = { 1.0, 2.0, 3.0, 4.0, 5.0 };
	// private double waitTime = 0.0;
	// private boolean justCross = true;
	private boolean genericCross = false;
	private boolean lowBarStay = false;
	private boolean lowBarShoot = false;
	private boolean spyBotShoot = false;

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
		table.putValue("Generic_Cross", genericCross);
		table.putValue("Lowbar_Stay", lowBarStay);
		table.putValue("Lowbar_Shoot", lowBarShoot);
		table.putValue("Spy_Bot_Shoot", spyBotShoot);
		// for(Command command : commandClasses)
		// table.putBoolean(command.getName(), false);
	}

	/**
	 * Goes through the {@link NetworkTable} and adds commands to the scheduler.
	 */
	public void poll()
	{
		// Puts the autonomous string on the Dashboard so the human can see

		if((Boolean) table.getValue("genericCross", true))
		{
			Robot.autonomousCommand = AutonomousManager.getInstance().getAutonomousCommand("Generic_Cross");
			commandToRun = "genericCross";
		}
		else if((Boolean) table.getValue("Lowbar_Stay", true))
		{
			Robot.autonomousCommand = AutonomousManager.getInstance().getAutonomousCommand("Lowbar_Stay");
			commandToRun = "lowBarStay";
		}
		else if((Boolean) table.getValue("Lowbar_Shoot", true))
		{
			Robot.autonomousCommand = AutonomousManager.getInstance().getAutonomousCommand("Lowbar_Shoot");
			commandToRun = "lowBarShoot";
		}
		else if((Boolean) table.getValue("Spy_Bot_Shoot", true))
		{
			Robot.autonomousCommand = AutonomousManager.getInstance().getAutonomousCommand("Spy_Bot_Shoot");
			commandToRun = "spyBotShoot";
		}
		else
		{
			Robot.autonomousCommand = AutonomousManager.getInstance().getAutonomousCommand("Reach_Defense");
			commandToRun = "Reach_Defense";
		}

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

	public void log(String key, String value)
	{
		table.putString(key, value);
	}
}
