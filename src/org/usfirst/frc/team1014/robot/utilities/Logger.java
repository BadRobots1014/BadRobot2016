package org.usfirst.frc.team1014.robot.utilities;

/**
 * 
 * @author Ryan T.
 *
 */
public class Logger
{
	// is logging enabled
	private static boolean CONSOLE_OUTPUT_ENABLED = true;
	// is logging debug mode enabled
	private static boolean DEBUG_ENABLED = true;

	public static void logOnce(String toLog)
	{
		Logger.log(Level.Info, "Simple Log", toLog);
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
				SmartDashboard.getInstance().log(id, out);
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
		Debug("Debug"), Info("Info"), Error("Error"), Warning("Warning");

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
