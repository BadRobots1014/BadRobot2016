package org.usfirst.frc.team1014.robot.controls;

import java.lang.reflect.InvocationTargetException;

import org.usfirst.frc.team1014.robot.commands.TeleopGroup;
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
	private Command teleopCommandInstance;
	private Class<? extends Command> mainTeleopClass;
	private Command nowRunning;

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
			teleopCommandInstance = (Command) mainTeleopClass.newInstance();
			scheduler.add(teleopCommandInstance);
			nowRunning = teleopCommandInstance;
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

	/**
	 * Resets the instance of the teleop group in the scheduler.
	 * 
	 * @param button
	 *            an override for the Y button press
	 * @param nextCommandInput
	 *            an instance to add to scheduler
	 */
	public void changeCommand(boolean button, Class<? extends Command> nextCommandInput)
	{
		try
		{
			// If Y is pressed clear out scheduler and adds a new instance of the mainTeleopClass
			// after emptying the scheduler
			if(ControlsManager.primaryXboxController.isStartButtonPressedPrimaryLayout())
			{
				resetTeleopCommandToInitial();
			}
			else
			{
				if(button) // Assumes Y is pressed
				{
					if(!nowRunning.getName().equals(nextCommandInput.getName()))
					{
						Command newCommand;
						String newCommandString = nextCommandInput.getName().substring(45);
						switch(newCommandString)
						{
							case "AutoRotate":
								try
								{
									Logger.logThis("rotate");
									newCommand = nextCommandInput.getConstructor(Double.class).newInstance(new Double(ShooterAndGrabber.SHOOTER_DEFAULT_SHOOTING_POS));
									break;
								} catch(IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1)
								{
									e1.printStackTrace();
								}
							case "AutoShoot":
								try
								{
									Logger.logThis("shoot");
									newCommand = nextCommandInput.getConstructor(Double.class).newInstance(new Double(2));
									break;
								} catch(IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1)
								{
									e1.printStackTrace();
								}
							case "AutoTurn":
								try
								{
									Logger.logThis("turn");
									newCommand = nextCommandInput.getConstructor(Double.class).newInstance(new Double(0));
									break;
								} catch(IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1)
								{
									e1.printStackTrace();
								}
							case "AutoSallyPortArm":
								try
								{
									Logger.logThis("sally port");
									newCommand = nextCommandInput.getConstructor(Double.class, Boolean.class).newInstance(new Double(1), new Boolean(true));
									break;
								} catch(IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1)
								{
									e1.printStackTrace();
								}
							default:
								Logger.logThis("defeault");
								newCommand = nextCommandInput.newInstance();
						}
						scheduler.removeAll();
						scheduler.add(newCommand);
						nowRunning = newCommand;
					}
				}
				else
				{
					// If command is no longer running replace it with a new instance
					Logger.logThis("------------------- " + nowRunning.getName());
					Logger.logThis("Things: " + nowRunning.isRunning());
					if(!nowRunning.isRunning())
						resetCommandIfStopped();
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
	private void resetCommandIfStopped() throws InstantiationException, IllegalAccessException
	{
		if(!teleopCommandInstance.isRunning())
		{
			scheduler.removeAll();
			teleopCommandInstance = (Command) mainTeleopClass.newInstance();
			scheduler.add(teleopCommandInstance);
			nowRunning = teleopCommandInstance;
		}
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
	private void resetTeleopCommandToInitial() throws InstantiationException, IllegalAccessException
	{
		if(teleopCommandInstance.getName() != mainTeleopClass.getName())
		{
			scheduler.removeAll();
			teleopCommandInstance = (Command) mainTeleopClass.newInstance();
			scheduler.add(teleopCommandInstance);
			nowRunning = teleopCommandInstance;
		}
	}
}
