package org.usfirst.frc.team1014.robot.sensors;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

/**
 * 
 * @author Tze Hei T.
 *
 */
public class BadTalon extends Talon {

	public Encoder encoder;

	public BadTalon(int talonPWM, int aChannel, int bChannel) {
		super(talonPWM);
		encoder = new Encoder(aChannel, bChannel);
		this.encoder.setDistancePerPulse(.05);
	}

	public double getRpm() {
		return encoder.getRate() * 60;
	}
}