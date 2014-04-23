package g39631.gameover.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Dungeon class
 * 
 * @version 2.00
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 */
public class Dungeon {

	public static final int DUNGEON_SIDE_SIZE = 5;
	private static Dungeon instance;
	private Room[][] roomss;

	/**
	 * Create a new Dungeon by adding all rooms in the game board (2D array).
	 * For each PRINCESS color, besides, there are 4 armed BLORK too. Using
	 * rooms.size() to put only 2 KEY, 2 BLORKS INVICIBLE &amp; 1 GATE.
	 * Collection.shuffle() to shake the game board randomly.
	 * 
	 * @see java.util.Collections#shuffle(List)
	 */
	private Dungeon() {

		this.roomss = new Room[DUNGEON_SIDE_SIZE][DUNGEON_SIDE_SIZE];

		List<Room> rooms = new ArrayList<Room>();

		rooms = roomsInList(rooms);
		Collections.shuffle(rooms);
		roomsInGameBoard(rooms);
	}

	/**
	 * Create a new Game with a specific gameboard. Package visibility only used
	 * by JUnit test.
	 * 
	 * @param configuration
	 *            A new game board.
	 */
	Dungeon(Room[][] configuration) {

		Dungeon.instance = Dungeon.getInstance();
		Dungeon.getInstance().roomss = configuration;
	}

	/**
	 * The only way to create a new Dungeon is to use the following method. It
	 * is the only one who calls private constructor and it does call it only if
	 * there is no instance yet.
	 * 
	 * @return A new Dungeon.
	 */
	public static synchronized Dungeon getInstance() {

		if (instance == null) {

			instance = new Dungeon();
		}

		return instance;
	}

	/**
	 * Add 25 rooms in a List of Room. Only used by private constructor.
	 * 
	 * @param rooms
	 *            an empty list of Room.
	 * 
	 * @return a list filled of Room.
	 * 
	 */
	private List<Room> roomsInList(List<Room> rooms) {

		for (BarbarianColor color : BarbarianColor.values()) { // Four times.

			for (WeaponType weapon : WeaponType.values()) { // Four times.

				rooms.add(new Room(RoomType.BLORK, weapon, null, true));
			}

			rooms.add(new Room(RoomType.PRINCESS, null, color, true));
		}

		rooms.add(new Room(RoomType.BLORK, null, null, true));
		rooms.add(new Room(RoomType.BLORK, null, null, true));
		rooms.add(new Room(RoomType.KEY, null, null, true));
		rooms.add(new Room(RoomType.KEY, null, null, true));
		rooms.add(new Room(RoomType.GATE, null, null, true));

		return rooms;
	}

	/**
	 * Add every Rooms from List of Room in the roomss array game board. Only
	 * used by private constructor.
	 * 
	 * @param rooms
	 *            A list filled of Rooms.
	 */
	private void roomsInGameBoard(List<Room> rooms) {

		int size;
		int i;

		size 	= Dungeon.DUNGEON_SIDE_SIZE;
		i 		= 0;

		for (Room room : rooms) {

			this.roomss[(i / size)][(i % size)] = room;
			i = i + 1;
		}
	}

	/**
	 * 
	 * @param position
	 *            a Dungeon's position.
	 * 
	 * @return What is inside the room.
	 * 
	 */
	public Room getRoom(DungeonPosition position) {

		return instance.roomss[position.getRow()][position.getColumn()];
	}

	/**
	 * Set a Room as visited.
	 * 
	 * @param position
	 *            a Dungeon's position.
	 */
	public void show(DungeonPosition position) {

		instance.getRoom(position).setHidden(false);
	}

	/**
	 * Set all Rooms as hidden.
	 */
	public void hideAll() {

		int sideS;

		sideS = Dungeon.DUNGEON_SIDE_SIZE;

		for (int i = 0; i < (sideS * sideS); i++) {

			instance.roomss[(i / sideS)][(i % sideS)].setHidden(true);
		}
	}

	/**
	 * 
	 * Swap two position by changing there positional attributes.
	 * 
	 * @param posBlork
	 *            The first position in the Dungeon.
	 * @param positionTwo
	 *            The second position in the Dungeon.
	 * @throws GameOverException
	 *             If you swap your own position. If your first parameter is not
	 *             a INVICIBLE BLORK.
	 */
	public void swap(DungeonPosition posBlork, DungeonPosition positionTwo)
		throws GameOverException {

			swapExceptions(posBlork, positionTwo);

			Room swapRoom = this.getRoom(posBlork);

			instance.roomss[posBlork.getRow()][posBlork.getColumn()] = this
				.getRoom(positionTwo);

			instance.roomss[positionTwo.getRow()][positionTwo.getColumn()] = swapRoom;
		}

