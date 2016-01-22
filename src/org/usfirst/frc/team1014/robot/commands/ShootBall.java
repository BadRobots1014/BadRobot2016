package org.usfirst.frc.team1014.robot.commands;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ShootBall extends CommandBase {

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		requires((Subsystem) shooter);
		shooter.shoot(0.0);
	}

	@Override
	public String getConsoleIdentity() {
		// TODO Auto-generated method stub
		return "ShootBall";
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		shooter.shoot(0.0);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		shooter.shoot(oi.secXboxController.getLeftStickY());
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
