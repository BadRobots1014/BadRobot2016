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
		arm.useIt(0);
	}

	@Override
	protected void execute()
	{
		if(ControlsManager.secondaryXboxController.getPOV() == 1)
			arm.useIt(.25);
		else if(ControlsManager.secondaryXboxController.getPOV() == 5)
			arm.useIt(-.25);
		else
			arm.useIt(0);

		// arm.useIt(ControlsManager.secondaryXboxController.getRightStickY() / 2);
	}

	@Override
	protected void initialize()
	{
		arm.useIt(0);
	}

	@Override
	protected void interrupted()
	{

	}

	@Override
	protected boolean isFinished()
	{
		return false;
	}

	@Override
	public String getConsoleIdentity()
	{
		return null;
	}

}
