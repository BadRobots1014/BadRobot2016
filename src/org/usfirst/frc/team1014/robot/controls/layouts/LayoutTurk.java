package org.usfirst.frc.team1014.robot.controls.layouts;

import org.usfirst.frc.team1014.robot.controls.ControllerLayout;
import org.usfirst.frc.team1014.robot.controls.XboxController;

/**
 * A {@code ControllerLayout} for Ryan Turk that flips the orientation of the articulator.
 * 
 * @author Ryan T.
 *
 */
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
