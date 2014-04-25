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
     *             If there is any IOException. If there is no path. If file
     *             does not exist. If file is not readable. If file is a
     *             directory. If there is more than 4 players. If there is less
     *             than 2 players.
     * 
     */
    static String[] fileToArray(String... args) throws GameOverException {

	Path path = pathCreation(args);
	BufferedReader input = fileOpen(path);
	String[] names = fileRead(input);
	fileClose(input);

	return names;
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
    private static Path pathCreation(String... args) throws GameOverException {

	if (args.length < 1) {

	    throw new GameOverException("There is not any file path.");
	}

	Path path = Paths.get(args[0]);
	pathExceptions(path);

	return path;
    }

    /**
     * 
     * Throws Path exceptions.
     * 
     * @param path
     *            a file path.
     * 
     * @throws GameOverException
     *             If there is no path. If file does not exist. If file is not
     *             readable. If file is a directory.
     * 
     */
    private static void pathExceptions(Path path) throws GameOverException {

	if (!(Files.exists(path))) {

	    throw new GameOverException("Players file not found.");
	}

	if (!(Files.isReadable(path))) {

	    throw new GameOverException("Players file unreadable.");
	}

	if (Files.isDirectory(path)) {

	    throw new GameOverException("Players file is a directory.");
	}
    }

    /**
     * 
     * Open player's file read-only.
     * 
     * @param path
     *            to find file.
     * 
     * @return A BufferedReader ready to read file.
     * 
     * @throws GameOverException
     *             If there is any IOException.
     * 
     */
    private static BufferedReader fileOpen(Path path) throws GameOverException {

	BufferedReader input = null;

	try {

	    input = Files.newBufferedReader(path,
		    java.nio.charset.Charset.forName("UTF-8" ));
	} catch (IOException e) {

	    throw new GameOverException(e.getMessage());
	}

	return input;
    }

    /**
     * 
     * Read all the file and write it in a String array. It is made of names and
     * beginner state if a player is.
     * 
     * @param input
     *            The opened &amp; readable player's file.
     * 
     * @return Player's name &amp; state.
     * 
     * @throws GameOverException
     *             If there is more than 4 players. If there is less than 2
     *             players. If there is any IOException while closing file.
     * 
     */
    private static String[] fileRead(BufferedReader input)
	throws GameOverException {

	    String names = null;
	    String line = null;
	    int length = 0;

	    line = lineRead(line, input);

	    while ((line != null) && (length < 4)) {

		if (names == null) {

		    names = line;
		} else {

		    names = names + " " + line;
		}

		length++;
		line = lineRead(line, input);
	    }

	    lengthError(length, line, input);
	    return names.split(" ");
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
     * @param input
     *            The opened &amp; readable player's file.
     * 
     * @throws GameOverException
     *             If there is more than 4 players. If there is less than 2
     *             players. If there is any IOException while closing file.
     * 
     */
    private static void lengthError(int length, String line,
	    BufferedReader input) throws GameOverException {

	if (length < 2) {

	    fileClose(input);
	    throw new GameOverException("At least two players required.");
	}

	if ((length > 3) && (line != null)) {

	    fileClose(input);
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
     * @param input
     *            The opened &amp; readable player's file.
     * 
     * @return Every characters of read line in a string.
     * 
     * @throws GameOverException
     *             If there is any IOException.
     * 
     */
    private static String lineRead(String line, BufferedReader input)
	throws GameOverException {

	    try {

		line = input.readLine();
	    } catch (IOException Iex) {

		fileClose(input);
		throw new GameOverException(Iex.getMessage());
	    }

	    return line;
	}

    /**
     * 
     * Closes a opened player's file.
     * 
     * @param input
     *            The opened &amp; readable player's file.
     * 
     * @throws GameOverException
     *             If there is any IOException.
     * 
     */
    private static void fileClose(BufferedReader input)
	throws GameOverException {

	    try {

		input.close();
	    } catch (IOException e) {

		throw new GameOverException(e.getMessage());
	    }
	}

}
