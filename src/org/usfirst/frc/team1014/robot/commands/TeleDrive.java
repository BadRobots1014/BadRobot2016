package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.Robot;
import org.usfirst.frc.team1014.robot.controls.ControlsManager;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This class defines how the robot drives through teleop.
 * 
 * @author Manu S.
 * 
 */
public class TeleDrive extends CommandBase
{
	public double targetGyro;
	public boolean gyroSet;

	public TeleDrive()
	{
		requires((Subsystem) driveTrain);
		targetGyro = 0;
		gyroSet = false;
	}

	/**
	 * This method runs before the command is executed to make sure everything is ready for it to be
	 * executed.
	 */
	@Override
	protected void initialize()
	{
		driveTrain.tankDrive(0, 0);
		driveTrain.resetMXPAngle();
	}

	/**
	 * @return the name of the class.
	 */
	@Override
	public String getConsoleIdentity()
	{
		return "Tele-Drive";
	}

	/**
	 * This is the method that gets called over and over again while the command is actually
	 * running.
	 */
	@Override
	protected void execute()
	{
		// Normal drive and drive straight
		if(ControlsManager.driver.getDriveStraight_(1))
		{
			if(!gyroSet)
			{
				targetGyro = driveTrain.getAngle();
				gyroSet = true;
			}
			// TODO: Change so we are not inverting
			driveTrain.driveStraight(-ControlsManager.driver.getLeftDrive_Shooter(1), targetGyro);
		}
		else
		{
			if(ControlsManager.driver.getAdjustBackward_ArticulatorUp(1))
				driveTrain.tankDrive(.6, .6);
			else if(ControlsManager.driver.getAdjustForward_ArticulatorDown(1))
				driveTrain.tankDrive(-.6, -.6);
			else if(ControlsManager.driver.getAdjustLeft_Servo(1))
				driveTrain.tankDrive(.6, -.6);
			else if(ControlsManager.driver.getAdjustRight_AutoShoot(1))
				driveTrain.tankDrive(-.6, .6);
			else
				driveTrain.tankDrive(-ControlsManager.driver.getLeftDrive_Shooter(1), -ControlsManager.driver.getRightDrive_Articulator(1));

			gyroSet = false;
		}

		// Switch between primary and secondary layouts;
		if(ControlsManager.driver.getLayoutChange())
			ControlsManager.changeToSecondaryLayout(1);
		else
			ControlsManager.changeToPrimaryLayout(1);

		if(ControlsManager.driver.getUnderVoltClear(1) || ControlsManager.driver.getUnderVoltClear(2))
			Robot.lowVoltage = false;

	}

	/**
	 * Lets the system know when to stop this command and do some other one.
	 */
	@Override
	protected boolean isFinished()
	{
		return false;
	}

	/**
	 * Removes loose ends and exits command properly.
	 */
	@Override
	protected void end()
	{
		driveTrain.tankDrive(0, 0);
	}
}
