package org.usfirst.frc.team1014.robot.controls.layouts;

import org.usfirst.frc.team1014.robot.controls.ControllerLayout;
import org.usfirst.frc.team1014.robot.controls.XboxController;

/**
 * A {@code ControllerLayout} that inverts the A and Y buttons.
 * 
 * @author Ryan T.
 *
 */
public class DriveButtonInvertLayout extends ControllerLayout
{

	public DriveButtonInvertLayout(int controllerPort)
	{
		super(controllerPort);
		super.assignFunctionButton(ControllerLayout.adjustForward, XboxController.A_BUTTON);
		super.assignFunctionButton(ControllerLayout.adjustBackward, XboxController.Y_BUTTON);
	}

}
