alert("entering");

function countWord(str) {
	return str.split(" ").length;
}

function validateSignUpForm(s_up_username, s_up_password, s_up_nickname,
		s_up_url, s_up_descr) {
	var succ_block = document.getElementById("registeration-success-message");
	var username_l_msg = document.getElementById("s_username_l_error");
	var password_l_msg = document.getElementById("s_password_l_error");
	var nickname_l_msg = document.getElementById("s_nickname_l_error");
	var url_l_msg = document.getElementById("s_url_l_error");
	var descr_l_msg = document.getElementById("s_descr_l_error");
	var succ_flag = true;
	var flag_username_l = false;
	var flag_password_l = false;
	var flag_nickname_l = false;
	var flag_url_l = false;
	var flag_descr_l = false;

	succ_flag = true
	if (s_up_username == null || s_up_username.length == 0
			|| s_up_username.length > 10) {
		/* alert("Name must be filled out"); */
		flag_username_l = true;
		succ_flag = false;
	}
	if (s_up_password == null || s_up_password.length == 0
			|| s_up_password.length > 8) {
		flag_password_l = true;
		succ_flag = false;
	}
	if (s_up_nickname == null || s_up_nickname.length == 0
			|| s_up_nickname.length > 20) {
		flag_nickname_l = true;
		succ_flag = false;
	}
	if (s_up_url.length > 400) {
		url_l_msg.style.display = "inline-block";
		flag_url_l = true;
		succ_flag = false;
	}

	if (countWord(s_up_descr) > 50) {
		descr_l_msg.style.display = "inline-block";
		flag_descr_l = true;
		succ_flag = false;
	}

	if (flag_username_l == true) {
		username_l_msg.style.display = "inline-block";
	} else {
		username_l_msg.style.display = "none";
	}

	if (flag_password_l == true) {
		password_l_msg.style.display = "inline-block";
	} else {
		password_l_msg.style.display = "none";
	}

	if (flag_nickname_l == true) {
		nickname_l_msg.style.display = "inline-block";
	} else {
		nickname_l_msg.style.display = "none";
	}

	if (flag_url_l == true) {
		url_l_msg.style.display = "inline-block";
	} else {
		url_l_msg.style.display = "none";
	}

	if (flag_descr_l == true) {
		descr_l_msg.style.display = "inline-block";
	} else {
		descr_l_msg.style.display = "none";
	}
	if (succ_flag == true)
		succ_block.style.display = "block";
	return succ_flag;

}

function updateProfilePic(val) {
	var pp = document.getElementById("profilePic");
	if (val) {
		pp.style.border = "solid red";
		pp.style.content = "url('" + val + "')";
	} else {
		pp.style.border = "solid green";
		pp.style.content = 'url("deps/img/default_profile_pic.jpg")';
	}

}

function handleSignUpForm() {

	var s_up_username = document.forms["sign-up_form"]["sign-up_username"].value;
	var s_up_password = document.forms["sign-up_form"]["sign-up_password"].value;
	var s_up_nickname = document.forms["sign-up_form"]["sign-up_user-nickname"].value;
	var s_up_url = document.forms["sign-up_form"]["sign-up_user-photo-url"].value;
	alert(s_up_url);
	if ( ( s_up_url == 'undefined' ) || ( s_up_url == null ) || ( s_up_url == "" ) )
	{
		alert('u got default pic');
		s_up_url = "deps/img/default_profile_pic.jpg";
	}
	var s_up_descr = document.forms["sign-up_form"]["sign-up_short-description"].value;

	var isOK = validateSignUpForm(s_up_username, s_up_password, s_up_nickname,
			s_up_url, s_up_descr);

	if (isOK)
		sendSignUpForm(s_up_username, s_up_password, s_up_nickname, s_up_url,
				s_up_descr);

	return false;

}

function sendSignUpForm(s_up_username, s_up_password, s_up_nickname, s_up_url,
		s_up_descr) {

	var xmlhttp = new XMLHttpRequest();
	var url = "http://localhost:8080/WebApp2/SignUp";

	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

			var status = xmlhttp.responseText;

			if (status == 0) {

				alert("Your account has been successfully created. Press "
						+ "OK to move to the home page");
				sessionStorage.thisUsername = s_up_username;
				window.location.href = "http://localhost:8080/WebApp2/welcomePage.html";

			}

			else {

				alert("User already exists");

			}

		}

	};

	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	
	var sign_up_query = {
		name : s_up_username,
		password : s_up_password,
		nickname : s_up_nickname,
		description : s_up_descr,
		image : s_up_url
	};
	
	/*var sign_up_query = "{ " +
    "name: '" + s_up_username +
    "', password: '" + s_up_password +
    "', nickname: '" + s_up_nickname +
    "', description: '" + s_up_descr +
    "', image: '" + s_up_url +
    "' }";*/

	alert(JSON.stringify(sign_up_query));
	
	/*
	 * xmlhttp.send("hidden-input=" + requestType + "&sign-up_username=" +
	 * username.value + "&sign-up_password=" + password.value +
	 * "&sign-up_user-nickname=" + userNickname.value +
	 * "&sign-up_user-photo-url=" + imageURL.value +
	 * "&sign-up_short-description=" + shortDescription.value); return false;
	 */

	xmlhttp.send("jsonObjectString=" + JSON.stringify(sign_up_query));

}

function handleLogInForm() {

	var username = document.getElementById("log-in_Username").value;
	var password = document.getElementById("log-in_Password").value;

	xmlhttp = new XMLHttpRequest();
	var url = "http://localhost:8080/WebApp2/LogIn";

	xmlhttp.onreadystatechange = function() {

		if (xmlhttp.status == 200 && xmlhttp.readyState == 4) {

			var status = xmlhttp.responseText;

			if (status == 0) {

				alert("you got it");
				sessionStorage.thisUsername = username;
				window.location.href = "http://localhost:8080/WebApp2/welcomePage.html";

			}

			else {

				alert("The username-password combination you entered is not valid");

			}

		}

	}

	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	var logInQuery = {
		name : username,
		password : password
	};

	xmlhttp.send("jsonObjectString=" + JSON.stringify(logInQuery));

	return false;

}

// sessionStorage.chr = ("helloworld2");
