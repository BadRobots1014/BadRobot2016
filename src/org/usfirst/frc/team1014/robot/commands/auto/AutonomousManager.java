package org.usfirst.frc.team1014.robot.commands.auto;

import java.util.HashMap;

import org.usfirst.frc.team1014.robot.commands.BadCommandGroup;
import org.usfirst.frc.team1014.robot.commands.DummyCommandGroup;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.ChevalDeFrise;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.Drawbridge;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.GenericCrossDefense;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.LowBar;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.Portcullis;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.SallyPort;
import org.usfirst.frc.team1014.robot.utilities.Logger;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This {@code CommandGroup} is how we will run our autonomous programs. The constructor takes
 * various variables as inputs and creates its own autonomous program to carry it out.
 * 
 * @author Manu S.
 * 
 */
public class AutonomousManager extends CommandGroup
{
	private static AutonomousManager instance;
	public HashMap<String, BadCommandGroup> autonomousCommands = new HashMap<String, BadCommandGroup>();

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
		registerAutonomousCommand("Generic_Cross", new BadCommandGroup(new AutoDrive(4, .9)));
		registerAutonomousCommand("Shoot_And_Come_Back", new BadCommandGroup(new AutoDriveDistanceUltrasonic(1, 132), new AutoTurn(90.0), new AutoDrive(.5, 1), new AutoTurn(-90.0), new AutoShoot(1.0), new AutoTurn(-90.0), new AutoDrive(.5, 1), new AutoTurn(-90.0), new AutoDrive(2, 1)));
		registerAutonomousCommand("Shoot_And_Stay", new BadCommandGroup(new AutoDriveDistanceUltrasonic(1, 132), new AutoTurn(90.0), new AutoDrive(.5, 1), new AutoTurn(-90.0), new AutoShoot(1.0)));
		registerAutonomousCommand("Lowbar_Stay", new BadCommandGroup(new AutoDriveServo(true), new PreDefinedRotation(true), new AutoDrive(6, .6), new AutoTurn(60.0)));
		registerAutonomousCommand("Lowbar_Shoot", new BadCommandGroup(new AutoDriveServo(true), new PreDefinedRotation(true), new AutoDrive(6, .6), new AutoTurn(60.0), new AutoShoot(3.0)));
		registerAutonomousCommand("Go_Over", new BadCommandGroup(new AutoDriveDistanceUltrasonic(1, 132)));
		registerAutonomousCommand("Go_Over_And_Come_Back", new BadCommandGroup(new AutoDriveDistanceUltrasonic(1, 132), new AutoDrive(2, 1)));
	}

	public void registerAutonomousCommand(String name, BadCommandGroup command)
	{
		autonomousCommands.put(name, command);
	}

	public BadCommandGroup getAutnomouscommand(String name)
	{
		return autonomousCommands.get(name);
	}

	public void setup()
	{

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

		/*
		 * Sets the turn amount based on if the robot is shooting high or low
		 */
		if(goingForLow)
		{
			switch(defensePos)
			{
				case 1:
					turnToGoal = new AutoTurn(new Double(60));
					break;
				case 2:
					turnToGoal = new AutoTurn(new Double(60));
					break;
				case 3:
					turnToGoal = new AutoTurn(new Double(-30));
					break;
				case 4:
					turnToGoal = new AutoTurn(new Double(30));
					break;
				case 5:
					turnToGoal = new AutoTurn(new Double(-60));
					break;
				default:
					turnToGoal = new AutoTurn(new Double(0));
					Logger.log(Logger.Level.Error, "AutoTurn", "Default Triggered");
					break;
			}
		}
		else
		{
			switch(defensePos)
			{
				case 1:
					turnToGoal = new AutoTurn(new Double(60));
					break;
				case 2:
					turnToGoal = new AutoTurn(new Double(60));
					break;
				case 3:
					turnToGoal = new AutoTurn(new Double(22));
					break;
				case 4:
					turnToGoal = new AutoTurn(new Double(-8));
					break;
				case 5:
					turnToGoal = new AutoTurn(new Double(-60));
					break;
				default:
					turnToGoal = new AutoTurn(new Double(0));
					Logger.log(Logger.Level.Error, "AutoTurn", "Default Triggered");
					break;
			}
		}

		/*
		 * Moves the shooter
		 */
		if(isShooting && !goingForLow)
		{
			moveShooter = new PreDefinedRotation(false);
			findTargetCommand = new FindTarget();
			shootBall = new AutoShoot(new Double(3));
		}
		else if(isShooting && goingForLow)
		{
			moveShooter = new DummyCommandGroup();
			findTargetCommand = new DummyCommandGroup();
			shootBall = new AutoShoot(new Double(3));

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
	}

	public void queue()
	{
		this.addSequential(waitTimeCommand);
		this.addSequential(crossDefense);
		this.addSequential(moveToTurnSpot);
		this.addSequential(moveShooter);
		this.addSequential(turnToGoal);
		this.addSequential(findTargetCommand);
		this.addSequential(shootBall);
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