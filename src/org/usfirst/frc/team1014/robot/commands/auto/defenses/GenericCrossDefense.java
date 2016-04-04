package org.usfirst.frc.team1014.robot.commands.auto.defenses;

import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveDistanceEncoder;
import org.usfirst.frc.team1014.robot.commands.auto.AutoRotate;
import org.usfirst.frc.team1014.robot.subsystems.ShooterAndGrabber;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GenericCrossDefense extends CommandGroup
{
	public GenericCrossDefense()
	{
		this.addSequential(new AutoRotate(ShooterAndGrabber.SHOOTER_HIGHEST_POS));
		this.addSequential(new AutoDriveDistanceEncoder(1, 4.3));
	}
}
