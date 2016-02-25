package jsonClasses;

// a class to represent a user when exchanging
// data between the client and the server
public class User {

	private String name;
	private String password;
	private String nickname;
	private String description;
	private String image;
	
	// fields used for the last queries of the project
	
	private Topic[] expertiseProfile;
	
	private double rating;
	
	private Question[] lastAskedQuestions;
	private Question[] lastAnsweredQuestions;
	
	private Answer[] lastAnswers;

	// simple getters and setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Topic[] getExpertiseProfile() {
		return expertiseProfile;
	}

	public void setExpertiseProfile(Topic[] expertiseProfile) {
		this.expertiseProfile = expertiseProfile;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Question[] getLastAskedQuestions() {
		return lastAskedQuestions;
	}

	public void setLastAskedQuestions(Question[] lastAskedQuestions) {
		this.lastAskedQuestions = lastAskedQuestions;
	}

	public Question[] getLastAnsweredQuestions() {
		return lastAnsweredQuestions;
	}

	public void setLastAnsweredQuestions(Question[] lastAnsweredQuestions) {
		this.lastAnsweredQuestions = lastAnsweredQuestions;
	}

	public Answer[] getLastAnswers() {
		return lastAnswers;
	}

	public void setLastAnswers(Answer[] lastAnswers) {
		this.lastAnswers = lastAnswers;
	}

	// a toString method to properly display the user
	@Override
	public String toString() {
	
		return String.format(
				"Username: %s\nPassword: %s\nUser Nickname: %s\n" + 
				"Image URL: %s\nShort Description: %s\n", name,
				password, nickname, image, description);
	
	}

}
