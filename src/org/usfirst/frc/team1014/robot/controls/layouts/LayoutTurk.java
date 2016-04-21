package org.usfirst.frc.team1014.robot.controls.layouts;

import org.usfirst.frc.team1014.robot.controls.ControllerLayout;
import org.usfirst.frc.team1014.robot.controls.XboxController;

public class LayoutTurk extends ControllerLayout
{
	public LayoutTurk(int controllerPort)
	{
		super(controllerPort);
		super.rightDrive_articulator = -XboxController.RIGHT_STICK_Y;
		super.adjustBackward_articulatorUp = XboxController.Y_BUTTON;
		super.adjustForward_articulatorDown = XboxController.A_BUTTON;
	}
}
