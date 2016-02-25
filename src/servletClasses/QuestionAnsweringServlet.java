package servletClasses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsonClasses.Answer;
import main.*;

public class QuestionAnsweringServlet extends MusicityServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doGet(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		// declaring the connection and the statement objects to use
		Connection connection = null;
		Statement statement = null;

		// getting the answer to add

		Answer answerToAdd = (Answer) fromJson(getRequestData(request), Answer.class);

		System.out.println(answerToAdd.getText());

		// getting the user name from his cookies

		String username = getUsernameFromSession(request);

		if (username == null) {

			ClientInteractor.sendStatus(response, 1);

			return;

		}

		try {

			// getting the connection and the statement objects
			connection = DatabaseInteractor.getConnection();
			statement = connection.createStatement();

			// getting the answer time stamp
			String answerTimestamp = getCurrentTimestamp();

			// inserting the answer to the answers table
			DatabaseInteractor.insertInto(StringConstants.ANSWERS_TABLE, connection,
					String.format("%d", answerToAdd.getQuestionId()), answerToAdd.getText(), username, answerTimestamp);

			// updating the user answers number
			DatabaseInteractor.addUserAnswersNumber(username, statement);

			System.out.println("after adding the user answers number");

			// updating the question answers number
			DatabaseInteractor.addQuestionAnswersNumber(answerToAdd.getQuestionId(), statement);

			ClientInteractor.sendStatus(response, 0);

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
