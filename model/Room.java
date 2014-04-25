package g39631.gameover.model;

import java.util.Objects;

/**
 * This class create a room of the dungeon and puts all the elements 
 * that compose it.
 * It is closed and impossible to know what there is inside. After a visit, 
 * a Barbarian knows what did he find inside.
 * 
 * @version 2.00
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 */
public class Room {

	private RoomType type;
	private WeaponType weapon;
	private BarbarianColor color;
	private boolean hidden;

	/**
	 * Create a new room.
	 * 
	 * @param type
	 *            What we can find in this room.
	 * @param weapon
	 *            If a BLORK is inside, which WEAPON can kill it.
	 * @param color
	 *            If a PRINCESS is inside, which BARBARIAN COLOR can save her.
	 * @param hidden
	 *            Usually <code>false</code> because nobody has ever been there.
	 */
	Room(RoomType type, WeaponType weapon, BarbarianColor color, boolean hidden) {

		this.type = type;
		this.weapon = weapon;
		this.color = color;
		this.hidden = hidden;
	}

	/**
	 * 
	 * @return <code>True</code> if no one has ever been inside.
	 */
	public boolean isHidden() {

		return this.hidden;
	}

	// Getters
	/**
	 * 
	 * @return What is inside.
	 */
	public RoomType getType() {

		return this.type;
	}

	/**
	 * 
	 * @return Which WEAPON is inside.
	 */
	public WeaponType getWeapon() {

		return this.weapon;
	}

	/**
	 * 
	 * @return Which BARBARIAN COLOR is inside.
	 */
	public BarbarianColor getColor() {

		return this.color;
	}

	// Setters
	/**
	 * 
	 * @param hidden
	 *            To hide or show a room.
	 */
	public void setHidden(boolean hidden) {

		this.hidden = hidden;
	}

	/**
	 * Generate an integer hashcode for this object only based on its attributes
	 * (hashCode) and should be as unique as possible. It will be use with
	 * overriding equals() (on Room class) to compare room in the dungeon.
	 * <code>hidden</code> attribute will not be used because a room is always
	 * visible to see what is inside. Overriding of the Object.hashCode().
	 * 
	 * @see java.util.Objects#hash(Object... obj)
	 * 
	 * @return A hash code value for this object.
	 */
	@Override
		public int hashCode() {

			return Objects.hash(this.color, this.type, this.weapon);
		}

	/**
	 * Compare two (supposed) rooms of the dungeon. The send Object is supposed
	 * to be a notNull &amp; a room, or a class extended from Room class. The
	 * hashCode() comparison will be applied only if conditions are
	 * <code>true</code>. Overriding of the Object.equals(Object object).
	 * 
	 * @see java.util.Objects#equals(Object obj)
	 * 
	 * @return If rooms are, exactly, the same.
	 */
	@Override
		public boolean equals(Object obj) {

			boolean equal = false;

			if (!(obj == null) && (obj instanceof Room)) {

				Room room = (Room) obj;

				equal = (Objects.equals(this.color, room.color)
						&& Objects.equals(this.type, room.type) 
						&& Objects.equals(this.weapon, room.weapon));
			}

			return equal;
		}
}
