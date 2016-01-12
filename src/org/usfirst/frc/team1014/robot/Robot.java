package org.usfirst.frc.team1014.robot;

import org.usfirst.frc.team1014.robot.commands.ExampleCommand;
import org.usfirst.frc.team1014.robot.subsystems.ExampleSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot
{

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

	public Command autonomousCommand;

	// is logging enabled
	private static boolean CONSOLE_OUTPUT_ENABLED = true;
	// is logging debug mode enabled
	private static boolean DEBUG_ENABLED = true;

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	public void robotInit()
	{
		oi = new OI();
		// instantiate the command used for the autonomous period
		autonomousCommand = new ExampleCommand();
	}

	public void disabledPeriodic()
	{
		Scheduler.getInstance().run();
	}

	public void autonomousInit()
	{
		// schedule the autonomous command (example)
		if(autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}

	public void teleopInit()
	{
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if(autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it to reset subsystems
	 * before shutting down.
	 */
	public void disabledInit()
	{

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic()
	{
		LiveWindow.run();
	}

	/**
	 * logs the string to be outputted with the given status level. Enabled only if the master
	 * boolean is enabled and the debug level only works when debug is enabled. Calls the
	 * getConsoleIdentity method that grabs the identity that is wished to appear in the console.
	 * Most likely, this will just be the class name.
	 * 
	 * @param Level
	 *            status level of the log output
	 * @param String
	 *            id of the class/object that calls the log. Used for display and info purposes.
	 *            Recommended for Commands and SubSystems to use getConsoleIdentity() for the id
	 * 
	 * @param String
	 *            text to be outputted to the console for information to the user
	 */
	public static void log(Level level, String id, String out)
	{
		if(CONSOLE_OUTPUT_ENABLED)
		{
			if(level.equals(Level.Debug) && DEBUG_ENABLED)
				System.out.println("[" + level.getDisplayText() + ":" + id + "] " + out);
			else if(!level.equals(Level.Debug))
				System.out.println("[" + level.getDisplayText() + ":" + id + "] " + out);
		}
	}

	/**
	 * Log level enum. Used for display purposes during logging to show the status level of the
	 * passed message.
	 */
	public enum Level
	{
		Debug("Debug"), Info("Info"), Error("Error");

		private String displayText;

		Level(String display)
		{
			this.displayText = display;
		}

		/**
		 * @return The text form of the enum that is used to display in the console
		 */
		String getDisplayText()
		{
			return this.displayText;
		}
	}
}