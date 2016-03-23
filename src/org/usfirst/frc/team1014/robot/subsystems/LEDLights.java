package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDLights extends Subsystem
{
	public static LEDLights instance;

	private DigitalOutput bit1;
	private DigitalOutput bit2;

	public LEDLights()
	{

	}

	public static LEDLights getInstance()
	{
		if(instance == null)
			instance = new LEDLights();

		return instance;
	}

	protected void initialize()
	{
		bit1 = new DigitalOutput(ControlsManager.LED_BIT1);
		bit2 = new DigitalOutput(ControlsManager.LED_BIT2);
	}
	
	public void setLights(ColorState state)
	{
		switch(state)
		{
			case kRED: 
				bit1.set(false); bit2.set(false);
				break;
			case kBLUE:
				bit1.set(false); bit2.set(true);
				break;
			case kSHOOT:
				bit1.set(true);	bit2.set(false);
				break;
			case kGATHER: 
				bit1.set(true); bit2.set(true);
				break;
			default: 
				bit1.set(false); bit2.set(false);
				break;
		}
	}

	@Override
	protected void initDefaultCommand()
	{

	}

	public enum ColorState
	{
		kRED, kBLUE, kSHOOT, kGATHER
	}
}
