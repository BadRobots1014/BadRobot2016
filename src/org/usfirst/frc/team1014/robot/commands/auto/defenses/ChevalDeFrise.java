package org.usfirst.frc.team1014.robot.commands.auto.defenses;

import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveDistanceEncoder;
import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveServo;
import org.usfirst.frc.team1014.robot.commands.auto.PreDefinedRotation;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ChevalDeFrise extends CommandGroup
{
	public ChevalDeFrise()
	{
		this.addSequential(new AutoDriveServo(true));
		this.addSequential(new PreDefinedRotation(true));
		this.addSequential(new AutoDriveDistanceEncoder(.5, .3));
		this.addParallel(new PreDefinedRotation(false));
		this.addParallel(new AutoDriveDistanceEncoder(1, 4));
		this.addSequential(new PreDefinedRotation(true));
	}
}
