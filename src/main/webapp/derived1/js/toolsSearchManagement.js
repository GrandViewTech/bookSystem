
var app = angular.module('myApp', []);
app.controller( 'toolsSearchManagementController',  function($scope, $http, $window) {
	
	$scope.user = {};
	
	var user = $window.localStorage.getItem('loggedInUser');
	console.log('user is '+user);
	
		if(user == 'null'){
			user = null;
		}
	
	
		if(user != null){
		console.log('right');
		user = jQuery.parseJSON(user);
		console.log('user.userType is '+user.userType);
			if(user.userType != 'ADMIN'){
				console.log('not logged in as Admin');
				$window.location.href = 'index.html';
			}
		}
		else{
		console.log('not logged in as Admin');
		$window.location.href = 'index.html';
		}
	
	
	var gC = "(?i)A.*";
	gC = window.encodeURIComponent(gC);
	var url = '../ws/rest/digitalToolService/searchDigitalTool/name/'+gC+'/token/test';
	console.log(url);
	   $http.get(url).
	    success(function(data) {
		console.log('here ');
		   $scope.searchResponse = data;
		    
		   
	    });
	
	$scope.signOff = function(){
		console.log('in signoff method');
		
		$window.localStorage.setItem("loggedInUser", null);
		$window.location.href = 'index.html';
	}
	
	
	$scope.updateDigitalTool = function(digitalTool) {
	console.log('searchable '+digitalTool.searchable);
	var url = '../ws/rest/digitalToolService/updateKeyword/token/test';
		$http.post(url, digitalTool).
	    success(function(data) {
		alert('DigitalTool Search Status Updated');
		   
	    });
	};
	
	$scope.fetchDigitalTools = function(startWith) {
		
		var gC = "(?i)"+startWith+".*";
		gC = window.encodeURIComponent(gC);
		var url = '../ws/rest/digitalToolService/searchDigitalTool/name/'+gC+'/token/test';
		console.log(url);
		   $http.get(url).
		    success(function(data) {
				console.log('classes '+JSON.stringify(data));
			    $scope.searchResponse = data;
			   
		    });
		
	};
		
	
	
});