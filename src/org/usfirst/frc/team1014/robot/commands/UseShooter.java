package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.OI;
import org.usfirst.frc.team1014.robot.utilities.Logger;
import edu.wpi.first.wpilibj.command.Subsystem;

public class UseShooter extends CommandBase {

	boolean usingShooter;
	double maxSpeed;

	public UseShooter()
	{
		requires(shooter);
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		usingShooter = false;
		maxSpeed = .5;

		shooter.shoot(0.0);
		shooter.rotate(0.0);
	}

	@Override
	public String getConsoleIdentity()
	{
		// TODO Auto-generated method stub
		return "UseShooter";
	}

	@Override
	protected void execute()
	{
		if(OI.secXboxController.isBButtonPressed() || OI.secXboxController.isXButtonPressed())
		{
			if(OI.secXboxController.isXButtonPressed() && maxSpeed > .5)
				maxSpeed -= .1;
			else if(OI.secXboxController.isBButtonPressed() && maxSpeed < 1.0)
				maxSpeed += .1;
		}
		shooter.shoot(OI.secXboxController.getRightStickY());

		shooter.rotate(OI.secXboxController.getLeftStickY());
		
		if(OI.secXboxController.isLBButtonPressed())
		{
			shooter.ringLightOn();
		}
	}

	public double scaleSpeed(double speed)
	{
		return speed * maxSpeed;
	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		shooter.shoot(0.0);
		shooter.rotate(0.0);
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub
		Logger.logThis(getConsoleIdentity() + ": I've been interrupted!");
	}

}
