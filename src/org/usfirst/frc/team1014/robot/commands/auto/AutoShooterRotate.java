package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.CommandBase;
import org.usfirst.frc.team1014.robot.controls.ControlsManager;

import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoShooterRotate extends CommandBase
{

	public AutoShooterRotate()
	{
		requires((Subsystem) shooter);
	}

	@Override
	protected void initialize()
	{

	}

	@Override
	public String getConsoleIdentity()
	{
		return null;
	}

	@Override
	protected void end()
	{

	}

	@Override
	protected void execute()
	{
		if(ControlsManager.getLayout(2) == 1)
		{
			// b button is pressed, should rotate arm straight up
			if(ControlsManager.secondaryXboxController.isBButtonPressed())
			{
				// arm is currently too low
				if(shooter.getArticulatorPosition() > 3)
				{
					// rotate up
					shooter.rotate(-0.15);
					// arm is currently too high
				}
				else if(shooter.getArticulatorPosition() < -3)
				{
					// rotate down
					shooter.rotate(0.15);
				}
			}
			// y button is pressed, should rotate arm to ~30 degrees
			else if(ControlsManager.secondaryXboxController.isYButtonPressed())
			{
				// arm is currently too low
				if(shooter.getArticulatorPosition() > (33 / 360))
				{
					// rotate up
					shooter.rotate(-0.15);
				}
				// arm is currently too high
				else if(shooter.getArticulatorPosition() < (27 / 360))
				{
					// rotate down
					shooter.rotate(0.15);
				}
			}
			// x button is pressed, should rotate arm to 75 degrees
			else if(ControlsManager.secondaryXboxController.isXButtonPressed())
			{
				// arm is currently too low
				if(shooter.getArticulatorPosition() > (78 / 360))
				{
					// rotate up
					shooter.rotate(-0.15);
				}
				// arm is currently too high
				else if(shooter.getArticulatorPosition() < (72 / 360))
				{
					// rorate down
					shooter.rotate(0.15);
				}
			}
		}
	}

	@Override
	protected void interrupted()
	{

	}

	@Override
	protected boolean isFinished()
	{
		return false;
	}

}
