package g39631.gameover.view;

import g39631.gameover.model.*;

/**
 * Display class
 * 
 * @version 1.00
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 */
public class Display {

	private Game newGame;
	private BarbarianColor color;
	private WeaponType weapon;
	private RoomType room;
	private final String PRINCESS 	= "PRINCESS         | ";
	private final String BLORK 		= "BLORK            | ";
	private final String INVINCIBLE = "INVINCIBLE       | ";
	private final String KEY 		= "KEY              | ";
	private final String GATE 		= "GATE             | ";
	private final String ARROWS		= "ARROWS           | ";
	private final String GUN		= "GUN              | ";
	private final String BLUDGEON	= "BLUDGEON         | ";
	private final String POTION		= "POTION           | ";
	private final String RED		= "RED              | ";
	private final String GREEN		= "GREEN            | ";
	private final String YELLOW		= "YELLOW           | ";
	private final String BLUE		= "BLUE             | ";
	private String row1;
	private String row2;
	private String row3;
	
	/**
	 * 
	 * @param newGame
	 * @param color
	 * @param weapon
	 * @param room
	 */
	public Display(Game newGame, BarbarianColor color, WeaponType weapon,
						RoomType room) {
		
		this.newGame = newGame;
		this.color = color;
		this.weapon = weapon;
		this.room = room;
		this.row1 = "| ";
		this.row3 = "| ";
		this.row2 = "| ";
	}
	
	/**
	 * 
	 * Display a string serving top &amp; bottom board.
	 * 
	 */
	private static void topBoard() {

		System.out.println("\t \t" + "  ________________  "
				+ " ________________ " + "  ________________ "
				+ "  ________________ " + "  ________________ ");
	}

	/**
	 * Clear Unix based terminal.
	 */
	static void clearBash() {

		System.out.print("\033[2J\033[;H");
	}

	/**
	 * 
	 * Display a banner of the game.
	 * 
	 */
	private static void bannerPrint() {

		System.out
			.println("\t \t \t"
					+ " \033[41m######      ###    ##     ## ########     #######  ##     ## ######## ########\033[0m"
					+ "\n \t \t \t"
					+ "\033[41m##    ##    ## ##   ###   ### ##          ##     ## ##     ## ##       ##     ##\033[0m"
					+ "\n \t \t \t"
					+ "\033[41m##         ##   ##  #### #### ##          ##     ## ##     ## ##       ##     ##\033[0m"
					+ "\n \t \t \t"
					+ "\033[41m##   #### ##     ## ## ### ## ######      ##     ## ##     ## ######   ########\033[0m"
					+ "\n \t \t \t"
					+ "\033[41m##    ##  ######### ##     ## ##          ##     ##  ##   ##  ##       ##   ##\033[0m"
					+ "\n \t \t \t"
					+ "\033[41m##    ##  ##     ## ##     ## ##          ##     ##   ## ##   ##       ##    ##\033[0m"
					+ "\n \t \t \t"
					+ " \033[41m######   ##     ## ##     ## ########     #######     ###    ######## ##     ##\033[0m");
	}

	/**
	 * Print a start message.
	 * 
	 * @param game
	 *            The current game to get Players informations.
	 */
	static void playerPlay(Game game) {

		System.out.print("\n \t \t Player " + game.getCurrentPlayer().getName()
				+ " : your turn to play.\n" + "\t \t Try to find your "
				+ game.getCurrentPlayer().getColor() + " PRINCESS !\n \n");
	}

