package g39631.gameover.view;

import g39631.gameover.model.*;

/**
 * 
 * This class is responsible for all display requests. 
 * It has been constructed as a display dictionary for every 
 * interaction with the player.
 * 
 * @version 2.00
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 */
public class Display {

	private BarbarianColor[] 	colors;
	private WeaponType[] 		weapons;
	private RoomType[] 			types;
	private DungeonPosition[] 	positions;
	private Room[] 				rooms;

	private String row1;
	private String row2;
	private String row3;

	private final String ROW_1_INIT = " \t \t| ";
	private final String ROW_2_INIT = " \t \t| ";
	private final String ROW_3_INIT = " \t \t| ";
	private final String TOP 		= "  ________________ ";

	private final String CLEAR 		= "\033[2J\033[;H";

	private final String[] CODE_COLOR = {"\033[31m","\033[32m","\033[33m",
		"\033[34m"};
	private final String INIT_COLOR = "\033[0m";

	private final String WEAPONS = "POTION(1) - ARROWS(2) - BLUDGEON(3) - GUN(4)";
	private final String DIRECTIONS = "UP(1) - DOWN(2) - RIGHT(3) - LEFT(4)";

	private final String NEXTP = "You failed.\n\n\t\t Next player to play. \n";
	private final String END = "\n\n\t \t \t END OF \"GAME OVER\" THE GAME. \n";


	/**
	 * This create a new Display wrote for "GAMEOVER". Arrays are number of
	 * Rooms length and their informations are initialized when
	 * dungeonBoard(Game game) is called. Row1-2-3 are going to be complete with
	 * Dungeon informations.
	 */
	Display() {

		this.colors 	= new BarbarianColor[Dungeon.N * Dungeon.N];
		this.weapons 	= new WeaponType[Dungeon.N * Dungeon.N];
		this.types 		= new RoomType[Dungeon.N * Dungeon.N];
		this.positions 	= new DungeonPosition[Dungeon.N * Dungeon.N];
		this.rooms 		= new Room[Dungeon.N * Dungeon.N];
		this.row1 		= ROW_1_INIT;
		this.row2 		= ROW_2_INIT;
		this.row3 		= ROW_3_INIT;
	}

	/**
	 * 
	 * Display a string serving top &amp; bottom board.
	 * 
	 */
	private void topBoard() {

		System.out.println("\t \t" 
				+ this.TOP
				+ this.TOP
				+ this.TOP
				+ this.TOP
				+ this.TOP);
	}

	/**
	 * Clear Unix based terminal.
	 */
	private void clearBash() {

		System.out.print(this.CLEAR);
	}

	/**
	 * 
	 * Display a "GAME OVER" banner.
	 * 
	 */
	private void banner() {

		System.out
			.println("\t \t \t"
					+ " \033[41m######      ###    ##     ## ########     #######  ##     ## ######## ########"
					+ this.INIT_COLOR
					+ "\n \t \t \t"
					+ "\033[41m##    ##    ## ##   ###   ### ##          ##     ## ##     ## ##       ##     ##"
					+ this.INIT_COLOR
					+ "\n \t \t \t"
					+ "\033[41m##         ##   ##  #### #### ##          ##     ## ##     ## ##       ##     ##"
					+ this.INIT_COLOR
					+ "\n \t \t \t"
					+ "\033[41m##   #### ##     ## ## ### ## ######      ##     ## ##     ## ######   ########"
					+ this.INIT_COLOR
					+ "\n \t \t \t"
					+ "\033[41m##    ##  ######### ##     ## ##          ##     ##  ##   ##  ##       ##   ##"
					+ this.INIT_COLOR
					+ "\n \t \t \t"
					+ "\033[41m##    ##  ##     ## ##     ## ##          ##     ##   ## ##   ##       ##    ##"
					+ this.INIT_COLOR
					+ "\n \t \t \t"
					+ " \033[41m######   ##     ## ##     ## ########     #######     ###    ######## ##     ##"
					+ this.INIT_COLOR);
	}

	/**
	 * Display a start message.
	 * 
	 * @param player
	 *            The current player.
	 */
	void instructions(Player player) {

		System.out.print("\n \t \t Player " 
				+ player.getName()
				+ " : your turn to play.\n" 
				+ "\t \t Try to find your "
				+ player.getColor() 
				+ " PRINCESS !\n \n");
	}

	/**
	 * 
	 * Prepare and display all the Dungeon with its hidden and visited parts.
	 * 
	 * @param game
	 *            The current Game to get Rooms informations.
	 * 
	 * @throws GameOverException
	 *             If you create a DungeonPosition outside the Dungeon by a
	 *             mismatch.
	 */
	void dungeonBoard(Game game) throws GameOverException {

		this.initPos(game);
		this.clearBash();
		this.banner();
		this.topBoard();
		this.displayBoard();
	}

