package org.usfirst.frc.team1014.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SpyBotShootHigh extends CommandGroup
{
	public SpyBotShootHigh()
	{
		this.addSequential(new AutoDriveServo(true));
		this.addSequential(new MoveToLowerRetroTape());
		this.addSequential(new AutoShoot(1.5));
	}
}
