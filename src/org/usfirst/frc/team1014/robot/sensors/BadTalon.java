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
		this(talonPWM, aChannel, bChannel, .05);
	}
	
	public BadTalon(int talonPWM, int aChannel, int bChannel, double distancePerPulse)
	{
		super(talonPWM);
		encoder = new Encoder(aChannel, bChannel);
		this.encoder.setDistancePerPulse(distancePerPulse); // encoder is 20 pulses per revolution
	}

	public double getRpm()
	{
		return encoder.getRate() * 60;
	}

	public double getDistance()
	{
		return encoder.getDistance();
	}
}
