package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author Subash C.
 *
 */
public class FindTarget extends CommandBase {

	public final int TARGET_X = 160;
	public final int TARGET_Y = 120;
	
	public FindTarget()
	{
		requires((Subsystem) shooter);
		requires((Subsystem) driveTrain);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		driveTrain.tankDrive(0, 0);
		shooter.rotate(0);
		shooter.shoot(0.0);
		shooter.ringLightOn();
	}

	@Override
	public String getConsoleIdentity() {
		// TODO Auto-generated method stub
		return "FindTarget";
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		driveTrain.tankDrive(0, 0);
		shooter.shoot(0.0);
		shooter.rotate(0.0);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		if(ProcessedCam.getInstance().getObjTrackScore() > 90)
		{
			driveTrain.tankDrive((ProcessedCam.getInstance().getX() - TARGET_X)/160,
					-(ProcessedCam.getInstance().getY() - TARGET_X)/160);
			shooter.rotate((ProcessedCam.getInstance().getY() - TARGET_Y)/120);
		}
		
	}
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		Logger.logThis(getConsoleIdentity() + " I have been interrupted!");
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		if(Math.abs(ProcessedCam.getInstance().getX() - TARGET_X) < 10)
			return true;
		else
			return false;
	}

}
