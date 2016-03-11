package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoSallyPortArm extends CommandBase
{
	public double endTime;
	public double runTime;
	public double currentTime;
	public boolean goingOut;

	/**
	 * Constructor. {@code time} is how long the arm should move and {@code out} determines which
	 * direction. If {@code out} is true, the arm will move out.
	 * 
	 * @param time
	 *            - how long to move the arm
	 * @param out
	 *            - should the arm be going out
	 */
	public AutoSallyPortArm(double time, boolean out)
	{
		requires((Subsystem) arm);
		runTime = time * 1;
		goingOut = out;
	}

	@Override
	protected void end()
	{
		arm.useIt(0);
	}

	@Override
	protected void execute()
	{
		if(!goingOut)
			arm.useIt(-.25);
		else
			arm.useIt(.25);
		
		Logger.logThis("endTime: " + endTime);
		Logger.logThis("currentTime: " + currentTime);
	}

	@Override
	protected void initialize()
	{
		currentTime = Utility.getFPGATime();
		endTime = currentTime + runTime;
	}

	@Override
	protected void interrupted()
	{
		Logger.logThis(getConsoleIdentity() + ": I've been interrupted!");
	}

	@Override
	protected boolean isFinished()
	{
		if(currentTime > endTime)
		{
			Logger.logThis("DONE!");
			return true;
		}else
			return false;
	}

	@Override
	public String getConsoleIdentity()
	{
		return "AutoSallyPortArm";
	}

}
