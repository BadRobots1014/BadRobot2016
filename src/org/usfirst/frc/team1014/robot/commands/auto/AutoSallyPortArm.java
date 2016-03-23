package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

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
	public AutoSallyPortArm(Double time, Boolean out)
	{
		requires((Subsystem) arm);
		runTime = time * 1000000;
		endTime = Utility.getFPGATime() + runTime;
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
			arm.useIt(-.4);
		else
			arm.useIt(.4);

		currentTime = Utility.getFPGATime();
	}

	@Override
	protected void initialize()
	{
		arm.useIt(0);
	}

	@Override
	protected boolean isFinished()
	{
		return currentTime > endTime;
	}

	@Override
	public String getConsoleIdentity()
	{
		return "Auto_Sally_Port_Arm";
	}

}
