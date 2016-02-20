package org.usfirst.frc.team1014.robot.controls;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * A class that adds the {@link TeleopGroup} to the {@link Scheduler}.
 */
public class BadScheduler
{
	private Scheduler scheduler = Scheduler.getInstance();
	private Command teleopCommandInstance;
	private Class<? extends Command> mainTeleopClass;

	/**
	 * Sets the {@code mainTeleopClass} to a Java {@link Class} object
	 * so when {@code initTeleop()} is called an instance is created and
	 * added to the scheduler.
	 * @param mainTeleopClass the class object of the teleop command
	 */
	public BadScheduler(Class<? extends Command> mainTeleopClass)
	{
		this.mainTeleopClass = mainTeleopClass;
	}

	/**
	 * Creates a new instance from the {@code mainTeleopClass} and
	 * adds the instance to the scheduler.
	 */
	public void initTeleop()
	{
		try
		{
			teleopCommandInstance = (Command) mainTeleopClass.newInstance();
			scheduler.add(teleopCommandInstance);
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
	 * @param button an override for the Y button press
	 * @param nextCommandInput an instance to add to scheduler
	 */
	public void changeCommand(boolean button, Command nextCommandInput)
	{
		// This method should not exist
		
		try
		{
			// If Y is pressed clear out scheduler and adds a new instance of the mainTeleopClass
			// after emptying the scheduler
			if(ControlsManager.primaryXboxController.isYButtonPressed())
				resetTeleopCommandToInitial();
			else //If Y button not pressed
				if(button) // Assumes Y is pressed
					resetTeleopCommandToInitial();
				else
					// If command is no longer running replace it with a new instance
					resetCommandIfStopped();
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
	 * If {@code teleopCommandInstance.isRunning()} returns false the scheduler
	 * is cleared and a new instance.
	 * @throws InstantiationException if the class can't be created
	 * @throws IllegalAccessException if the class constructor can't be accessed
	 */
	private void resetCommandIfStopped() throws InstantiationException, IllegalAccessException {
		if(!teleopCommandInstance.isRunning())
		{
			scheduler.removeAll();
			teleopCommandInstance = (Command) mainTeleopClass.newInstance();
			scheduler.add(teleopCommandInstance);
		}
	}

	/**
	 * If the name of the {@code teleopCommandInstance} is not the same as
	 * the name of {@code mainTeleopClass} it will empty the scheduler and
	 * add a new instance of the {@code mainTeleopClass}.
	 * @throws InstantiationException if the class can't be created
	 * @throws IllegalAccessException if the class constructor can't be accessed
	 */
	private void resetTeleopCommandToInitial() throws InstantiationException, IllegalAccessException {
		if(teleopCommandInstance.getName() != mainTeleopClass.getName())
		{
			scheduler.removeAll();
			teleopCommandInstance = (Command) mainTeleopClass.newInstance();
			scheduler.add(teleopCommandInstance);
		}
	}
}
