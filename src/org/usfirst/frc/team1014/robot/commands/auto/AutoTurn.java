package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoTurn extends CommandBase{
	
	public double degree, difference, proportion, sign;
	public static int rotationCoefficient = 60;
	
	public AutoTurn(double degree){
		this.degree = degree;
		requires((Subsystem)driveTrain);
		driveTrain.resetMXPAngle();
		sign = Math.abs(degree) / degree;
	}

	@Override
	protected void initialize() {
		requires(driveTrain);
		driveTrain.tankDrive(0, 0);
		
	}

	@Override
	public String getConsoleIdentity() {
		return "AutoTurn";
	}

	@Override
	protected void end() {
		driveTrain.tankDrive(0, 0);
		
	}

	@Override
	protected void execute() {
		difference = driveTrain.getAngle() - degree;
		if(sign < 0){
			driveTrain.tankDrive(-(rotation()), rotation());
		}
		if(sign > 0){
			driveTrain.tankDrive(rotation(), -(rotation()));
		}
	}

	@Override
	protected void interrupted() {
		System.out.println("AutoTurn was interrupted");
		
	}
	public double rotation()
	{
		return (difference/rotationCoefficient);
	}

	@Override
	protected boolean isFinished() {
		if(Math.abs(difference) < 5){
			return true;
		}else
		{
		return false;
		}
	}

}
