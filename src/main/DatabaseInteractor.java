package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpServletResponse;

import jsonClasses.Answer;
import jsonClasses.Question;
import jsonClasses.Topic;
import jsonClasses.User;

/**
 * The DatabaseInteractor class provides an API for interacting with the APACHE
 * DERBY database. Many create table methods, inserting data methods, querying
 * methods and updating methods are provided in this API. This class is used by
 * the servlets of this project to update the database in doPost methods, or
 * query it in doGet methods. This class deeply depends on the constant strings
 * defined in the {@link StringConstants} class.
 * 
 * @author LIAV
 * @since 2016-02-26
 * @see StringConstants
 *
 */
public class DatabaseInteractor {

	/**
	 * A hash map object provides a way to get the name of a key column in a
	 * table through the table name.
	 */
	private static HashMap<String, String> keyColumnFinder = null;

	/**
	 * Supplies a connection with the APACHE DERBY database.
	 * 
	 * @return A connection object to the APACHE DERBY database.
	 * @throws SQLException
	 *             If fails to establish connection to the APACHE DERBY
	 *             database.
	 * @throws ClassNotFoundException
	 *             If fails to locate the class path of the derby driver.
	 */
	public static Connection getConnection() throws SQLException, ClassNotFoundException {

		// initializing the key column finder hash map, if it hasn't yet
		// been initialized
		if (keyColumnFinder == null) {
			keyColumnFinder = new HashMap<String, String>();
			keyColumnFinder.put(StringConstants.USERS_TABLE, StringConstants.USERNAME);
			keyColumnFinder.put(StringConstants.QUESTIONS_TABLE, StringConstants.ID);
			keyColumnFinder.put(StringConstants.ANSWERS_TABLE, StringConstants.ID);
			keyColumnFinder.put(StringConstants.TOPICS_TABLE, StringConstants.TOPIC_NAME);
		}

		// setting the URL string object for the database
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

	/**
	 * Shuts down the database.
	 * 
	 * @throws SQLException
	 *             If fails to establish connection to the database.
	 */
	public static void shutDownDatabase() throws SQLException {

		DriverManager.getConnection(StringConstants.DERBY_SHUT_DOWN);

	}

	/**
	 * Creates a table in the database.
	 * 
	 * @param table
	 *            The table name.
	 * @param statement
	 *            A statement object to execute the update.
	 * @throws SQLException
	 *             If fails to create the table.
	 */
	public static void createTable(String table, Statement statement) throws SQLException {

		// getting the SQL command from the table name

		String sqlCommand;

		switch (table) {

		case StringConstants.USERS_TABLE:

			// setting a command to create the users table

			sqlCommand = StringConstants.CREATE_USERS_TABLE;

			break;

		case StringConstants.QUESTIONS_TABLE:

			// setting a command to create the questions table

			sqlCommand = StringConstants.CREATE_QUESTIONS_TABLE;

			break;

		case StringConstants.ANSWERS_TABLE:

			// setting a command to create the answers table

			sqlCommand = StringConstants.CREATE_ANSWERS_TABLE;

			break;

		case StringConstants.TOPICS_TABLE:

			// setting a command to create the topics table

			sqlCommand = StringConstants.CREATE_TOPICS_TABLE;

			break;

		case StringConstants.USER_QUESTION_VOTES_TABLE:

			// setting a command to create the user question votes table
			// where each question vote is kept with the user and the question

			sqlCommand = StringConstants.CREATE_USER_QUESTION_VOTES_TABLE;

			break;

		case StringConstants.USER_ANSWER_VOTES_TABLE:

			// setting a command to create the user answer votes table
			// where each answer vote is kept with the user and the answer

			sqlCommand = StringConstants.CREATE_USER_ANSWER_VOTES_TABLE;

			break;

		case StringConstants.USER_TOPIC_RANKS_TABLE:

			// setting a command to create a user topic ranks table
			// where each triple of user, topic and it's rank is kept

			sqlCommand = StringConstants.CREATE_USER_TOPIC_RANKS_TABLE;

			break;

		default:
			sqlCommand = "";

		}

		// creating the table
		try {

			statement.executeUpdate(sqlCommand);

		} // handling the exceptions
		catch (SQLException exception) {

			// getting the error code
			int errorCode = exception.getErrorCode();

			// if the error code means the table already exists
			// that's OK - otherwise - throw an exception
			if (errorCode != 30000)
				throw exception;

		}

	}

	/**
	 * Inserts data to the database.
	 * 
	 * @param table
	 *            The table name.
	 * @param connection
	 *            A connection object to get a prepared statement object from,
	 *            to update the database.
	 * @param args
	 *            Arguments for the insertion. (e.g. user name, questionId...)
	 * @throws SQLException
	 *             If fails to insert the record to the table.
	 */
	public static void insertInto(String table, Connection connection, String... args) throws SQLException {

		// getting a prepared statement object to update the database with
		PreparedStatement preparedStatement = null;

		// getting the SQL command from the table name
		String sqlCommand;

		switch (table) {

		case StringConstants.USERS_TABLE:

			// setting a command to insert a user to the users table
			sqlCommand = StringConstants.INSERT_USER;

			// preparing a statement
			preparedStatement = connection.prepareStatement(sqlCommand);

			// setting the arguments to the statement
			for (int i = 1; i <= 5; i++)
				preparedStatement.setString(i, args[i - 1]);

			break;

		case StringConstants.QUESTIONS_TABLE:

			// setting a command to insert a question to the questions table
			sqlCommand = StringConstants.INSERT_QUESTION;

			// preparing a statement
			preparedStatement = connection.prepareStatement(sqlCommand);

			// setting the arguments to the statement
			for (int i = 0; i < 4; i++)
				preparedStatement.setString(i + 1, args[i]);

			break;

		case StringConstants.ANSWERS_TABLE:

			// setting a command to insert an answer to the answers table
			sqlCommand = StringConstants.INSERT_ANSWER;

			// preparing a statement
			preparedStatement = connection.prepareStatement(sqlCommand);

			// setting the arguments to the statement
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

			// setting the arguments to the statement
			preparedStatement.setString(1, args[0]);

			break;

		case StringConstants.USER_QUESTION_VOTES_TABLE:

			// setting a command to insert a user question vote
			// to the user question votes table
			sqlCommand = StringConstants.INSERT_USER_QUESTION_VOTE;

			// preparing a statement
			preparedStatement = connection.prepareStatement(sqlCommand);

			// setting the arguments to the statement
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

			// setting the arguments to the statement
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

			// setting the arguments to the statement
			preparedStatement.setString(1, args[0]);
			preparedStatement.setString(2, args[1]);

			break;

		default:
			sqlCommand = "";

		}

		// executing the command and closing the prepared statement object
		preparedStatement.executeUpdate();
		preparedStatement.close();

	}

	/**
	 * Overloaded method. Deletes a record with a string type key from the
	 * database.
	 * 
	 * @param table
	 *            The table name which the record is in.
	 * @param key
	 *            The value of the primary key column of this record.
	 * @param statement
	 *            A statement object to delete from the database.
	 * @throws SQLException
	 *             If fails to delete the record from the database.
	 */
	public static void deleteRecord(String table, String key, Statement statement) throws SQLException {

		// getting the column name of the key in this table
		String keyColumn = keyColumnFinder.get(table);

		// deleting the record
		statement.executeUpdate(String.format("DELETE FROM %s WHERE %s='%s'", table, keyColumn, key));

	}

	/**
	 * Overloaded method. Deletes a record with an integer type key from the
	 * database.
	 * 
	 * @param table
	 *            The table name which the record is in.
	 * @param key
	 *            The value of the primary key column of this record.
	 * @param statement
	 *            A statement object to delete from the database.
	 * @throws SQLException
	 *             If fails to delete the record from the database.
	 */
	public static void deleteRecord(String table, Integer key, Statement statement) throws SQLException {

		// getting the column name of the key in this table
		String keyColumn = keyColumnFinder.get(table);

		// deleting the record
		statement.executeUpdate(String.format("DELETE FROM %s WHERE %s=%s", table, keyColumn, key));

	}

	/**
	 * Overloaded method. Deletes a record, which is uniquely identified by two
	 * string type keys, from the database.
	 * 
	 * @param table
	 *            The table name which the record is in.
	 * @param firstKeyColumn
	 *            The name of the column of the first key in this table.
	 * @param firstKey
	 *            The value of the first key of this record.
	 * @param secondKeyColumn
	 *            The name of the column of the second key in this table.
	 * @param secondKey
	 *            The value of the second key of this record
	 * @param statement
	 *            A statement object to delete from the database.
	 * @throws SQLException
	 *             If fails to delete the record from the database.
	 */
	public static void deleteRecord(String table, String firstKeyColumn, String firstKey, String secondKeyColumn,
			String secondKey, Statement statement) throws SQLException {

		// deleting the record
		statement.executeUpdate(String.format("DELETE FROM %s WHERE %s='%s' AND %s='%s'", table, firstKeyColumn,
				firstKey, secondKeyColumn, secondKey));

	}

	/**
	 * Overloaded method. Deletes a record, which is uniquely identified by one
	 * string type key and one integer type key, from the database.
	 * 
	 * @param table
	 *            The table name which the record is in.
	 * @param firstKeyColumn
	 *            The name of the column of the first key in this table.
	 * @param firstKey
	 *            The value of the first key of this record.
	 * @param secondKeyColumn
	 *            The name of the column of the second key in this table.
	 * @param secondKey
	 *            The value of the second key of this record
	 * @param statement
	 *            A statement object to delete from the database.
	 * @throws SQLException
	 *             If fails to delete the record from the database.
	 * 
	 */
	public static void deleteRecord(String table, String firstKeyColumn, String firstKey, String secondKeyColumn,
			Integer secondKey, Statement statement) throws SQLException {

		// deleting the record
		statement.executeUpdate(String.format("DELETE FROM %s WHERE %s='%s' AND %s=%s", table, firstKeyColumn, firstKey,
				secondKeyColumn, secondKey));

	}

	/**
	 * Overloaded method. Deletes a record, which is uniquely identified by two
	 * integer type keys, from the database.
	 * 
	 * @param table
	 *            The table name which the record is in.
	 * @param firstKeyColumn
	 *            The name of the column of the first key in this table.
	 * @param firstKey
	 *            The value of the first key of this record.
	 * @param secondKeyColumn
	 *            The name of the column of the second key in this table.
	 * @param secondKey
	 *            The value of the second key of this record
	 * @param statement
	 *            A statement object to delete from the database.
	 * @throws SQLException
	 *             If fails to delete the record from the database.
	 */
	public static void deleteRecord(String table, String firstKeyColumn, Integer firstKey, String secondKeyColumn,
			Integer secondKey, Statement statement) throws SQLException {

		// deleting the record
		statement.executeUpdate(String.format("DELETE FROM %s WHERE %s=%s AND %s=%s", table, firstKeyColumn, firstKey,
				secondKeyColumn, secondKey));

	}

	/**
	 * Overloaded method. Updates a string type value in a record, which is
	 * identified by a string type key, in the database.
	 * 
	 * @param key
	 *            The key value of this record.
	 * @param table
	 *            The table name which the record is in.
	 * @param column
	 *            The name of the column that the method updates in this record.
	 * @param newValue
	 *            The new value that is stored in this record.
	 * @param statement
	 *            A statement object to update the table.
	 * @throws SQLException
	 *             If fails to delete the record from the database.
	 */
	public static void updateDatabase(String key, String table, String column, String newValue, Statement statement)
			throws SQLException {

		// getting the name of the column of this table's key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the record
		statement.executeUpdate(
				String.format("UPDATE %s SET %s='%s' WHERE %s='%s'", table, column, newValue, keyColumn, key));

	}

	/**
	 * Overloaded method. Updates an integer type value in a record, which is
	 * identified by a string type key, in the database.
	 * 
	 * @param key
	 *            The key value of this record.
	 * @param table
	 *            The table name which the record is in.
	 * @param column
	 *            The name of the column that the method updates in this record.
	 * @param newValue
	 *            The new value that is stored in this record.
	 * @param statement
	 *            A statement object to update the table.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void updateDatabase(String key, String table, String column, Integer newValue, Statement statement)
			throws SQLException {

		// getting the name of the column of this table's key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the record
		statement.executeUpdate(
				String.format("UPDATE %s SET %s=%s WHERE %s='%s'", table, column, newValue, keyColumn, key));

	}

	/**
	 * Overloaded method. Updates a double type value in a record, which is
	 * identified by a string type key, in the database.
	 * 
	 * @param key
	 *            The key value of this record.
	 * @param table
	 *            The table name which the record is in.
	 * @param column
	 *            The name of the column that the method updates in this record.
	 * @param newValue
	 *            The new value that is stored in this record.
	 * @param statement
	 *            A statement object to update the table.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void updateDatabase(String key, String table, String column, Double newValue, Statement statement)
			throws SQLException {

		// getting the name of the column of this table's key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the record
		statement.executeUpdate(
				String.format("UPDATE %s SET %s=%s WHERE %s='%s'", table, column, newValue, keyColumn, key));

	}

	/**
	 * Overloaded method. Updates a string type value in a record, which is
	 * identified by an integer type key, in the database.
	 * 
	 * @param key
	 *            The key value of this record.
	 * @param table
	 *            The table name which the record is in.
	 * @param column
	 *            The name of the column that the method updates in this record.
	 * @param newValue
	 *            The new value that is stored in this record.
	 * @param statement
	 *            A statement object to update the table.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void updateDatabase(Integer key, String table, String column, String newValue, Statement statement)
			throws SQLException {

		// getting the name of the column of this table's key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the record
		statement.executeUpdate(
				String.format("UPDATE %s SET %s='%s' WHERE %s=%s", table, column, newValue, keyColumn, key));

	}

	/**
	 * Overloaded method. Updates an integer type value in a record, which is
	 * identified by an integer type key, in the database.
	 * 
	 * @param key
	 *            The key value of this record.
	 * @param table
	 *            The table name which the record is in.
	 * @param column
	 *            The name of the column that the method updates in this record.
	 * @param newValue
	 *            The new value that is stored in this record.
	 * @param statement
	 *            A statement object to update the table.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void updateDatabase(Integer key, String table, String column, Integer newValue, Statement statement)
			throws SQLException {

		// getting the name of the column of this table's key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the record
		statement.executeUpdate(
				String.format("UPDATE %s SET %s=%s WHERE %s=%s", table, column, newValue, keyColumn, key));

	}

	/**
	 * Overloaded method. Updates a double type value in a record, which is
	 * identified by an integer type key, in the database.
	 * 
	 * @param key
	 *            The key value of this record.
	 * @param table
	 *            The table name which the record is in.
	 * @param column
	 *            The name of the column that the method updates in this record.
	 * @param newValue
	 *            The new value that is stored in this record.
	 * @param statement
	 *            A statement object to update the table.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void updateDatabase(Integer key, String table, String column, Double newValue, Statement statement)
			throws SQLException {

		// getting the name of the column of this table's key column
		String keyColumn = keyColumnFinder.get(table);

		// updating the record
		statement.executeUpdate(
				String.format("UPDATE %s SET %s=%s WHERE %s=%s", table, column, newValue, keyColumn, key));

	}

	/**
	 * Overloaded method. Updates an integer type value in a record, which is
	 * identified by two string type keys, in the database.
	 * 
	 * @param table
	 *            The table name which the record is in.
	 * @param firstKeyColumn
	 *            The name of the column of the first key in this table.
	 * @param firstKey
	 *            The value of the first key of this record.
	 * @param secondKeyColumn
	 *            The name of the column of the second key in this table.
	 * @param secondKey
	 *            The value of the second key of this record.
	 * @param column
	 *            The name of the column that is updated in this record.
	 * @param newValue
	 *            The new value that is stored in this record.
	 * @param statement
	 *            A statement object to update the table.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void updateDatabase(String table, String firstKeyColumn, String firstKey, String secondKeyColumn,
			String secondKey, String column, Integer newValue, Statement statement) throws SQLException {

		// updating the record
		statement.executeUpdate(String.format("UPDATE %s SET %s=%s WHERE %s='%s' AND %s='%s'", table, column, newValue,
				firstKeyColumn, firstKey, secondKeyColumn, secondKey));

	}

	/**
	 * Overloaded method. Updates an integer type value in a record, which is
	 * identified by a string type key and an integer type key, in the database.
	 * 
	 * @param table
	 *            The table name which the record is in.
	 * @param firstKeyColumn
	 *            The name of the column of the first key in this table.
	 * @param firstKey
	 *            The value of the first key of this record.
	 * @param secondKeyColumn
	 *            The name of the column of the second key in this table.
	 * @param secondKey
	 *            The value of the second key of this record.
	 * @param column
	 *            The name of the column that is updated in this record.
	 * @param newValue
	 *            The new value that is stored in this record.
	 * @param statement
	 *            A statement object to update the table.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void updateDatabase(String table, String firstKeyColumn, String firstKey, String secondKeyColumn,
			Integer secondKey, String column, Integer newValue, Statement statement) throws SQLException {

		// updating the record
		statement.executeUpdate(String.format("UPDATE %s SET %s=%s WHERE %s='%s' AND %s=%s", table, column, newValue,
				firstKeyColumn, firstKey, secondKeyColumn, secondKey));

	}

	/**
	 * An overloaded method. Returns a record, which is identified by a string
	 * type key, from the database.
	 * 
	 * @param table
	 *            The name of the table that record is in.
	 * @param keyColumn
	 *            The name of the key column of this table.
	 * @param key
	 *            The value of this record's key.
	 * @param statement
	 *            A statement object to query the database.
	 * @return A ResultSet object containing this record.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	private static ResultSet getRecordFromKey(String table, String keyColumn, String key, Statement statement)
			throws SQLException {

		// getting the record from the parameters
		ResultSet record = statement.executeQuery(
				String.format("SELECT %s FROM %s WHERE %s='%s'", StringConstants.ALL, table, keyColumn, key));

		// returning the record
		return record;

	}

	/**
	 * An overloaded method. Returns a record, which is identified by an integer
	 * type key, from the database.
	 * 
	 * @param table
	 *            The name of the table that record is in.
	 * @param keyColumn
	 *            The name of the key column of this table.
	 * @param key
	 *            The value of this record's key.
	 * @param statement
	 *            A statement object to query the database.
	 * @return A ResultSet object containing this record.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	private static ResultSet getRecordFromKey(String table, String keyColumn, Integer key, Statement statement)
			throws SQLException {

		// getting the record from the parameters
		ResultSet record = statement.executeQuery(
				String.format("SELECT %s FROM %s WHERE %s=%s", StringConstants.ALL, table, keyColumn, key));

		// returning the record
		return record;

	}

	/**
	 * An overloaded method. Returns a record, which is uniquely identified by
	 * two string type keys, from the database.
	 * 
	 * @param table
	 *            The name of the table that record is in.
	 * @param firstKeyColumn
	 *            The name of the column of the first key in this table.
	 * @param firstKey
	 *            The value of the first key of this record.
	 * @param secondKeyColumn
	 *            The name of the column of the second key in this table.
	 * @param secondKey
	 *            The value of the second key of this record.
	 * @param statement
	 *            A statement object to query the database.
	 * @return A ResultSet object containing this record.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	private static ResultSet getRecordFromPair(String table, String firstKeyColumn, String firstKey,
			String secondKeyColumn, String secondKey, Statement statement) throws SQLException {

		// getting the record from the parameters
		ResultSet record = statement.executeQuery(String.format("SELECT %s FROM %s WHERE %s='%s' AND %s='%s'",
				StringConstants.ALL, table, firstKeyColumn, firstKey, secondKeyColumn, secondKey));

		// returning the record
		return record;

	}

	/**
	 * An overloaded method. Returns a record, which is uniquely identified by a
	 * string type key and an integer type key, from the database.
	 * 
	 * @param table
	 *            The name of the table that record is in.
	 * @param firstKeyColumn
	 *            The name of the column of the first key in this table.
	 * @param firstKey
	 *            The value of the first key of this record.
	 * @param secondKeyColumn
	 *            The name of the column of the second key in this table.
	 * @param secondKey
	 *            The value of the second key of this record.
	 * @param statement
	 *            A statement object to query the database.
	 * @return A ResultSet object containing this record.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	private static ResultSet getRecordFromPair(String table, String firstKeyColumn, String firstKey,
			String secondKeyColumn, Integer secondKey, Statement statement) throws SQLException {

		// getting the record from the parameters
		ResultSet record = statement.executeQuery(String.format("SELECT %s FROM %s WHERE %s='%s' AND %s=%s",
				StringConstants.ALL, table, firstKeyColumn, firstKey, secondKeyColumn, secondKey));

		// returning the record
		return record;

	}

	/**
	 * An overloaded method. Returns a record, which is uniquely identified by
	 * two integer type keys, from the database.
	 * 
	 * @param table
	 *            The name of the table that record is in.
	 * @param firstKeyColumn
	 *            The name of the column of the first key in this table.
	 * @param firstKey
	 *            The value of the first key of this record.
	 * @param secondKeyColumn
	 *            The name of the column of the second key in this table.
	 * @param secondKey
	 *            The value of the second key of this record.
	 * @param statement
	 *            A statement object to query the database.
	 * @return A ResultSet object containing this record.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	private static ResultSet getRecordFromPair(String table, String firstKeyColumn, Integer firstKey,
			String secondKeyColumn, Integer secondKey, Statement statement) throws SQLException {

		// getting the record from the parameters
		ResultSet record = statement.executeQuery(String.format("SELECT %s FROM %s WHERE %s=%s AND %s=%s",
				StringConstants.ALL, table, firstKeyColumn, firstKey, secondKeyColumn, secondKey));

		// returning the record
		return record;

	}

	/**
	 * Updates the user rating column, which is calculated by the project
	 * formula.
	 * 
	 * @param username
	 *            The name of the user which is updates.
	 * @param statement
	 *            A statement object to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	private static void updateUserRating(String username, Statement statement) throws SQLException {

		// update the user rating
		updateDatabase(username, StringConstants.USERS_TABLE, StringConstants.RATING,
				computeUserRating(username, statement), statement);

	}

	/**
	 * Updates a question rating column, which is calculated by the project
	 * formula.
	 * 
	 * @param questionId
	 *            The id of the question which is updated.
	 * @param statement
	 *            A statement object which is used to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	private static void updateQuestionRating(int questionId, Statement statement) throws SQLException {

		// updating the question rating
		updateDatabase(questionId, StringConstants.QUESTIONS_TABLE, StringConstants.RATING,
				computeQuestionRating(questionId, statement), statement);

	}

	/**
	 * Computes the question rating, which is calculated by the project formula.
	 * 
	 * @param questionId
	 *            The id of the question that its rating is calculated.
	 * @param statement
	 *            A statement object used to query the database.
	 * @return The question rating.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	private static double computeQuestionRating(int questionId, Statement statement) throws SQLException {

		// getting the question voting score
		int questionVotingScore = Integer.parseInt(getColumnFromKey(StringConstants.QUESTION_VOTING_SCORE,
				StringConstants.ID, questionId, StringConstants.QUESTIONS_TABLE, statement));

		// getting the number of answers which were submitted to this question
		int numberOfAnswers = Integer.parseInt(getColumnFromKey(StringConstants.QUESTION_ANSWERS_NUMBER,
				StringConstants.ID, questionId, StringConstants.QUESTIONS_TABLE, statement));

		// getting the sum of rating of answers which were submitted to this
		// question
		int answersRatingSum = Integer.parseInt(getColumnFromKey(StringConstants.QUESTION_ANSWERS_RATING_SUM,
				StringConstants.ID, questionId, StringConstants.QUESTIONS_TABLE, statement));

		// calculating the question rating via the project formula

		/*
		 * here we make an assumption: if the question doesn't have any answers,
		 * then the amount that is added to the question rating because of
		 * answers will be zero
		 */

		double amountToAddForAnswers = numberOfAnswers == 0 ? 0 : (0.8 * answersRatingSum) / numberOfAnswers;

		double amountToAddForQuestionVotingScore = 0.2 * questionVotingScore;

		// calculating the question rating via the project formula
		double questionRating = amountToAddForQuestionVotingScore + amountToAddForAnswers;

		// returning the question rating
		return questionRating;

	}

