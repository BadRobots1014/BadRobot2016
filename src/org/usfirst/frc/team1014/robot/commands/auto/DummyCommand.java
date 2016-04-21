package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;

public class DummyCommand extends CommandBase
{
	private double timeToWait = 0.0;
	private boolean isFinished = false;

	public DummyCommand(double waitTime)
	{
		this.timeToWait = waitTime * 1000000;
	}

	@Override
	protected void initialize()
	{
		timeToWait += Utility.getFPGATime();
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
		isFinished = Utility.getFPGATime() >= timeToWait;
	}

	@Override
	protected boolean isFinished()
	{
		return isFinished;
	}
}
