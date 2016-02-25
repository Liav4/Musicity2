package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import jsonClasses.Answer;
import jsonClasses.Question;
import jsonClasses.Topic;
import jsonClasses.User;

// a class which interacts with the database
// throughout the servlet lifetime
public class DatabaseInteractor {

	// a hash map that gets the key column string from the table string
	private static HashMap<String, String> keyColumnFinder = null;

	// a method to get a connection with our apache derby database
	public static Connection getConnection() throws SQLException, ClassNotFoundException {

		// initializing the key column finder hash map
		if (keyColumnFinder == null) {
			keyColumnFinder = new HashMap<String, String>();
			keyColumnFinder.put(StringConstants.USERS_TABLE, StringConstants.USERNAME);
			keyColumnFinder.put(StringConstants.QUESTIONS_TABLE, StringConstants.ID);
			keyColumnFinder.put(StringConstants.ANSWERS_TABLE, StringConstants.ID);
			keyColumnFinder.put(StringConstants.TOPICS_TABLE, StringConstants.TOPIC_NAME);
		}

		// creating the URL string object for the database
		String databaseURL = StringConstants.DERBY_GET_DATABASE;

		// creating the object which connects to the database
		Connection connection = null;

		// registering the driver
		Class.forName(StringConstants.DERBY_EMBEDDED_DRIVER_PATH);

		// getting a connection to the database
		connection = DriverManager.getConnection(databaseURL);

		// returning the connection
		return connection;

	}

	public static void shutDownDatabase() throws SQLException {

		DriverManager.getConnection(StringConstants.DERBY_SHUT_DOWN);

	}

	// a method for creating a table
	public static void createTable(String table, Statement statement) throws ClassNotFoundException, SQLException {

		// getting the SQL command from the table name
		String sqlCommand;

		switch (table) {

		case StringConstants.USERS_TABLE: // setting a command to create a users
											// table

			sqlCommand = StringConstants.CREATE_USERS_TABLE;

			break;

		case StringConstants.QUESTIONS_TABLE: // setting a command to create a
												// questions table

			sqlCommand = StringConstants.CREATE_QUESTIONS_TABLE;

			break;

		case StringConstants.ANSWERS_TABLE: // setting a command to create an
											// answers table

			sqlCommand = StringConstants.CREATE_ANSWERS_TABLE;

			break;

		case StringConstants.TOPICS_TABLE: // setting a command to create a
											// topics table

			sqlCommand = StringConstants.CREATE_TOPICS_TABLE;

			break;

		case StringConstants.USER_QUESTION_VOTES_TABLE: // setting a command to
														// create a user
														// question
			// votes table
			// where each question vote is kept with the
			// user and
			// the question

			sqlCommand = StringConstants.CREATE_USER_QUESTION_VOTES_TABLE;

			break;

		case StringConstants.USER_ANSWER_VOTES_TABLE: // setting a command to
														// create a user answer
			// votes table
			// where each answer vote is kept with the user
			// and the answer

			sqlCommand = StringConstants.CREATE_USER_ANSWER_VOTES_TABLE;

			break;

		case StringConstants.USER_TOPIC_RANKS_TABLE: // setting a command to
														// create a user topic
														// ranks
			// table
			// where each triple of user, topic and it's
			// rank is kept

			sqlCommand = StringConstants.CREATE_USER_TOPIC_RANKS_TABLE;

			break;

		default:
			sqlCommand = "";

		}

		// creating the table and closing the resources
		try {

			// trying to create the table
			System.out.println(sqlCommand);

			statement.executeUpdate(sqlCommand);

			System.out.println("created table: " + table);

		} // handling the exceptions
		catch (SQLException exception) {

			System.out.println(exception.getMessage());

			// getting the error code
			int errorCode = exception.getErrorCode();

			// if the error code means the table already exists
			// that's OK - otherwise - throw an exception
			if (errorCode != 30000)
				throw exception;

		}

	}

	// a method to insert a user to the users table
	public static void insertInto(String table, Connection connection, String... args)
			throws ClassNotFoundException, SQLException {

		PreparedStatement preparedStatement = null;

		// getting the SQL command from the table name
		String sqlCommand;

		switch (table) {

		case StringConstants.USERS_TABLE:

			// setting a command to insert a user to the users table
			sqlCommand = StringConstants.INSERT_USER;

			// preparing a statement
			preparedStatement = connection.prepareStatement(sqlCommand);

			// inserting the arguments to the statement
			for (int i = 1; i <= 5; i++)
				preparedStatement.setString(i, args[i - 1]);

			break;

		case StringConstants.QUESTIONS_TABLE:

			// setting a command to insert a question to the questions table
			sqlCommand = StringConstants.INSERT_QUESTION;

			// preparing a statement
			preparedStatement = connection.prepareStatement(sqlCommand);

			// inserting arguments to the statement
			for (int i = 0; i < 4; i++)
				preparedStatement.setString(i + 1, args[i]);

			break;

		case StringConstants.ANSWERS_TABLE:

			// setting a command to insert an answer to the answers table
			sqlCommand = StringConstants.INSERT_ANSWER;

			// preparing a statement
			preparedStatement = connection.prepareStatement(sqlCommand);

			// inserting arguments to the statement
			preparedStatement.setInt(1, Integer.parseInt(args[0]));
			preparedStatement.setString(2, args[1]);
			preparedStatement.setString(3, args[2]);
			preparedStatement.setString(4, args[3]);

			break;

		case StringConstants.TOPICS_TABLE:

			// setting a command to insert a topic to the topics table
			sqlCommand = StringConstants.INSERT_TOPIC;

			// preparing a statement
			preparedStatement = connection.prepareStatement(sqlCommand);

			// inserting arguments to the statement
			preparedStatement.setString(1, args[0]);

			break;

		case StringConstants.USER_QUESTION_VOTES_TABLE:

			// setting a command to insert a user question vote
			// to the user question votes table
			sqlCommand = StringConstants.INSERT_USER_QUESTION_VOTE;

			// preparing a statement
			preparedStatement = connection.prepareStatement(sqlCommand);

			// inserting arguments to the statement
			preparedStatement.setString(1, args[0]);
			preparedStatement.setInt(2, Integer.parseInt(args[1]));
			preparedStatement.setInt(3, Integer.parseInt(args[2]));

			break;

		case StringConstants.USER_ANSWER_VOTES_TABLE:

			// setting a command to insert a user answer vote
			// to the user answer votes table
			sqlCommand = StringConstants.INSERT_USER_ANSWER_VOTE;

			// preparing a statement
			preparedStatement = connection.prepareStatement(sqlCommand);

			// inserting arguments to the statement
			preparedStatement.setString(1, args[0]);
			preparedStatement.setInt(2, Integer.parseInt(args[1]));
			preparedStatement.setInt(3, Integer.parseInt(args[2]));

			break;

		case StringConstants.USER_TOPIC_RANKS_TABLE:

			// setting a command to insert a user topic rank
			// to the user topic ranks table
			sqlCommand = StringConstants.INSERT_USER_TOPIC_RANK;

			// preparing a statement
			preparedStatement = connection.prepareStatement(sqlCommand);

			// inserting arguments to the statement
			preparedStatement.setString(1, args[0]);
			preparedStatement.setString(2, args[1]);

			break;

		default:
			sqlCommand = "";

		}

		// executing the command and closing the resources
		preparedStatement.executeUpdate();
		preparedStatement.close();

	}

