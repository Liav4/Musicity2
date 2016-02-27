package servletClasses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsonClasses.Topic;
import main.*;

/**
 * The Top Topics Showing Servlet provides an API to handle a request to view
 * the top topics of the website, ordered by their popularity.
 * 
 * @author LIAV
 * @since 2016-02-26
 * @see main.DatabaseInteractor
 * @see main.StringConstants
 * @see main.MusicityServlet
 */
public class TopTopicsShowingServlet extends MusicityServlet {

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

		// declaring the connection and the statement objects to use
		Connection connection = null;
		Statement statement = null;

		// getting the page number to show from the request

		int pageNumber = Integer.parseInt(request.getParameter(StringConstants.PAGE_NUMBER));

		try {

			// getting the connection and the statement objects
			connection = DatabaseInteractor.getConnection();
			statement = connection.createStatement();

			// getting the top topics
			Topic[] topics = DatabaseInteractor.getTopTopics(pageNumber, statement, response);

			// sending the finishing of the JSON formatted string to the client.
			String dataToSend = toJson(topics) + " }";

			ClientInteractor.sendData(response, dataToSend);

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

				if (statement != null)
					statement.close();

				if (connection != null)
					connection.close();

			}

			catch (SQLException exception) {

				System.err.println(StringConstants.DATABASE_ACCESS_ERROR);
				exception.printStackTrace();

			}

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doPost(request, response);

	}

}
