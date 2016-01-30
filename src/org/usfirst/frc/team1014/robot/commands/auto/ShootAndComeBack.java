package org.usfirst.frc.team1014.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootAndComeBack extends CommandGroup
{
	public ShootAndComeBack()
	{
		this.addSequential(new DriveForwardDistance(1, 132));
		this.addSequential(new AutoTurn(90));
		this.addSequential(new DriveForward(.5, 1));
		this.addSequential(new AutoTurn(-90));
		this.addSequential(new AutoShoot(1));
		this.addSequential(new AutoTurn(-90));
		this.addSequential(new DriveForward(.5, 1));
		this.addSequential(new AutoTurn(-90));
		this.addSequential(new DriveForward(2, 1));
	
	}
}
