package jsonClasses;

/**
 * An Answer object stores the information associated with an answer that is
 * sent to or from the client.
 * 
 * @author LIAV
 * @since 2016-02-25
 */
public class Answer {

	/**
	 * The text of the answer.
	 */
	private String text;

	/**
	 * The nickname of the user who wrote the answer.
	 */
	private String authorNickname;

	/**
	 * The user name of the user who wrote the answer.
	 */
	private String authorUsername;

	/**
	 * The time stamp of when the answer was submitted.
	 */
	private String timestamp;

	/**
	 * The overall rating score of the answer.
	 */
	private int rating;

	/**
	 * The id that is given to the answer from the database.
	 */
	private int id;

	/**
	 * The id of the question that this answer was submitted to.
	 */
	private int questionId;

	/**
	 * The vote (1 - like, 0 - none, (-1) - dislike) of the answer. It is given
	 * to it when the information is sent to a specific user which may have
	 * liked or disliked this answer before.
	 */
	private int vote;

	// getters and setters

	/**
	 * Returns the text of this answer.
	 * 
	 * @return A string instance representing this answer's text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets this answer text.
	 * 
	 * @param text
	 *            The text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Returns the nickname of this answer's author.
	 * 
	 * @return A string instance representing the nickname of this answer's
	 *         author.
	 */
	public String getAuthorNickname() {
		return authorNickname;
	}

	/**
	 * Sets the nickname of this answer's author.
	 * 
	 * @param authorNickname
	 *            The nickname to set.
	 */
	public void setAuthorNickname(String authorNickname) {
		this.authorNickname = authorNickname;
	}

	/**
	 * Returns the user name of this answer's author.
	 * 
	 * @return A string instance representing the user name of this answer's
	 *         author.
	 */
	public String getAuthorUsername() {
		return authorUsername;
	}

	/**
	 * Sets the user name of this answer's author.
	 * 
	 * @param authorUsername
	 *            The user name to set.
	 */
	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}

	/**
	 * Returns this answer's time stamp.
	 * 
	 * @return A string instance representing this answer's time stamp.
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets this answer's time stamp.
	 * 
	 * @param timestamp
	 *            The time stamp to set.
	 */
	public void setTimeStamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Returns the rating (sum of answer votes) of this answer.
	 * 
	 * @return The rating of this answer.
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Sets the rating (sum of answer votes) of this answer.
	 * 
	 * @param rating
	 *            The rating to set.
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * Returns this answer's id.
	 * 
	 * @return This answer's id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of this answer.
	 * 
	 * @param id
	 *            The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the id of the question that this answer was submitted to.
	 * 
	 * @return The id of this answer's question.
	 */
	public int getQuestionId() {
		return questionId;
	}

	/**
	 * Sets the id of this answer's question.
	 * 
	 * @param questionId
	 *            The id to set.
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	/**
	 * Returns the vote that this answer has, in according to the requesting
	 * user.
	 * 
	 * @return The vote of this answer.
	 */
	public int getVote() {
		return vote;
	}

	/**
	 * Sets the vote that this answer will have, in according to the requesting
	 * user.
	 * 
	 * @param vote
	 *            The vote to set.
	 */
	public void setVote(int vote) {
		this.vote = vote;
	}

}
