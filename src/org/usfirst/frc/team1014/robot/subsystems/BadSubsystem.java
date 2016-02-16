
package org.usfirst.frc.team1014.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An abstract class with the minimums for a subsystem.
 */
public abstract class BadSubsystem extends Subsystem
{
	protected BadSubsystem()
	{
		initialize();
	}

	/**
	 * Subclasses should implement their own implementations on initialize. This method is meant to
	 * instantiate any hardware or variables that will be needed. This is specific to each class and
	 * can be left blank.
	 */
	protected abstract void initialize();

	/**
	 * @return The String that should appear whenever this Subsystem outputs a String. Can be
	 *         whatever you want, most likely the class name though.
	 */
	public abstract String getConsoleIdentity();
}

