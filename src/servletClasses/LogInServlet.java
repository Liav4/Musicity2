package servletClasses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jsonClasses.User;
import main.*;

/**
 * The Log In Servlet provides an API to handle a request to log in to the
 * server.
 * 
 * 
 * @author LIAV
 * @since 2016-02-26
 * @see main.DatabaseInteractor
 * @see main.StringConstants
 * @see main.MusicityServlet
 */
public class LogInServlet extends MusicityServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Provides support for a GET request - passing the parameters to the super
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

		// getting the user to log in

		User userToLogIn = (User) fromJson(getRequestData(request), User.class);

		// setting the name of that user to the user name attribute of the
		// request session

		HttpSession newSession = request.getSession();
		newSession.setAttribute(StringConstants.ATTRIBUTE_USERNAME_NAME, userToLogIn.getName());

		try {

			// getting the connection and the statement objects
			connection = DatabaseInteractor.getConnection();
			statement = connection.createStatement();

			// checking if the user exists and matches his password in the users
			// table
			if (DatabaseInteractor.doesExistIn(userToLogIn.getName(), StringConstants.USERS_TABLE, statement)
					&& DatabaseInteractor.doesMatchPassword(userToLogIn, statement)) {

				// if the user exists and matches the password, send a success
				// status with the user nickname for displaying in the client

				String dataToSend = String.format(" { \"status\": %d, \"nickname\": \"%s\", \"imageURL\": \"%s\" } ", 0,
						DatabaseInteractor.getColumnFromKey(StringConstants.USER_NICKNAME, StringConstants.USERNAME,
								userToLogIn.getName(), StringConstants.USERS_TABLE, statement),
						DatabaseInteractor.getColumnFromKey(StringConstants.USER_IMAGE_URL, StringConstants.USERNAME,
								userToLogIn.getName(), StringConstants.USERS_TABLE, statement));

				System.out.println(dataToSend);

				ClientInteractor.sendData(response, dataToSend);

			} else {

				// the user doesn't exist, send a failure status
				ClientInteractor.sendData(response, " { \"status\": 1 }");

			}

		} // handling the exceptions
		catch (SQLException exception) {

			System.err.println(StringConstants.DATABASE_ACCESS_ERROR);
			exception.printStackTrace();

			// notify the client about an exception
			ClientInteractor.sendStatus(response, 3);

		} catch (ClassNotFoundException exception) {

			System.err.println(StringConstants.CLASS_PATH_ERROR);
			exception.printStackTrace();

			// notify the client about an exception
			ClientInteractor.sendStatus(response, 3);

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

}
