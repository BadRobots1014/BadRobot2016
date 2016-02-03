package org.usfirst.frc.team1014.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;

public class BadCAN extends CANTalon{
	
	public Encoder encoder;

	public BadCAN(int deviceNumber, int aChannel, int bChannel) {
		super(deviceNumber);
		encoder = new Encoder(aChannel, bChannel);
	}
	public double getRpm()
	{
		return encoder.getRate() * 60;	
	}
}
