package org.usfirst.frc.team1014.robot.commands;

import org.usfirst.frc.team1014.robot.OI;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;


public class TestSRX extends CommandBase 
{

	public TestSRX()
	{
		requires((Subsystem) testBed);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		testBed.testSRX(0.0);
	}

	@Override
	public String getConsoleIdentity() {
		// TODO Auto-generated method stub
		return "TestSRX";
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		testBed.testSRX(0.0);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		testBed.testSRX(OI.priXboxController.getLeftStickY());
		Logger.logThis("Encoder Value: " + testBed.getEncoderValue());
		Logger.logThis("Encoder RPM: " + testBed.getEncoderRPM());
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
}