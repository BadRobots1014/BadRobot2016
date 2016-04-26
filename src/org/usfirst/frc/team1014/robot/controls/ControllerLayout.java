package org.usfirst.frc.team1014.robot.controls;

/**
 * Used to create layouts for different users dependent upon their control preference
 *
 * @author Ryan T.
 *
 */
public class ControllerLayout
{
	private int[] stickAssignments = new int[4];
	private int[] buttonAssignments = new int[14];
	private int[] triggerAssignments = new int[2];

	// Stick function id's
	public static int leftDrive = 0, rightDrive = 1, shooter = 2,
			articulator = 3;

	// Button function id's
	public static int adjustBackward = 0, adjustRight = 1, adjustLeft = 2,
			adjustForward = 3, driveStraight = 4, underVoltClear = 5,
			articulatorUp = 6, autoShoot = 7, servo = 8, articulatorDown = 9,
			autoGrab = 10, ringLight = 11, left_joy_click = 12,
			right_joy_click = 13;

	// Trigger function id's
	public static int layoutchange = 0, right_trigger = 1;

	public static final int CONTROL_NONE = -1;

	// the actual controller object
	public XboxController controller;

	public ControllerLayout(int controllerPort)
	{
		this.controller = new XboxController(controllerPort, true);
		this.loadDefaults();
	}

	private void loadDefaults()
	{
		stickAssignments[leftDrive] = XboxController.LEFT_STICK_Y;
		stickAssignments[rightDrive] = XboxController.RIGHT_STICK_Y;
		stickAssignments[shooter] = XboxController.LEFT_STICK_Y;
		stickAssignments[articulator] = XboxController.RIGHT_STICK_Y;

		buttonAssignments[adjustBackward] = XboxController.A_BUTTON;
		buttonAssignments[adjustRight] = XboxController.B_BUTTON;
		buttonAssignments[adjustLeft] = XboxController.X_BUTTON;
		buttonAssignments[adjustForward] = XboxController.Y_BUTTON;
		buttonAssignments[driveStraight] = XboxController.LB;
		buttonAssignments[underVoltClear] = XboxController.SELECT;
		buttonAssignments[articulatorUp] = XboxController.A_BUTTON;
		buttonAssignments[autoShoot] = XboxController.B_BUTTON;
		buttonAssignments[servo] = XboxController.X_BUTTON;
		buttonAssignments[articulatorDown] = XboxController.Y_BUTTON;
		buttonAssignments[autoGrab] = XboxController.RB;
		buttonAssignments[ringLight] = XboxController.START;
		buttonAssignments[left_joy_click] = XboxController.LEFT_JOY_CLICK;
		buttonAssignments[right_joy_click] = XboxController.RIGHT_JOY_CLICK;

		triggerAssignments[layoutchange] = XboxController.LEFT_TRIGGER;
		triggerAssignments[right_trigger] = XboxController.RIGHT_TRIGGER;
	}

	public void assignFunctionStick(int function, int stick)
	{
		if(function >= 0 && function < stickAssignments.length)
			this.stickAssignments[function] = stick;
	}

	public void assignFunctionButton(int function, int button)
	{
		if(function >= 0 && function < buttonAssignments.length)
			this.buttonAssignments[function] = button;
	}

	public void assignFunctionTrigger(int function, int trigger)
	{
		if(function >= 0 && function < triggerAssignments.length)
			this.triggerAssignments[function] = trigger;
	}

	public double getStickValue(int layout, int functionID)
	{
		int axisID = this.stickAssignments[functionID];
		if(axisID == ControllerLayout.CONTROL_NONE)
			return 0;

		int sign = axisID / Math.abs(axisID);
		if(controller.onPrimaryLayout && layout == 1)
			return sign * XboxController.deadzone(controller.getRawAxis(Math.abs(axisID)));
		else if(!controller.onPrimaryLayout && layout == 2)
			return sign * XboxController.deadzone(controller.getRawAxis(Math.abs(axisID)));
		else
			return 0;
	}

	public boolean getButtonValue(int layout, int functionID)
	{
		int buttonID = this.buttonAssignments[functionID];
		if(buttonID == ControllerLayout.CONTROL_NONE)
			return false;
		if(controller.onPrimaryLayout && layout == 1)
			return controller.getRawButton(buttonID);
		else if(!controller.onPrimaryLayout && layout == 2)
			return controller.getRawButton(buttonID);
		else
			return false;
	}

	public double getTriggerValue(int layout, int functionID)
	{
		int axisID = this.triggerAssignments[functionID];
		if(axisID == ControllerLayout.CONTROL_NONE)
			return 0;
		int sign = axisID / Math.abs(axisID);
		if(controller.onPrimaryLayout && layout == 1)
			return sign * XboxController.deadzone(controller.getRawAxis(Math.abs(axisID)));
		else if(!controller.onPrimaryLayout && layout == 2)
			return sign * XboxController.deadzone(controller.getRawAxis(Math.abs(axisID)));
		else
			return 0;
	}

	public boolean getLayoutChange()
	{
		return this.getStickValue(1, layoutchange) > 0.5 || this.getStickValue(2, layoutchange) > 0.5;
	}
}
