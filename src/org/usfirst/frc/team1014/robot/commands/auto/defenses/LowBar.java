package org.usfirst.frc.team1014.robot.commands.auto.defenses;

import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveDistanceEncoder;
import org.usfirst.frc.team1014.robot.commands.auto.AutoRotate;
import org.usfirst.frc.team1014.robot.subsystems.ShooterAndGrabber;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBar extends CommandGroup
{
	public LowBar()
	{
		this.addSequential(new AutoRotate(ShooterAndGrabber.SHOOTER_LOWEST_POS));
		this.addSequential(new AutoDriveDistanceEncoder(.35, 6.2));
		this.addSequential(new AutoRotate(ShooterAndGrabber.SHOOTER_HIGHEST_POS));
	}

}
