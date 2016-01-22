package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.OI;
import org.usfirst.frc.team1014.robot.utilities.Logger;


public class toggleSpeeds extends CommandBase
{

	float speed = 0.5f;
	boolean ready = true;
	
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		driveTrain.tankDrive(0, 0);
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		driveTrain.tankDrive(OI.priXboxController.getLeftStickY() * speed , OI.priXboxController.getRightStickY() * speed);
		
		if(ready)
		{
			if(OI.priXboxController.isAButtonPressed())
			{
				speed -= (float) 0.1;
			}
			else if(OI.priXboxController.isYButtonPressed())
			{
				speed += (float) 0.1;
			}
			if(speed > 1.0)
			{
				speed = (float) 1.0;
			}
			else if(speed < .5)
			{
				speed = (float) .5;
			}
			ready = false;
		}
		else
		{
			if(!OI.priXboxController.isYButtonPressed() && !OI.priXboxController.isAButtonPressed())
			{
				ready = true;
			}
		}
		
		if(oi.priXboxController.isBButtonPressed())
			driveTrain.turnOnRingLight();
		else
			driveTrain.turnOffRingLight();
		
		Logger.logThis("MAX_SPEED = " + speed);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		requires(driveTrain);
		driveTrain.tankDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getConsoleIdentity() {
		// TODO Auto-generated method stub
		return "TeleDrive";
	}
	
	
}
