package org.usfirst.frc.team1014.robot.subsystems;
import org.usfirst.frc.team1014.robot.utilities.Logger;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class PixyCam extends BadSubsystem{

	SerialPort pixyPort;
	private static PixyCam instance;
	
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
		pixyPort = new SerialPort(4800,Port.kOnboard);
		
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
		
		pixyPort.writeString("1");
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buf = pixyPort.read(64);
		Logger.logThis(buf.toString());
	}
	
	public void die()
	{
		pixyPort.free();
	}
	

}
