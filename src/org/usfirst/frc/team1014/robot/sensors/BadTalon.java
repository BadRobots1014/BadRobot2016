package org.usfirst.frc.team1014.robot.sensors;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

/**
 * This class allows an encoder to be attached to each Talon and use of getRpm
 * 
 * @author Tze Hei T.
 * 
 */
public class BadTalon extends Talon
{

	public Encoder encoder;

	/**
	 * Constructor
	 * 
	 * @param talonPWM
	 *            - port number of talon
	 * @param aChannel
	 *            - a channel of encoder
	 * @param bChannel
	 *            - b channel of encoder
	 */
	public BadTalon(int talonPWM, int aChannel, int bChannel)
	{
		super(talonPWM);
		encoder = new Encoder(aChannel, bChannel);
		this.encoder.setDistancePerPulse(.05); // encoder is 20 pulses per revolution
	}

	public double getRpm()
	{
		return encoder.getRate() * 60;
	}

	public double get()
	{
		return encoder.get();
	}
}
