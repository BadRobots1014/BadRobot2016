package org.usfirst.frc.team1014.robot;
import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.commands.TeleopGroup;
import org.usfirst.frc.team1014.robot.commands.auto.FindTarget;
import org.usfirst.frc.team1014.robot.controls.BadScheduler;
import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.utilities.SmartDashboard;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot
{
	public Command autonomousCommand;
	public static SmartDashboard dashboard;
	public static BadScheduler badScheduler = new BadScheduler(TeleopGroup.class);
	public FindTarget visionTracking;

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	public void robotInit()
	{

		// SmartDashboard.initDashboard();

		CommandBase.init();
		dashboard = new SmartDashboard();
		visionTracking = new FindTarget();
	}

	/**
	 * This function is called periodically while the robot is disabled
	 */
	public void disabledPeriodic()
	{
		Scheduler.getInstance().run();
	}

	/**
	 * Called when autonomous is first started
	 */
	public void autonomousInit()
	{
		// schedule the autonomous command (example)
		if(autonomousCommand != null)
			autonomousCommand.start();
		dashboard.poll();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic()
	{
		dashboard.update();
		Scheduler.getInstance().run();
	}

	/**
	 * Called when teleop is first started
	 */
	public void teleopInit()
	{
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if(autonomousCommand != null)
			autonomousCommand.cancel();
		badScheduler.initTeleop();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it to reset subsystems
	 * before shutting down.
	 */
	public void disabledInit()
	{

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic()
	{
		dashboard.update();
		badScheduler.changeCommand(ControlsManager.secondaryXboxController.isYButtonPressed(), FindTarget.class);
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic()
	{
		LiveWindow.run();
	}
}
