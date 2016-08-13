var FeedbackService = angular.module('FeedbackService', [])
.service('Feedback', ['$http', function($http) {
    this.feedbacksubmit = function (feedback) {
    feedback.name = window.encodeURIComponent(feedback.name);
    feedback.email = window.encodeURIComponent(feedback.email);
	var url = '../ws/rest/utilService/feedbackEmail/email/'+feedback.email+'/name/'+feedback.name+'/token/test';
	$http.post(url, feedback.message).
	    success(function(data) {
	    	console.log('feedback posted to server');
	    });
    
	return "Thanks for writing in. Your message has been submitted. We will get back to you!";
    
    };
    
  

}]);

var UserService = angular.module('UserService', [])
.service('UserService', ['$http',  '$window', function($http, $window) {
var _this = this;
    this.signup = function (user) {
   
	console.log('bbbb '+user.email);
		
		var url = '../ws/rest/resourceService/saveOrUpdateUser/token/test';
		$http.post(url, user) .
			success(function(data) {
			console.log('request successful');
			
			var serviceResponse = data;
			console.log(JSON.stringify(serviceResponse));
				if(serviceResponse.responseStatus == 'User_Saved'){
					console.log('user saved');
					$scope.loggedInUser = user.email;
					alert("Congratulations. You are registered. Please check your email for the activation link"); 
					_this.closeRegDialog();
				}
				else if(serviceResponse.responseStatus.startsWith("User_Exists_Social_Media_")){
					var socialMedia = serviceResponse.responseStatus.substring("User_Exists_Social_Media_".length(), serviceResponse.responseStatus.length);
					//$("<div title='Already logged in through '"+socialMedia+"'> Please use another email id for registration</div>").dialog();
					//Feedback.showDialog('Already logged in through '+socialMedia, 'Please use another email id for registration');
					alert('You had logged in earlier through '+socialMedia+'. You can continue use  '+socialMedia +' to log in or else register using a different password.');
					_this.closeRegDialog();
				}
				else if(serviceResponse.responseStatus == 'User_Exists_Normal'){
					//$("<div title='Basic dialog'>You are already registered. Just Log in</div>").dialog();
					//Feedback.showDialog('Basic dialog', 'You are already registered. Just Log in');
					
					alert('You are already registered. Just Log in using your email Id. In case you have forgotten your password, use the Forget Password link');
					_this.closeRegDialog();
				}
		});
		
		return true;
    };
    
  this.closeRegDialog = function(user){
	console.log(' from service close');
	document.getElementById('modal').style.display = "none";
  };
  
  

}]);