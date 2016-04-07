package org.usfirst.frc.team1014.robot.commands.auto;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1014.robot.commands.BadCommandGroup;
import org.usfirst.frc.team1014.robot.commands.DummyCommand;
import org.usfirst.frc.team1014.robot.commands.DummyCommandGroup;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.ChevalDeFrise;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.Drawbridge;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.GenericCrossDefense;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.LowBar;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.Portcullis;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.SallyPort;
import org.usfirst.frc.team1014.robot.commands.auto.groups.LowBarShoot;
import org.usfirst.frc.team1014.robot.commands.auto.groups.LowBarStay;
import org.usfirst.frc.team1014.robot.commands.auto.groups.SpyBotShootHigh;
import org.usfirst.frc.team1014.robot.commands.auto.groups.SpyBotShootHighAndMove;
import org.usfirst.frc.team1014.robot.controls.ControlsManager;
import org.usfirst.frc.team1014.robot.utilities.CustomEntry;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This {@code CommandGroup} is how we will run our autonomous programs. The constructor takes
 * various variables as inputs and creates its own autonomous program to carry it out.
 * 
 * @author Manu S.
 * @edit Ryan T. and Subash C.
 */
public class AutonomousManager
{
	private static AutonomousManager instance;
	private List<CustomEntry<String, Command>> autonomousCommands = new ArrayList<CustomEntry<String, Command>>();
	public static final CustomEntry<String, Command> DEFAULT_AUTO = new CustomEntry<String, Command>("ReachDefense", new AutoDrive(.5, .5));
	public ArrayList<Command> commandsToAdd = new ArrayList<Command>();

	public boolean isShooting = false;
	public boolean goingForLow = true;
	public int defensePos = 1;
	private Defense defense = Defense.NONE;
	private double waitTime = 0;
	private boolean justCross = true;

	public Command waitTimeCommand;
	public Command crossDefense;
	public Command moveToTurnSpot;
	public Command moveShooter;
	public Command turnToGoal;
	public Command shootBall;
	public Command findTargetCommand;
	private static DigitalInput A1 = new DigitalInput(ControlsManager.A1SWITCH);
	private static DigitalInput A2 = new DigitalInput(ControlsManager.A2SWITCH);
	private static DigitalInput A3 = new DigitalInput(ControlsManager.A3SWITCH);

	public static byte pollSwitches()
	{
		byte autoToRun = 0;
		if(!A1.get())
			autoToRun += 1;
		if(!A2.get())
			autoToRun += 2;
		if(!A3.get())
			autoToRun += 4;
		return autoToRun;
	}

	public enum Defense
	{
		PORTCULLIS("Portcullis"), SALLYPORT("SallyPort"), DRAWBRIDGE("Drawbridge"), CHEVALDEFRISE("ChevalDeFrise"), LOWBAR("LowBar"), GENERIC("Generic"), NONE("None");

		private String name;

		Defense(String name)
		{
			this.name = name;
		}

		public String getDisplayString()
		{
			return name;
		}
	}

	public void loadAutonoumsCommands()
	{
		// 0
		this.registerAutonomousCommand("GenericCross", new AutoDrive(2, .9));
		// 1
		this.registerAutonomousCommand("LowBarStay", new LowBarStay());
		// 2
		this.registerAutonomousCommand("LowBarShoot", new LowBarShoot());
		// 3
		this.registerAutonomousCommand("SpyBotShootHigh", new SpyBotShootHigh());
		// 4
		this.registerAutonomousCommand("SpyBotShootHighAndMove", new SpyBotShootHighAndMove());
		// 5
		this.registerAutonomousCommand("ReachDefense", new AutoDrive(.5, .5));
	}

	public void registerAutonomousCommand(String name, Command command)
	{
		autonomousCommands.add(new CustomEntry<String, Command>(name, command));
	}

	public Command getAutonomousCommand(String name)
	{
		for(CustomEntry<String, Command> entry : autonomousCommands)
			if(entry.getKey().equals(name))
				return entry.getValue();
		return DEFAULT_AUTO.getValue();
	}

	public CustomEntry<String, Command> getAutonomousCommand(int id)
	{
		if(id >= this.autonomousCommands.size() || id < 0)
			return DEFAULT_AUTO;
		return this.autonomousCommands.get(id);
	}

