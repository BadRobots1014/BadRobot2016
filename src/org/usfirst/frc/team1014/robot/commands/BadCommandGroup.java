package org.usfirst.frc.team1014.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This class is to replace {@code CommandGroup}. Ask Turk more about it.
 * 
 * @author Ryan T.
 *
 */
public class BadCommandGroup extends CommandGroup
{
	private Command[] commands;

	public BadCommandGroup(Command... commands)
	{
		this.commands = commands;
	}

	public void build()
	{
		for(Command c : this.commands)
			this.addSequential(c);
	}
}