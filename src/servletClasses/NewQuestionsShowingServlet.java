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
 * Servlet implementation class NewQuestionsShowingServlet
 */
public class NewQuestionsShowingServlet extends MusicityServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		// declaring the connection and the statement objects to use
		Connection connection = null;

		// getting the page number

		int pageNumber = Integer.parseInt(request.getParameter(StringConstants.PAGE_NUMBER));
		System.out.println(pageNumber);

		// getting the user name from his cookies

		HttpSession currentSession = request.getSession();
		String username = (String) currentSession.getAttribute(StringConstants.ATTRIBUTE_USERNAME_NAME);

		if (username == null) {

			ClientInteractor.sendStatus(response, 2);
			return;

		}

		try {

			// getting the connection and the statement objects
			connection = DatabaseInteractor.getConnection();

			// getting the newly submitted questions from the database
			Question[] newQuestions = DatabaseInteractor.getNewQuestions(pageNumber, username, connection, response);
			
			String dataToSend = toJson(newQuestions) + " }";
			
			// String dataToSend = "{ \"isLastPage\":" + isLastPage + ", \"questions\": " + toJson(newQuestions) + " }";
			
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doPost(request, response);

	}

}
