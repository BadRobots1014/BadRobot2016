package org.usfirst.frc.team1014.robot.controls.layouts;

import org.usfirst.frc.team1014.robot.controls.ControllerLayout;
import org.usfirst.frc.team1014.robot.controls.XboxController;

public class DriveButtonInvertLayout extends ControllerLayout
{

	public DriveButtonInvertLayout(int controllerPort)
	{
		super(controllerPort);
		super.assignFunctionButton(ControllerLayout.adjustForward, XboxController.A_BUTTON);
		super.assignFunctionButton(ControllerLayout.adjustBackward, XboxController.Y_BUTTON);
	}

}
