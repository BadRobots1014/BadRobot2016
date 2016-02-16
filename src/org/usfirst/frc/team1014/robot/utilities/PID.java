package org.usfirst.frc.team1014.robot.utilities;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class PID extends PIDController
{
	public PID(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output)
	{
		super(Kp, Ki, Kd, source, output);
	}

	public void cal()
	{
		this.calculate();
	}
}