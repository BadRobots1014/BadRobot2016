package org.usfirst.frc.team1014.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A {@link CommandBase} group that represents all of the teleop commands. All commands added will
 * run in parallel.
 * 
 * @author Manu S.
 */
public class TeleopGroup extends CommandGroup
{
	public TeleopGroup()
	{
		this.addParallel(new TeleDrive());
		this.addParallel(new UseShooter());
		this.addParallel(new UseSallyPortArm());
	}
}
