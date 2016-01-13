package org.usfirst.frc.team1014.robot.commands;


public class PixyCommand extends CommandBase
{

	@Override
	protected void initialize()
	{
		requires(pixy);
	}

	@Override
	public String getConsoleIdentity()
	{
		return "pixyCam";
	}

	@Override
	protected void end()
	{
		pixy.die();
	}

	@Override
	protected void execute()
	{
		pixy.grabAndLog();
	}

	@Override
	protected void interrupted()
	{

	}

	@Override
	protected boolean isFinished()
	{
		return false;
	}

}
