package g39631.gameover.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import g39631.gameover.model.GameOverException;

/**
 * This class is a player's file reader utility only made 
 * to create the game "GAMEOVER". It reads the file line by line 
 * and fills a string of them. This string will fill an array containing 
 * player's names and status for beginner players concerned.
 * 
 * @version 2.00
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 */
public class GameFile {

	private Path path;
	private BufferedReader input;
	private String[] names;
	
	/**
	 * 
	 */
	GameFile() {
		
		this.path = null;
		this.input = null;
		this.names = null;
	}
	/**
	 * 
	 * Read player's file and create an array made of every string inside. Turns
	 * an array to a string. Then every word, split by whitespace, will become
	 * an array cell (name or state).
	 * 
	 * @param args
	 *            An array of strings with file path.
	 * 
	 * @return an array filled of names by the file.
	 * 
	 * @throws GameOverException
	 *             If there is any IOException. 
	 *             If there is no path. 
	 *             If file does not exist. 
	 *             If file is not readable. 
	 *             If file is a directory. 
	 *             If there is more than 4 players. 
	 *             If there is less than 2 players.
	 * 
	 */
	public String[] fileToArray(String... args) throws GameOverException {

		this.pathCreation(args);
		this.fileOpen();
		this.fileRead();
		this.fileClose();

		return this.names;
	}

	/**
	 * 
	 * Create a new Path made of a String representation of path.
	 * 
	 * @param args
	 *            A varargs of String with file path.
	 * 
	 * @return A quite usable path.
	 * 
	 * @throws GameOverException
	 *             If there is no path. If file does not exist. If file is not
	 *             readable. If file is a directory.
	 * 
	 */
	private void pathCreation(String... args) throws GameOverException {

		if (args.length < 1) {

			throw new GameOverException("There is not any file path.");
		}

		this.path = Paths.get(args[0]);
		this.pathExceptions();
	}

	/**
	 * 
	 * Throws Path exceptions.
	 * 
	 * @throws GameOverException
	 *             If there is no path. If file does not exist. If file is not
	 *             readable. If file is a directory.
	 * 
	 */
	private void pathExceptions() throws GameOverException {

		if (!(Files.exists(this.path))) {

			throw new GameOverException("Players file not found.");
		}

		if (!(Files.isReadable(this.path))) {

			throw new GameOverException("Players file unreadable.");
		}

		if (Files.isDirectory(this.path)) {

			throw new GameOverException("Players file is a directory.");
		}
	}

	/**
	 * 
	 * Open player's file read-only.
	 * 
	 * @return A BufferedReader ready to read file.
	 * 
	 * @throws GameOverException
	 *             If there is any IOException.
	 * 
	 */
	private void fileOpen() throws GameOverException {

		try {

			this.input = Files.newBufferedReader(this.path,
					java.nio.charset.StandardCharsets.UTF_8);
		} catch (IOException e) {

			throw new GameOverException(e.getMessage());
		}
	}

	/**
	 * 
	 * Read all the file and write it in a String array. It is made of names and
	 * beginner state if a player is.
	 * 
	 * @return Player's name &amp; state.
	 * 
	 * @throws GameOverException
	 *             If there is more than 4 players. 
	 *             If there is less than 2 players. 
	 *             If there is any IOException while closing file.
	 * 
	 */
	private void fileRead() throws GameOverException {

			String names 	= null;
			String line 	= null;
			int length 		= 0;

			line = this.lineRead(line);
			while ((line != null) && (length < 4)) {

				names = (names==null)
						?line
						:names + " " + line;

				length++;
				line = this.lineRead(line);
			}

			this.lengthError(length, line);
			this.names =  names.split(" ");
		}

	/**
	 * 
	 * Throws "number of players" exception &amp; close the file.
	 * 
	 * @param length
	 *            Number of read line.
	 * 
	 * @param line
	 *            The last read line. 
	 * 
	 * @throws GameOverException
	 *             If there is more than 4 players. 
	 *             If there is less than 2 players. 
	 *             If there is any IOException while closing file.
	 * 
	 */
	private void lengthError(int length, String line) throws GameOverException {

		if (length < 2) {

			this.fileClose();
			throw new GameOverException("At least two players required.");
		}

		if ((length > 3) && (line != null)) {

			this.fileClose();
			throw new GameOverException("Up to 4 players.");
		}
	}

	/**
	 * 
	 * Reads a line of player's file and throws reading exception.
	 * 
	 * @param line
	 *            The last read line.
	 * 
	 * @return Every characters of read line in a string.
	 * 
	 * @throws GameOverException
	 *             If there is any IOException.
	 * 
	 */
	private String lineRead(String line) throws GameOverException {

			try {

				line = this.input.readLine();
			} catch (IOException Iex) {

				this.fileClose();
				throw new GameOverException(Iex.getMessage());
			}

			return line;
		}

	/**
	 * 
	 * Closes a opened player's file.
	 * 
	 * @throws GameOverException
	 *             If there is any IOException.
	 * 
	 */
	private void fileClose() throws GameOverException {

			try {

				this.input.close();
			} catch (IOException e) {

				throw new GameOverException(e.getMessage());
			}
		}
}
