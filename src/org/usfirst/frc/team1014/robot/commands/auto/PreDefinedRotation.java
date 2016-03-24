package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PreDefinedRotation extends CommandBase
{

	private boolean goingDown = false;
	private double speed = 0.4;
	private double timer = 0.0;

	public PreDefinedRotation(Boolean goingDown)
	{
		requires((Subsystem) shooter);
		this.goingDown = goingDown;
	}

	@Override
	protected void initialize()
	{

		timer = Utility.getFPGATime() + 2000000;
	}

	@Override
	public String getConsoleIdentity()
	{
		return "Predefined_Rotation";
	}

	@Override
	protected void end()
	{
		shooter.rotate(0);
		shooter.isLow = goingDown;

	}

	@Override
	protected void execute()
	{
		if(goingDown)
			shooter.rotate(-speed);
		else
			shooter.rotate(speed);
	}

	@Override
	protected boolean isFinished()
	{
		return Utility.getFPGATime() > timer;
	}
}
