package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.commands.auto.DummyCommand;

public class DummyCommandGroup extends BadCommandGroup
{
	@Override
	public void build()
	{
		this.addSequential(new DummyCommand(0));
	}

	@Override
	public void start()
	{

	}
}