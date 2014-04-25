package g39631.gameover.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has all game's mechanics. It run Player's actions like move
 * into Dungeon, fight a BLORK, hide a Room, use a GATE,.. 
 * 
 * @version 2.00
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 */
public class Game {

	private Dungeon dungeon;
	private List<Player> players = new ArrayList<Player>();
	private int idCurrent;
	private BarbarianState stateCurrent;
	private DungeonPosition lastPosition;
	private boolean keyFound;
	private boolean princessFound;
	private int idWinner;
	private boolean jokerUsed;

	/**
	 * Creates a new Game and subscribes players in.
	 * 
	 * @param names
	 *            Player's names array.
	 * 
	 */
	public Game(String... names) {

		/*
		 * "Wrong number of players" test are made by 
		 * g39631.gameover.view.GameFile because of number of file's lines read. 
		 * If it did not do it, an exception would be launched in 
		 * g39631.gameover.model.Game after an unknown number 
		 * of row read (for example 300).
		 */
		Player.playersNb = 0;

		this.dungeon 		= Dungeon.getInstance();
		this.playersAdd(names);
		this.idCurrent 		= 0;
		this.stateCurrent 	= BarbarianState.READY_TO_GO;
		this.lastPosition 	= players.get(idCurrent).getInitPosition();
		this.keyFound 		= false;
		this.princessFound 	= false;
		this.idWinner 		= -1;
		this.jokerUsed 		= false;
	}

	/**
	 * 
	 * Create new Players and write them in an Player arrayList. Only used by
	 * constructor.
	 * 
	 * @param names
	 *            Player's names and beginner state array.
	 * 
	 */
	private void playersAdd(String... names) {

		int i = 0;

		while (i < names.length) {

			this.players.add(new Player(names[i]));

			if ((i != (names.length - 1)) && (names[i + 1].equals("debutant"))) {

				this.players.get(this.players.size() - 1).setBeginner(true);
				i++;
			}

			i++;
		}
	}

	/**
	 * This method is the most important action for the player in this game. It
	 * makes him move, Room by Room in the Dungeon. It is used whenever you have
	 * the opportunity to walk or move again.
	 * 
	 * @param direction
	 *            The way he will go by in the Dungeon.
	 * @param weapon
	 *            The Weapon he will use to face enemies.
	 * 
	 * @return <code>CONTINUE</code> if he can play once again.
	 *         <code>GAMEOVER</code> if you loose. 
	 *         <code>BEAM_ME_UP</code> if you find a GATE. 
	 *         <code>WIN</code> if you find a KEY and your PRINCESS. 
	 *         <code>MOVE_BLORK</code> if you find a INVICIBLE BLORK.
	 * 
	 * @throws GameOverException
	 *             If there is a winner. 
	 *             If you can not move anymore. 
	 *             If he goes in a visited Room. 
	 *             (Thrown by DungeonPosition : If you are out of the Dungeon).
	 */
	public BarbarianState play(Direction direction, WeaponType weapon)
		throws GameOverException {

			this.playExceptions(direction);
			this.lastPosition = this.lastPosition.move(direction);
			this.stateCurrent = BarbarianState.CONTINUE;
			this.playTreatmentInit(weapon);
			this.hideRoom();

			return this.stateCurrent;
		}

	/**
	 * 
	 * This method throws GameOverException made in the method "play".
	 * 
	 * @param direction
	 *            The Direction you go.
	 * 
	 * @throws GameOverException
	 *             If game is over. 
	 *             If you are not READY_TO_GO or you can not CONTINUE. 
	 *             If the Room you want to go in has been already visited.
	 */
	private void playExceptions(Direction direction) throws GameOverException {

		if (this.isOver()) {

			throw new GameOverException("Game is over :"
					+ " there is a winner.");
		}

		if ((this.stateCurrent != BarbarianState.CONTINUE)
				&& (this.stateCurrent != BarbarianState.READY_TO_GO)) {

			throw new GameOverException("You can not move anymore.");
		}

		if (this.getDungeon().getRoom(this.lastPosition.move(direction))
				.isHidden() == false) {

			throw new GameOverException(
					"Room already visited, please try again.");
		}
	}

