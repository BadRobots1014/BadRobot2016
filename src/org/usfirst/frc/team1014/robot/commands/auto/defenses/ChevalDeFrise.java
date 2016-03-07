package org.usfirst.frc.team1014.robot.commands.auto.defenses;

import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveDistanceEncoder;
import org.usfirst.frc.team1014.robot.commands.auto.AutoRotate;
import org.usfirst.frc.team1014.robot.subsystems.ShooterAndGrabber;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ChevalDeFrise extends CommandGroup
{
	public ChevalDeFrise()
	{
		this.addSequential(new AutoRotate(ShooterAndGrabber.SHOOTER_LOWEST_POS));
		this.addSequential(new AutoDriveDistanceEncoder(1, .3));
		this.addParallel(new AutoRotate(ShooterAndGrabber.SHOOTER_HIGHEST_POS));
		this.addParallel(new AutoDriveDistanceEncoder(1, 4));
	}
}
