package main;

// a class to save all the string constants throughout the project
public class StringConstants {

	// generic string constants

	public static final String TOPIC_DIVIDER = "#";
	public static final String ALL = "*";
	public static final String ATTRIBUTE_USERNAME_NAME = "username";
	public static final String PAGE_NUMBER = "pageNumber";
	public static final String NO_INPUT_PARAMETER = "undefined";
	public static final String PROJECT_DATE_FORMAT = "dd/MM/yyyy hh:mm:ss a";
	
	// database connection strings
	
	public static final String DATABASE_ACCESS_ERROR = "Error accessing the database";
	public static final String DERBY_EMBEDDED_DRIVER_PATH = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String DERBY_DATABASE_PATH = "jdbc:derby:C:\\Apache\\Databases\\TryDatabase";
	public static final String DERBY_SHUT_DOWN = DERBY_DATABASE_PATH + ";shutdown=true";
	public static final String DERBY_GET_DATABASE = DERBY_DATABASE_PATH + ";create=true";
	
	// network transfer parameters strings
	
	public static final String JSON_STRING_PARAMETER_NAME = "jsonObjectString";
	
	// error strings
	
	public static final String EXCPETION_MESSAGE = "Error message: ";
	public static final String RESPONSE_WRITER_ERROR = "Error getting the response writer";
	public static final String CLASS_PATH_ERROR = "Error with the class path";
	public static final String DATABASE_QUERIES_ERROR = "Error utilizing the database";
	
	// table names

	public static final String USERS_TABLE = "Users";
	public static final String QUESTIONS_TABLE = "Questions";
	public static final String ANSWERS_TABLE = "Answers";
	public static final String TOPICS_TABLE = "Topics";
	public static final String USER_QUESTION_VOTES_TABLE = "UserQuestionVotes";
	public static final String USER_ANSWER_VOTES_TABLE = "UserAnswerVotes";
	public static final String USER_TOPIC_RANKS_TABLE = "UserTopicRanks";

	// column types

	public static final String USERNAME_TYPE = "VARCHAR(10)";
	public static final String USER_USERNAME_TYPE = USERNAME_TYPE + " PRIMARY KEY";
	public static final String OTHER_USERNAME_TYPE = USERNAME_TYPE + " NOT NULL";
	public static final String USER_PASSWORD_TYPE = "VARCHAR(8) NOT NULL";
	public static final String USER_NICKNAME_TYPE = "VARCHAR(20) NOT NULL";
	public static final String USER_IMAGE_URL_TYPE = "VARCHAR(500)";
	public static final String USER_SHORT_DESCRIPTION_TYPE = "VARCHAR(50)";
	public static final String ID_TYPE = "INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)";
	public static final String REAL_TYPE = "REAL NOT NULL";
	public static final String INTEGER_TYPE = "INTEGER NOT NULL";
	public static final String TIMESTAMP_TYPE = "VARCHAR(25)";
	public static final String TOPIC_NAME_TYPE = "VARCHAR(50)";
	public static final String QUESTION_TOPICS_TYPE = "VARCHAR(500)";
	public static final String TOPIC_TOPIC_NAME_TYPE = TOPIC_NAME_TYPE + " PRIMARY KEY";
	public static final String QUESTION_TEXT_TYPE = "VARCHAR(300) NOT NULL";
	public static final String ANSWER_TEXT_TYPE = QUESTION_TEXT_TYPE;
	public static final String SESSION_ID_TYPE = "INTEGER PRIMARY KEY";

	// column names

	// generic column names

	public static final String RATING = "Rating";
	public static final String USERNAME = "Username";
	public static final String AUTHOR = "Author";
	public static final String ID = "ID";
	public static final String TIMESTAMP = "Timestamp";
	public static final String QUESTION_ID = "QuestionID";
	public static final String TEXT = "Text";
	public static final String VOTE = "VoteType";

	// users column names

	public static final String USER_PASSWORD = "Password";
	public static final String USER_NICKNAME = "Nickname";
	public static final String USER_IMAGE_URL = "ImageURL";
	public static final String USER_SHORT_DESCRIPTION = "ShortDescription";
	public static final String USER_QUESTIONS_NUMBER = "NumberOfQuestions";
	public static final String USER_ANSWERS_NUMBER = "NumberOfAnswers";
	public static final String USER_QUESTIONS_RATING_SUM = "QuestionsRatingSum";
	public static final String USER_ANSWERS_RATING_SUM = "AnswersRatingSum";

	// topics column names

	public static final String TOPIC_NAME = "Name";
	public static final String TOPIC_POPULARITY = "Popularity";

	// questions column names

	public static final String QUESTION_TOPICS = "Topics";
	public static final String QUESTION_VOTING_SCORE = "OwnVotingScore";
	public static final String QUESTION_ANSWERS_NUMBER = "NumberOfAnswers";
	public static final String QUESTION_ANSWERS_RATING_SUM = "AnswersRatingSum";

	// answers column names

	public static final String ANSWER_ID = "AnswerID";

	// user topic ranks column names

	public static final String USER_TOPIC_RANK_TOPIC_NAME = "TopicName";
	public static final String USER_TOPIC_RANK_RANK = "Rank";

	// open sessions column names

	public static final String SESSION_ID = "SessionID";

	// SQL create commands

