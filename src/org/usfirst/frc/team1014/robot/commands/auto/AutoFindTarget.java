package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoFindTarget extends CommandBase {

	public int x, y;

	public AutoFindTarget(int x, int y) {
		this.x = x;
		this.y = y;
		requires((Subsystem) driveTrain);
		requires((Subsystem) shooter);
	}

	@Override
	protected void initialize() {
		driveTrain.tankDrive(0, 0);
		shooter.rotate(0);
	}

	@Override
	public String getConsoleIdentity() {
		return "AutoFind";
	}

	@Override
	protected void end() {
		driveTrain.tankDrive(0, 0);
		shooter.rotate(0);

	}

	@Override
	protected void execute() {

		if (x < 0) {
			driveTrain.tankDrive(1, -1);
		}
		if (x > 0) {
			driveTrain.tankDrive(-1, 1);
		}

		if (y < 0) {
			shooter.rotate(1);
		}
		if (y > 0) {
			shooter.rotate(-1);
		}

	}

	@Override
	protected void interrupted() {
		System.out.println("AutoFind was interrupted");

	}

	@Override
	protected boolean isFinished() {
		if ((Math.abs(x) < 5) && (Math.abs(y) < 5)) {
			return true;
		}
		return false;
	}

}
