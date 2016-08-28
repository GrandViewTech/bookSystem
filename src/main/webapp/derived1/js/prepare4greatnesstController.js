/**
 * @author puneetsharma
 * @decription : prepare4greatnesstController
 * @scope.searchType : anonymous for without loggedIn and user when user is
 *                   logged in
 */
function generateToken()
	{
		var d = new Date().getTime();
		var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c)
			{
				var r = (d + Math.random() * 16) % 16 | 0;
				d = Math.floor(d / 16);
				return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
			});
		return uuid;
	};




var searchEntity = 'coachingClass';

var register = angular.module('myApp', ['FeedbackService', 'UserService', 'ngImgCrop']);
register.directive('myPostRepeatDirective', function()
	{
		return function(scope, element, attrs)
			{
				if (scope.$last)
					{
						initializeSlider();
					}
			};
	});
register.controller("prepare4greatnesstController", function($scope, $http, $window, $filter, $sce, Feedback)
	{
		
		var baseUrl="..";
		baseUrl="http://localhost:8080/bookSystem";
		$scope.searchEntity = 'book';
		$scope.searchkeyword=null ;
		$scope.userId ="anonymous";
		$scope.location ="location";
		$scope.resourceEntities = [];		
		$scope.resource = {};
		$scope.statesAndCities = {};
		$scope.cities = [];
		$scope.states = [];		
		$scope.slider=[];
		$scope.rating=2;
		$scope.identity1="Effectiveness";
		$scope.identity2="Visual Aides";
		$scope.identity3="Solutions to practical problems";
		$scope.identity4="Real-world examples";
		$scope.review={};
		$scope.review.effectivenessAndEaseOfCommunication=1;
		$scope.review.solutionToPracticeProblems=1;
		$scope.review.visualTools=1;
		$scope.review.solvedExamples=1;
		$scope.isUserLoggedIn=false;
		$scope.selectedOrderBy='rating';
		$scope.ratingScope={}
		$scope.ratingScope.identity1="";
		$scope.ratingScope.identity2="";
		$scope.ratingScope.identity3="";
		$scope.ratingScope.identity4="";
		$scope.page="";
		$scope.showBook=true;
		$scope.showCoachingClass=false;
		$scope.showDigitalResource=false;
		$scope.submitted = "";
		//#scope.sort=[{"key" : ""},{}];
		
		//var slider=	 jQuery('.slider4').lbSlider({leftBtn: '.sa-left4',rightBtn: '.sa-right4',visible: 4,autoPlay: true,autoPlayDelay: 5});
		$scope.applyOrderBy=function (argument) 
		{
			//alert("findByDistinctStateAndCityForGivenCountry");
			//$scope.apply();
		}
		
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

		$scope.updateCity = function()
			{
				var cityCounter = 0;
				$scope.cities = [];
				for ( var j in $scope.statesAndCities[$scope.selectedState])
					{
						$scope.cities.push($scope.statesAndCities[$scope.selectedState][j]);
						if (cityCounter == 0)
							{
								$scope.selectedCity = $scope.statesAndCities[$scope.selectedState][j];
								cityCounter++;
							}
					}
			}
		$scope.getstates = function()
			{
				var statesUrl = "../ws/rest/resourceService/findByDistinctStateAndCityForGivenCountry/token/" + generateToken() + "?country=India";
				$http.get(statesUrl).success(function(stateCitiesResponse)
					{
						//console.log("stateCitiesResponse : " + JSON.stringify(stateCitiesResponse))
						$scope.statesAndCities = stateCitiesResponse;
						var stateCounter = 0;
						for ( var i in $scope.statesAndCities)
							{
								$scope.states.push(i);
								//console.log("states : " + JSON.stringify($scope.states))
								if (stateCounter == 0)
									{
										$scope.selectedState = i;
										stateCounter++;
									}
							}
						var cityCounter = 0;
						for ( var j in $scope.statesAndCities[$scope.selectedState])
							{
								$scope.cities.push($scope.statesAndCities[$scope.selectedState][j]);
								if (cityCounter == 0)
									{
										$scope.selectedCity = $scope.statesAndCities[$scope.selectedState][j];
										cityCounter++;
									}

							}
						console.log("cities : " + JSON.stringify($scope.cities))

					}).error(function(errorResponse)
					{
						console.log(JSON.stringify(errorResponse));
						//alert("Error While Searching the Result for States " + JSON.stringify(errorResponse));
					});
			};

			

		$scope.search = function(isReload)
			{
				var sortParam='name';
				if ($scope.userId === "anonymous" )
					{
						$scope.userId = "anonymous";
						$scope.isUserLoggedIn=false;
					}
					else
					{
						$window.localStorage.setItem("userId", $scope.userId);
						$scope.isUserLoggedIn=true;	
					}
				$scope.searchType = ($scope.isUserLoggedIn === true) ? "user" : "generic";
				var applyFilter=false;
				var resourceLimit=10;
				if($scope.page==='write_a_review')
					{
						resourceLimit=24;
					}
					else if($scope.page==='prepare_for_greatness')
					{
						resourceLimit=10;
					}
					//alert($scope.page +" "+resourceLimit);
				var searchUrl = "../ws/rest/resourceService/getResourceEntityBasedByUponSearchCriteria/token/" + generateToken() + "?searchType=" + $scope.searchType + "&userId=" + $scope.userId + "&keyword=None&searchEntity=" + $scope.searchEntity+"&location="+$scope.location+"&resourceLimit="+resourceLimit ;
				console.log("searchUrl " + searchUrl);
				//alert('url ' +searchUrl);
				$http.get(searchUrl).success(function(resourceEntities)
					{
							$scope.resourceEntities = resourceEntities;
							var i=0;
							$scope.slider=[];
							$scope.resourceEntities = resourceEntities;
							if ($scope.resourceEntities.length > 0)
							{
								$scope.resource = $scope.resourceEntities[0];
							}
							if(isReload===true)
							{	
								$scope.$apply();
								jQuery('.slider4').lbSlider({  leftBtn: '.sa-left4', rightBtn: '.sa-right4',visible: 4,autoPlay: true,autoPlayDelay: 5});
							}
							console.log(JSON.stringify($scope.resourceEntities));
							
							
					}).error(function(errorResponse)
					{
						console.log(JSON.stringify(errorResponse));
						//alert("Error While Searching the Result for Books");
					});

			};

		$scope.latitude=0.0;
		$scope.longitude=0.0	;
		$scope.nearMe=function()
		{
 			if (navigator.geolocation) 
 			 	{
        			navigator.geolocation.getCurrentPosition(function (position) 
        			{
						$scope.latitude= position.coords.latitude; 
                		$scope.longitude = position.coords.longitude;
                	});
                }
		}
		$scope.init = function(page,isReload)
			{
				$scope.nearMe();			
				$("#classCaret").hide();
				$("#bookCaret").show();
				$("#digitalCaret").hide();
				$("#classCaretDown").hide();
				$("#bookCaretDown").show();
				$("#digitalCaretDown").hide();
				$scope.page=page;
				var user = $window.localStorage.getItem('loggedInUser');
				if(user==='null' || user === "null")
				{
					user=null;
				}
				$scope.loggedInUser = '';
				$scope.profileText = 'login / register';
				$scope.profileDialog = 'modal';
				if(user === null)
				{
					$scope.userId = "anonymous";
					console.log('disable profile div');
					$scope.headeruser = '';
					if($scope.page==='write_a_review')
					{
						$("#submitReview").hide();
					}
				}
				else
				{
					user = jQuery.parseJSON(user);
					if (user.userType == 'ADMIN') 
					{
						console.log('not logged in as Admin');
						$window.location.href = 'AdminShortCuts.html';
					} 
					else 
					{
						$scope.loggedInUser = user;
						$scope.headeruser = '#headeruser';
						$scope.profileDialog = '';
						$scope.profileText = user.firstName + ' '+ user.lastName;
						console.log('profileDialog is '+ $scope.profileDialog);
						if($scope.page==='write_a_review')
						{
							$("#submitReview").show();
						}
					}
				}
				
				$scope.getstates();
				$scope.search(isReload);
			};
		
		$scope.searchBar=function()
		{
				//alert('$scope.searchkeyword '+$scope.searchkeyword);
				var sortParam='name';
				if ($scope.userId === "anonymous" )
					{
						$scope.userId = "anonymous";
						$scope.isUserLoggedIn=false;
					}
					else
					{
						$window.localStorage.setItem("userId", $scope.userId);
						$scope.isUserLoggedIn=true;	
					}
					var resourceLimit=10;
					if($scope.page==='write_a_review')
					{
						resourceLimit=20;
					}
					else if($scope.page==='prepare_for_greatness')
					{
						resourceLimit=10;
					}
				var keyword=($scope.searchkeyword === null || $scope.searchkeyword === "null" || $scope.searchkeyword === 'null' ) ? "None" : $scope.searchkeyword ;
				$scope.searchType = ($scope.isUserLoggedIn === true) ? "user" : "generic";
				var applyFilter=true;
				var searchUrl = "../ws/rest/resourceService/getResourceEntityBasedByUponSearchCriteria/token/" + generateToken() + "?searchType=" + $scope.searchType + "&userId=" + $scope.userId + "&keyword=" + keyword + "&searchEntity=" + $scope.searchEntity+"&location="+$scope.location+"&resourceLimit="+resourceLimit ;
				console.log("searchUrl " + searchUrl);
				//alert('url' +searchUrl);
				$http.get(searchUrl).success(function(resourceEntities)
					{
							$scope.resourceEntities = resourceEntities;
							if ($scope.resourceEntities.length > 0)
							{
								$scope.resource = $scope.resourceEntities[0];
							}
							console.log(JSON.stringify($scope.resourceEntities));
							
					}).error(function(errorResponse)
					{
						console.log(JSON.stringify(errorResponse));
						//alert("Error While Searching the Result for Books");
					});
		};
		

		$scope.updateSelectedInstance = function(resource)
			{
				//alert(JSON.stringify($scope.resource));
				$scope.resource = resource;
				//console.log("update updateSelectedInstance : " + JSON.stringify($scope.resource))
			};

		$scope.changeActive = function(entityName)
			{
				console.log("entityName " + entityName)
				if (entityName === 'coachingClass')
					{
						$scope.identity1="Faculty";
						$scope.identity2="Study Materials";
						$scope.identity3="Personalization";
						$scope.identity4="Infrastructure";
						$scope.searchEntity = 'coachingClass';
						$scope.showBook=false;
						$scope.showCoachingClass=true;
						$scope.showDigitalResource=false;
						$scope.review.resourceReviewedType="COACHING_CLASS";
						$("#selectbook").removeClass("active");
						$("#selectDigitalResource").removeClass("active");
						$("#selectCoachingClass").addClass("active");
						//$scope.searchCoachingClassBasedUponLocation();
						$("#coachingclasses").show();
						$("#classCaret").show();
						$("#bookCaret").hide();
						$("#digitalCaret").hide();
						$("#classCaretDown").show();
						$("#bookCaretDown").hide();
						$("#digitalCaretDown").hide();
						

					}
				else if (entityName === 'digitalResource')
					{
						$scope.review.resourceReviewedType="DIGITAL_RESOURCE";
						$scope.identity1="Personalization";
						$scope.identity2="Interactivity";
						$scope.identity3="Ease of Use";
						$scope.identity4="Study Materials";
						$scope.searchEntity = 'digitalResource';
						$scope.showBook=false;
						$scope.showCoachingClass=false;
						$scope.showDigitalResource=true;
						$("#selectbook").removeClass("active");
						$("#selectDigitalResource").addClass("active");
						$("#selectCoachingClass").removeClass("active");
						$scope.searchBar();
						$("#classCaret").hide();
						$("#bookCaret").hide();
						$("#digitalCaret").show();
						$("#classCaretDown").hide();
						$("#bookCaretDown").hide();
						$("#digitalCaretDown").show();
						
					}
				else if (entityName === 'book')
					{
						$scope.review.resourceReviewedType="BOOK";
						$scope.identity1="Effectiveness";
						$scope.identity2="Visual Aides";
						$scope.identity3="Solutions to practical problems";
						$scope.identity4="Real-world examples";
						$scope.searchEntity = 'book';
						$scope.showBook=true;
						$scope.showCoachingClass=false;
						$scope.showDigitalResource=false;
						$("#selectbook").addClass("active");
						$("#selectDigitalResource").removeClass("active");
						$("#selectCoachingClass").removeClass("active");
						$scope.searchBar();
						$("#classCaret").hide();
						$("#bookCaret").show();
						$("#digitalCaret").hide();
						$("#classCaretDown").hide();
						$("#bookCaretDown").show();
						$("#digitalCaretDown").hide();
					}
			};

			$scope.updateIdentity1=function(value)
			{
				var entityName=$scope.searchEntity;
				$scope.ratingScope.identity1=value;
				if (entityName === 'coachingClass')
					{
						$scope.review.faculty=value;
					}
					else if (entityName === 'digitalResource')
					{
						$scope.review.personalization=value;
					}
					else if (entityName === 'book')
					{
						$scope.review.effectivenessAndEaseOfCommunication=value;
					}
				
			};
			$scope.updateIdentity2=function(value)
			{
				var entityName=$scope.searchEntity;
				$scope.ratingScope.identity2=value;
				if (entityName === 'coachingClass')
					{
						 $scope.review.studyMaterial=value;
					}
					else if (entityName === 'digitalResource')
					{
						$scope.review.interactivity=value;
					}
					else if (entityName === 'book')
					{
						$scope.review.visualTools=value;
					}
				
			};
			$scope.updateIdentity3=function(value)
			{
				var entityName=$scope.searchEntity;
				$scope.ratingScope.identity3=value;
				if (entityName === 'coachingClass')
					{
						$scope.review.personalization=value;
					}
					else if (entityName === 'digitalResource')
					{
						$scope.review.easyOfUse=value;
					}
					else if (entityName === 'book')
					{
						$scope.review.solutionToPracticeProblems=value;

					}
			
				
			};
			$scope.updateIdentity4=function(value)
			{
				var entityName=$scope.searchEntity;
				$scope.ratingScope.identity4=value;
				if (entityName === 'coachingClass')
					{
						$scope.review.infrastructure=value;
					}
					else if (entityName === 'digitalResource')
					{	
						$scope.review.studyMaterial=value;
					}
					else if (entityName === 'book')
					{
						$scope.review.solvedExamples=value;
					}
				
			};

			$scope.writeAReview=function()
			{
				//$scope.review.resourceTitle=$scope.resource.title;
				$scope.review.reviewedBy=$scope.userId;
				$scope.review.resourceIdentity=$scope.resource.identity;
				if($scope.review.reviewedBy===null || $scope.review.reviewedBy==="")
				{
					$scope.review.reviewedBy='anonymous';
				}
				if($scope.searchEntity==='coachingClass')
				{
					$scope.review.location= $scope.resource.branch;
					$scope.review.resourceIdentity=$scope.resource.name;
				}
				else
				{
					$scope.review.location= "anonymous";
				}
				
				var reviewUrl="../ws/rest/reviewRelatedService/saveOrUpdateReview/token/"+generateToken();
				//alert(JSON.stringify($scope.review));
				$http.post(reviewUrl, $scope.review).success(function(data)
					{
						bootbox.alert("Review Submited successfully");
						$scope.review={};
						if($scope.searchEntity==='coachingClass')
						{
							$scope.searchCoachingClassBasedUponLocation();
						}
						else
						{
							$scope.searchBar();
						}

					}).error(function(errorResponse)
					{
						console.log(JSON.stringify(errorResponse));
						bootbox.alert(" Unable to Submit Review " + JSON.stringify(errorResponse));
					});
				
			};

			$scope.closeSelectStateCityOption=function()
			{
				$("#coachingclasses").hide();
			}

			$scope.searchCoachingClassBasedUponLocation=function()
			{
				$("#coachingclasses").hide();
				$scope.location=$scope.selectedCity;
				$scope.searchBar();
			};


			// LOGIN SECTION
			$scope.profilePic = "images/testimonials.png"
			var user = $window.localStorage.getItem('loggedInUser');
			$scope.loggedInUser = '';
			$scope.profileText = 'login / register';
			$scope.profileDialog = 'modal';
			$scope.login = function(user)
			{
				var usr = window.encodeURIComponent(user.user);
				var password = window.encodeURIComponent(user.password);
				var url = '../ws/rest/resourceService/authenticate/user/' + usr + '/password/' + password + '/socialMediaType/NONE';
				$http.post(url, null).success(function(data)
					{
						$scope.headeruser = '#headeruser';
						console.log('request successful');
						var u = data;
						console.log(' user got is ' + u);
						$scope.profileDialog = '';
						$scope.profileText = data.firstName + ' ' + data.lastName;
						$scope.loggedInUser =data;						
						var base64Image=$scope.loggedInUser.base64Image
						$scope.defImage =(base64Image===null || base64Image==='null' || base64Image==="null")? $scope.profilePic:base64Image;
						$scope.editOption  = ($scope.loggedInUser.socialMediaType==="NONE") ? true : false;
						$scope.closeRegDialog();
						console.log('data... is ' + data);
						$window.localStorage.setItem("loggedInUser", JSON.stringify(data));
						console.log('user type is' + user.userType);
						//$scope.$apply()
						if($scope.page==='write_a_review')
						{	
							$("#submitReview").show();
						}
						if (u.userType == 'ADMIN')
							{
								$window.location.href = 'AdminShortCuts.html';
							}
					}).error(function(data, status, headers, config)
					{

						bootbox.alert("Your Login attempt failed! can you re-check your credentials and try again");
					});
			}



			$scope.closeRegDialog = function()
			{
				console.log(' inside closeRegDialog');
				document.getElementById('modal').style.display = "none";
			}

			$scope.signOff = function() {
						console.log('in signoff method');
						document.getElementById('headeruser').style.display = "none";
						bootbox.confirm("Are you sure you want to sign off!",function(result) 
						{	
							console.log('result is ' + result);
							if (result === true) 
							{
								console.log('true');
								$scope.headeruser = '';
								$scope.profileText = 'login / register';
								$scope.profileDialog = 'modal';
								$window.localStorage.setItem("loggedInUser", null);
								$scope.$apply();
								if($scope.page==='write_a_review')
								{									
									$("#submitReview").hide();
								}
							} 
						});
					}

		$scope.checkPasswordValidations = function()
			{
				if ($scope.loggedInUser.newPassword.length < 5)
					{
						bootbox.alert("New Password should have atleast 5 characters");
					}
				else if ($scope.loggedInUser.newPassword != $scope.repeatPassword)
					{
						bootbox.alert("New and Repeat Passwords are not same ");
					}
				else
					{
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

		$scope.showEditProfile = function()
			{
				document.getElementById('tab').style.display = "";
			}

		$scope.showInviteFriends = function()
			{
				document.getElementById('headeruser').style.display = "none";
				$("#inviteFriendsLogin").show();
				// document.getElementById('headeruser').style.display = "none";
			}
		$scope.socialLoginForGetFriends = function(network)
			{
				$scope.socialLogin(network);
				$("#inviteFriendsLogin").hide();
				$scope.getfriends();
			}

		$scope.hideInviteFriends = function()
			{
				document.getElementById('inviteFriendsLogin').style.display = "none";
			}

		$scope.updateUserProfile = function()
			{
				console.log('first name is ' + $scope.loggedInUser.firstName);
				console.log('birth date is ' + $scope.loggedInUser.birthDate);
				console.log(JSON.stringify($scope.loggedInUser));
				$http.post('../ws/rest/resourceService/saveOrUpdateUser/token/test', $scope.loggedInUser).success(function(data)
					{
						console.log('user profile updated');
						$window.localStorage.setItem("loggedInUser", JSON.stringify($scope.loggedInUser));

					});

			}

			/******* Added By Sagar ********/
			$scope.myImage='';
			//var usr = $.extend({},$scope.loggedInUser);
			//var base64Image=usr.base64Image

			//$scope.defImage =(base64Image===null || base64Image==='null' || base64Image==="null")? $scope.profilePic:base64Image;
			$scope.myCroppedImage = '';
			$scope.showImagePreview = false;
			$scope.photoStatus = "Edit Photo";
			$scope.editOption=false;
			//$scope.editOption  = angular.isDefined(usr.socialMediaType);

			$scope.editPhotoBegin = function(){ 
				if($scope.showImagePreview)
				{
					$scope.photoStatus = "Edit photo";
				}
				else
				{
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

			$scope.hideTab = function()
			{
				document.getElementById('tab').style.display = "none";
			}

		// Social Login Related Code
		// @author sharmapuneet1510@gmail.com
		var userId = "";
		var client = "";
		var network = "";
		var socialMedialogin = false;
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
						// $("#loading").show();
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
												// $scope.$apply();
												$("#loading").hide();
												$("#modal").hide();
												$("#invitefriendsmodal").show();
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
							redirect_uri : 'index.html',
							scope : [ 'basic', 'email', 'friends' ]
						});
					}
				else if (network == 'facebook')
					{
						hello.init({
							'facebook' : '617188258436797'
						}, {
							redirect_uri : 'index.html'
						});
					}
				else if (network == 'linkedin')
					{
						hello.init({
							'linkedin' : '75aycy8klwf70r'
						}, {
							redirect_uri : 'index.html',
							scope : [ 'friends', 'email' ]
						});
					}
				else if (network == 'twitter')
					{
						hello.init({
							'twitter' : 'NftcrZDlBGbPmhIX5vPMKSyPb'
						}, {
							redirect_uri : 'index.html'
						});
					}
				else if (network == 'instagram')
					{
						hello.init({
							'instagram' : '517d3cc4879f4e7385491b4352abcdc5'
						}, {
							redirect_uri : 'index.html'
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
						var url = '../ws/rest/resourceService/getUserByEmailAddressAndSocialMediaType/socialMediaType/' + network + '/token/'+generateToken();
						if (network == 'google')
							{
								// userId=response.email;
								$scope.profilePic = response.picture;
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
								$scope.profilePic = response.picture;
								userId = response.email;
							}
						else if (network == 'linkedin')
							{
								$scope.profilePic = response.picture;
								userId = response.data.username + "@linkedin.com";
							}
						else if (network == 'twitter')
							{
								$scope.profilePic = response.picture;
								userId = response.screen_name + "@twitter.com";
							}
						else if (network == 'instagram')
							{
								userId = response.data.username + "@instagram.com";
								$scope.profilePic = response.data.profile_picture;
							}

						$http.get(url + "?emailAddress=" + userId).success(function(userResponse)
							{
								if (userResponse != null && userResponse == "")
									{
										$scope.user={};
										if (network == 'google' || network == 'facebook' || network == 'twitter' || network == 'linkedin')
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
												else if (names.length === 2)
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
														console.log('Create User User ' + JSON.stringify(userResponse));

														// Setting Mapping User
														// to Normal User
														$scope.loggedInUser = userResponse;

														$scope.headeruser = '#headeruser';
														$scope.profileDialog = '';
														// $scope.profileDialog
														// = 'signoff';
														$scope.profileText = $scope.loggedInUser.firstName + ' ' + $scope.loggedInUser.lastName;
														$scope.userId = $scope.loggedInUser.user;
														console.log('profileDialog is ' + $scope.profileDialog + ' User Id ' + $scope.userId);
													});
												$("#modal").hide();
												$("#inviteFriends").show();
												if($scope.page==='write_a_review')
												{
													$("#submitReview").show();
												}
												console.log('Create User');

											}).error(function(errorResponse)
											{
												$scope.profilePic = "images/testimonials.png"
												bootbox.alert("Error Registering User From Social Media");
											});
									}
								else
									{
										console.log('Create User User ' + JSON.stringify(userResponse));
										$("inviteFriendsLogin").hide();
										$("#modal").hide();
										$("#inviteFriends").show();
										$("#submitReview").show();
										// Setting Mapping User to Normal User
										$scope.loggedInUser = userResponse;
										$scope.headeruser = '#headeruser';
										$scope.profileDialog = '';										
										// $scope.profileDialog = 'signoff';
										$scope.userId = $scope.loggedInUser.user;
										$scope.profileText = $scope.loggedInUser.firstName + ' ' + $scope.loggedInUser.lastName;
										console.log('profileDialog is ' + $scope.profileDialog + ' User Id ' + $scope.userId);
									}
							});
					});
			}
			
			$scope.signup = function(user) {
						console.log(user.email);

						var url = '../ws/rest/resourceService/saveOrUpdateUser/token/'+generateToken();
						$http
								.post(url, user)
								.success(
										function(data) {
											console.log('request successful');

											var serviceResponse = data;
											console
													.log(JSON
															.stringify(serviceResponse));
											if (serviceResponse.responseStatus == 'User_Saved') {
												console.log('user saved');
												$scope.loggedInUser = user.email;
												// alert("Congratulations. You
												// are registered. Please check
												// your email for the activation
												// link");
												bootbox
														.alert("Congratulations. You are registered. Please check your email for the activation link");
												$scope.closeRegDialog();
											} else if (serviceResponse.responseStatus
													.startsWith("User_Exists_Social_Media_")) {
												var socialMedia = serviceResponse.responseStatus
														.substring(
																"User_Exists_Social_Media_"
																		.length(),
																serviceResponse.responseStatus.length);
												// $("<div title='Already logged
												// in through '"+socialMedia+"'>
												// Please use another email id
												// for
												// registration</div>").dialog();
												// Feedback.showDialog('Already
												// logged in through
												// '+socialMedia, 'Please use
												// another email id for
												// registration');
												// alert('You had logged in
												// earlier through
												// '+socialMedia+'. You can
												// continue use '+socialMedia +'
												// to log in or else register
												// using a different
												// password.');
												bootbox
														.alert('You had logged in earlier through '
																+ socialMedia
																+ '. You can continue use  '
																+ socialMedia
																+ ' to log in or else register using a different password.');
												$scope.closeRegDialog();
											} else if (serviceResponse.responseStatus == 'User_Exists_Normal') {
												// $("<div title='Basic
												// dialog'>You are already
												// registered. Just Log
												// in</div>").dialog();
												// Feedback.showDialog('Basic
												// dialog', 'You are already
												// registered. Just Log in');

												// alert('You are already
												// registered. Just Log in using
												// your email Id. In case you
												// have forgotten your password,
												// use the Forget Password
												// link');
												bootbox
														.alert('You are already registered. Just Log in using your email Id. In case you have forgotten your password, use the Forget Password link');
												$scope.closeRegDialog();
											}
										});

						return $scope.submitted;
					}

	});