	public static final String CREATE_USERS_TABLE = "CREATE TABLE " + USERS_TABLE + " (" + USERNAME + " "
			+ USER_USERNAME_TYPE + ", " + USER_PASSWORD + " " + USER_PASSWORD_TYPE + ", " + USER_NICKNAME + " "
			+ USER_NICKNAME_TYPE + ", " + USER_IMAGE_URL + " " + USER_IMAGE_URL_TYPE + ", " + USER_SHORT_DESCRIPTION
			+ " " + USER_SHORT_DESCRIPTION_TYPE + ", " + USER_QUESTIONS_NUMBER + " " + INTEGER_TYPE + ", "
			+ USER_ANSWERS_NUMBER + " " + INTEGER_TYPE + ", " + USER_QUESTIONS_RATING_SUM + " " + INTEGER_TYPE + ", "
			+ USER_ANSWERS_RATING_SUM + " " + INTEGER_TYPE + ", " + RATING + " " + REAL_TYPE + ")";

	public static final String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + QUESTIONS_TABLE + " (" + ID + " " + ID_TYPE
			+ ", " + TEXT + " " + QUESTION_TEXT_TYPE + ", " + AUTHOR + " " + OTHER_USERNAME_TYPE + ", "
			+ QUESTION_TOPICS + " " + QUESTION_TOPICS_TYPE + ", " + TIMESTAMP + " " + TIMESTAMP_TYPE + ", "
			+ QUESTION_VOTING_SCORE + " " + INTEGER_TYPE + ", " + QUESTION_ANSWERS_NUMBER + " " + INTEGER_TYPE + ", "
			+ QUESTION_ANSWERS_RATING_SUM + " " + INTEGER_TYPE + ", " + RATING + " " + REAL_TYPE + ", " + 
			"PRIMARY KEY(" + ID + "))";

	public static final String CREATE_ANSWERS_TABLE = "CREATE TABLE " + ANSWERS_TABLE + " (" + ID + " " + ID_TYPE + ", "
			+ QUESTION_ID + " " + INTEGER_TYPE + ", " + TEXT + " " + ANSWER_TEXT_TYPE + ", " + AUTHOR + " "
			+ OTHER_USERNAME_TYPE + ", " + TIMESTAMP + " " + TIMESTAMP_TYPE + ", " + RATING + " " + INTEGER_TYPE + ", " +
			"PRIMARY KEY(" + ID + "))";

	public static final String CREATE_TOPICS_TABLE = "CREATE TABLE " + TOPICS_TABLE + " (" + TOPIC_NAME + " "
			+ TOPIC_TOPIC_NAME_TYPE + ", " + TOPIC_POPULARITY + " " + REAL_TYPE + ")";

	public static final String CREATE_USER_QUESTION_VOTES_TABLE = "CREATE TABLE " + USER_QUESTION_VOTES_TABLE + " ("
			+ USERNAME + " " + OTHER_USERNAME_TYPE + ", " + QUESTION_ID + " " + INTEGER_TYPE + ", " + VOTE + " "
			+ INTEGER_TYPE + ")";

	public static final String CREATE_USER_ANSWER_VOTES_TABLE = "CREATE TABLE " + USER_ANSWER_VOTES_TABLE + " ("
			+ USERNAME + " " + OTHER_USERNAME_TYPE + ", " + ANSWER_ID + " " + INTEGER_TYPE + ", " + VOTE + " "
			+ INTEGER_TYPE + ")";

	public static final String CREATE_USER_TOPIC_RANKS_TABLE = "CREATE TABLE " + USER_TOPIC_RANKS_TABLE + " ("
			+ USERNAME + " " + OTHER_USERNAME_TYPE + ", " + USER_TOPIC_RANK_TOPIC_NAME + " " + TOPIC_NAME_TYPE + ", "
			+ USER_TOPIC_RANK_RANK + " " + INTEGER_TYPE + ")";

	// SQL insert commands

	public static final String INSERT_USER = "INSERT INTO " + USERS_TABLE + " VALUES (?, ?, ?, ?, ?, 0, 0, 0, 0, 0)";

	public static final String INSERT_QUESTION = "INSERT INTO " + QUESTIONS_TABLE + " (" + TEXT + ", " + AUTHOR +
			", " + QUESTION_TOPICS + ", " + TIMESTAMP + ", " + QUESTION_VOTING_SCORE + ", "
			+ QUESTION_ANSWERS_NUMBER + ", " + QUESTION_ANSWERS_RATING_SUM + ", " + RATING
			+ ") VALUES (?, ?, ?, ?, 0, 0, 0, 0)";

	public static final String INSERT_ANSWER = "INSERT INTO " + ANSWERS_TABLE + " (" + QUESTION_ID + ", " + TEXT + ", "
			+ AUTHOR + ", " + TIMESTAMP + ", " + RATING
			+ ") VALUES (?, ?, ?, ?, 0)";

	public static final String INSERT_TOPIC = "INSERT INTO " + TOPICS_TABLE + " VALUES(?, 0)";

	public static final String INSERT_USER_QUESTION_VOTE = "INSERT INTO " + USER_QUESTION_VOTES_TABLE
			+ " VALUES(?, ?, ?)";

	public static final String INSERT_USER_ANSWER_VOTE = "INSERT INTO " + USER_ANSWER_VOTES_TABLE + " VALUES(?, ?, ?)";

	public static final String INSERT_USER_TOPIC_RANK = "INSERT INTO " + USER_TOPIC_RANKS_TABLE + " VALUES(?, ?, 0)";

	// SQL queries

	public static final String SELECT = "SELECT %s FROM %s";

	public static final String SELECT_WITH_ONE_PREDICATE = "SELECT %s FROM %s WHERE %s='%s'";

	public static final String SELECT_WITH_TWO_PREDICATES = SELECT_WITH_ONE_PREDICATE + " AND %s=%s";

	public static final String UPDATE = "UPDATE %s SET %s=%s WHERE %s=%s";

	public static final String DELETE_WITH_ONE_PREDICATE = "DELETE FROM %s WHERE %s=%s";

	public static final String DELETE_WITH_TWO_PREDICATES = DELETE_WITH_ONE_PREDICATE + " AND %s=%s";
}
