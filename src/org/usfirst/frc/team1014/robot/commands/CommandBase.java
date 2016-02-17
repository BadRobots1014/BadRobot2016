package org.usfirst.frc.team1014.robot.commands;

import java.util.ArrayList;

import org.usfirst.frc.team1014.robot.commands.auto.AutoDrive;
import org.usfirst.frc.team1014.robot.commands.auto.AutoGrab;
import org.usfirst.frc.team1014.robot.commands.auto.AutoShoot;
import org.usfirst.frc.team1014.robot.commands.auto.AutoTurn;
import org.usfirst.frc.team1014.robot.commands.auto.DriveForwardDistance;
import org.usfirst.frc.team1014.robot.commands.auto.FindTarget;
import org.usfirst.frc.team1014.robot.commands.auto.GoOver;
import org.usfirst.frc.team1014.robot.commands.auto.GoOverAndComeBack;
import org.usfirst.frc.team1014.robot.commands.auto.ShootAndComeBack;
import org.usfirst.frc.team1014.robot.commands.auto.ShootAndStay;
// The imports for the final subsystems
import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1014.robot.subsystems.ShooterAndGrabber;
import org.usfirst.frc.team1014.robot.utilities.Logger;
import org.usfirst.frc.team1014.robot.utilities.Logger.Level;

import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandBase extends Command
{

	public static DriveTrain driveTrain;
	public static ShooterAndGrabber shooter;

	public static ArrayList<Command> commandClasses = new ArrayList<Command>();

	// The subsystems on the final robot go here

	/**
	 * Initializes all commands that the robot will needed. This function should only be called
	 * once, from the main Robot class.
	 */
	public static void init()
	{
		// Final Subsystems

		driveTrain = DriveTrain.getInstance();
		shooter = ShooterAndGrabber.getInstance();
		// camera = new AxisCamera("axis-camera.local");

		// This MUST be here. If the OI creates Commands (which it very likely
		// will), constructing it during the construction of CommandBase (from
		// which commands extend), subsystems are not guaranteed to be
		// yet. Thus, their requires() statements may grab null pointers. Bad
		// news. Don't move it.
		ControlsManager.init();
		addAuto();
	}

	public static void addAuto()
	{
		commandClasses.add(new AutoGrab(0.0));
		commandClasses.add(new AutoShoot(0.0));
		commandClasses.add(new AutoTurn(90.0));
		commandClasses.add(new AutoDrive(0.0, 0.0));
		commandClasses.add(new DriveForwardDistance(0.0, 0.0));
		commandClasses.add(new GoOver());
		commandClasses.add(new GoOverAndComeBack());
		commandClasses.add(new ShootAndComeBack());
		commandClasses.add(new ShootAndStay());
		commandClasses.add(new FindTarget());
	}

	public CommandBase()
	{
		super();
	}

	protected abstract void initialize();

	public abstract String getConsoleIdentity();

	protected boolean isFinished = false;

	/*
	 * public boolean isFinished() { return isFinished; }
	 */

	/**
	 * Not sure what this is used for.
	 */
	@Override
	protected void interrupted()
	{
		Logger.log(Level.Error, getConsoleIdentity(), "I've been interrupted!");
	}

}
