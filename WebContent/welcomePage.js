


function validateTopics(str) {
  
  if (str.length > 500) {
    alert("Please do not enter more than 500 characters");
    return '';
  }
  
  var i;
  
  for ( i = 0; i < str.length; i++ ) {
    if ( str.charAt(i) == '\'' ) {
      alert("No \' allowed");
      return '';
    }
    
    if ( str.charAt(i) == '+' ) {
        alert("No + allowed");
        return '';
    }
    
  }
  
  if (str.charAt(0) != '#') {
    alert("The first character must be '#'");
    return '';
  }
  str = str.substring(1);
  var strArray = str.split('#');
  var resultStr;
  for( i = 0; i < strArray.length; ++i ) {
    if(strArray[i].length > 50) {
        alert("Each topic can NOT contain more than 50 characters");
        return '';
    }
     else if( strArray[i].length > 0) {
       resultStr += '#' + strArray[i];
     }
  }
  return resultStr.substring(9);
}



var homeApp = angular.module('homeApp', []);

homeApp.controller('getUsername', function ($scope, $http) {
  $scope.username = sessionStorage.thisUserNickname;
  $scope.submitQ = function() {
	if( ( typeof $scope.qText != 'undefined' ) || ( $scope.qText.length != 0 ) ) {
		
		var topicsStr = validateTopics( $scope.qTopics );
		alert(topicsStr);
		if ( topicsStr.length !== 0) {
			var questionToSend = {
				'text'			: $scope.qText,
				'topicsString'	: topicsStr
			};
			$http.post("http://localhost:8080/WebApp2/QuestionSubmission", JSON.stringify(questionToSend)).then(function(response) {
			
				if ( response.data == 1 ) {
					alert("Failed to submit the question");
				}
				else if ( response.data == 2 )
				{
					alert("Invalid session - moving to the entry page");
					window.location.href = "http://localhost:8080/WebApp2/index.html";
					return;
				}
				
				window.location.href = "http://localhost:8080/WebApp2/welcomePage.html";
			});
		}
	
	}
  }
});