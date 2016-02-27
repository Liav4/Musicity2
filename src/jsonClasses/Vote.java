package jsonClasses;

/**
 * A Vote object stores the information associated with a vote (either question
 * vote or answer vote) that is sent to or from the client.
 * 
 * @author LIAV
 * @since 2016-02-26
 */
public class Vote {

	/**
	 * The user name of the user who provided the vote.
	 */
	private String username;

	/**
	 * The id of the question or the answer which the vote was given to.
	 */
	private int id;

	/**
	 * The vote type (1 - like, 0 - none, (-1) - dislike).
	 */
	private int vote;

	/**
	 * Returns the user name of the user who provided this vote.
	 * 
	 * @return A string representing the user name of the user who provided this
	 *         vote.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the user name of the user who provided this vote.
	 * 
	 * @param username
	 *            The user name to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns the id of the answer or the question which the vote was given to.
	 * 
	 * @return The id of the answer or the question which the vote was given to.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets to id of the question or the answer that this vote was given to.
	 * 
	 * @param id
	 *            The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the vote type of this vote (1 - like, 0 - none, (-1) - dislike).
	 * 
	 * @return The vote type of this vote.
	 */
	public int getVote() {
		return vote;
	}

	/**
	 * Sets the vote type of this vote (1 - like, 0 - none, (-1) - dislike).
	 * 
	 * @param vote
	 *            The vote type to set.
	 */
	public void setVote(int vote) {
		this.vote = vote;
	}

}
