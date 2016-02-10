package org.usfirst.frc.team1014.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TeleopGroup extends CommandGroup {

	public TeleopGroup() {
		this.addParallel(new TeleDrive());
		this.addParallel(new UseShooter());
	}
}
