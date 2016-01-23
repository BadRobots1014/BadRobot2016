package org.usfirst.frc.team1014.robot.commands;

// The imports for the final subsystems
import org.usfirst.frc.team1014.robot.OI;
import org.usfirst.frc.team1014.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1014.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandBase extends Command {
	
    public static OI oi;
    public static DriveTrain driveTrain;
    public static Shooter shooter;
    
    // The subsystems on the final robot go here
    
    @SuppressWarnings("static-access")
	public static void init() {
        //Final Subsystems
    	
    	driveTrain = DriveTrain.getInstance();
    	shooter = new Shooter();
    	//camera  = new AxisCamera("axis-camera.local");
    	
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
        oi.init();
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