	/**
	 * 
	 * Display all the Dungeon with its hidden and visited parts.
	 * 
	 * @param game
	 *            The current game to get Rooms informations (Hidden :
	 *            <code>false</code>)
	 * @throws GameOverException
	 *             If you create a DungeonPosition outside the Dungeon by a
	 *             mismatch.
	 */
	static void dungeonBoard(Game game) throws GameOverException {

		String row1; // RoomType.
		String row2; // Attribute.
		String row3; // Position.

		row1 = "| ";
		row2 = "| ";
		row3 = "| ";

		clearBash();
		bannerPrint();
		topBoard();

		for (int i = 0; i < 25; i++) {

			DungeonPosition position = new DungeonPosition((i / 5), (i % 5));
			row3 = row3 + "\033[37m" + position + "\033[0m" + " | ";

			// If already visited.
			if (!(game.getDungeon().getRoom(position).isHidden())) {

				switch (game.getDungeon().getRoom(position).getType()) {
					case BLORK:
						row1 = row1 + "BLORK            | ";
						// Which type of BLORK.
						if (game.getDungeon().getRoom(position).getWeapon() == null) {

							row2 = row2 + "INVINCIBLE       | ";
						} else {

							row2 = weaponRow(game.getDungeon().getRoom(position)
									.getWeapon(), row2);
						}
						break;

					case PRINCESS:
						row1 = row1
							+ colors(game.getDungeon().getRoom(position)
									.getColor()) + "PRINCESS" + "\033[0m"
							+ "         | ";
						row2 = colorRow(game.getDungeon().getRoom(position)
								.getColor(), row2);
						break;

					case KEY:
						row1 = row1 + "KEY              | ";
						row2 = row2 + "                 | ";
						break;

					case GATE:
						row1 = row1 + "GATE             | ";
						row2 = row2 + "                 | ";
						break;

					default:
						row1 = row1 + "                 | ";
						row2 = row2 + "                 | ";
						break;
				} // End switch.
			} else {
				// Hidden Room.
				row1 = row1 + "                 | ";
				row2 = row2 + "                 | ";
			} 

			// At the end of the row,
			// Display it and prepare the next row.
			row1 = displayRow(i, row1);
			row2 = displayRow(i, row2);
			row3 = displayRow(i, row3);
			if ((i % 5) == 4) {

				topBoard();
			}
		}
	}

	/**
	 * 
	 * @param weapon
	 *            The WeaponType of the Room.
	 * 
	 * @param row2
	 *            Its printed representation.
	 * 
	 * @return A printable variable weapon.
	 * 
	 */
	private static String weaponRow(WeaponType weapon, String row2) {

		switch (weapon) {
			case ARROWS:
				row2 = row2 + "ARROWS           | ";
				break;
			case GUN:
				row2 = row2 + "GUN              | ";
				break;
			case BLUDGEON:
				row2 = row2 + "BLUDGEON         | ";
				break;
			case POTION:
				row2 = row2 + "POTION           | ";
				break;
			default:
				row2 = row2 + "                 | ";
				break;
		}

		return row2;
	}

	/**
	 * 
	 * @param color
	 *            The BarbarianColor of the Room.
	 * 
	 * @param row2
	 *            Its printed representation.
	 * 
	 * @return A printable variable color.
	 * 
	 */
	private static String colorRow(BarbarianColor color, String row2) {

		switch (color) {
			case RED:
				row2 = row2 + "RED              | ";
				break;
			case GREEN:
				row2 = row2 + "GREEN            | ";
				break;
			case YELLOW:
				row2 = row2 + "YELLOW           | ";
				break;
			case BLUE:
				row2 = row2 + "BLUE             | ";
				break;
			default:
				row2 = row2 + "                 | ";
				break;
		}

		return row2;
	}

	/**
	 * 
	 * @param i
	 *            A Room index.
	 * 
	 * @param row
	 *            All RoomType of a row.
	 * 
	 * @return A empty RoomType for next row.
	 * 
	 */
	private static String displayRow(int i, String row) {

		if (i % 5 == 4) {

			System.out.println(" \t \t" + row);
			row = "| ";
		}

		return row;
	}



	/**
	 * 
	 * Display a message to choose weapon.
	 * 
	 */
	static void weaponChoose() {

		System.out.println("\n \t \tWhich weapon do you choose ?");
		System.out.print("\t\tPOTION(1) - ARROWS(2) - BLUDGEON(3) - GUN(4)"
				+ "\n\t\t");
	}

	/**
	 * 
	 * Display a message to choose direction.
	 * 
	 */
	static void directionChoose() {

		System.out.println("\n \t \tWhich way do you go ?");
		System.out.print("\t\tUP(1) - DOWN(2) - RIGHT(3) - LEFT(4) \n\t\t");
	}

	/**
	 * 
	 * Display a message that BEAM_ME_UP is usable.
	 * 
	 */
	static void gate() {

		System.out.println("\n\t\t" + colors(BarbarianColor.RED)
				+ "You found a magical door. "
				+ "You can teleport yourself to any unvisited Room !"
				+ "\033[0m");
	}

