package g39631.gameover.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test class - Dungeon
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 * @version 2.00
 * 
 */
public class DungeonTest {

	@Test
		public void test_getInstance_01() {

			Dungeon dungeon;

			dungeon = Dungeon.getInstance();

			assertNotNull(dungeon);
		}

	/*
	 * GetInstance on a second dungeon do not create once more but should be the
	 * exactly the same.
	 */
	@Test
		public void test_getInstance_02() {

			Dungeon dungeon;
			Dungeon dungeon2;

			dungeon = Dungeon.getInstance();
			dungeon2 = Dungeon.getInstance();

			assertTrue(dungeon.equals(dungeon2));
		}

	/*
	 * And it is the same for the instance address. Useless test because the
	 * attribute is Static.
	 */
	@Test
		public void test_getInstance_03() {

			Dungeon.getInstance();
			Dungeon.getInstance();

			assertTrue(Dungeon.getInstance() == Dungeon.getInstance());
		}

	@Test
		public void test_getInstance_04() {

			Dungeon.getInstance();

			assertEquals(Dungeon.DUNGEON_SIDE_SIZE,
					Dungeon.DUNGEON_SIDE_SIZE);
		}

	// Is the dungeon full of rooms ?
	@Test
		public void test_constr_01() throws GameOverException {

			int rooms = 0;
			Dungeon dungeon = Dungeon.getInstance();

			for (int i = 0; i < 25; i++) {

				DungeonPosition position = new DungeonPosition(i / 5, i % 5);

				switch (dungeon.getRoom(position).getType()) {

					case PRINCESS:
						rooms = rooms + 1;
						break;
					case GATE:
						rooms = rooms + 1;
						break;
					case KEY:
						rooms = rooms + 1;
						break;
					case BLORK:
						rooms = rooms + 1;
						break;
					default:
						break;
				}
			}

			assertEquals(25, rooms);
		}

	/*
	 * Are there 4 BLORKS && 2 INVICIBLE BLORKS && 4 PRINCESSES && 2 KEY && 1
	 * GATE ?
	 */
	@Test
		public void test_constr_02() throws GameOverException {

			int blork = 0;
			int blorkI = 0;
			int princess = 0;
			int key = 0;
			int gate = 0;
			Dungeon dungeon = Dungeon.getInstance();

			for (int i = 0; i < 25; i++) {

				DungeonPosition position = new DungeonPosition(i / 5, i % 5);

				switch (dungeon.getRoom(position).getType()) {

					case PRINCESS:
						princess = princess + 1;
						break;
					case GATE:
						gate = gate + 1;
						break;
					case KEY:
						key = key + 1;
						break;
					case BLORK:
						if (dungeon.getRoom(position).getWeapon() == null) {
							blorkI = blorkI + 1;
						} else {
							blork = blork + 1;
						}
						break;
					default:
						break;
				}
			}

			assertTrue((blork == 16) && (blorkI == 2) && (princess == 4)
					&& (key == 2) && (gate == 1));
		}

	@Test
		public void test_constr_03() throws GameOverException {

			int blorkP = 0;
			int blorkG = 0;
			int blorkA = 0;
			int blorkB = 0;
			Dungeon dungeon = Dungeon.getInstance();
			for (int i = 0; i < 25; i++) {

				DungeonPosition position = new DungeonPosition(i / 5, i % 5);

				// We do not need to test INVICIBLE BLORKS, PRINCESS, GATE & KEY
				if (!(dungeon.getRoom(position).getWeapon() == null)) {

					switch (dungeon.getRoom(position).getWeapon()) {

						case GUN:
							blorkG = blorkG + 1;
							break;
						case POTION:
							blorkP = blorkP + 1;
							break;
						case ARROWS:
							blorkA = blorkA + 1;
							break;
						case BLUDGEON:
							blorkB = blorkB + 1;
							break;
						default:
							break;
					}
				}
			}

			assertTrue((blorkP == 4) && (blorkA == 4) && (blorkB == 4)
					&& (blorkG == 4));
		}

