package org.usfirst.frc.team1014.robot.commands.auto.defenses;

import org.usfirst.frc.team1014.robot.commands.BadCommandGroup;
import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveDistanceEncoder;
import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveServo;
import org.usfirst.frc.team1014.robot.commands.auto.PreDefinedRotation;

public class GenericCrossDefense extends BadCommandGroup
{
	public GenericCrossDefense()
	{
	}

	public void build()
	{
		this.addSequential(new AutoDriveServo(true));
		this.addSequential(new AutoDriveDistanceEncoder(1, 4.3));
		this.addSequential(new PreDefinedRotation(true));
	}
}
