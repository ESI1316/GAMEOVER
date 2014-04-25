package g39631.gameover.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import g39631.gameover.model.*;

/**
 * 
 * This class is the link between players, displays and game's mechanics. 
 * It treats player's input, gives game's instructions and feedbacks.
 * 
 * @version 2.00
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 */
public class GameView {

	private Display 		display;
	private final Scanner 	keyboard = new Scanner(System.in);
	private Game 			newGame;
	private BarbarianState 	state;

	/**
	 * 
	 * This try to launch "GAMEOVER".
	 * First step : set its own Display &amp;. 
	 * Second step : try to create a new Game from player's list.
	 * Third step : launch GAMEOVER.
	 * 
	 * @param args
	 *            Player's file path (first position of the table).
	 */
	private GameView(String[] args) {

		try {

			this.display = new Display();
			this.creation(args);
			this.state = BarbarianState.READY_TO_GO;	
			this.gameOverTheGame(args);
		} catch (GameOverException ex) {

			this.display.shutDown(ex.getMessage());
			System.exit(1);
		}
	}

	/**
	 * 
	 * This launches a new Game with player's file. He calls GameView constructor
	 * to set everything. 
	 * This is only used to run the application.
	 * 
	 * @param args
	 *            Player's file path (first position of the table).
	 *            
	 */
	public static void main(String[] args) {

		new GameView(args);
	}

	/**
	 * 
	 * This reads Player's file fills an array of names &amp; novices status.
	 * 
	 * @param args
	 *            Player's file path (first position of the table).
	 *            
	 * @throws GameOverException
	 * 				If there is any IOException (OPEN-CLOSE-READ-PATH).
	 * 				If there is any problem during Game's creation.
	 * 
	 */
	private void creation(String[] args) throws GameOverException {

		args 			= GameFile.fileToArray(args);
		this.newGame 	= new Game(args);
	}

	/**
	 * 
	 * @param args
	 * 			An array made of player's informations.
	 *  
	 * @throws GameOverException
	 * 			If there is any Display's error because of DungeonPosition 
	 * 			created outside the Dungeon.
	 * 
	 */
	private void gameOverTheGame(String[] args) throws GameOverException {

		while (this.state != BarbarianState.WIN) {

			this.state = BarbarianState.READY_TO_GO;
			this.oneTurn();
			this.nextPlayerTurn();
		}

		this.display.winner(this.newGame.getWinner());
		System.exit(0);
	}

	/**
	 * 
	 * This method plays the player during a round. It can play again if he does
	 * not die.
	 * 
	 * @throws GameOverException
	 *             Thrown by Display.*() : "Out of Dungeon Exception".
	 * 
	 */
	private void oneTurn() throws GameOverException {

		while ((this.state != BarbarianState.GAMEOVER)
				&& (this.state != BarbarianState.WIN)) {

			this.display.dungeonBoard(this.newGame);
			this.display.instructions(this.newGame.getCurrentPlayer());

			this.onePlay();
			this.specialState(); // In case of MOVE_BLORK, BEAM_ME_UP or JOKER.

			this.state = this.newGame.isSurrounded(this.state);
		}
	}

	/**
	 * The Player plays during a round : he chooses his weapon and the path he
	 * wants to go. Using Thread to temporarily display GameOverException's
	 * message.
	 * 
	 */
	private void onePlay() {

		int weapon;
		int direction;

		weapon 		= this.weaponChoose(0);// 0 Is a wrong Weapon and Direction.
		direction 	= this.directionChoose(0);

		try {

			this.state = this.newGame.play(Direction.values()[direction - 1],
					WeaponType.values()[weapon - 1]);

		} catch (GameOverException e) {

			this.display.errorDisplay(e.getMessage());
		}
	}

	/**
	 * 
	 * This method launches a specific treatment for each special case.
	 * 
	 * @throws GameOverException
	 *             Throws by onePlayGate. 
	 *             Throws by onePlayBlorkInvincible.
	 *             Throws by onePlayJoker.
	 * 
	 */
	private void specialState() throws GameOverException {

		if (this.state == BarbarianState.BEAM_ME_UP) {

			this.display.dungeonBoard(this.newGame);
			this.onePlayGate();
		}

		if (this.state == BarbarianState.MOVE_BLORK) {

			this.display.dungeonBoard(this.newGame);
			this.onePlayBlorkInvincible();
		}

		if (this.state == BarbarianState.JOKER) {

			this.display.dungeonBoard(this.newGame);
			this.onePlayJoker();
		}
	}

