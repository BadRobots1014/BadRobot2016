package org.usfirst.frc.team1014.robot.sensors;

/**
 * 
 * @author Subash C.
 * 
 */
public class ProcessedCam
{
	public static ProcessedCam processedCam;
	private static double halfWidth = 160;
	private static double halfHeight = 120;

	public static ProcessedCam getInstance()
	{
		if(processedCam == null)
			processedCam = new ProcessedCam();
		return processedCam;
	}

	private double X = 0;
	private double Y = 0;
	private double trackingScore = 0;

	public double getX()
	{
		return X;
	}

	public void setX(double x)
	{
		X = x - halfWidth;
	}

	public double getTrackingScore()
	{
		return trackingScore;
	}

	public void setTrackingScore(double trackingScore)
	{
		this.trackingScore = trackingScore;
	}

	public double getY()
	{
		return Y;
	}

	public void setY(double y)
	{
		Y = y - halfHeight;
	}

	public double getHalfWidth()
	{
		return halfWidth;
	}

	public void setHalfWidth(double halfWidth)
	{
		ProcessedCam.halfWidth = halfWidth;
	}

	public double getHalfHeight()
	{
		return halfHeight;
	}

	public void setHalfHeight(double halfHeight)
	{
		ProcessedCam.halfHeight = halfHeight;
	}
}
