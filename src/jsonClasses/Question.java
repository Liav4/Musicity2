package jsonClasses;

// a class to represent a question when exchanging
// data between the client and the server
public class Question {

	private String timestamp;
	private String text;
	private String authorNickname;
	private String authorUsername;
	private String topicsString;

	private Topic[] topics;

	private int rating;
	private int id;
	private int vote;

	private Answer[] answers;

	// getters and setters

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimeStamp(String timestamp) {
		this.timestamp = timestamp;
	}

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

	public Topic[] getTopics() {
		return topics;
	}

	public void setTopics(Topic[] topics) {
		this.topics = topics;
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

	public Answer[] getAnswers() {
		return answers;
	}

	public void setAnswers(Answer[] answers) {
		this.answers = answers;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public String getTopicsString() {
		return topicsString;
	}

	public void setTopicsString(String topicsString) {
		this.topicsString = topicsString;
	}

	// a toString method to properly display the question
	@Override
	public String toString() {

		return String.format("Time of submission: %s\nText: %s\n" + "Author Nickname: %s\nTopics: %s\nRating: %d\n",
				timestamp, text, authorNickname, topics, rating);

	}

}
