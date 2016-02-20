package org.usfirst.frc.team1014.robot.sensors;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;

/**
 * This class allows an encoder to be attached to each TalonSRX and use of getRpm
 * 
 * @author Tze Hei T.
 *
 * 
 */

public class BadCAN extends CANTalon
{
	public Encoder encoder;

	/**
	 * Constructor
	 * 
	 * @param deviceNumber
	 *            - number of CanTalon
	 * @param aChannel
	 *            - a channel for encoder
	 * @param bChannel
	 *            - b channel for encoder
	 */
	public BadCAN(int deviceNumber, int aChannel, int bChannel)
	{
		super(deviceNumber);
		encoder = new Encoder(aChannel, bChannel);
		this.encoder.setDistancePerPulse(.05); // encoder is 20 pulses per revolution
	}

	public double getRpm()
	{
		return encoder.getRate() * 60;
	}
}