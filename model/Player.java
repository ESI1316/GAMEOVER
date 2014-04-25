package g39631.gameover.model;

/**
 * This class creates a new Barbarian for each player. He has his own player's
 * number, color flag, initial position and name. A player can be beginner if
 * he looks weak.
 * 
 * @version 2.00
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 */
public class Player {

	static int playersNb = 0;
	private int number;
	private String name;
	private BarbarianColor color;
	private DungeonPosition initPosition;
	private boolean beginner;

	/*
	 * An array of all the start position to set the player's BARBARIAN
	 * according to its number.
	 */
	private final DungeonPosition[] POSITIONS = {
		DungeonPosition.P_BARBARIAN_1, DungeonPosition.P_BARBARIAN_2,
		DungeonPosition.P_BARBARIAN_3, DungeonPosition.P_BARBARIAN_4 };

	/**
	 * Create a new gamer tag with his own attributes created by the
	 * registration number.
	 * 
	 * @param name
	 *            Player's name.
	 * 
	 */
	public Player(String name) {

		this.number = Player.playersNb;
		this.name = name;
		this.color = BarbarianColor.values()[this.number];
		this.initPosition = POSITIONS[this.number];
		this.beginner = false;

		// Increment Players number for the next player.
		Player.playersNb++;
	}

	/**
	 * 
	 * @return Player BARBARIAN's color.
	 */
	public BarbarianColor getColor() {

		return this.color;
	}

	/**
	 * 
	 * @return Player BARBARIAN's initial position.
	 */
	public DungeonPosition getInitPosition() {

		return this.initPosition;
	}

	/**
	 * 
	 * @return Player's name.
	 */
	public String getName() {

		return this.name;
	}

	/**
	 * 
	 * @return <code>True</code> if Player is a beginner.
	 */
	public boolean isBeginner() {

		return beginner;
	}

	/**
	 * 
	 * @param beginner
	 *            If Player is a beginner.
	 */
	public void setBeginner(boolean beginner) {

		this.beginner = beginner;
	}
}