	/**
	 * 
	 * Display a message that you have to transfer an INVICIBLE BLORK.
	 * 
	 */
	static void invincible() {

		System.out.println("\n\t\t" + colors(BarbarianColor.RED)
				+ "You can not kill that INVINCIBLE BLORK but in a last "
				+ "effort, you quickly transfer it to another Room."
				+ "\033[0m");
	}

	/**
	 * 
	 * Display a message to choose row.
	 * 
	 */
	static void rowChoose() {

		System.out.println("\n \t \tWhich row do you choose ?");
		System.out.print("\t\tFrom up to down : 0 - 1 - 2 - 3 - 4 \n\t\t");
	}

	/**
	 * 
	 * Display a message to choose column.
	 * 
	 */
	static void columnChoose() {

		System.out.println("\n \t \tWhich column do you choose ?");
		System.out.print("\t\tFrom left to right : 0 - 1 - 2 - 3 - 4 \n\t\t");
	}

	/**
	 * 
	 * Display a message that JOKER is usable.
	 * 
	 */
	static void joker() {

		System.out.println("\n\t\t" + colors(BarbarianColor.RED)
				+ "You can not kill that BLORK But in a last "
				+ "effort, you quickly change weapon using your JOKER."
				+ "\033[0m");
	}



	/**
	 * 
	 * Run a timer to temporary watch message.
	 * 
	 * @param millis
	 * 			How many time is the system hold.
	 * 
	 */
	static void errorTimer(Long millis) {

		try {

			java.lang.Thread.sleep(millis);
		} catch (InterruptedException ea) {

			System.out.println("You only had to wait 2s !");
		}
	}

	/**
	 * 
	 * @param color
	 *            A PRINCESS color.
	 * 
	 * @return A string with bash color code.
	 * 
	 */
	private static String colors(BarbarianColor color) {

		String colorCode = "\033[0m";

		switch (color) {

			case RED:
				colorCode = "\033[31m";
				break;
			case GREEN:
				colorCode = "\033[32m";
				break;
			case BLUE:
				colorCode = "\033[34m";
				break;
			case YELLOW:
				colorCode = "\033[33m";
				break;
			default:
				break;
		}

		return colorCode;
	}

	/**
	 * 
	 * Print a failed message and call next Player. Using Thread to temporarily
	 * display message.
	 * 
	 * @see java.lang.Thread#sleep(long)
	 */
	static void nextPlayer() {

		System.out.println("\n \t \t \033[31mYou failed.\n \n"
				+ "\t \t Next player to play.\033[0m \n");

		errorTimer((long)(2000));
	}

	/**
	 * Display a message to announce the winner.
	 * 
	 * @param game
	 *            The current game to get winner's informations.
	 * @throws GameOverException
	 *             From Display.dungeonBoard(game) : If you create a
	 *             DungeonPosition outside the Dungeon by a mismatch.
	 */
	static void winner(Game game) throws GameOverException {

		dungeonBoard(game);

		System.out.print("\n \t \t There is a winner !\n \n");
		System.out.println("\t \t Winner is : " + game.getWinner().getName());
		System.out.println("\t \t He found his " + game.getWinner().getColor()
				+ " PRINCESS ! \n");
	}

	/**
	 * 
	 * Print exception message that shut program down.
	 * 
	 * @param error
	 *            A exception message.
	 * 
	 */
	static void shutDown(String error) {

		clearBash();
		bannerPrint();
		System.out.println("\n \t \t \t " + error);
		System.out.println("\n \t \t \t END OF \"GAME OVER\" THE GAME. \n");
	}

	/**
	 * 
	 * Display an inputMismatch error message to say what is required.
	 * 
	 * @param input
	 *            name of required input.
	 * 
	 */
	static void inputMismatchDisplay(String input) {

		System.out.println("\n \t \t \t " + "A " + input
				+ " number is required.");
	}

	/**
	 * 
	 * @param error
	 *            every error.getMessage()
	 * 
	 */
	static void errorDisplay(String error) {

		System.out.println("\n \t \t \t " + error + "\n");
	}
}
