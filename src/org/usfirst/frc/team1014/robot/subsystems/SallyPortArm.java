package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;

public class SallyPortArm extends BadSubsystem
{
	public static SallyPortArm instance;

	public SpeedController actuator;

	public SallyPortArm()
	{

	}

	public static SallyPortArm getInstance()
	{
		if(instance == null)
			instance = new SallyPortArm();

		return instance;
	}

	@Override
	protected void initialize()
	{
		actuator = new CANTalon(ControlsManager.SALLY_PORT_ARM);
	}

	public void setPower(double power)
	{
		actuator.set(power);
	}

	@Override
	public String getConsoleIdentity()
	{
		return "Sally_Port_Arm";
	}

	@Override
	protected void initDefaultCommand()
	{

	}
}
