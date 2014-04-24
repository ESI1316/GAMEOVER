package g39631.gameover.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import g39631.gameover.model.*;

/**
 * GameView class
 * 
 * @version 2.00
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 */
public class GameView {

	private Display display;
	private final Scanner keyboard = new Scanner(System.in);
	private Game newGame;
	private BarbarianState state;
	
	/**
	 * 
	 * @param args
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
	 * @param args
	 *            Player's file path (args[0]).
	 */
	public static void main(String[] args) {

		new GameView(args);
	}
	
	/**
	 * 
	 * @param args
	 * @throws GameOverException
	 */
	private void creation(String[] args) throws GameOverException {

			args = GameFile.fileToArray(args);
			this.newGame = new Game(args);
	}
	
	/**
	 * 
	 * @param args
	 * @throws GameOverException
	 */
	private void gameOverTheGame(String[] args) throws GameOverException {
		
			while (this.state != BarbarianState.WIN) {

				this.state = BarbarianState.READY_TO_GO;
//				this.state = this.oneTurn(this.state);
				this.oneTurn();
//				this.nextPlayerTurn(this.state);
				this.nextPlayerTurn();
			}

			this.display.winner(this.newGame);
			System.exit(0);
	}
	
	/**
	 * 
	 * This method plays the player during a round. It can play again if he does
	 * not die.
	 * 
	 * @param newGame
	 *            The current Game.
	 * 
	 * @param state
	 *            The current Player's state.
	 * 
	 * @return The new Player's state after one turn.
	 * 
	 * @throws GameOverException
	 *             Thrown by Display.*() : "Out of Dungeon Exception".
	 * 
	 */
	private void oneTurn() throws GameOverException {

		while ((this.state != BarbarianState.GAMEOVER)
				&& (this.state != BarbarianState.WIN)) {

			this.display.dungeonBoard(this.newGame);
			this.display.playerPlay(this.newGame); // Instructions.

//			this.state = this.onePlay(state); // Play one time.
			this.onePlay(); // Play one time.
//			this.state = this.specialState(state); // In case of MOVE_BLORK,
			this.specialState(); // In case of MOVE_BLORK,
			// BEAM_ME_UP or JOKER.

			this.state = this.newGame.isSurrounded(state);
		}
	}
	
//	/**
//	 * 
//	 * This method plays the player during a round. It can play again if he does
//	 * not die.
//	 * 
//	 * @param newGame
//	 *            The current Game.
//	 * 
//	 * @param state
//	 *            The current Player's state.
//	 * 
//	 * @return The new Player's state after one turn.
//	 * 
//	 * @throws GameOverException
//	 *             Thrown by Display.*() : "Out of Dungeon Exception".
//	 * 
//	 */
//	private BarbarianState oneTurn(BarbarianState state)
//			throws GameOverException {
//
//		while ((state != BarbarianState.GAMEOVER)
//				&& (state != BarbarianState.WIN)) {
//
//			this.display.dungeonBoard(this.newGame);
//			this.display.playerPlay(this.newGame); // Instructions.
//
//			state = this.onePlay(state); // Play one time.
//			state = this.specialState(state); // In case of MOVE_BLORK,
//			// BEAM_ME_UP or JOKER.
//
//			state = this.newGame.isSurrounded(state);
//		}
//
//		return state;
//	}

	/**
	 * The Player plays during a round : he chooses his weapon and the path he
	 * wants to go. Using Thread to temporarily display GameOverException's
	 * message.
	 * 
	 * @param newGame
	 *            The current Game to launch a player's movement.
	 * 
	 * @param state
	 *            The current Player's state.
	 * 
	 * @return If he can play again.
	 * 
	 */
	private void onePlay() {

		int weapon;
		int direction;

		weapon = this.weaponChoose(0); // 0 Is a wrong Weapon and Direction.
		direction = this.directionChoose(0);

		try {

			// Throw "visited Room" & "out of Dungeon" Exceptions.
			this.state = this.newGame.play(Direction.values()[direction - 1],
					WeaponType.values()[weapon - 1]);

		} catch (GameOverException e) {

			this.display.errorDisplay(e.getMessage());
			this.display.errorTimer((long) (2000));
		}
	}
	
//	/**
//	 * The Player plays during a round : he chooses his weapon and the path he
//	 * wants to go. Using Thread to temporarily display GameOverException's
//	 * message.
//	 * 
//	 * @param newGame
//	 *            The current Game to launch a player's movement.
//	 * 
//	 * @param state
//	 *            The current Player's state.
//	 * 
//	 * @return If he can play again.
//	 * 
//	 */
//	private BarbarianState onePlay(BarbarianState state) {
//
//		int weapon;
//		int direction;
//
//		weapon = this.weaponChoose(0); // 0 Is a wrong Weapon and Direction.
//		direction = this.directionChoose(0);
//
//		try {
//
//			// Throw "visited Room" & "out of Dungeon" Exceptions.
//			state = this.newGame.play(Direction.values()[direction - 1],
//					WeaponType.values()[weapon - 1]);
//
//		} catch (GameOverException e) {
//
//			this.display.errorDisplay(e.getMessage());
//			this.display.errorTimer((long) (2000));
//		}
//
//		return state;
//	}

