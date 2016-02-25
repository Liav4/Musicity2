package servletClasses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsonClasses.Question;
import main.*;

/**
 * Servlet implementation class QuestionSubmissionServlet
 */
public class QuestionSubmissionServlet extends MusicityServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.getWriter().println("hello charlie");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		// declaring the connection and the statement objects to use
		Connection connection = null;
		Statement statement = null;

		// getting the question to add

		String data = getRequestData(request);

		System.out.println("data is: " + data);
				
		System.out.println("updated data is " + data);

		Question questionToAdd = (Question) fromJson(data, Question.class);

		// getting the user name from his cookies

		String username = getUsernameFromSession(request);
		
		if (username == null) {

			ClientInteractor.sendStatus(response, 1);

			return;

		}

		// checking the assignments

		System.out.println(questionToAdd);

		try {

			// getting the connection and the statement objects
			connection = DatabaseInteractor.getConnection();
			statement = connection.createStatement();
			
			// getting the topics string
			String topics = questionToAdd.getTopicsString();
			if (topics.equals(StringConstants.NO_INPUT_PARAMETER))
				topics = "";
			
			String questionTimestamp = getCurrentTimestamp();

			// inserting the new question to the questions table
			DatabaseInteractor.insertInto(StringConstants.QUESTIONS_TABLE, connection, questionToAdd.getText(),
					username, topics, questionTimestamp);

			System.out.println("after question insertion");

			if (topics.length() != 0) {
				
				// getting all the topics in an array
				String[] topicsArray = questionToAdd.getTopicsString().substring(1).split(StringConstants.TOPIC_DIVIDER);
				System.out.println("number of topics is " + topicsArray.length);
				System.out.println("the first topic is " + topicsArray[0]);
	
				System.out.println("inserting " + topicsArray[0]);
	
				// inserting all the topics that don't already exist in the
				// database to the topics table, and to the user topic ranks table
				for (int i = 0; i < topicsArray.length; i++) {
	
					if (!DatabaseInteractor.doesExistIn(topicsArray[i], StringConstants.TOPICS_TABLE, statement)) {
	
						System.out.println("the topic " + topicsArray[i] + " does not exist");
	
						DatabaseInteractor.insertInto(StringConstants.TOPICS_TABLE, connection, topicsArray[i]);
	
						System.out.println("after topic insertion");
	
						DatabaseInteractor.addUserTopicPairs(topicsArray[i], connection, statement);
	
						System.out.println("after user topic pairs insertion");
	
					}
	
					System.out.println("didn't enter to the if statement");
	
				}
				
			}

			System.out.println("after the for loop");

			// updating the user submitted questions number
			DatabaseInteractor.addUserQuestionsNumber(username, statement);

			System.out.println("finished");
			
			ClientInteractor.sendStatus(response, 0);

		} // handling the exceptions
		catch (SQLException exception) {

			System.err.println(StringConstants.DATABASE_ACCESS_ERROR);
			exception.printStackTrace();
			ClientInteractor.sendStatus(response, 1);
			
		} catch (ClassNotFoundException exception) {

			System.err.println(StringConstants.CLASS_PATH_ERROR);
			exception.printStackTrace();
			ClientInteractor.sendStatus(response, 1);
			
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
