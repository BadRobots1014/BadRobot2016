package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.OI;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

public class UseShooter extends CommandBase {

	boolean usingShooter;
	double maxSpeed;
	double servoPos;
	
	public UseShooter()
	{
		requires((Subsystem) shooter);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		usingShooter = false;
		maxSpeed = .5;
		servoPos = .5;
		
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
		if(OI.secXboxController.isBButtonPressed() || OI.secXboxController.isXButtonPressed())
		{
			if(OI.secXboxController.isXButtonPressed() && maxSpeed > .5)
				maxSpeed -= .1;
			else if(OI.secXboxController.isBButtonPressed() && maxSpeed < 1.0)
				maxSpeed += .1;
		}

		shooter.shoot(-OI.secXboxController.getRightStickY());
		
		servoPos += OI.secXboxController.getLeftTrigger() * .5;
		servoPos -= OI.secXboxController.getRightTrigger() * .5;
		
		Logger.logThis("Left Trigger: " + OI.secXboxController.getLeftTrigger());
		Logger.logThis("Right Trigger: " + OI.secXboxController.getRightTrigger());
		
		shooter.pusher.set(servoPos);
		
		Logger.logThis("Servo position: " + shooter.pusher.get());
		
		shooter.rotate(OI.secXboxController.getLeftStickY());
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
