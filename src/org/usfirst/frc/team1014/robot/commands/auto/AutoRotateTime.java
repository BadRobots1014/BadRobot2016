package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoRotateTime extends CommandBase
{
	public double time;
	public boolean down;
	
	public double currentTime;
	public double endTime;
	
	public AutoRotateTime(double rotateTime, boolean goingDown)
	{
		requires((Subsystem) shooter);
		time = rotateTime * 1000000;
		down = goingDown;
	}

	@Override
	protected void initialize()
	{
		currentTime = Utility.getFPGATime();
		endTime = currentTime + time;
	}

	@Override
	public String getConsoleIdentity()
	{
		// TODO Auto-generated method stub
		return "AutoRotateTime";
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		shooter.rotate(0.0);
	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		shooter.rotate(down ? -.35 : .35);
		
		currentTime = Utility.getFPGATime();
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return (currentTime > endTime);
	}

}
