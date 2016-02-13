package org.usfirst.frc.team1014.robot.utilities;

import java.util.ArrayList;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.sensors.ProcessedCam;
import org.usfirst.frc.team1014.robot.utilities.Logger.Level;

import com.ni.vision.VisionException;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class SmartDashboard
{
	public static SmartDashboard smartDashboard;
	public static NetworkTable table;
	private ArrayList<Command> commandClasses = CommandBase.commandClasses;
	private static String commandToRun;
	private static final String commandRunKey = "Command running: ";

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
//		CameraServer server = CameraServer.getInstance();
//		USBCamera camera;
//		try
//		{
//			camera = new USBCamera("cam0");
//			camera.openCamera();
//			server.startAutomaticCapture(camera);
//		} catch(VisionException ex)
//		{
//			Logger.log(Level.Error, "SmartDash", "Camera(cam0) failed to initialize");
//		} finally
//		{
//			server.startAutomaticCapture("cam0");
//			Logger.log(Level.Debug, "SmartDash", "Camera(cam0) initialized");
//		}
	}

	private void setup()
	{
		table.putString(commandRunKey, "");
		for(Command command : commandClasses)
			table.putBoolean(command.getName(), false);
	}

	public void poll()
	{
		for(Command command : commandClasses)
		{
			if(table.getBoolean(command.getName(), false))
			{
				Scheduler.getInstance().add(command);
				commandToRun = command.getName();
				table.putString(commandRunKey, commandToRun);
				break;
			}
		}
	}

	public void update()
	{
		ProcessedCam.getInstance().setX(table.getNumber("OBJECT_TRACKING_X", 0.0));
		ProcessedCam.getInstance().setY(table.getNumber("OBJECT_TRACKING_Y", 0.0));
		ProcessedCam.getInstance().setTrackingScore(table.getNumber("OBJECT_TRACKING_SCORE", 0.0));
		System.out.println("getX: " + ProcessedCam.getInstance().getX());
		System.out.println("getY: " + ProcessedCam.getInstance().getY());
		System.out.println("getScore: " + ProcessedCam.getInstance().getTrackingScore());
	}
}
