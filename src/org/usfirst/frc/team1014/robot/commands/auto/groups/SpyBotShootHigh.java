package org.usfirst.frc.team1014.robot.commands.auto.groups;

import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveServo;
import org.usfirst.frc.team1014.robot.commands.auto.AutoShoot;
import org.usfirst.frc.team1014.robot.commands.auto.MoveToLowerRetroTape;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 * @author Manu S.
 *
 */
public class SpyBotShootHigh extends CommandGroup
{
	public SpyBotShootHigh()
	{
		this.addSequential(new AutoDriveServo(true));
		this.addSequential(new MoveToLowerRetroTape());
		this.addSequential(new AutoShoot(1.5));
	}
}
