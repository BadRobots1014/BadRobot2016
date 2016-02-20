package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;

import edu.wpi.first.wpilibj.Talon;

public class SallyPortArm extends BadSubsystem {

	private static SallyPortArm instance;
	
	public static SallyPortArm getInstance() {
		if(instance == null)
			instance = new SallyPortArm();
		return instance;
	}
	
	private Talon armMotorController;
	
	@Override
	protected void initialize() {
		armMotorController = new Talon(ControlsManager.ARM_SPEED_CONTROLLER);
	}
	
	public void setSpeed(double speed) {
		armMotorController.set(speed);
	}

	@Override
	public String getConsoleIdentity() {
		return this.getClass().getName();
	}

	@Override
	protected void initDefaultCommand() {
		// Leave Blank
	}

}