	/**
	 * Computes the user rating, which is calculated by the project formula.
	 * 
	 * @param username
	 *            The user name of the user whose rating is calculated.
	 * @param statement
	 *            A statement object used to query the database.
	 * @return The user rating.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	private static double computeUserRating(String username, Statement statement) throws SQLException {

		// getting the sum of the questions rating scores that this user has
		// asked
		int userQuestionsRatingSum = Integer.parseInt(getColumnFromKey(StringConstants.USER_QUESTIONS_RATING_SUM,
				StringConstants.USERNAME, username, StringConstants.USERS_TABLE, statement));

		// getting the number of questions that this user has asked
		int userQuestionsNumber = Integer.parseInt(getColumnFromKey(StringConstants.USER_QUESTIONS_NUMBER,
				StringConstants.USERNAME, username, StringConstants.USERS_TABLE, statement));

		// getting the sum of the answers rating scores that this user has
		// submitted
		int userAnswersRatingSum = Integer.parseInt(getColumnFromKey(StringConstants.USER_ANSWERS_RATING_SUM,
				StringConstants.USERNAME, username, StringConstants.USERS_TABLE, statement));

		// getting the number of answers that this user has submitted
		int userAnswersNumber = Integer.parseInt(getColumnFromKey(StringConstants.USER_ANSWERS_NUMBER,
				StringConstants.USERNAME, username, StringConstants.USERS_TABLE, statement));

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

		// returning the user rating
		return userRating;

	}

	/**
	 * An overloaded method. Checks the existence of a key in a table where the
	 * key type is a string.
	 * 
	 * @param key
	 *            The value of the key.
	 * @param table
	 *            The name of the table.
	 * @param statement
	 *            A statement object to query the database.
	 * @return A flag indicates the existence of this key in this table.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static boolean doesExistIn(String key, String table, Statement statement) throws SQLException {

		// getting the key column name from the table
		String keyColumn = keyColumnFinder.get(table);

		// getting the record from its key
		ResultSet record = getRecordFromKey(table, keyColumn, key, statement);

		// returning if the result set has any rows.
		return record.next();

	}

	/**
	 * An overloaded method. Checks the existence of a key in a table where the
	 * key type is an integer.
	 * 
	 * @param key
	 *            The value of the key.
	 * @param table
	 *            The name of the table.
	 * @param statement
	 *            A statement object to query the database.
	 * @return A flag indicates the existence of this key in this table.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static boolean doesExistIn(Integer key, String table, Statement statement) throws SQLException {

		// getting the key column name from the table
		String keyColumn = keyColumnFinder.get(table);

		// getting the record from its key
		ResultSet record = getRecordFromKey(table, keyColumn, key, statement);

		// returning if the result set has any rows.
		return record.next();

	}

	/**
	 * An overloaded method. Checks the existence of a pair in a table where
	 * both of the keys are strings.
	 * 
	 * @param table
	 *            The name of the table.
	 * @param firstKeyColumn
	 *            The name of the column of the first key in this table.
	 * @param firstKey
	 *            The first key that is checked.
	 * @param secondKeyColumn
	 *            The name of the column of the second key in this table.
	 * @param secondKey
	 *            The second key that is checked.
	 * @param statement
	 *            A statement object to query the database.
	 * @return A flag indicates the existence of this key in this table.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static boolean doesExistIn(String table, String firstKeyColumn, String firstKey, String secondKeyColumn,
			String secondKey, Statement statement) throws SQLException {

		// getting a record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// returning if the result set has any rows
		return record.next();

	}

	/**
	 * An overloaded method. Checks the existence of a pair in a table where the
	 * first key is a string and the second key is an integer.
	 * 
	 * @param table
	 *            The name of the table.
	 * @param firstKeyColumn
	 *            The name of the column of the first key in this table.
	 * @param firstKey
	 *            The first key that is checked.
	 * @param secondKeyColumn
	 *            The name of the column of the second key in this table.
	 * @param secondKey
	 *            The second key that is checked.
	 * @param statement
	 *            A statement object to query the database.
	 * @return A flag indicates the existence of this key in this table.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static boolean doesExistIn(String table, String firstKeyColumn, String firstKey, String secondKeyColumn,
			Integer secondKey, Statement statement) throws SQLException {

		// getting a record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// returning if the result set has any rows.
		return record.next();

	}

	/**
	 * An overloaded method. Checks the existence of a pair in a table where
	 * both of the keys are of integer type.
	 * 
	 * @param table
	 *            The name of the table.
	 * @param firstKeyColumn
	 *            The name of the column of the first key in this table.
	 * @param firstKey
	 *            The first key that is checked.
	 * @param secondKeyColumn
	 *            The name of the column of the second key in this table.
	 * @param secondKey
	 *            The second key that is checked.
	 * @param statement
	 *            A statement object to query the database.
	 * @return A flag indicates the existence of this key in this table.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static boolean doesExistIn(String table, String firstKeyColumn, Integer firstKey, String secondKeyColumn,
			Integer secondKey, Statement statement) throws SQLException {

		// getting a record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// returning if the result set has any rows.
		return record.next();

	}

	/**
	 * Checks if a given user match its password in the users table.
	 * 
	 * @param user
	 *            The user which is checked.
	 * @param statement
	 *            A statement object to query the database.
	 * @return A flag which indicates whether the user matched the password in
	 *         the database or not.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static boolean doesMatchPassword(User user, Statement statement) throws SQLException {

		// declaring a result set object to be used
		ResultSet record = null;

		// selecting this user from the users table
		record = getRecordFromKey(StringConstants.USERS_TABLE, StringConstants.USERNAME, user.getName(), statement);

		// moving the cursor to the password we got
		record.next();

		// returning true if and only if the passwords match
		return record.getString(StringConstants.USER_PASSWORD).equals(user.getPassword());

	}

	/**
	 * An overloaded method. Gets a column from a pair of uniquely identifying
	 * keys, where both of the keys are of string type, in the table.
	 * 
	 * @param column
	 *            The name of the column that this method gets.
	 * @param firstKeyColumn
	 *            The name of the column of this table's first key.
	 * @param firstKey
	 *            The value of the first key of this record.
	 * @param secondKeyColumn
	 *            The name of the column of this table's second key.
	 * @param secondKey
	 *            The value of the second key of this record.
	 * @param table
	 *            The name of the table.
	 * @param statement
	 *            A statement object used to query the database.
	 * @return A string instance which has the wanted column value.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static String getColumnFromPair(String column, String firstKeyColumn, String firstKey,
			String secondKeyColumn, String secondKey, String table, Statement statement) throws SQLException {

		// getting the record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// getting the requested column from the record
		record.next();
		return record.getString(column);

	}

	/**
	 * An overloaded method. Gets a column from a pair of uniquely identifying
	 * keys, where one of the keys is of string type, and the other is of
	 * integer type, in the table.
	 * 
	 * @param column
	 *            The name of the column that this method gets.
	 * @param firstKeyColumn
	 *            The name of the column of this table's first key.
	 * @param firstKey
	 *            The value of the first key of this record.
	 * @param secondKeyColumn
	 *            The name of the column of this table's second key.
	 * @param secondKey
	 *            The value of the second key of this record.
	 * @param table
	 *            The name of the table.
	 * @param statement
	 *            A statement object used to query the database.
	 * @return A string instance which has the wanted column value.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static String getColumnFromPair(String column, String firstKeyColumn, String firstKey,
			String secondKeyColumn, Integer secondKey, String table, Statement statement) throws SQLException {

		// getting the record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// getting the requested column from the record
		record.next();
		return record.getString(column);

	}

	/**
	 * An overloaded method. Gets a column from a pair of uniquely identifying
	 * keys, where both of the keys are of a string type, in the table.
	 * 
	 * @param column
	 *            The name of the column that this method gets.
	 * @param firstKeyColumn
	 *            The name of the column of this table's first key.
	 * @param firstKey
	 *            The value of the first key of this record.
	 * @param secondKeyColumn
	 *            The name of the column of this table's second key.
	 * @param secondKey
	 *            The value of the second key of this record.
	 * @param table
	 *            The name of the table.
	 * @param statement
	 *            A statement object used to query the database.
	 * @return A string instance which has the wanted column value.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static String getColumnFromPair(String column, String firstKeyColumn, Integer firstKey,
			String secondKeyColumn, Integer secondKey, String table, Statement statement) throws SQLException {

		// getting the record from the unique pair
		ResultSet record = getRecordFromPair(table, firstKeyColumn, firstKey, secondKeyColumn, secondKey, statement);

		// getting the requested column from the record
		record.next();
		return record.getString(column);

	}

	/**
	 * An overloaded method. Gets a column from a string type key.
	 * 
	 * @param column
	 *            The requested column name.
	 * @param keyColumn
	 *            The name of the column of the key.
	 * @param key
	 *            The value of the key.
	 * @param table
	 *            The table name.
	 * @param statement
	 *            A statement object used to query the database.
	 * @return A string instance which has the wanted column value.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static String getColumnFromKey(String column, String keyColumn, String key, String table,
			Statement statement) throws SQLException {

		// getting the record from the key
		ResultSet record = getRecordFromKey(table, keyColumn, key, statement);

		// getting the requested column from the record
		record.next();
		return record.getString(column);

	}

	/**
	 * An overloaded method. Gets a column from an integer type key.
	 * 
	 * @param column
	 *            The requested column name.
	 * @param keyColumn
	 *            The name of the column of the key.
	 * @param key
	 *            The value of the key.
	 * @param table
	 *            The table name.
	 * @param statement
	 *            A statement object used to query the database.
	 * @return A string instance which has the wanted column value.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static String getColumnFromKey(String column, String keyColumn, Integer key, String table,
			Statement statement) throws SQLException {

		// getting the record from the key
		ResultSet record = getRecordFromKey(table, keyColumn, key, statement);

		// getting the requested column from the record
		record.next();
		return record.getString(column);

	}

	/**
	 * An overloaded method. Adds the user topic pairs for the user topic ranks
	 * table after a user is added to the database.
	 * 
	 * @param user
	 *            The user which was added.
	 * @param connection
	 *            A connection object for the insert operation.
	 * @param statement
	 *            A statement object to query the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void addUserTopicPairs(User user, Connection connection, Statement statement) throws SQLException {

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

	/**
	 * Adds a value to the answers rating sum of a user.
	 * 
	 * @param username
	 *            The user name of the user which is about to be updated.
	 * @param scoreChange
	 *            The value to add to the answers rating sum.
	 * @param statement
	 *            A statement object to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void addUserAnswersVotingScore(String username, int scoreChange, Statement statement)
			throws SQLException {

		// update the user answers rating sum
		updateUser(username, StringConstants.USER_ANSWERS_RATING_SUM, scoreChange, statement);

	}

	/**
	 * Adds a value to the questions rating sum of a user.
	 * 
	 * @param username
	 *            The user name of the user which is about to be updated.
	 * @param scoreChange
	 *            The value to add to the questions rating sum.
	 * @param statement
	 *            A statement object to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void addUserQuestionsVotingScore(String username, int scoreChange, Statement statement)
			throws SQLException {

		// update the user questions rating sum
		updateUser(username, StringConstants.USER_QUESTIONS_RATING_SUM, scoreChange, statement);

	}

	/**
	 * Adds 1 to the user answers number in the database.
	 * 
	 * @param username
	 *            The user name of the user which is about to be updated.
	 * @param statement
	 *            A statement object to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void addUserAnswersNumber(String username, Statement statement) throws SQLException {

		// updating the answers number column
		updateUser(username, StringConstants.USER_ANSWERS_NUMBER, 1, statement);

	}

	/**
	 * Adds 1 to the user questions number in the database.
	 * 
	 * @param username
	 *            The user name of the user which is about to be updated.
	 * @param statement
	 *            A statement object to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void addUserQuestionsNumber(String username, Statement statement) throws SQLException {

		// updating the questions number column
		updateUser(username, StringConstants.USER_QUESTIONS_NUMBER, 1, statement);

	}

	/**
	 * An overloaded method. Adds the user topic pairs for the user topic ranks
	 * table after a topic is added to the database.
	 * 
	 * @param topic
	 *            The topic which was added.
	 * @param connection
	 *            A connection object for the insert operation.
	 * @param statement
	 *            A statement object to query the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void addUserTopicPairs(String topic, Connection connection, Statement statement) throws SQLException {

		// getting a result set containing all the users
		ResultSet users = statement.executeQuery(
				String.format(StringConstants.SELECT, StringConstants.USERNAME, StringConstants.USERS_TABLE));

		// moving through all the users, inserting each one,
		// with the topic, to the user topic ranks table, with rank 0,
		// since this is a new topic
		while (users.next())
			insertInto(StringConstants.USER_TOPIC_RANKS_TABLE, connection, users.getString(StringConstants.USERNAME),
					topic, "0");

	}

	/**
	 * Adds a value to the answers rating sum of a question.
	 * 
	 * @param questionId
	 *            The id of the question which is about to be updated.
	 * @param scoreChange
	 *            The value to add to the answers rating sum.
	 * @param statement
	 *            A statement object to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void addQuestionAnswersVotingScore(int questionId, int scoreChange, Statement statement)
			throws SQLException {

		// updating the sum of answers rating scores which were submitted to
		// this question
		updateQuestion(questionId, StringConstants.QUESTION_ANSWERS_RATING_SUM, scoreChange, statement);

	}

	/**
	 * Adds a value to the voting score of a question.
	 * 
	 * @param questionId
	 *            The id of the question which is about to be updated.
	 * @param scoreChange
	 *            The value to add to the question voting score.
	 * @param statement
	 *            A statement object to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void addQuestionVotingScore(int questionId, int scoreChange, Statement statement)
			throws SQLException {

		// updating the question
		updateQuestion(questionId, StringConstants.QUESTION_VOTING_SCORE, scoreChange, statement);

	}

	/**
	 * Adds 1 to the answers number of a question.
	 * 
	 * @param questionId
	 *            The id of the question which is about to be updated.
	 * @param statement
	 *            A statement object to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void addQuestionAnswersNumber(int questionId, Statement statement) throws SQLException {

		// updating the question
		updateQuestion(questionId, StringConstants.QUESTION_ANSWERS_NUMBER, 1, statement);

	}

	/**
	 * Adds a value to the voting score of an answer.
	 * 
	 * @param answerId
	 *            The id of the answer which is about to be updated.
	 * @param scoreChange
	 *            The value to add to the answer voting score.
	 * @param statement
	 *            A statement object to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	public static void addAnswerVotingScore(int answerId, int scoreChange, Statement statement) throws SQLException {

		// updating the answer
		updateAnswer(answerId, StringConstants.RATING, scoreChange, statement);

	}

	/**
	 * Updates an answer in the database.
	 * 
	 * @param answerId
	 *            The id of the answer to update.
	 * @param column
	 *            The column to update.
	 * @param columnChange
	 *            The change to be applied to the column.
	 * @param statement
	 *            A statement object used to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	private static void updateAnswer(int answerId, String column, int columnChange, Statement statement)
			throws SQLException {

		// getting the author name of this answer and the question id of the
		// question that
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

	/**
	 * Updates a question in the database.
	 * 
	 * @param questionId
	 *            The id of the question to update.
	 * @param column
	 *            The column to update.
	 * @param columnChange
	 *            The change to be applied to the column.
	 * @param statement
	 *            A statement object used to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	private static void updateQuestion(int questionId, String column, int columnChange, Statement statement)
			throws SQLException {

		// updating the question and getting the question rating change

		int currentColumnValue = Integer.parseInt(
				getColumnFromKey(column, StringConstants.ID, questionId, StringConstants.QUESTIONS_TABLE, statement));

		double beforeQuestionRating = Double.parseDouble(getColumnFromKey(StringConstants.RATING, StringConstants.ID,
				questionId, StringConstants.QUESTIONS_TABLE, statement));

		updateDatabase(questionId, StringConstants.QUESTIONS_TABLE, column, currentColumnValue + columnChange,
				statement);

		updateQuestionRating(questionId, statement);

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

	/**
	 * Updates a user in the database.
	 * 
	 * @param username
	 *            The name of the user to update.
	 * @param column
	 *            The column to update.
	 * @param columnChange
	 *            The change to be applied to the column.
	 * @param statement
	 *            A statement object used to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	private static void updateUser(String username, String column, int columnChange, Statement statement)
			throws SQLException {

		// updating the user

		int currentColumnValue = Integer.parseInt(
				getColumnFromKey(column, StringConstants.USERNAME, username, StringConstants.USERS_TABLE, statement));

		int updatedColumnValue = currentColumnValue + columnChange;

		updateDatabase(username, StringConstants.USERS_TABLE, column, updatedColumnValue, statement);

		updateUserRating(username, statement);

	}

	/**
	 * Gets all the topics associated with a question.
	 * 
	 * @param questionId
	 *            The id of the question that its topics are to be retrieved.
	 * @param statement
	 *            A statement object to query the database.
	 * @return A string array which includes all the names of topics associated
	 *         with the given question.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	private static String[] getTopicsFromQuestion(int questionId, Statement statement) throws SQLException {

		// getting the topics string of this question
		String questionTopics = getColumnFromKey(StringConstants.QUESTION_TOPICS,
				keyColumnFinder.get(StringConstants.QUESTIONS_TABLE), questionId, StringConstants.QUESTIONS_TABLE,
				statement);

		// if it's not empty, return all the topics that are divided by the
		// topic divider character
		if (questionTopics.length() != 0)
			return questionTopics.substring(1).split(StringConstants.TOPIC_DIVIDER);

		// else - return an empty array
		return new String[0];

	}

	/**
	 * Updates the user topic ranks of a given user and all the topics
	 * associated with a given question.
	 * 
	 * @param username
	 *            The name of the given user.
	 * @param questionId
	 *            The id of the given question.
	 * @param rankChange
	 *            The rank change to be applied to the topics.
	 * @param statement
	 *            A statement object to update the database.
	 * @throws SQLException
	 *             If fails to update the database.
	 */
	private static void updateUserTopicRanks(String username, int questionId, int rankChange, Statement statement)
			throws SQLException {

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

	/**
	 * Gets the new questions from the database. A question is new if no answer
	 * has been submitted to it yet. The questions are retrieved sorted from
	 * newest to oldest, and the user who requests to view them may view up to
	 * 20 at a time.
	 * 
	 * @param pageNumber
	 *            The number of the page which the user requests to view.
	 * @param requestingUsername
	 *            The name of the user who requests to view the questions.
	 * @param connection
	 *            A connection object used to provide statement objects to query
	 *            the database throughout the method operation time.
	 * @param response
	 *            A response object used to send information to the client which
	 *            is retrieved in run time.
	 * @return An array which includes the questions to be viewed by the user.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static Question[] getNewQuestions(int pageNumber, String requestingUsername, Connection connection,
			HttpServletResponse response) throws SQLException {

		// getting a statement object to query the database
		Statement statement = connection.createStatement();

		// getting a result set containing the questions sorted
		// in descending order by their id (time of submission)

		ResultSet sortedQuestions = statement
				.executeQuery(String.format("SELECT %s FROM %s WHERE %s=0 ORDER BY %s DESC", StringConstants.ALL,
						StringConstants.QUESTIONS_TABLE, StringConstants.QUESTION_ANSWERS_NUMBER, StringConstants.ID));

		// moving the cursor to the required row
		int startIndex = (pageNumber - 1) * 20 + 1;
		for (int i = 1; i < startIndex; i++)
			sortedQuestions.next();

		// putting the needed questions into an array list of questions

		ArrayList<Question> neededQuestionsArrayList = new ArrayList<Question>();

		// an integer required to notify the user if the current page is the
		// last
		int isLastPage = 0;

		for (int i = 0; i < 20; i++) {

			// if the result set is finished, this page is the last
			if (sortedQuestions.next() == false) {
				isLastPage = 1;
				break;
			}

			neededQuestionsArrayList.add(buildQuestionFromRecord(sortedQuestions, requestingUsername, connection));

		}

		if (isLastPage == 1 || sortedQuestions.next() == false)
			isLastPage = 1;

		ClientInteractor.sendData(response, "{ \"isLastPage\": " + isLastPage + ", \"questions\": ");

		statement.close();

		return neededQuestionsArrayList.toArray(new Question[neededQuestionsArrayList.size()]);

	}

	/**
	 * Gets the existing questions from the database. The questions are
	 * retrieved sorted by their rating score, which was calculated by the
	 * project formula, from high to low. The user who requests to view them may
	 * view up to 20 at a time.
	 * 
	 * @param pageNumber
	 *            The number of the page which the user requests to view.
	 * @param requestingUsername
	 *            The name of the user who requests to view the questions.
	 * @param connection
	 *            A connection object used to provide statement objects to query
	 *            the database throughout the method operation time.
	 * @param response
	 *            A response object used to send information to the client which
	 *            is retrieved in run time.
	 * @return An array which includes the questions to be viewed by the user.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static Question[] getExistingQuestions(int pageNumber, String requestingUsername, Connection connection,
			HttpServletResponse response) throws SQLException {

		// getting a statement object to query the database
		Statement statement = connection.createStatement();

		// getting a result set containing the questions sorted
		// in descending order by their rating

		ResultSet sortedQuestions = statement.executeQuery(String.format("SELECT %s FROM %s ORDER BY %s DESC",
				StringConstants.ALL, StringConstants.QUESTIONS_TABLE, StringConstants.RATING));

		// moving the cursor to the required row
		int startIndex = (pageNumber - 1) * 20 + 1;
		for (int i = 1; i < startIndex; i++)
			sortedQuestions.next();

		// putting the needed questions into an array list of questions

		ArrayList<Question> neededQuestionsArrayList = new ArrayList<Question>();

		// an integer used to indicate if this page is the last page
		int isLastPage = 0;

		for (int i = 0; i < 20; i++) {

			// if the result set is finished - this page is the last page
			if (sortedQuestions.next() == false) {
				isLastPage = 1;
				break;
			}

			neededQuestionsArrayList.add(buildQuestionFromRecord(sortedQuestions, requestingUsername, connection));

		}

		if (isLastPage == 1 || sortedQuestions.next() == false)
			isLastPage = 1;

		ClientInteractor.sendData(response, "{ \"isLastPage\": " + isLastPage + ", \"questions\": ");

		statement.close();

		return neededQuestionsArrayList.toArray(new Question[neededQuestionsArrayList.size()]);

	}

	/**
	 * Gets the top 20 highly rated users from the database.
	 * 
	 * @param requestingUsername
	 *            The name of the user who requests to view the users.
	 * @param connection
	 *            A connection object used to provide statement objects to query
	 *            the database throughout the method operation time.
	 * @param response
	 *            A response object used to send information to the client which
	 *            is retrieved in run time.
	 * @return An array which includes the users to be viewed by the user.
	 * @throws SQLException
	 *             If fails to query the database.
	 * 
	 */
	public static User[] getUsersLeaderBoard(String requestingUsername, Connection connection,
			HttpServletResponse response) throws SQLException {

		// getting a statement object to query the database
		Statement statement = connection.createStatement();

		// getting a result set containing the users sorted
		// in descending order by their rating

		ResultSet sortedUsers = statement.executeQuery(String.format("SELECT %s FROM %s ORDER BY %s DESC",
				StringConstants.ALL, StringConstants.USERS_TABLE, StringConstants.RATING));

		// putting the needed users into an array list of users

		ArrayList<User> neededUsersArrayList = new ArrayList<User>();

		for (int i = 0; i < 20; i++) {

			if (sortedUsers.next() == false)
				break;

			neededUsersArrayList.add(buildUserFromRecord(sortedUsers, requestingUsername, connection));

		}

		statement.close();

		return neededUsersArrayList.toArray(new User[neededUsersArrayList.size()]);

	}

	/**
	 * Gets the topics requested by a user sorted by their popularity. The user
	 * may request to view up to 20 topics at a time.
	 * 
	 * @param pageNumber
	 *            The number of the page requested by the user.
	 * @param statement
	 *            A statement object used to query the database.
	 * @param response
	 *            A response object used to send information to the client which
	 *            is retrieved in run time.
	 * @return An array which includes the topics to be viewed by the user.
	 * @throws SQLException
	 *             If fails to query the database.
	 * 
	 */
	public static Topic[] getTopTopics(int pageNumber, Statement statement, HttpServletResponse response)
			throws SQLException {

		// getting a result set containing the topics, sorted descending order
		// by their rating

		ResultSet sortedTopics = statement.executeQuery(String.format("SELECT %s FROM %s ORDER BY %s DESC",
				StringConstants.ALL, StringConstants.TOPICS_TABLE, StringConstants.TOPIC_POPULARITY));

		// moving the cursor to the required row
		int startIndex = (pageNumber - 1) * 20 + 1;
		for (int i = 1; i < startIndex; i++)
			sortedTopics.next();

		// putting the needed topics into an array list of topics

		ArrayList<Topic> neededTopicsArrayList = new ArrayList<Topic>();

		// an integer used to indicate if it's the last page
		int isLastPage = 0;

		for (int i = 0; i < 20; i++) {

			// if the result set is finished, this is the last page
			if (sortedTopics.next() == false) {
				isLastPage = 1;
				break;
			}

			neededTopicsArrayList.add(buildTopicFromRecord(sortedTopics));

		}

		if (isLastPage == 1 || sortedTopics.next() == false)
			isLastPage = 1;

		ClientInteractor.sendData(response, " { \"isLastPage\": " + isLastPage + ", \"topics\": ");

		return neededTopicsArrayList.toArray(new Topic[neededTopicsArrayList.size()]);

	}

	/**
	 * Gets a user summary.
	 * 
	 * @param username
	 *            The name of the user whose summary is requested to view.
	 * @param requestingUsername
	 *            The name of the user who requests to view the the user
	 *            summary.
	 * @param connection
	 *            A connection object used to provide statement objects to query
	 *            the database throughout the method operation time.
	 * @return A user object which includes the information to be viewed.
	 * @throws SQLException
	 *             If fails to query the database.
	 * 
	 */
	public static User getUserSummary(String username, String requestingUsername, Connection connection)
			throws SQLException {

		// getting a statement object to query the database
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

	/**
	 * Gets the top questions associated with a given topic. The questions will
	 * be sorted by their rating.
	 * 
	 * @param topic
	 *            The name of the topic associated with the questions.
	 * @param pageNumber
	 *            The page number which is requested to view by the user.
	 * @param requestingUsername
	 *            The name of the user who requests to view the questions.
	 * @param connection
	 *            A connection object used to provide statements to query the
	 *            database throughout the execution.
	 * @param response
	 *            A response object used to write data, which is retrieved in
	 *            run time, to the client.
	 * @return An array of questions which includes the top questions associated
	 *         with the given topic.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	public static Question[] getTopQuestionsByTopic(String topic, int pageNumber, String requestingUsername,
			Connection connection, HttpServletResponse response) throws SQLException {

		// getting a statement object to query the database
		Statement statement = connection.createStatement();

		// getting a questions result set where the questions are associated
		// with the given topic, sorted by their question rating

		ResultSet sortedQuestionsByTopicSet = statement.executeQuery(String.format(
				"SELECT %s FROM %s WHERE %s like '%%#%s%%' ORDER BY %s DESC", StringConstants.ALL,
				StringConstants.QUESTIONS_TABLE, StringConstants.QUESTION_TOPICS, topic, StringConstants.RATING));

		System.out.println("page number = " + pageNumber + " topic name = " + topic);
		
		// moving the cursor to the required row
		int startIndex = (pageNumber - 1) * 20 + 1;
		for (int i = 1; i < startIndex; i++)
			sortedQuestionsByTopicSet.next();

		// putting the needed questions from the above records in an array list

		ArrayList<Question> neededQuestions = new ArrayList<Question>();

		// an integer used to indicate if it's the last page
		int isLastPage = 0;

		for (int i = 0; i < 20; i++) {

			// if the result set is finished then this is the last page
			if (sortedQuestionsByTopicSet.next() == false) {
				isLastPage = 1;
				break;
			}

			neededQuestions.add(buildQuestionFromRecord(sortedQuestionsByTopicSet, requestingUsername, connection));

		}

		if (isLastPage == 1 || sortedQuestionsByTopicSet.next() == false)
			isLastPage = 1;

		ClientInteractor.sendData(response, " { \"isLastPage\": " + isLastPage + ", \"questions\": ");

		statement.close();
		
		for (Question question : neededQuestions)
			System.out.println(question.getText());

		return neededQuestions.toArray(new Question[neededQuestions.size()]);

	}

	/**
	 * Builds a user object from a record of a row in the users table.
	 * 
	 * @param userRecord
	 *            A record of a row in the users table.
	 * @param requestingUsername
	 *            The name of the user who requested to view the user.
	 * @param connection
	 *            A connection object to provide statements used to query the
	 *            database throughout the build.
	 * @return A user object which includes the information the user has asked
	 *         to view.
	 * @throws SQLException
	 *             If fails to query the database.
	 * 
	 */
	private static User buildUserFromRecord(ResultSet userRecord, String requestingUsername, Connection connection)
			throws SQLException {

		// getting a statement object to query the database
		Statement statement = connection.createStatement();

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

		// getting the user last answered questions

		Question[] lastAnsweredQuestions = getLastAnsweredQuestions(userToBuild.getName(), 5, requestingUsername,
				connection);

		// getting the answers which the user has submitted to those questions

		for (int i = 0; i < lastAnsweredQuestions.length; i++) {

			/*
			 * int questionId =
			 * Integer.parseInt(getColumnFromKey(StringConstants.QUESTION_ID,
			 * StringConstants.ID, lastAnsweredAnswers[i].getId(),
			 * StringConstants.ANSWERS_TABLE, statement));
			 * 
			 * ResultSet questionRecord =
			 * getRecordFromKey(StringConstants.QUESTIONS_TABLE,
			 * StringConstants.ID, questionId, statement);
			 * questionRecord.next();
			 * 
			 * lastAnsweredQuestions[i] =
			 * buildQuestionFromRecord(questionRecord, requestingUsername,
			 * connection);
			 * 
			 * Answer[] questionAnswers = new Answer[1]; questionAnswers[0] =
			 * lastAnsweredAnswers[i];
			 * 
			 * lastAnsweredQuestions[i].setAnswers(questionAnswers);
			 */

			Answer[] userAnswer = new Answer[1];

			ResultSet userAnswersResultSet = statement
					.executeQuery(String.format("SELECT %s FROM %s WHERE %s='%s' AND %s=%d", StringConstants.ID,
							StringConstants.ANSWERS_TABLE, StringConstants.AUTHOR, userToBuild.getName(),
							StringConstants.QUESTION_ID, lastAnsweredQuestions[i].getId()));

			userAnswersResultSet.next();

			ResultSet currentAnswer = getRecordFromKey(StringConstants.ANSWERS_TABLE, StringConstants.ID,
					userAnswersResultSet.getInt(StringConstants.ID), statement);

			currentAnswer.next();

			userAnswer[0] = buildAnswerFromRecord(currentAnswer, requestingUsername, connection);

			lastAnsweredQuestions[i].setAnswers(userAnswer);

		}

		userToBuild.setLastAnsweredQuestions(lastAnsweredQuestions);

		statement.close();

		return userToBuild;

	}

	/**
	 * Builds a question object from a record of a row in the questions table.
	 * 
	 * @param questionRecord
	 *            A record of a row in the questions table.
	 * @param requestingUsername
	 *            The name of the user who requested to view the question.
	 * @param connection
	 *            A connection object used to provide statements to query the
	 *            database throughout the execution.
	 * @return A question object which includes the information to be viewed.
	 * @throws SQLException
	 *             If fails to query the database.
	 */
	private static Question buildQuestionFromRecord(ResultSet questionRecord, String requestingUsername,
			Connection connection) throws SQLException {

		// getting a statement object to query the database
		Statement statement = connection.createStatement();

		// making the question object

		Question questionToBuild = new Question();

		questionToBuild.setId(questionRecord.getInt(StringConstants.ID));

		questionToBuild.setTimeStamp(questionRecord.getString(StringConstants.TIMESTAMP));

		questionToBuild.setText(questionRecord.getString(StringConstants.TEXT));

		String topicsString = questionRecord.getString(StringConstants.QUESTION_TOPICS);

		// getting the topics associated with the question

		Topic[] topics;

		// if the topic string is not empty, then divide it by the topic divider
		// character
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

			// else - make an empty array
			topics = new Topic[0];

		}

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

		ArrayList<Answer> questionAnswersArrayList = new ArrayList<Answer>();

		while (questionAnswers.next()) {

			questionAnswersArrayList.add(buildAnswerFromRecord(questionAnswers, requestingUsername, connection));

		}

		questionToBuild.setAnswers(questionAnswersArrayList.toArray(new Answer[questionAnswersArrayList.size()]));

		statement.close();

		return questionToBuild;

	}

	/**
	 * Builds an answer object from a record of a row in the answers table.
	 * 
	 * @param answerRecord
	 *            A record of a row in the answers table.
	 * @param requestingUsername
	 *            The name of the user who requested to view the answer.
	 * @param connection
	 *            A connection object used to provide statements to query the
	 *            database throughout the execution.
	 * @return An answer object which includes the information to be viewed.
	 * @throws SQLException
	 *             If fails to query the database.
	 * 
	 */
	private static Answer buildAnswerFromRecord(ResultSet answerRecord, String requestingUsername,
			Connection connection) throws SQLException {

		// getting a statement object to query the database
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

	/**
	 * Builds a topic object from a record of a row in the topics table.
	 * 
	 * @param topicRecord
	 *            A record of a row in the topics table.
	 * @param requestingUsername
	 *            The name of the user who requested to view the topic.
	 * @param connection
	 *            A connection object used to provide statements to query the
	 *            database throughout the execution.
	 * @return A topic object which includes the information to be viewed.
	 * @throws SQLException
	 *             If fails to query the database.
	 * 
	 */
	private static Topic buildTopicFromRecord(ResultSet topicRecord) throws SQLException {

		// building the topic object

		Topic topicToBuild = new Topic();

		topicToBuild.setName(topicRecord.getString(StringConstants.TOPIC_NAME));

		// returning the topic object

		return topicToBuild;

	}

	/**
	 * Gets the last asked questions of a user, sorted from newest to oldest.
	 * 
	 * @param username
	 *            The name of the user who asked to questions.
	 * @param amount
	 *            The amount of questions to get.
	 * @param requestionUsername
	 *            The name of the user who requests to view the questions.
	 * @param connection
	 *            A connection object used to provide statement objects to query
	 *            the database throughout the execution.
	 * @return An array of questions which includes the last asked questions of
	 *         the given user.
	 * @throws SQLException
	 *             If fails to query the database.
	 * 
	 */
	private static Question[] getLastAskedQuestions(String username, int amount, String requestionUsername,
			Connection connection) throws SQLException {

		// getting a statement object to query the database
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

	/**
	 * Gets the last answered questions of a user, sorted from newest to oldest.
	 * 
	 * @param username
	 *            The name of the user who answered the questions.
	 * @param amount
	 *            The amount of questions to get.
	 * @param requestionUsername
	 *            The name of the user who requests to view the questions.
	 * @param connection
	 *            A connection object used to provide statement objects to query
	 *            the database throughout the execution.
	 * @return An array of questions which includes the last answered questions
	 *         of the given user.
	 * @throws SQLException
	 *             If fails to query the database.
	 * 
	 */
	private static Question[] getLastAnsweredQuestions(String username, int amount, String requestingUsername,
			Connection connection) throws SQLException {

		// getting two statements objects to query the database
		Statement statement = connection.createStatement();
		Statement statementForGettingRecords = connection.createStatement();

		// getting a result set of answers answered by this user,
		// sorted by their id (from newest to oldest)
		ResultSet lastAnsweredAnswersSet = statement
				.executeQuery(String.format("SELECT %s FROM %s WHERE %s='%s' ORDER BY %s DESC", StringConstants.ALL,
						StringConstants.ANSWERS_TABLE, StringConstants.AUTHOR, username, StringConstants.ID));

		// getting the last 'amount' of answered questions
		HashSet<Integer> duplicatePreventer = new HashSet<Integer>();
		int i = 0;

		// getting an array list of questions from this result set

		ArrayList<Question> lastAnsweredQuestions = new ArrayList<Question>();

		while (i < amount && lastAnsweredAnswersSet.next()) {

			if (duplicatePreventer.contains(lastAnsweredAnswersSet.getInt(StringConstants.QUESTION_ID)))
				continue;

			ResultSet questionRecord = getRecordFromKey(StringConstants.QUESTIONS_TABLE, StringConstants.ID,
					lastAnsweredAnswersSet.getInt(StringConstants.QUESTION_ID), statementForGettingRecords);

			questionRecord.next();

			lastAnsweredQuestions.add(buildQuestionFromRecord(questionRecord, requestingUsername, connection));

			// preventing the duplication
			
			duplicatePreventer.add(questionRecord.getInt(StringConstants.ID));
			
			// preventing getting more than the amount requested
			
			i++;

		}

		/*
		 * if (lastAnsweredAnswersSet.next()) {
		 * 
		 * for (int i = 0; i < amount; i++) {
		 * 
		 * ResultSet questionRecord =
		 * getRecordFromKey(StringConstants.QUESTIONS_TABLE, StringConstants.ID,
		 * lastAnsweredAnswersSet.getInt(StringConstants.QUESTION_ID),
		 * statementForGettingRecords);
		 * 
		 * questionRecord.next();
		 * 
		 * lastAnsweredQuestions.add(buildQuestionFromRecord(questionRecord,
		 * requestingUsername, connection));
		 * 
		 * if (lastAnsweredAnswersSet.next() == false) break;
		 * 
		 * }
		 * 
		 * }
		 */
		statement.close();

		return lastAnsweredQuestions.toArray(new Question[lastAnsweredQuestions.size()]);

	}

	/**
	 * Shows the questions table. Used for debugging.
	 */
	public static void showQuestions() {

		System.out.println("Printing the questions table:\n");

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

	/**
	 * Shows the answers table. Used for debugging.
	 */
	public static void showAnswers() {

		System.out.println("Printing the answers:\n");

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

	/**
	 * Shows the users table. Used for debugging.
	 */
	public static void showUsers() {

		System.out.println("Printing users.\n");

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

	/**
	 * Shows the topics table. Used for debugging.
	 */
	public static void showTopics() {

		System.out.println("Printing topics.\n");

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

	/**
	 * Shows the user question votes table. Used for debugging.
	 */
	public static void showUserQuestionVotes() {

		System.out.println("Printing user question votes.\n");

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

	/**
	 * Shows the user answer votes table. Used for debugging.
	 */
	public static void showUserAnswerVotes() {

		System.out.println("Printing user answer votes.\n");

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

	/**
	 * Shows the questions table. Used for debugging.
	 */
	public static void showUserTopicRanks() {

		System.out.println("Printing user topic ranks.\n");

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
