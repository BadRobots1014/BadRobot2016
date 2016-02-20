package org.usfirst.frc.team1014.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * This class is the wrapper class for the MaxBotix ultrasonic range finder. It has a maximum range
 * of around 64 inches.
 * 
 * @author Manu S.
 *
 */
public class BadUltrasonic extends AnalogInput
{
	private static double SCALE_FACTOR = 0.6005858778953552 / 64; // in V/inches
/**
 * 
 * @param ultraChannel
 * 					- Channel for ultrasonic sensor
 */
	public BadUltrasonic(int ultraChannel)
	{
		super(ultraChannel);
	}

	public double getDistance()
	{
		return ((AnalogInput) this).getVoltage() / SCALE_FACTOR;

	}
}
