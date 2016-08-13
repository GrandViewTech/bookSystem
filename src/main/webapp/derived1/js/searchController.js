
var app = angular.module('myApp', ['FeedbackService', 'UserService']);

app.controller( 'searchController',  function($scope, $http, $window, $filter, $sce, Feedback, UserService) {
	$scope.feedback = {};	
	$scope.submitted = "";
	$scope.user = {};
	$scope.someObject = {};
	$scope.termCheck = false;
	
	var user = $window.localStorage.getItem('loggedInUser');
	console.log('user is '+user);
	
	
	$scope.loggedInUser = '';
	$scope.profileText = 'login / register';
	$scope.profileDialog = 'modal';
	
		if(user != 'null'){
		console.log('right');
		user = jQuery.parseJSON(user);
		console.log('user.user is '+user.user);
			if(user.userType == 'ADMIN'){
				console.log('not logged in as Admin');
				$window.location.href = 'AdminShortCuts.html';
			}
			else{
				$scope.loggedInUser = user.user;
				$scope.profileDialog = 'signoff';
				$scope.profileText = user.user;
				console.log('profileDialog is '+$scope.profileDialog);
			}
		
			
		}
		else{
		console.log('wrong');
		}
	
	$scope.popup = function(screen){
		console.log('inside popup screen is '+screen);
		document.getElementById(screen).style.display = "";
	}
	
	
	console.log('termscheck is '+$scope.termCheck );
	
	$scope.submit=function(feed){
		console.log(feed.email);
		$scope.submitted = Feedback.feedbacksubmit($scope.feedback);
		$scope.feedback.name = "";
		$scope.feedback.email="";
		$scope.feedback.message = "";
		return $scope.submitted;
	}
	
	$scope.signup=function(user){
	console.log(' in signup 1..');
		UserService.signup(user);
	}
	
	$scope.login = function(user){
	var usr = window.encodeURIComponent(user.user);
	var password = window.encodeURIComponent(user.password);
	var url = '../ws/rest/resourceService/authenticate/user/'+usr+'/password/'+password+'/socialMediaType/NONE';
		$http.post(url, null) .
			success(function(data) {
			console.log('request successful');
			var u = data;
			$scope.profileDialog = 'signoff';
			$scope.profileText = user.user;
			$scope.closeRegDialog();
			console.log('data is '+data);
			$window.localStorage.setItem("loggedInUser", JSON.stringify(data));
				if(u.userType == 'ADMIN'){
					$window.location.href = 'AdminShortCuts.html';
				}
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
		$scope.profileDialog = 'login / register';
		$scope.profileText = 'login / register';
		$scope.profileDialog = 'modal';
		$window.localStorage.setItem("loggedInUser", null);
		document.getElementById('signoff').style.display = "none";
	}
	
	
	
	$scope.searchString = '';
	$scope.searchResponse = null;
	$scope.searchOption = '';
	$scope.review = '';
	
	$scope.isbnSearchCheck = false;
	
	var searchString = $window.localStorage.getItem('searchString');
		if(searchString == 'null'){
			searchString = 'Maths';
		}
		else if(searchString == null){
			searchString = 'Maths';
		}
		else if(searchString == ''){
			searchString = 'Maths';
		}
		else{
			$scope.searchString = searchString;
			searchString = window.encodeURIComponent(searchString);
			
		}
		
		var doc = document.documentElement;
		var bottom = document.body.scrollHeight;
		var top = (window.pageYOffset || doc.scrollTop)  - (doc.clientTop || 0);
		console.log(' top n bottom are '+top+' n '+bottom);
		window.scrollTo(0, (top + bottom)/2);
	
	var url = '../ws/rest/bookService/search/genericCriteria/'+searchString+'/startFrom/1/maxResults/16/token/test';
	$http.get(url).
	    success(function(data) {
	    	console.log('template data fetched');
	    	$scope.searchResponse = data;
		//console.log(JSON.stringify($scope.searchResponse.books[0]));
		console.log('books got ');
		$scope.hideRecommLabel=false;
		console.log('no of books '+$scope.searchResponse.books.length);
			if(!($scope.searchResponse.books.length > 15)){
				document.getElementById('loadmore').style.display = "none";
				
				console.log('no of books '+$scope.searchResponse.books.length);
			}	
			else{
				console.log('no of books11 '+$scope.searchResponse.books.length);
				$scope.searchResponse.books.pop();
				document.getElementById('loadmore').style.display = "";
			}

			if($scope.searchResponse.books.length==0){
				document.getElementById('noRecsFound').style.display = "";
			}
			else{
				document.getElementById('noRecsFound').style.display = "none";
			}
			
		
	    });
	    
	    
	$scope.feedback = {};	
	$scope.submitted = "";
	
	$scope.submit=function(feed){
		console.log(feed.email);
		$scope.submitted = Feedback.feedbacksubmit($scope.feedback);
		$scope.feedback.name = "";
		$scope.feedback.email="";
		$scope.feedback.message = "";
		return $scope.submitted;
	};
	    
	    $scope.isbnCheckboxChange = function(check){
	    console.log('check is '+check);
		if(check){
			document.getElementsByName('autosearch')[0].placeholder='SEARCH by ISBN';
		}
		else{
			document.getElementsByName('autosearch')[0].placeholder='SEARCH';
		}
	    
	    };
	    
	    $scope.loadMoreSearchBooks = function(){
	    var check = $scope.isbnSearchCheck;
	    console.log('check is .. '+check);
		
			
		var search = window.encodeURIComponent($scope.searchString);
		console.log(' search is '+search+' length '+search.length);
			if(search.length==0){
			search = 'Maths';
			}
		var url = '';
		
			if(!check){
				url = '../ws/rest/bookService/search/genericCriteria/'+search+'/startFrom/1/maxResults/30/token/test';
			}
			else{
				url = '../ws/rest/bookService/search/isbn/'+search+'/startFrom/1/maxResults/30/token/test';
			}
			
			
			
			console.log('url to search '+url);
			$http.get(url).
				success(function(data) {
				console.log('template data fetched');
				$scope.searchResponse = data;
				console.log('books got ');
				$scope.hideRecommLabel=true;
				console.log('books size '+$scope.searchResponse.books.length);
				document.getElementById('loadmore').style.display = "none";
			});
		
	    
	    };
	    
	    $scope.searchBooks = function() {
	    var check = $scope.isbnSearchCheck;
	    console.log('check is .. '+check);
		
			
		var search = window.encodeURIComponent($scope.searchString);
		var url = '';
		
			if(!check){
				url = '../ws/rest/bookService/search/genericCriteria/'+search+'/startFrom/1/maxResults/16/token/test';
			}
			else{
				url = '../ws/rest/bookService/search/isbn/'+search+'/startFrom/1/maxResults/16/token/test';
			}
			
			
			
		console.log('url to search '+url);
		$http.get(url).
			success(function(data) {
			console.log('template data fetched');
			$scope.searchResponse = data;
			console.log('books got ');
			$scope.hideRecommLabel=true;
			console.log('books size '+$scope.searchResponse.books.length);
			if(!($scope.searchResponse.books.length >= 16)){
				console.log('here111');
				document.getElementById('loadmore').style.display = "none";
				
				console.log('no of books '+$scope.searchResponse.books.length);
			}	
			else{
				console.log('here112');
				$scope.searchResponse.books.pop();
				document.getElementById('loadmore').style.display = "";
			}	
			
			if($scope.searchResponse.books.length==0){
				document.getElementById('noRecsFound').style.display = "";
			}
			else{
				document.getElementById('noRecsFound').style.display = "none";
			}
		});
		var ele = document.getElementById('searchclose');	
		console.log(ele);
	};
	
	$scope.reviewBook=function(bookId, imageUrl, bookIsbn, bookTitle, authors){
		//alert(bookIsbn);
		$window.localStorage.setItem("bookIdForReview", bookId);
		$window.localStorage.setItem("imageUrl", imageUrl);
		$window.localStorage.setItem("bookIsbn", bookIsbn);
		$window.localStorage.setItem("bookTitle", bookTitle);
		$window.localStorage.setItem("bookAuthors", authors);
		$window.location.href = 'write_review1.html';
	};
	
	
	
	
});

