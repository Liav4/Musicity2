package main;

/**
 * The StringConstants class provides a variety of constant string used
 * throughout the project.
 * 
 * @author LIAV
 * @since 2016-02-26
 */
public class StringConstants {

	// generic string constants

	/**
	 * The character that divides the topics given from the client.
	 */
	public static final String TOPIC_DIVIDER = "#";

	/**
	 * The character which indicates selecting all the columns from a table.
	 */
	public static final String ALL = "*";

	/**
	 * The name of the attribute of sessions that the user name is kept in.
	 */
	public static final String ATTRIBUTE_USERNAME_NAME = "username";

	/**
	 * The name of the page number parameter in the get methods.
	 */
	public static final String PAGE_NUMBER = "pageNumber";

	/**
	 * The name of the topic name parameter in the get methods.
	 */
	public static final String TOPIC_NAME_PARAMETER = "topicName";

	/**
	 * The name given to an input that the client didn't supply any value to it.
	 */
	public static final String NO_INPUT_PARAMETER = "undefined";

	/**
	 * The time stamp format of this project.
	 */
	public static final String PROJECT_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

	// database connection strings

	/**
	 * An error after trying to access the database.
	 */
	public static final String DATABASE_ACCESS_ERROR = "Error accessing the database";

	/**
	 * The path to the derby driver.
	 */
	public static final String DERBY_EMBEDDED_DRIVER_PATH = "org.apache.derby.jdbc.EmbeddedDriver";

	/**
	 * The path in the source host to create the database in.
	 */
	public static final String DERBY_DATABASE_PATH = "jdbc:derby:C:\\Apache\\Databases\\TryDatabase";

	/**
	 * An addition to the {@link StringConstants#DERBY_DATABASE_PATH} string,
	 * used when shutting down the database.
	 */
	public static final String DERBY_SHUT_DOWN = DERBY_DATABASE_PATH + ";shutdown=true";

	/**
	 * An addition to the {@link StringConstants#DERBY_DATABASE_PATH} string,
	 * used when creating the database.
	 */
	public static final String DERBY_GET_DATABASE = DERBY_DATABASE_PATH + ";create=true";

	// network transfer parameters strings

	/**
	 * The string used to get the parameter name of the JSON string from the
	 * client.
	 */
	public static final String JSON_STRING_PARAMETER_NAME = "jsonObjectString";

	// error strings

	/**
	 * The prefix of an error message.
	 */
	public static final String EXCPETION_MESSAGE = "Error message: ";

	/**
	 * An error that occurs after trying to create the writer to the response
	 * packet.
	 */
	public static final String RESPONSE_WRITER_ERROR = "Error getting the response writer";

	/**
	 * An error that occurs after trying to use a class.
	 */
	public static final String CLASS_PATH_ERROR = "Error with the class path";

	/**
	 * An error that occurs after trying to query the database.
	 */
	public static final String DATABASE_QUERIES_ERROR = "Error utilizing the database";

	// table names

	/**
	 * The name of the users table.
	 */
	public static final String USERS_TABLE = "Users";

	/**
	 * The name of the questions table.
	 */
	public static final String QUESTIONS_TABLE = "Questions";

	/**
	 * The name of the answers table.
	 */
	public static final String ANSWERS_TABLE = "Answers";

	/**
	 * The name of the topics table.
	 */
	public static final String TOPICS_TABLE = "Topics";

	/**
	 * The name of the user question votes table.
	 */
	public static final String USER_QUESTION_VOTES_TABLE = "UserQuestionVotes";

	/**
	 * The name of the user answer votes table.
	 */
	public static final String USER_ANSWER_VOTES_TABLE = "UserAnswerVotes";

	/**
	 * The name of the user topic ranks table.
	 */
	public static final String USER_TOPIC_RANKS_TABLE = "UserTopicRanks";

	// column types

	/**
	 * The type of the user name column.
	 */
	public static final String USERNAME_TYPE = "VARCHAR(10)";

	/**
	 * The type of the user name column in the users table.
	 */
	public static final String USER_USERNAME_TYPE = USERNAME_TYPE + " PRIMARY KEY";

	/**
	 * The type of the user name column in other tables.
	 */
	public static final String OTHER_USERNAME_TYPE = USERNAME_TYPE + " NOT NULL";

