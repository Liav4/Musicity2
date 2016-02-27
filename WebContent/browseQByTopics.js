var topTopicsApp = angular.module('topTopics', []);

topTopicsApp.controller('listTopics', function ($scope, $http) {
	
	$http.get("http://localhost:8080/WebApp2/TopTopicsShowing?pageNumber=1")
	.then(function(response) {
		
    	if (response.data == 2) {
    		
			alert("Invalid session - moving to the entry page");
			window.location.href = "http://localhost:8080/WebApp2/index.html";
			return;
    		
    	}
		
		$scope.topTopics = response.data.topics;
		$scope.isLastPage = response.data.isLastPage;
		
	});
	
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
    	
		alert('you will go to ' + username + "'s page");
		
    	sessionStorage.userToRequest = username;
    	
    	window.location.href = "http://localhost:8080/WebApp2/showUser.html"
    	
    };
	
	
	//$scope.name = "Charlie";
	$scope.handleTopicClick = function(topic){
				
		var topicName = topic.name;
		
		sessionStorage.topicToRequest = topicName;
		
		window.location.href = "http://localhost:8080/WebApp2/topicQs.html";
		
	}
	$scope.thisPage = 1;//initialize it as 1
	$scope.handleNextPage = function(){			
		$scope.thisPage += 1;
		//requestQuestions($scope.thisPage);
		$http.get("http://localhost:8080/WebApp2/TopTopicsShowing?pageNumber=" + $scope.thisPage)
	    .then(function(response) {
	    	
	    	if (response.data == 2) {
	    		
				alert("Invalid session - moving to the entry page");
				window.location.href = "http://localhost:8080/WebApp2/index.html";
				return;
	    		
	    	}
	    	
	    	$scope.topTopics = response.data.topics;
			$scope.isLastPage = response.data.isLastPage;
			
	    });
		window.location.href = "#";
	}
	$scope.handlePrevPage = function(){
		$scope.thisPage -= 1;
		//requestQuestions($scope.thisPage);
		$http.get("http://localhost:8080/WebApp2/TopTopicsShowing?pageNumber=" + $scope.thisPage)
	    .then(function(response) {
	    	
	    	if (response.data == 2) {
	    		
				alert("Invalid session - moving to the entry page");
				window.location.href = "http://localhost:8080/WebApp2/index.html";
				return;
	    		
	    	}
	    	
	    	$scope.topTopics = response.data.topics;
			$scope.isLastPage = response.data.isLastPage;
			
	    });
		window.location.href = "#";
	}

});

