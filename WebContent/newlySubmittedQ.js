var idG = 777;

alert("something");

var questionArr;

/*

function requestQuestions(pageNumber) {
	
	xmlhttp = new XMLHttpRequest()
	var url = "http://localhost:8080/WebApp2/NewQuestionsShowing?pageNumber=" + pageNumber;
	
	xmlhttp.open("GET", url, true)

	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.status == 200 && xmlhttp.readyState == 4) {

			questionArr = JSON.parse(xmlhttp.responseText);

		}

	}
	
	xmlhttp.send();
	
}

*/



var browseExistingQsApp = angular.module('viewNewlyQapp', []);

browseExistingQsApp.controller('listQs', function ($scope, $http) {
	//alert('entering controller');
	$scope.less = true;
	$http.get("http://localhost:8080/WebApp2/NewQuestionsShowing?pageNumber=1")
    .then(function(response) {
    	alert(response.data);
        $scope.questions = response.data.questions;
        $scope.isLastPage = response.data.isLastPage;
    });
	//$scope.questions = questionArr;
	//$scope.answers = questionsArr.questions.answers;
	$scope.name = "Charlie";
	$scope.providedAns = 'hello';
	$scope.submitAns = function(q) {
	    if (q.providedAns) {
	      alert('new answer to question with ID = ' + q.id + ' : ' + q.providedAns + 'num = ' + q.answers.length);
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
	//if ( questionArr[0].id == 1)
	//{
	//	alert('entered');
	//	temp = document.getElementById("like_q_1");
		//temp.style.border = "solid red;";
//	}
	$scope.handleLike = function(q, isQ){//if we are in question isQ == 1
		//alert('handleLike');
		//check if the user isnt voting for himself
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
		$http.get("http://localhost:8080/WebApp2/NewQuestionsShowing?pageNumber=" + $scope.thisPage)
	    .then(function(response) {
	        $scope.questions = response.data.questions;
	        $scope.isLastPage = response.data.isLastPage;
	    });
		window.location.href = "#";
	}
	$scope.handlePrevPage = function(){
		$scope.thisPage -= 1;
		//requestQuestions($scope.thisPage);
		$http.get("http://localhost:8080/WebApp2/NewQuestionsShowing?pageNumber=" + $scope.thisPage)
	    .then(function(response) {
	        $scope.questions = response.data.questions;
	        $scope.isLastPage = response.data.isLastPage;
	    });
		window.location.href = "#";
	}


});






