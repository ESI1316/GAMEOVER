package g39631.gameover.view;

import g39631.gameover.model.*;

/**
 * Display class
 * 
 * @version 2.00
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 */
public class Display {

    private BarbarianColor[] colors;
    private WeaponType[] weapons;
    private RoomType[] types;
    private DungeonPosition[] positions;
    private Room[] rooms;
    private String row1;
    private String row2;
    private String row3;
    private boolean hidden;
    private final String ROW_1_INIT =  "| ";
    private final String ROW_2_INIT =  "| ";
    private final String ROW_3_INIT =  "| ";
    private final String CLEAR = "\033[2J\033[;H";

    /**
     * 
     */
    Display() {

	this.colors 	= new BarbarianColor[Dungeon.N * Dungeon.N];
	this.weapons 	= new WeaponType[Dungeon.N * Dungeon.N];
	this.types 		= new RoomType[Dungeon.N * Dungeon.N];
	this.positions 	= new DungeonPosition[Dungeon.N * Dungeon.N];
	this.rooms		= new Room[Dungeon.N * Dungeon.N];
	this.row1 = ROW_1_INIT;
	this.row2 = ROW_2_INIT;
	this.row3 = ROW_3_INIT;
	this.hidden = true;
    }

    /**
     * 
     * Display a string serving top &amp; bottom board.
     * 
     */
    private void topBoard() {

	System.out.println("\t \t " + " ________________  "
		+ " ________________ " + "  ________________ "
		+ "  ________________ " + "  ________________ ");
    }

    /**
     * Clear Unix based terminal.
     */
    void clearBash() {

	System.out.print(this.CLEAR);
    }

