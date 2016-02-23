package org.usfirst.frc.team1014.robot.subsystems;

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
		// TODO Auto-generated method stub
		actuator = new CANTalon(4);
	}

	public void useIt(double power)
	{
		actuator.set(power);
	}

	@Override
	public String getConsoleIdentity()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initDefaultCommand()
	{
		// TODO Auto-generated method stub

	}

}
