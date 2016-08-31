var app = angular.module('myApp', []);

app
		.controller(
				'verifyController',
				function($scope, $http, $window) {

					

					

					$scope.goToHomePage = function(user) {
						console.log(' user in ver is '+user);
						$window.localStorage.setItem('loggedInUser', JSON.stringify(user));
						$window.location.href = 'derived1/index.html';
					}

				});

