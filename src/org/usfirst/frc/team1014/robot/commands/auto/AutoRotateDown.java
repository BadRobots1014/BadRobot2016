package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.sensors.BadTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author Tze Hei T.
 *
 */
public class AutoRotateDown extends CommandBase{
	
	double currentRevolution;
	int place = 0;		// this is all the way down (guess)

	public AutoRotateDown(){
		requires((Subsystem)shooter);
	}
	
	@Override
	protected void initialize() {
		shooter.rotate(0);
		
	}

	@Override
	public String getConsoleIdentity() {

		return "AutoRotateDown";
	}

	@Override
	protected void end() {
		shooter.rotate(0);
		
	}

	@Override
	protected void execute() {
		currentRevolution = ((BadTalon) shooter.rotator).get();
		shooter.rotate(-1);
		
	}

	@Override
	protected void interrupted() {
		System.out.println("AutoRotateDown was interrupted");
		
	}

	@Override
	protected boolean isFinished() {
		if(currentRevolution <= place){
			return true;
		}
		return false;
	}

}
