package org.usfirst.frc.team1014.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarShoot extends CommandGroup
{
	public LowBarShoot()
	{
		this.addSequential(new AutoDriveServo(true));
		this.addSequential(new PreDefinedRotation(true));
		this.addSequential(new AutoDriveDistanceEncoder(.7, 8.409));
		this.addSequential(new AutoTurn(60.0));
		this.addSequential(new AutoShoot(1.0));
	}
}
