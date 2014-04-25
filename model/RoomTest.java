package g39631.gameover.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit test class - Room
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 * @version 2.00
 * 
 */
public class RoomTest {

	@Test
		public void test_constr_01() {

			Room room = new Room(RoomType.BLORK, WeaponType.GUN, null, false);

			assertTrue(room.getType() == RoomType.BLORK
					&& room.getWeapon() == WeaponType.GUN
					&& room.getColor() == null && room.isHidden() == false);
		}

	@Test
		public void test_constr_02() {

			Room room = new Room(RoomType.BLORK, null, null, false);

			assertTrue(room.getType() == RoomType.BLORK && room.getWeapon()==null
					&& room.getColor() == null && room.isHidden() == false);
		}

	@Test
		public void test_constr_03() {

			Room room = new Room(RoomType.PRINCESS,null,BarbarianColor.RED,
					false);

			assertTrue(room.getType() == RoomType.PRINCESS
					&& room.getWeapon() == null
					&& room.getColor() == BarbarianColor.RED
					&& room.isHidden() == false);
		}

	@Test
		public void test_constr_04() {

			Room room = new Room(RoomType.GATE, null, null, false);

			assertTrue(room.getType() == RoomType.GATE && room.getWeapon()==null
					&& room.getColor() == null && room.isHidden() == false);
		}

	@Test
		public void test_constr_05() {

			Room room = new Room(RoomType.KEY, null, null, false);

			assertTrue(room.getType() == RoomType.KEY && room.getWeapon() == null
					&& room.getColor() == null && room.isHidden() == false);
		}

	@Test
		public void test_setHidden_01() {

			Room room = new Room(RoomType.KEY, null, null, false);

			room.setHidden(true);

			assertTrue(room.isHidden());
		}

	@Test
		public void test_setHidden_02() {

			Room room = new Room(RoomType.KEY, null, null, false);

			assertFalse(room.isHidden());
		}

	// Hashcode test to compare rooms with Equals()
	// Hidden is set as 'false' but even if 'true' is
	// comparison can not be done with an hidden room.
	@Test
		public void test_hashCode_01() {

			Room room = new Room(RoomType.KEY, null, null, false);
			Room room2 = new Room(RoomType.KEY, null, null, false);

			assertTrue(room.hashCode() == room2.hashCode());
		}

	@Test
		public void test_hashCode_02() {

			Room room = new Room(RoomType.BLORK, WeaponType.POTION, null, false);
			Room room2 = new Room(RoomType.BLORK, WeaponType.POTION, null,false);

			assertEquals(room.hashCode(), room2.hashCode());
		}

	@Test
		public void test_hashCode_03() {

			Room room = new Room(RoomType.BLORK, WeaponType.BLUDGEON,null,false);
			Room room2 = new Room(RoomType.PRINCESS, null, BarbarianColor.GREEN,
					false);

			assertFalse(room.hashCode() == room2.hashCode());
		}

	// Compare two object supposed to be rooms.
	@Test
		public void test_equals_01() throws GameOverException {

			Room room = new Room(RoomType.BLORK, null, null, false);
			DungeonPosition room2 = new DungeonPosition(1, 4);

			assertFalse(room.equals(room2));
		}

	@Test
		public void test_equals_02() {

			Room room = new Room(RoomType.BLORK, null, null, false);
			Room room2 = null;

			assertFalse(room.equals(room2));
		}

	@Test
		public void test_equals_03() {

			Room room = new Room(RoomType.PRINCESS, null, BarbarianColor.GREEN,
					true);
			Room room2 = new Room(RoomType.PRINCESS, null, BarbarianColor.GREEN,
					true);

			assertTrue(room2.equals(room));
		}

	@Test
		public void test_equals_04() {

			Room room = new Room(RoomType.PRINCESS,null,BarbarianColor.RED,true);
			Room room2 = new Room(RoomType.PRINCESS, null, BarbarianColor.GREEN,
					true);

			assertFalse(room.equals(room2));
		}

	@Test
		public void test_equals_05() {

			Room room = new Room(RoomType.PRINCESS, null, BarbarianColor.GREEN,
					true);
			Room room2 = room;

			assertTrue(room2.equals(room));
		}

	@Test
		public void test_equals_06() {

			Room room = new Room(RoomType.PRINCESS, null, BarbarianColor.GREEN,
					true);

			assertTrue(room.equals(room));
		}
}