	/**
	 * 
	 * Fill arrays attributes with every useful Dungeon's informations.
	 * 
	 * @param game
	 *            The current game to get Rooms informations.
	 * 
	 * @throws GameOverException
	 *             If you create a DungeonPosition outside the Dungeon by a
	 *             mismatch.
	 */
	private void initPos(Game game) throws GameOverException {

		for (int i = 0; i < (Dungeon.N * Dungeon.N); i++) {

			this.positions[i] 	= new DungeonPosition(i/Dungeon.N, i%Dungeon.N);
			this.rooms[i] 		= game.getDungeon().getRoom(this.positions[i]);
			this.colors[i] 		= this.rooms[i].getColor();
			this.types[i] 		= this.rooms[i].getType();
			this.weapons[i] 	= this.rooms[i].getWeapon();
		}

	}

	/**
	 * Fill the Dungeon's rows of informations.
	 */
	private void displayBoard() {

		for (int i = 0; i < (Dungeon.N * Dungeon.N); i++) {

			this.positionRow(this.positions[i]);

			if (!this.rooms[i].isHidden()) {

				this.typeRow(this.types[i]);
				this.weaponRow(this.types[i], this.weapons[i]);
				this.colorRow(this.colors[i]);// OR
				this.emptyRow(this.types[i]);// OR
			} else {

				this.row1 += String.format("%-17s| ", " ");
				this.row2 += String.format("%-17s| ", " ");
			}

			this.displayRow(i);
			if ((i % Dungeon.N) == (Dungeon.N - 1)) {

				this.topBoard();
			}
		}
	}

	/**
	 * 
	 * Fill Room's type informations in his own string.
	 * 
	 * @param roomType
	 *            What kind of Room is it.
	 */
	private void typeRow(RoomType roomType) {

		this.row1 += String.format("%-17s| ", roomType);
	}

	/**
	 * 
	 * Fill BLORK's weapon informations in the additional information's row.
	 * 
	 * @param roomType
	 *            What kind of Room is it.
	 * 
	 * @param weaponType
	 *            What kind of BLORK's weapon is it.
	 */
	private void weaponRow(RoomType roomType, WeaponType weaponType) {

		if (roomType == RoomType.BLORK) {

			if (weaponType == null) {

				this.row2 += String.format("%-17s| ", "INVINCIBLE");
			} else {

				this.row2 += String.format("%-17s| ", weaponType);
			}
		}
	}

	/**
	 * 
	 * Fill PRINCESS's color in the additional information's row.
	 * 
	 * @param color
	 *            The BarbarianColor of the Room.
	 * 
	 */
	private void colorRow(BarbarianColor color) {

		if (color != null) {

			this.row2 += colors(color) 
				+ String.format("%-17s", color)
				+ this.INIT_COLOR 
				+ "| ";
		}
	}

	/**
	 * Do not fill any informations in the additional information's row.
	 * 
	 * @param roomType
	 *            What kind of Room is it.
	 */
	private void emptyRow(RoomType roomType) {

		if ((roomType != RoomType.BLORK) && (roomType != RoomType.PRINCESS)) {

			this.row2 += String.format("%-17s| ", " ");
		}
	}

	/**
	 * 
	 * Fill positions (along x-axis and -y-axis) in the location's row (reversed
	 * Cartesian coordinates plan).
	 * 
	 * @param position
	 *            A DungeonPosition in the Dungeon.
	 * 
	 */
	private void positionRow(DungeonPosition position) {

		this.row3 += "\033[37m" 
			+ String.format("%-17s", position)
			+ this.INIT_COLOR 
			+ "| ";
	}

	/**
	 * 
	 * Display all the Dungeon with its hidden and visited parts.
	 * 
	 * @param i
	 *            A Room's index to know how many Rooms have been analyzed.
	 * 
	 */
	private void displayRow(int i) {

		if ((i % Dungeon.N) == (Dungeon.N - 1)) {

			System.out.println(this.row1);
			System.out.println(this.row2);
			System.out.println(this.row3);
			this.row1 = ROW_1_INIT;
			this.row2 = ROW_2_INIT;
			this.row3 = ROW_3_INIT;
		}
	}

	/**
	 * 
	 * Display a message to select weapon.
	 * 
	 */
	void weaponChoose() {

		System.out.print("\n \t \t" 
				+ "Which weapon do you choose ?" 
				+ "\n"
				+ String.format("\t\t%-44s\n\t\t", this.WEAPONS));
	}