	// Princesses color are unique.
	@Test
		public void test_constr_04() throws GameOverException {

			Dungeon dungeon = Dungeon.getInstance();
			List<BarbarianColor> colors = new ArrayList<BarbarianColor>();
			int i = 0;
			boolean ok = true;

			while ((i < 25) && ok) {

				DungeonPosition position = new DungeonPosition(i / 5, i % 5);

				// We do not need to test BLORKS, KEYS & GATE.
				if (!(dungeon.getRoom(position).getColor() == null)) {

					// If I have already seen a PRINCESS with the same color.
					ok = (!(colors.contains(dungeon.getRoom(position).getColor())));

					if (ok) {

						colors.add(dungeon.getRoom(position).getColor());
					}
				}

				i = i + 1;
			}

			assertTrue(ok);
		}

	@Test
		public void test_constr_05() throws GameOverException {

			Dungeon dungeon = Dungeon.getInstance();
			Dungeon dungeon2 = Dungeon.getInstance();

			DungeonPosition position = new DungeonPosition(1, 3);

			assertTrue(dungeon.getRoom(position).equals(dungeon2.getRoom(position)));
		}

	@Test
		public void test_constr_06() throws GameOverException {

			Dungeon dungeon = Dungeon.getInstance();

			DungeonPosition position = new DungeonPosition(4, 4);

			assertTrue(dungeon.getRoom(position).equals(dungeon.getRoom(position)));
		}

	@Test
		public void test_show_01() throws GameOverException {

			Dungeon dungeon = Dungeon.getInstance();
			DungeonPosition position = new DungeonPosition(3, 4);

			dungeon.show(position);

			assertTrue(!(dungeon.getRoom(position).isHidden()));
		}

	@Test
		public void test_show_02() throws GameOverException {

			Dungeon dungeon = Dungeon.getInstance();
			DungeonPosition position = new DungeonPosition(3, 4);

			dungeon.show(position);
			dungeon.show(position);

			assertTrue(!(dungeon.getRoom(position).isHidden()));
		}

	@Test
		public void test_hideAll_01() throws GameOverException {

			Dungeon dungeon = Dungeon.getInstance();
			DungeonPosition position = new DungeonPosition(3, 4);
			int i = 0;
			boolean ok = true;

			dungeon.show(position);
			dungeon.hideAll();

			while ((i < 25) && (ok)) {

				DungeonPosition pos = new DungeonPosition(i / 5, i % 5);

				ok = (dungeon.getRoom(pos).isHidden());
				i = i + 1;
			}

			assertTrue(ok);
		}

	@Test
		public void test_hideAll_02() throws GameOverException {

			Dungeon dungeon = Dungeon.getInstance();
			DungeonPosition position = new DungeonPosition(3, 4);
			DungeonPosition position1 = new DungeonPosition(0, 0);
			DungeonPosition position2 = new DungeonPosition(0, 2);
			int i = 0;
			boolean ok = true;

			dungeon.show(position);
			dungeon.show(position1);
			dungeon.show(position2);
			dungeon.hideAll();

			while ((i < 25) && (ok)) {

				DungeonPosition pos = new DungeonPosition(i / 5, i % 5);

				ok = (dungeon.getRoom(pos).isHidden());
				i = i + 1;
			}

			assertTrue(ok);
		}

