package org.usfirst.frc.team1014.robot.commands;

import edu.wpi.first.wpilibj.command.Subsystem;

public class PixyCommand extends CommandBase
{

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		requires((Subsystem) pixy);
	}

	@Override
	public String getConsoleIdentity() {
		// TODO Auto-generated method stub
		return "pixyCam";
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		pixy.die();
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		pixy.grabAndLog();
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
	

}
