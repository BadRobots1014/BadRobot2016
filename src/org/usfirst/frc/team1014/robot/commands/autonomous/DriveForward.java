package org.usfirst.frc.team1014.robot.commands.autonomous;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;

public class DriveForward extends CommandBase{

	public double startTime, passedTime, time, speed;
	
	public DriveForward(double speed, double time){
		
		this.speed = speed;
		this.time = time;
		passedTime = 0;
		startTime = Utility.getFPGATime();
		
	}

	@Override
	protected void initialize() {
		driveTrain.tankDrive(0, 0);
		
	}

	@Override
	public String getConsoleIdentity() {
		return "Drive";
	}

	@Override
	protected void end() {
		driveTrain.tankDrive(0, 0);
		
	}

	@Override
	protected void execute() {
		passedTime = Utility.getFPGATime() - startTime;
		driveTrain.tankDrive(speed, speed);
		
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() 
	{
		if((passedTime / 1000000) > time)
		{
			
			System.out.println("Drove forward");
			return true;
			
		}else
		{
		return false;
		}
	}
}
