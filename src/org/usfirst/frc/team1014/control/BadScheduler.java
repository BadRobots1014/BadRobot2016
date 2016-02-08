package org.usfirst.frc.team1014.control;

import org.usfirst.frc.team1014.robot.controls.ControlsManager;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class BadScheduler
{
	private Scheduler scheduler = Scheduler.getInstance();
	private String mainTeleop;
	private String commandsPackageName = "org.usfirst.frc.team1014.robot.commands.";	
	private Command nowRunning;
	private Class<?> mainTeleopClass;
	
	public BadScheduler(String mainTeleop)
	{
		try
		{
			this.mainTeleop = mainTeleop;
			mainTeleopClass = Class.forName(commandsPackageName+this.mainTeleop);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("class no found issue");
			e.printStackTrace();
		}
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
						scheduler.removeAll();
						nowRunning = nextCommandInput;
						scheduler.add(nowRunning);
					}
				}
				else
				{
					if(!nowRunning.isRunning())
					{
						scheduler.removeAll();
						nowRunning = (Command) mainTeleopClass.newInstance();
						scheduler.add(nowRunning);
					}
				}
			}
		}
		catch(InstantiationException e)
		{
			System.out.println("can't instantiate stuffs");
			e.printStackTrace();
		}
		catch(IllegalAccessException e)
		{
			System.out.println("illegal acces exception 2");
			e.printStackTrace();
		}

	}
	
	
	
}
