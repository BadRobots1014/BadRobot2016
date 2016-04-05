package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.sensors.BadCAN;

import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoRotateDown extends CommandBase
{
	private int stopCounter;
	private double stoppedEncoderValue;

	public AutoRotateDown()
	{
		requires((Subsystem) shooter);
		shooter.rotate(0);
	}

	@Override
	protected void end()
	{
		shooter.rotate(0);
	}

	@Override
	protected void execute()
	{
		shooter.rotate(-.3);

		stopCounter++;
		if(Math.abs(((BadCAN) shooter.rotator).getDistance() - stoppedEncoderValue) >= 1)
			stopCounter = 0;
	}

	@Override
	protected void initialize()
	{
		stopCounter = 0;
		stoppedEncoderValue = 0;

	}

	@Override
	protected boolean isFinished()
	{
		return stopCounter > 50;
	}

	@Override
	public String getConsoleIdentity()
	{
		return "AutoRotateDown";
	}
}