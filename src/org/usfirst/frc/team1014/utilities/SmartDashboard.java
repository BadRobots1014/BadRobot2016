package org.usfirst.frc.team1014.utilities;

import org.usfirst.frc.team1014.utilities.Logger.Level;

import edu.wpi.first.wpilibj.CameraServer;

public class SmartDashboard
{
	public static void initDashboard()
	{
		CameraServer server = CameraServer.getInstance();
		server.startAutomaticCapture("cam0");
		Logger.log(Level.Debug, "SmartDash", "Camera initialized");
	}
}
