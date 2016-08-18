var app = angular.module('myApp', ['FeedbackService', 'UserService', 'ngImgCrop']);

app.controller( 'reviewsController',  function($scope, $http, $window, $filter, $sce) {
	
	
	
	$scope.resourceReviews ={};
		
	 var url = '../ws/rest/reviewRelatedService/topRatedBooks/limit/100/token/test';
	  $http.get(url).
			success(function(data) {
			console.log('reviews got');
			$scope.resourceReviews = data;
		});
		
		
	
	$scope.goback=function(){
		$window.location.href = 'write_review.html';
	}
	
	
});


app.controller( 'indexController',  function($scope, $http, $window, $filter, $sce, Feedback) 
		{
			$scope.booksCount = 0;
			var redirectUrl = "http://www.grovenue.co/";
	var url = '../ws/rest/bookService/search/criteria/JEE/startFrom/1/maxResults/5/token/test';
		$http.get(url).
			success(function(data) {
			console.log('reviews got');
			$scope.searchResponse = data;
			$scope.books = data.books;
			
			console.log(' books length '+$scope.books.length);
			if($scope.books.length > 2){
			$scope.books = $scope.books.slice(0, 2);
			}
		});
	
	
	$scope.increaseCounter = function(){
		if($scope.booksCount == 4){
			$scope.booksCount = 0;
		}
		else{
			$scope.booksCount++;
		}
		
		if($scope.searchResponse.books.length == 5){
			if($scope.booksCount < 3){
				$scope.books = $scope.searchResponse.books.slice($scope.booksCount, $scope.booksCount + 2);
			}
			else{
				$scope.books = $scope.searchResponse.books.slice(0, 2);
			}
		}
	}
	
	$scope.decreaseCounter = function(){
		if($scope.booksCount == 0){
			$scope.booksCount = 4;
		}
		else{
			$scope.booksCount--;
		}
		
		if($scope.searchResponse.books.length == 5){
			if($scope.booksCount > 1){
				$scope.books = $scope.searchResponse.books.slice($scope.booksCount - 2, $scope.booksCount);
			}
			else{
				$scope.books = $scope.searchResponse.books.slice(0, 2);
			}
		}
	}
	

$scope.classesCounter = 0;
var search = window.encodeURIComponent('(?i).*B.*');

	
	var url = '../ws/rest/coachingClassService/searchCoachingClass/name/'+search+'/token/test';
		$http.get(url).
			success(function(data) {
			console.log('coaching classes got');
			$scope.searchResponseCoach = data;
			$scope.classes = data.classes;
			
			console.log(' books length '+$scope.classes.length);
			if($scope.classes.length > 2){
			$scope.classes = $scope.classes.slice(0, 2);
			}
		});
		
		
	$scope.increaseClassCounter = function(){
		if($scope.classesCounter == 4){
			$scope.classesCounter = 0;
		}
		else{
			$scope.classesCounter++;
		}
		
		if($scope.searchResponseCoach.classes.length >= 5){
			if($scope.classesCounter < 3){
				$scope.classes = $scope.searchResponseCoach.classes.slice($scope.classesCounter, $scope.classesCounter + 2);
			}
			else{
				$scope.classes = $scope.searchResponseCoach.classes.slice(0, 2);
			}
		}
	}
	
	$scope.decreaseClassCounter = function(){
		if($scope.classesCounter == 0){
			$scope.classesCounter = 4;
		}
		else{
			$scope.classesCounter--;
		}
		
		if($scope.searchResponseCoach.classes.length >= 5){
			if($scope.classesCounter > 1){
				$scope.classes = $scope.searchResponseCoach.classes.slice($scope.classesCounter - 2, $scope.classesCounter);
			}
			else{
				$scope.classes = $scope.searchResponseCoach.classes.slice(0, 2);
			}
		}
	}
	
$scope.digitalResourcesCounter = 0;
var searchD = window.encodeURIComponent('(?i).*A.*');

	
	var url = '../ws/rest/digitalToolService/searchDigitalTool/name/'+searchD+'/token/test';
		$http.get(url).
			success(function(data) {
			console.log('digital resources got');
			$scope.searchResponseD = data;
			$scope.tools = data.tools;
			
			console.log(' tools length '+$scope.tools.length);
			if($scope.tools.length > 2){
			$scope.tools  = $scope.tools .slice(0, 2);
			}
		});
		
		
	$scope.increaseDRCounter = function(){
		if($scope.digitalResourcesCounter == 4){
			$scope.digitalResourcesCounter = 0;
		}
		else{
			$scope.digitalResourcesCounter++;
		}
		
		if($scope.searchResponseD.tools.length >= 5){
			if($scope.digitalResourcesCounter < 3){
				$scope.tools =$scope.searchResponseD.tools.slice($scope.digitalResourcesCounter, $scope.digitalResourcesCounter + 2);
			}
			else{
				$scope.tools = $scope.searchResponseD.tools.slice(0, 2);
			}
		}
	}
	
	$scope.decreaseDRCounter = function(){
		if($scope.digitalResourcesCounter == 0){
			$scope.digitalResourcesCounter = 4;
		}
		else{
			$scope.digitalResourcesCounter--;
		}
		
		if($scope.searchResponseD.tools.length >= 5){
			if($scope.digitalResourcesCounter > 1){
				$scope.tools = $scope.searchResponseD.tools.slice($scope.digitalResourcesCounter - 2, $scope.digitalResourcesCounter);
				
			}
			else{
				$scope.tools = $scope.searchResponseD.tools.slice(0, 2);
			}
		}
	}	
	
	$scope.fetchOrdersForUser = function(){
		var url = '../ws/rest/orderWebService/orders/user/'+$scope.loggedInUser.user+'/socialMedia/'+$scope.loggedInUser.socialMedia+'/token/test';
		console.log('fetching orders');
		$http.get(url).
			success(function(data) {
			console.log(' orders got');
			$scope.orders = data;
				if($scope.orders.length == 0){
					$scope.noServicesAvailedYet = 'No services availed yet';
				}
			});
	}
	
	
	
	
	
	
	
	$scope.feedback = {};	
	$scope.submitted = "";
	$scope.user = {};
	$scope.someObject = {};
	$scope.termCheck = false;
	$scope.searchString = '';
	
	var user = $window.localStorage.getItem('loggedInUser');
	console.log('user is '+user);
	
	
	$scope.loggedInUser = '';
	$scope.profileText = 'login / register';
	$scope.profileDialog = 'modal';
	
	$scope.headeruser = '';
	$scope.repeatPassword = '';
	
	$scope.courseCompletionYear = ['1985', '1986', '1987', '1988', '1989', '1990', '1991', '1992', '1993', '1994', '1995', '1996', '1997', '1998', '1998', '1999', '2000', '2001', '2002', '2003', '2004', '2005', '2006', '2007', '2008', '2009', '2010', '2011', '2012', '2013', '2014', '2015', '2016'];

	if(user == 'null'){
		user = null;
	}
	
		if(user != null){
		console.log('right');
		user = jQuery.parseJSON(user);
		console.log('user.user is '+user.user);
			if(user.userType == 'ADMIN'){
				console.log('not logged in as Admin');
				$window.location.href = 'AdminShortCuts.html';
			}
			else{
				$scope.loggedInUser = user;
				$scope.headeruser = '#headeruser';
				$scope.profileDialog = '';
				//$scope.profileDialog = 'signoff';
				$scope.profileText = user.firstName+' '+user.lastName;
				console.log('profileDialog is '+$scope.profileDialog);
				$scope.fetchOrdersForUser();
			}
		
			
		}
		else{
		console.log('disable profile div');
		$scope.headeruser = '';
		
		}
	
	
	
	
	
	console.log('termscheck is '+$scope.termCheck );
	
	$scope.submit=function(feed){
		console.log(feed.email);
		$scope.submitted = Feedback.feedbacksubmit($scope.feedback);
		$scope.feedback.name = "";
		$scope.feedback.email="";
		$scope.feedback.message = "";
		
		document.getElementById('fbForm').style.display = "none";
		bootbox.alert("Your feedback is submitted. Thanks");
		return $scope.submitted;
	}
	
	
	$scope.downloadCoverLetter = function(){
		var email = window.encodeURIComponent($scope.loggedInUser.user);
		var sMedia = $scope.loggedInUser.socialMediaType;
		var url = '../ws/rest/templateService/checkIfCoverOrCVExistsForUser/user/'+email+'/socialMedia/'+sMedia+'/token/test';
			$http.post(url, null) .
			success(function(data) {
				if(!data.coverAvailable){
					bootbox.alert("Sorry! There is no cover letter generated yet for your profile!");
				}
				else{
					url = '../ws/rest/templateService/generateCoverLetterPDFForUser/user/'+email+'/socialMedia/'+sMedia+'/token/test';
					$http({
					    url: url,
					    dataType: 'json',
					    method: 'POST',
					    data: null,
					    headers: {
						"Content-Type": "application/json"
					    },
					    responseType : 'arraybuffer'
					}).success(function(response){
						console.log(' response got');
						var file = new Blob([response], {type: 'application/pdf'});
						saveAs(file, $scope.loggedInUser.user+'-COVER-.pdf');
						
						
					});
					
				}
			});
	}
	
	$scope.downloadCV = function(){
		var email = window.encodeURIComponent($scope.loggedInUser.user);
		var sMedia = $scope.loggedInUser.socialMediaType;
		var url = '../ws/rest/templateService/checkIfCoverOrCVExistsForUser/user/'+email+'/socialMedia/'+sMedia+'/token/test';
			$http.post(url, null) .
			success(function(data) {
				if(!data.cvAvailable){
					bootbox.alert("Sorry! There is no CV generated yet for your profile!");
				}
				else{
					url = '../ws/rest/templateService/generateCVPDFForUser/user/'+email+'/socialMedia/'+sMedia+'/token/test';
					$http({
					    url: url,
					    dataType: 'json',
					    method: 'POST',
					    data: null,
					    headers: {
						"Content-Type": "application/json"
					    },
					    responseType : 'arraybuffer'
					}).success(function(response){
						console.log(' response got');
						var file = new Blob([response], {type: 'application/pdf'});
						saveAs(file, $scope.loggedInUser.user+'-CV-.pdf');
						
						
					});
					
				}
			});
	}
	
	$scope.signup=function(user){
		console.log(user.email);
		
		var url = '../ws/rest/resourceService/saveOrUpdateUser/token/test';
		$http.post(url, user) .
			success(function(data) {
			console.log('request successful');
			
			var serviceResponse = data;
			console.log(JSON.stringify(serviceResponse));
				if(serviceResponse.responseStatus == 'User_Saved'){
					console.log('user saved');
					$scope.loggedInUser = user.email;
					//alert("Congratulations. You are registered. Please check your email for the activation link"); 
					bootbox.alert("Congratulations. You are registered. Please check your email for the activation link");
					$scope.closeRegDialog();
				}
				else if(serviceResponse.responseStatus.startsWith("User_Exists_Social_Media_")){
					var socialMedia = serviceResponse.responseStatus.substring("User_Exists_Social_Media_".length(), serviceResponse.responseStatus.length);
					//$("<div title='Already logged in through '"+socialMedia+"'> Please use another email id for registration</div>").dialog();
					//Feedback.showDialog('Already logged in through '+socialMedia, 'Please use another email id for registration');
					//alert('You had logged in earlier through '+socialMedia+'. You can continue use  '+socialMedia +' to log in or else register using a different password.');
					bootbox.alert('You had logged in earlier through '+socialMedia+'. You can continue use  '+socialMedia +' to log in or else register using a different password.');
					$scope.closeRegDialog();
				}
				else if(serviceResponse.responseStatus == 'User_Exists_Normal'){
					//$("<div title='Basic dialog'>You are already registered. Just Log in</div>").dialog();
					//Feedback.showDialog('Basic dialog', 'You are already registered. Just Log in');
					
					//alert('You are already registered. Just Log in using your email Id. In case you have forgotten your password, use the Forget Password link');
					bootbox.alert('You are already registered. Just Log in using your email Id. In case you have forgotten your password, use the Forget Password link');
					$scope.closeRegDialog();
				}
		});
		
		return $scope.submitted;
	}
	
	$scope.login = function(user){
	var usr = window.encodeURIComponent(user.user);
	var password = window.encodeURIComponent(user.password);
	var url = '../ws/rest/resourceService/authenticate/user/'+usr+'/password/'+password+'/socialMediaType/NONE';
		$http.post(url, null) .
			success(function(data) {
			$scope.loggedInUser = data;
			$scope.headeruser = '#headeruser';
			console.log('request successful');
			var u = data;
			console.log(' user got is '+u);
			$scope.profileDialog = '';
			$scope.profileText = data.firstName+' '+data.lastName;
			$scope.closeRegDialog();
			console.log('data... is '+data);
			$window.localStorage.setItem("loggedInUser", JSON.stringify(data));
			console.log('user type is'+user.userType);
			 //$scope.$apply()
				if(u.userType == 'ADMIN'){
					$window.location.href = 'AdminShortCuts.html';
				}
		}).
		error(function (data, status, headers, config) {
                        
			bootbox.alert("Your Login attempt failed! can you re-check your credentials and try again");
                });
	}
	
	$scope.display=function(){
		console.log('termscheck is ...'+$scope.termCheck);
		
	}
	
	$scope.check=function(){
		console.log('termscheck is ...'+$scope.termCheck);
		if($scope.termCheck){
			return false;
		}
		else{
			return true;
		}
	}
	
	$scope.closeRegDialog = function(){
	console.log(' inside closeRegDialog');
		document.getElementById('modal').style.display = "none";
	
	}
	
	$scope.signOff = function(){
		console.log('in signoff method');
		
		document.getElementById('headeruser').style.display = "none";
		bootbox.confirm("Are you sure you want to sign off!", function(result) {
		 console.log('result is '+result);
			if(result == true){
				console.log('true');
				$scope.headeruser = '';
				$scope.profileText = 'login / register';
				$scope.profileDialog = 'modal';
				$window.localStorage.setItem("loggedInUser", null);
				$scope.$apply();
			}
			else{
				//do nothing
			}
		}); 
	}
	
	$scope.search = function(){
		console.log(' in search function search string is '+$scope.searchString);
			if($scope.searchString == '' || $scope.searchString == 'null' || $scope.searchString == null){
				return;
			}
			else{
				$window.localStorage.setItem("searchString", $scope.searchString);
				$window.location.href = 'write_review.html';
				$scope.searchString = '';
			}
		
	}
	
	
	//tab
	
	$scope.showEditProfile = function(){
		document.getElementById('headeruser').style.display = "none";
		document.getElementById('tab').style.display = "";
	}
	
	$scope.showInviteFriends = function(){
		if(socialMedialogin ==false )
		{
			document.getElementById('inviteFriendsLogin').style.display = "";
			document.getElementById('headeruser').style.display = "none";
			
		}
		else
		{
		    $scope.getfriends();
		}
	}
	
	$scope.hideInviteFriends = function(){
	
		document.getElementById('inviteFriendsLogin').style.display = "none";
		
	}
	
	
	/******* Added By Sagar ********/
	$scope.myImage='';
	var usr = $.extend({},$scope.loggedInUser);
	$scope.defImage = usr.base64Image;
	$scope.myCroppedImage = '';
	$scope.showImagePreview = false;
	$scope.photoStatus = "Edit Photo";
	$scope.editOption  = angular.isDefined(usr.socialMediaType);

	$scope.editPhotoBegin = function(){ 
		if($scope.showImagePreview){
			$scope.photoStatus = "Edit photo";
		}else{
			$scope.photoStatus = "Done";
		}
		$scope.showImagePreview = !$scope.showImagePreview;
	}

	$scope.$watch('myCroppedImage',function(newVal,oldVal){
		console.log('-------',newVal);
		if($scope.showImagePreview){
				$scope.defImage = newVal;
				$scope.loggedInUser.base64Image = newVal;
		}
	});

	var handleFileSelect=function(evt) {
		var file=evt.currentTarget.files[0];
		var reader = new FileReader();
		reader.onload = function (evt) {
			$scope.$apply(function($scope){
				$scope.myImage=evt.target.result;
			});
		};
		reader.readAsDataURL(file);
	};
	angular.element(document.querySelector('#fileInput')).on('change',handleFileSelect);
	/******* end Added By Sagar ********/
	
	
	$scope.updateUserProfile = function(){
	console.log('first name is '+$scope.loggedInUser.firstName);
	console.log('birth date is '+$scope.loggedInUser.birthDate);
	console.log(JSON.stringify($scope.loggedInUser));
		$http.post('../ws/rest/resourceService/saveOrUpdateUser/token/test', $scope.loggedInUser).success(function(data)
		{
			console.log('user profile updated');
			$window.localStorage.setItem("loggedInUser", JSON.stringify($scope.loggedInUser));

		});
		
	}
	
	$scope.checkPasswordValidations = function(){
		if($scope.loggedInUser.newPassword.length < 5){
			bootbox.alert("New Password should have atleast 5 characters");
		}
		else if($scope.loggedInUser.newPassword != $scope.repeatPassword){
			bootbox.alert("New and Repeat Passwords are not same ");
		}
		else{
			$http.post('../ws/rest/resourceService/updatePassword/token/test', $scope.loggedInUser).success(function(data)
			{
				console.log('user password updated');
				$window.localStorage.setItem("loggedInUser", JSON.stringify($scope.loggedInUser));
				
				bootbox.alert("Congratulations! Your Password has been successfully updated.");
				$scope.loggedInUser.newPassword = '';
				$scope.repeatPassword = '';
				document.getElementById('changepassword').style.display = "none";
				
			});
		}
		
		
	}
	
	$scope.hideTab = function(){
	document.getElementById('tab').style.display = "none";
	}
	
	// Social Login Related Code
	// @author sharmapuneet1510@gmail.com
	var userId = "";
	var client = "";
	var network = "";
	var socialMedialogin = false;
	$scope.profilePic = "";
	$scope.friendslist = [];
	var loggedIdUser;
	var clientNetwork;
	$scope.inviteFriendLists = [];

	$scope.invitationMessage = "";

	// Check whether User Been Selected
	$scope.isSelected = function(emailAddress)
		{
			return $scope.inviteFriendLists.indexOf(emailAddress) >= 0;
		};
	$scope.addFriendToInvitationList = function(emailAddress)
		{
			if ($scope.inviteFriendLists.indexOf(emailAddress) === -1)
				{
					console.log("select : " + emailAddress);
					$scope.inviteFriendLists.push(emailAddress);
				}
			else
				{
					console.log("un-select : " + emailAddress);
					$scope.inviteFriendLists.splice($scope.inviteFriendLists.indexOf(emailAddress), 1);
				}
		};

	$scope.inviteFriends = function()
		{
			var url = '../ws/rest/resourceService/inviteFriendThroughSocialMedia/socialMediaType/' + clientNetwork + '/token/test?userId=' + userId;
			$scope.invitation = {};
			$scope.invitation.emailAddresses = $scope.inviteFriendLists;
			$scope.invitation.invitationMessage = $scope.invitationMessage;
			$http.post(url, $scope.invitation).success(function(data)
				{
					// alert("User successfully Invited");
					$("#invitefriendsmodal").hide();
				});

		}
	$scope.friendslistTemp = [];
	$scope.getfriends = function()
		{
			if (clientNetwork == 'google' || clientNetwork == 'facebook')
				{
					$("#loading").show();
					var path;
					if (clientNetwork == 'google')
						{
							path = "me/contacts";
						}
					else if (clientNetwork == 'facebook')
						{
							path = "me/friends";
						}
					hello(clientNetwork).api(path, {
						limit : 1000
					}).then(function responseHandler(response)
						{
							for (var i = 0; i < response.data.length; i++)
								{
									var socialFriend = response.data[i];
									console.log(JSON.stringify(socialFriend));
									$scope.friend = {};
									// $scope.friend.displayPic=socialFriend.thumbnail;
									// can be used in future
									$scope.friend.displayPic = "images/userfill.png";
									$scope.friend.email = socialFriend.email;
									if (socialFriend.name != null && socialFriend.name != "")
										{
											$scope.friend.displayName = socialFriend.name;
											$scope.friendslistTemp.push($scope.friend);
										}
								}
							if (response.data.length > 0)
								{
									$http.post('../ws/rest/resourceService/addFriendsFromSocialMedia/socialMediaType/' + clientNetwork + '/token/test?userId=' + userId, $scope.friendslistTemp).success(function(response)
										{
											for (var i = 0; i < response.length; i++)
												{
													$scope.friend = response[i];
													$scope.friendslist.push($scope.friend);
													$scope.addFriendToInvitationList($scope.friend.email);
												}
											console.log("Updated List of Emails" + JSON.stringify($scope.inviteFriendLists));
											// 
											$("#loading").hide();
											$("#modal").hide();
											$("#invitefriendsmodal").show();
											// $scope.$apply();
										});
									console.log(JSON.stringify($scope.friendslist));
									console.log(JSON.stringify($scope.friendslist.length));
								}
						});
				}
		}

	$scope.closeInvitefriendsmodal = function()
		{
			$("#invitefriendsmodal").hide();
		}
	
	$scope.socialLogin = function(network)
		{
			clientNetwork = network;
			if (network == 'google')
				{
					hello.init({
						'google' : '37695017955-fdprjmfel4mtmful1tfc1rpmtc955kpm.apps.googleusercontent.com'
					}, {
						redirect_uri : redirectUrl,
						scope : [ 'basic', 'email', 'friends' ]
					});
				}
			else if (network == 'facebook')
				{
					hello.init({
						'facebook' : '617188258436797'
					}, {
						redirect_uri : redirectUrl
					});
				}
			else if (network == 'linkedin')
				{
					hello.init({
						'linkedin' : '75aycy8klwf70r'
					}, {
						redirect_uri : redirectUrl,
						scope : [ 'friends', 'email' ]
					});
				}
			else if (network == 'twitter')
				{
					hello.init({
						'twitter' : 'NftcrZDlBGbPmhIX5vPMKSyPb'
					}, {
						redirect_uri : redirectUrl
					});
				}
			else if (network == 'instagram')
				{
					hello.init({
						'instagram' : '517d3cc4879f4e7385491b4352abcdc5'
					}, {
						redirect_uri : redirectUrl
					});
				}
			var socail = hello(network);
			socail.login().then(function(r)
				{
					socialMedialogin = true;
					// alert('You are signed in to '+network);
					return socail.api('me');
				}, function()
				{
					console.log(JSON.stringify(arguments))
				}).then(function(response)
				{
					socialMedialogin = true;
					console.log(JSON.stringify(response));
					var url = '../ws/rest/resourceService/getUserByEmailAddressAndSocialMediaType/socialMediaType/' + network + '/token/token';
					if (network == 'google')
						{
							// userId=response.email;
							for (var i = 0; i < response.emails.length; i++)
								{
									if (response.emails[i].type === 'account')
										{
											userId = response.emails[i].value
										}
								}
						}
					else if (network == 'facebook')
						{
							userId = response.email;

						}
					else if (network == 'linkedin')
						{
							userId = response.data.username + "@linkedin.com";
						}
					else if (network == 'twitter')
						{
							userId = response.screen_name + "@twitter.com";
						}
					else if (network == 'instagram')
						{
							userId = response.data.username + "@instagram.com";
						}

					$http.get(url + "?emailAddress=" + userId).success(function(userResponse)
						{
							if (userResponse == "")
								{
									if (network == 'google' || network == 'facebook' || network == 'twitter')
										{
											$scope.user.firstName = response.first_name;
											$scope.user.lastName = response.last_name;
											$scope.user.validated = response.verified;
										}
									else if (network == 'linkedin')
										{
											$scope.user.firstName = response.first_name;
											$scope.user.lastName = response.last_name;
											$scope.user.validated = response.verified;
										}
									else if (network == 'instagram')
										{
											var fullName = response.data.full_name;
											$scope.user.fullName = fullName;
											var names = fullName.split(" ");
											if (names.length === 1)
												{
													$scope.user.firstName = names[0];
												}
											else if (names.length > 2)
												{
													$scope.user.firstName = names[0];
													$scope.user.lastName = names[1];
												}

										}
									$scope.user.user = userId;
									$scope.user.socialMedia = true;
									$scope.user.socialMediaType = network.toUpperCase();
									$http.post('../ws/rest/resourceService/saveOrUpdateUser/token/test', $scope.user).success(function(data)
										{
											$http.get(url + "?emailAddress=" + userId).success(function(userResponse)
												{
													console.log('Create User User ' + JSON.stringify(userResponse.data));
													$scope.user = userResponse.data;
												});
											$("#modal").hide();
											$("#inviteFriends").show();
											console.log('Create User');

										});
								}
							else
								{
									console.log('Create User User ' + JSON.stringify(userResponse.data));
									$("inviteFriendsLogin").hide();
									$scope.user = userResponse.data;
									$("#modal").hide();
									$("#inviteFriends").show();
								}
						});
				});
		}
	
	
});
        	
		     