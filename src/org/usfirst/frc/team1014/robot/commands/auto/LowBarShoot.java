package org.usfirst.frc.team1014.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarShoot extends CommandGroup
{
	public LowBarShoot()
	{
		this.addSequential(new AutoDriveServo(true));
		this.addSequential(new PreDefinedRotation(true));
		this.addSequential(new AutoDrive(4, .6));
		this.addSequential(new AutoTurn(60.0));
		this.addSequential(new AutoShoot(3.0));
	}
}
