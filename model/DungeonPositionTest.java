package g39631.gameover.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test class - DungeonPosition
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 * @version 2.00
 * 
 */
public class DungeonPositionTest {

	@Test
		public void test_const_01() throws GameOverException {

			DungeonPosition position = new DungeonPosition(0, 2);

			assertEquals(2, position.getColumn());
		}

	@Test
		public void test_const_02() throws GameOverException {

			DungeonPosition position = new DungeonPosition(0, 2);

			assertEquals(0, position.getRow());
		}

	@Test
		public void test_const_03() throws GameOverException {

			DungeonPosition position = new DungeonPosition(4, 3);

			int column = 3;
			int row = 4;

			assertTrue((column == position.getColumn())
					&& (row == position.getRow()));
		}

	@Test(expected = GameOverException.class)
		public void test_const_04() throws GameOverException {

			new DungeonPosition(-1, 0);
		}

	@Test(expected = GameOverException.class)
		public void test_const_05() throws GameOverException {

			new DungeonPosition(0, -1);
		}

	@Test(expected = GameOverException.class)
		public void test_const_06() throws GameOverException {

			new DungeonPosition(5, 0);
		}

	@Test(expected = GameOverException.class)
		public void test_const_07() throws GameOverException {

			new DungeonPosition(0, 5);
		}

	@Test(expected = GameOverException.class)
		public void test_const_08() throws GameOverException {

			new DungeonPosition(22, 6);
		}

	// Barbarian's initial position tests
	@Test
		public void test_PBARBAR_01_1() {

			assertEquals(-1, DungeonPosition.P_BARBARIAN_1.getRow());
		}

	@Test
		public void test_PBARBAR_01_2() {

			assertEquals(0, DungeonPosition.P_BARBARIAN_1.getColumn());
		}

	@Test
		public void test_PBARBAR_02_1() {

			assertEquals(5, DungeonPosition.P_BARBARIAN_2.getRow());
		}

	@Test
		public void test_PBARBAR_02_2() {

			assertEquals(4, DungeonPosition.P_BARBARIAN_2.getColumn());
		}

	@Test
		public void test_PBARBAR_03_1() {

			assertEquals(4, DungeonPosition.P_BARBARIAN_3.getRow());
		}

	@Test
		public void test_PBARBAR_03_2() {

			assertEquals(-1, DungeonPosition.P_BARBARIAN_3.getColumn());
		}

	@Test
		public void test_PBARBAR_04_1() {

			assertEquals(0, DungeonPosition.P_BARBARIAN_4.getRow());
		}

	@Test
		public void test_PBARBAR_04_2() {

			assertEquals(5, DungeonPosition.P_BARBARIAN_4.getColumn());
		}

	// move(Direction) tests
	@Test(expected = GameOverException.class)
		public void test_move_01() throws GameOverException {

			DungeonPosition position = new DungeonPosition(0, 0);
			position = position.move(Direction.UP);
		}

	@Test(expected = GameOverException.class)
		public void test_move_02() throws GameOverException {

			DungeonPosition position = new DungeonPosition(0, 0);
			position = position.move(Direction.LEFT);
		}

	@Test(expected = GameOverException.class)
		public void test_move_03() throws GameOverException {

			DungeonPosition position = new DungeonPosition(4, 4);
			position = position.move(Direction.DOWN);
		}

	@Test(expected = GameOverException.class)
		public void test_move_04() throws GameOverException {

			DungeonPosition position = new DungeonPosition(4, 4);
			position = position.move(Direction.RIGHT);
		}

	@Test
		public void test_move_05() throws GameOverException {

			DungeonPosition position = new DungeonPosition(3, 3);
			position = position.move(Direction.UP);

			assertEquals(2, position.getRow());
		}

	@Test
		public void test_move_06() throws GameOverException {

			DungeonPosition position = new DungeonPosition(3, 3);
			position = position.move(Direction.DOWN);

			assertEquals(4, position.getRow());
		}

	@Test
		public void test_move_07() throws GameOverException {

			DungeonPosition position = new DungeonPosition(3, 3);
			position = position.move(Direction.LEFT);

			assertEquals(2, position.getColumn());
		}

	@Test
		public void test_move_08() throws GameOverException {

			DungeonPosition position = new DungeonPosition(3, 3);
			position = position.move(Direction.RIGHT);

			assertEquals(4, position.getColumn());
		}

	@Test
		public void test_move_09() throws GameOverException {

			DungeonPosition position = new DungeonPosition(3, 3);
			position = position.move(Direction.RIGHT);

			assertTrue((4 == position.getColumn()) && 3 == position.getRow());
		}

	@Test
		public void test_isCorner_01() throws GameOverException {

			DungeonPosition position = new DungeonPosition(0, 0);

			assertTrue(position.isCorner());
		}

	@Test
		public void test_isCorner_02() throws GameOverException {

			DungeonPosition position = new DungeonPosition(0, 4);

			assertTrue(position.isCorner());
		}

	@Test
		public void test_isCorner_03() throws GameOverException {

			DungeonPosition position = new DungeonPosition(4, 0);

			assertTrue(position.isCorner());
		}

	@Test
		public void test_isCorner_04() throws GameOverException {

			DungeonPosition position = new DungeonPosition(4, 4);

			assertTrue(position.isCorner());
		}

	@Test
		public void test_isCorner_05() throws GameOverException {

			DungeonPosition position = new DungeonPosition(2, 3);

			assertFalse(position.isCorner());
		}

	@Test
		public void test_isCorner_06() throws GameOverException {

			DungeonPosition position = new DungeonPosition(0, 2);

			assertFalse(position.isCorner());
		}
}