	/**
	 * 
	 * This method initializes the parameters required for treatment and sends
	 * them to this.playTreatment.
	 * 
	 * @param weapon
	 *            The Barbarian's weapon.
	 * 
	 */
	private void playTreatmentInit(WeaponType weapon) {

		RoomType 		roomType;
		WeaponType 		weaponType;
		BarbarianColor 	barbarianColor;

		roomType = this.getDungeon().getRoom(this.lastPosition).getType();
		weaponType = this.getDungeon().getRoom(this.lastPosition).getWeapon();
		barbarianColor = this.getDungeon().getRoom(this.lastPosition)
			.getColor();

		this.stateCurrent = this.playTreatment(roomType, this.stateCurrent,
				barbarianColor, weaponType, weapon);
	}

	/**
	 * 
	 * This method treats each type of room you might have.
	 * 
	 * @param roomType
	 *            What is in the Room you are actually in.
	 * @param currentState
	 *            Your current BarbarianState.
	 * @param barbarianColor
	 *            Your BARBARIAN's Color.
	 * @param weaponType
	 *            The weakness of the Room's monster.
	 * @param weapon
	 *            Your weapon.
	 * 
	 * @return Your new statement.
	 */
	private BarbarianState playTreatment(RoomType roomType,
			BarbarianState currentState, BarbarianColor barbarianColor,
			WeaponType weaponType, WeaponType weapon) {

		switch (roomType) {

			case PRINCESS:
				currentState = this.playPrincess(barbarianColor);
				break;

			case KEY:
				currentState = this.playKey();
				break;

			case GATE:
				currentState = BarbarianState.BEAM_ME_UP;
				break;

			case BLORK:
				currentState = this.playBlork(weaponType, weapon);
				break;

			default:
				break;
		}

		return currentState;
	}

	/**
	 * 
	 * When you find a PRINCESS.
	 * 
	 * @param barbarianColor
	 *            The COLOR of the PRINCESS you found.
	 * 
	 * @return <code>BarbarianState.WIN</code> if you found your PRINCESS &amp;
	 *         an already have a KEY. <code>BarbarianState.CONTINUE</code> in
	 *         other case.
	 * 
	 */
	private BarbarianState playPrincess(BarbarianColor barbarianColor) {

		BarbarianState currentState = BarbarianState.CONTINUE;

		if (barbarianColor == this.getCurrentPlayer().getColor()) {

			this.princessFound = true;

			if (this.keyFound) {

				this.setWinner();
				currentState = BarbarianState.WIN;
			}
		}

		return currentState;
	}

	/**
	 * 
	 * When you find a KEY.
	 * 
	 * @return BarbarianState.WIN if you already have your PRINCESS found.
	 *         BarbarianState.CONTINUE in other case.
	 */
	private BarbarianState playKey() {

		BarbarianState currentState = BarbarianState.CONTINUE;

		this.keyFound = true;

		if (this.princessFound) {

			this.setWinner();
			currentState = BarbarianState.WIN;
		}

		return currentState;
	}

	/**
	 * 
	 * When you are in front of a BLORK.
	 * 
	 * @param weaponType
	 *            The BLORK's wickness.
	 * @param weapon
	 *            Your weapon.
	 * 
	 * @return BarbarianState.MOVE_BLORK if BLORK is invincible.
	 *         BarbarianState_GAMEOVER if you can not kill him.
	 *         BarbarianState_CONTINUE in other case.
	 */
	private BarbarianState playBlork(WeaponType weaponType, WeaponType weapon) {

		BarbarianState currentState = BarbarianState.CONTINUE;

		if (weaponType == null) {

			currentState = BarbarianState.MOVE_BLORK;
		} else {

			if (weaponType != (weapon)) {

				if (!(this.jokerUsed)
						&& (this.players.get(idCurrent).isBeginner())) {

					currentState = BarbarianState.JOKER;
				} else {

					currentState = BarbarianState.GAMEOVER;
				}
			}
		}

		return currentState;
	}