	/**
	 * 
	 * Display a message to select Barbarian's direction through the Dungeon.
	 * 
	 */
	void directionChoose() {

		System.out.print("\n \t \t" 
				+ "Which way do you go ?" 
				+ "\n"
				+ String.format("\t\t%-44s\n\t\t", this.DIRECTIONS));
	}

	/**
	 * 
	 * Display a message to warn BEAM_ME_UP is available.
	 * 
	 */
	void gate() {

		System.out.println("\n\t\t" 
				+ this.CODE_COLOR[0]
				+ "You found a magical door. "
				+ "You can teleport yourself to any unvisited Room !"
				+ this.INIT_COLOR);
	}

	/**
	 * 
	 * Display a message to warn that INVICIBLE BLORK can be moved.
	 * 
	 */
	void invincible() {

		System.out.println("\n\t\t" + this.CODE_COLOR[0]
				+ "You can not kill that INVINCIBLE BLORK but in a last "
				+ "effort, you quickly transfer it to another Room."
				+ this.INIT_COLOR);
	}

	/**
	 * 
	 * Display a message to select row (y-axis).
	 * 
	 */
	void rowChoose() {

		System.out.print("\n \t \t" 
				+ "Which row do you choose ?" 
				+ "\n\t\t"
				+ "From up to down : 0 - 1 - 2 - 3 - 4 " 
				+ "\n\t\t");
	}

	/**
	 * 
	 * Display a message to choose column (x-axis).
	 * 
	 */
	void columnChoose() {

		System.out.print("\n \t \t" 
				+ "Which column do you choose ?" 
				+ "\n\t\t"
				+ "From left to right : 0 - 1 - 2 - 3 - 4 " 
				+ "\n\t\t");
	}

	/**
	 * 
	 * Display a message to warn that JOKER gives you another attempt.
	 * 
	 */
	void joker() {

		System.out.println("\n\t\t" 
				+ this.CODE_COLOR[0]
				+ "You can not kill that BLORK But in a last "
				+ "effort, you quickly change weapon using your JOKER."
				+ this.INIT_COLOR);
	}

	/**
	 * 
	 * Run a timer to temporarily watch message.
	 * 
	 */
	private void errorTimer() {

		try {

			java.lang.Thread.sleep(2000);
		} catch (InterruptedException ea) {

			System.out.println("You only had to wait 2s !");
		}
	}

	/**
	 * 
	 * @param color
	 *            The color of a bound barbarian to a princess.
	 * 
	 * @return A string with bash color code.
	 * 
	 */
	private String colors(BarbarianColor color) {

		String colorCode = this.INIT_COLOR;

		switch (color) {

			case RED:
				colorCode = this.CODE_COLOR[0];
				break;
			case GREEN:
				colorCode = this.CODE_COLOR[1];
				break;
			case BLUE:
				colorCode = this.CODE_COLOR[2];
				break;
			case YELLOW:
				colorCode = this.CODE_COLOR[3];
				break;
			default:
				break;
		}

		return colorCode;
	}

	/**
	 * 
	 * Display a failed message and call next Player.
	 * 
	 * @see java.lang.Thread#sleep(long)
	 */
	void nextPlayer() {

		System.out.println("\n \t \t " 
				+ this.CODE_COLOR[0] 
				+ this.NEXTP
				+ this.INIT_COLOR);

		this.errorTimer();
	}

	/**
	 * Display a message to announce the winner.
	 * 
	 * @param winner
	 *            The Game's winner.
	 * 
	 */
	void winner(Player winner) {

		this.clearBash();
		this.banner();
		this.topBoard();
		this.displayBoard();
		System.out.print("\n \t \t There is a winner !\n \n"
				+ "\t \t Winner is : " + winner.getName()
				+ "\n \t \t He found his " + winner.getColor()
				+ " PRINCESS ! \n \n");
	}

	/**
	 * 
	 * Display an error's message that stops "GAMEOVER".
	 * 
	 * @param error
	 *            An error's message.
	 * 
	 */
	void shutDown(String error) {

		this.clearBash();
		this.banner();
		System.out.println("\n \t \t \t " + error + this.END);
	}

	/**
	 * 
	 * Display an inputMismatch's message to say what is required.
	 * 
	 * @param input
	 *            Name of required input.
	 * 
	 */
	void inputMismatchDisplay(String input) {

		this.errorDisplay("A " + input + " number is required.");
	}

	/**
	 * 
	 * Display an error's message to say what is wrong.
	 * 
	 * @param error
	 *            A GameOverException's message.
	 * 
	 */
	void errorDisplay(String error) {

		System.out.println("\n \t \t \t " + error);
		this.errorTimer();
	}
}
