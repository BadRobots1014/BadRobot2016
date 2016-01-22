package org.usfirst.frc.team1014.utilities;

import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.CameraServer;

public class SmartDashboard
{
	public static void initDashboard()
	{
		CameraServer server = CameraServer.getInstance();
		server.startAutomaticCapture("cam0");
		Logger.log(Logger.Level.Debug, "SmartDash", "Camera initialized");
	}
}
