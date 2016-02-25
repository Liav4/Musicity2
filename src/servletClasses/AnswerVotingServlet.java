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
 * Servlet implementation class AnswerVotingServlet
 */
public class AnswerVotingServlet extends MusicityServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doGet(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		// declaring the connection and the statement objects to use
		Connection connection = null;
		Statement statement = null;

		// getting the vote to add

		Vote voteToAdd = (Vote) fromJson(getRequestData(request), Vote.class);

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

			// if the users tries to vote for his answer, he will fail
			String authorNickname = DatabaseInteractor.getColumnFromKey(StringConstants.AUTHOR, StringConstants.ID,
					voteToAdd.getId(), StringConstants.ANSWERS_TABLE, statement);

			if (authorNickname.equals(username)) {

				ClientInteractor.sendStatus(response, 2);

				return;

			}

			// getting the before and after vote scores
			int afterVotingScore = voteToAdd.getVote();
			int beforeVotingScore = !DatabaseInteractor.doesExistIn(StringConstants.USER_ANSWER_VOTES_TABLE,
					StringConstants.ANSWER_ID, voteToAdd.getId(), StringConstants.USERNAME, username, statement)
							? 0
							: Integer.parseInt(DatabaseInteractor.getColumnFromPair(StringConstants.VOTE,
									StringConstants.USERNAME, username, StringConstants.ANSWER_ID, voteToAdd.getId(),
									StringConstants.USER_ANSWER_VOTES_TABLE, statement));

			// computing the score change
			int scoreChange = afterVotingScore - beforeVotingScore;

			// updating the answer and all the other data that is linked to it
			DatabaseInteractor.addAnswerVotingScore(voteToAdd.getId(), scoreChange, statement);

			// if the user cancelled his vote - delete from votes
			if (afterVotingScore == 0)
				DatabaseInteractor.deleteRecord(StringConstants.USER_ANSWER_VOTES_TABLE, StringConstants.USERNAME,
						username, StringConstants.ANSWER_ID, voteToAdd.getId(), statement);
			else if (beforeVotingScore == 0) {
				// insert the data
				DatabaseInteractor.insertInto(StringConstants.USER_ANSWER_VOTES_TABLE, connection, username,
						String.format("%d", voteToAdd.getId()), String.format("%d", voteToAdd.getVote()));
			} else {
				// update the data
				System.out.println("vote now is: " + voteToAdd.getVote());
				DatabaseInteractor.updateDatabase(StringConstants.USER_ANSWER_VOTES_TABLE, StringConstants.USERNAME,
						username, StringConstants.ANSWER_ID, voteToAdd.getId(), StringConstants.VOTE,
						voteToAdd.getVote(), statement);
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
