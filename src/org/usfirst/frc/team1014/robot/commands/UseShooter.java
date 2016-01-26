package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.OI;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

public class UseShooter extends CommandBase {

	boolean usingShooter;
	double maxSpeed;
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		requires((Subsystem) shooter);
		usingShooter = false;
		maxSpeed = .5;
		
		shooter.shoot(0.0);
		shooter.rotate(0.0);
	}

	@Override
	public String getConsoleIdentity() {
		// TODO Auto-generated method stub
		return "UseShooter";
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		if(oi.secXboxController.isXButtonPressed())
			usingShooter = true;
		else if(oi.secXboxController.isBButtonPressed())
			usingShooter = false;
		
		if(oi.secXboxController.isAButtonPressed() || oi.secXboxController.isYButtonPressed())
		{
			if(oi.secXboxController.isAButtonPressed() && maxSpeed > .5)
				maxSpeed -= .1;
			else if(oi.secXboxController.isYButtonPressed() && maxSpeed < 1.0)
				maxSpeed += .1;
		}
		
		shooter.shoot(scaleSpeed(oi.secXboxController.getLeftStickY()));
		shooter.grab(scaleSpeed(oi.secXboxController.getRightStickY()));
		
		if(oi.secXboxController.getLeftTrigger() > 0.0)
			shooter.rotate(oi.secXboxController.getLeftTrigger());
		else if(oi.secXboxController.getRightStickY() > 0.0)
			shooter.rotate(oi.secXboxController.getRightTrigger());
		
	}

	public double scaleSpeed(double speed)
	{
		return speed * maxSpeed;
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		shooter.shoot(0.0);
		shooter.rotate(0.0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		Logger.logThis(getConsoleIdentity() + ": I've been interrupted!");
	}

}
