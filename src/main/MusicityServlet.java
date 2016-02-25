package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class MusicityServlet
 */
public class MusicityServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// parameters used by all the other musicity servlets

	// a connection object to the database
	protected Connection connection;

	// an object to convert from and to JSON formatted strings
	protected Gson gson = new Gson();

	// methods used by all the other musicity servlets

	public void init() {

		Connection connection = null;
		Statement statement = null;

		try {

			connection = DatabaseInteractor.getConnection();
			statement = connection.createStatement();

		} catch (SQLException exception) {
			exception.printStackTrace();
			return;
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
			return;
		}

		try {

			DatabaseInteractor.createTable(StringConstants.USERS_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.QUESTIONS_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.ANSWERS_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.TOPICS_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.USER_QUESTION_VOTES_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.USER_ANSWER_VOTES_TABLE, statement);
			DatabaseInteractor.createTable(StringConstants.USER_TOPIC_RANKS_TABLE, statement);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// a method to get the user name from the session
	protected String getUsernameFromSession(HttpServletRequest request) {

		HttpSession currentSession = request.getSession();
		if (currentSession.isNew())
			return null;

		return (String) currentSession.getAttribute(StringConstants.ATTRIBUTE_USERNAME_NAME);

	}

	protected String getCurrentTimestamp() {

		Date currentDate = new Date();

		/*
		 * here we make an assumption - we add the P.M. or A.M. sign
		 * to the time stamp to clarify exactly when the action was performed
		 */
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(StringConstants.PROJECT_DATE_FORMAT);

		return dateFormatter.format(currentDate);

	}
	
	protected String toJson(Object object) {
		
		Gson gson = new Gson();
		
		return gson.toJson(object);
		
	}
	
	protected String toJson(Object[] objects) {
		
		Gson gson = new Gson();
		
		return gson.toJson(objects);
		
	}

	// a method to convert a JSON formatted string to an object
	protected <T> Object fromJson(String jsonString, Class<T> classInstance) {

		System.out.println("jsonString = " + jsonString);
		return gson.fromJson(jsonString, classInstance);

	}

	// a method to get the data from an HTTP request
	protected String getRequestData(HttpServletRequest request) {

		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader reader = request.getReader();
			String currentLine = "";
			while ((currentLine = reader.readLine()) != null) {

				stringBuilder.append(currentLine);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doPost(request, response);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doGet(request, response);

	}

}
