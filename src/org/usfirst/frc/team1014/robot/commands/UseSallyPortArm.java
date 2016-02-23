package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;

import edu.wpi.first.wpilibj.command.Subsystem;

public class UseSallyPortArm extends CommandBase
{

	public UseSallyPortArm()
	{
		// TODO Auto-generated constructor stub
		requires((Subsystem) arm);
	}
	
	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		arm.useIt(0);
	}

	@Override
	protected void execute()
	{
		// TODO Auto-generated method stub
		
//		if(ControlsManager.secondaryXboxController.getPOV() == 1)
//			arm.useIt(.25);
//		else if(ControlsManager.secondaryXboxController.getPOV() == 5)
//			arm.useIt(-.25);
//		else
//			arm.useIt(0);
		
		arm.useIt(ControlsManager.secondaryXboxController.getRightStickY() / 2);
	}

	@Override
	protected void initialize()
	{
		// TODO Auto-generated method stub
		arm.useIt(0);
	}

	@Override
	protected void interrupted()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getConsoleIdentity()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
