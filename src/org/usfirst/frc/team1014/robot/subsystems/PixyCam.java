package org.usfirst.frc.team1014.robot.subsystems;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;

public class PixyCam extends BadSubsystem{

	SerialPort pixyPort;
	String bufOut;
	private static PixyCam instance;
	
	public PixyCam()
	{
		pixyPort = new SerialPort(9600, SerialPort.Port.kOnboard);
	}
	
	public static PixyCam getInstance()
	{
		if(instance == null)
		{
			instance = new PixyCam();
		}
		
		return instance;
	}
	
	
	@Override 
	protected void initialize() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public String getConsoleIdentity() {
		// TODO Auto-generated method stub
		return "PixyCam";
	}


	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void grabAndLog()
	{
		//bufOut = pixyPort.readString();
		//Logger.logThis(bufOut);
	}
	
	public void die()
	{
		pixyPort.free();
	}
	

}
