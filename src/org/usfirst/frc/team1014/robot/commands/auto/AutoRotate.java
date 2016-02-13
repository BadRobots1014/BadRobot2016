package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.sensors.BadTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author Tze Hei T.
 *
 */

public class AutoRotate extends CommandBase {

	double revolutions;
	double currentRevolutions;
	double difference;
	boolean direction;

	/**
	 * Constructor
	 * @param revolutions
	 * 				- amount of revolutions you want to turn
	 * @param direction
	 * 				- false for down, true for up
	 */
	public AutoRotate(double revolutions, boolean direction) {
		this.direction = direction;
		this.revolutions = revolutions;
		requires((Subsystem) shooter);
	}

	@Override
	protected void initialize() {
		shooter.rotate(0);

	}

	@Override
	public String getConsoleIdentity() {

		return "AutoRotate";
	}

	@Override
	protected void end() {
		shooter.rotate(0);

	}

	@Override
	protected void execute() {
		currentRevolutions = ((BadTalon) shooter.rotator).get();
		difference = currentRevolutions - revolutions;
		
		if(direction == true){
			shooter.rotate(1);
		}if(direction == false){
			shooter.rotate(-1);
		}

	}

	@Override
	protected void interrupted() {
		System.out.println("AutoRotate was interrupted");

	}

	@Override
	protected boolean isFinished() {
		if (Math.abs(difference) <= 0) {
			return true;
		} else {
			return false;
		}
	}

}
