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
 * Servlet implementation class QuestionsByTopicsShowingServlet
 */
public class QuestionsByTopicsShowingServlet extends MusicityServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		// declaring the connection and the statement objects to use
		Connection connection = null;

		// getting the topic name and the page number from the request

		int pageNumber = Integer.parseInt(request.getParameter(StringConstants.PAGE_NUMBER));
		String topicName = request.getParameter("topicName");

		// getting the user name from his cookies

		String username = getUsernameFromSession(request);

		if (username == null) {

			ClientInteractor.sendStatus(response, 1);

			return;

		}

		try {

			// getting the connection and the statement objects
			connection = DatabaseInteractor.getConnection();

			// get the top questions by this topic
			Question[] topQuestionsByTopic = DatabaseInteractor.getTopQuestionsByTopic(topicName, pageNumber, username,
					connection, response);

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doPost(request, response);

	}

}
