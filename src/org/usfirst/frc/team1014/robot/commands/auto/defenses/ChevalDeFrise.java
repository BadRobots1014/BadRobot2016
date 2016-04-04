package org.usfirst.frc.team1014.robot.commands.auto.defenses;

import org.usfirst.frc.team1014.robot.commands.BadCommandGroup;
import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveDistanceEncoder;
import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveServo;
import org.usfirst.frc.team1014.robot.commands.auto.PreDefinedRotation;

public class ChevalDeFrise extends BadCommandGroup
{
	public ChevalDeFrise()
	{

	}

	public void build()
	{
		this.addSequential(new AutoDriveServo(true));
		this.addSequential(new PreDefinedRotation(true));
		this.addSequential(new AutoDriveDistanceEncoder(.5, .3));
		this.addParallel(new PreDefinedRotation(false));
		this.addParallel(new AutoDriveDistanceEncoder(1, 4));
		this.addSequential(new PreDefinedRotation(true));
	}
}