	/**
	 * 
	 * This method launches a specific treatment for each special case.
	 * 
	 * @param newGame
	 *            The current Game
	 * 
	 * @param state
	 *            The current Player's state.
	 * 
	 * @return <code>CONTINUE</code> If it is already your current state. If you
	 *         use GATE and win. If you use your JOKER and win.
	 *         <code>GAMEOVER</code> If it is already your current state. If you
	 *         use a GATE and loose. If you use you JOKER and loose. If you
	 *         current state is MOVE_BLORK.
	 * 
	 * 
	 * @throws GameOverException
	 *             Throws by onePlayGate. Throws by onePlayBlorkInvincible.
	 *             Throws by onePlayJoker.
	 * 
	 */
	private void specialState() throws GameOverException {

		if (this.state == BarbarianState.BEAM_ME_UP) {

			this.display.dungeonBoard(this.newGame);
//			this.state = this.onePlayGate(state);
			this.onePlayGate();
		}

		if (this.state == BarbarianState.MOVE_BLORK) {

			this.display.dungeonBoard(this.newGame);
//			this.state = this.onePlayBlorkInvincible(state);
			this.onePlayBlorkInvincible();
		}

		if (this.state == BarbarianState.JOKER) {

			this.display.dungeonBoard(this.newGame);
//			this.state = this.onePlayJoker(state);
			this.onePlayJoker();
		}
	}
	
//	/**
//	 * 
//	 * This method launches a specific treatment for each special case.
//	 * 
//	 * @param newGame
//	 *            The current Game
//	 * 
//	 * @param state
//	 *            The current Player's state.
//	 * 
//	 * @return <code>CONTINUE</code> If it is already your current state. If you
//	 *         use GATE and win. If you use your JOKER and win.
//	 *         <code>GAMEOVER</code> If it is already your current state. If you
//	 *         use a GATE and loose. If you use you JOKER and loose. If you
//	 *         current state is MOVE_BLORK.
//	 * 
//	 * 
//	 * @throws GameOverException
//	 *             Throws by onePlayGate. Throws by onePlayBlorkInvincible.
//	 *             Throws by onePlayJoker.
//	 * 
//	 */
//	private BarbarianState specialState(BarbarianState state) 
//			throws GameOverException {
//
//		if (state == BarbarianState.BEAM_ME_UP) {
//
//			this.display.dungeonBoard(this.newGame);
//			state = this.onePlayGate(state);
//		}
//
//		if (state == BarbarianState.MOVE_BLORK) {
//
//			this.display.dungeonBoard(this.newGame);
//			state = this.onePlayBlorkInvincible(state);
//		}
//
//		if (state == BarbarianState.JOKER) {
//
//			this.display.dungeonBoard(this.newGame);
//			state = this.onePlayJoker(state);
//		}
//
//		return state;
//	}

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
	 * @param newGame
	 *            The current Game
	 * 
	 * @param state
	 *            The current Player's state.
	 * 
	 * @throws GameOverException
	 *             Throws by newGame.nextPlayer().
	 * 
	 */
	private void nextPlayerTurn() throws GameOverException {

		if ((this.state != BarbarianState.WIN) && !(this.newGame.isOver())) {

			this.display.dungeonBoard(this.newGame);
			this.display.nextPlayer(); 

			this.newGame.nextPlayer(); // Apply changes for the next player.
		}
	}
	
//	/**
//	 * 
//	 * Call the next player and prepares the Game for him.
//	 * 
//	 * @param newGame
//	 *            The current Game
//	 * 
//	 * @param state
//	 *            The current Player's state.
//	 * 
//	 * @throws GameOverException
//	 *             Throws by newGame.nextPlayer().
//	 * 
//	 */
//	private void nextPlayerTurn(BarbarianState state)
//			throws GameOverException {
//
//		if ((state != BarbarianState.WIN) && !(this.newGame.isOver())) {
//
//			this.display.dungeonBoard(this.newGame);
//			this.display.nextPlayer(); 
//
//			this.newGame.nextPlayer(); // Apply changes for the next player.
//		}
//	}

