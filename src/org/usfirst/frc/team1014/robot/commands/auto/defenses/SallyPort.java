package org.usfirst.frc.team1014.robot.commands.auto.defenses;

import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveDistanceEncoder;
import org.usfirst.frc.team1014.robot.commands.auto.AutoDriveServo;
import org.usfirst.frc.team1014.robot.commands.auto.AutoSallyPortArm;
import org.usfirst.frc.team1014.robot.commands.auto.AutoTurn;
import org.usfirst.frc.team1014.robot.commands.auto.PreDefinedRotation;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SallyPort extends CommandGroup
{
	public SallyPort()
	{
		this.addSequential(new AutoDriveServo(true));
		this.addSequential(new AutoSallyPortArm(new Double(3), true));
		this.addSequential(new AutoDriveDistanceEncoder(-1, .5));
		this.addSequential(new AutoTurn(new Double(-15)));
		this.addSequential(new AutoSallyPortArm(new Double(3), false));
		this.addSequential(new AutoTurn(new Double(15)));
		this.addSequential(new AutoDriveDistanceEncoder(1, .5));
		this.addSequential(new AutoDriveDistanceEncoder(.5, 4.3));
		this.addSequential(new PreDefinedRotation(true));
	}
}
