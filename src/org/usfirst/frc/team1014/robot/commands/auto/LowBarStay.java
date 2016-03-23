package org.usfirst.frc.team1014.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarStay extends CommandGroup
{
	public LowBarStay()
	{
		this.addSequential(new AutoDriveServo(true));
		this.addSequential(new PreDefinedRotation(true));
		this.addSequential(new AutoDriveDistanceEncoder(.6, 8.409));
		this.addSequential(new AutoTurn(60.0));
	}
}
