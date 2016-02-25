package main;

import com.google.gson.Gson;

import jsonClasses.Question;

public class Try {

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
		
		Gson gson = new Gson();
		
		System.out.println(((Question) gson.fromJson(" { \"text\": \"val'ue\" } ", Question.class)).getText());
						
	}
	
}
