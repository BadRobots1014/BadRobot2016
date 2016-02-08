package org.usfirst.frc.team1014.control;
import org.usfirst.frc.team1014.robot.OI;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class BadScheduler
{
	private Scheduler scheduler = Scheduler.getInstance();
	private String mainTeleop = "TeleopGroup";
	private String commandsPackageName = "org.usfirst.frc.team1014.robot.commands.";	
	private Command nowRunning;
	private Class<?> mainTeleopClass;
	
	public BadScheduler()
	{
		try
		{
			mainTeleopClass = Class.forName(commandsPackageName+mainTeleop);
		}
		catch(ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initTeleop()
	{
			try
			{
				nowRunning = (Command) mainTeleopClass.newInstance();
				scheduler.add(nowRunning);
			} catch(InstantiationException | IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	public void changeCommand(boolean button, Command nextCommandInput)
	{
		try
		{
			if(OI.priXboxController.isYButtonPressed())
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
		catch(InstantiationException | IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
}
