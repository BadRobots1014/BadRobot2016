package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.OI;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

public class UseShooter extends CommandBase {

	boolean usingShooter;
	double maxSpeed;
	
	public UseShooter()
	{
		requires((Subsystem) shooter);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
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
		if(OI.secXboxController.isXButtonPressed())
			usingShooter = true;
		else if(OI.secXboxController.isBButtonPressed())
			usingShooter = false;
		
		if(OI.secXboxController.isAButtonPressed() || OI.secXboxController.isYButtonPressed())
		{
			if(OI.secXboxController.isAButtonPressed() && maxSpeed > .5)
				maxSpeed -= .1;
			else if(OI.secXboxController.isYButtonPressed() && maxSpeed < 1.0)
				maxSpeed += .1;
		}
		
		if(usingShooter)
		{
			shooter.shoot(scaleSpeed(OI.secXboxController.getLeftStickY()));
		}
		else
		{
			shooter.grab(scaleSpeed(OI.secXboxController.getLeftStickY()));
		}
		
		if(OI.secXboxController.getLeftTrigger() > 0.0)
			shooter.rotate(OI.secXboxController.getLeftTrigger());
		else if(OI.secXboxController.getRightTrigger() > 0.0)
			shooter.rotate(OI.secXboxController.getRightTrigger());
		
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
