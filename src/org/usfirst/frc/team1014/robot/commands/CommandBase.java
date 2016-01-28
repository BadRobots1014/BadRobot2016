package org.usfirst.frc.team1014.robot.commands;


// The imports for the final subsystems
import org.usfirst.frc.team1014.robot.OI;
import org.usfirst.frc.team1014.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandBase extends Command
{
	public static DriveTrain driveTrain;
	
	// The subsystems on the final robot go here

	public static void init()
	{
		// Final Subsystems
		driveTrain = DriveTrain.getInstance();

		// camera = new AxisCamera("axis-camera.local");
		
		OI.init();
		
		// Show what command your subsystem is running on the SmartDashboard
		// SmartDashboard.putData(exampleSubsystem);
	}

	public CommandBase(String name)
	{
		super(name);
	}

	public CommandBase()
	{
		super();
	}

	protected abstract void initialize();

	public abstract String getConsoleIdentity();
}
