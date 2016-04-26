package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Moves the servo pusher in or out in autonomous.
 * 
 * @author Manu S.
 *
 */
public class AutoDriveServo extends CommandBase
{
	private boolean servoIn;
	private boolean isServoIn;

	public AutoDriveServo(boolean servoIn)
	{
		requires((Subsystem) shooter);
		this.servoIn = servoIn;
	}

	@Override
	protected void end()
	{
		shooter.driveServo(false);
	}

	@Override
	protected void execute()
	{
		shooter.driveServo(!servoIn);
		isServoIn = servoIn;
	}

	@Override
	protected void initialize()
	{
		shooter.driveServo(false);
	}

	@Override
	protected boolean isFinished()
	{
		return (isServoIn && servoIn) || (!isServoIn && !servoIn);
	}

	@Override
	public String getConsoleIdentity()
	{
		return "Auto_Drive_Servo";
	}

}