	/**
	 * 
	 * This method throws GameOverException for Swap method.
	 * 
	 * @param posBlork
	 *            The position of the INVICIBLE BLORK.
	 * 
	 * @param posTwo
	 *            An another position in the Dungeon.
	 * 
	 * @throws GameOverException
	 *             If you swap your own position. If your first parameter is not
	 *             a INVICIBLE BLORK.
	 * 
	 */
	private void swapExceptions(DungeonPosition posBlork, DungeonPosition posTwo)
		throws GameOverException {

			// Swap is not possible with the same DungeonPosition.
			if ((posBlork.getRow() == posTwo.getRow())
					&& (posBlork.getColumn() == posTwo.getColumn())) {

				throw new GameOverException("You have to swap with an another"
						+ " position in the Dungeon.");
			}

			// If it is not a BLORK, else if is not INVICIBLE.
			if ((this.getRoom(posBlork).getType() != RoomType.BLORK)
					|| (this.getRoom(posBlork).getWeapon() != null)) {

				throw new GameOverException("You can not swap anything other"
						+ " than a INVICIBLE BLORK.");
			}
		}

	/**
	 * 
	 * This method test if you can move one more time or if you are surrounded.
	 * 
	 * @param pos
	 *            A position in the Dungeon.
	 * 
	 * @return <code>True</code> if the Room at this position is surrounded by
	 *         visited Rooms.
	 * 
	 * @throws GameOverException
	 *             If a created DungeonPosition is out of the Dungeon.
	 * 
	 */
	public boolean isSurrounded(DungeonPosition pos) throws GameOverException {

		boolean surrounded;
		List<DungeonPosition> adj;

		surrounded = true;
		adj = new ArrayList<DungeonPosition>();

		adj = this.isSurroundedLR(adj, pos);
		adj = this.isSurroundedUD(adj, pos);
		surrounded = this.isSurroundedTest(adj, surrounded);

		return surrounded;
	}

	/**
	 * 
	 * This method tests position you had to test.
	 * 
	 * @param adj
	 *            A list of DungeonPosition we need to test.
	 * 
	 * @param surrounded
	 *            <code>True</code> initialized boolean.
	 * 
	 * @return <code>True</code> If all DungeonPosition, from list, are visited.
	 * 
	 */
	private boolean isSurroundedTest(List<DungeonPosition> adj,
			boolean surrounded) {

		int cpt;

		cpt = 0;

		while ((surrounded) && (cpt < adj.size())) {

			surrounded = !(this.getRoom(adj.get(cpt)).isHidden());
			cpt = cpt + 1;
		}

		return surrounded;
	}

	/**
	 * 
	 * This method adds left and right DungeonPosition you have to tests for
	 * isSurrounded.
	 * 
	 * @param adj
	 *            A list of DungeonPosition we need to test.
	 * 
	 * @param pos
	 *            The Player's current position.
	 * 
	 * @return A list with the adjacent positions.
	 * 
	 * @throws GameOverException
	 *             If DungeonPosition created out of Dungeon.
	 * 
	 */
	private List<DungeonPosition> isSurroundedLR(List<DungeonPosition> adj,
			DungeonPosition pos) throws GameOverException {

		if (pos.getColumn() < 4) { // RIGHT

			adj.add(new DungeonPosition(pos.getRow(), pos.getColumn() + 1));
		}

		if (pos.getColumn() > 0) { // LEFT

			adj.add(new DungeonPosition(pos.getRow(), pos.getColumn() - 1));
		}

		return adj;
	}

	/**
	 * 
	 * This method adds up and down DungeonPosition you have to tests for
	 * isSurrounded.
	 * 
	 * @param adj
	 *            A list of DungeonPosition we need to test.
	 * 
	 * @param pos
	 *            The Player's current position.
	 * 
	 * @return A list of DungeonPosition with new DungeonPosition to test.
	 * 
	 * @throws GameOverException
	 *             If DungeonPosition created out of Dungeon.
	 * 
	 */
	private List<DungeonPosition> isSurroundedUD(List<DungeonPosition> adj,
			DungeonPosition pos) throws GameOverException {

		switch (pos.getRow()) {

			case 0: // Rooms of the top.

				adj.add(new DungeonPosition(pos.getRow() + 1, pos.getColumn()));// Down.
				break;

			case 4: // Rooms of the bottom.

				adj.add(new DungeonPosition(pos.getRow() - 1, pos.getColumn()));// Up
				break;

			default: // Between top and bottom.

				adj.add(new DungeonPosition(pos.getRow() - 1, pos.getColumn()));// Up
				adj.add(new DungeonPosition(pos.getRow() + 1, pos.getColumn()));// Down
				break;
		}

		return adj;
	}

	/**
	 * 
	 * Make sure that there is no instance of the Dungeon. Package visibility
	 * only used by JUnit test.
	 * 
	 */
	void setInstance() {

		instance = null;
	}
}
