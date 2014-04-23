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

	/**
	 * 
	 * @param args
	 *            Player's name.
	 */
	public static void main(String[] args) {

		Game newGame;
		BarbarianState state;

		try {

			args = GameFile.fileToArray(args);

			newGame = new Game(args);
			state = BarbarianState.READY_TO_GO;

			while (state != BarbarianState.WIN) {

				state = BarbarianState.READY_TO_GO;
				state = oneTurn(newGame, state);
				nextPlayerTurn(newGame, state);
			}

			Display.winner(newGame);
			System.exit(0);
		} catch (GameOverException ex) {

			Display.shutDown(ex.getMessage());
			System.exit(1);
		}
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
	private static BarbarianState oneTurn(Game newGame, BarbarianState state)
			throws GameOverException {

		while ((state != BarbarianState.GAMEOVER)
				&& (state != BarbarianState.WIN)) {

			Display.dungeonBoard(newGame);
			Display.playerPlay(newGame); // Instructions.

			state = onePlay(newGame, state); // Play one time.
			state = specialState(newGame, state); // In case of MOVE_BLORK,
			// BEAM_ME_UP or JOKER.

			state = newGame.isSurrounded(state);
		}

		return state;
	}

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
	private static BarbarianState onePlay(Game newGame, BarbarianState state) {

		int weapon;
		int direction;

		weapon = weaponChoose(0); // 0 Is a wrong Weapon and Direction.
		direction = directionChoose(0);

		try {

			// Throw "visited Room" & "out of Dungeon" Exceptions.
			state = newGame.play(Direction.values()[direction - 1],
					WeaponType.values()[weapon - 1]);

		} catch (GameOverException e) {

			Display.errorDisplay(e.getMessage());
			Display.errorTimer((long) (2000));
		}

		return state;
	}

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
	private static BarbarianState specialState(Game newGame,
			BarbarianState state) throws GameOverException {

		if (state == BarbarianState.BEAM_ME_UP) {

			Display.dungeonBoard(newGame);
			state = onePlayGate(newGame, state);
		}

		if (state == BarbarianState.MOVE_BLORK) {

			Display.dungeonBoard(newGame);
			state = onePlayBlorkInvincible(newGame, state);
		}

		if (state == BarbarianState.JOKER) {

			Display.dungeonBoard(newGame);
			state = onePlayJoker(newGame, state);
		}

		return state;
	}

	/**
	 * 
	 * @param weapon
	 *            A default wrong weapon number <code>0</code>.
	 * 
	 * @return A number corresponding to a printed Weapon.
	 */
	private static int weaponChoose(int weapon) {

		Scanner clavier = new Scanner(System.in);

		while ((weapon < 1) || (weapon > 4)) {

			Display.weaponChoose();
			try {

				weapon = clavier.nextInt();
			} catch (InputMismatchException ex) {

				Display.inputMismatchDisplay("weapon");
				clavier.next();
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
	private static int directionChoose(int direction) {

		Scanner clavier = new Scanner(System.in);

		while ((direction < 1) || (direction > 4)) {

			Display.directionChoose();
			try {

				direction = clavier.nextInt();
			} catch (InputMismatchException ex) {

				Display.inputMismatchDisplay("direction");
				clavier.next();
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
	private static void nextPlayerTurn(Game newGame, BarbarianState state)
			throws GameOverException {

		if ((state != BarbarianState.WIN) && !(newGame.isOver())) {

			Display.dungeonBoard(newGame);
			Display.nextPlayer(); // Print player change.

			newGame.nextPlayer(); // Apply changes for the next player.
		}
	}

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
	private static BarbarianState onePlayGate(Game newGame, BarbarianState state) {

		DungeonPosition pos = null;

		Display.gate();

		while (pos == null) {

			try {

				pos = jumpChoose(-1, -1);

				state = newGame.playGate(pos,
						WeaponType.values()[weaponChoose(0) - 1]);
			} catch (GameOverException gateE) {

				Display.errorDisplay(gateE.getMessage());
				Display.errorTimer((long) (2000));
				pos = null;
			}
		}

		return state;
	}

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
	private static BarbarianState onePlayBlorkInvincible(Game newGame,
			BarbarianState state) {

		DungeonPosition pos = null;

		Display.invincible();

		while (pos == null) {

			try {

				pos = jumpChoose(-1, -1);
				state = newGame.playBlorkInvincible(pos);

			} catch (GameOverException blorkE) {

				Display.errorDisplay(blorkE.getMessage());
				Display.errorTimer((long) (2000));
				pos = null;
			}
		}

		return state;
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
	private static int rowChoose(int row) {

		Scanner clavier = new Scanner(System.in);

		while ((row < 0) || (row > 4)) {

			Display.rowChoose();
			try {

				row = clavier.nextInt();
			} catch (InputMismatchException ex) {

				Display.inputMismatchDisplay("row");
				clavier.next();
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
	private static int columnChoose(int column) {

		Scanner clavier = new Scanner(System.in);

		while ((column < 0) || (column > 4)) {

			Display.columnChoose();
			try {

				column = clavier.nextInt();
			} catch (InputMismatchException ex) {

				Display.inputMismatchDisplay("column");
				clavier.next();
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
	private static DungeonPosition jumpChoose(int row, int column)
			throws GameOverException {

		row = rowChoose(row);
		column = columnChoose(column);

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
	private static BarbarianState onePlayJoker(Game newGame,
			BarbarianState state) {

		int weapon;

		Display.joker();
		weapon = weaponChoose(0); // 0 Is a wrong Weapon and Direction.

		try {

			// Throw "visited Room" & "out of Dungeon" Exceptions.
			state = newGame.playJoker(WeaponType.values()[weapon - 1]);

		} catch (GameOverException e) {

			Display.errorDisplay(e.getMessage());
			Display.errorTimer((long) (2000));
		}

		return state;
	}
}
