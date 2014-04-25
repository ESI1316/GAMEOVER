package g39631.gameover.view;

import static org.junit.Assert.*;
import g39631.gameover.model.GameOverException;

import org.junit.Test;

/**
 * JUnit test class - GameFile
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 * @version 2.00
 * 
 */
public class GameFileTest {

	@Test
		public void fileToArray_pathCreation_unreadable_01()
		throws GameOverException {

			try {

				GameFile.fileToArray
					("/home/g39631/Project/V2GameOver/Files/unreadablePlayers.txt");
			} catch (GameOverException ex) {

				assertTrue(ex.getMessage().equals("Players file unreadable."));
			}
		}

	@Test
		public void fileToArray_pathCreation_02() throws GameOverException {

			try {

				GameFile.fileToArray("Je ne suis pas là.");
			} catch (GameOverException ex) {

				assertTrue(ex.getMessage().equals("Players file not found."));
			}
		}

	@Test
		public void fileToArray_pathCreation_03() throws GameOverException {

			try {

				GameFile.fileToArray
					("/home/g39631/Project/V2GameOver/Files/directoryPlayers.txt/");
			} catch (GameOverException ex) {

				assertTrue(ex.getMessage().equals("Players file is a directory."));
			}

		}

	@Test
		public void fileToArray_pathCreation_04() throws GameOverException {

			try {

				GameFile.fileToArray(); // Not any file path.
			} catch (GameOverException ex) {

				assertTrue(ex.getMessage().equals("There is not any file path."));
			}
		}

	@Test
		public void fileToArray_players_01() throws GameOverException {

			try {

				GameFile.fileToArray("/home/g39631/Project/V2GameOver/Files/0player.txt");
			} catch (GameOverException ex) {

				assertTrue(ex.getMessage().equals("At least two players required."));
			}
		}

	@Test
		public void fileToArray_players_02() throws GameOverException {

			try {

				GameFile.fileToArray("/home/g39631/Project/V2GameOver/Files/1player.txt"); 
			} catch (GameOverException ex) {

				System.out.println(ex.getMessage());
				assertTrue(ex.getMessage().equals("At least two players required."));
			}
		}

	@Test
		public void fileToArray_players_03() throws GameOverException {

			assertTrue(GameFile.fileToArray
					("/home/g39631/Project/V2GameOver/Files/2players.txt").length != 0);
		}

	@Test
		public void fileToArray_players_04() throws GameOverException {

			assertTrue(GameFile.
					fileToArray("/home/g39631/Project/V2GameOver/Files/3players.txt").length != 0); 
		}

	@Test
		public void fileToArray_players_05() throws GameOverException {

			assertTrue(GameFile.
					fileToArray("/home/g39631/Project/V2GameOver/Files/4players.txt").length != 0);
		}

	@Test
		public void fileToArray_players_06() throws GameOverException {

			try {

				GameFile.fileToArray("/home/g39631/Project/V2GameOver/Files/5players.txt"); 
			} catch (GameOverException ex) {

				assertTrue(ex.getMessage().equals("Up to 4 players."));
			}
		} 
}
