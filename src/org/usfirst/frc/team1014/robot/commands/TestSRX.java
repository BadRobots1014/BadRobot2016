package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;

public class TestSRX extends CommandBase
{

	public TestSRX()
	{
		requires((Subsystem) testBed);
	}

	@Override
	protected void initialize()
	{
		testBed.testSRX(0.0);
	}

	@Override
	public String getConsoleIdentity()
	{
		return "TestSRX";
	}

	@Override
	protected void end()
	{
		testBed.testSRX(0.0);
	}

	@Override
	protected void execute()
	{
		testBed.testSRX(ControlsManager.primaryXboxController.getLeftStickY());
		Logger.logThis("Encoder Value: " + testBed.getEncoderValue());
		Logger.logThis("Encoder RPM: " + testBed.getEncoderRPM());
	}

	@Override
	protected void interrupted()
	{

	}

	@Override
	protected boolean isFinished()
	{
		return false;
	}

}