	public void setup()
	{
		Command waitTimeCommand;
		BadCommandGroup crossDefense;
		Command moveToTurnSpot;
		Command moveShooter;
		Command turnToGoal;
		Command shootBall;
		Command findTargetCommand = new DummyCommand();

		/*
		 * Picks the defense that the robot will be crossing
		 */

		waitTimeCommand = new WaitTime(waitTime);

		switch(defense)
		{
			case PORTCULLIS:
				crossDefense = new Portcullis();
				break;
			case SALLYPORT:
				crossDefense = new SallyPort();
				break;
			case DRAWBRIDGE:
				crossDefense = new Drawbridge();
				break;
			case CHEVALDEFRISE:
				crossDefense = new ChevalDeFrise();
				break;
			case LOWBAR:
				crossDefense = new LowBar();
				break;
			default:
				crossDefense = new GenericCrossDefense();
				Logger.log(Logger.Level.Error, "Cross Defense", "Default Triggered");
				break;
		}

		/*
		 * Make sure people aren't stupid since low bar is always in the first position
		 */
		if(defensePos == 1)
			crossDefense = new LowBar();

		commandsToAdd.add(crossDefense);

		/*
		 * Makes the robot move the turn spot if it isn't already there
		 */
		switch(defensePos)
		{
			case 2:
				moveToTurnSpot = new AutoDriveDistanceEncoder(.5, 3.046);
				break;
			case 5:
				moveToTurnSpot = new AutoDriveDistanceEncoder(.5, 3.690);
				break;
			default:
				moveToTurnSpot = new AutoDriveDistanceEncoder(.5, 0);
				Logger.log(Logger.Level.Error, "Move to Turn", "Default Triggered");
				break;
		}

		commandsToAdd.add(moveToTurnSpot);

		/*
		 * Sets the turn amount based on if the robot is shooting high or low
		 */
		if(goingForLow)
		{
			switch(defensePos)
			{
				case 1:
					turnToGoal = new AutoTurn(60.0);
					break;
				case 2:
					turnToGoal = new AutoTurn(60.0);
					break;
				case 3:
					turnToGoal = new AutoTurn(-30.0);
					break;
				case 4:
					turnToGoal = new AutoTurn(30.0);
					break;
				case 5:
					turnToGoal = new AutoTurn(-60.0);
					break;
				default:
					turnToGoal = new AutoTurn(0.0);
					Logger.log(Logger.Level.Error, "AutoTurn", "Default Triggered");
					break;
			}
		}
		else
		{
			switch(defensePos)
			{
				case 1:
					turnToGoal = new AutoTurn(60.0);
					break;
				case 2:
					turnToGoal = new AutoTurn(60.0);
					break;
				case 3:
					turnToGoal = new AutoTurn(22.0);
					break;
				case 4:
					turnToGoal = new AutoTurn(-8.0);
					break;
				case 5:
					turnToGoal = new AutoTurn(-60.0);
					break;
				default:
					turnToGoal = new AutoTurn(0.0);
					Logger.log(Logger.Level.Error, "AutoTurn", "Default Triggered");
					break;
			}
		}

		commandsToAdd.add(turnToGoal);

		/*
		 * Moves the shooter
		 */
		if(isShooting && !goingForLow)
		{
			moveShooter = new PreDefinedRotation(false);

			commandsToAdd.add(new PreDefinedRotation(false));
			commandsToAdd.add(new FindTarget());
			commandsToAdd.add(new AutoShoot(3.0));
			shootBall = new AutoShoot(3.0);
		}
		else if(isShooting && goingForLow)
		{
			moveShooter = new DummyCommandGroup();
			findTargetCommand = new DummyCommandGroup();
			shootBall = new AutoShoot(3.0);

			commandsToAdd.add(new AutoShoot(3.0));

		}
		else
		{
			Logger.log(Logger.Level.Error, "Move Shooter", "Default Triggered");
			moveShooter = new DummyCommandGroup();
			findTargetCommand = new DummyCommandGroup();
			shootBall = new DummyCommandGroup();
		}

		if(justCross)
		{
			moveShooter = new DummyCommandGroup();
			turnToGoal = new DummyCommandGroup();
			findTargetCommand = new DummyCommandGroup();
			shootBall = new DummyCommandGroup();
		}

		if(defense.equals(Defense.NONE))
		{
			waitTimeCommand = new DummyCommandGroup();
			crossDefense = new DummyCommandGroup();
			moveToTurnSpot = new DummyCommandGroup();
			moveShooter = new DummyCommandGroup();
			turnToGoal = new DummyCommandGroup();
			findTargetCommand = new DummyCommandGroup();
			shootBall = new DummyCommandGroup();
		}

		crossDefense.build();
		this.registerAutonomousCommand("Custom_Defense_Cross", new BadCommandGroup(waitTimeCommand, crossDefense, moveToTurnSpot, moveShooter, turnToGoal, findTargetCommand, shootBall));
	}

	public boolean isShooting()
	{
		return isShooting;
	}

	public void setShooting(boolean isShooting)
	{
		this.isShooting = isShooting;
	}

	public boolean isGoingForLow()
	{
		return goingForLow;
	}

	public void setGoingForLow(boolean goingForLow)
	{
		this.goingForLow = goingForLow;
	}

	public int getDefensePos()
	{
		return defensePos;
	}

	public void setDefensePos(int defensePos)
	{
		this.defensePos = defensePos;
	}

	public Defense getDefense()
	{
		return defense;
	}

	public void setDefense(Defense defense)
	{
		this.defense = defense;
	}

	public double getWaitTime()
	{
		return waitTime;
	}

	public void setWaitTime(double waitTime)
	{
		this.waitTime = waitTime;
	}

	public boolean isJustCross()
	{
		return justCross;
	}

	public void setJustCross(boolean justCross)
	{
		this.justCross = justCross;
	}

	public static AutonomousManager getInstance()
	{
		return instance == null ? (instance = new AutonomousManager()) : instance;
	}
}