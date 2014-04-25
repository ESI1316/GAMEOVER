package g39631.gameover.model;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * JUnit test class - Player
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 * @version 2.00
 * 
 */
public class PlayerTest {

	@Test
		public void test_playersNb_01() {

			Player.playersNb = 0;
			new Player("David");
			Player player2 = new Player("Gloire");
			player2.setBeginner(false);
			Player player3 = new Player("Espoir");
			player3.setBeginner(true);
			new Player("Adolf");

			assertEquals(4, Player.playersNb);

			// Absolutely needed to do not fail other tests.
			Player.playersNb = 0;
		}

	@Test
		public void test_playersNb_02() {

			Player.playersNb = 0;
			new Player("David");
			new Player("Gloire");

			assertEquals(2, Player.playersNb);

			// Absolutely needed to do not fail other tests.
			Player.playersNb = 0;
		}

	@Test
		public void test_playersNb_03() {

			Player.playersNb = 0;
			new Player("David");
			new Player("Gloire");
			new Player("Adolf");

			assertEquals(3, Player.playersNb);

			// Absolutely needed to do not fail other tests.
			Player.playersNb = 0;
		}

	@Test
		public void test_playersNb_04() {

			Player.playersNb = 0;
			new Player("David");

			assertEquals(1, Player.playersNb);

			// Absolutely needed to do not fail other tests.
			Player.playersNb = 0;
		}

	// Init. position test
	@Test
		public void test_constr_01() {

			Player.playersNb = 0;
			Player player = new Player("Simon");

			assertEquals(DungeonPosition.P_BARBARIAN_1,player.getInitPosition());
		}

	@Test
		public void test_constr_02() {

			Player.playersNb = 0;
			new Player("Simon");
			Player player2 = new Player("Johnny");

			assertEquals(DungeonPosition.P_BARBARIAN_2,
					player2.getInitPosition());
		}

	@Test
		public void test_constr_03() {

			Player.playersNb = 0;
			new Player("Simon");
			new Player("Johnny");
			Player player3 = new Player("Jason");

			assertEquals(DungeonPosition.P_BARBARIAN_3, 
					player3.getInitPosition());
		}

	@Test
		public void test_constr_04() {

			Player.playersNb = 0;
			new Player("Simon");
			new Player("Johnny");
			new Player("Jason");
			Player player4 = new Player("Jean-Charles de La Rondelle");

			assertEquals(DungeonPosition.P_BARBARIAN_4, 
					player4.getInitPosition());
		}

	// Color test
	@Test
		public void test_constr_05() {

			Player.playersNb = 0;

			Player player = new Player("Olive");

			assertEquals(BarbarianColor.RED, player.getColor());
		}

	@Test
		public void test_constr_06() {

			Player.playersNb = 0;
			new Player("Olive");
			Player player2 = new Player("Tom");

			assertEquals(BarbarianColor.GREEN, player2.getColor());
		}

	@Test
		public void test_constr_07() {

			Player.playersNb = 0;
			new Player("Olive");
			new Player("Tom");
			Player player3 = new Player("Olive");

			assertEquals(BarbarianColor.BLUE, player3.getColor());
		}

	@Test
		public void test_constr_08() {

			Player.playersNb = 0;
			new Player("Olive");
			new Player("Tom");
			new Player("Olive");
			Player player4 = new Player("Olive");

			assertEquals(BarbarianColor.YELLOW, player4.getColor());
		}

}
