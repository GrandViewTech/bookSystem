
var app = angular.module('myApp', []);

app.directive('fileModel', ['$parse', function ($parse) {
            return {
               restrict: 'A',
               link: function(scope, element, attrs) {
                  var model = $parse(attrs.fileModel);
                  var modelSetter = model.assign;
                  
                  element.bind('change', function(){
                     scope.$apply(function(){
                        modelSetter(scope, element[0].files[0]);
                     });
                  });
               }
            };
         }]);
	 
app.service('fileUpload', ['$http', function ($http) {
            this.uploadFileToUrl = function(file, uploadUrl){
               var fd = new FormData();
               fd.append('file', file);
            
               $http.post(uploadUrl, fd, {
                  transformRequest: angular.identity,
                  headers: {'Content-Type': undefined}
               })
            
               .success(function(){
               })
            
               .error(function(){
               });
            }
         }]);

	 
app.controller( 'coachingClassUploadController',  function($scope, $http, $window, $filter, fileUpload) {
	
	
	
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
	
	$scope.uploadStatus = '';
	document.getElementById('uploadStatus').style.display = "none";
	
	$scope.uploadFile = function(){
               var file = $scope.myFile;
               
               console.log('file is ' );
               console.dir(file);
               $scope.uploadStatus = 'File Upload in progress...... ';
		       document.getElementById('uploadStatus').style.display = "";
               var url = '../ws/rest/coachingClassService/uploadCoachingClassExcel/token/test';
               //fileUpload.uploadFileToUrl(file, url);
	        var fd = new FormData();
                fd.append('file', file);
		
			 $http.post(url, fd, {
			  transformRequest: angular.identity,
			  headers: {'Content-Type': undefined}
		       })
		    
		       .success(function(){
		       console.log('succss ');
			$scope.uploadStatus = 'File successfully uploaded onto the server. ';
			document.getElementById('uploadStatus').style.display = "";
		       })
		    
		       .error(function(){
		       console.log('failure ');
		       $scope.uploadStatus = 'File Upload failed. Try again. If the problem persists check your excel file format or contact Administrator! ';
		       document.getElementById('uploadStatus').style.display = "";
		       });
		};
	
	
	
});