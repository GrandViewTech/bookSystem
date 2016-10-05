var app = angular.module('myApp',  ['ui.bootstrap'], ['autocomplete']);


        
app.controller( 'autoCompleteController',  function($scope, $http, $window, $filter) {
	var url = 'ws/rest/resourceService/autoComplete/start/user/test/token/test';
	console.log(url)
	   // $http.get('../ws/rest/bookService/search/criteria/Architecture/startFrom/1/maxResults/11/token/temp').
	   $http.get(url).
	    success(function(data) {
		   console.log('success');
	    	$scope.autoCompleteKeywords = data;
	});
	
	
	
	var autoComplete = function(text) {
	text = window.encodeURIComponent(text);
	var url = 'ws/rest/resourceService/autoComplete/contains/text/'+text+'/user/test/token/test';
        	console.log(url)
	   // $http.get('../ws/rest/bookService/search/criteria/Architecture/startFrom/1/maxResults/11/token/temp').
	   $http.get(url).
	    success(function(data) {
		   
	    	$scope.autoCompleteKeywords = data;
	});
	
       
        };
	    
 });
           
        