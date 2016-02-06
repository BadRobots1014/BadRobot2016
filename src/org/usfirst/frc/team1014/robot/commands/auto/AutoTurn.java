package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * @author Tze Hei T.
 *
 */
public class AutoTurn extends CommandBase {

	public double degree, difference, proportion, sign;
	public static int rotationCoefficient = 60;

	// Positive degree values turn left
	// Negative degree values turn right
	public AutoTurn(double degree) {
		this.degree = degree;
		requires((Subsystem) driveTrain);
		driveTrain.resetMXPAngle();
		sign = Math.abs(degree) / degree;
		// Gets the sign of degree
	}

	@Override
	protected void initialize() {
		driveTrain.resetMXPAngle();
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

		difference = driveTrain.getAngle360() - degree;
		// difference is how far off the angle is from the desired

		if (sign < 0) {
			driveTrain.tankDrive((rotation()), -rotation());
		}
		if (sign > 0) {
			driveTrain.tankDrive(-rotation(), (rotation()));
		}
		Logger.logThis("MXP Angle: " + driveTrain.getAngle360());
	}

	@Override
	protected void interrupted() {
		System.out.println("AutoTurn was interrupted");

	}

	public double rotation() {
		return (difference / rotationCoefficient);
		//Scales difference to a smaller value for the drivetrain
	}

	@Override
	protected boolean isFinished() {
		// Stops if the difference is in the range
		if (Math.abs(difference) < 5) {
			return true;
		} else {
			return false;
		}
	}

}
