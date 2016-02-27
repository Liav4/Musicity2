//alert("entering");


function doesContainSpecialChars(str) {
	
	var i;
	var flag = 0;
	for ( i = 0 ; i < str.length ; i++ ) {
		var asciiCode = str.charAt(i);
		if ( ( asciiCode > 'Z' && asciiCode < 'a' ) ||
			asciiCode > 'z' || 
			asciiCode < '0' || 
			( asciiCode > '9' && asciiCode < 'A' ) ) {
			
			flag = 1;
			
		}

	}
	
	return flag;
	
}



function validateSignUpForm(usernameToValidate, passwordToValidate, nicknameToValidate,
		urlToValidate, descriptionToValidate) {

	
	
	//alert( usernameToValidate );
	if ( ( typeof usernameToValidate == 'undefined' ) || 
		 ( usernameToValidate.length === 0 ) ) {
		
		return 8;
		
	}
	
	if ( doesContainSpecialChars(usernameToValidate) ) {

		return 1;

	}

	if ( usernameToValidate.length > 10 ) {

		return 2;

	}

	if ( ( typeof passwordToValidate == 'undefined' ) || 
		 ( passwordToValidate.length === 0 ) ) {
		return 9;
	}
	
	if ( passwordToValidate.length > 8 ) {

		return 3;

	}
	
	if ( ( typeof nicknameToValidate == 'undefined' ) || 
		 ( nicknameToValidate.length === 0 ) ) {
		return 10;
	}
	
	if ( nicknameToValidate.length > 20 ) {

		return 4;

	}
	
	if ( typeof urlToValidate != 'undefined' ) {
		if ( urlToValidate.length > 5000 ) {
			
			return 5;
	
		}
	}
	if ( typeof descriptionToValidate != 'undefined' ) {
		if ( descriptionToValidate.length > 50 ) {
	
			return 6;
	
		}
	}
	return 0;

}

/*
function updateProfilePic(val) {
	var pp = document.getElementById("profilePic");
	if (val) {
		pp.style.content = "url('" + val + "')";
	} 
	else {
		pp.style.content = 'url("deps/img/default_profile_pic.jpg")';
	}

}

*/

// sessionStorage.chr = ("helloworld2");



var enteringApp = angular.module('enterApp', []);

enteringApp.controller('enteringController', function ($scope, $http) {

	$scope.errorCode = -1;
	$scope.imageSource = "deps/img/default_profile_pic.jpg";
    $scope.sendLogInForm = function(){
    	//alert("got:\nname: " + $scope.logInUsername + ' pass:' + $scope.logInPassword)
    	
    	if ( typeof $scope.logInUsername == 'undefined' ||
    		 $scope.logInUsername.length == 0 || 
    		 typeof $scope.logInPassword == 'undefined' ||
    		 $scope.logInPassword.length ==0 ) {
    		
    		$scope.errorCode = -1;
    		return ;
    		
    	}
    		
    	
    	if( doesContainSpecialChars($scope.logInUsername) != 1) {
	        var logInData = {
	            'name': $scope.logInUsername,
	            'password' : $scope.logInPassword
	        }
	        $http.post("http://localhost:8080/WebApp2/LogIn", JSON.stringify(logInData))
	        .then(function(response) {
	        	alert(response.data.status);
	        	var status = response.data.status;
	        	if (status == 0) {
	        		
	        		sessionStorage.thisImageURL = response.data.imageURL;
	        		sessionStorage.thisUserNickname = response.data.nickname;
					sessionStorage.thisUsername = $scope.logInUsername;
					window.location.href = "http://localhost:8080/WebApp2/welcomePage.html";
					
				}
				else 
				{
		    		$scope.errorCode = 12;
				}
		    });
    	}
    	else { //the username contains special characters
    		
    		$scope.errorCode = 11;
    		
    	}
    }

    $scope.sendSignUpForm = function(){
    	var imageURL = $scope.signUpImage;
    	if ( ( !imageURL ) || ( imageURL == 'undefined' ) || ( imageURL == null ) || ( imageURL == "" ) )
		{
			//alert('u got default pic');
			imageURL = "deps/img/default_profile_pic.jpg";
		}


    	$scope.errorCode = validateSignUpForm($scope.signUpUsername, $scope.signUpPassword, $scope.signUpNickname, $scope.signUpImage,  $scope.signUpdescription); 
		if ( $scope.errorCode == 0 ) {

	    	var signUpData = {
	            'name'        : $scope.signUpUsername,
	            'password'    : $scope.signUpPassword,
	            'nickname' 	  : $scope.signUpNickname,
	            'description' : $scope.signUpdescription,
	            'image'		  : imageURL
	        };

	        $http.post("http://localhost:8080/WebApp2/SignUp", JSON.stringify(signUpData))
	        .then(function(response) {
	        	//alert(response.data);
		        var status = response.data;
		        if (status == 0) {

		        	sessionStorage.thisImageURL = imageURL;
		        	sessionStorage.thisUserNickname = $scope.signUpNickname;
					sessionStorage.thisUsername = $scope.signUpUsername;
					$scope.errorCode = 20; // everything is alright
					
					setTimeout( function() {
						window.location.href = "http://localhost:8080/WebApp2/welcomePage.html";
					}, 2000);

				}
		        else { // status == 1 hence user already exists
		        	$scope.errorCode = 7;
		        }
			});

   		};
   	};
   	$scope.updateProfilePic = function(val) {
   		if ($scope.signUpImage) {
   			$scope.imageSource = $scope.signUpImage;
   		} 
   		else {
   			$scope.imageSource = "deps/img/default_profile_pic.jpg";
   		}

   	}
});