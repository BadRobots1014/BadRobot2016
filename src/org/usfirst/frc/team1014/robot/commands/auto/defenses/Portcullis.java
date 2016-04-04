package org.usfirst.frc.team1014.robot.commands.auto.defenses;

import org.usfirst.frc.team1014.robot.commands.BadCommandGroup;
import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveServo;

public class Portcullis extends BadCommandGroup
{
	public Portcullis()
	{
	}

	public void build()
	{
		this.addSequential(new AutoDriveServo(true));
		// We don't have a thing for this so....
	}
}
