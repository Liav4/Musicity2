package jsonClasses;

/**
 * A User object stores the information associated with a user that is sent to
 * or from the client.
 * 
 * @author LIAV
 * @since 2016-02-26
 */
public class User {

	/**
	 * The user name of this user.
	 */
	private String name;

	/**
	 * The password of this user.
	 */
	private String password;

	/**
	 * The nickname of this user.
	 */
	private String nickname;

	/**
	 * The description of this user.
	 */
	private String description;

	/**
	 * The URL address of the image of this user.
	 */
	private String image;

	// fields used for the last queries of the project

	/**
	 * The top topics of this user, sorted by their rank in accordance with this
	 * user.
	 */
	private Topic[] expertiseProfile;

	/**
	 * The rating of this user, calculated by the project formula.
	 */
	private double rating;

	/**
	 * The last questions that this user has asked.
	 */
	private Question[] lastAskedQuestions;

	/**
	 * The last questions that this user has answered.
	 */
	private Question[] lastAnsweredQuestions;

	/**
	 * The last answers that this user has submitted.
	 */
	private Answer[] lastAnswers;

	// simple getters and setters

	/**
	 * Returns the user name of this user.
	 * 
	 * @return A string instance representing the user name of this user.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the user name of this user.
	 * 
	 * @param name
	 *            The user name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the password of this user.
	 * 
	 * @return A string instance representing this user's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of this user.
	 * 
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the nickname of this user.
	 * 
	 * @return A string instance representing the nickname of this user.
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Sets the nickname of this user.
	 * 
	 * @param nickname
	 *            The nickname to set.
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Returns the description of this user.
	 * 
	 * @return A string instance representing this user's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of this user.
	 * 
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the URL address of this user's image.
	 * 
	 * @return A string instance representing the URL address of this user's
	 *         image.
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Sets the URL address of this user's image.
	 * 
	 * @param image
	 *            The URL address to set.
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * Returns the top topics of this user, sorted by their rank in accordance
	 * with this user.
	 * 
	 * @return An array which includes the top topics of this user.
	 */
	public Topic[] getExpertiseProfile() {
		return expertiseProfile;
	}

	/**
	 * Sets the top topics of this user, sorted by their rank in accordance with
	 * this user.
	 * 
	 * @param expertiseProfile
	 *            The topics to set.
	 */
	public void setExpertiseProfile(Topic[] expertiseProfile) {
		this.expertiseProfile = expertiseProfile;
	}

	/**
	 * Returns the rating of this user, calculated by the project formula.
	 * 
	 * @return The rating of this user.
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * Sets the rating of this user, calculated by the project formula.
	 * 
	 * @param rating
	 *            The rating to set.
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

	/**
	 * Returns the last questions asked by this user.
	 * 
	 * @return An array which includes the last questions asked by this user.
	 */
	public Question[] getLastAskedQuestions() {
		return lastAskedQuestions;
	}

	/**
	 * Sets the last questions asked by this user.
	 * 
	 * @param lastAskedQuestions
	 *            The questions to set.
	 */
	public void setLastAskedQuestions(Question[] lastAskedQuestions) {
		this.lastAskedQuestions = lastAskedQuestions;
	}

	/**
	 * Returns the last questions answered by this user.
	 * 
	 * @return An array which includes the last questions answered by this user.
	 */
	public Question[] getLastAnsweredQuestions() {
		return lastAnsweredQuestions;
	}

	/**
	 * Sets the last questions answered by this user.
	 * 
	 * @param lastAnsweredQuestions
	 *            The questions to set.
	 */
	public void setLastAnsweredQuestions(Question[] lastAnsweredQuestions) {
		this.lastAnsweredQuestions = lastAnsweredQuestions;
	}

	/**
	 * Returns the last answers which were submitted by this user.
	 * 
	 * @return An array which includes the last answers which were submitted by
	 *         this user.
	 */
	public Answer[] getLastAnswers() {
		return lastAnswers;
	}

	/**
	 * Sets the last answers which were submitted by this user.
	 * 
	 * @param lastAnswers
	 *            The answers to set.
	 */
	public void setLastAnswers(Answer[] lastAnswers) {
		this.lastAnswers = lastAnswers;
	}

}
