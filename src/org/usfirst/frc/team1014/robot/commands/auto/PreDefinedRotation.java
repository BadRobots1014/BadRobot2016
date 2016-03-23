package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.sensors.BadCAN;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PreDefinedRotation extends CommandBase
{

	private boolean goingDown = false;
	private double speed = 0.4;
	private int counter = 0;
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
		// TODO Auto-generated method stub
		return "PreDefinedRotation";
	}

	@Override
	protected void end()
	{
		shooter.rotate(0);

	}

	@Override
	protected void execute()
	{
		if(goingDown)
		{
			shooter.rotate(-speed);
		}
		else
		{
			shooter.rotate(speed);
		}

		Logger.logThis("shooterRPM: " + ((BadCAN) shooter.rotator).encoder.getRate());

	}

	@Override
	protected boolean isFinished()
	{
		if(Utility.getFPGATime() > timer)
		{
			return true;
		}
		if(counter < 50 && Math.abs(((BadCAN) shooter.rotator).encoder.getRate()) < 10)
		{
			counter++;
		}
		else if(counter >= 50)
		{
			return true;
		}
		else if(Math.abs(((BadCAN) shooter.rotator).encoder.getRate()) > 10)
		{
			counter = 0;
		}
		return false;
	}

}
