package jsonClasses;

/**
 * An Answer object stores the information associated with an answer
 * that is sent to the client.
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
	 * The vote (1 - like, 0 - none, (-1) - dislike) of the answer.
	 * It is given to it when the information is sent to a specific
	 * user which may have liked or disliked this answer before.
	 */
	private int vote;

	// getters and setters
	
	/**
	 * Returns the {@link Answer#text} of the answer.
	 * @return The {@link String} instance representing the answer {@link Answer#text}
	 */
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthorNickname() {
		return authorNickname;
	}

	public void setAuthorNickname(String authorNickname) {
		this.authorNickname = authorNickname;
	}
	
	public String getAuthorUsername() {
		return authorUsername;
	}
	
	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimeStamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionId() {
		return questionId;
	}
	
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public int getVote() {
		return vote;
	}
	
	public void setVote(int vote) {
		this.vote = vote;
	}
	
}
