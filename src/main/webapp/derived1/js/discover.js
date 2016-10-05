function generateToken() {
	var d = new Date().getTime();
	var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g,
			function(c) {
				var r = (d + Math.random() * 16) % 16 | 0;
				d = Math.floor(d / 16);
				return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
			});
	return uuid;
};

var app = angular.module('myApp', ['FeedbackService', 'UserService', 'ngImgCrop']);
app.controller( 'discoverController',  function($scope, $http, $window, Feedback) {

	$scope.bookmarkedResources = {};
	
		$scope.fetchBookmarks = function(){
					
		var user = $window.localStorage.getItem('loggedInUser');
		var usr = '';
			if (user === null || user === 'null' || user === "null") {
				return;
			}
			else{
				user = jQuery.parseJSON(user);
				usr = user.user;
			}
			
		
			var url = "../ws/rest/reviewRelatedService/fetchBookMarks/user/"+usr+"/token/test";
				$http
					.get(url)
					.success(
							function(response) {
								$scope.bookmarkedResources = response;
							})
					.error(
						function(errorResponse) {
						
						console.log(JSON.stringify(errorResponse));
					});
		}
		
		$scope.fetchBookmarks();
		
		$scope.goToBookmarkResource = function(resourceName, resourceType){
			bootbox.alert("Redirection to bookmarked resource coming soon!");
						// window.localStorage.setItem("isRedirectedSearch",
						// true);
						// window.localStorage.setItem("redirectSearchKeyword",
						// resourceName);
						// window.localStorage.setItem("resourceType",
						// resourceType);
						// $window.location.href = 'prepare_for_greatness.html';
		}


	<!-- Begin user sign/profile/social/etc related stuff
	
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
	
	$scope.courseCompletionYear = ['1985', '1986', '1987', '1988', '1989', '1990', '1991', '1992', '1993', '1994', '1995', '1996', '1997', '1998', '1998', '1999', 

'2000', '2001', '2002', '2003', '2004', '2005', '2006', '2007', '2008', '2009', '2010', '2011', '2012', '2013', '2014', '2015', '2016'];

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
				// $scope.profileDialog = 'signoff';
				$scope.profileText = user.firstName+' '+user.lastName;
				console.log('profileDialog is '+$scope.profileDialog);
				
			}
		
			
		}
		else{
		console.log('disable profile div');
		$scope.headeruser = '';
		
		}
	
		
	
	
	
	console.log('termscheck is '+$scope.termCheck );
	
	$scope.submit = function(feed) {
		console.log(feed.email);
		$scope.submitted = Feedback
				.feedbacksubmit($scope.feedback);
		$scope.feedback.name = "";
		$scope.feedback.email = "";
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
					// alert("Congratulations. You are registered. Please check
					// your email for the activation link");
					bootbox.alert("Congratulations. You are registered. Please check your email for the activation link");
					$scope.closeRegDialog();
				}
				else if(serviceResponse.responseStatus.startsWith("User_Exists_Social_Media_")){
					var socialMedia = serviceResponse.responseStatus.substring("User_Exists_Social_Media_".length(), serviceResponse.responseStatus.length);
					// $("<div title='Already logged in through
					// '"+socialMedia+"'> Please use another email id for
					// registration</div>").dialog();
					// Feedback.showDialog('Already logged in through
					// '+socialMedia, 'Please use another email id for
					// registration');
					// alert('You had logged in earlier through '+socialMedia+'.
					// You can continue use '+socialMedia +' to log in or else
					// register using a different password.');
					bootbox.alert('You had logged in earlier through '+socialMedia+'. You can continue use  '+socialMedia +' to log in or else register using a different password.');
					$scope.closeRegDialog();
				}
				else if(serviceResponse.responseStatus == 'User_Exists_Normal'){
					// $("<div title='Basic dialog'>You are already registered.
					// Just Log in</div>").dialog();
					// Feedback.showDialog('Basic dialog', 'You are already
					// registered. Just Log in');
					
					// alert('You are already registered. Just Log in using your
					// email Id. In case you have forgotten your password, use
					// the Forget Password link');
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
			 $scope.$apply()
				if(u.userType == 'ADMIN'){
					$window.location.href = 'AdminShortCuts.html';
				}
				
			$scope.fetchBookmarks();
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
				// do nothing
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
	
	
	// tab
	
	$scope.showEditProfile = function(){
		document.getElementById('headeruser').style.display = "none";
		document.getElementById('tab').style.display = "";
	}
	
					$scope.showInviteFriends = function() 
					{
						
						document.getElementById('headeruser').style.display = "none";
						if($scope.loggedInUser.socialMedia===false)
						{
						 	 bootbox.alert("LoggedIn user must be a social User in order to use this functionality");	
						}
						else	
						{
							$("#headerinvitefriend").show();
						}
						

					}
					$scope.socialLoginForGetFriends = function(network) 
					{
						$scope.socialLogin(network);
						$("#inviteFriendsLogin").hide();
						$scope.getfriends();
						$scope.headerinvitefriend();
					}

					$scope.headerinvitefriend=function()
					{
						$("#headerinvitefriend").hide();
					}
	
	
	/** ***** Added By Sagar ******* */
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
	/** ***** Added By Sagar ******* */
	
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
									$http.post('../ws/rest/resourceService/addFriendsFromSocialMedia/socialMediaType/' + clientNetwork + '/token/test?userId=' + $scope.loggedInUser.user, $scope.friendslistTemp).success(function(response)
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
	var  redirectUrl="index.html";
	$scope.socialLogin = function(network)
		{
		clientNetwork = network;
		if (network == 'google') {
			hello
					.init(
							{
								'google' : '37695017955-fdprjmfel4mtmful1tfc1rpmtc955kpm.apps.googleusercontent.com'
							}, {
								redirect_uri : 'index.html',
								scope : [ 'basic', 'email',
										'friends' ]
							});
		} else if (network == 'facebook') {
			hello.init({
				'facebook' : '617188258436797'
			}, {
				redirect_uri : 'index.html'
			});
		} else if (network == 'linkedin') {
			hello.init({
				'linkedin' : '75aycy8klwf70r'
			}, {
				redirect_uri : 'index.html',
				scope : [ 'friends', 'email' ]
			});
		} else if (network == 'twitter') {
			hello.init({
				'twitter' : 'NftcrZDlBGbPmhIX5vPMKSyPb'
			}, {
				redirect_uri : 'index.html'
			});
		} else if (network == 'instagram') {
			hello
					.init(
							{
								'instagram' : '517d3cc4879f4e7385491b4352abcdc5'
							}, {
								redirect_uri : 'index.html'
							});
		}
		var socail = hello(network);
		socail
				.login()
				.then(function(r) {
					socialMedialogin = true;
					// alert('You are signed in to '+network);
					return socail.api('me');
				}, function() {
					console.log(JSON.stringify(arguments))
				})
				.then(
						function(response) {
							socialMedialogin = true;
							console.log(JSON
									.stringify(response));
							var url = '../ws/rest/resourceService/getUserByEmailAddressAndSocialMediaType/socialMediaType/'
									+ network
									+ '/token/'
									+ generateToken();
							if (network == 'google') {
								// userId=response.email;
								$scope.profilePic = response.picture;
								for (var i = 0; i < response.emails.length; i++) {
									if (response.emails[i].type === 'account') {
										userId = response.emails[i].value
									}
								}
							} else if (network == 'facebook') {
								$scope.profilePic = response.picture;
								userId = response.email;
							} else if (network == 'linkedin') {
								$scope.profilePic = response.picture;
								userId = response.data.username
										+ "@linkedin.com";
							} else if (network == 'twitter') {
								$scope.profilePic = response.picture;
								userId = response.screen_name
										+ "@twitter.com";
							} else if (network == 'instagram') {
								userId = response.data.username
										+ "@instagram.com";
								$scope.profilePic = response.data.profile_picture;
							}

							$http
									.get(
											url
													+ "?emailAddress="
													+ userId)
									.success(
											function(
													userResponse) {
												if (userResponse != null
														&& userResponse == "") {
													$scope.user = {};
													if (network == 'google'
															|| network == 'facebook'
															|| network == 'twitter'
															|| network == 'linkedin') {
														$scope.user.firstName = response.first_name;
														$scope.user.lastName = response.last_name;
														$scope.user.validated = response.verified;
													} else if (network == 'instagram') {
														var fullName = response.data.full_name;
														$scope.user.fullName = fullName;
														var names = fullName
																.split(" ");
														if (names.length === 1) {
															$scope.user.firstName = names[0];
														} else if (names.length === 2) {
															$scope.user.firstName = names[0];
															$scope.user.lastName = names[1];
														}

													}
													$scope.user.user = userId;
													$scope.user.socialMedia = true;
													$scope.user.socialMediaType = network
															.toUpperCase();
													$http
															.post(
																	'../ws/rest/resourceService/saveOrUpdateUser/token/test',
																	$scope.user)
															.success(
																	function(
																			data) {
																		$http
																				.get(
																						url
																								+ "?emailAddress="
																								+ userId)
																				.success(
																						function(
																								userResponse) {
																							console
																									.log('Create User User '
																											+ JSON
																													.stringify(userResponse));

																							// Setting
																							// Mapping
																							// User
																							// to
																							// Normal
																							// User
																							$scope.loggedInUser = userResponse;

																							$scope.headeruser = '#headeruser';
																							$scope.profileDialog = '';
																							// $scope.profileDialog
																							// =
																							// 'signoff';
																							$scope.profileText = $scope.loggedInUser.firstName+ ' '+ $scope.loggedInUser.lastName;
																							$scope.userId = $scope.loggedInUser.user;
																							console.log('profileDialog is '+ $scope.profileDialog+ ' User Id '+ $scope.userId);
																							$window.localStorage.setItem("loggedInUser", JSON.stringify($scope.loggedInUser));
																						});
																		$(
																				"#modal")
																				.hide();
																		$(
																				"#inviteFriends")
																				.show();
																		if ($scope.page === 'write_a_review') {
																			$(
																					"#submitReview")
																					.show();
																		}
																		console
																				.log('Create User');

																	})
															.error(
																	function(
																			errorResponse) {
																		$scope.profilePic = "images/testimonials.png"
																		bootbox
																				.alert("Error Registering User From Social Media");
																	});
												} else {
													console
															.log('Create User User '
																	+ JSON
																			.stringify(userResponse));
													$(
															"inviteFriendsLogin")
															.hide();
													$("#modal")
															.hide();
													$(
															"#inviteFriends")
															.show();
													$(
															"#submitReview")
															.show();
													// Setting
													// Mapping
													// User to
													// Normal
													// User
													$scope.loggedInUser = userResponse;
													$scope.headeruser = '#headeruser';
													$scope.profileDialog = '';
													// $scope.profileDialog
													// =
													// 'signoff';
													$scope.userId = $scope.loggedInUser.user;
													$scope.profileText = $scope.loggedInUser.firstName
															+ ' '
															+ $scope.loggedInUser.lastName;
													console.log('profileDialog is '+ $scope.profileDialog+ ' User Id '+ $scope.userId);
													$window.localStorage.setItem("loggedInUser", JSON.stringify($scope.loggedInUser));
												}
											});
						});
		
		}
	
	
	<!---Start Discover stuff -->
	$scope.show_discover_steps = function(toShow){
	console.log('toShow is '+toShow);
		if(toShow){
			document.getElementById('discover_steps').style.display = "";
			document.getElementById('results').style.display = "none";
		}
		else{
			document.getElementById('discover_steps').style.display = "none";
			document.getElementById('results').style.display = "none";
		}
	}
	
	$scope.show_discover_steps(false);
	
	
	$scope.questions1 = {};
	$scope.questions2 = {};
	$scope.questions3 = {};
	$scope.questions4 = {};
	$scope.questions5 = {};
	$scope.resultsFromOnet = {};
	
	$scope.realisticScore = '';
	$scope.investigativeScore = '';
	$scope.artisticScore = '';
	$scope.socialScore = '';
	$scope.enterprisingScore = '';
	$scope.conventionalScore = '';
	
	// $scope.careersJobZone1 = {};
	// $scope.careersJobZone2 = {};
	// $scope.careersJobZone3 = {};
	// $scope.careersJobZone4 = {};
	// $scope.careersJobZone5 = {};
	// $scope.careersJobZone = {};
	$scope.jobPreparationText = 'Medium job preparation';
	
	$scope.careerDetails = {};
	
	$scope.alsoCalled = 'Also called: '; 
	$scope.steps = [];
	$scope.knowledgeElements = [];
	$scope.skillsElements = [];
	$scope.abilitiesElements = [];
	$scope.workStylesElements = [];
	
	$scope.technologies = [];
	
	
	var url = '../ws/rest/onetService/onet/questions/start/1/end/12/token/test';
	console.log(url);
	   $http.get(url).
	    success(function(data) {
		console.log('here ');
		   $scope.questions1 = data;
		    console.log($scope.questions1.length);
		    console.log($scope.questions1.question.length);
		    for(i=0; i<$scope.questions1.question.length; i++){
			// $scope.questions1.question[i].answer="3";
			}
		   
	    });
	    
	    url = '../ws/rest/onetService/onet/questions/start/13/end/24/token/test';
	console.log(url);
	   $http.get(url).
	    success(function(data) {
		console.log('here ');
		   $scope.questions2 = data;
		    console.log(' q 2 is '+$scope.questions2.question[0].text);
		     for(i=0; i<$scope.questions2.question.length; i++){
			// $scope.questions2.question[i].answer="3";
			}
		   
	    });
	    
	     url = '../ws/rest/onetService/onet/questions/start/25/end/36/token/test';
	console.log(url);
	   $http.get(url).
	    success(function(data) {
		console.log('here ');
		   $scope.questions3 = data;
		     for(i=0; i<$scope.questions3.question.length; i++){
			// $scope.questions3.question[i].answer="3";
			}
		   
	    });
	    
	     url = '../ws/rest/onetService/onet/questions/start/37/end/48/token/test';
	console.log(url);
	   $http.get(url).
	    success(function(data) {
		console.log('here ');
		   $scope.questions4 = data;
		     for(i=0; i<$scope.questions4.question.length; i++){
			// $scope.questions4.question[i].answer="3";
			}
		   
	    });
	    
	     url = '../ws/rest/onetService/onet/questions/start/49/end/60/token/test';
	console.log(url);
	   $http.get(url).
	    success(function(data) {
		console.log('here ');
		   $scope.questions5 = data;
		     for(i=0; i<$scope.questions5.question.length; i++){
			// $scope.questions5.question[i].answer="3";
			}
		   
	    });
	
	
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
				// do nothing
			}
		}); 
	}
	
	$scope.check = function(){
	var filled = true;
	
		for(i=0; i<$scope.questions1.question.length; i++){
		
			if($scope.questions1.question[i].answer == null){
			console.log($scope.questions1.question[i].index );
			console.log($scope.questions1.question[i].answer );
				filled = false;
				return filled;
			}
		}
		
		for(i=0; i<$scope.questions2.question.length; i++){
			if($scope.questions2.question[i].answer== null){
				filled = false;
				return filled;
			}
		}
		
		for(i=0; i<$scope.questions3.question.length; i++){
			if($scope.questions3.question[i].answer== null){
				filled = false;
				return filled;
			}
		}
		
		for(i=0; i<$scope.questions4.question.length; i++){
			if($scope.questions4.question[i].answer== null){
				filled = false;
				return filled;
			}
		}
		
		for(i=0; i<$scope.questions5.question.length; i++){
			if($scope.questions5.question[i].answer== null){
				filled = false;
				return filled;
			}
		}
		
		return filled;
	}
	
	
	$scope.submitAnswers = function(){
	var ans = '';
		for(i=0; i<$scope.questions1.question.length; i++){
			ans += $scope.questions1.question[i].answer;
		}
		
		for(i=0; i<$scope.questions2.question.length; i++){
			ans += $scope.questions2.question[i].answer;
		}
		
		for(i=0; i<$scope.questions3.question.length; i++){
			ans += $scope.questions3.question[i].answer;
		}
		
		for(i=0; i<$scope.questions4.question.length; i++){
			ans += $scope.questions4.question[i].answer;
		}
		
		for(i=0; i<$scope.questions5.question.length; i++){
			ans += $scope.questions5.question[i].answer;
		}
	
	 var url = '../ws/rest/onetService/onet/questions/answers/'+ans+'/token/test';
	 console.log('url is '+url);
		 $http.get(url).
	    success(function(data) {
		console.log('here ');
		  $scope.resultsFromOnet = data;
		  for(i=0;i<$scope.resultsFromOnet.result.length;i++){
		  console.log(' area is '+$scope.resultsFromOnet.result[i].area);
			if($scope.resultsFromOnet.result[i].area == 'Realistic'){
				$scope.realisticScore = $scope.resultsFromOnet.result[i].score;
			}
			else if($scope.resultsFromOnet.result[i].area == 'Investigative'){
				$scope.investigativeScore = $scope.resultsFromOnet.result[i].score;
			}
			else if($scope.resultsFromOnet.result[i].area == 'Artistic'){
				$scope.artisticScore = $scope.resultsFromOnet.result[i].score;
			}
			else if($scope.resultsFromOnet.result[i].area == 'Social'){
				$scope.socialScore = $scope.resultsFromOnet.result[i].score;
			}
			else if($scope.resultsFromOnet.result[i].area == 'Enterprising'){
				$scope.enterprisingScore = $scope.resultsFromOnet.result[i].score;
			}
			else if($scope.resultsFromOnet.result[i].area == 'Conventional'){
				$scope.conventionalScore = $scope.resultsFromOnet.result[i].score;
			}
		  
		
		  }
		  
				
		  
		    $('#step10').hide();
			$('#step11').show();
			loadBars();
		   
	    });
	}
	
	$scope.goToCareers = function(jobZone){
	$scope.showLoadingMessage = 'Data is being loaded from server. Please be patient........................';
	console.log('in goToCareers jobZone is '+jobZone);
		var ans = '';
		for(i=0; i<$scope.questions1.question.length; i++){
			ans += $scope.questions1.question[i].answer;
		}
		
		for(i=0; i<$scope.questions2.question.length; i++){
			ans += $scope.questions2.question[i].answer;
		}
		
		for(i=0; i<$scope.questions3.question.length; i++){
			ans += $scope.questions3.question[i].answer;
		}
		
		for(i=0; i<$scope.questions4.question.length; i++){
			ans += $scope.questions4.question[i].answer;
		}
		
		for(i=0; i<$scope.questions5.question.length; i++){
			ans += $scope.questions5.question[i].answer;
		}
		
	var url = '../ws/rest/onetService/onet/careers/answers/'+ans+'/jobZone/'+jobZone+'/start/1/end/1000/token/test';
	 console.log('url is '+url);
		$http.get(url).
	    success(function(data) {
		console.log('here ');
		$scope.showLoadingMessage = '';
		
		  $scope.careersJobZone = data;
			for(i=0; i<$scope.careersJobZone.career.length; i++){
			console.log(' t1 is '+$scope.careersJobZone.career[i].tags.brightOutlook);
		
			console.log($scope.careersJobZone.career[i].title);
				if($scope.careersJobZone.career[i].fit == 'Best'){
					$scope.careersJobZone.career[i].star = 'bluestar';
				}
				else{
					$scope.careersJobZone.career[i].star = 'pinkstar';
				}
				
				if($scope.careersJobZone.career[i].tags.brightOutlook == true){
					// $scope.careersJobZone.career[i].tags.showBright = '<i
					// class=\"fa fa-circle\" aria-hidden=\"true\"></i>';
					$scope.careersJobZone.career[i].tags.showBright = 'YES';
					console.log($scope.careersJobZone.career[i].tags.showBright );
				}
				else{
					$scope.careersJobZone.career[i].tags.showBright = '';
				}
				
				if($scope.careersJobZone.career[i].tags.green == true){
					// $scope.careersJobZone.career[i].tags.showGreen = '<i
					// class=\"fa fa-circle\" aria-hidden=\"true\"></i>';
					$scope.careersJobZone.career[i].tags.showGreen = 'YES';
				}
				else{
					$scope.careersJobZone.career[i].tags.showGreen = '';
				}
			}
			
		$('#step13').hide();
		$('#step15').show();
		loadBars();
		 
	    });
	
	}
	
	
	$scope.fetchCareersByJobZone = function(jobzone){
	console.log(' in fetchCareersByJobZone jobzone is '+jobzone); 
		if(jobzone == '1'){
			$scope.jobPreparationText = 'No job preparation';
		}
		else if(jobzone == '2'){
			$scope.jobPreparationText = 'Some job preparation';
		}
		else if(jobzone == '3'){
			$scope.jobPreparationText = 'Medium job preparation';
		}
		else if(jobzone == '4'){
			$scope.jobPreparationText = 'High job preparation';
		}
		else if(jobzone == '5'){
			$scope.jobPreparationText = 'Extensive job preparation';
		}
		
		$scope.goToCareers(jobzone);
	}
	
	
	$scope.fetchCareerDetails = function(careerCode){
	console.log(' in fetchCareerDetails careerCode is '+careerCode); 
	$scope.showLoadingMessage = 'Data is being loaded from server. Please be patient..................';
	
	var url = '../ws/rest/onetService/onet/occupationFullDetails/occupationCode/'+careerCode+'/token/test';
	
		$http.get(url).
	    success(function(data) {
		console.log('here ');
		$scope.showLoadingMessage = '';
		$scope.careerDetails = data;
			for(i=0 ; i<$scope.careerDetails.occupation.sampleOfReportedJobTitles.title.length; i++){
				if( i == ($scope.careerDetails.occupation.sampleOfReportedJobTitles.title.length - 1) ){
					$scope.alsoCalled += $scope.careerDetails.occupation.sampleOfReportedJobTitles.title[i];
				}
				else{
					$scope.alsoCalled += $scope.careerDetails.occupation.sampleOfReportedJobTitles.title[i] +", ";
				}
				
			}
			
			console.log(' also called '+$scope.alsoCalled);
			
			for(i=0;i<$scope.careerDetails.tasks.task.length; i++){
				$scope.steps.push($scope.careerDetails.tasks.task[i].statement);
			}
			console.log($scope.steps);
			
			
			
			
			for(i=0;i<$scope.careerDetails.knowledge.element.length; i++){
				$scope.knowledgeElements.push($scope.careerDetails.knowledge.element[i].name);
			}
			
			for(i=0;i<$scope.careerDetails.skills.element.length; i++){
				$scope.skillsElements.push($scope.careerDetails.skills.element[i].name);
			}
		
		
			for(i=0;i<$scope.careerDetails.abilities.element.length; i++){
					$scope.abilitiesElements.push($scope.careerDetails.abilities.element[i].name);
				}
			
			
		
			for(i=0;i<$scope.careerDetails.workStyles.element.length; i++){
				$scope.workStylesElements.push($scope.careerDetails.workStyles.element[i].name);
			}
			
			
			for(i=0;i<$scope.careerDetails.toolsTechnology.technology.category[0].example.length; i++){
				$scope.technologies.push($scope.careerDetails.toolsTechnology.technology.category[0].example[i]);
			}
			
		
		document.getElementById('results').style.display = "";
		bootbox.alert('Scroll down to see the results');
		 
	    });
	
	}
	
	$scope.disableResultsSection = function(){
		document.getElementById('results').style.display = "none";
	}
	
	$scope.endDiscover = function(){
		bootbox.confirm("Thank you for taking the Interest Profiler test. Click cancel if you want to continue!", function(result) {
		 console.log('result is '+result);
			if(result == true){
			console.log('true');
				document.getElementById('discover_steps').style.display = "none";
				document.getElementById('results').style.display = "none";
			}
			else{
				document.getElementById('results').style.display = "none";
			}
		}); 
	}
	
	$scope.redirectAndSearch = function() {
		window.localStorage.setItem("isRedirectedSearch", true);
		window.localStorage.setItem("redirectSearchKeyword",
				$scope.searchText);
		$window.location.href = 'prepare_for_greatness.html';
	}
});