	// a method to delete a record from the database
	public static void deleteRecord(String table, String key, Statement statement)
			throws SQLException, ClassNotFoundException {

		// getting the key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the database
		statement.executeUpdate(String.format("DELETE FROM %s WHERE %s='%s'", table, keyColumn, key));

	}

	// a method to delete a record from the database
	public static void deleteRecord(String table, Integer key, Statement statement)
			throws SQLException, ClassNotFoundException {

		// getting the key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the database
		statement.executeUpdate(String.format("DELETE FROM %s WHERE %s=%s", table, keyColumn, key));

	}

	// a method to delete a record from the database with two uniquely
	// identifying keys
	public static void deleteRecord(String table, String firstKeyColumn, String firstKey, String secondKeyColumn,
			String secondKey, Statement statement) throws SQLException, ClassNotFoundException {

		// updating the database
		statement.executeUpdate(String.format("DELETE FROM %s WHERE %s='%s' AND %s='%s'", table, firstKeyColumn,
				firstKey, secondKeyColumn, secondKey));

	}

	// a method to delete a record from the database with two uniquely
	// identifying keys
	public static void deleteRecord(String table, String firstKeyColumn, String firstKey, String secondKeyColumn,
			Integer secondKey, Statement statement) throws SQLException, ClassNotFoundException {

		// updating the database
		statement.executeUpdate(String.format("DELETE FROM %s WHERE %s='%s' AND %s=%s", table, firstKeyColumn, firstKey,
				secondKeyColumn, secondKey));

	}

	// a method to delete a record from the database with two uniquely
	// identifying keys
	public static void deleteRecord(String table, String firstKeyColumn, Integer firstKey, String secondKeyColumn,
			String secondKey, Statement statement) throws SQLException, ClassNotFoundException {

		// updating the database
		statement.executeUpdate(String.format("DELETE FROM %s WHERE %s=%s AND %s=%s", table, firstKeyColumn, firstKey,
				secondKeyColumn, secondKey));

	}

	// a method to delete a record from the database with two uniquely
	// identifying keys
	public static void deleteRecord(String table, String firstKeyColumn, Integer firstKey, String secondKeyColumn,
			Integer secondKey, Statement statement) throws SQLException, ClassNotFoundException {

		// updating the database
		statement.executeUpdate(String.format("DELETE FROM %s WHERE %s=%s AND %s=%s", table, firstKeyColumn, firstKey,
				secondKeyColumn, secondKey));

	}

	// a method for updating a table in the database
	public static void updateDatabase(String key, String table, String column, String newValue, Statement statement)
			throws ClassNotFoundException, SQLException {

		// getting the key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the database
		statement.executeUpdate(
				String.format("UPDATE %s SET %s='%s' WHERE %s='%s'", table, column, newValue, keyColumn, key));

	}

	// a method for updating a table in the database
	public static void updateDatabase(String key, String table, String column, Integer newValue, Statement statement)
			throws ClassNotFoundException, SQLException {

		System.out.println("column = " + column);

		// getting the key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the database
		statement.executeUpdate(
				String.format("UPDATE %s SET %s=%s WHERE %s='%s'", table, column, newValue, keyColumn, key));

	}

	// a method for updating a table in the database
	public static void updateDatabase(String key, String table, String column, Double newValue, Statement statement)
			throws ClassNotFoundException, SQLException {

		System.out.println("column is " + column);

		// getting the key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the database
		statement.executeUpdate(
				String.format("UPDATE %s SET %s=%s WHERE %s='%s'", table, column, newValue, keyColumn, key));

	}

	// a method for updating a table in the database
	public static void updateDatabase(Integer key, String table, String column, String newValue, Statement statement)
			throws ClassNotFoundException, SQLException {

		// getting the key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the database
		statement.executeUpdate(
				String.format("UPDATE %s SET %s='%s' WHERE %s=%s", table, column, newValue, keyColumn, key));

	}

	// a method for updating a table in the database
	public static void updateDatabase(Integer key, String table, String column, Integer newValue, Statement statement)
			throws ClassNotFoundException, SQLException {

		// getting the key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the database
		statement.executeUpdate(
				String.format("UPDATE %s SET %s=%s WHERE %s=%s", table, column, newValue, keyColumn, key));

	}

	// a method for updating a table in the database
	public static void updateDatabase(Integer key, String table, String column, Double newValue, Statement statement)
			throws ClassNotFoundException, SQLException {

		// getting the key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the database
		statement.executeUpdate(
				String.format("UPDATE %s SET %s=%s WHERE %s=%s", table, column, newValue, keyColumn, key));

	}

	// a method for updating a table in the database
	public static void updateDatabase(String table, String firstKeyColumn, String firstKey, String secondKeyColumn,
			String secondKey, String column, Integer newValue, Statement statement)
					throws ClassNotFoundException, SQLException {

		// updating the database
		statement.executeUpdate(String.format("UPDATE %s SET %s=%s WHERE %s='%s' AND %s='%s'", table, column, newValue,
				firstKeyColumn, firstKey, secondKeyColumn, secondKey));

	}

	// a method for updating a table in the database
	public static void updateDatabase(String table, String firstKeyColumn, String firstKey, String secondKeyColumn,
			Integer secondKey, String column, Integer newValue, Statement statement)
					throws ClassNotFoundException, SQLException {

		// updating the database
		statement.executeUpdate(String.format("UPDATE %s SET %s=%s WHERE %s='%s' AND %s=%s", table, column, newValue,
				firstKeyColumn, firstKey, secondKeyColumn, secondKey));

	}

	// a method for getting a record from the database by its key - one column,
	// string key
	private static ResultSet getRecordFromKey(String table, String keyColumn, String key, Statement statement)
			throws SQLException, ClassNotFoundException {

		System.out.println("\n\nkey = " + key);
		System.out.println("table = " + table);
		System.out.println("key column = " + keyColumn);

		// getting the result from the parameters
		ResultSet record = statement.executeQuery(
				String.format("SELECT %s FROM %s WHERE %s='%s'", StringConstants.ALL, table, keyColumn, key));

		// returning the record
		return record;

	}

	// a method for getting a record from the database by its key - one column,
	// integer key
	private static ResultSet getRecordFromKey(String table, String keyColumn, Integer key, Statement statement)
			throws SQLException, ClassNotFoundException {

		// getting the result from the parameters
		ResultSet record = statement.executeQuery(
				String.format("SELECT %s FROM %s WHERE %s=%s", StringConstants.ALL, table, keyColumn, key));

		// returning the record
		return record;

	}

	// a method for getting a record from the database by its key - two columns,
	// first key is a string, second key is a string
	private static ResultSet getRecordFromPair(String table, String firstKeyColumn, String firstKey,
			String secondKeyColumn, String secondKey, Statement statement) throws SQLException, ClassNotFoundException {

		// getting the result from the parameters
		ResultSet record = statement.executeQuery(String.format("SELECT %s FROM %s WHERE %s='%s' AND %s='%s'",
				StringConstants.ALL, table, firstKeyColumn, firstKey, secondKeyColumn, secondKey));

		// returning the record
		return record;

	}

