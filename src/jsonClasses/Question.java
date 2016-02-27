package jsonClasses;

/**
 * A Question object stores the information associated with a question that is
 * sent to or from the client.
 * 
 * @author LIAV
 * @since 2016-02-26
 */
public class Question {

	/**
	 * The time stamp of when this question was submitted.
	 */
	private String timestamp;

	/**
	 * The text of this question.
	 */
	private String text;

	/**
	 * The nickname of this question's author.
	 */
	private String authorNickname;

	/**
	 * The user name of this question's author.
	 */
	private String authorUsername;

	/**
	 * The string instance representing all the topics associated with this
	 * question, divided with the {@link main.StringConstants#TOPIC_DIVIDER}
	 * character.
	 */
	private String topicsString;

	/**
	 * The topics associated with this question.
	 */
	private Topic[] topics;

	/**
	 * The rating of this question.
	 */
	private int rating;

	/**
	 * The id of this question.
	 */
	private int id;

	/**
	 * The vote that this question got, in accordance with the user who requests
	 * to view it. (1 - like, 0 - none, (-1) - dislike)
	 */
	private int vote;

	/**
	 * The answers which were submitted to this question.
	 */
	private Answer[] answers;

	// getters and setters

	/**
	 * Returns this question's time stamp.
	 * 
	 * @return A string instance representing this question's time stamp.
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets this question's time stamp.
	 * 
	 * @param timestamp
	 *            The time stamp to set.
	 */
	public void setTimeStamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Returns this question's text.
	 * 
	 * @return A string instance representing this question's text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets this question's text
	 * 
	 * @param text
	 *            The text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Returns the nickname of this question's author.
	 * 
	 * @return A string instance representing the nickname of this question's
	 *         author.
	 */
	public String getAuthorNickname() {
		return authorNickname;
	}

	/**
	 * Sets the nickname of this question's author.
	 * 
	 * @param authorNickname
	 *            The nickname to set.
	 */
	public void setAuthorNickname(String authorNickname) {
		this.authorNickname = authorNickname;
	}

	/**
	 * Returns the user name of this question's author.
	 * 
	 * @return A string instance representing the user name of this question's
	 *         author.
	 */
	public String getAuthorUsername() {
		return authorUsername;
	}

	/**
	 * Sets the user name of this question's author.
	 * 
	 * @param authorUsername
	 *            The user name to set.
	 */
	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}

	/**
	 * Returns the topics associated with this question.
	 * 
	 * @return A topic array which includes the topics associated with this
	 *         question.
	 */
	public Topic[] getTopics() {
		return topics;
	}

	/**
	 * Sets the topics of this question.
	 * 
	 * @param topics
	 *            The topics to set.
	 */
	public void setTopics(Topic[] topics) {
		this.topics = topics;
	}

	/**
	 * Returns the rating (sum of all votes) of this question.
	 * 
	 * @return The rating of this question.
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Sets the rating of this question.
	 * 
	 * @param rating
	 *            The rating to set.
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * Returns the id of this question.
	 * 
	 * @return The id of this question.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of this question.
	 * 
	 * @param id
	 *            The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the answers which were submitted to this question.
	 * 
	 * @return An array which includes all the answers which were submitted to
	 *         this question.
	 */
	public Answer[] getAnswers() {
		return answers;
	}

	/**
	 * Sets the answers which were submitted to this question.
	 * 
	 * @param answers
	 *            The answers to set.
	 */
	public void setAnswers(Answer[] answers) {
		this.answers = answers;
	}

	/**
	 * Returns the vote that this question has in accordance with the user who
	 * requests to view it.
	 * 
	 * @return The vote that this answer got.
	 */
	public int getVote() {
		return vote;
	}

	/**
	 * Sets the vote that this question has in accordance with the user who
	 * requests to view it.
	 * 
	 * @param vote
	 *            The vote to set.
	 */
	public void setVote(int vote) {
		this.vote = vote;
	}

	/**
	 * Returns the string which represents all the topics associated with this
	 * question, divided by the {@link main.StringConstants#TOPIC_DIVIDER}
	 * character.
	 * 
	 * @return A string instance representing all the topics associated with
	 *         this question.
	 */
	public String getTopicsString() {
		return topicsString;
	}

	/**
	 * Sets the topics string which represents all the topics associated with
	 * this question, divided by the {@link main.StringConstants#TOPIC_DIVIDER}
	 * character.
	 * 
	 * @param topicsString
	 *            The topics string to set.
	 */
	public void setTopicsString(String topicsString) {
		this.topicsString = topicsString;
	}

}
