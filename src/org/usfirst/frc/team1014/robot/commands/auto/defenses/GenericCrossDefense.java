package org.usfirst.frc.team1014.robot.commands.auto.defenses;

import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveDistanceEncoder;
import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveServo;
import org.usfirst.frc.team1014.robot.commands.auto.PreDefinedRotation;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GenericCrossDefense extends CommandGroup
{
	public GenericCrossDefense()
	{
		this.addSequential(new AutoDriveServo(true));
		this.addSequential(new AutoDriveDistanceEncoder(1, 4.3));
		this.addSequential(new PreDefinedRotation(true));
	}
}
