package org.usfirst.frc.team1014.robot;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.commands.TeleopGroup;
import org.usfirst.frc.team1014.robot.commands.auto.AutonomousManager;
import org.usfirst.frc.team1014.robot.controls.BadScheduler;
import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.subsystems.LEDLights;
import org.usfirst.frc.team1014.robot.subsystems.LEDLights.LEDState;
import org.usfirst.frc.team1014.robot.utilities.Logger;
import org.usfirst.frc.team1014.robot.utilities.Logger.Level;
import org.usfirst.frc.team1014.robot.utilities.SmartDashboard;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.hal.PowerJNI;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot
{
	public static Command autonomousCommand;
	public static SmartDashboard dashboard;
	public static BadScheduler badScheduler;

	public static boolean lowVoltage = false;

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	public void robotInit()
	{
		CommandBase.init();
		// AutonomousManager.getInstance().loadAutonoumsCommands();
		dashboard = SmartDashboard.getInstance();
		ControlsManager.init();
		badScheduler = new BadScheduler(TeleopGroup.class);

		// set lights to alliance color (probably)
		if(CommandBase.lights != null)
		{
			// CommandBase.lights.SetLights(DriverStation.getInstance().getAlliance() ==
			// DriverStation.Alliance.Blue ? LEDState.kBLUE : LEDState.kRED);
			CommandBase.lights.setLights(LEDState.kGATHER);
		}
	}

	/**
	 * This function is called periodically while the robot is disabled
	 */
	public void disabledPeriodic()
	{
		Scheduler.getInstance().run();
		if(PowerJNI.getVinVoltage() < 12f)
		{
			LEDLights.getInstance().setLights(LEDState.kBATTERY);
			Logger.log(Level.Warning, "Battery", "Battery voltage under 12 while disabled!");
		}
	}

	/**
	 * Called when autonomous is first started
	 */
	public void autonomousInit()
	{
		// schedule the autonomous command (example)
		autonomousCommand = dashboard.poll();

		// build the autonomous to run
		// autonomousCommand.build();

		// System.out.println(AutonomousManager.pollSwitches());
		Scheduler.getInstance().add(autonomousCommand);

		// set lights to alliance color (definitely?)
		if(CommandBase.lights != null)
			CommandBase.lights.setLights(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue ? LEDState.kBLUE : LEDState.kRED);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic()
	{
		AutonomousManager.pollSwitches();
		Scheduler.getInstance().run();
	}

	/**
	 * Called when teleop is first started
	 */
	public void teleopInit()
	{
		ControlsManager.updateControllers();
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.

		if(autonomousCommand != null)
			autonomousCommand.cancel();
		badScheduler.initTeleop();

		// set lights to alliance color (definitely?)
		if(CommandBase.lights != null)
			CommandBase.lights.setLights(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue ? LEDState.kBLUE : LEDState.kRED);
	}

	/**
	 * This function is called when the disabled button is hit. You can use it to reset subsystems
	 * before shutting down.
	 */
	public void disabledInit()
	{
		Robot.lowVoltage = false;
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic()
	{
		badScheduler.changeCommand();
		Scheduler.getInstance().run();
		// TODO: Chance volatge lower bound
		if(PowerJNI.getVinVoltage() < 10f)
		{
			lowVoltage = true;
			LEDLights.getInstance().setLights(LEDState.kBATTERY);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic()
	{
		LiveWindow.run();
	}
}
