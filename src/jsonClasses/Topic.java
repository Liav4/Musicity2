package jsonClasses;

/**
 * A Topic object stores the information associated with a topic that is sent to
 * or from the client.
 * 
 * @author LIAV
 * @since 2016-02-26
 */
public class Topic {

	/**
	 * The topic name.
	 */
	private String name;

	// getters and setters

	/**
	 * Returns the topic name.
	 * 
	 * @return A string instance representing the topic name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the topic name.
	 * 
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

}
