var usersLeaderboardApp = angular.module('usersLeaderboardApp', []);

usersLeaderboardApp.controller('listUser', function ($scope, $http) {
	$http.get("http://localhost:8080/WebApp2/TopUsersShowing?pageNumber=1")
    .then(function(response) {

    	if (response.data == 2) {
    		
			alert("Invalid session - moving to the entry page");
			window.location.href = "http://localhost:8080/WebApp2/index.html";
			return;
    		
    	}
    	
		$scope.topUsers = response.data;
		
		
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
	
	$scope.handleTopicClick = function(topic){
		
			alert(topic.name);
			
			var topicName = topic.name;
			
			sessionStorage.topicToRequest = topicName;
			
			window.location.href = "http://localhost:8080/WebApp2/topicQs.html";
		
	}
    
    $scope.showUserSummary = function(username) {
    	
    	sessionStorage.userToRequest = username;
    	
    	window.location.href = "http://localhost:8080/WebApp2/showUser.html"
    	
    };
	
});

