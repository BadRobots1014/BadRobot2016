package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;

import edu.wpi.first.wpilibj.command.Subsystem;

public class UseSallyPortArm extends CommandBase
{

	public UseSallyPortArm()
	{
		requires((Subsystem) arm);
	}

	@Override
	protected void end()
	{
		arm.setPower(0);
	}

	@Override
	protected void execute()
	{
		if(ControlsManager.secondaryXboxController.getLeftTriggerPrimaryLayout() > .5 || ControlsManager.secondaryXboxController.getLeftTriggerSecondaryLayout() > .5)
			ControlsManager.changeToSecondaryLayout(2);
		else
			ControlsManager.changeToPrimaryLayout(2);
		
		arm.setPower(-ControlsManager.secondaryXboxController.getRightStickYSecondaryLayout() * .5);
	}

	@Override
	protected void initialize()
	{
		arm.setPower(0);
	}

	@Override
	protected boolean isFinished()
	{
		return false;
	}

	@Override
	public String getConsoleIdentity()
	{
		return "Use_Sally_Port_Arm";
	}

}
