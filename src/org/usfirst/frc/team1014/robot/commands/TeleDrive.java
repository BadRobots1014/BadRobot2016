package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.Robot;
import org.usfirst.frc.team1014.robot.controls.ControllerLayout;
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
		if(ControlsManager.driver.getButtonValue(1, ControllerLayout.driveStraight))
		{
			if(!gyroSet)
			{
				targetGyro = driveTrain.getAngle();
				gyroSet = true;
			}
			// TODO: Change so we are not inverting
			driveTrain.driveStraight(-ControlsManager.driver.getStickValue(1, ControllerLayout.leftDrive), targetGyro);
		}
		else
		{
			if(ControlsManager.driver.getButtonValue(1, ControllerLayout.adjustBackward))
				driveTrain.tankDrive(.6, .6);
			else if(ControlsManager.driver.getButtonValue(1, ControllerLayout.adjustForward))
				driveTrain.tankDrive(-.6, -.6);
			else if(ControlsManager.driver.getButtonValue(1, ControllerLayout.adjustLeft))
				driveTrain.tankDrive(.6, -.6);
			else if(ControlsManager.driver.getButtonValue(1, ControllerLayout.adjustRight))
				driveTrain.tankDrive(-.6, .6);
			else
				driveTrain.tankDrive(-ControlsManager.driver.getStickValue(1, ControllerLayout.rightDrive), -ControlsManager.driver.getStickValue(1, ControllerLayout.leftDrive));

			gyroSet = false;
		}

		// Switch between primary and secondary layouts;
		if(ControlsManager.driver.getLayoutChange())
			ControlsManager.changeToSecondaryLayout(1);
		else
			ControlsManager.changeToPrimaryLayout(1);

		if(ControlsManager.driver.getButtonValue(1, ControllerLayout.underVoltClear) || ControlsManager.driver.getButtonValue(2, ControllerLayout.underVoltClear))
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
