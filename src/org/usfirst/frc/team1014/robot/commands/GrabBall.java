package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class is the grabbing command that tells the robot to grab the ball.
 * 
 * @author Manu S.
 * 
 */
public class GrabBall extends CommandBase
{

	public GrabBall()
	{
		requires((Subsystem) shooter);
	}

	@Override
	protected void initialize()
	{

		shooter.grab(0.0);
	}

	@Override
	public String getConsoleIdentity()
	{
		// TODO Auto-generated method stub
		return "GrabBall";
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		shooter.grab(0.0);
	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		if(oi.secXboxController.isXButtonPressed())
			shooter.grab(0.7);
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		Logger.logThis(getConsoleIdentity() + ": I've been interrupted!");
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
