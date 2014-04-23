package g39631.gameover.model;

/**
 * GameOverException class made for "GAME OVER" the Game.
 * 
 * @version 2.00
 * 
 * @author Placentino Simon
 * @author G39631
 * 
 *         HEB ESI LAJ1 2013-2014
 */
public class GameOverException extends Exception {

	/**
	 * Create a new exception for GameOver the game with all Exceptions
	 * attributes.
	 * 
	 * @param errorMessage
	 *            Error's description. It is saved for later retrieval by the
	 *            Throwable.getMessage() method.
	 * 
	 */
	public GameOverException(String errorMessage) {
		super(errorMessage);
	}
}
