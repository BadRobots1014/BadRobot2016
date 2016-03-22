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

	class DummyCommand extends CommandBase
	{

		@Override
		protected void initialize()
		{
		}

		@Override
		public String getConsoleIdentity()
		{
			return "Dummy_Command";
		}

		@Override
		protected void end()
		{
		}

		@Override
		protected void execute()
		{
		}

		@Override
		protected boolean isFinished()
		{
			return true;
		}
	}
}