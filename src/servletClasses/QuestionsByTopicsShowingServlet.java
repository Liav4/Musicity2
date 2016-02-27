package servletClasses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsonClasses.Question;
import main.*;

/**
 * The Questions By Topic Showing Servlet provides an API to handle a request to
 * view questions associated with a given topic, which were submitted on the
 * website.
 * 
 * @author LIAV
 * @since 2016-02-26
 * @see main.DatabaseInteractor
 * @see main.StringConstants
 * @see main.MusicityServlet
 */
public class QuestionsByTopicsShowingServlet extends MusicityServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Provides support for a GET request.
	 * 
	 * @param request
	 *            The request to the server.
	 * @param response
	 *            The response from the server.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		// declaring the connection object to be used
		Connection connection = null;

		// getting the topic name and the page number from the request

		int pageNumber = Integer.parseInt(request.getParameter(StringConstants.PAGE_NUMBER));
		String topicName = request.getParameter(StringConstants.TOPIC_NAME_PARAMETER);

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

			// get the top questions by this topic
			Question[] topQuestionsByTopic = DatabaseInteractor.getTopQuestionsByTopic(topicName, pageNumber, username,
					connection, response);

			// sending the finishing of the JSON formatted string to the client
			ClientInteractor.sendData(response, toJson(topQuestionsByTopic) + " }");

		} // handling the exceptions
		catch (SQLException exception) {
			System.err.println(StringConstants.DATABASE_QUERIES_ERROR);
			exception.printStackTrace();
		} catch (ClassNotFoundException exception) {
			System.err.println(StringConstants.CLASS_PATH_ERROR);
			exception.printStackTrace();
		} // releasing the resources
		finally {

			try {

				if (connection != null)
					connection.close();

			}

			catch (SQLException exception) {

				System.err.println(StringConstants.DATABASE_ACCESS_ERROR);
				exception.printStackTrace();

			}

		}

	}

	/**
	 * Provides support for a POST request - sending the parameters to the super
	 * class.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doPost(request, response);

	}

}
