var app = angular.module('myApp', []);

app
		.controller(
				'landingController',
				function($scope, $http, $window) {

					$scope.tempUser = '';
					$scope.user = '';
					$scope.password = '';

					$scope.sendUser = function() {
						var url = 'ws/rest/utilService/tempUser/email/'
								+ $scope.tempUser + '/token/test';
						$http
								.post(url, null)
								.success(
										function(data) {
											bootbox
													.alert("Thank you for your interest! We will get back to you soon");
										});
					}

					$scope.login = function() {
						var usr = window.encodeURIComponent($scope.user);
						var password = window
								.encodeURIComponent($scope.password);
						var url = 'ws/rest/resourceService/authenticate/user/'
								+ usr + '/password/' + password
								+ '/socialMediaType/NONE';

						$http
								.post(url, null)
								.success(
										function(data) {
											$scope.loggedInUser = data;
											var u = data;
											$window.localStorage.setItem(
													"loggedInUser", JSON
															.stringify(data));

											if (u.userType == 'ADMIN') {
												$window.location.href = 'app/AdminShortCuts.html';
											} else {
												$window.location.href = 'app/index.html';
											}
										});

					}

				});
