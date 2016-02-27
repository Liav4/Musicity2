package servletClasses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jsonClasses.Question;
import main.*;

/**
 * The New Questions Showing Servlet provides an API to handle a request to view
 * the newly submitted questions on the website.
 * 
 * @author LIAV
 * @since 2016-02-26
 * @see main.DatabaseInteractor
 * @see main.StringConstants
 * @see main.MusicityServlet
 */
public class NewQuestionsShowingServlet extends MusicityServlet {

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

		// declaring the connection object to use
		Connection connection = null;

		// getting the page number

		int pageNumber = Integer.parseInt(request.getParameter(StringConstants.PAGE_NUMBER));

		// getting the user name from the request session

		HttpSession currentSession = request.getSession();
		String username = (String) currentSession.getAttribute(StringConstants.ATTRIBUTE_USERNAME_NAME);

		if (username == null) {

			// if the session is not valid - return a relevant status
			ClientInteractor.sendStatus(response, 2);
			return;

		}

		try {

			// getting the connection and the statement objects
			connection = DatabaseInteractor.getConnection();

			// getting the newly submitted questions from the database
			Question[] newQuestions = DatabaseInteractor.getNewQuestions(pageNumber, username, connection, response);

			// sending the finishing of the JSON formatted string

			String dataToSend = toJson(newQuestions) + " }";

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
	 * class
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doPost(request, response);

	}

}