	/**
	 * 
	 * Play a turn when you found a GATE.
	 * 
	 * @param newGame
	 *            The current Game.
	 * 
	 * @param state
	 *            The current Barbarian's state.
	 * 
	 * @return The new Barbarian's state <code>WIN</code> <code>GAMEOVER</code>
	 *         <code>CONTINUE</code> <code>MOVE_BLORK</code>
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
				this.display.errorTimer((long) (2000));
				pos = null;
			}
		}
	}
	
//	/**
//	 * 
//	 * Play a turn when you found a GATE.
//	 * 
//	 * @param newGame
//	 *            The current Game.
//	 * 
//	 * @param state
//	 *            The current Barbarian's state.
//	 * 
//	 * @return The new Barbarian's state <code>WIN</code> <code>GAMEOVER</code>
//	 *         <code>CONTINUE</code> <code>MOVE_BLORK</code>
//	 * 
//	 */
//	private BarbarianState onePlayGate(BarbarianState state) {
//
//		DungeonPosition pos = null;
//
//		this.display.gate();
//
//		while (pos == null) {
//
//			try {
//
//				pos = this.jumpChoose(-1, -1);
//
//				state = this.newGame.playGate(pos,
//						WeaponType.values()[this.weaponChoose(0) - 1]);
//			} catch (GameOverException gateE) {
//
//				this.display.errorDisplay(gateE.getMessage());
//				this.display.errorTimer((long) (2000));
//				pos = null;
//			}
//		}
//
//		return state;
//	}

	/**
	 * 
	 * Play a turn when you found a INVINCIBLE BLORK.
	 * 
	 * @param newGame
	 *            The current Game.
	 * 
	 * @param state
	 *            The current Barbarian's state.
	 * 
	 * @return <code>GAMEOVER</code> In every case.
	 * 
	 */
	private void onePlayBlorkInvincible() {

		DungeonPosition pos = null;

		this.display.invincible();

		while (pos == null) {

			try {

				pos = this.jumpChoose(-1, -1);
				this.state = this.newGame.playBlorkInvincible(pos);

			} catch (GameOverException blorkE) {

				this.display.errorDisplay(blorkE.getMessage());
				this.display.errorTimer((long) (2000));
				pos = null;
			}
		}
	}
	
//	/**
//	 * 
//	 * Play a turn when you found a INVINCIBLE BLORK.
//	 * 
//	 * @param newGame
//	 *            The current Game.
//	 * 
//	 * @param state
//	 *            The current Barbarian's state.
//	 * 
//	 * @return <code>GAMEOVER</code> In every case.
//	 * 
//	 */
//	private BarbarianState onePlayBlorkInvincible(BarbarianState state) {
//
//		DungeonPosition pos = null;
//
//		this.display.invincible();
//
//		while (pos == null) {
//
//			try {
//
//				pos = this.jumpChoose(-1, -1);
//				state = this.newGame.playBlorkInvincible(pos);
//
//			} catch (GameOverException blorkE) {
//
//				this.display.errorDisplay(blorkE.getMessage());
//				this.display.errorTimer((long) (2000));
//				pos = null;
//			}
//		}
//
//		return state;
//	}

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

		row = this.rowChoose(row);
		column = this.columnChoose(column);

		return new DungeonPosition(row, column);
	}

	/**
	 * 
	 * Makes you play again if you lose in front of an armed BLORK.
	 * 
	 * @param newGame
	 *            The current Game.
	 * 
	 * @param state
	 *            The current Barbarian's state.
	 * 
	 * @return <code>GAMEOVER</code> if you chose the wrong one again.
	 *         <code>CONTINUE</code> if you chose the right one.
	 */
	private void onePlayJoker() {

		int weapon;

		this.display.joker();
		weapon = this.weaponChoose(0); // 0 Is a wrong Weapon and Direction.

		try {

			// Throw "visited Room" & "out of Dungeon" Exceptions.
			this.state = this.newGame.playJoker(WeaponType.values()[weapon - 1]);

		} catch (GameOverException e) {

			this.display.errorDisplay(e.getMessage());
			this.display.errorTimer((long) (2000));
		}
	}
	
//	/**
//	 * 
//	 * Makes you play again if you lose in front of an armed BLORK.
//	 * 
//	 * @param newGame
//	 *            The current Game.
//	 * 
//	 * @param state
//	 *            The current Barbarian's state.
//	 * 
//	 * @return <code>GAMEOVER</code> if you chose the wrong one again.
//	 *         <code>CONTINUE</code> if you chose the right one.
//	 */
//	private BarbarianState onePlayJoker(BarbarianState state) {
//
//		int weapon;
//
//		this.display.joker();
//		weapon = this.weaponChoose(0); // 0 Is a wrong Weapon and Direction.
//
//		try {
//
//			// Throw "visited Room" & "out of Dungeon" Exceptions.
//			state = this.newGame.playJoker(WeaponType.values()[weapon - 1]);
//
//		} catch (GameOverException e) {
//
//			this.display.errorDisplay(e.getMessage());
//			this.display.errorTimer((long) (2000));
//		}
//
//		return state;
//	}
}