	// a method for getting a record from the database by its key - two columns,
	// first key is a string, second key is an integer
	private static ResultSet getRecordFromPair(String table, String firstKeyColumn, String firstKey,
			String secondKeyColumn, Integer secondKey, Statement statement)
					throws SQLException, ClassNotFoundException {

		// getting the result from the parameters
		ResultSet record = statement.executeQuery(String.format("SELECT %s FROM %s WHERE %s='%s' AND %s=%s",
				StringConstants.ALL, table, firstKeyColumn, firstKey, secondKeyColumn, secondKey));

		// returning the record
		return record;

	}

	// a method for getting a record from the database by its key - two columns,
	// first key is an integer, second key is a string
	private static ResultSet getRecordFromPair(String table, String firstKeyColumn, Integer firstKey,
			String secondKeyColumn, String secondKey, Statement statement) throws SQLException, ClassNotFoundException {

		// getting the result from the parameters
		ResultSet record = statement.executeQuery(String.format("SELECT %s FROM %s WHERE %s=%s AND %s='%s'",
				StringConstants.ALL, table, firstKeyColumn, firstKey, secondKeyColumn, secondKey));

		// returning the record
		return record;

	}

	// a method for getting a record from the database by its key - two columns,
	// first key is an integer, second key is an integer
	private static ResultSet getRecordFromPair(String table, String firstKeyColumn, Integer firstKey,
			String secondKeyColumn, Integer secondKey, Statement statement)
					throws SQLException, ClassNotFoundException {

		// getting the result from the parameters
		ResultSet record = statement.executeQuery(String.format("SELECT %s FROM %s WHERE %s=%s AND %s=%s",
				StringConstants.ALL, table, firstKeyColumn, firstKey, secondKeyColumn, secondKey));

		// returning the record
		return record;

	}