	/**
	 * The type of the password column.
	 */
	public static final String USER_PASSWORD_TYPE = "VARCHAR(8) NOT NULL";

	/**
	 * The type of the nickname column.
	 */
	public static final String USER_NICKNAME_TYPE = "VARCHAR(20) NOT NULL";

	/**
	 * The type of the image URL column.
	 */
	public static final String USER_IMAGE_URL_TYPE = "VARCHAR(5000)";

	/**
	 * The type of the description column.
	 */
	public static final String USER_SHORT_DESCRIPTION_TYPE = "VARCHAR(50)";

	/**
	 * The type of the question id column - for different IDs
	 */
	public static final String QUESTION_ID_TYPE = "INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)";

	/**
	 * The type of the answer id column - for different IDs
	 */
	public static final String ANSWER_ID_TYPE = "INTEGER GENERATED ALWAYS AS IDENTITY (START WITH -1, INCREMENT BY -1)";

	/**
	 * The type a real type column.
	 */
	public static final String REAL_TYPE = "REAL NOT NULL";

	/**
	 * The type of an integer type column.
	 */
	public static final String INTEGER_TYPE = "INTEGER NOT NULL";

	/**
	 * The type of a time stamp column.
	 */
	public static final String TIMESTAMP_TYPE = "VARCHAR(25)";

	/**
	 * The type of a topic name column.
	 */
	public static final String TOPIC_NAME_TYPE = "VARCHAR(50)";

	/**
	 * The type of the question topics column.
	 */
	public static final String QUESTION_TOPICS_TYPE = "VARCHAR(500)";

	/**
	 * The type of the topic name column in the topics table.
	 */
	public static final String TOPIC_TOPIC_NAME_TYPE = TOPIC_NAME_TYPE + " PRIMARY KEY";

	/**
	 * The type of the question text column.
	 */
	public static final String QUESTION_TEXT_TYPE = "VARCHAR(300) NOT NULL";

	/**
	 * The type of the answer text column.
	 */
	public static final String ANSWER_TEXT_TYPE = QUESTION_TEXT_TYPE;

	// column names

	// generic column names

	/**
	 * The name of the rating column.
	 */
	public static final String RATING = "Rating";

	/**
	 * The name of the user name column.
	 */
	public static final String USERNAME = "Username";

	/**
	 * The name of the author column.
	 */
	public static final String AUTHOR = "Author";

	/**
	 * The name of the id column.
	 */
	public static final String ID = "ID";

	/**
	 * The name of the time stamp column.
	 */
	public static final String TIMESTAMP = "Timestamp";

	/**
	 * The name of the question id column.
	 */
	public static final String QUESTION_ID = "QuestionID";

	/**
	 * The name of the text column.
	 */
	public static final String TEXT = "Text";

	/**
	 * The name of the vote column.
	 */
	public static final String VOTE = "VoteType";

	// users column names

	/**
	 * The name of the password column.
	 */
	public static final String USER_PASSWORD = "Password";

	/**
	 * The name of the nickname column.
	 */
	public static final String USER_NICKNAME = "Nickname";

	/**
	 * The name of the image URL column.
	 */
	public static final String USER_IMAGE_URL = "ImageURL";

	/**
	 * The name of the description column.
	 */
	public static final String USER_SHORT_DESCRIPTION = "ShortDescription";

	/**
	 * The name of the user questions number column.
	 */
	public static final String USER_QUESTIONS_NUMBER = "NumberOfQuestions";

	/**
	 * The name of the user answers number column.
	 */
	public static final String USER_ANSWERS_NUMBER = "NumberOfAnswers";

	/**
	 * The name of the user questions rating sum column.
	 */
	public static final String USER_QUESTIONS_RATING_SUM = "QuestionsRatingSum";

	/**
	 * The name of the user answers rating sum column.
	 */
	public static final String USER_ANSWERS_RATING_SUM = "AnswersRatingSum";

	// topics column names

	/**
	 * The name of the topic name column.
	 */
	public static final String TOPIC_NAME = "Name";

	/**
	 * The name of the topic popularity column.
	 */
	public static final String TOPIC_POPULARITY = "Popularity";

	// questions column names

	/**
	 * The name of the question topics column.
	 */
	public static final String QUESTION_TOPICS = "Topics";

