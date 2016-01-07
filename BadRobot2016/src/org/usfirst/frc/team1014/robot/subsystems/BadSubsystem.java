package org.usfirst.frc.team1014.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class BadSubsystem extends Subsystem {
    protected BadSubsystem()
    {
        initialize();
    }
    
    //is logging enabled
    protected boolean CONSOLE_OUTPUT_ENABLED = true;
	// is debug mode enabled
	protected boolean DEBUG_ENABLED = true;
    
    /**
     * Subclasses should implement their own implementations on initialize. This
     * method is meant to instantiate any hardware or variables that will be 
     * needed. This is specific to each class and can be left blank.
     */
    protected abstract void initialize();
    
    /**
     * logs the string to be outputted with the given status level. Enabled 
     * only if the master boolean is enabled and debug only works when debug is
     * enabled. Calls the getConsoleIdentity method that grabs the identity 
     * that is wished to appear in the console. Most likely, this will just be
     * the class name.
     * @param status level of the log output
     * @param out the string to be outputted
     */
    public void log(Level level, String out)
	{
		if(CONSOLE_OUTPUT_ENABLED)
		{
			if(level.equals(Level.Debug) && this.DEBUG_ENABLED)
			{
				System.out.println("[" + level.getDisplayText() + ": " + this.getConsoleIdentity() + "] " + out);
			}
			else if(!level.equals(Level.Debug))
			{
				System.out.println("[" + level.getDisplayText() + ": " + this.getConsoleIdentity() + "] " + out);
			}
		}
	}
    
    /**
     * @return The String that should appear whenever this Subsystem outputs a String. 
     * Can be whatever you want, most likely the class name though.
     */
    public abstract String getConsoleIdentity();
    
    /**
	 * Log level enum. Used for display purposes during logging to show the status level of the passed message.
	 */
	public enum Level
	{
		Debug("Debug"), Info("Info"), Error("Error");

		private String displayText;

		Level(String display)
		{
			this.displayText = display;
		}

		String getDisplayText()
		{
			return this.displayText;
		}
	}
}
