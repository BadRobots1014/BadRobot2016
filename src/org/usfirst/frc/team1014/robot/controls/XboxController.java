package org.usfirst.frc.team1014.robot.controls;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class outlines an XboxController as seen by the FRC Driver Station in 2016
 * 
 * @author Manu S.
 */
public class XboxController extends Joystick
{
	public static double DEADZONE_MAGIC_NUMBER = .15;

	private static int LEFT_STICK_X = 0, LEFT_STICK_Y = 1, RIGHT_STICK_X = 4,
			RIGHT_STICK_Y = 5;
	public static int A_BUTTON = 1, B_BUTTON = 2, X_BUTTON = 3, Y_BUTTON = 4,
			LB = 5, RB = 6, SELECT = 7, START = 8, LEFT_JOY_CLICK = 9,
			RIGHT_JOY_CLICK = 10;
	private static int LEFT_TRIGGER = 2, RIGHT_TRIGGER = 3;

	// Unused
	// private long lastTimeXHeld, timeXHeld;

	public XboxController(int port)
	{
		super(port);
	}

	/**
	 * Creates a deadzone for joysticks, the controllers sticks are a little loose and so there is a
	 * margin of error for where they should be considered "neutral/not pushed"
	 * 
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
		if(Math.abs(d) < DEADZONE_MAGIC_NUMBER)
		{
			return 0;
		}

		if(d == 0)
		{
			return 0;
		}
		// When the joystick is used for a purpose (passes the if statements,
		// hence not just being loose), do math
		return (d / Math.abs(d)) // gets the sign of d, negative or positive
				* ((Math.abs(d) - DEADZONE_MAGIC_NUMBER) / (1 - DEADZONE_MAGIC_NUMBER)); // scales
																							// it
	}

	public double getLeftStickX()
	{
		return deadzone(this.getRawAxis(LEFT_STICK_X));
	}

	public double getLeftStickY()
	{
		return deadzone(this.getRawAxis(LEFT_STICK_Y));
	}

	public double getRightStickX()
	{
		return deadzone(this.getRawAxis(RIGHT_STICK_X));
	}

	public double getRightStickY()
	{
		return deadzone(this.getRawAxis(RIGHT_STICK_Y));
	}

	public boolean isXButtonPressed()
	{
		return this.getRawButton(X_BUTTON);
	}

	public boolean isYButtonPressed()
	{
		return this.getRawButton(Y_BUTTON);
	}

	public boolean isAButtonPressed()
	{
		return this.getRawButton(A_BUTTON);
	}

	public boolean isBButtonPressed()
	{
		return this.getRawButton(B_BUTTON);
	}

	public boolean isRBButtonPressed()
	{
		return this.getRawButton(RB);
	}

	public boolean isLBButtonPressed()
	{
		return this.getRawButton(LB);
	}

	public boolean isLeftJoyClick()
	{
		return this.getRawButton(RIGHT_JOY_CLICK);
	}

	public boolean isRightJoyClick()
	{
		return this.getRawButton(LEFT_JOY_CLICK);
	}

	public boolean isSelectButtonPressed()
	{
		return this.getRawButton(SELECT);
	}

	public boolean isStartButtonPressed()
	{
		return this.getRawButton(START);
	}

	public boolean isAButtonToggled(boolean lastState)
	{
		if(lastState == isAButtonPressed())
			return lastState;
		else
		{
			lastState = isAButtonPressed();
			return lastState;
		}
	}

	/**
	 * Gets analog trigger values.
	 * 
	 * 0 is unpressed and 1 is completely pressed in on the Right trigger
	 * 
	 * @return
	 */
	public double getRightTrigger()
	{
		return deadzone(this.getRawAxis(RIGHT_TRIGGER));
	}

	public double getLeftTrigger()
	{
		return deadzone(this.getRawAxis(LEFT_TRIGGER));
	}
}