	@Test
		public void test_constr_configurated_01() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						true);
			}

			Dungeon dungeon = new Dungeon(configuration);

			assertNotNull(dungeon);

			dungeon.setInstance();
		}

	@Test
		public void test_constr_configurated_02() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						true);
			}
			configuration[0][0] = new Room(RoomType.GATE, null, null, true);

			Dungeon dungeon = new Dungeon(configuration);

			assertTrue(dungeon.getRoom(new DungeonPosition(0, 0)).getType() == RoomType.GATE);

			dungeon.setInstance();
		}

	@Test
		public void test_swap_01() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			configuration[4][4] = new Room(RoomType.BLORK, null, null, true);
			configuration[3][0] = new Room(RoomType.KEY, null, null, true);

			Dungeon dungeon = new Dungeon(configuration);

			dungeon.swap(new DungeonPosition(4, 4), new DungeonPosition(3, 0));

			assertTrue(dungeon.getRoom(new DungeonPosition(4, 4)).getType() == (RoomType.KEY));

			dungeon.setInstance();
		}

	@Test
		public void test_swap02() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			configuration[2][0] = new Room(RoomType.BLORK, null, null, false);
			configuration[3][4] = new Room(RoomType.PRINCESS, null,
					BarbarianColor.GREEN, true);

			Dungeon dungeon = new Dungeon(configuration);

			dungeon.swap(new DungeonPosition(2, 0), new DungeonPosition(3, 4));

			assertTrue((dungeon.getRoom(new DungeonPosition(3, 4)).isHidden() == false)
					&& (dungeon.getRoom(new DungeonPosition(2, 0)).isHidden() == true));

			dungeon.setInstance();
		}

	@Test(expected = GameOverException.class)
		public void test_swap03() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			configuration[2][0] = new Room(RoomType.BLORK, null, null, false);
			configuration[3][4] = new Room(RoomType.PRINCESS, null,
					BarbarianColor.GREEN, true);

			Dungeon dungeon = new Dungeon(configuration);

			dungeon.swap(new DungeonPosition(3, 4), new DungeonPosition(2, 0));
			dungeon.setInstance();
		}

	@Test
		public void test_swap04() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			configuration[2][0] = new Room(RoomType.BLORK, null, null, false);

			Dungeon dungeon = new Dungeon(configuration);

			try {
				dungeon.swap(new DungeonPosition(2, 0), new DungeonPosition(2, 0));
				fail("I should have catch an GOException");
			} catch (GameOverException ex) {

				assertTrue(ex.getMessage().equals(
							"You have to swap with "
							+ "an another position in the Dungeon."));
			}

			dungeon.setInstance();
		}

	@Test
		public void test_isSurrounded_01() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						false);
			}

			configuration[0][0] = new Room(RoomType.KEY, null, null, true);
			Dungeon dungeon = new Dungeon(configuration);

			assertTrue(dungeon.isSurrounded(new DungeonPosition(0, 0)));

			dungeon.setInstance();
		}

	@Test
		public void test_isSurrounded_02() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						false);
			}

			configuration[0][0] = new Room(RoomType.KEY, null, null, true);
			configuration[1][0] = new Room(RoomType.KEY, null, null, true);
			Dungeon dungeon = new Dungeon(configuration);

			assertFalse(dungeon.isSurrounded(new DungeonPosition(0, 0)));

			dungeon.setInstance();
		}

	@Test
		public void test_isSurrounded_03() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						false);
			}

			configuration[0][4] = new Room(RoomType.KEY, null, null, true);
			Dungeon dungeon = new Dungeon(configuration);

			assertTrue(dungeon.isSurrounded(new DungeonPosition(0, 4)));

			dungeon.setInstance();
		}

	@Test
		public void test_isSurrounded_04() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						false);
			}

			configuration[4][0] = new Room(RoomType.KEY, null, null, true);
			Dungeon dungeon = new Dungeon(configuration);

			assertTrue(dungeon.isSurrounded(new DungeonPosition(4, 0)));

			dungeon.setInstance();
		}

	@Test
		public void test_isSurrounded_05() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						false);
			}

			Dungeon dungeon = new Dungeon(configuration);

			assertTrue(dungeon.isSurrounded(new DungeonPosition(4, 4)));

			dungeon.setInstance();
		}

	@Test
		public void test_isSurrounded_06() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						true);
			}

			configuration[3][3] = new Room(RoomType.KEY, null, null, true);
			configuration[3][4] = new Room(RoomType.KEY, null, null, false);
			Dungeon dungeon = new Dungeon(configuration);

			assertFalse(dungeon.isSurrounded(new DungeonPosition(3, 3)));

			dungeon.setInstance();
		}

	@Test
		public void test_isSurrounded_07() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						false);
			}

			Dungeon dungeon = new Dungeon(configuration);

			assertTrue(dungeon.isSurrounded(new DungeonPosition(2, 2)));

			dungeon.setInstance();
		}

	@Test
		public void test_isSurrounded_08() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						false);
			}

			Dungeon dungeon = new Dungeon(configuration);

			assertTrue(dungeon.isSurrounded(new DungeonPosition(4, 0)));

			dungeon.setInstance();
		}

	@Test
		public void test_isSurrounded_09() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						false);
			}

			Dungeon dungeon = new Dungeon(configuration);

			assertTrue(dungeon.isSurrounded(new DungeonPosition(4, 4)));

			dungeon.setInstance();
		}
}
