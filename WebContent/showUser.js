var showUserApp = angular.module('userPage', []);

showUserApp.controller('showUser', function ($scope, $http) {
	
	$http.get("http://localhost:8080/WebApp2/UserSummaryShowing?username=" +
			sessionStorage.userToRequest).then(function(response) {

		    	if (response.data == 2) {
		    		
					alert("Invalid session - moving to the entry page");
					window.location.href = "http://localhost:8080/WebApp2/index.html";
					return;
		    		
		    	}
				
		    	$scope.user = response.data;
		    	$scope.user.rating = ($scope.user.rating).toFixed(2);
		    	if (typeof $scope.user.description == 'undefined' ||
		    			$scope.user.description.length == 0 )
		    		$scope.user.description = "";
		
	});
	
	
	$scope.handleTopicClick = function(topic){
		
		alert(topic.name);
		
		var topicName = topic.name;
		
		sessionStorage.topicToRequest = topicName;
		
		window.location.href = "http://localhost:8080/WebApp2/topicQs.html";
		
	}
	$scope.imageURL = sessionStorage.thisImageURL;
	$scope.currentUsername = sessionStorage.thisUsername;
	$scope.currentUserNickname = sessionStorage.thisUserNickname;
	
	$scope.handleLogOut = function() {
		
    	sessionStorage.topicToRequest = '';
    	sessionStorage.userToRequest = '';
    	sessionStorage.thisUsername = '';
    	sessionStorage.thisUserNickname = '';
    	sessionStorage.thisImageURL = '';
		$http.post("http://localhost:8080/WebApp2/LogOut");
		window.location.href = "http://localhost:8080/WebApp2/index.html";
		return;
		
	}

	$scope.showUserSummary = function(username) {
		
    	sessionStorage.userToRequest = username;
    	
    	window.location.href = "http://localhost:8080/WebApp2/showUser.html"
    	
    };
    
    
    $scope.submitAns = function(q) {
	    if (q.providedAns) {
	      alert('new answer to question with ID = ' + q.id + ' : ' + q.providedAns + 'num = ' + q.answers.length);
	      
	      var currentdate = new Date();
	      var temptimestamp = currentdate.getDate() + '/'
   		 + (currentdate.getMonth()+1) + '/' + 
   		 currentdate.getFullYear() + ' ' +
   		  currentdate.getHours() + ':' + 
   		  currentdate.getMinutes() + ':' + 
   		  currentdate.getSeconds();
	      
	      
	      var tempAns = {
	      	timestamp		: temptimestamp,
			text			: q.providedAns,
			authorNickname  : sessionStorage.thisUserNickname,
			authorUsername  : sessionStorage.thisUsername,
			rating			: 0,
			id				: idG,
			vote			: 0,
			questionId		: q.id
	      };
	      
	      idG++;
	      ansData = {
	      	'text': q.providedAns,
	      	'questionId' : q.id
	      };
	      $http.post('http://localhost:8080/WebApp2/QuestionAnswering', JSON.stringify(ansData));


	      (q.answers).push(tempAns);
	      q.providedAns = '';
	    }
	}

    
    
    $scope.handleLike = function(q, isQ){//if we are in question isQ == 1
		//alert('handleLike');
		//check if the user isnt voting for himself
		if ( sessionStorage.thisUsername != q.authorUsername)
		{
			$scope.errorCode = -1;
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
		}
		else { //- if the user is voting for himself - do nothing
			$scope.thisId = q.id;
			$scope.errorCode = 13;
		} 

	}

	$scope.handleDislike = function(q, isQ){
		if ( sessionStorage.thisUsername != q.authorUsername)
		{
			$scope.errorCode = -1;
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
		
		else { // if the user is voting for himself
			$scope.thisId = q.id;
			$scope.errorCode = 13;
		} 

	}

});

