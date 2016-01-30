package org.usfirst.frc.team1014.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GoOver extends CommandGroup{
	
	public GoOver(){
		
		this.addSequential(new DriveForwardDistance(1, 132));
		
	}

}
