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

	public static int LEFT_STICK_X = 0, LEFT_STICK_Y = 1, RIGHT_STICK_X = 4,
			RIGHT_STICK_Y = 5;
	public static int A_BUTTON = 1, B_BUTTON = 2, X_BUTTON = 3, Y_BUTTON = 4,
			LB = 5, RB = 6, SELECT = 7, START = 8, LEFT_JOY_CLICK = 9,
			RIGHT_JOY_CLICK = 10;
	public static int LEFT_TRIGGER = 2, RIGHT_TRIGGER = 3;

	public boolean onPrimaryLayout;

	// Unused
	// private long lastTimeXHeld, timeXHeld;

	public XboxController(int port, boolean onPrimaryLayout)
	{
		super(port);
		this.onPrimaryLayout = onPrimaryLayout;
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
		if(Math.abs(d) < DEADZONE_MAGIC_NUMBER || d == 0)
		{
			return 0;
		}
		// When the joystick is used for a purpose (passes the if statements,
		// hence not just being loose), do math
		return (d / Math.abs(d)) // gets the sign of d, negative or positive
				* ((Math.abs(d) - DEADZONE_MAGIC_NUMBER) / (1 - DEADZONE_MAGIC_NUMBER)); // scales
																							// it
	}

	public void changeLayout(boolean primary)
	{
		if(onPrimaryLayout && !primary)
			onPrimaryLayout = false;
		else if(!onPrimaryLayout && primary)
			onPrimaryLayout = true;
	}

	public double getLeftStickXPrimaryLayout()
	{
		if(onPrimaryLayout)
			return deadzone(this.getRawAxis(LEFT_STICK_X));
		else
			return 0;
	}

	public double getLeftStickYPrimaryLayout()
	{
		if(onPrimaryLayout)
			return deadzone(this.getRawAxis(LEFT_STICK_Y));
		else
			return 0;
	}

	public double getRightStickXPrimaryLayout()
	{
		if(onPrimaryLayout)
			return deadzone(this.getRawAxis(RIGHT_STICK_X));
		else
			return 0;
	}

	public double getRightStickYPrimaryLayout()
	{
		if(onPrimaryLayout)
			return deadzone(this.getRawAxis(RIGHT_STICK_Y));
		else
			return 0;
	}

	public boolean isXButtonPressedPrimaryLayout()
	{
		if(onPrimaryLayout)
			return this.getRawButton(X_BUTTON);
		else
			return false;
	}

	public boolean isYButtonPressedPrimaryLayout()
	{
		if(onPrimaryLayout)
			return this.getRawButton(Y_BUTTON);
		else
			return false;
	}

	public boolean isAButtonPressedPrimaryLayout()
	{
		if(onPrimaryLayout)
			return this.getRawButton(A_BUTTON);
		else
			return false;
	}

	public boolean isBButtonPressedPrimaryLayout()
	{
		if(onPrimaryLayout)
			return this.getRawButton(B_BUTTON);
		else
			return false;
	}

	public boolean isRBButtonPressedPrimaryLayout()
	{
		if(onPrimaryLayout)
			return this.getRawButton(RB);
		else
			return false;
	}

	public boolean isLBButtonPressedPrimaryLayout()
	{
		if(onPrimaryLayout)
			return this.getRawButton(LB);
		else
			return false;
	}

	public boolean isLeftJoyClickPrimaryLayout()
	{
		if(onPrimaryLayout)
			return this.getRawButton(RIGHT_JOY_CLICK);
		else
			return false;
	}

	public boolean isRightJoyClickPrimaryLayout()
	{
		if(onPrimaryLayout)
			return this.getRawButton(LEFT_JOY_CLICK);
		else
			return false;
	}

	public boolean isSelectButtonPressedPrimaryLayout()
	{
		if(onPrimaryLayout)
			return this.getRawButton(SELECT);
		else
			return false;
	}

	public boolean isStartButtonPressedPrimaryLayout()
	{
		if(onPrimaryLayout)
			return this.getRawButton(START);
		else
			return false;
	}

	public double getLeftStickXSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return deadzone(this.getRawAxis(LEFT_STICK_X));
		else
			return 0;
	}

	public double getLeftStickYSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return deadzone(this.getRawAxis(LEFT_STICK_Y));
		else
			return 0;
	}

	public double getRightStickXSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return deadzone(this.getRawAxis(RIGHT_STICK_X));
		else
			return 0;
	}

	public double getRightStickYSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return deadzone(this.getRawAxis(RIGHT_STICK_Y));
		else
			return 0;
	}

	public boolean isXButtonPressedSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return this.getRawButton(X_BUTTON);
		else
			return false;
	}

	public boolean isYButtonPressedSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return this.getRawButton(Y_BUTTON);
		else
			return false;
	}

	public boolean isAButtonPressedSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return this.getRawButton(A_BUTTON);
		else
			return false;
	}

	public boolean isBButtonPressedSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return this.getRawButton(B_BUTTON);
		else
			return false;
	}

	public boolean isRBButtonPressedSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return this.getRawButton(RB);
		else
			return false;
	}

	public boolean isLBButtonPressedSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return this.getRawButton(LB);
		else
			return false;
	}

	public boolean isLeftJoyClickSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return this.getRawButton(RIGHT_JOY_CLICK);
		else
			return false;
	}

	public boolean isRightJoyClickSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return this.getRawButton(LEFT_JOY_CLICK);
		else
			return false;
	}

	public boolean isSelectButtonPressedSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return this.getRawButton(SELECT);
		else
			return false;
	}

	public boolean isStartButtonPressedSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return this.getRawButton(START);
		else
			return false;
	}

	/**
	 * Gets analog trigger values.
	 * 
	 * 0 is unpressed and 1 is completely pressed in on the Right trigger
	 * 
	 * @return
	 */
	public double getRightTriggerPrimaryLayout()
	{
		if(onPrimaryLayout)
			return deadzone(this.getRawAxis(RIGHT_TRIGGER));
		else
			return 0;
	}

	public double getLeftTriggerPrimaryLayout()
	{
		if(onPrimaryLayout)
			return deadzone(this.getRawAxis(LEFT_TRIGGER));
		else
			return 0;
	}

	public double getRightTriggerSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return deadzone(this.getRawAxis(RIGHT_TRIGGER));
		else
			return 0;
	}

	public double getLeftTriggerSecondaryLayout()
	{
		if(!onPrimaryLayout)
			return deadzone(this.getRawAxis(LEFT_TRIGGER));
		else
			return 0;
	}
}