package main;

import com.google.gson.Gson;

import jsonClasses.User;

/**
 * The DatabaseShower class is used to check all the tables and records in the
 * database. Mainly for debugging.
 * 
 * @author LIAV
 * @since 2016-02-26
 * @see main.DatabaseInteractor
 */
public class DatabaseShower {

	public static void main(String[] args) {

		DatabaseInteractor.showQuestions();

		System.out.println("*************************************************************************************");
		System.out.println("\n\n\n\n");

		DatabaseInteractor.showAnswers();

		System.out.println("*************************************************************************************");
		System.out.println("\n\n\n\n");

		DatabaseInteractor.showUsers();

		System.out.println("*************************************************************************************");
		System.out.println("\n\n\n\n");

		DatabaseInteractor.showTopics();

		System.out.println("*************************************************************************************");
		System.out.println("\n\n\n\n");

		DatabaseInteractor.showUserTopicRanks();

		System.out.println("*************************************************************************************");
		System.out.println("\n\n\n\n");

		DatabaseInteractor.showUserQuestionVotes();

		System.out.println("*************************************************************************************");
		System.out.println("\n\n\n\n");

		DatabaseInteractor.showUserAnswerVotes();
		
	}

}
