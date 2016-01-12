package org.usfirst.frc.team1014.robot.commands;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class defines how the robot drives through teleop.
 * @author Manu S.
 *
 */
public class TeleDrive extends CommandBase {

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		requires((Subsystem) driveTrain);
		driveTrain.tankDrive(0, 0);
	}

	@Override
	public String getConsoleIdentity() {
		// TODO Auto-generated method stub
		return "TeleDrive";
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		driveTrain.tankDrive(oi.priXboxController.getLeftStickY(), oi.priXboxController.getRightStickY());
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		driveTrain.tankDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
