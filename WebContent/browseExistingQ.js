var idG = 777;

//alert(questionsArr.questions[2].answers.length);

var browseExistingQsApp = angular.module('browseExistingQsApp', []);

browseExistingQsApp.controller('listQs', function ($scope,$http) {
	//$scope.questions = questionsArr.questions;
	//$scope.answers = questionsArr.questions.answers;

	$http.get("http://localhost:8080/WebApp2/ExistingQuestionsShowing?pageNumber=1")
    .then(function(response) {
        $scope.questions = response.data.questions;
        $scope.isLastPage = response.data.isLastPage;
    });
	$scope.name = "Charlie";
	$scope.providedAns = 'hello';
	$scope.submitAns = function(q) {
	    if (q.providedAns) {
	      alert('new answer to question with ID = ' + q.id + ' : ' + q.providedAns + 'num = ' + q.answers.length);
	     /* var tempAns = {
	      	timestamp:Date.now(),
			text:q.providedAns,
			authorNickname:"Charlie-nck wrote this ans",
			rating: 0,
			id:1232,
			vote:0,
			questionId:q.id
	      };*/
	      ansData = {
	      	'text': q.providedAns,
	      	'questionId' : q.id
	      };
	      $http.post('http://localhost:8080/WebApp2/QuestionAnswering', JSON.stringify(ansData));

	      var currentdate = new Date(); 

	      var temptimestamp = currentdate.getDate() + '/'
	      		 + (currentdate.getMonth()+1) + '/' + 
	      		 currentdate.getFullYear() + ' ' +
	      		  currentdate.getHours() + ':' + 
	      		  currentdate.getMinutes() + ':' + 
	      		  currentdate.getSeconds();

	      (q.answers).push({
	      	timestamp:temptimestamp,
			text:q.providedAns,
			authorNickname:"Charlie-nck wrote this ans",
			rating: 0,
			id:idG,
			vote:0,
			questionId:q.id
	      });
	      idG ++;
	      q.providedAns = '';
	    }
	}

	
	$scope.handleLike = function(q, isQ){//if we are in question isQ == 1
		//alert('handleLike');
		//check if the user isn't voting for himself
			if(q.vote != 1)
			{
				
				if(q.vote == 0)
				{
					q.rating += 1;
				}
				else //q.vote == -1
				{
					q.rating += 2;
				}
				q.vote = 1;
			}
			else
			{
				q.vote = 0;
				q.rating -= 1;
			}
			//alert('vote = ' + q.vote);
			voteData = {
				'vote':q.vote,
				'id':q.id
			};
			if(isQ)
				$http.post('http://localhost:8080/WebApp2/QuestionVoting', JSON.stringify(voteData));//.then(successCallback, errorCallback);
			else
				$http.post('http://localhost:8080/WebApp2/AnswerVoting', JSON.stringify(voteData));//.then(successCallback, errorCallback);
		//else - if the user is voting for himself - do nothing
	}

	
	
	//	temp = document.getElementById("like_q_1");
		//temp.style.border = "solid red;";


	$scope.handleDislike = function(q, isQ){
		if(q.vote != -1)
		{
			
			if(q.vote == 0)
			{
				q.rating -= 1;
			}
			else //q.vote == -1
			{
				q.rating -= 2;
			}
			q.vote = -1;
		}
		else
		{
			q.rating += 1;
			q.vote = 0
		}
		voteData = {
				'vote':q.vote,
				'id':q.id
			};
		if(isQ)
			$http.post('http://localhost:8080/WebApp2/QuestionVoting', JSON.stringify(voteData));//.then(successCallback, errorCallback);
		else
			$http.post('http://localhost:8080/WebApp2/AnswerVoting', JSON.stringify(voteData));//.then(successCallback, errorCallback);

	}

	$scope.thisPage = 1;//initialize it as 1
	$scope.handleNextPage = function(){			
		$scope.thisPage += 1;
		//requestQuestions($scope.thisPage);
		$http.get("http://localhost:8080/WebApp2/ExistingQuestionsShowing?pageNumber=" + $scope.thisPage)
	    .then(function(response) {
	        $scope.questions = response.data.questions;
	        $scope.isLastPage = response.data.isLastPage;
	    });
		window.location.href = "#";
	}
	$scope.handlePrevPage = function(){
		$scope.thisPage -= 1;
		//requestQuestions($scope.thisPage);
		$http.get("http://localhost:8080/WebApp2/ExistingQuestionsShowing?pageNumber=" + $scope.thisPage)
	    .then(function(response) {
	        $scope.questions = response.data.questions;
	        $scope.isLastPage = response.data.isLastPage;
	    });
		window.location.href = "#";
	}
	
	
});

		//var temp = document.getElementById("like_q_1");
		//temp.style.display = "none";

	/*function test(var)
	{
		var.style.border = "solid red";
	}
/*var qLen = questionsArr.questions.length;
var ansLen = 0;
for(i = 0 ; i < qLen ; i++)
{
	ansLen = questionsArr.questions[i].answers.length;
	if(ansLen == 1)
	{
		var myEl = angular.element[0]( document.querySelector( '#show-more' + questionsArr.questions[i].id) );
		alert(questionsArr.questions[i].id)
		var tempId = 'show-more' + questionsArr.questions[i].id;
		alert(tempId)
		var temp = document.getElementById('show-more');
		myEl.style.border = "solid red";
	}
}




*/
