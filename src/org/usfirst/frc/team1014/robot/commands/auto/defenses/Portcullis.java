package org.usfirst.frc.team1014.robot.commands.auto.defenses;

import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveServo;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Portcullis extends CommandGroup
{
	public Portcullis()
	{
		this.addSequential(new AutoDriveServo(true));
		// We don't have a thing for this so....
	}
}
