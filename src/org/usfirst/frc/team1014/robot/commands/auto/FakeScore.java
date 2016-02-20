package org.usfirst.frc.team1014.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FakeScore extends CommandGroup
{
	public FakeScore()
	{
		this.addSequential(new AutoDrive(1.0, .5));
		this.addSequential(new AutoTurn(50));
		this.addSequential(new AutoRotate(.5, true));
		this.addSequential(new AutoShoot(.5));
	}
}
