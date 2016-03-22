package org.usfirst.frc.team1014.robot.controls;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class outlines the Extreme 3D Pro flight joystick to be used for FRC driving as of 2016
 * 
 * @author Manu S.
 * 
 */
public class JoyStickController extends Joystick
{

	public static final double DEADZONE_MAGIC_NUMBER = 0.15;

	private static int STICK_X = 0, STICK_Y = 1, TWIST = 2, SLIDER = 3;
	private static int TRIGGER = 0, BUTTON_2 = 1, BUTTON_3 = 2, BUTTON_4 = 3,
			BUTTON_5 = 4, BUTTON_6 = 5, BUTTON_7 = 6, BUTTON_8 = 7,
			BUTTON_9 = 8, BUTTON_10 = 9, BUTTON_11 = 10, BUTTON_12 = 11;

	public JoyStickController(int port)
	{
		super(port);
	}

	/**
	 * Creates a deadzone for joysticks, the controllers sticks are a little loose and so there is a
	 * margin of error for where they should be considered "neutral/not pushed"
	 * 
	 * @param d
	 *            Double between -1 and 1 to be deadzoned
	 * @return The deadzoned value
	 */
	public static double deadzone(double d)
	{
		// whenever the controller moves LESS than the magic number, the
		// joystick is in the loose position so return zero - as if the
		// joystick was not moved
		if(Math.abs(d) < DEADZONE_MAGIC_NUMBER || d == 0)
			return 0;

		// When the joystick is used for a purpose (passes the if statements,
		// hence not just being loose), do math
		return (d / Math.abs(d)) // gets the sign of d, negative or positive
				* ((Math.abs(d) - DEADZONE_MAGIC_NUMBER) / (1 - DEADZONE_MAGIC_NUMBER)); // scales
																							// it
	}

	/**
	 * This returns the deadzoned flight stick x-axis
	 * 
	 * @return
	 */
	public double getStickX()
	{
		return deadzone(this.getRawAxis(STICK_X));
	}

	/**
	 * This returns the deadzoned flight stick y-axis
	 * 
	 * @return
	 */
	public double getStickY()
	{
		return deadzone(this.getRawAxis(STICK_Y));
	}

	/**
	 * This reutrns the deadzoned twist of the joystick
	 * 
	 * @return
	 */
	@Override
	public double getTwist()
	{
		return deadzone(this.getRawAxis(TWIST));
	}

	/**
	 * This returns the deadzoned value of the slider
	 * 
	 * @return
	 */
	public double getSlider()
	{
		return deadzone(this.getRawAxis(SLIDER));
	}

	/**
	 * This gets if the trigger is pressed down or not
	 * 
	 * @return
	 */
	public boolean getTriggerButton()
	{
		return this.getRawButton(TRIGGER);
	}

	public boolean getButton2()
	{
		return this.getRawButton(BUTTON_2);
	}

	public boolean getButton3()
	{
		return this.getRawButton(BUTTON_3);
	}

	public boolean getButton4()
	{
		return this.getRawButton(BUTTON_4);
	}

	public boolean getButton5()
	{
		return this.getRawButton(BUTTON_5);
	}

	public boolean getButton6()
	{
		return this.getRawButton(BUTTON_6);
	}

	public boolean getButton7()
	{
		return this.getRawButton(BUTTON_7);
	}

	public boolean getButton8()
	{
		return this.getRawButton(BUTTON_8);
	}

	public boolean getButton9()
	{
		return this.getRawButton(BUTTON_9);
	}

	public boolean getButton10()
	{
		return this.getRawButton(BUTTON_10);
	}

	public boolean getButton11()
	{
		return this.getRawButton(BUTTON_11);
	}

	public boolean getButton12()
	{
		return this.getRawButton(BUTTON_12);
	}
}