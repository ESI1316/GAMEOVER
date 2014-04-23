package g39631.gameover.model;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * JUnit test class - Game
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 * @version 2.00
 * 
 */
public class GameTest {
	/*
	 * @Test(expected = GameOverException.class) public void test_constr_01()
	 * throws GameOverException {
	 * 
	 * Game newGame = new Game("Simon"); }
	 * 
	 * @Test(expected = GameOverException.class) public void test_constr_02()
	 * throws GameOverException {
	 * 
	 * Game newGame = new Game(); }
	 * 
	 * @Test(expected = GameOverException.class) public void test_constr_03()
	 * throws GameOverException {
	 * 
	 * Game newGame = new Game("Josh", "Trent", "Dave", "Louis", "Levis"); }
	 */
	@Test
		public void test_constr_04() throws GameOverException {

			Game newGame = new Game("Josh", "Trent", "Dave");

			assertNotNull(newGame);
		}

	@Test
		public void test_constr_05() throws GameOverException {

			Game newGame = new Game("Jack", "Dan");

			assertNotNull(newGame.getDungeon());
		}

	@Test
		public void test_constr_06() throws GameOverException {

			new Game("Jack", "Dan");

			assertEquals(2, Player.playersNb);
		}

	@Test
		public void test_constr_07() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						true);
			}

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Joe", "Le taxi", "dermiste");
			newGame.setDungeon(dungeon);

			boolean ok = true;
			int j = 0;

			while ((j < 25) && ok) {

				ok = (newGame.getDungeon()
						.getRoom(new DungeonPosition(j / 5, j % 5)).getType() == RoomType.KEY);

				j = j + 1;
			}

			assertTrue(ok);
		}

	@Test
		public void test_getCurrPlayer_01() throws GameOverException {

			Game newGame = new Game("Simon", "Christian", "Dominique");

			assertTrue("Simon" == newGame.getCurrentPlayer().getName());
		}

	@Test
		public void test_isOver_01() throws GameOverException {

			Game newGame = new Game("Simon", "Christian", "Dominique");

			assertFalse(newGame.isOver());
		}

	@Test(expected = GameOverException.class)
		public void test_play_01() throws GameOverException {

			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.play(Direction.UP, WeaponType.ARROWS);
		}

	@Test(expected = GameOverException.class)
		public void test_play_02() throws GameOverException {

			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.play(Direction.LEFT, WeaponType.ARROWS);
		}

	@Test(expected = GameOverException.class)
		public void test_play_03() throws GameOverException {

			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.play(Direction.RIGHT, WeaponType.ARROWS);
		}

	@Test
		public void test_play_04() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						true);
			}
			configuration[0][0] = new Room(RoomType.BLORK, WeaponType.ARROWS, null,
					true);

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			assertTrue(newGame.play(Direction.DOWN, WeaponType.ARROWS) == BarbarianState.CONTINUE);

			dungeon.setInstance();
		}

	@Test
		public void test_play_05() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.KEY, null, null,
						true);
			}

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);

			assertTrue(newGame.play(Direction.DOWN, WeaponType.ARROWS) == BarbarianState.CONTINUE);

			dungeon.setInstance();
		}

	@Test
		public void test_play_06() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.PRINCESS, null,
						null, true);
			}

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			assertTrue(newGame.play(Direction.DOWN, WeaponType.ARROWS) == BarbarianState.CONTINUE);

			dungeon.setInstance();
		}

	@Test
		public void test_play_07() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.GATE, null, null,
						true);
			}

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			assertTrue(newGame.play(Direction.DOWN, WeaponType.ARROWS) == BarbarianState.BEAM_ME_UP);

			dungeon.setInstance();
		}

	@Test
		public void test_play_08() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK, null, null,
						true);
			}

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			assertTrue(newGame.play(Direction.DOWN, WeaponType.ARROWS) == BarbarianState.MOVE_BLORK);

			dungeon.setInstance();
		}

	@Test(expected = GameOverException.class)
		public void test_play_09() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK, null, null,
						true);
			}

			// Visited room.
			configuration[0][0] = new Room(RoomType.BLORK, WeaponType.ARROWS, null,
					false);

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.ARROWS);

			dungeon.setInstance();
		}

	@Test(expected = GameOverException.class)
		public void test_isOver_02() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK, null, null,
						true);
			}

			configuration[0][0] = new Room(RoomType.KEY, null, null, true);
			configuration[1][0] = new Room(RoomType.PRINCESS, null,
					BarbarianColor.RED, true);

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.play(Direction.DOWN, WeaponType.ARROWS);

			// Throw isOver() exception at the beginning
			newGame.play(Direction.DOWN, WeaponType.ARROWS);

			dungeon.setInstance();
		}

	@Test(expected = GameOverException.class)
		public void test_isOver_03() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK, null, null,
						true);

			}

			configuration[0][0] = new Room(RoomType.PRINCESS, null,
					BarbarianColor.RED, true);
			configuration[1][0] = new Room(RoomType.KEY, null, null, true);

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.play(Direction.DOWN, WeaponType.ARROWS);

			// Throw isOver() exception at the beginning
			newGame.play(Direction.DOWN, WeaponType.ARROWS);

			dungeon.setInstance();
		}

	@Test
		public void test_getWinner_01() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK, null, null,
						true);
			}

			configuration[1][0] = new Room(RoomType.KEY, null, null, true);
			configuration[0][0] = new Room(RoomType.PRINCESS, null,
					BarbarianColor.RED, true);

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.play(Direction.DOWN, WeaponType.ARROWS);

			assertTrue(newGame.getWinner().getName() == newGame.getCurrentPlayer()
					.getName());

			dungeon.setInstance();
		}

	@Test
		public void test_getWinner_02() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK, null, null,
						true);
			}

			configuration[1][0] = new Room(RoomType.KEY, null, null, true);
			configuration[0][0] = new Room(RoomType.PRINCESS, null,
					BarbarianColor.RED, true);

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.play(Direction.DOWN, WeaponType.ARROWS);

			assertFalse(newGame.getWinner() == null);

			dungeon.setInstance();
		}

	@Test
		public void test_getWinner_03() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK, null, null,
						true);
			}

			configuration[1][0] = new Room(RoomType.KEY, null, null, true);
			configuration[0][0] = new Room(RoomType.PRINCESS, null,
					BarbarianColor.RED, true);

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.ARROWS);

			assertTrue(newGame.getWinner() == null);

			dungeon.setInstance();
		}

	@Test
		public void test_nextPlayer_01() throws GameOverException {

			Game newGame = new Game("Salvina", "Deborou", "Antonou");

			newGame.nextPlayer();

			assertTrue(newGame.getCurrentPlayer().getName() == "Deborou");
		}

	@Test
		public void test_nextPlayer_02() throws GameOverException {

			Game newGame = new Game("Salvina", "Deborou", "Antonou");

			newGame.nextPlayer();
			newGame.nextPlayer();

			assertTrue(newGame.getCurrentPlayer().getName() == "Antonou");
		}

	@Test
		public void test_nextPlayer_03() throws GameOverException {

			Game newGame = new Game("Salvina", "Deborou", "Antonou");

			newGame.nextPlayer();
			newGame.nextPlayer();
			newGame.nextPlayer();

			assertTrue(newGame.getCurrentPlayer().getName() == "Salvina");
		}

	@Test
		public void test_nextPlayer_04() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK, null, null,
						true);
			}

			// Visited room.
			configuration[4][4] = new Room(RoomType.BLORK, WeaponType.ARROWS, null,
					true);

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			newGame.nextPlayer();

			assertTrue(newGame.play(Direction.UP, WeaponType.ARROWS) == BarbarianState.CONTINUE);

			dungeon.setInstance();
		}

	@Test
		public void test_nextPlayer_05() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK, null, null,
						true);
			}

			// Visited room.
			configuration[4][0] = new Room(RoomType.BLORK, WeaponType.ARROWS, null,
					true);

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			newGame.nextPlayer();
			newGame.nextPlayer();

			assertTrue(newGame.play(Direction.RIGHT, WeaponType.ARROWS) == BarbarianState.CONTINUE);

			dungeon.setInstance();
		}

	@Test
		public void test_nextPlayer_06() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK, null, null,
						true);
			}

			// Visited room.
			configuration[0][4] = new Room(RoomType.BLORK, WeaponType.ARROWS, null,
					true);

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou", "Simon");
			newGame.setDungeon(dungeon);
			
			newGame.nextPlayer();
			newGame.nextPlayer();
			newGame.nextPlayer();

			assertTrue(newGame.play(Direction.LEFT, WeaponType.ARROWS) == BarbarianState.CONTINUE);

			dungeon.setInstance();
		}

	@Test(expected = GameOverException.class)
		public void test_play_10() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK, null, null,
						true);
			}

			configuration[0][0] = new Room(RoomType.BLORK, WeaponType.ARROWS, null,
					true);
			configuration[1][0] = new Room(RoomType.BLORK, WeaponType.ARROWS, null,
					true);

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.play(Direction.UP, WeaponType.ARROWS);

			dungeon.setInstance();
		}

	@Test(expected = GameOverException.class)
		public void test_play_11() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK,
						WeaponType.ARROWS, null, true);
			}

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			newGame.nextPlayer();

			newGame.play(Direction.UP, WeaponType.ARROWS);
			newGame.play(Direction.LEFT, WeaponType.ARROWS);
			newGame.play(Direction.RIGHT, WeaponType.ARROWS);

			dungeon.setInstance();
		}

	@Test(expected = GameOverException.class)
		public void test_play_12() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK,
						WeaponType.ARROWS, null, true);
			}

			configuration[0][0] = new Room(RoomType.PRINCESS, null,
					BarbarianColor.RED, true);
			configuration[1][0] = new Room(RoomType.BLORK, WeaponType.ARROWS, null,
					true);
			configuration[2][0] = new Room(RoomType.BLORK, WeaponType.ARROWS, null,
					true);
			configuration[2][1] = new Room(RoomType.PRINCESS, null,
					BarbarianColor.GREEN, true);
			configuration[2][2] = new Room(RoomType.BLORK, WeaponType.POTION, null,
					true);
			configuration[1][2] = new Room(RoomType.KEY, null, null, true);

			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Salvina", "Deborou", "Antonou");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.play(Direction.RIGHT, WeaponType.ARROWS);
			newGame.play(Direction.RIGHT, WeaponType.POTION);
			newGame.play(Direction.UP, WeaponType.POTION);

			// Throws GameOverException because of isOver()
			newGame.play(Direction.UP, WeaponType.POTION);

			dungeon.setInstance();
		}

	@Test(expected = GameOverException.class)
		public void playBlorkInvincible_test_01() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK,
						WeaponType.ARROWS, null, true);
			}

			configuration[0][0] = new Room(RoomType.BLORK, WeaponType.POTION, null,
					true);
			configuration[1][0] = new Room(RoomType.BLORK, null, null, true);
			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Pierre", "Paul", "Jacques");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.POTION);
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.playBlorkInvincible(new DungeonPosition(4, 4));
			dungeon.setInstance();
		}

	@Test
		public void playBlorkInvincible_test_02() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK,
						WeaponType.ARROWS, null, true);
			}

			configuration[0][0] = new Room(RoomType.BLORK, WeaponType.POTION, null,
					true);
			configuration[1][0] = new Room(RoomType.BLORK, null, null, true);
			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Pierre", "Paul", "Jacques");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.POTION);
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.playBlorkInvincible(new DungeonPosition(3, 3));
			assertTrue(newGame.getDungeon().getRoom(new DungeonPosition(1, 0))
					.getWeapon() == WeaponType.ARROWS);
			dungeon.setInstance();
		}

	@Test(expected = GameOverException.class)
		public void playBlorkInvincible_test_03() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK,
						WeaponType.ARROWS, null, true);
			}

			configuration[0][0] = new Room(RoomType.BLORK, WeaponType.POTION, null,
					true);
			configuration[1][0] = new Room(RoomType.BLORK, null, null, true);
			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Pierre", "Paul", "Jacques");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.POTION);
			newGame.playBlorkInvincible(new DungeonPosition(3, 3));
			dungeon.setInstance();
		}

	@Test(expected = GameOverException.class)
		public void playBlorkInvincible_test_04() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK,
						WeaponType.ARROWS, null, true);
			}

			configuration[0][0] = new Room(RoomType.BLORK, WeaponType.POTION, null,
					true);
			configuration[1][0] = new Room(RoomType.BLORK, null, null, true);
			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Pierre", "Paul", "Jacques");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.POTION);
			newGame.playBlorkInvincible(new DungeonPosition(1, 0));
			dungeon.setInstance();
		}

	@Test(expected = GameOverException.class)
		public void playBlorkInvincible_test_05() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK,
						WeaponType.ARROWS, null, true);
			}

			configuration[0][0] = new Room(RoomType.BLORK, WeaponType.POTION, null,
					true);
			configuration[1][0] = new Room(RoomType.BLORK, null, null, true);
			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Pierre", "Paul", "Jacques");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.POTION);
			newGame.playBlorkInvincible(new DungeonPosition(4, 2));
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			dungeon.setInstance();
		}

	@Test
		public void playGate_test_01() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK,
						WeaponType.ARROWS, null, true);
			}

			configuration[0][0] = new Room(RoomType.GATE, null, null, true);
			configuration[4][4] = new Room(RoomType.BLORK, WeaponType.POTION, null,
					true);
			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Pierre", "Paul", "Jacques");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.playGate(new DungeonPosition(3, 4), WeaponType.ARROWS);

			assertTrue((newGame.play(Direction.DOWN, WeaponType.POTION) == BarbarianState.CONTINUE)
					&& (newGame.getCurrentPlayer().getName() == "Pierre"));

			dungeon.setInstance();
		}

	@Test
		public void playGate_test_02() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK,
						WeaponType.ARROWS, null, true);
			}
			configuration[0][0] = new Room(RoomType.GATE, null, null, true);
			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Jean", "Jacques", "Goldman", "Afou");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			assertTrue(newGame.playGate(new DungeonPosition(3, 4),
						WeaponType.ARROWS) == BarbarianState.CONTINUE);

			dungeon.setInstance();
		}

	@Test
		public void playGate_test_03() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK,
						WeaponType.ARROWS, null, true);
			}
			configuration[0][0] = new Room(RoomType.PRINCESS, null,
					BarbarianColor.RED, true);
			configuration[1][0] = new Room(RoomType.GATE, null, null, true);
			configuration[3][2] = new Room(RoomType.KEY, null, null, true);
			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Jean", "Jacques", "Goldman", "Afou");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			assertTrue(newGame.playGate(new DungeonPosition(3, 2),
						WeaponType.ARROWS) == BarbarianState.WIN);

			dungeon.setInstance();
		}

	@Test(expected = GameOverException.class)
		public void playGate_test_04() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK,
						WeaponType.ARROWS, null, true);
			}
			configuration[0][0] = new Room(RoomType.GATE, null, null, true);
			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("Jean", "Jacques", "Goldman", "Afou");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.playGate(new DungeonPosition(0, 0), WeaponType.ARROWS);

			dungeon.setInstance();
		}

	@Test(expected = GameOverException.class)
		public void playGate_test_05() throws GameOverException {

			Room[][] configuration = new Room[5][5];

			for (int i = 0; i < 25; i++) {

				configuration[i / 5][i % 5] = new Room(RoomType.BLORK,
						WeaponType.ARROWS, null, true);
			}
			configuration[0][0] = new Room(RoomType.GATE, null, null, true);
			configuration[3][0] = new Room(RoomType.GATE, null, null, false);
			Dungeon dungeon = new Dungeon(configuration);
			Game newGame = new Game("David", "Roger", "Nick", "Richard");
			newGame.setDungeon(dungeon);
			
			newGame.play(Direction.DOWN, WeaponType.ARROWS);
			newGame.playGate(new DungeonPosition(3, 0), WeaponType.ARROWS);

			dungeon.setInstance();
		}
}
