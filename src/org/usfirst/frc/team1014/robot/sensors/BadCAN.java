package org.usfirst.frc.team1014.robot.sensors;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * 
 * @author Tze Hei T.
 *
 */
public class BadCAN extends CANTalon implements PIDSource, PIDOutput
{

	public Encoder encoder;

	public BadCAN(int deviceNumber, int aChannel, int bChannel)
	{
		super(deviceNumber);
		encoder = new Encoder(aChannel, bChannel);
		this.encoder.setDistancePerPulse(1 / 20);
	}

	public double getRpm()
	{
		return encoder.getRate() * 60;
	}
}