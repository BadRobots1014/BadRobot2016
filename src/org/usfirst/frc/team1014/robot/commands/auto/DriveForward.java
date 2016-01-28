package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Utility;

public class DriveForward extends CommandBase {
	
	public double driveTime;
	public double speed;
	public double startTime;
	public double passedTime;
	
	public DriveForward (double driveTime, double speed) {
		this.driveTime = driveTime;
		this.speed = speed;
		startTime = Utility.getFPGATime();
		passedTime = 0;
	}

	@Override
	protected void initialize() {
		driveTrain.tankDrive(0,0);
	}

	@Override
	public String getConsoleIdentity() {
		return null;
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
		
	}

	@Override
	protected boolean isFinished() {
		if((passedTime / 1000000) > driveTime) {
			System.out.println("DriveForward is done");
			return true;
		} else
			return false;
	}
	
	public static double rotation() {
		return -(driveTrain.getAngle()/45);
	}

	public static double deadzone (double d) {
		if (Math.abs(d) < .1) {
            return 0;
        }
		if (d == 0) {
            return 0;
        }
		return (d / Math.abs(d)) * ((Math.abs(d) - .1) / (1 - .1));
	}

}