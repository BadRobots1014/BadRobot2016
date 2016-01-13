package org.usfirst.frc.team1014.robot.subsystems;

import org.usfirst.frc.team1014.utilities.Logger;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class PixyCam extends BadSubsystem
{
	SerialPort pixyPort;
	byte[] buf = new byte[32];
	private static PixyCam instance;

	public static PixyCam getInstance()
	{
		if(instance == null)
			instance = new PixyCam();
		return instance;
	}

	@Override
	protected void initialize()
	{
		pixyPort = new SerialPort(4800, Port.kOnboard);

	}

	@Override
	public String getConsoleIdentity()
	{
		return "PixyCam";
	}

	@Override
	protected void initDefaultCommand()
	{

	}

	public void grabAndLog()
	{
		buf = pixyPort.read(32);
		Logger.logThis(buf.toString());
	}

	public void die()
	{
		pixyPort.free();
	}
}