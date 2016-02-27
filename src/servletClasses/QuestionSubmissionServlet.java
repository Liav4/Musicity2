package servletClasses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsonClasses.Question;
import main.*;

/**
 * The Question Submission Servlet provides an API to handle a question
 * submission in the website.
 * 
 * 
 * @author LIAV
 * @since 2016-02-26
 * @see main.DatabaseInteractor
 * @see main.StringConstants
 * @see main.MusicityServlet
 */
public class QuestionSubmissionServlet extends MusicityServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Provides support for a GET request - sending the parameters to the super
	 * class.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doGet(request, response);

	}

	/**
	 * Provides support for a POST request.
	 * 
	 * @param request
	 *            The request to the server.
	 * @param response
	 *            The response from the server.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		// declaring the connection and the statement objects to use
		Connection connection = null;
		Statement statement = null;

		// getting the question to add

		Question questionToAdd = (Question) fromJson(getRequestData(request), Question.class);

		// getting the user name from the request session

		String username = getUsernameFromSession(request);

		if (username == null) {

			// if the session is not valid - return a relevant status
			ClientInteractor.sendStatus(response, 2);

			return;

		}

		try {

			// getting the connection and the statement objects
			connection = DatabaseInteractor.getConnection();
			statement = connection.createStatement();

			// getting the topics string

			// if there are no topics - the string will be empty
			String topics = questionToAdd.getTopicsString();
			if (topics.equals(StringConstants.NO_INPUT_PARAMETER))
				topics = "";

			String[] topicsArray = null;

			// if topics were added, add them to the database
			if (topics.length() != 0) {
				
				

				// getting all the topics in an array
				topicsArray = questionToAdd.getTopicsString().substring(1).split(StringConstants.TOPIC_DIVIDER);

				for (String topic : topicsArray) {

					if (topic.length() == 0 || topic.length() > 50) {

						ClientInteractor.sendStatus(response, 1);
						return;

					}

				}

				// preventing topics duplication

				HashSet<String> topicDuplicatePreventer = new HashSet<String>();

				StringBuilder stringBuilder = new StringBuilder();

				for (String topic : topicsArray)
					if (!topicDuplicatePreventer.contains(topic)) {
						topicDuplicatePreventer.add(topic);
						stringBuilder.append(StringConstants.TOPIC_DIVIDER + topic);
					}

				topics = stringBuilder.toString();

				topicsArray = topics.split(StringConstants.TOPIC_DIVIDER);

			}

			String questionTimestamp = getCurrentTimestamp();

			// inserting the new question to the questions table
			DatabaseInteractor.insertInto(StringConstants.QUESTIONS_TABLE, connection, questionToAdd.getText(),
					username, topics, questionTimestamp);

			// if topics were added, add them to the database
			if (topics.length() != 0) {

				// inserting all the topics that don't already exist in the
				// database to the topics table, and to the user topic ranks
				// table
				for (int i = 0; i < topicsArray.length; i++) {

					if (!DatabaseInteractor.doesExistIn(topicsArray[i], StringConstants.TOPICS_TABLE, statement)) {

						DatabaseInteractor.insertInto(StringConstants.TOPICS_TABLE, connection, topicsArray[i]);

						DatabaseInteractor.addUserTopicPairs(topicsArray[i], connection, statement);

					}

				}

			}

			// updating the user submitted questions number
			DatabaseInteractor.addUserQuestionsNumber(username, statement);

			// sending a success status
			ClientInteractor.sendStatus(response, 0);

		} // handling the exceptions
		catch (SQLException exception) {

			System.err.println(StringConstants.DATABASE_ACCESS_ERROR);
			exception.printStackTrace();

			// sending a failure status
			ClientInteractor.sendStatus(response, 1);

		} catch (ClassNotFoundException exception) {

			System.err.println(StringConstants.CLASS_PATH_ERROR);
			exception.printStackTrace();

			// sending a failure status
			ClientInteractor.sendStatus(response, 1);

		} // releasing the resources
		finally {

			try {

				if (statement != null)
					statement.close();

				if (connection != null)
					connection.close();

			}

			catch (SQLException exception) {

				System.err.println(StringConstants.DATABASE_ACCESS_ERROR);
				exception.printStackTrace();

				// sending a failure status
				ClientInteractor.sendStatus(response, 1);

			}

		}

	}

}
