package org.usfirst.frc.team1014.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 * @author Tze Hei T.
 * 
 */
public class GoOver extends CommandGroup
{
	public GoOver()
	{
		this.addSequential(new DriveForwardDistance(1, 132));
	}
}