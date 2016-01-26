package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ShootBall extends CommandBase
{

	public ShootBall()
	{
		requires((Subsystem) shooter);
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stu
		shooter.shoot(0.0);
	}

	@Override
	public String getConsoleIdentity()
	{
		// TODO Auto-generated method stub
		return "ShootBall";
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		shooter.shoot(0.0);
	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		shooter.shoot(oi.secXboxController.getLeftStickY());
		if(oi.secXboxController.isAButtonPressed())
			shooter.ringLightOn();
		else shooter.ringLightOff();
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
