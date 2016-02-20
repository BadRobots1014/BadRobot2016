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

	/**
	 * This method scales values based on two cosine curves to bring the robot back to the center
	 * position.
	 * 
	 * @param difference
	 *            - how far off the robot is
	 * @param min
	 *            - the lowest value for this value
	 * @param max
	 *            - the highest value for this value
	 * @return the correction value 
	 */
	public static double trigScale(double difference, double min, double max)
	{
		double range = max - min;
		if(difference < 0)
			return -Math.cos((Math.PI / range) * difference - .5);
		else 
			return -Math.cos((Math.PI / range) * difference + .5);
	}
}
