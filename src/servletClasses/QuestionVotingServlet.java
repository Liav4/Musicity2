package servletClasses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsonClasses.Vote;
import main.*;

/**
 * The Question Voting Servlet provides an API to handle a question voting in
 * the website.
 * 
 * 
 * @author LIAV
 * @since 2016-02-26
 * @see main.DatabaseInteractor
 * @see main.StringConstants
 * @see main.MusicityServlet
 */
public class QuestionVotingServlet extends MusicityServlet {

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

		// getting the vote to add

		Vote voteToAdd = (Vote) fromJson(getRequestData(request), Vote.class);

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
			statement = connection.createStatement();

			// if the users tries to vote for his question, he will fail
			String authorUsername = DatabaseInteractor.getColumnFromKey(StringConstants.AUTHOR, StringConstants.ID,
					voteToAdd.getId(), StringConstants.QUESTIONS_TABLE, statement);

			if (authorUsername.equals(username)) {

				// sending a failure status
				ClientInteractor.sendStatus(response, 1);

				return;

			}

			// getting the before and after vote scores
			int afterVotingScore = voteToAdd.getVote();
			int beforeVotingScore = !DatabaseInteractor.doesExistIn(StringConstants.USER_QUESTION_VOTES_TABLE,
					StringConstants.USERNAME, username, StringConstants.QUESTION_ID, voteToAdd.getId(), statement)
							? 0
							: Integer.parseInt(DatabaseInteractor.getColumnFromPair(StringConstants.VOTE,
									StringConstants.USERNAME, username, StringConstants.QUESTION_ID, voteToAdd.getId(),
									StringConstants.USER_QUESTION_VOTES_TABLE, statement));

			// computing the score change
			int scoreChange = afterVotingScore - beforeVotingScore;

			// updating the question and all the information that is linked
			// to it
			DatabaseInteractor.addQuestionVotingScore(voteToAdd.getId(), scoreChange, statement);

			// if the user cancelled his vote - delete from votes
			if (afterVotingScore == 0)
				DatabaseInteractor.deleteRecord(StringConstants.USER_QUESTION_VOTES_TABLE, StringConstants.USERNAME,
						username, StringConstants.QUESTION_ID, voteToAdd.getId(), statement);
			else if (beforeVotingScore == 0) {
				// insert the data
				DatabaseInteractor.insertInto(StringConstants.USER_QUESTION_VOTES_TABLE, connection, username,
						String.format("%d", voteToAdd.getId()), String.format("%d", voteToAdd.getVote()));
			} else {
				// update the data
				System.out.println("vote now is: " + voteToAdd.getVote());
				DatabaseInteractor.updateDatabase(StringConstants.USER_QUESTION_VOTES_TABLE, StringConstants.USERNAME,
						username, StringConstants.QUESTION_ID, voteToAdd.getId(), StringConstants.VOTE,
						voteToAdd.getVote(), statement);
			}

			// sending a success status
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
