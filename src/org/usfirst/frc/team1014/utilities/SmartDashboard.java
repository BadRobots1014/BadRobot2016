package org.usfirst.frc.team1014.utilities;

import java.util.Set;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.utilities.Logger.Level;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SmartDashboard
{
	public static SmartDashboard smartDashboard;
	public static NetworkTable table;
	Set<String> keys;
	
	public SmartDashboard()
	{
		NetworkTable.initialize();
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
	
	public void initDashboard()
	{
		CameraServer server = CameraServer.getInstance();
		server.startAutomaticCapture("cam0");
		Logger.log(Level.Debug, "SmartDash", "Camera initialized");
	}
	
	public void setup()
	{
		keys = CommandBase.commands.keySet();
		for(String key:keys)
		{
			table.putBoolean(key, false);
		}
	}
	
	public void poll()
	{
		for(String key:keys)
		{
			if(table.getBoolean(key, false))
				Scheduler.getInstance().add(CommandBase.commands.get(key));
		}
	}
	
	
	
	public void update()
	{
		
	}
}