	/**
	 * 
	 * Set all Rooms of the Dungeon as hidden.
	 * 
	 */
	private void hideRoom() {

		if (this.stateCurrent != BarbarianState.JOKER) {

			this.getDungeon().getRoom(this.lastPosition).setHidden(false);
		}
	}

	/**
	 * 
	 * When you find a GATE.
	 * 
	 * @param position
	 *            The new BarbarianPosition.
	 * 
	 * @param weapon
	 *            The Barbarian's weapon.
	 * 
	 * @return <code>CONTINUE</code> if he can play once again.
	 *         <code>GAMEOVER</code> if you loose. 
	 *         <code>BEAM_ME_UP</code> if you find a GATE. 
	 *         <code>WIN</code> if you find a KEY and your PRINCESS. 
	 *         <code>MOVE_BLORK</code> if you find a INVICIBLE BLORK.
	 * 
	 * @throws GameOverException
	 *             If the game is over. 
	 *             If player's state is not BEAM_ME_UP.
	 */
	public BarbarianState playGate(DungeonPosition position, WeaponType weapon)
		throws GameOverException {

			this.playGateExceptions(position);
			this.lastPosition = position;
			this.stateCurrent = BarbarianState.CONTINUE;
			this.playTreatmentInit(weapon);
			this.hideRoom();

			return this.stateCurrent;
		}

	/**
	 * 
	 * This method throws GameOverException made in the method "playGate".
	 * 
	 * @param position
	 *            A Dungeon's position.
	 * 
	 * @throws GameOverException
	 *             If game is over. If you do not have BEAM_ME_UP state.
	 * 
	 */
	private void playGateExceptions(DungeonPosition position)
		throws GameOverException {

			if (this.isOver()) {

				throw new GameOverException("Game is over :"
						+ " there is a winner.");
			}

			if (this.stateCurrent != BarbarianState.BEAM_ME_UP) {

				throw new GameOverException("You can not use the GATE.");
			}

			if (this.lastPosition.equals(position)) {

				throw new GameOverException("You have to go in an another Room.");
			}

			if (!(this.getDungeon().getRoom(position).isHidden())) {

				throw new GameOverException("You already visited this room.");
			}
		}

	/**
	 * 
	 * Make you try to kill a BLORK only once more.
	 * 
	 * @param weapon
	 *            The Barbarian's weapon.
	 * 
	 * @return <code>CONTINUE</code> if you can play once again.
	 *         <code>GAMEOVER</code> if you lost.
	 * 
	 * @throws GameOverException
	 *             If there is any JOKER problem.
	 * 
	 */
	public BarbarianState playJoker(WeaponType weapon) throws GameOverException {

		this.playJokerExceptions();
		this.jokerUsed 		= true;
		this.stateCurrent 	= BarbarianState.CONTINUE;
		this.getDungeon().getRoom(this.lastPosition).setHidden(false);
		this.playTreatmentInit(weapon);

		return this.stateCurrent;
	}

	/**
	 * 
	 * This method throws playJoker Exceptions.
	 * 
	 * @throws GameOverException
	 *             If the Game is over. 
	 *             If you can not use your JOKER. 
	 *             If you already have you JOKER used.
	 * 
	 */
	private void playJokerExceptions() throws GameOverException {

		if (this.isOver()) {

			throw new GameOverException("Game is over :"
					+ " there is a winner.");
		}

		if (this.stateCurrent != BarbarianState.JOKER) {

			throw new GameOverException("You can not use your JOKER.");
		}

		if (this.jokerUsed) {

			throw new GameOverException("You already have your"
					+ " JOKER used.");
		}
	}

