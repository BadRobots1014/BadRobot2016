package org.usfirst.frc.team1014.robot.controls.layouts;

import org.usfirst.frc.team1014.robot.controls.ControllerLayout;
import org.usfirst.frc.team1014.robot.controls.XboxController;

public class DriveButtonInvertLayout extends ControllerLayout
{

	public DriveButtonInvertLayout(int controllerPort)
	{
		super(controllerPort);
		super.adjustForward_articulatorDown = XboxController.A_BUTTON;
		super.adjustBackward_articulatorUp = XboxController.Y_BUTTON;
	}

}
