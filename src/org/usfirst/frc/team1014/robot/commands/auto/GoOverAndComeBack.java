package org.usfirst.frc.team1014.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 * @author Tze Hei T.
 *
 */
public class GoOverAndComeBack extends CommandGroup
{

	public GoOverAndComeBack()
	{

		this.addSequential(new DriveForwardDistance(1, 132));
		this.addSequential(new DriveForward(2, 1));

	}

}
