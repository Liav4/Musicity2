package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * The MusicityServlet class is the super class of all the other servlets. It
 * provides an API used by most of the servlets, and an init method used to
 * create the tables on server startup.
 *
 * @author LIAV
 * @since 2016-02-26
 * 
 */
public class MusicityServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// parameters used by all the other musicity servlets

	/**
	 * A GSON object used to parse a JSON formatted string into an object and
	 * vice versa.
	 */
	protected Gson gson = new Gson();

	// methods used by all the other musicity servlets

	/**
	 * Initializes the server. Creates all the tables in the database.
	 */
	public void init() {

		Connection connection = null;
		Statement statement = null;

		try {

			connection = DatabaseInteractor.getConnection();
			statement = connection.createStatement();

		} catch (SQLException exception) {
			exception.printStackTrace();
			return;
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
			return;
		}

		try {

			DatabaseInteractor.createTable(StringConstants.USERS_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.QUESTIONS_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.ANSWERS_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.TOPICS_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.USER_QUESTION_VOTES_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.USER_ANSWER_VOTES_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.USER_TOPIC_RANKS_TABLE, statement);

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

	}

	/**
	 * Gets the user name from a session of a given request.
	 * 
	 * @param request
	 *            The request from the client.
	 * @return A string instance representing the user name from the session.
	 */
	protected String getUsernameFromSession(HttpServletRequest request) {

		// getting the session from the request
		HttpSession currentSession = request.getSession();

		// if the session is new - returning null
		if (currentSession.isNew())
			return null;

		return (String) currentSession.getAttribute(StringConstants.ATTRIBUTE_USERNAME_NAME);

	}

	/**
	 * Returns the current time stamp formatted in the project time stamp
	 * format.
	 * 
	 * @return A string instance representing the current time stamp formatted
	 *         in the project format.
	 */
	protected String getCurrentTimestamp() {

		Date currentDate = new Date();

		/*
		 * here we make an assumption - we add the P.M. or A.M. sign to the time
		 * stamp to clarify exactly when the action was performed
		 */

		SimpleDateFormat dateFormatter = new SimpleDateFormat(StringConstants.PROJECT_DATE_FORMAT);

		return dateFormatter.format(currentDate);

	}

	/**
	 * An overloaded method. Parses an object into a JSON string.
	 * 
	 * @param object
	 *            The object to parse.
	 * @return A string instance representing the JSON format of the given
	 *         object.
	 */
	protected String toJson(Object object) {

		Gson gson = new Gson();

		return gson.toJson(object);

	}

	/**
	 * An overloaded method. Parses objects into a JSON string.
	 * 
	 * @param objects
	 *            The objects to parse.
	 * @return A string instance representing the JSON format of the given
	 *         objects.
	 */
	protected String toJson(Object[] objects) {

		Gson gson = new Gson();

		return gson.toJson(objects);

	}

	/**
	 * Converts a JSON formatted string into an object of the specified class.
	 * 
	 * @param jsonString
	 *            The JSON string.
	 * @param classInstance
	 *            The class to convert to.
	 * @return An object of the specified class.
	 */
	protected <T> Object fromJson(String jsonString, Class<T> classInstance) {

		return gson.fromJson(jsonString, classInstance);

	}

	/**
	 * Gets the data from a request.
	 * 
	 * @param request
	 *            The request to get the data from.
	 * @return A string instance representing the data from the request.
	 */
	protected String getRequestData(HttpServletRequest request) {

		// building the data via an efficient string builder object

		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader reader = request.getReader();
			String currentLine = "";
			while ((currentLine = reader.readLine()) != null) {

				stringBuilder.append(currentLine);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();

	}

	/**
	 * Transfers the parameters into the super class doPost method.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doPost(request, response);

	}

	/**
	 * Transfers the parameters into the super class doGet method.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doGet(request, response);

	}

}
