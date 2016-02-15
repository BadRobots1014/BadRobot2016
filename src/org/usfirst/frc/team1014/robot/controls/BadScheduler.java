package org.usfirst.frc.team1014.robot.controls;

import org.usfirst.frc.team1014.robot.utilities.Logger;
import org.usfirst.frc.team1014.robot.utilities.Logger.Level;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class BadScheduler
{
	private Scheduler scheduler = Scheduler.getInstance();
	private Command nowRunning;
	private Class<? extends Command> mainTeleopClass;

	public BadScheduler(Class<? extends Command> mainTeleopClass)
	{
		this.mainTeleopClass = mainTeleopClass;
	}

	public void initTeleop()
	{

		try
		{
			nowRunning = (Command) mainTeleopClass.newInstance();
			scheduler.add(nowRunning);
		} catch(InstantiationException e)
		{
			System.out.println("instance issue with " + mainTeleopClass.getName());
			e.printStackTrace();
		} catch(IllegalAccessException e)
		{
			System.out.println("illegal access exception 1");
			e.printStackTrace();
		}

	}

	public void changeCommand(boolean button, Command nextCommandInput)
	{
		try
		{
			if(ControlsManager.primaryXboxController.isYButtonPressed())
			{
				if(nowRunning.getName() != mainTeleopClass.getName())
				{
					scheduler.removeAll();
					nowRunning = (Command) mainTeleopClass.newInstance();
					scheduler.add(nowRunning);
				}
			}
			else
			{
				if(button)
				{
					if(nowRunning.getName() != nextCommandInput.getName())
					{
						Logger.log(Level.Debug, "69", "started new command");
						scheduler.removeAll();
						nowRunning = nextCommandInput;
						scheduler.add(nowRunning);
					}
				}
				else
				{
					if(!nowRunning.isRunning())
					{
						Logger.logThis("new Tele");
						scheduler.removeAll();
						nowRunning = (Command) mainTeleopClass.newInstance();
						scheduler.add(nowRunning);
					}
				}
			}

		} catch(InstantiationException e)
		{
			System.out.println("can't instantiate stuffs");
			e.printStackTrace();
		} catch(IllegalAccessException e)
		{
			System.out.println("illegal acces exception 2");
			e.printStackTrace();
		}

	}

}

