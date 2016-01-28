package org.usfirst.frc.team1014.utilities;

import org.usfirst.frc.team1014.utilities.Logger.Level;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SmartDashboard
{
	public static SmartDashboard smartDashboard;
	public static NetworkTable table;
	public String[] commands = {"driveTele"};
	private static final String commandsPackageName = "org.usfirst.frc.team1014.robot.commands.";
	
	public SmartDashboard()
	{
		table = NetworkTable.getTable("SmartDashboard");
		setup();
		initDashboard();
	}
	
	public static SmartDashboard getInstance()
	{
		if(smartDashboard == null)
		{
			smartDashboard = new SmartDashboard();
		}
		return smartDashboard;
	}
	
	private void initDashboard()
	{
		CameraServer server = CameraServer.getInstance();
		server.startAutomaticCapture("cam0");
		Logger.log(Level.Debug, "SmartDash", "Camera initialized");
	}
	
	private void setup()
	{
		for(String str:commands)
		{
			try
			{
				Class.forName(str);
			}catch(Exception e)
			{
				e.printStackTrace();
				continue;
			}
			table.putBoolean(str, false);
		}
	}
	
	public void poll()
	{
		for(String str:commands)
		{
			if(table.getBoolean(str, false))
			{
				try {
					Scheduler.getInstance().add((Command)Class.forName(commandsPackageName + str).newInstance());
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
					continue;
				}
			}
		}
	}
	
	
	
	public void update()
	{
		
	}
}
