package org.usfirst.frc.team1014.robot.commands;

public class DummyCommandGroup extends BadCommandGroup
{
	@Override
	public void build()
	{
		this.addSequential(new DummyCommand());
	}

	@Override
	public void start()
	{

	}
}