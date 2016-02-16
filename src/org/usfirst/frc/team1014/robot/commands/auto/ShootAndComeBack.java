package org.usfirst.frc.team1014.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 * @author Tze Hei T.
 * 
 */
public class ShootAndComeBack extends CommandGroup
{
	public ShootAndComeBack()
	{
		this.addSequential(new DriveForwardDistance(1, 132));
		this.addSequential(new AutoTurn(90));
		this.addSequential(new AutoDrive(.5, 1));
		this.addSequential(new AutoTurn(-90));
		this.addSequential(new AutoShoot(1));
		this.addSequential(new AutoTurn(-90));
		this.addSequential(new AutoDrive(.5, 1));
		this.addSequential(new AutoTurn(-90));
		this.addSequential(new AutoDrive(2, 1));
	}
}