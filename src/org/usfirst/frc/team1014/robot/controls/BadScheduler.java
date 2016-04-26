package org.usfirst.frc.team1014.robot.controls;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.usfirst.frc.team1014.robot.commands.TeleopGroup;
import org.usfirst.frc.team1014.robot.commands.auto.AutoShoot;
import org.usfirst.frc.team1014.robot.commands.auto.AutoTurn;
import org.usfirst.frc.team1014.robot.subsystems.ShooterAndGrabber;
import org.usfirst.frc.team1014.robot.utilities.Logger;
import org.usfirst.frc.team1014.robot.utilities.Logger.Level;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * A class that adds the {@link TeleopGroup} to the {@link Scheduler}.
 * 
 * @author Subash C. & Manu S.
 */
public class BadScheduler
{
	private Scheduler scheduler = Scheduler.getInstance();
	private Class<? extends Command> mainTeleopClass;
	private Command nowRunning;
	private HashMap<Class<? extends Command>, Method> autos = new HashMap<Class<? extends Command>, Method>();

	interface Method
	{

		boolean runMethod();
	}

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
			Logger.log(Level.Error, "TeleopInit", "instance issue with " + mainTeleopClass.getName());
			e.printStackTrace();
		} catch(IllegalAccessException e)
		{
			Logger.log(Level.Error, "TeleopInit", "illegal access exception 1");
			e.printStackTrace();
		}
		autos.put(AutoShoot.class, new Method()
		{
			@Override
			public boolean runMethod()
			{
				return ControlsManager.shooter.getButtonValue(2, ControllerLayout.autoShoot);
			};
		});

		autos.put(AutoTurn.class, new Method()
		{

			@Override
			public boolean runMethod()
			{
				return ControlsManager.shooter.getButtonValue(2, ControllerLayout.adjustBackward);
			};

		});
	}

	/**
	 * Resets the instance of the teleop group in the scheduler.
	 * 
	 * @param button
	 *            an override for the Y button press
	 * @param nextCommandInput
	 *            an instance to add to scheduler
	 */
	public void changeCommand()
	{
		try
		{
			// If Y is pressed clear out scheduler and adds a new instance of the mainTeleopClass
			// after emptying the scheduler
			if(ControlsManager.driver.getButtonValue(2, ControllerLayout.ringLight))
			{
				resetTeleopCommand();
			}
			else
			{
				Class<? extends Command> commandClass = null;
				for(Class<? extends Command> com : autos.keySet())
				{
					if(autos.get(com).runMethod())
					{
						commandClass = com;
						break;
					}
				}

				if(commandClass != null) // Assumes Y is pressed
				{
					String newCommandString = commandClass.getName().substring(45);
					if(!nowRunning.getName().equals(newCommandString))
					{
						try
						{

							switch(newCommandString)
							{
								case "AutoRotate":
									nowRunning = commandClass.getConstructor(Double.class).newInstance(new Double(ShooterAndGrabber.SHOOTER_DEFAULT_SHOOTING_POS));
									break;
								case "AutoShoot":
									nowRunning = commandClass.getConstructor(Double.class).newInstance(new Double(5));
									break;
								case "AutoTurn":
									nowRunning = commandClass.getConstructor(Double.class).newInstance(new Double(90));
									break;
								case "AutoSallyPortArm":
									nowRunning = commandClass.getConstructor(Double.class, Boolean.class).newInstance(new Double(1), new Boolean(true));
									break;
								default:
									nowRunning = commandClass.newInstance();
							}
						} catch(IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1)
						{
							e1.printStackTrace();
						}
						scheduler.removeAll();
						scheduler.add(nowRunning);
					}
				}
				else
				{
					// If command is no longer running replace it with a new instance

					Logger.log(Level.Debug, "Running", nowRunning.getName());
					Logger.log(Level.Debug, "Things", "" + nowRunning.isRunning());
					if(!nowRunning.isRunning())
						resetTeleopCommand();
				}
			}

		} catch(InstantiationException e)
		{
			Logger.log(Level.Error, "Change_Command", "Can't instantiate stuffs");
			e.printStackTrace();
		} catch(IllegalAccessException e)
		{
			Logger.log(Level.Error, "Change_Command", "Illegal acces exception 2");
			e.printStackTrace();
		}
	}

	private void resetTeleopCommand() throws InstantiationException, IllegalAccessException
	{
		scheduler.removeAll();
		nowRunning = mainTeleopClass.newInstance();
		scheduler.add(nowRunning);
		Logger.logOnce("TeleOp has Been Reset!");
	}
}
