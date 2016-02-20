package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;

import edu.wpi.first.wpilibj.command.Subsystem;

public class UseArm extends CommandBase {

	@Override
	protected void initialize() {
		requires((Subsystem) sallyPortArm);
	}

	@Override
	public String getConsoleIdentity() {
		return this.getClass().getName();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		double armSpeed = 0;
//		if(ControlsManager.secondaryXboxController.getLeftTrigger() < .5)
//			armSpeed = -1;
//		if(ControlsManager.secondaryXboxController.getRightTrigger() > .5)
//			armSpeed = 1;
		
		armSpeed = ControlsManager.secondaryXboxController.getLeftTrigger() + ControlsManager.secondaryXboxController.getRightTrigger();
		
		sallyPortArm.setSpeed(armSpeed);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
