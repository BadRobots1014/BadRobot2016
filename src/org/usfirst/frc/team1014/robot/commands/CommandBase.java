package org.usfirst.frc.team1014.robot.commands;

import java.util.HashMap;

// The imports for the final subsystems
import org.usfirst.frc.team1014.robot.OI;
import org.usfirst.frc.team1014.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1014.robot.subsystems.PixyCam;
import org.usfirst.frc.team1014.utilities.SmartDashboard;

import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandBase extends Command
{
	public static DriveTrain driveTrain;
	public static PixyCam pixy;
	public static HashMap<String, Command> commands;
	
	// The subsystems on the final robot go here

	public static void init()
	{
		// Final Subsystems
		driveTrain = DriveTrain.getInstance();
		//pixy = PixyCam.getInstance();
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
		commands.put(getConsoleIdentity(), this);
	}

	protected abstract void initialize();

	public abstract String getConsoleIdentity();
}
