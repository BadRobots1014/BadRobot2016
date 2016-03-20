package org.usfirst.frc.team1014.robot.commands.auto;

import org.usfirst.frc.team1014.robot.commands.DummyCommand;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.ChevalDeFrise;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.Drawbridge;
import org.usfirst.frc.team1014.robot.commands.auto.defenses.GenericCrossDefense;
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
public class BadAutonomous extends CommandGroup
{
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

	/**
	 * Creates a simple autonomous that goes through the low bar and doesn't shoot.
	 */
	public BadAutonomous()
	{
		setVariables(false, true, 1, Defense.NONE, 0, true);

	}

	/**
	 * Creates a specialized autonomous based on the parameters fed into it.
	 * 
	 * @param willShoot
	 *            - whether or not the robot will shoot the ball at the end, true for yes, false for
	 *            no
	 * @param lowScore
	 *            - whether or not the robot will score in the low goal, true for yes, false for no
	 * @param crossingDefense
	 *            - the placement of the defense the robot will cross (1, 2, 3, 4 or 5)
	 * @param defense
	 *            - the name of the defense it's crossing (spelled correctly) (e.g. Portcullis,
	 *            Drawbridge, Rough Terrain etc)
	 */
	public BadAutonomous(boolean willShoot, boolean lowScore, int crossingDefense, Defense defense, double waitTime, boolean justCross)
	{
		setVariables(willShoot, lowScore, crossingDefense, defense, waitTime, justCross);
	}

	public void setVariables(boolean willShoot, boolean lowScore, int crossingDefense, Defense defense, double waitTime, boolean justCross)
	{
		this.isShooting = willShoot;
		this.goingForLow = lowScore;
		this.defensePos = crossingDefense;
		this.defense = defense;
		this.waitTime = waitTime;
		this.justCross = justCross;
		setup();
	}

	/**
	 * Creates a specialized autonomous based on the parameters fed into it.
	 * 
	 * @param willShoot
	 *            - whether or not the robot will shoot the ball at the end, true for yes, false for
	 *            no
	 * @param lowScore
	 *            - whether or not the robot will score in the low goal, true for yes, false for no
	 * @param crossingDefense
	 *            - the placement of the defense the robot will cross (1, 2, 3, 4 or 5)
	 * @param defense
	 *            - the name of the defense it's crossing (spelled correctly) (e.g. Portcullis,
	 *            Drawbridge, Rough Terrain etc)
	 * @param waitTime
	 *            - the time to wait before carrying out the autonomous
	 */
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
			moveShooter = new DummyCommand();
			findTargetCommand = new DummyCommand();
			shootBall = new AutoShoot(new Double(3));
			Logger.logThis("Shooting Low");

		}
		else
		{
			Logger.log(Logger.Level.Error, "Move Shooter", "Default Triggered");
			moveShooter = new DummyCommand();
			findTargetCommand = new DummyCommand();
			shootBall = new DummyCommand();
		}

		if(justCross)
		{
			moveShooter = new DummyCommand();
			turnToGoal = new DummyCommand();
			findTargetCommand = new DummyCommand();
			shootBall = new DummyCommand();
		}

		if(defense.equals(Defense.NONE))
		{
			waitTimeCommand = new DummyCommand();
			crossDefense = new DummyCommand();
			moveToTurnSpot = new DummyCommand();
			moveShooter = new DummyCommand();
			turnToGoal = new DummyCommand();
			findTargetCommand = new DummyCommand();
			shootBall = new DummyCommand();
		}

		// adds some of the commands to the Scheduler

		// Logger.logThis("Crossing-----------------" + crossDefense.getName());
		// Logger.logThis("Move To Turn Spot-----------------" + moveToTurnSpot.getName());
		// Logger.logThis("Move Shooter-----------------" + moveShooter.getName());
		// Logger.logThis("Turn to Goal-----------------" + turnToGoal.getName());

		/*
		 * If scoring low, add some more commands to get robot to the right spot
		 */
		// if(goingForLow)
		// {
		// if(defenseToCross == 3 && goingForLow)
		// {
		// this.addSequential(new AutoDriveDistanceEncoder(.5, 3.638));
		// this.addSequential(new AutoTurn(new Double(90)));
		// }
		// else if(defenseToCross == 4 && goingForLow)
		// {
		// this.addSequential(new AutoDriveDistanceEncoder(.5, 4.192));
		// this.addSequential(new AutoTurn(new Double(-90)));
		// }
		// else
		// {
		// }
		// }

		/*
		 * Creates the command to shoot
		 */
		/*
		 * findTargetCommand = new DummyCommand(); if(isShooting && !goingForLow) {
		 * findTargetCommand = new FindTarget(); shootBall = new AutoShoot(new Double(3)); } else
		 * if(isShooting && goingForLow) { shootBall = new AutoShoot(new Double(3)); Logger.logThis(
		 * "Shooting Low"); } else { shootBall = new AutoShoot(new Double(0));
		 * Logger.log(Level.Error, "AutoShoot", "NOT SHOOTING BALL"); }
		 */

		// Logger.logThis("Shoot Ball-----------------" + shootBall.getName());

		// add the final part

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

}