	/**
	 * The name of the question voting score column.
	 */
	public static final String QUESTION_VOTING_SCORE = "OwnVotingScore";

	/**
	 * The name of the question answers number column.
	 */
	public static final String QUESTION_ANSWERS_NUMBER = "NumberOfAnswers";

	/**
	 * The name of the question answers rating sum column.
	 */
	public static final String QUESTION_ANSWERS_RATING_SUM = "AnswersRatingSum";

	// answers column names

	/**
	 * The name of the answer id column.
	 */
	public static final String ANSWER_ID = "AnswerID";

	// user topic ranks column names

	/**
	 * The name of the topic name column in the user topic ranks table.
	 */
	public static final String USER_TOPIC_RANK_TOPIC_NAME = "TopicName";

	/**
	 * The name of the rank column in the user topic ranks table.
	 */
	public static final String USER_TOPIC_RANK_RANK = "Rank";

	// SQL create commands

	/**
	 * The SQL command to create the users table.
	 */
	public static final String CREATE_USERS_TABLE = "CREATE TABLE " + USERS_TABLE + " (" + USERNAME + " "
			+ USER_USERNAME_TYPE + ", " + USER_PASSWORD + " " + USER_PASSWORD_TYPE + ", " + USER_NICKNAME + " "
			+ USER_NICKNAME_TYPE + ", " + USER_IMAGE_URL + " " + USER_IMAGE_URL_TYPE + ", " + USER_SHORT_DESCRIPTION
			+ " " + USER_SHORT_DESCRIPTION_TYPE + ", " + USER_QUESTIONS_NUMBER + " " + INTEGER_TYPE + ", "
			+ USER_ANSWERS_NUMBER + " " + INTEGER_TYPE + ", " + USER_QUESTIONS_RATING_SUM + " " + INTEGER_TYPE + ", "
			+ USER_ANSWERS_RATING_SUM + " " + INTEGER_TYPE + ", " + RATING + " " + REAL_TYPE + ")";

	/**
	 * The SQL command to create the questions table.
	 */
	public static final String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + QUESTIONS_TABLE + " (" + ID + " "
			+ QUESTION_ID_TYPE + ", " + TEXT + " " + QUESTION_TEXT_TYPE + ", " + AUTHOR + " " + OTHER_USERNAME_TYPE
			+ ", " + QUESTION_TOPICS + " " + QUESTION_TOPICS_TYPE + ", " + TIMESTAMP + " " + TIMESTAMP_TYPE + ", "
			+ QUESTION_VOTING_SCORE + " " + INTEGER_TYPE + ", " + QUESTION_ANSWERS_NUMBER + " " + INTEGER_TYPE + ", "
			+ QUESTION_ANSWERS_RATING_SUM + " " + INTEGER_TYPE + ", " + RATING + " " + REAL_TYPE + ", " + "PRIMARY KEY("
			+ ID + "))";

	/**
	 * The SQL command to create the answers table.
	 */
	public static final String CREATE_ANSWERS_TABLE = "CREATE TABLE " + ANSWERS_TABLE + " (" + ID + " " + ANSWER_ID_TYPE
			+ ", " + QUESTION_ID + " " + INTEGER_TYPE + ", " + TEXT + " " + ANSWER_TEXT_TYPE + ", " + AUTHOR + " "
			+ OTHER_USERNAME_TYPE + ", " + TIMESTAMP + " " + TIMESTAMP_TYPE + ", " + RATING + " " + INTEGER_TYPE + ", "
			+ "PRIMARY KEY(" + ID + "))";

	/**
	 * The SQL command to create the topics table.
	 */
	public static final String CREATE_TOPICS_TABLE = "CREATE TABLE " + TOPICS_TABLE + " (" + TOPIC_NAME + " "
			+ TOPIC_TOPIC_NAME_TYPE + ", " + TOPIC_POPULARITY + " " + REAL_TYPE + ")";

	/**
	 * The SQL command to create the user question votes table.
	 */
	public static final String CREATE_USER_QUESTION_VOTES_TABLE = "CREATE TABLE " + USER_QUESTION_VOTES_TABLE + " ("
			+ USERNAME + " " + OTHER_USERNAME_TYPE + ", " + QUESTION_ID + " " + INTEGER_TYPE + ", " + VOTE + " "
			+ INTEGER_TYPE + ")";