	// a method to update the user rating
	private static void updateUserRating(String username, Statement statement)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// update the user rating
		updateDatabase(username, StringConstants.USERS_TABLE, StringConstants.RATING,
				computeUserRating(username, statement), statement);

	}

	// a method to update the question rating
	private static void updateQuestionRating(int questionId, Statement statement)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// updating the question rating
		updateDatabase(questionId, StringConstants.QUESTIONS_TABLE, StringConstants.RATING,
				computeQuestionRating(questionId, statement), statement);

	}

	// a method to compute the question rating
	private static double computeQuestionRating(int questionId, Statement statement)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// getting the question voting score
		int questionVotingScore = Integer.parseInt(getColumnFromKey(StringConstants.QUESTION_VOTING_SCORE,
				StringConstants.ID, questionId, StringConstants.QUESTIONS_TABLE, statement));

		// getting the number of answers
		int numberOfAnswers = Integer.parseInt(getColumnFromKey(StringConstants.QUESTION_ANSWERS_NUMBER,
				StringConstants.ID, questionId, StringConstants.QUESTIONS_TABLE, statement));

		// getting the question rating sum of the question answers
		int answersRatingSum = Integer.parseInt(getColumnFromKey(StringConstants.QUESTION_ANSWERS_RATING_SUM,
				StringConstants.ID, questionId, StringConstants.QUESTIONS_TABLE, statement));

		// calculating the user rating via the project formula

		/*
		 * here we make an assumption: if the question doesn't have any answers,
		 * then the amount that is added to the question rating for answers will
		 * be zero
		 */

		double amountToAddForAnswers = numberOfAnswers == 0 ? 0 : (0.8 * answersRatingSum) / numberOfAnswers;

		double amountToAddForQuestionVotingScore = 0.2 * questionVotingScore;

		// calculating the question rating via the project formula
		double questionRating = amountToAddForQuestionVotingScore + amountToAddForAnswers;

		// returning the question rating
		return questionRating;

	}

	// a method to compute the user rating
	private static double computeUserRating(String username, Statement statement)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// getting the sum of the questions rating scores
		// that this user has asked
		int userQuestionsRatingSum = Integer.parseInt(getColumnFromKey(StringConstants.USER_QUESTIONS_RATING_SUM,
				StringConstants.USERNAME, username, StringConstants.USERS_TABLE, statement));

		// getting the number of questions that this user has asked
		int userQuestionsNumber = Integer.parseInt(getColumnFromKey(StringConstants.USER_QUESTIONS_NUMBER,
				StringConstants.USERNAME, username, StringConstants.USERS_TABLE, statement));

		// getting the sum of the answers rating scores
		// that this user has answered
		int userAnswersRatingSum = Integer.parseInt(getColumnFromKey(StringConstants.USER_ANSWERS_RATING_SUM,
				StringConstants.USERNAME, username, StringConstants.USERS_TABLE, statement));

		// getting the number of questions that this user has asked
		int userAnswersNumber = Integer.parseInt(getColumnFromKey(StringConstants.USER_ANSWERS_NUMBER,
				StringConstants.USERNAME, username, StringConstants.USERS_TABLE, statement));

		System.out.println("after getting all that is needed:");
		System.out.println("current questions number = " + userQuestionsNumber);
		System.out.println("current answers number = " + userAnswersNumber);
		System.out.println("current questions rating sum = " + userQuestionsRatingSum);
		System.out.println("current answers rating sum = " + userAnswersRatingSum);

		// calculating the user rating via the project formula

		/*
		 * here we make an assumption: if the user didn't submit any questions
		 * or answers, then the amount that is added to the user rating for the
		 * appropriate column (answers or questions) will be zero
		 */

		double amountToAddForQuestions = userQuestionsNumber == 0 ? 0
				: (0.2 * userQuestionsRatingSum) / userQuestionsNumber;

		double amountToAddForAnswers = userAnswersNumber == 0 ? 0 : (0.8 * userAnswersRatingSum) / userAnswersNumber;

		double userRating = amountToAddForQuestions + amountToAddForAnswers;

		System.out.println("current rating is now " + userRating);

		// returning the user rating
		return userRating;

	}

	// a method for adding change to an integer column in a record
	private static void updateColumn(Integer key, int columnChange, String column, String table, Statement statement)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		switch (table) {

		case StringConstants.QUESTIONS_TABLE:

			// updating the question

			updateQuestion(key, column, columnChange, statement);

			break;

		case StringConstants.ANSWERS_TABLE:

			// updating the answer

			updateAnswer(key, column, columnChange, statement);

		}

	}

	// a method for checking the existence of a key in a table
	public static boolean doesExistIn(String key, String table, Statement statement)
			throws ClassNotFoundException, SQLException {

		// getting the key column name from the table
		String keyColumn = keyColumnFinder.get(table);

		// get the record from its key
		ResultSet record = getRecordFromKey(table, keyColumn, key, statement);

		// returning if the record has any elements
		return record.next();

	}

	// a method for checking the existence of a key in a table
	public static boolean doesExistIn(Integer key, String table, Statement statement)
			throws ClassNotFoundException, SQLException {

		// getting the key column name from the table
		String keyColumn = keyColumnFinder.get(table);

		// get the record from its key
		ResultSet record = getRecordFromKey(table, keyColumn, key, statement);

		// returning if the record was empty
		return record.next();

	}

	// a method for checking the existence of a unique pair in a table
	public static boolean doesExistIn(String table, String firstKeyColumn, String firstKey, String secondKeyColumn,
			String secondKey, Statement statement) throws ClassNotFoundException, SQLException {

		// getting a record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// returning true if the record was not empty
		return record.next();

	}

	// a method for checking the existence of a unique pair in a table
	public static boolean doesExistIn(String table, String firstKeyColumn, String firstKey, String secondKeyColumn,
			Integer secondKey, Statement statement) throws ClassNotFoundException, SQLException {

		// getting a record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// returning true if the record was not empty
		return record.next();

	}

	// a method for checking the existence of a unique pair in a table
	public static boolean doesExistIn(String table, String firstKeyColumn, Integer firstKey, String secondKeyColumn,
			String secondKey, Statement statement) throws ClassNotFoundException, SQLException {

		// getting a record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// returning true if the record was not empty
		return record.next();

	}

	// a method for checking the existence of a unique pair in a table
	public static boolean doesExistIn(String table, String firstKeyColumn, Integer firstKey, String secondKeyColumn,
			Integer secondKey, Statement statement) throws ClassNotFoundException, SQLException {

		// getting a record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// returning true if the record was not empty
		return record.next();

	}

	public static boolean doesMatchPassword(User user, Statement statement)
			throws ClassNotFoundException, SQLException {

		// declaring a result set object to be used
		ResultSet record = null;

		// selecting this user from the users table
		record = getRecordFromKey(StringConstants.USERS_TABLE, StringConstants.USERNAME, user.getName(), statement);

		// moving the cursor to the password we got
		record.next();

		// returning true if and only if the passwords match
		return record.getString(StringConstants.USER_PASSWORD).equals(user.getPassword());

	}

	// a method for getting a column data from a unique pair
	public static String getColumnFromPair(String column, String firstKeyColumn, String firstKey,
			String secondKeyColumn, String secondKey, String table, Statement statement)
					throws ClassNotFoundException, SQLException {

		// getting the record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// getting the request column from the record
		record.next();
		return record.getString(column);

	}

	// a method for getting a column data from a unique pair
	public static String getColumnFromPair(String column, String firstKeyColumn, String firstKey,
			String secondKeyColumn, Integer secondKey, String table, Statement statement)
					throws ClassNotFoundException, SQLException {

		// getting the record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// getting the request column from the record
		record.next();
		return record.getString(column);

	}

	// a method for getting a column data from a unique pair
	public static String getColumnFromPair(String column, String firstKeyColumn, Integer firstKey,
			String secondKeyColumn, String secondKey, String table, Statement statement)
					throws ClassNotFoundException, SQLException {

		// getting the record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// getting the request column from the record
		record.next();
		return record.getString(column);

	}

	// a method for getting a column data from a unique pair
	public static String getColumnFromPair(String column, String firstKeyColumn, Integer firstKey,
			String secondKeyColumn, Integer secondKey, String table, Statement statement)
					throws ClassNotFoundException, SQLException {

		// getting the record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// getting the request column from the record
		record.next();
		return record.getString(column);

	}

	// a method for getting a data in a column from a string key
	public static String getColumnFromKey(String column, String keyColumn, String key, String table,
			Statement statement) throws ClassNotFoundException, SQLException {

		// getting the record from the key
		ResultSet record = getRecordFromKey(table, keyColumn, key, statement);

		// getting the request column from the record
		record.next();
		return record.getString(column);

	}

	// a method for getting a data in a column from an integer key
	public static String getColumnFromKey(String column, String keyColumn, Integer key, String table,
			Statement statement) throws ClassNotFoundException, SQLException {

		// getting the record from the key
		ResultSet record = getRecordFromKey(table, keyColumn, key, statement);

		// getting the request column from the record
		record.next();

		System.out.println(key);

		return record.getString(column);

	}

	// a method for adding all the user-topic pairs to
	// the user topic ranks table, when a new user is
	// added to the database
	public static void addUserTopicPairs(User user, Connection connection, Statement statement)
			throws SQLException, ClassNotFoundException {

		// getting a result set containing all the topics
		ResultSet topics = statement.executeQuery(
				String.format(StringConstants.SELECT, StringConstants.TOPIC_NAME, StringConstants.TOPICS_TABLE));

		// moving through all the topics, inserting each one,
		// with the user, to the user topic ranks table, with rank 0,
		// since this is a new user

		while (topics.next())
			insertInto(StringConstants.USER_TOPIC_RANKS_TABLE, connection, user.getName(),
					topics.getString(StringConstants.TOPIC_NAME), "0");

	}

	// a method to change the user answers voting score
	public static void addUserAnswersVotingScore(String username, int scoreChange, Statement statement)
			throws ClassNotFoundException, SQLException {

		// update the user answers rating sum
		updateUser(username, StringConstants.USER_ANSWERS_RATING_SUM, scoreChange, statement);

	}

	// a method to add a user question voting score sum
	public static void addUserQuestionsVotingScore(String username, int scoreChange, Statement statement)
			throws ClassNotFoundException, SQLException {

		// update the user questions rating sum
		updateUser(username, StringConstants.USER_QUESTIONS_RATING_SUM, scoreChange, statement);

	}

	// a method for adding 1 to the user submitted answers number
	public static void addUserAnswersNumber(String username, Statement statement)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// updating the questions number column
		updateUser(username, StringConstants.USER_ANSWERS_NUMBER, 1, statement);

	}

	// a method for adding 1 to the user submitted questions number
	public static void addUserQuestionsNumber(String username, Statement statement)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// updating the questions number column
		updateUser(username, StringConstants.USER_QUESTIONS_NUMBER, 1, statement);

	}

	// a method for adding all the user-topic pairs to
	// the user topic ranks table, when a new topic is
	// added to the database
	public static void addUserTopicPairs(String topic, Connection connection, Statement statement)
			throws ClassNotFoundException, SQLException {

		// getting a result set containing all the user names
		ResultSet users = statement.executeQuery(
				String.format(StringConstants.SELECT, StringConstants.USERNAME, StringConstants.USERS_TABLE));

		// moving through all the user names, inserting each one,
		// with the topic, to the user topic ranks table, with rank 0,
		// since this is a new topic
		while (users.next())
			insertInto(StringConstants.USER_TOPIC_RANKS_TABLE, connection, users.getString(StringConstants.USERNAME),
					topic, "0");

	}

	// a method for changing the voting score sum of the answers of a question
	public static void addQuestionAnswersVotingScore(int questionId, int scoreChange, Statement statement)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// updating the question score
		updateColumn(questionId, scoreChange, StringConstants.QUESTION_ANSWERS_RATING_SUM,
				StringConstants.QUESTIONS_TABLE, statement);

	}

	// a method for changing the voting score of a question
	public static void addQuestionVotingScore(int questionId, int scoreChange, Statement statement)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// updating the question
		updateQuestion(questionId, StringConstants.QUESTION_VOTING_SCORE, scoreChange, statement);

	}

	// a method for updating an answer in the answers table
	private static void updateAnswer(int answerId, String column, int columnChange, Statement statement)
			throws ClassNotFoundException, SQLException {

		// getting the author name and the question id of the question that
		// this answer was submitted to

		String username = getColumnFromKey(StringConstants.AUTHOR, StringConstants.ID, answerId,
				StringConstants.ANSWERS_TABLE, statement);

		int questionId = Integer.parseInt(getColumnFromKey(StringConstants.QUESTION_ID, StringConstants.ID, answerId,
				StringConstants.ANSWERS_TABLE, statement));

		// updating the answer itself

		int currentColumnValue = Integer.parseInt(
				getColumnFromKey(column, StringConstants.ID, answerId, StringConstants.ANSWERS_TABLE, statement));

		int updatedColumnValue = currentColumnValue + columnChange;

		updateDatabase(answerId, StringConstants.ANSWERS_TABLE, column, updatedColumnValue, statement);

		// updating the user topic ranks table

		updateUserTopicRanks(username, questionId, columnChange, statement);

		// updating the author of this answer

		updateUser(username, StringConstants.USER_ANSWERS_RATING_SUM, columnChange, statement);

		// updating the question that this answer was submitted to

		updateQuestion(questionId, StringConstants.QUESTION_ANSWERS_RATING_SUM, columnChange, statement);

	}

	// a method for updating a user in the users table
	private static void updateUser(String username, String column, int columnChange, Statement statement)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// updating the user

		int currentColumnValue = Integer.parseInt(
				getColumnFromKey(column, StringConstants.USERNAME, username, StringConstants.USERS_TABLE, statement));

		int updatedColumnValue = currentColumnValue + columnChange;

		System.out.println("current = " + currentColumnValue + "\n");
		System.out.println("updated = " + updatedColumnValue + "\n");

		updateDatabase(username, StringConstants.USERS_TABLE, column, updatedColumnValue, statement);

		System.out.println("after updating the user questions number to " + updatedColumnValue);

		updateUserRating(username, statement);

	}

	// a method for updating a question in the questions table
	private static void updateQuestion(int questionId, String column, int columnChange, Statement statement)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// updating the question and getting the question rating change

		System.out.println("before any update");

		int currentColumnValue = Integer.parseInt(
				getColumnFromKey(column, StringConstants.ID, questionId, StringConstants.QUESTIONS_TABLE, statement));

		double beforeQuestionRating = Double.parseDouble(getColumnFromKey(StringConstants.RATING, StringConstants.ID,
				questionId, StringConstants.QUESTIONS_TABLE, statement));

		System.out.println("before adding the question " + column + " and rating");

		updateDatabase(questionId, StringConstants.QUESTIONS_TABLE, column, currentColumnValue + columnChange,
				statement);

		updateQuestionRating(questionId, statement);

		System.out.println("after updating the question " + column + " and rating");

		double afterQuestionRating = Double.parseDouble(getColumnFromKey(StringConstants.RATING, StringConstants.ID,
				questionId, StringConstants.QUESTIONS_TABLE, statement));

		double questionRatingChange = afterQuestionRating - beforeQuestionRating;

		// updating the user question voting score sum if the column is question
		// voting score

		if (column == StringConstants.QUESTION_VOTING_SCORE) {

			String username = getColumnFromKey(StringConstants.AUTHOR, StringConstants.ID, questionId,
					StringConstants.QUESTIONS_TABLE, statement);

			updateUser(username, StringConstants.USER_QUESTIONS_RATING_SUM, columnChange, statement);

		}

		// updating the topic popularity of topics in this question

		String[] topics = getTopicsFromQuestion(questionId, statement);

		for (String topic : topics) {

			double currentTopicPopularity = Double.parseDouble(getColumnFromKey(StringConstants.TOPIC_POPULARITY,
					StringConstants.TOPIC_NAME, topic, StringConstants.TOPICS_TABLE, statement));

			updateDatabase(topic, StringConstants.TOPICS_TABLE, StringConstants.TOPIC_POPULARITY,
					currentTopicPopularity + questionRatingChange, statement);

		}

	}

	// a method for adding 1 to the question submitted answers number
	public static void addQuestionAnswersNumber(int questionId, Statement statement)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// updating the question
		updateQuestion(questionId, StringConstants.QUESTION_ANSWERS_NUMBER, 1, statement);

	}

	// a method for changing the voting score of an answer
	public static void addAnswerVotingScore(int answerId, int scoreChange, Statement statement)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// updating the answer
		updateAnswer(answerId, StringConstants.RATING, scoreChange, statement);

	}

	// a method for getting an array list of strings containing all the topics
	// of a given question
	private static String[] getTopicsFromQuestion(int questionId, Statement statement)
			throws SQLException, ClassNotFoundException {

		String questionTopics = getColumnFromKey(StringConstants.QUESTION_TOPICS,
				keyColumnFinder.get(StringConstants.QUESTIONS_TABLE), questionId, StringConstants.QUESTIONS_TABLE,
				statement);

		if (questionTopics.length() != 0)
			return questionTopics.substring(1).split(StringConstants.TOPIC_DIVIDER);

		return new String[0];

	}

	// a method for updating the user topic ranks of a given user
	// and all the topics of a given question
	private static void updateUserTopicRanks(String username, int questionId, int rankChange, Statement statement)
			throws ClassNotFoundException, SQLException {

		// getting the topics from this question
		String[] topics = getTopicsFromQuestion(questionId, statement);

		// for every topic in this array list, update the user topic rank
		for (String topic : topics) {

			int currentRank = Integer.parseInt(getColumnFromPair(StringConstants.USER_TOPIC_RANK_RANK,
					StringConstants.USERNAME, username, StringConstants.USER_TOPIC_RANK_TOPIC_NAME, topic,
					StringConstants.USER_TOPIC_RANKS_TABLE, statement));

			int updatedRank = currentRank + rankChange;

			updateDatabase(StringConstants.USER_TOPIC_RANKS_TABLE, StringConstants.USERNAME, username,
					StringConstants.USER_TOPIC_RANK_TOPIC_NAME, topic, StringConstants.USER_TOPIC_RANK_RANK,
					updatedRank, statement);

		}

	}

	// a method for getting the new questions in the parameter page
	public static Question[] getNewQuestions(int pageNum, String username, Connection connection,
			HttpServletResponse response) throws ClassNotFoundException, SQLException {

		Statement statement = connection.createStatement();

		// getting a result set containing the questions sorted
		// in descending order by their id (time of submission

		ResultSet sortedQuestions = statement
				.executeQuery(String.format("SELECT %s FROM %s WHERE " + "%s=0 ORDER BY %s DESC", StringConstants.ALL,
						StringConstants.QUESTIONS_TABLE, StringConstants.QUESTION_ANSWERS_NUMBER, StringConstants.ID));

		int startIndex = (pageNum - 1) * 20 + 1;
		for (int i = 1; i < startIndex; i++)
			sortedQuestions.next();

		// putting the needed questions into an array list of questions

		ArrayList<Question> neededQuestionsArrayList = new ArrayList<Question>();

		int isLastPage = 0;
		
		for (int i = 0; i < 20; i++) {

			if (sortedQuestions.next() == false) {
				isLastPage = 1;
				break;
			}

			neededQuestionsArrayList.add(buildQuestionFromRecord(sortedQuestions, username, connection));

		}

		ClientInteractor.sendData(response, "{ \"isLastPage\": " + isLastPage + ", \"questions\": ");
		
		statement.close();

		return neededQuestionsArrayList.toArray(new Question[neededQuestionsArrayList.size()]);

	}

	// a method for getting the existing questions in the parameter page
	public static Question[] getExistingQuestions(int pageNumber, String username, Connection connection,
			HttpServletResponse response) throws ClassNotFoundException, SQLException {

		Statement statement = connection.createStatement();

		// getting a result set containing the questions sorted
		// in descending order by their rating

		ResultSet sortedQuestions = statement.executeQuery(String.format("SELECT %s FROM %s ORDER BY %s DESC",
				StringConstants.ALL, StringConstants.QUESTIONS_TABLE, StringConstants.RATING));

		int startIndex = (pageNumber - 1) * 20 + 1;
		for (int i = 1; i < startIndex; i++)
			sortedQuestions.next();

		// putting the needed questions into an array list of questions

		ArrayList<Question> neededQuestionsArrayList = new ArrayList<Question>();

		int isLastPage = 0;
		
		for (int i = 0; i < 20; i++) {

			if (sortedQuestions.next() == false) {
				isLastPage = 1;
				break;
			}

			neededQuestionsArrayList.add(buildQuestionFromRecord(sortedQuestions, username, connection));

		}
		
		ClientInteractor.sendData(response, "{ \"isLastPage\": " + isLastPage + ", \"questions\": ");

		statement.close();

		return neededQuestionsArrayList.toArray(new Question[neededQuestionsArrayList.size()]);

	}

	// a method for getting the top 20 users
	public static User[] getUsersLeaderBoard(String requestingUsername, Connection connection,
			HttpServletResponse response) throws SQLException, ClassNotFoundException {

		Statement statement = connection.createStatement();

		// getting a result set containing the users sorted
		// in descending order by their rating

		ResultSet sortedUsers = statement.executeQuery(String.format("SELECT %s FROM %s ORDER BY %s DESC",
				StringConstants.ALL, StringConstants.USERS_TABLE, StringConstants.RATING));

		// putting the needed users into an array list of users

		ArrayList<User> neededUsersArrayList = new ArrayList<User>();

		for (int i = 0; i < 20; i++) {

			if (sortedUsers.next() == false) {
				//ClientInteractor.sendStatus(response, 3);
				break;
			}

			neededUsersArrayList.add(buildUserFromRecord(sortedUsers, requestingUsername, connection));

		}

		statement.close();

		return neededUsersArrayList.toArray(new User[neededUsersArrayList.size()]);

	}

	// a method for getting the top topics
	public static Topic[] getTopTopics(int pageNumber, Statement statement, HttpServletResponse response)
			throws SQLException, ClassNotFoundException {

		// getting a result set containing the topics, sorted descending order
		// by their rating

		ResultSet sortedTopics = statement.executeQuery(String.format("SELECT %s FROM %s ORDER BY %s DESC",
				StringConstants.ALL, StringConstants.TOPICS_TABLE, StringConstants.TOPIC_POPULARITY));

		int startIndex = (pageNumber - 1) * 20 + 1;
		for (int i = 1; i < startIndex; i++)
			sortedTopics.next();

		// putting the needed topics into an array list of topics

		ArrayList<Topic> neededTopicsArrayList = new ArrayList<Topic>();

		int isLastPage = 0;
		
		for (int i = 0; i < 20; i++) {

			if (sortedTopics.next() == false) {
				isLastPage = 1;
				break;
			}

			neededTopicsArrayList.add(buildTopicFromRecord(sortedTopics));

		}

		ClientInteractor.sendData(response, " { \"isLastPage\": " + isLastPage + ", \"topics\": ");
		
		return neededTopicsArrayList.toArray(new Topic[neededTopicsArrayList.size()]);

	}

	// a method for getting the user summary
	public static User getUserSummary(String username, String requestingUsername, Connection connection)
			throws ClassNotFoundException, SQLException {

		Statement statement = connection.createStatement();

		// getting the user record from the user name

		ResultSet userRecord = getRecordFromKey(StringConstants.USERS_TABLE, StringConstants.USERNAME, username,
				statement);
		userRecord.next();

		// building the user from its record

		User user = buildUserFromRecord(userRecord, requestingUsername, connection);

		statement.close();

		// returning the user

		return user;

	}

	// a method for building a user object from a user record
	private static User buildUserFromRecord(ResultSet userRecord, String requestingUsername, Connection connection)
			throws SQLException, ClassNotFoundException {

		System.out.println("is closed ? " + userRecord.isClosed());

		Statement statement = connection.createStatement();

		System.out.println("is closed after creating statement ? " + userRecord.isClosed());

		// making the user object

		User userToBuild = new User();

		// setting the user properties

		userToBuild.setName(userRecord.getString(StringConstants.USERNAME));

		userToBuild.setImage(userRecord.getString(StringConstants.USER_IMAGE_URL));

		userToBuild.setNickname(userRecord.getString(StringConstants.USER_NICKNAME));

		userToBuild.setDescription(userRecord.getString(StringConstants.USER_SHORT_DESCRIPTION));

		userToBuild.setRating(userRecord.getDouble(StringConstants.RATING));

		// getting the user 5 top topics

		ArrayList<Topic> userExpertiseProfile = new ArrayList<Topic>();

		ResultSet userExpertiseProfileResultSet = statement
				.executeQuery(String.format("SELECT %s FROM %s WHERE %s='%s' ORDER BY %s DESC", StringConstants.ALL,
						StringConstants.USER_TOPIC_RANKS_TABLE, StringConstants.USERNAME, userToBuild.getName(),
						StringConstants.USER_TOPIC_RANK_RANK));

		if (userExpertiseProfileResultSet.next()) {

			for (int i = 0; i < 5; i++) {

				Topic topicToAdd = new Topic();

				topicToAdd.setName(userExpertiseProfileResultSet.getString(StringConstants.USER_TOPIC_RANK_TOPIC_NAME));

				userExpertiseProfile.add(topicToAdd);

				if (!userExpertiseProfileResultSet.next())
					break;

			}

		}

		userToBuild.setExpertiseProfile(userExpertiseProfile.toArray(new Topic[userExpertiseProfile.size()]));

		// getting the user last asked questions

		Question[] lastAskedQuestions = getLastAskedQuestions(userToBuild.getName(), 5, requestingUsername, connection);

		userToBuild.setLastAskedQuestions(lastAskedQuestions);

		// getting the user last answered answers

		Answer[] lastAnsweredAnswers = getLastAnsweredAnswers(userToBuild.getName(), 5, requestingUsername, connection);

		// getting the questions which the user last answered answers were
		// submitted to

		Question[] lastAnsweredQuestions = new Question[lastAnsweredAnswers.length];

		for (int i = 0; i < lastAnsweredQuestions.length; i++) {

			int questionId = Integer.parseInt(getColumnFromKey(StringConstants.QUESTION_ID, StringConstants.ID,
					lastAnsweredAnswers[i].getId(), StringConstants.ANSWERS_TABLE, statement));

			ResultSet questionRecord = getRecordFromKey(StringConstants.QUESTIONS_TABLE, StringConstants.ID, questionId,
					statement);
			questionRecord.next();

			lastAnsweredQuestions[i] = buildQuestionFromRecord(questionRecord, requestingUsername, connection);

			Answer[] questionAnswers = new Answer[1];
			questionAnswers[0] = lastAnsweredAnswers[i];

			lastAnsweredQuestions[i].setAnswers(questionAnswers);

		}

		userToBuild.setLastAnsweredQuestions(lastAnsweredQuestions);

		return userToBuild;

	}

	// a method for building a question object from a question record and a user
	// name
	// (for checking what the vote should display)
	private static Question buildQuestionFromRecord(ResultSet questionRecord, String requestingUsername,
			Connection connection) throws SQLException, ClassNotFoundException {

		Statement statement = connection.createStatement();

		// making the question object

		Question questionToBuild = new Question();

		questionToBuild.setId(questionRecord.getInt(StringConstants.ID));

		questionToBuild.setTimeStamp(questionRecord.getString(StringConstants.TIMESTAMP));

		questionToBuild.setText(questionRecord.getString(StringConstants.TEXT));

		String topicsString = questionRecord.getString(StringConstants.QUESTION_TOPICS);

		Topic[] topics;

		if (topicsString.length() != 0) {

			String[] topicsArrayString = topicsString.substring(1).split(StringConstants.TOPIC_DIVIDER);
			System.out.println("\nnumber of topics = " + topicsArrayString.length);
			for (String s : topicsArrayString)
				System.out.println(s);
			topics = new Topic[topicsArrayString.length];
			for (int j = 0; j < topicsArrayString.length; j++) {
				topics[j] = new Topic();
				topics[j].setName(topicsArrayString[j]);
			}

		} else {

			topics = new Topic[0];

		}

		System.out.println("is closed before ? " + questionRecord.isClosed());

		questionToBuild.setTopics(topics);

		questionToBuild.setAuthorUsername(questionRecord.getString(StringConstants.AUTHOR));

		questionToBuild.setAuthorNickname(getColumnFromKey(StringConstants.USER_NICKNAME, StringConstants.USERNAME,
				questionToBuild.getAuthorUsername(), StringConstants.USERS_TABLE, statement));

		questionToBuild.setRating(questionRecord.getInt(StringConstants.QUESTION_VOTING_SCORE));

		// if the user voted for this question, get the vote and set to the
		// question

		if (doesExistIn(StringConstants.USER_QUESTION_VOTES_TABLE, StringConstants.USERNAME, requestingUsername,
				StringConstants.QUESTION_ID, questionToBuild.getId(), statement)) {

			questionToBuild.setVote(Integer.parseInt(getColumnFromPair(StringConstants.VOTE, StringConstants.USERNAME,
					requestingUsername, StringConstants.QUESTION_ID, questionToBuild.getId(),
					StringConstants.USER_QUESTION_VOTES_TABLE, statement)));

		} else {

			questionToBuild.setVote(0);

		}

		// getting the question answers

		ResultSet questionAnswers = statement.executeQuery(String.format(
				"SELECT %s FROM %s WHERE %s=%d ORDER BY %s DESC", StringConstants.ALL, StringConstants.ANSWERS_TABLE,
				StringConstants.QUESTION_ID, questionToBuild.getId(), StringConstants.RATING));

		System.out.println("question answers closed ? " + questionAnswers.isClosed());

		ArrayList<Answer> questionAnswersArrayList = new ArrayList<Answer>();

		while (questionAnswers.next()) {

			questionAnswersArrayList.add(buildAnswerFromRecord(questionAnswers, requestingUsername, connection));

		}

		questionToBuild.setAnswers(questionAnswersArrayList.toArray(new Answer[questionAnswersArrayList.size()]));

		System.out.println("is closed ? " + questionRecord.isClosed());

		statement.close();

		return questionToBuild;

	}

	// a method for building an answer object from an answer record
	private static Answer buildAnswerFromRecord(ResultSet answerRecord, String requestingUsername,
			Connection connection) throws SQLException, ClassNotFoundException {

		Statement statement = connection.createStatement();

		Answer answerToBuild = new Answer();

		answerToBuild.setId(answerRecord.getInt(StringConstants.ID));

		answerToBuild.setText(answerRecord.getString(StringConstants.TEXT));

		answerToBuild.setTimeStamp(answerRecord.getString(StringConstants.TIMESTAMP));

		answerToBuild.setRating(answerRecord.getInt(StringConstants.RATING));

		answerToBuild.setAuthorNickname(getColumnFromKey(StringConstants.USER_NICKNAME, StringConstants.USERNAME,
				answerRecord.getString(StringConstants.AUTHOR), StringConstants.USERS_TABLE, statement));

		answerToBuild.setAuthorUsername(answerRecord.getString(StringConstants.AUTHOR));

		answerToBuild.setQuestionId(answerRecord.getInt(StringConstants.QUESTION_ID));

		// if the user voted for this answer, get the vote and set to the
		// answer

		if (doesExistIn(StringConstants.USER_ANSWER_VOTES_TABLE, StringConstants.USERNAME, requestingUsername,
				StringConstants.ANSWER_ID, answerToBuild.getId(), statement)) {

			answerToBuild.setVote(Integer.parseInt(getColumnFromPair(StringConstants.VOTE, StringConstants.USERNAME,
					requestingUsername, StringConstants.ANSWER_ID, answerToBuild.getId(),
					StringConstants.USER_ANSWER_VOTES_TABLE, statement)));

		}

		else {

			answerToBuild.setVote(0);

		}

		statement.close();

		return answerToBuild;

	}

	// a method for getting a topic object from a topic record
	private static Topic buildTopicFromRecord(ResultSet topicRecord) throws SQLException {

		// building the topic object

		Topic topicToBuild = new Topic();

		topicToBuild.setName(topicRecord.getString(StringConstants.TOPIC_NAME));

		// returning the topic object

		return topicToBuild;

	}

	// a method for getting the last amount of asked questions by a given user
	private static Question[] getLastAskedQuestions(String username, int amount, String requestionUsername,
			Connection connection) throws ClassNotFoundException, SQLException {

		Statement statement = connection.createStatement();

		// getting a question a result set of questions asked by this user name,
		// sorted by their id (from newest to oldest)
		ResultSet lastAskedQuestionsSet = statement.executeQuery(
				String.format("SELECT %s FROM " + "%s WHERE %s='%s' ORDER BY %s DESC", StringConstants.ALL,
						StringConstants.QUESTIONS_TABLE, StringConstants.AUTHOR, username, StringConstants.ID));

		// getting an array list of questions from this result set

		ArrayList<Question> lastAskedQuestionsList = new ArrayList<Question>();

		// putting the questions to the array list

		if (lastAskedQuestionsSet.next()) {

			for (int i = 0; i < amount; i++) {

				lastAskedQuestionsList
						.add(buildQuestionFromRecord(lastAskedQuestionsSet, requestionUsername, connection));

				if (lastAskedQuestionsSet.next() == false)
					break;

			}

		}

		statement.close();

		return lastAskedQuestionsList.toArray(new Question[lastAskedQuestionsList.size()]);

	}

	// a method for getting the last answered answers of a given user
	private static Answer[] getLastAnsweredAnswers(String username, int amount, String requestingUsername,
			Connection connection) throws SQLException, ClassNotFoundException {

		Statement statement = connection.createStatement();

		// getting a result set of answers answered by this user,
		// sorted by their id (from newest to oldest)
		ResultSet lastAnsweredAnswersSet = statement.executeQuery(
				String.format("SELECT %s FROM " + "%s WHERE %s='%s' ORDER BY %s DESC", StringConstants.ALL,
						StringConstants.ANSWERS_TABLE, StringConstants.AUTHOR, username, StringConstants.ID));

		// getting an array list of answers from this result set

		ArrayList<Answer> lastAnsweredAnswers = new ArrayList<Answer>();

		// putting the answers from the record to the answers array list

		if (lastAnsweredAnswersSet.next()) {

			for (int i = 0; i < amount; i++) {

				lastAnsweredAnswers.add(buildAnswerFromRecord(lastAnsweredAnswersSet, requestingUsername, connection));

				if (lastAnsweredAnswersSet.next() == false)
					break;

			}

		}

		statement.close();

		return lastAnsweredAnswers.toArray(new Answer[lastAnsweredAnswers.size()]);

	}

	// a method for getting the top questions by topic
	public static Question[] getTopQuestionsByTopic(String topic, int pageNumber, String requestingUsername,
			Connection connection, HttpServletResponse response) throws ClassNotFoundException, SQLException {

		Statement statement = connection.createStatement();

		// getting a questions result set where the questions have the given
		// topic
		// in their topics property, and the questions are given sorted by their
		// question rating

		ResultSet sortedQuestionsByTopicSet = statement.executeQuery(String.format(
				"SELECT %s FROM %s WHERE %s like '%%#%s%%' ORDER BY %s DESC", StringConstants.ALL,
				StringConstants.QUESTIONS_TABLE, StringConstants.QUESTION_TOPICS, topic, StringConstants.RATING));

		int startIndex = (pageNumber - 1) * 20 + 1;
		for (int i = 1; i < startIndex; i++)
			sortedQuestionsByTopicSet.next();

		// putting the needed questions from the above records in an array list

		ArrayList<Question> neededQuestions = new ArrayList<Question>();

		int isLastPage = 0;
		
		for (int i = 0; i < 20; i++) {

			if (sortedQuestionsByTopicSet.next() == false) {
				isLastPage = 1;
				break;
			}

			neededQuestions.add(buildQuestionFromRecord(sortedQuestionsByTopicSet, requestingUsername, connection));

		}
		
		ClientInteractor.sendData(response, " { \"isLastPage\": " + isLastPage
				+ ", \"questions\": ");

		statement.close();

		return neededQuestions.toArray(new Question[neededQuestions.size()]);

	}

	// a method for showing all the data in a table
	public static void showTable(String table, Statement statement) throws ClassNotFoundException, SQLException {

		System.out.println("\n\nShowing the " + table + " table:\n\n");

		// declaring a result set object to be used
		ResultSet resultSet = null;

		// selecting all from the table
		resultSet = statement.executeQuery(String.format(StringConstants.SELECT, StringConstants.ALL, table));

		switch (table) {

		case StringConstants.USERS_TABLE:

			// the result set is of users

			// moving through the results
			while (resultSet.next()) {

			}

			break;

		case StringConstants.QUESTIONS_TABLE:

			// the result set is of questions

			// moving through the results
			while (resultSet.next()) {

			}

			break;

		default:

			break;

		}

		// closing the resources
		resultSet.close();

	}

	public static void showQuestions() {

		System.out.println("printing questions!!!\n\n\n");

		Connection connection = null;
		Statement statement = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Questions");
			while (rs.next()) {

				String s = String.format(
						"id = %d\ntext = %s\nauthor nickname = %s\nquestion voting score = %d\n"
								+ "time stamp = %s\nnumber of answers = %d\n"
								+ "answers rating sum = %d\nquestion topics = %s\nrating = %f",
						rs.getInt(StringConstants.ID), rs.getString(StringConstants.TEXT),
						rs.getString(StringConstants.AUTHOR), rs.getInt(StringConstants.QUESTION_VOTING_SCORE),
						rs.getString(StringConstants.TIMESTAMP), rs.getInt(StringConstants.QUESTION_ANSWERS_NUMBER),
						rs.getInt(StringConstants.QUESTION_ANSWERS_RATING_SUM),
						rs.getString(StringConstants.QUESTION_TOPICS), rs.getDouble(StringConstants.RATING));

				System.out.println(s);

				System.out.println("\n");

			}

			System.out.println("******* THE END ********");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (statement != null)
					statement.close();

				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}

		}

	}

	public static void showAnswers() {

		System.out.println("printing answers!!!\n\n\n");

		Connection connection = null;
		Statement statement = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Answers");
			while (rs.next()) {

				String s = String.format(
						"id = %d\nquestionId = %d\ntext = %s\nauthor nickname = %s\n"
								+ "time stamp = %s\nrating = %d\n",
						rs.getInt(StringConstants.ID), rs.getInt(StringConstants.QUESTION_ID),
						rs.getString(StringConstants.TEXT), rs.getString(StringConstants.AUTHOR),
						rs.getString(StringConstants.TIMESTAMP), rs.getInt(StringConstants.RATING));

				System.out.println(s);

				System.out.println("\n");

			}

			System.out.println("******* THE END ********");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (statement != null)
					statement.close();

				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}

		}

	}

	public static void showUsers() {

		System.out.println("printing users!!!\n\n\n");

		Connection connection = null;
		Statement statement = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Users");
			while (rs.next()) {

				String s = String.format(
						"username = %s\npassword = %s\n" + "nickname = %s\nshort description = %s\n"
								+ "image url = %s\nnumber of questions = %d\n" + "number of answers = %d\n"
								+ "questions rating sum = %d\n" + "answers rating sum = %d\n" + "rating = %f",
						rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getDouble(10));

				System.out.println(s);

				System.out.println("\n");

			}

			System.out.println("******* THE END ********");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (statement != null)
					statement.close();

				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}

		}

	}

	public static void showTopics() {

		System.out.println("printing topics!!!\n\n\n");

		Connection connection = null;
		Statement statement = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Topics");
			while (rs.next()) {

				String s = String.format("topic = %s\ntopic popularity = %f\n", rs.getString(1), rs.getDouble(2));

				System.out.println(s);

				System.out.println("\n");

			}

			System.out.println("******* THE END ********");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (statement != null)
					statement.close();

				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}

		}

	}

	public static void showUserQuestionVotes() {

		System.out.println("printing user question votes!!!\n\n\n");

		Connection connection = null;
		Statement statement = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM UserQuestionVotes");
			while (rs.next()) {

				String s = String.format("username = %s\nquestion id = %d\n" + "vote = %d", rs.getString(1),
						rs.getInt(2), rs.getInt(3));

				System.out.println(s);

				System.out.println("\n");

			}

			System.out.println("******* THE END ********");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (statement != null)
					statement.close();

				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}

		}

	}

	public static void showUserAnswerVotes() {

		System.out.println("printing user answer votes!!!\n\n\n");

		Connection connection = null;
		Statement statement = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM UserAnswerVotes");
			while (rs.next()) {

				String s = String.format("username = %s\nanswer id = %d\n" + "vote = %d", rs.getString(1), rs.getInt(2),
						rs.getInt(3));

				System.out.println(s);

				System.out.println("\n");

			}

			System.out.println("******* THE END ********");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (statement != null)
					statement.close();

				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}

		}

	}

	public static void showUserTopicRanks() {

		System.out.println("printing user topic ranks!!!\n\n\n");

		Connection connection = null;
		Statement statement = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM UserTopicRanks");
			while (rs.next()) {

				String s = String.format("username = %s\ntopic = %s\n" + "rank = %d", rs.getString(1), rs.getString(2),
						rs.getInt(3));

				System.out.println(s);

				System.out.println("\n");

			}

			System.out.println("******* THE END ********");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (statement != null)
					statement.close();

				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}

		}

	}

}
