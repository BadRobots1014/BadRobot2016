package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class outlines the use of the Sally Port Arm that we never actually used.
 * 
 * @author Manu S.
 *
 */
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
		// moves the Sally Port Arm
		arm.setPower(-ControlsManager.shooter.getRightDrive_Articulator(2));
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