	/**
	 * The SQL command to create the user answer votes table.
	 */
	public static final String CREATE_USER_ANSWER_VOTES_TABLE = "CREATE TABLE " + USER_ANSWER_VOTES_TABLE + " ("
			+ USERNAME + " " + OTHER_USERNAME_TYPE + ", " + ANSWER_ID + " " + INTEGER_TYPE + ", " + VOTE + " "
			+ INTEGER_TYPE + ")";

	/**
	 * The SQL command to create the user topic ranks table.
	 */
	public static final String CREATE_USER_TOPIC_RANKS_TABLE = "CREATE TABLE " + USER_TOPIC_RANKS_TABLE + " ("
			+ USERNAME + " " + OTHER_USERNAME_TYPE + ", " + USER_TOPIC_RANK_TOPIC_NAME + " " + TOPIC_NAME_TYPE + ", "
			+ USER_TOPIC_RANK_RANK + " " + INTEGER_TYPE + ")";

	// SQL insert commands

	/**
	 * The SQL command to insert a user to the users table.
	 */
	public static final String INSERT_USER = "INSERT INTO " + USERS_TABLE + " VALUES (?, ?, ?, ?, ?, 0, 0, 0, 0, 0)";

	/**
	 * The SQL command to insert a question to the questions table.
	 */
	public static final String INSERT_QUESTION = "INSERT INTO " + QUESTIONS_TABLE + " (" + TEXT + ", " + AUTHOR + ", "
			+ QUESTION_TOPICS + ", " + TIMESTAMP + ", " + QUESTION_VOTING_SCORE + ", " + QUESTION_ANSWERS_NUMBER + ", "
			+ QUESTION_ANSWERS_RATING_SUM + ", " + RATING + ") VALUES (?, ?, ?, ?, 0, 0, 0, 0)";

	/**
	 * The SQL command to insert an answer to the answers table.
	 */
	public static final String INSERT_ANSWER = "INSERT INTO " + ANSWERS_TABLE + " (" + QUESTION_ID + ", " + TEXT + ", "
			+ AUTHOR + ", " + TIMESTAMP + ", " + RATING + ") VALUES (?, ?, ?, ?, 0)";

	/**
	 * The SQL command to insert a topic to the topics table.
	 */
	public static final String INSERT_TOPIC = "INSERT INTO " + TOPICS_TABLE + " VALUES(?, 0)";

	/**
	 * The SQL command to insert a user question vote to the user question votes
	 * table.
	 */
	public static final String INSERT_USER_QUESTION_VOTE = "INSERT INTO " + USER_QUESTION_VOTES_TABLE
			+ " VALUES(?, ?, ?)";

	/**
	 * The SQL command to insert a user answer vote to the user answers votes
	 * table.
	 */
	public static final String INSERT_USER_ANSWER_VOTE = "INSERT INTO " + USER_ANSWER_VOTES_TABLE + " VALUES(?, ?, ?)";

	/**
	 * The SQL command to insert a user topic rank to the user topic ranks
	 * table.
	 */
	public static final String INSERT_USER_TOPIC_RANK = "INSERT INTO " + USER_TOPIC_RANKS_TABLE + " VALUES(?, ?, 0)";

	// SQL queries

	/**
	 * The SQL command to select a column from a table.
	 */
	public static final String SELECT = "SELECT %s FROM %s";

	/**
	 * The SQL command to select a column from a table with one predicate.
	 */
	public static final String SELECT_WITH_ONE_PREDICATE = "SELECT %s FROM %s WHERE %s='%s'";

	/**
	 * The SQL command to select a column from a table with two predicates.
	 */
	public static final String SELECT_WITH_TWO_PREDICATES = SELECT_WITH_ONE_PREDICATE + " AND %s=%s";

	/**
	 * The SQL command to update a column from a table.
	 */
	public static final String UPDATE = "UPDATE %s SET %s=%s WHERE %s=%s";

	/**
	 * The SQL command to delete a column from a table with one predicate
	 */
	public static final String DELETE_WITH_ONE_PREDICATE = "DELETE FROM %s WHERE %s=%s";

	/**
	 * The SQL command to select a column from a table with two predicates.
	 */
	public static final String DELETE_WITH_TWO_PREDICATES = DELETE_WITH_ONE_PREDICATE + " AND %s=%s";
}
