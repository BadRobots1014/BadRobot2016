package org.usfirst.frc.team1014.robot.commands.autonomous;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

public class AutoTurn extends CommandBase{
	
	public double degree, difference, proportion;
	public static int rotationCoefficient = 60;
	
	public AutoTurn(double degree){
		this.degree = degree;
		driveTrain.resetMXPAngle();
		
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
		driveTrain.tankDrive(0, deadzone(rotation()));

	}

	@Override
	protected void interrupted() {
		System.out.println("AutoTurn was interrupted");
		
	}
	public double rotation()
	{
		return -(difference/60);
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
    public static double deadzone(double d) {
        //whenever the controller moves LESS than the magic number, the 
        //joystick is in the loose position so return zero - as if the 
        //joystick was not moved
        if (Math.abs(d) < .1) {
            return 0;
        }
        
        if (d == 0)
        {
            return 0;
        }
        //When the joystick is used for a purpose (passes the if statements, 
        //hence not just being loose), do math
        return ((d / Math.abs(d)) //gets the sign of d, negative or positive
            * ((Math.abs(d) - .1) / (1 - .1))) / 100; //scales it
    }
}
