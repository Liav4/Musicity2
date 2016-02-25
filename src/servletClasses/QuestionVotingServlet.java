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
 * Servlet implementation class QuestionVotingServlet
 */
public class QuestionVotingServlet extends MusicityServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doGet(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		// declaring the connection and the statement objects to use
		Connection connection = null;
		Statement statement = null;

		System.out.println("entering question voting servlet");

		// getting the vote to add

		String jsonVoteString = getRequestData(request);

		Vote voteToAdd = (Vote) fromJson(jsonVoteString, Vote.class);

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

			// if the users tries to vote for his question, he will fail
			String authorNickname = DatabaseInteractor.getColumnFromKey(StringConstants.AUTHOR, StringConstants.ID,
					voteToAdd.getId(), StringConstants.QUESTIONS_TABLE, statement);

			if (authorNickname.equals(username)) {

				ClientInteractor.sendStatus(response, 2);

				return;

			}

			// getting the before and after vote scores
			int afterVotingScore = voteToAdd.getVote();
			int beforeVotingScore = !DatabaseInteractor.doesExistIn(StringConstants.USER_QUESTION_VOTES_TABLE,
					StringConstants.QUESTION_ID, voteToAdd.getId(), StringConstants.USERNAME, username, statement)
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
