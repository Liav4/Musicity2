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
 * Servlet implementation class SignUpServlet
 */
public class SignUpServlet extends MusicityServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doGet(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		// declaring the connection and the statement objects to use
		Connection connection = null;
		Statement statement = null;

		System.out.println("in post method");

		// getting the user to sign up

		System.out.println(StringConstants.JSON_STRING_PARAMETER_NAME);

		System.out.println(request.getParameter(StringConstants.JSON_STRING_PARAMETER_NAME));

		User userToSignUp = (User) fromJson(request.getParameter(StringConstants.JSON_STRING_PARAMETER_NAME),
				User.class);

		// setting the cookie that is sent to the client

		HttpSession newSession = request.getSession();
		newSession.setAttribute(StringConstants.ATTRIBUTE_USERNAME_NAME, userToSignUp.getName());

		// checking the assignments
		System.out.println(userToSignUp);

		try {

			// getting the connection and the statement objects
			connection = DatabaseInteractor.getConnection();
			statement = connection.createStatement();

			// creating the users table, the open sessions table and the
			// user topic ranks table, if they don't already exist

			System.out.println(StringConstants.CREATE_USERS_TABLE);

			System.out.println("1");
			DatabaseInteractor.createTable(StringConstants.USERS_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.USER_TOPIC_RANKS_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.TOPICS_TABLE, statement);
			System.out.println("4");

			// inserting the user

			// checking if the user exists
			if (DatabaseInteractor.doesExistIn(userToSignUp.getName(), StringConstants.USERS_TABLE, statement)) {

				System.out.println("exists");

				// if the user already exists, notify the client
				ClientInteractor.sendStatus(response, 1);

			} else {

				System.out.println("doesn't exist");

				// inserting the user to the table, opening a session (or
				// updating one), adding all the user-topic pairs, and
				// notifying the client
				DatabaseInteractor.insertInto(StringConstants.USERS_TABLE, connection, userToSignUp.getName(),
						userToSignUp.getPassword(), userToSignUp.getNickname(), userToSignUp.getImage(),
						userToSignUp.getDescription());

				System.out.println("user " + userToSignUp.getName() + " was added.");

				DatabaseInteractor.addUserTopicPairs(userToSignUp, connection, statement);

				System.out.println("user topics were added.");

				ClientInteractor.sendStatus(response, 0);

			}

			// last of all - show the users table
			DatabaseInteractor.showTable(StringConstants.USERS_TABLE, statement);

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
