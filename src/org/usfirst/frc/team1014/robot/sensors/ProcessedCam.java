package org.usfirst.frc.team1014.robot.sensors;

/**
 * 
 * @author Subash C.
 *
 */
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
	
	private double X;
	private double Y;
	private double ObjTrackScore;
	
	public double getObjTrackScore() {
		return ObjTrackScore;
	}
	public void setObjTrackScore(double objTrackScore) {
		ObjTrackScore = objTrackScore;
	}
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
