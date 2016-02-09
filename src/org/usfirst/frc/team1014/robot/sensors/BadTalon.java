package org.usfirst.frc.team1014.robot.sensors;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class BadTalon extends Talon
{

	public Encoder encoder;

	public BadTalon(int talonPWM, int aChannel, int bChannel)
	{
		super(talonPWM);
		encoder = new Encoder(aChannel, bChannel);
	}

	public double getRpm()
	{
		return encoder.getRate() * 60;
	}
}