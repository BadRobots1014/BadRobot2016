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
	public static Command autonomousCommand; // the autonomous command the robot should run
	public static SmartDashboard dashboard; // the dashboard the robot should use
	public static BadScheduler badScheduler; // the scheduler the robot should use

	public static boolean lowVoltage = false; // is the robot under 12 V resting

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	public void robotInit()
	{
		// initializes the subsystems, autonomous commands, dashboard, controllers, and acheduler
		CommandBase.init();
		AutonomousManager.getInstance().loadAutonoumsCommands();
		dashboard = SmartDashboard.getInstance();
		ControlsManager.init();
		badScheduler = new BadScheduler(TeleopGroup.class);

		// set lights to initial state
		if(CommandBase.lights != null)
		{
			CommandBase.lights.setLights(LEDState.kGATHER);
		}
	}

	/**
	 * This function is called periodically while the robot is disabled
	 */
	public void disabledPeriodic()
	{
		Scheduler.getInstance().run();
		
		// sets LED lights based on battery level on disabled
		if(PowerJNI.getVinVoltage() < 12f)
		{
			LEDLights.getInstance().setLights(LEDState.kLOW_BATTERY);
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
		// assigns controller layouts to the controllers
		ControlsManager.updateControllers();
		
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if(autonomousCommand != null)
			autonomousCommand.cancel();
		
		// initializes teleop today
		badScheduler.initTeleop();

		// set lights to alliance color
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
		// allows the drivers to change using BadScheduler
		badScheduler.changeCommand();
		
		// runs the programs in the scheduler
		Scheduler.getInstance().run();
		
		// sets the lights to low battery if that is the case
		if(PowerJNI.getVinVoltage() < 7f)
		{
			lowVoltage = true;
			LEDLights.getInstance().setLights(LEDState.kLOW_BATTERY);
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
