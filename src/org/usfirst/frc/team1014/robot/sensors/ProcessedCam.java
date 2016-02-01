package org.usfirst.frc.team1014.robot.sensors;

public class ProcessedCam 
{
	public static  ProcessedCam processedCam;
	
	public static ProcessedCam getInstance()
	{
		if(processedCam == null)
		{
			processedCam = new ProcessedCam();
		}
		return processedCam;
	}
	
	private double X = 0;
	private double Y = 0;
	
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x - 320;
	}
	public double getY() {
		return Y;
	}
	public void setY(double y) {
		Y = y - 240;
	}
}
