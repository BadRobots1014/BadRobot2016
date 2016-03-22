package org.usfirst.frc.team1014.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

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