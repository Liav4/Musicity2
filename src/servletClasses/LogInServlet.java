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
 * Servlet implementation class LogInServlet
 */
public class LogInServlet extends MusicityServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.getWriter().append("hello");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		// declaring the connection and the statement objects to use
		Connection connection = null;
		Statement statement = null;

		// getting the user to log in

		User userToLogIn = (User) fromJson(request.getParameter(StringConstants.JSON_STRING_PARAMETER_NAME),
				User.class);

		// setting the cookie that is sent to the client

		HttpSession newSession = request.getSession();
		newSession.setAttribute(StringConstants.ATTRIBUTE_USERNAME_NAME, userToLogIn.getName());
		
		System.out.println("after adding the cookie\n");
		System.out.println(userToLogIn);

		try {

			// getting the connection and the statement objects
			connection = DatabaseInteractor.getConnection();
			statement = connection.createStatement();

			// checking if the user exists
			if (DatabaseInteractor.doesExistIn(userToLogIn.getName(), StringConstants.USERS_TABLE, statement) &&
					DatabaseInteractor.doesMatchPassword(userToLogIn, statement)) {

				System.out.println("sending an OK status");
					
				// if the user exists and matches the password, send an OK status
				ClientInteractor.sendStatus(response, 0);
				
			} else {
				
				System.out.println("sending a WRONG status");

				// the user doesn't exist, send a WRONG status
				ClientInteractor.sendStatus(response, 1);

			}
			
			System.out.println("after all the checks");

		} // handling the exceptions
		catch (SQLException exception) {

			System.err.println(StringConstants.DATABASE_ACCESS_ERROR);
			exception.printStackTrace();

			// notify the client about an exception
			ClientInteractor.sendStatus(response, 2);

		} catch (ClassNotFoundException exception) {

			System.err.println(StringConstants.CLASS_PATH_ERROR);
			exception.printStackTrace();

			// notify the client about an exception
			ClientInteractor.sendStatus(response, 2);

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
