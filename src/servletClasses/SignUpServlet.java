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
 * The Sign Up Servlet provides an API for inserting a user to the database at
 * sign up.
 * 
 * 
 * @author LIAV
 * @since 2016-02-26
 * @see main.DatabaseInteractor
 * @see main.StringConstants
 * @see main.MusicityServlet
 */
public class SignUpServlet extends MusicityServlet {

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

		// getting the user to sign up
		
		User userToSignUp = (User) fromJson(getRequestData(request), User.class);
		
		// setting the user name to the attribute of the request session

		HttpSession newSession = request.getSession();

		newSession.setAttribute(StringConstants.ATTRIBUTE_USERNAME_NAME, userToSignUp.getName());

		try {

			// getting the connection and the statement objects
			connection = DatabaseInteractor.getConnection();
			statement = connection.createStatement();

			// inserting the user

			// checking if the user exists
			if (DatabaseInteractor.doesExistIn(userToSignUp.getName(), StringConstants.USERS_TABLE, statement)) {

				// if the user already exists, notify the client about a failue
				ClientInteractor.sendStatus(response, 1);

			} else {

				// inserting the user to the table and adding all the user-topic
				// pairs, and notifying the client
				DatabaseInteractor.insertInto(StringConstants.USERS_TABLE, connection, userToSignUp.getName(),
						userToSignUp.getPassword(), userToSignUp.getNickname(), userToSignUp.getImage(),
						userToSignUp.getDescription());

				DatabaseInteractor.addUserTopicPairs(userToSignUp, connection, statement);

				// sending a success status
				ClientInteractor.sendStatus(response, 0);

			}

			try {

				// shutting down the database
				DatabaseInteractor.shutDownDatabase();

			} catch (SQLException e) {
			}

		} // handling the exceptions
		catch (SQLException exception) {

			System.err.println(StringConstants.DATABASE_ACCESS_ERROR);
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

}
