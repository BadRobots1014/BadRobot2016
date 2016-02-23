package org.usfirst.frc.team1014.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 * @author Tze Hei T.
 * 
 */
public class AutoMoat extends CommandGroup
{

	public AutoMoat()
	{
		this.addSequential(new AutoRotateDown());
		this.addSequential(new AutoDrive(2, 1));
		this.addSequential(new AutoRotate(.5, true));
	}

}
