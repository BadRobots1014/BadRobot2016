package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.robot.Robot;
import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.utilities.SmartDashboard;

import edu.wpi.first.wpilibj.DigitalOutput;

public class LEDLights
{
	private static LEDLights instance;
	private DigitalOutput bit1;

	public LEDLights()
	{
		bit1 = new DigitalOutput(ControlsManager.LED_BIT1);
	}

	/**
	 * returns the current instance of drive train. If none exists, then it creates a new instance.
	 * 
	 * @return instance of the DriveTrain
	 */
	public static LEDLights getInstance()
	{
		if(instance == null)
			instance = new LEDLights();
		return instance;
	}

	public void setLights(LEDState state)
	{
		if(Robot.lowVoltage)
			state = LEDState.kBATTERY;

		switch(state)
		{
			// Channel number is irrelevant here
			case kRED:
				bit1.pulse(ControlsManager.LED_BIT1, 0.00002f);
				break;
			case kBLUE:
				bit1.pulse(ControlsManager.LED_BIT1, 0.00004f);
				break;
			case kSHOOT:
				bit1.pulse(ControlsManager.LED_BIT1, 0.00006f);
				break;
			case kGATHER:
				bit1.pulse(ControlsManager.LED_BIT1, 0.00008f);
				break;
			case kBATTERY:
				bit1.pulse(ControlsManager.LED_BIT1, 0.00010f);
				break;
			default:
				bit1.pulse(ControlsManager.LED_BIT1, 0.00f);
				break;
		}
	}

	public void pulse()
	{
		if(!SmartDashboard.table.containsKey("Pulse Length"))
			SmartDashboard.table.putString("Pulse Length", "0.001");
		bit1.pulse(ControlsManager.LED_BIT1, Float.parseFloat(SmartDashboard.table.getString("Pulse Length", "0.001")));
	}

	public enum LEDState
	{
		kRED, kBLUE, kSHOOT, kGATHER, kBATTERY
	}

}
