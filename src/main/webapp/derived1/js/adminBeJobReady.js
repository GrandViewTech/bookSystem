var app = angular.module('myApp', []);
app.controller(
				'adminBeJobReadyController',
				function($scope, $http, $window) {

					$scope.user = {};

					var user = $window.localStorage.getItem('loggedInUser');
					console.log('user is ' + user);

					if (user == 'null' || user == 'no') {
						user = null;
					}

					if (user != null) {
						console.log('right');
						user = jQuery.parseJSON(user);
						console.log('user.userType is ' + user.userType);
						if (user.userType != 'ADMIN') {
							console.log('not logged in as Admin');
							$window.location.href = 'index.html';
						}
					} else {
						console.log('not logged in as Admin');
						$window.location.href = 'index.html';
					}

					var gC = "(?i)A.*";
					gC = window.encodeURIComponent(gC);
					var url = '../ws/rest/beJobReadyService/getAllUserTemplates/user/'
							+ gC + '/token/test';
					console.log(url);
					$http
							.get(url)
							.success(
									function(data) {
										console.log('here ');
										$scope.requests = data;
										for (i = 0; i < $scope.requests.length; i++) {
											if ($scope.requests[i].socialMediaType == 'NONE') {
												$scope.requests[i].st = 'Non Social Media Registered User';
											} else {
												$scope.requests[i].st = 'Registered through '
														+ $scope.requests[i].socialMediaType;
											}
										}

									});

					$scope.signOff = function() {
						console.log('in signoff method');

						$window.localStorage.setItem("loggedInUser", null);
						$window.location.href = 'index.html';
					}

					$scope.goToTemplateLandingPage = function(request) {
						$window.localStorage.setItem('candidateUser', JSON
								.stringify(request));
						$window.location.href = 'template1FormWizard.html';
					}

					$scope.fetchRequests = function(startWith) {

						var gC = "(?i)" + startWith + ".*";
						gC = window.encodeURIComponent(gC);
						var url = '../ws/rest/beJobReadyService/getAllUserTemplates/user/'
								+ gC + '/token/test';
						console.log(url);
						$http
								.get(url)
								.success(
										function(data) {
											console.log('classes '
													+ JSON.stringify(data));
											$scope.requests = data;
											for (i = 0; i < $scope.requests.length; i++) {
												if ($scope.requests[i].socialMediaType == 'NONE') {
													$scope.requests[i].st = 'Non Social Media Registered User';
												} else {
													$scope.requests[i].st = 'Registered through '
															+ $scope.requests[i].socialMediaType;
												}
											}

										});

					};

				});