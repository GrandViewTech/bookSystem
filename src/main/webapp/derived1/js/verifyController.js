var app = angular.module('myApp', []);

app
		.controller(
				'verifyController',
				function($scope, $http, $window) {

					

					

					$scope.goToHomePage = function(user) {
						console.log(' user in ver is '+user);
						$window.localStorage.setItem('loggedInUser', JSON.stringify(user));
						alert("Thanks for confirming your email. Welcome to Grovenue - we're delighted to have you join us in our journey!");
						$window.location.href = 'derived1/index.html';
					}

				});

