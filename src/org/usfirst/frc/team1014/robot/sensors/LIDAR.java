package org.usfirst.frc.team1014.robot.sensors;

import java.util.TimerTask;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;

/**
 * This is the class that defines the LIDAR sensor
 * and how it is supposed to work.
 * @author Manu S.
 *
 */
public class LIDAR {

	private I2C i2c;
	private byte[] distance;
	
	private final int LIDAR_ADDR = 0x62;
	private final int LIDAR_CONFIG_REGISTER = 0x00;
	private final int LIDAR_DISTANCE_REGISTER = 0x8f;
	private java.util.Timer updater;
	
	private long lastUpdateTime;
	
	public LIDAR(Port port)
	{
		i2c = new I2C(port, LIDAR_ADDR);
		
		distance = new byte[2];
		
		lastUpdateTime = System.currentTimeMillis();
		updater = new java.util.Timer();
	}
	
	public void start() {
		updater.scheduleAtFixedRate(new LIDARUpdater(), 0, 100);
	}
	
	public void start(int period) {
		updater.scheduleAtFixedRate(new LIDARUpdater(), 0, period);
	}
	
	public void stop() {
		updater.cancel();
		updater = new java.util.Timer();
	}
	
	public long updateTime() {
		return (System.currentTimeMillis() - lastUpdateTime);
	}
	
	/**
	 * This method uses the updated distance array and
	 * converts it to an intelligible number that represents
	 * the distance the LIDAR reads in inches.
	 * @return the distance to the object most directly
	 * in the LIDAR's path
	 */
	public int getDistance() {
		return (int)Integer.toUnsignedLong(distance[0] << 8) + Byte.toUnsignedInt(distance[1]);
	}
	
	/**
	 * This is what actually gets the distance and stores it
	 * in a byte array. 
	 * @return true - if the distance was read; false otherwise
	 */
	public boolean updateDistance() {
		if(System.currentTimeMillis() - lastUpdateTime >= 100) {
			i2c.write(LIDAR_CONFIG_REGISTER, 0x04);
			Timer.delay(0.04); 
			i2c.read(LIDAR_DISTANCE_REGISTER, 2, distance); 
			Timer.delay(0.005); 
			lastUpdateTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}
	
	/**
	 * This small class helps the LIDAR time how often it
	 * reads in information so that it doesnt overload itself.
	 * @author Manu S.
	 *
	 */
	private class LIDARUpdater extends TimerTask {
		public void run() {
			while(true) {
				updateDistance();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