	/**
	 * 
	 * This method specifically addresses the case of INVICIBLE BLORK.
	 * 
	 * @param pos
	 *            The new INVICIBLE BLORK's position.
	 * 
	 * @return <code>GAMEOVER</code> in every case.
	 * 
	 * @throws GameOverException
	 *             If the game is over. 
	 *             If you do not have right to move BLORK.
	 *             If you want to move INVICIBLE BLORK to a corner.
	 * 
	 */
	public BarbarianState playBlorkInvincible(DungeonPosition pos)
		throws GameOverException {

			this.playBlorkInvincibleExceptions(pos);
			this.getDungeon().swap(this.lastPosition, pos);
			this.getDungeon().show(pos); // Show swapped BLORK.

			if (!(this.jokerUsed) && (this.getCurrentPlayer().isBeginner())) {

				this.getDungeon().show(this.lastPosition); // Show swapped card.
				this.stateCurrent 	= BarbarianState.CONTINUE;
				this.jokerUsed 		= true;

			} else {

				this.stateCurrent 	= BarbarianState.GAMEOVER;
			}

			return this.stateCurrent;
		}

	/**
	 * 
	 * This method throws PlayBlorkInvicible Exceptions.
	 * 
	 * @param pos
	 *            The new INVICIBLE BLORK's position.
	 * 
	 * @throws GameOverException
	 *             If the game is over. 
	 *             If you do not have right to move BLORK.
	 *             If you want to move INVICIBLE BLORK to a corner.
	 * 
	 */
	private void playBlorkInvincibleExceptions(DungeonPosition pos)
		throws GameOverException {

			if (this.isOver()) {

				throw new GameOverException("Game is over :"
						+ " there is a winner.");
			}

			if (this.stateCurrent != BarbarianState.MOVE_BLORK) {

				throw new GameOverException("You can not move a"
						+ " INVICIBLE BLORK.");
			}

			if (pos.isCorner()) {

				throw new GameOverException("BLORK is in the corner.");
			}
		}

	/**
	 * 
	 * This methods tests if a Room of the Dungeon is surrounded by visited
	 * Room.
	 * 
	 * @param state
	 *            The current Barbarian's state.
	 * 
	 * @return <code>GAMEOVER</code> If you are surrounded. stateCurrent in
	 *         other case.
	 * 
	 * @throws GameOverException
	 *             If there is any problem while isSurrounded test.
	 * 
	 */
	public BarbarianState isSurrounded(BarbarianState state)
		throws GameOverException {

			if ((this.stateCurrent != BarbarianState.READY_TO_GO)
					&& (this.stateCurrent != BarbarianState.WIN)
					&& (this.getDungeon().isSurrounded(this.lastPosition))) {

				state = BarbarianState.GAMEOVER;
			}

			return state;
		}

	/**
	 * Set parameters as default for the next player : he will have his own id.
	 * he will begin at P_BARBARIAN_ position, he will have no KEY &amp; no
	 * PRINCESS. Every Rooms will be hidden.
	 */
	public void nextPlayer() {

		this.getDungeon().hideAll();

		this.idCurrent 		= (this.idCurrent + 1) % (this.players.size());
		this.stateCurrent 	= BarbarianState.READY_TO_GO;
		this.keyFound 		= false;
		this.princessFound 	= false;
		this.lastPosition 	= this.players.get(idCurrent).getInitPosition();
		this.jokerUsed 		= false;
	}

	/**
	 * 
	 * @return The dungeon used in this game.
	 */
	public Dungeon getDungeon() {

		return this.dungeon;
	}

	/**
	 * 
	 * @return Who is playing now.
	 */
	public Player getCurrentPlayer() {

		return players.get(this.idCurrent);
	}

	/**
	 * 
	 * @return If there is a winner.
	 */
	public boolean isOver() {

		return (this.idWinner != -1);
	}

	/**
	 * 
	 * Set the number of the winner.
	 * 
	 */
	private void setWinner() {

		this.idWinner = this.idCurrent;
	}

	/**
	 * If the game is over, send the Player who won.
	 * 
	 * @return The Player winner.
	 */
	public Player getWinner() {

		return this.isOver() ? this.players.get(idWinner) : null;
	}

	/**
	 * Set a configured Dungeon in a new Game. Only used by JUnit test.
	 * 
	 * @param dungeon
	 *            A new Dungeon.
	 */
	void setDungeon(Dungeon dungeon) {

		this.dungeon = dungeon;
	}
}
