package g39631.gameover.model;

/**
 * DungeonPosition class
 * 
 * @version 2.00
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 */
public class DungeonPosition {

	public static final DungeonPosition P_BARBARIAN_1;
	public static final DungeonPosition P_BARBARIAN_2;
	public static final DungeonPosition P_BARBARIAN_3;
	public static final DungeonPosition P_BARBARIAN_4;
	private int column;
	private int row;

	// BARBARIAN's initial position initialization.
	static {
		
		P_BARBARIAN_1 = new DungeonPosition();
		P_BARBARIAN_1.row = -1;

		P_BARBARIAN_2 = new DungeonPosition();
		P_BARBARIAN_2.row = 5;
		P_BARBARIAN_2.column = 4;

		P_BARBARIAN_3 = new DungeonPosition();
		P_BARBARIAN_3.row = 4;
		P_BARBARIAN_3.column = -1;

		P_BARBARIAN_4 = new DungeonPosition();
		P_BARBARIAN_4.column = 5;
	}

	/**
	 * Overriding of constructor to keep it private and useful only
	 * for P_BARBARIAN Instantiation.
	 * 
	 *             
	 */
	private DungeonPosition() {

		// this(0,0);
		
		this.row = 0;
		this.column = 0;
	}

	/**
	 * This creates a DungeonPosition by the given parameter position.
	 * 
	 * @param column
	 *            An eastern position.
	 * @param row
	 *            a northern position.
	 * @throws GameOverException
	 *             If you are out of the dungeon.
	 */
	public DungeonPosition(int row, int column) throws GameOverException {

		if ((column < 0) || (column > (Dungeon.DUNGEON_SIDE_SIZE - 1))
				|| (row < 0) || (row > (Dungeon.DUNGEON_SIDE_SIZE - 1))) {

			throw new GameOverException("Wrong position, out of dungeon.");
		}

		this.column = column;
		this.row = row;
	}

	/**
	 * You go to a new position in the dungeon with the movement made.
	 * 
	 * @param direction
	 *            A way to go in the dungeon.
	 * @return The new position in the dungeon.
	 * @throws GameOverException
	 *             If you are out of the dungeon.
	 */
	public DungeonPosition move(Direction direction) throws GameOverException {

		DungeonPosition newPosition = null;

		switch (direction) {
			case UP:
				newPosition = this.up();
				break;

			case DOWN:
				newPosition = this.down();
				break;

			case LEFT:
				newPosition = this.left();
				break;

			case RIGHT:
				newPosition = this.right();
				break;

			default:
				break;
		}

		return newPosition;
	}

	/**
	 * 
	 * Create a new higher position.
	 * 
	 * @return The new position in the dungeon.
	 * @throws GameOverException
	 *             If you are out of the dungeon from above.
	 * 
	 */
	private DungeonPosition up() throws GameOverException {

		return new DungeonPosition(this.getRow() - 1, this.getColumn());
	}

	/**
	 * 
	 * Create a new lower position.
	 * 
	 * @return The new position in the dungeon.
	 * @throws GameOverException
	 *             If you are out of the dungeon at the bottom.
	 * 
	 */
	private DungeonPosition down() throws GameOverException {

		return new DungeonPosition(this.getRow() + 1, this.getColumn());
	}

	/**
	 * 
	 * Create a new left position.
	 * 
	 * @return The new position in the dungeon.
	 * @throws GameOverException
	 *             If you are out of the dungeon to the left.
	 */
	private DungeonPosition left() throws GameOverException {

		return new DungeonPosition(this.getRow(), this.getColumn() - 1);
	}

	/**
	 * 
	 * Create a new right position.
	 * 
	 * @return The new position in the dungeon.
	 * @throws GameOverException
	 *             If you are out of the dungeon to the right.
	 */
	private DungeonPosition right() throws GameOverException {

		return new DungeonPosition(this.getRow(), this.getColumn() + 1);
	}

	/**
	 * 
	 * Test if the current DungeonPosition is in the corner of the Dungeon.
	 * 
	 * @return if the position in the Dungeon is in one of the four corners.
	 */
	public boolean isCorner() {

		return (this.row == 0 && (this.column == 4 || this.column == 0))
			|| (this.row == 4 && (this.column == 0 || this.column == 4));
	}

	/**
	 * 
	 * @return The current northern position.
	 */
	public int getRow() {

		return this.row;
	}

	/**
	 * 
	 * @return The current eastern position.
	 */
	public int getColumn() {

		return this.column;
	}

	/**
	 * @return A string representation of the object.
	 */
	public String toString() {

		return "Position : (" + this.getRow() + "," + this.getColumn() + ")";
	}
}
