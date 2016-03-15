package org.usfirst.frc.team1014.robot.controls;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.usfirst.frc.team1014.robot.commands.TeleopGroup;
import org.usfirst.frc.team1014.robot.commands.auto.AutoShoot;
import org.usfirst.frc.team1014.robot.commands.auto.AutoTurn;
import org.usfirst.frc.team1014.robot.subsystems.ShooterAndGrabber;
import org.usfirst.frc.team1014.robot.utilities.Logger;

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

	/**
	 * Sets the {@code mainTeleopClass} to a Java {@link Class} object so when {@code initTeleop()}
	 * is called an instance is created and added to the scheduler.
	 * 
	 * @param mainTeleopClass
	 *            the class object of the teleop command
	 */
	public BadScheduler(Class<? extends Command> mainTeleopClass)
	{
		this.mainTeleopClass = mainTeleopClass;
	}

	/**
	 * Creates a new instance from the {@code mainTeleopClass} and adds the instance to the
	 * scheduler.
	 */
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
		autos.put(AutoShoot.class, new Method()
		{
			@Override
			public boolean runMethod()
			{
				return ControlsManager.secondaryXboxController.isAButtonPressedSecondaryLayout();
			};
		});

		autos.put(AutoTurn.class, new Method()
		{

			@Override
			public boolean runMethod()
			{
				return ControlsManager.secondaryXboxController.isYButtonPressedSecondaryLayout();
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
			if(ControlsManager.primaryXboxController.isStartButtonPressedPrimaryLayout())
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
						Logger.logThis("nowRunning name  = " + nowRunning.getName());
						Logger.logThis("nextCommandInput  = " + newCommandString);
						switch(newCommandString)
						{
							case "AutoRotate":
								try
								{
									Logger.logThis("rotate");
									nowRunning = commandClass.getConstructor(Double.class).newInstance(new Double(ShooterAndGrabber.SHOOTER_DEFAULT_SHOOTING_POS));
								} catch(IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1)
								{
									e1.printStackTrace();
								}
								break;
							case "AutoShoot":
								try
								{
									Logger.logThis("shoot");
									nowRunning = commandClass.getConstructor(Double.class).newInstance(new Double(5));
								} catch(IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1)
								{
									e1.printStackTrace();
								}
								break;
							case "AutoTurn":
								try
								{
									Logger.logThis("turn");
									nowRunning = commandClass.getConstructor(Double.class).newInstance(new Double(90));
								} catch(IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1)
								{
									e1.printStackTrace();
								}
								break;
							case "AutoSallyPortArm":
								try
								{
									Logger.logThis("sally port");
									nowRunning = commandClass.getConstructor(Double.class, Boolean.class).newInstance(new Double(1), new Boolean(true));
								} catch(IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1)
								{
									e1.printStackTrace();
								}
								break;
							default:
								Logger.logThis("default");
								nowRunning = commandClass.newInstance();
						}
						scheduler.removeAll();
						scheduler.add(nowRunning);
					}
				}
				else
				{
					// If command is no longer running replace it with a new instance
					Logger.logThis("------------------- " + nowRunning.getName());
					Logger.logThis("Things: " + nowRunning.isRunning());
					if(!nowRunning.isRunning())
						resetTeleopCommand();
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

	/**
	 * If {@code teleopCommandInstance.isRunning()} returns false the scheduler is cleared and a new
	 * instance.
	 * 
	 * @throws InstantiationException
	 *             if the class can't be created
	 * @throws IllegalAccessException
	 *             if the class constructor can't be accessed
	 */
	/*
	 * private void resetCommandIfStopped() throws InstantiationException, IllegalAccessException {
	 * if(!teleopCommandInstance.isRunning()) { scheduler.removeAll(); teleopCommandInstance =
	 * (Command) mainTeleopClass.newInstance(); scheduler.add(teleopCommandInstance); nowRunning =
	 * teleopCommandInstance; } }
	 */

	private void resetTeleopCommand() throws InstantiationException, IllegalAccessException
	{
		scheduler.removeAll();
		nowRunning = mainTeleopClass.newInstance();
		scheduler.add(nowRunning);
		Logger.logThis("TeleOp has Been Reset!");
	}

	/**
	 * If the name of the {@code teleopCommandInstance} is not the same as the name of
	 * {@code mainTeleopClass} it will empty the scheduler and add a new instance of the
	 * {@code mainTeleopClass}.
	 * 
	 * @throws InstantiationException
	 *             if the class can't be created
	 * @throws IllegalAccessException
	 *             if the class constructor can't be accessed
	 */
	/*
	 * private void resetTeleopCommandToInitial() throws InstantiationException,
	 * IllegalAccessException { if(teleopCommandInstance.getName() != mainTeleopClass.getName()) {
	 * scheduler.removeAll(); teleopCommandInstance = (Command) mainTeleopClass.newInstance();
	 * scheduler.add(teleopCommandInstance); nowRunning = teleopCommandInstance; } }
	 */
}
