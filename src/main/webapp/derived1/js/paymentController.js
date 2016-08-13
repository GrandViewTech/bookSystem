var app = angular.module('myApp', ['FeedbackService', 'UserService']);

app.controller( 'paymentController',  function($scope, $http, $window, $filter, $sce) {
	
	var serviceCosting = $window.localStorage.getItem('serviceCosting');
	serviceCosting = jQuery.parseJSON(serviceCosting);
	$window.localStorage.setItem('serviceCosting', null);
	bootBox.alert("Payment Successful");
	$window.location.href = 'index.html';
	
	
	
	
});



        	
		     