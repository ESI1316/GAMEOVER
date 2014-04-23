package g39631.gameover.view;

import static org.junit.Assert.*;
import g39631.gameover.model.GameOverException;

import org.junit.Test;

public class GameFileTest {

	@Test
	public void fileToArray_pathCreation_unreadable_01()
			throws GameOverException {

		try {
			GameFile.fileToArray("/Users/michelejosse/Documents/workspace/GameOver/File/unreadable.txt"); // unreadable.
		} catch (GameOverException ex) {

			assertTrue(ex.getMessage().equals("Players file unreadable."));
		}
	}

	@Test
	public void fileToArray_pathCreation_02() throws GameOverException {

		try {
			GameFile.fileToArray("Je ne suis pas là."); // Players file not
														// found.
		} catch (GameOverException ex) {

			assertTrue(ex.getMessage().equals("Players file not found."));
		}
	}

	@Test
	public void fileToArray_pathCreation_03() throws GameOverException {

		try {
			GameFile.fileToArray("/Users/michelejosse/Documents/workspace/GameOver/File/playerDir.txt"); // Directory.
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
			GameFile.fileToArray("/Users/michelejosse/Documents/workspace/GameOver/File/0player.txt");
		} catch (GameOverException ex) {

			assertTrue(ex.getMessage().equals("At least two players required."));
		}
	}

	@Test
	public void fileToArray_players_02() throws GameOverException {

		try {
			
			GameFile.fileToArray("/Users/michelejosse/Documents/workspace/GameOver/File/1player.txt"); 
		} catch (GameOverException ex) {

			assertTrue(ex.getMessage().equals("At least two players required."));
		}
	}

	@Test
	public void fileToArray_players_03() throws GameOverException {

		assertTrue(GameFile.fileToArray("/Users/michelejosse/Documents/"
				+ "workspace/GameOver/File/2players.txt").length != 0); // OK
	}

	@Test
	public void fileToArray_players_04() throws GameOverException {

		assertTrue(GameFile.fileToArray("/Users/michelejosse/Documents/"
				+ "workspace/GameOver/File/3players.txt").length != 0); // OK
	}

	@Test
	public void fileToArray_players_05() throws GameOverException {

		assertTrue(GameFile.fileToArray("/Users/michelejosse/Documents/"
				+ "workspace/GameOver/File/4players.txt").length != 0); // OK
	}

	@Test
	public void fileToArray_players_06() throws GameOverException {

		try {
			GameFile.fileToArray("/Users/michelejosse/Documents/workspace/GameOver/File/5players.txt"); // Up to 4
																// players.
		} catch (GameOverException ex) {

			assertTrue(ex.getMessage().equals("Up to 4 players."));
		}
	}
}
