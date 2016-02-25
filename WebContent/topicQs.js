
var idG = 777;


var topicQsApp = angular.module('topicPage', []);

topicQsApp.controller('listQs', function ($scope, $http) {
	$scope.theTopic = "Top Questions About " + sessionStorage.topicToRequest;
	$http.get("http://localhost:8080/WebApp2/QuestionsByTopicShowing?pageNumber=1" +
			"&topicName=" + sessionStorage.topicToRequest).then(function(response) {
			$scope.questions = response.data.questions;
			$scope.isLastPage = response.data.isLastPage;
				
			});

	$scope.name = "Charlie";
    $scope.providedAns = 'hello';
    $scope.submitAns = function(q) {
          if (q.providedAns) {
            alert('new answer to question with ID = ' + q.id + ' : ' + q.providedAns);
            q.providedAns = '';
          }
      }
    
    
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
		$http.get("http://localhost:8080/WebApp2/QuestionsByTopicShowing?pageNumber=" + $scope.thisPage + "&topicName=" + "4")
	    .then(function(response) {
	        $scope.questions = response.data.questions;
	        $scope.isLastPage = response.data.isLastPage;
	    });
		window.location.href = "#";
	}
	$scope.handlePrevPage = function(){
		$scope.thisPage -= 1;
		//requestQuestions($scope.thisPage);
		$http.get("http://localhost:8080/WebApp2/QuestionsByTopicShowing?pageNumber=" + $scope.thisPage + "&topicName=" + "4")
	    .then(function(response) {
	        $scope.questions = response.data.questions;
	        $scope.isLastPage = response.data.isLastPage;
	    });
		window.location.href = "#";
	}



});



