	/**
	 * 
	 * @param weapon
	 *            A default wrong weapon number <code>0</code>.
	 * 
	 * @return A number corresponding to a printed Weapon.
	 */
	private int weaponChoose(int weapon) {

		while ((weapon < 1) || (weapon > 4)) {

			this.display.weaponChoose();
			try {

				weapon = this.keyboard.nextInt();
			} catch (InputMismatchException ex) {

				this.display.inputMismatchDisplay("weapon");
				this.keyboard.next();
			}
		}

		return weapon;
	}

	/**
	 * 
	 * @param direction
	 *            A default wrong direction number <code>0</code>.
	 * 
	 * @return A number corresponding to a printed Direction.
	 * 
	 */
	private int directionChoose(int direction) {

		while ((direction < 1) || (direction > 4)) {

			this.display.directionChoose();
			try {

				direction = this.keyboard.nextInt();
			} catch (InputMismatchException ex) {

				this.display.inputMismatchDisplay("direction");
				this.keyboard.next();
			}
		}

		return direction;
	}

	/**
	 * 
	 * Call the next player and prepares the Game for him.
	 * 
	 * @throws GameOverException
	 *             Throws by newGame.nextPlayer().
	 * 
	 */
	private void nextPlayerTurn() throws GameOverException {

		if ((this.state != BarbarianState.WIN) && !(this.newGame.isOver())) {

			this.display.dungeonBoard(this.newGame);
			this.display.nextPlayer(); 
			this.newGame.nextPlayer();
		}
	}

	/**
	 * 
	 * Play a turn when you found a GATE.
	 * 
	 */
	private void onePlayGate() {

		DungeonPosition pos = null;

		this.display.gate();

		while (pos == null) {

			try {

				pos = this.jumpChoose(-1, -1);

				this.state = this.newGame.playGate(pos,
						WeaponType.values()[this.weaponChoose(0) - 1]);
			} catch (GameOverException gateE) {

				this.display.errorDisplay(gateE.getMessage());
				pos = null;
			}
		}
	}

	/**
	 * 
	 * Play a turn when you found a INVINCIBLE BLORK.
	 * 
	 */
	private void onePlayBlorkInvincible() {

		DungeonPosition pos = null;

		this.display.invincible();

		while (pos == null) {

			try {

				pos 		= this.jumpChoose(-1, -1);
				this.state 	= this.newGame.playBlorkInvincible(pos);

			} catch (GameOverException blorkE) {

				this.display.errorDisplay(blorkE.getMessage());
				pos = null;
			}
		}
	}

	/**
	 * 
	 * Choose the row.
	 * 
	 * @param row
	 *            A default wrong x-axis position <code>-1</code>.
	 * 
	 * @return Available x-axis value.
	 * 
	 */
	private int rowChoose(int row) {

		while ((row < 0) || (row > 4)) {

			this.display.rowChoose();
			try {

				row = this.keyboard.nextInt();
			} catch (InputMismatchException ex) {

				this.display.inputMismatchDisplay("row");
				this.keyboard.next();
			}
		}

		return row;
	}

	/**
	 * 
	 * Choose the column.
	 * 
	 * @param column
	 *            A default wrong y-axis position <code>-1</code>.
	 * 
	 * @return Available y-axis value.
	 * 
	 */
	private int columnChoose(int column) {

		while ((column < 0) || (column > 4)) {

			this.display.columnChoose();
			try {

				column = this.keyboard.nextInt();
			} catch (InputMismatchException ex) {

				this.display.inputMismatchDisplay("column");
				this.keyboard.next();
			}
		}

		return column;
	}

	/**
	 * 
	 * Choose where you want to be transfer in the Dungeon.
	 * 
	 * @param row
	 *            A default wrong x-axis position <code>-1</code>.
	 * 
	 * @param column
	 *            A default wrong y-axis position <code>-1</code>
	 * 
	 * @return A new DungeonPosition.
	 * 
	 * @throws GameOverException
	 *             If you are out of the Dungeon.
	 * 
	 */
	private DungeonPosition jumpChoose(int row, int column)
		throws GameOverException {

			row 	= this.rowChoose(row);
			column 	= this.columnChoose(column);

			return new DungeonPosition(row, column);
		}

	/**
	 * 
	 * Makes you play again if you lose in front of an armed BLORK.
	 */
	private void onePlayJoker() {

		this.display.joker();
		try {

			this.state = this.newGame
					.playJoker(WeaponType.values()[this.weaponChoose(0) - 1]);

		} catch (GameOverException e) {

			this.display.errorDisplay(e.getMessage());
		}
	}
}
