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


app.controller( 'beJobReadyController',  function($scope, $http, $window, $filter, $sce, Feedback) {


	

	var redirectUrl = "http://www.grovenue.co/";
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
				//$scope.profileDialog = 'signoff';
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
	/******* Added By Sagar ********/
	
	
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
						'google' : '37695017955-fdprjmfel4mtmful1tfc1rpmtc955kpm.apps.googleusercontent.com'					}, {
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
									$http.post('../ws/rest/resourceService/saveOrUpdateUser/token/test', $scope.user).success

										(function(data)
										{
											$http.get(url + "?emailAddress=" + userId).success(function(userResponse)
												{
													console.log('Create User User ' + JSON.stringify

															(userResponse.data));
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
	
<!-- Begin Be job stuff.... -->
$scope.serviceCosting = {"templateUseOnly":false, "expressService": false, "superExpressService":false, "eachAdditionIteration":false, "coverLetter":false };
$scope.orderType = '';
console.log('!!!!!!!!!!!!!!!!!!!!!! order type begin rr'+$scope.orderType);
console.log('!!!!!!!!!!!!!!!!!!!!!! service costing begin rr'+$scope.serviceCosting);
var ordermodeServiceCosting = $window.localStorage.getItem('orderModeServiceCosting');
console.log(' ordermodeServiceCosting '+ordermodeServiceCosting);

			if(ordermodeServiceCosting == 'null'){
				ordermodeServiceCosting = null;
			}
			if(ordermodeServiceCosting != null){
				$scope.serviceCosting = jQuery.parseJSON(ordermodeServiceCosting);
				$scope.orderType = $window.localStorage.getItem('orderModeOrderType');
				$scope.orderType = window.encodeURIComponent($scope.orderType);
				//$scope.$apply();

				bootbox.confirm("You had chosen some services to buy. Dow you wish to proceed to pay. If yes click the Pay button?", function(result) 	

				{
					 console.log('result is '+result);
						if(result == true){
							
							document.getElementById('payButton').style.display = "";
							//window.scrollTo(0, document.height);
							//window.location.hash = '#payButton';
							var body = document.body,
						    html = document.documentElement;

							var height = Math.max( body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight );
							window.scrollTo(0, height);
							console.log('document.body.scrollHeight '+document.body.scrollHeight);
							console.log('height '+height);
							console.log(' order type begin '+$scope.orderType);
							document.getElementById('buildYourCV_zerotoonemodal').style.display = "none";
							document.getElementById('carte_options').style.display = "";
						}
						else{
						$scope.serviceCosting = {"templateUseOnly":false, "expressService": false, "superExpressService":false, "eachAdditionIteration":false, "coverLetter":false };
							$scope.orderType = '';
							$window.localStorage.setItem('orderModeServiceCosting', null)
							$window.localStorage.setItem('orderModeOrderType', null);
						}
					}); 

				
				
			}
			else{
				$scope.serviceCosting = {"templateUseOnly":false, "expressService": false, "superExpressService":false, "eachAdditionIteration":false, "coverLetter":false };
			$scope.orderType = '';
			}


	
	
	
	
	$scope.displayAlaCarteForCV = function(package, serviceType, price){
		$scope.orderType = '';
		$scope.orderType += 'serviceType='+$scope.serviceCosting.serviceType+'&package='+$scope.serviceCosting.package+'&';
		$scope.serviceCosting.serviceType=serviceType;
		$scope.serviceCosting.package=package;
		$scope.serviceCosting.price = price;
		//document.getElementById('buildYourCV_zerotoonemodal').style.display = "none";
		document.getElementById('coverletter').style.display = "none";
		
			document.getElementById('carte_options').style.display = "";
	}
	
	$scope.buyInterviewServices = function(){
	
	document.getElementById('zerotoonemodalinterview').style.display = "none";
	document.getElementById('onetothreemodalinterview').style.display = "none";
		bootbox.alert("Right now you can only buy CV and Cover Services");	
	}
	
	$scope.displayAlaCarteForCOVER = function(package, serviceType, price){
		$scope.orderType = '';
		$scope.orderType += 'serviceType='+$scope.serviceCosting.serviceType+'&package='+$scope.serviceCosting.package+'&';
		$scope.serviceCosting.serviceType=serviceType;
		$scope.serviceCosting.package=package;
		$scope.serviceCosting.price = price;
		
		document.getElementById('coverletter').style.display = "none";
		document.getElementById('carte_options').style.display = "";
		bootbox.alert("Right now You can purchase Cover Services by purchasing the CV Services with Cover Option selected");
	}
	
	$scope.confirmCosting = function(){
		$scope.orderType = '';
		$scope.orderType += 'sT='+$scope.serviceCosting.serviceType+'#pkg='+$scope.serviceCosting.package+'#';
		var totalPrice = $scope.serviceCosting.price ;
		console.log($scope.serviceCosting.templateUseOnly);
		console.log($scope.serviceCosting.expressService);
		console.log($scope.serviceCosting.superExpressService);
		console.log($scope.serviceCosting.eachAdditionIteration);
		console.log($scope.serviceCosting.coverLetter);
		
			if($scope.serviceCosting.templateUseOnly){
				totalPrice += 500;
				$scope.serviceCosting.templateUseOnlyAmount = 500;
			}
			else{
				$scope.serviceCosting.templateUseOnlyAmount = 0;
			}
			
			
			if($scope.serviceCosting.expressService){
				totalPrice += 300;
				$scope.serviceCosting.expressServiceAmount = 300;
			}
			else{
				$scope.serviceCosting.expressServiceAmount = 0;
			}
			
			if($scope.serviceCosting.superExpressService){
				totalPrice += 750;
				$scope.serviceCosting.superExpressServiceAmount = 750;
			}
			else{
				$scope.serviceCosting.superExpressServiceAmount = 0;
			}
			
			if($scope.serviceCosting.eachAdditionIteration){
				totalPrice += 1000;
				$scope.serviceCosting.eachAdditionIterationAmount = 1000;
			}
			else{
				$scope.serviceCosting.eachAdditionIterationAmount = 0;
			}
			
			if($scope.serviceCosting.coverLetter){
				totalPrice += 1000;
				$scope.serviceCosting.coverLetterAmount = 1000;
			}
			else{
				$scope.serviceCosting.coverLetterAmount = 0;
			}

			$scope.serviceCosting.totalPrice = totalPrice;	
			
			var details = 'tUO='+$scope.serviceCosting.templateUseOnly+'#';
			details += 'bP='+$scope.serviceCosting.price+'#';
			details += 'tPrice='+$scope.serviceCosting.totalPrice+'#';
			details += 'eS='+$scope.serviceCosting.expressService+'#';
			details += 'sES='+$scope.serviceCosting.superExpressService+'#';
			details += 'eAI='+$scope.serviceCosting.eachAdditionIteration+'#';
			details += 'cL='+$scope.serviceCosting.coverLetter+'#';
				
				if($scope.serviceCosting.templateUseOnly){
					details += 'tuAmount='+$scope.serviceCosting.templateUseOnlyAmount+'#';
				}
				
				if($scope.serviceCosting.expressService){
					details += 'eSAmt='+$scope.serviceCosting.expressServiceAmount+'#';
				}
				
				if($scope.serviceCosting.superExpressService){
					details += 'sESAmt='+$scope.serviceCosting.superExpressServiceAmount+'#';
				}
				
				if($scope.serviceCosting.eachAdditionIteration){
					details += 'eAIAmt='+$scope.serviceCosting.eachAdditionIterationAmount+'#';
				}
				
				if($scope.serviceCosting.coverLetter){
					details += 'covAmt='+$scope.serviceCosting.coverLetterAmount+'#';
				}
				
			$scope.orderType += details;
			$scope.orderType = window.encodeURIComponent($scope.orderType);
			console.log(' order type is '+$scope.orderType);
			
			var tableHtml = "<table id='confirmTable' class='table' <thead> <tr> <th>Price Header</th> <th>Cost(INR)</th></thead>"+
			"<tbody><tr><td>"+$scope.serviceCosting.serviceType+"-"+$scope.serviceCosting.package+"</td><td>"+$scope.serviceCosting.price +"</td></tr>"+
			"<tr><td>Template Use Only</td><td>"+$scope.serviceCosting.templateUseOnlyAmount+"</td></tr>"+
			"<tr><td>Express Service</td><td>"+$scope.serviceCosting.expressServiceAmount+"</td></tr>"+
			"<tr><td>Super Express Service</td><td>"+$scope.serviceCosting.superExpressServiceAmount+"</td></tr>"+
			"<tr><td>Each Additional Iteration</td><td>"+$scope.serviceCosting.eachAdditionIterationAmount+"</td></tr>"+
			"<tr><td>Cover Letter</td><td>"+$scope.serviceCosting.coverLetterAmount+"</td></tr>"+
			"<tr><td>Total Price</td><td>"+$scope.serviceCosting.totalPrice+"</td></tr></table>";
			
			//bootbox.confirm("Total Price is "+totalPrice+". To proceed to pay, click on OK or else click Cancel", function(result) {
			bootbox.confirm("Here are the order details - <br>"+tableHtml+"<br>. To confirm click OK or else click Cancel", function(result) {
		 console.log('result is '+result);
			if(result == true){
				console.log('true');
				
				document.getElementById('payButton').style.display = "";
				$window.localStorage.setItem("orderModeServiceCosting", JSON.stringify($scope.serviceCosting));
				$window.localStorage.setItem("orderModeOrderType", $scope.orderType);
				window.location.reload();
			}
			else{
				//do nothing
			}
		}); 
			
			
	}
	
	
	
	
});
        	
		     