package jsonClasses;

// a class to represent a user vote when exchanging
// data between the client and the server
public class Vote {

	private String username;

	private int id;

	private int vote;

	// a simple constructor

	public Vote(String username, int id, int vote) {

		this.username = username;
		this.id = id;
		this.vote = vote;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setQuestionId(int id) {
		this.id = id;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

}
