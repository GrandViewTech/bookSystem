
//			else{
//				$window.location.href = 'index.html';	
//			}
	
	var gC = "(?i)A.*";
			gC = window.encodeURIComponent(gC);
	var url = 'ws/rest/bookService/searchBook/title/'+gC+'/token/test';
	console.log(url);
	   $http.get(url).
	    success(function(data) {
			console.log('here ');
		    $scope.searchResponse = data;
		    
		   
	    });
	   
	   $scope.logOff = function() { 

		   $window.localStorage.setItem("loggedIn", 'no');
		   $window.location.href = 'login.html';

		  };
		
		$scope.updateBook = function(book) {
		console.log('searchable '+book.searchable);
		var url = 'ws/rest/bookService/saveOrUpdate/book/token/test';
			$http.post(url, book).
		    success(function(data) {
		    	alert('Book Search Status Updated');
			   
		    });
		};
		
		$scope.fetchBooks = function(startWith) {
			
			var gC = "(?i)"+startWith+".*";
			gC = window.encodeURIComponent(gC);
			var url = 'ws/rest/bookService/searchBook/title/'+gC+'/token/test';
			console.log(url);
			   $http.get(url).
			    success(function(data) {
					console.log('books '+JSON.stringify(data));
				    $scope.searchResponse = data;
				   
			    });
			
		};
		
	    
 });