    /**
     * 
     * Display a banner of the game.
     * 
     */
    private void banner() {

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
    void playerPlay(Game game) {

	System.out.print("\n \t \t Player " + game.getCurrentPlayer().getName()
		+ " : your turn to play.\n" + "\t \t Try to find your "
		+ game.getCurrentPlayer().getColor() + " PRINCESS !\n \n");
    }

    /**
     * 
     * Display all the Dungeon with its hidden and visited parts.
     * 
     * @param game
     *            The current game to get Rooms informations.
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
     * @param game
     *            The current game to get Rooms informations.
     * 
     * @throws GameOverException
     *             If you create a DungeonPosition outside the Dungeon by a
     *             mismatch.
     */
    private void initPos(Game game) throws GameOverException {

	for (int i = 0; i < (Dungeon.N * Dungeon.N); i++) {

		this.positions[i] = new DungeonPosition(i / Dungeon.N, i % Dungeon.N);
		this.rooms[i] = game.getDungeon().getRoom(this.positions[i]);
	    this.colors[i] = this.rooms[i].getColor();
	    this.types[i] = this.rooms[i].getType();
	    this.weapons[i] = this.rooms[i].getWeapon();
	}

    }

    /**
     * 
     */
    private void displayBoard() {

	    for (int i = 0; i < (Dungeon.N * Dungeon.N); i++) {

		this.hidden = this.rooms[i].isHidden();

		positionRow(this.positions[i]);
		if (!(hidden)) {

		    this.typeRow(this.types[i]);
		    this.weaponRow(this.types[i], this.weapons[i]);
		    this.colorRow(this.colors[i]);// OR
		    this.emptyRow(this.types[i]);// OR
		} else {

		    this.row1 += String.format("%-17s| ", " ");
		    this.row2 += String.format("%-17s| ", " ");
		}

		this.displayRow(i);
		if ((i % Dungeon.N) == 4) {

		    this.topBoard();
		}
	    }
	}

    /**
     * 
     * @param roomType
     */
    private void typeRow(RoomType roomType) {

	this.row1 += String.format("%-17s| ", roomType);
    }

    /**
     * 
     * @param roomType
     * @param weaponType
     */
    private void weaponRow(RoomType roomType, WeaponType weaponType) {

	if (roomType == RoomType.BLORK) {

	    if (weaponType == null) {

		this.row2 += String.format("%-17s| ", "INVINCIBLE");
	    }else{

		this.row2 += String.format("%-17s| ", weaponType);
	    }
	}
    }

    /**
     * 
     * @param color
     *            The BarbarianColor of the Room.
     * 
     */
    private void colorRow(BarbarianColor color) {

	if (color != null) {

	    this.row2 += colors(color) + String.format("%-17s", color)
		+ "\033[0m" + "| ";
	}
    }

    /**
     * 
     * @param roomType
     */
    private void emptyRow(RoomType roomType) {

	if ((roomType != RoomType.BLORK) && (roomType != RoomType.PRINCESS)) {

	    this.row2 += String.format("%-17s| ", " ");
	}
    }

    /**
     * 
     * @param position
     */
    private void positionRow(DungeonPosition position) {

	this.row3 += "\033[37m" + String.format("%-15s", position) 
	    + "\033[0m" + " | ";
    }

    /**
     * 
     * @param i
     *            A Room index.
     * 
     * 
     */
    private void displayRow(int i) {

	if (i % Dungeon.N == 4) {

	    System.out.println(" \t \t" + this.row1);
	    System.out.println(" \t \t" + this.row2);
	    System.out.println(" \t \t" + this.row3);
	    this.row1 = ROW_1_INIT;
	    this.row2 = ROW_2_INIT;
	    this.row3 = ROW_3_INIT;
	}
    }

    /**
     * 
     * Display a message to choose weapon.
     * 
     */
    void weaponChoose() {

	System.out.println("\n \t \tWhich weapon do you choose ?");
	System.out.print("\t\tPOTION(1) - ARROWS(2) - BLUDGEON(3) - GUN(4)"
		+ "\n\t\t");
    }

    /**
     * 
     * Display a message to choose direction.
     * 
     */
    void directionChoose() {

	System.out.println("\n \t \tWhich way do you go ?");
	System.out.print("\t\tUP(1) - DOWN(2) - RIGHT(3) - LEFT(4) \n\t\t");
    }

    /**
     * 
     * Display a message that BEAM_ME_UP is usable.
     * 
     */
    void gate() {

	System.out.println("\n\t\t" + this.colors(BarbarianColor.RED)
		+ "You found a magical door. "
		+ "You can teleport yourself to any unvisited Room !"
		+ "\033[0m");
    }

    /**
     * 
     * Display a message that you have to transfer an INVICIBLE BLORK.
     * 
     */
    void invincible() {

	System.out.println("\n\t\t" + this.colors(BarbarianColor.RED)
		+ "You can not kill that INVINCIBLE BLORK but in a last "
		+ "effort, you quickly transfer it to another Room."
		+ "\033[0m");
    }

    /**
     * 
     * Display a message to choose row.
     * 
     */
    void rowChoose() {

	System.out.println("\n \t \tWhich row do you choose ?");
	System.out.print("\t\tFrom up to down : 0 - 1 - 2 - 3 - 4 \n\t\t");
    }

    /**
     * 
     * Display a message to choose column.
     * 
     */
    void columnChoose() {

	System.out.println("\n \t \tWhich column do you choose ?");
	System.out.print("\t\tFrom left to right : 0 - 1 - 2 - 3 - 4 \n\t\t");
    }

    /**
     * 
     * Display a message that JOKER is usable.
     * 
     */
    void joker() {

	System.out.println("\n\t\t" + this.colors(BarbarianColor.RED)
		+ "You can not kill that BLORK But in a last "
		+ "effort, you quickly change weapon using your JOKER."
		+ "\033[0m");
    }

    /**
     * 
     * Run a timer to temporary watch message.
     * 
     * @param millis
     *            How many time is the system hold.
     * 
     */
    void errorTimer(Long millis) {

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
    private String colors(BarbarianColor color) {

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
    void nextPlayer() {

	System.out.println("\n \t \t \033[31mYou failed.\n \n"
		+ "\t \t Next player to play.\033[0m \n");

	this.errorTimer((long) (2000));
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
    void winner(Game game) throws GameOverException {

	this.dungeonBoard(game);

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
    void shutDown(String error) {

	this.clearBash();
	this.banner();
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
    void inputMismatchDisplay(String input) {

	System.out.println("\n \t \t \t " + "A " + input
		+ " number is required.");
    }

    /**
     * 
     * @param error
     *            every error.getMessage()
     * 
     */
    void errorDisplay(String error) {

	System.out.println("\n \t \t \t " + error + "\n");
    }
}
