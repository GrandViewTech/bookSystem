var app = angular.module('myApp', []);


        
app.controller( 'mainController',  function($scope, $http, $window, $filter, $sce) {
	
	
	
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
		
	var request = 	$window.localStorage.getItem('candidateUser');
	console.log('request is '+request);
	if(request == null || request == 'null'){
		$window.location.href = 'adminBeJobReady.html';
	}
	request = 	jQuery.parseJSON(request);

	//var candidate = request.user +'-'+ request.temlplateType;
	var candidate = '';
	var candidateEmail = window.encodeURIComponent(request.user);
	
	$scope.loadTemplate = function(){
		var url = '../ws/rest/templateService/template1/candidateName/'+candidate+'/token/test';
		$http.get(url).
	    success(function(data) {
	    	console.log('template data fetched');
	    	 $scope.template = data;
	    });
		
	}
	
	
	var url = '../ws/rest/resourceService/getUserByEmailAddressAndSocialMediaType/socialMediaType/'+request.socialMediaType+'/token/test?emailAddress='+candidateEmail;
	console.log(' fetch user url '+url);
		$http.get(url).
	    success(function(data) {
	    	console.log('user fetched fetched '+data);
	    	candidate = data.user+'['+request.socialMediaType+']['+request.temlplateType+']';
		console.log('candidate is '+candidate);	

		candidate = window.encodeURIComponent(candidate);
		$scope.loadTemplate();
	    });
			
	
	$scope.currentSchool = '';

	$scope.currentCompany = '';
		 
	$scope.schoolNameDisabled = 'false';

	$scope.schoolFormShow = 'false';

	$scope.companyNameDisabled = 'false';

	$scope.companyRoleDisabled = 'false';


	$scope.activitySourceDisabled = 'false';
			
	$scope.activityFormShow = 'false';

	
	
	

	  
	$scope.addSchool = function() {
		console.log('in add school');
		$scope.currentSchool = '';
		$scope.schoolNameDisabled = 'false';
		$scope.schoolFormShow = 'true';
	};
	
	
	
	$scope.editSchool = function(school) {
		console.log('in edit school');
		$scope.currentSchool = school;
		$scope.schoolNameDisabled = 'true';
		$scope.schoolFormShow = 'true';
	};
	
	
	
	
	$scope.deleteSchool = function(school) {
		//var candidateName = window.encodeURIComponent($scope.template.candidateName);
		var schoolId = window.encodeURIComponent(school.id);
		var url = '../ws/rest/templateService/template1/candidateName/'+candidate+'/delete/schoolId/'+schoolId+'/token/{token}';
		
		$http.post(url, null).
	    success(function(data) {
	    	console.log('school deleted');
	    	// $scope.template = data;
	    	//now call 
	    	$scope.loadTemplate();
	    });
		
	};
	
	
	
	
	$scope.saveSchool = function(school) {
		
		if(school == null || school == '' ){
		console.log('school null');
			alert("Enter Details for School before saving");
			document.getElementById("errorschool").innerHTML = "Enter School Details";
			document.getElementById("schoolName").focus();
			return;
		}
		
		if(school.schoolName  == null){
			document.getElementById("errorschool").innerHTML = "Enter School Name";
			document.getElementById("schoolName").focus();
			return;
		}
		
		school.schoolName = school.schoolName.trim();
		if(school.schoolName == null || school.schoolName == ''){
			document.getElementById("errorschool").innerHTML = "Enter School Name";
			document.getElementById("schoolName").focus();
			return;
		}
		else{
		document.getElementById("errorschool").innerHTML = "";
		}
		
		if(school.schoolLocation == null || school.schoolLocation == ''){
			document.getElementById("errorschool").innerHTML = "Enter School Location";
			document.getElementById("schoolLocation").focus();
			return;
		}
		else{
			document.getElementById("errorschool").innerHTML = "";
		}
		
		if(school.monthAndYear == null || school.monthAndYear == ''){
			document.getElementById("errorschool").innerHTML = "Enter Pass Year (Month and Year)";
			document.getElementById("monthAndYear").focus();
			return;
		}
		else{
			document.getElementById("errorschool").innerHTML = "";
		}
		
		if(school.degree == null || school.degree == ''){
			document.getElementById("errorschool").innerHTML = "Enter Degree";
			document.getElementById("degree").focus();
			return;
		}
		else{
			document.getElementById("errorschool").innerHTML = "";
		}
		
		
		var url = '../ws/rest/templateService/template1/school/candidateName/'+candidate+'/token/test';
		$http.post(url, school).
	    success(function(data) {
	    	console.log('school saved');
		$scope.currentSchool = '';
		$scope.schoolFormShow = 'false';
	    	$scope.loadTemplate();
		});
		
		
	};	
	
$scope.saveTemplate1 = function() {
	console.log(JSON.stringify(	$scope.template));
		var url = '../ws/rest/templateService/template1/candidateName/'+candidate+'/token/test';
		$http.post(url, $scope.template).
	    success(function(data) {
	    	console.log('school saved');
	    	$scope.loadTemplate();
		$scope.schoolFormShow = 'false';
		});
		
		
	};	
	
	
	$scope.addComment = function() {
	var comment = $scope.schoolComment;
	
		if(comment != null){
			comment = comment.trim();
		}
	
		if(comment == null || comment ==''){
			document.getElementById("errorschoolcomment").innerHTML = "School Comment can not be blank";
			document.getElementById("schoolComment").focus();
			return;
		}
		else{
		document.getElementById("errorschoolcomment").innerHTML = "";
		}
	
	
	console.log(comment);
		console.log(JSON.stringify($scope.currentSchool));
		console.log(JSON.stringify($scope.currentSchool.schoolComments));
			if(  typeof $scope.currentSchool.schoolComments == 'undefined'){
				console.log('school comments is not defnd');
				$scope.currentSchool.schoolComments = new Array();
			}
		$scope.currentSchool.schoolComments.push(comment);
		console.log(JSON.stringify($scope.currentSchool));
		$scope.schoolComment = '';
		//$window.location.href = 'schoolForm.html';	
		
	};
	
	
	$scope.deleteComment = function(comment) {
	console.log('comment  to be deleted '+comment);
		for(var i=0;i<$scope.currentSchool.schoolComments.length; i++){
			var comm = $scope.currentSchool.schoolComments[i];
			if(comm == comment){
				$scope.currentSchool.schoolComments.splice(i, 1);
				console.log('comment  deleted');
				console.log(JSON.stringify($scope.currentSchool.schoolComments));
				break;
			}
		}
		
	};
	
	
	
	/////////////
	
	
	$scope.addCompany = function() {
		console.log('in add company');
		$scope.currentCompany = '';
		$scope.companyNameDisabled = 'false';
		$scope.companyRoleDisabled = 'false';
		$scope.companyFormShow = 'true';
	};
	
	
	$scope.editCompany = function(company) {
		console.log('in edit company');
		$scope.currentCompany = company;
		$scope.companyNameDisabled = 'true';
		$scope.companyRoleDisabled = 'true';
		$scope.companyFormShow = 'true';
	};
	
	
	
	
	$scope.deleteCompany = function(company) {
		//var candidateName = window.encodeURIComponent($scope.template.candidateName);
		var companyId = window.encodeURIComponent(company.id);
	//	var role = window.encodeURIComponent(company.role);
		var url = '../ws/rest/templateService/template1/candidateName/'+candidate+'/delete/companyId/'+companyId+'/token/test';
		
		$http.post(url, null).
	    success(function(data) {
	    	console.log('company deleted');
	    	// $scope.template = data;
	    	//now call 
	    	$scope.loadTemplate();
	    });
		
	};
	
	
	
	
	$scope.saveCompany = function(company) {
	console.log(JSON.stringify(company));
	
		if(company == null || company == '' ){
			console.log('company null');
			document.getElementById("errorcompany").innerHTML = "Enter Company Details";
			document.getElementById("companyName").focus();
			return;
		}
		
		console.log('rrrrrr');
			if(company.companyName != null){
			company.companyName = company.companyName.trim();
			}
			
			if(company.role != null){
			company.role = company.role.trim();
			}
		
		
		
		if(company.companyName == '' || company.companyName == null){
			document.getElementById("errorcompany").innerHTML = "Enter Company Name";
			document.getElementById("companyName").focus();
			return;
		}
		else{
			document.getElementById("errorcompany").innerHTML = "";
			
		}
		
		if(company.role == '' || company.role == null){
			document.getElementById("errorcompany").innerHTML = "Enter Role for the Company";
			document.getElementById("role").focus();
			return;
		}
		else{
			document.getElementById("errorcompany").innerHTML = "";
			
		}
		
		if(company.companyLocation == '' || company.companyLocation == null){
			document.getElementById("errorcompany").innerHTML = "Enter Company Location";
			document.getElementById("companyLocation").focus();
			return;
		}
		else{
			document.getElementById("errorcompany").innerHTML = "";
			
		}
		
		if(company.duration == '' || company.duration == null){
			document.getElementById("errorcompany").innerHTML = "Enter Company Duration";
			document.getElementById("duration").focus();
			return;
		}
		else{
			document.getElementById("errorcompany").innerHTML = "";
			
		}
		
		
		
		var url = '../ws/rest/templateService/template1/company/candidateName/'+candidate+'/token/test';
		$http.post(url, company).
	    success(function(data) {
	    	console.log('company saved');
		$scope.currentCompany = '';
		$scope.companyNameDisabled = 'false';
		$scope.companyRoleDisabled = 'false';
		$scope.companyFormShow = 'false';
	    	$scope.loadTemplate();
		});
		
		
	};	
	
	$scope.addRoleComment = function() {
	var comment = $scope.roleComment;
	
		if(comment != null){
			comment = comment.trim();
		}
	
		if(comment == null || comment ==''){
			document.getElementById("errorrolecomment").innerHTML = "Role Comment can not be blank";
			document.getElementById("roleComment").focus();
			return;
		}
		else{
		document.getElementById("errorrolecomment").innerHTML = "";
		}
	
	
	console.log(comment);
		console.log(JSON.stringify($scope.currentCompany));
		console.log(JSON.stringify($scope.currentCompany.roleComments));
			if(  typeof $scope.currentCompany.roleComments == 'undefined'){
				console.log('Company comments is not defnd');
				$scope.currentCompany.roleComments = new Array();
			}
		$scope.currentCompany.roleComments.push(comment);
		console.log(JSON.stringify($scope.currentCompany));
		$scope.roleComment = '';
		//$window.location.href = 'schoolForm.html';	
		
	};
	
	
	$scope.deleteRoleComment = function(comment) {
	console.log('comment  to be deleted '+comment);
		for(var i=0;i<$scope.currentCompany.roleComments.length; i++){
			var comm = $scope.currentCompany.roleComments[i];
			if(comm == comment){
				$scope.currentCompany.roleComments.splice(i, 1);
				console.log('comment  deleted');
				console.log(JSON.stringify($scope.currentCompany.roleComments));
				break;
			}
		}
		
	};
	
	
	
	////// last 
	
	$scope.addActivity = function() {
		console.log('in add leadership');
		$scope.currentActivity = '';
		$scope.activitySourceDisabled = 'false';
		
		$scope.activityFormShow = 'true';
	};
	
	
	$scope.editActivity = function(activity) {
		console.log('in edit activity');
		$scope.currentActivity = activity;
		$scope.activitySourceDisabled = 'true';
		$scope.activityFormShow = 'true';
	};
	
	
	
	
	$scope.deleteActivity = function(activity) {
		//var candidateName = window.encodeURIComponent($scope.template.candidateName);
		var leadershipId = window.encodeURIComponent(activity.id);
		
		var url = '../ws/rest/templateService/template1/candidateName/'+candidate+'/delete/leadershipId/'+leadershipId+'/token/test';
		
		$http.post(url, null).
	    success(function(data) {
	    	console.log('company deleted');
	   
	    	$scope.loadTemplate();
	    });
		
	};
	
	
	
	
	$scope.saveActivity = function(activity) {
		
		if(activity == null || activity == '' ){
			//alert("Enter Details for Leadership or Activity section");
			document.getElementById("errorleadership").innerHTML = "Enter Leadership or Activity Details";
			document.getElementById("source").focus();
			return;
		}
		
		
		if(activity.source != null){
			activity.source = activity.source.trim();
		}
		
		if(activity.source == null ||  activity.source == '' ){
			document.getElementById("errorleadership").innerHTML = "Enter Leadership or Activity Source";
			document.getElementById("source").focus();
			return;
		}else{
		document.getElementById("errorleadership").innerHTML = "";
		}
		
		
		
		//var candidateName = window.encodeURIComponent($scope.template.candidateName);
		
		var url = '../ws/rest/templateService/template1/leadershipActivity/candidateName/'+candidate+'/token/test';
		$http.post(url, activity).
	    success(function(data) {
	    	console.log('company saved');
		$scope.currentActivity = '';
		$scope.activitySourceDisabled = 'true';
		$scope.activityFormShow = 'false';
	    	$scope.loadTemplate();
		});
		
		
	};	
	
	$scope.addActivityComment = function() {
	var comment = $scope.activityComment;
	
		if(comment != null){
			comment = comment.trim();
		}
	
		if(comment == null || comment ==''){
			document.getElementById("errorleadership").innerHTML = "Leadership Activity or Comment can not be blank";
			document.getElementById("activityComment").focus();
			return;
		}
		else{
		document.getElementById("errorleadership").innerHTML = "";
		}
	
	console.log(comment);
		console.log(JSON.stringify($scope.currentActivity));
		console.log(JSON.stringify($scope.currentActivity.comments));
			if(  typeof $scope.currentActivity.comments == 'undefined'){
				console.log('Activity comments is not defnd');
				$scope.currentActivity.comments = new Array();
			}
		$scope.currentActivity.comments.push(comment);
		console.log(JSON.stringify($scope.currentActivity));
		$scope.activityComment = '';
		//$window.location.href = 'schoolForm.html';	
		
	};
	
	
	$scope.deleteActivityComment = function(comment) {
	console.log('comment  to be deleted '+comment);
		for(var i=0;i<$scope.currentActivity.comments.length; i++){
			var comm = $scope.currentActivity.comments[i];
			if(comm == comment){
				$scope.currentActivity.comments.splice(i, 1);
				console.log('comment  deleted');
				console.log(JSON.stringify($scope.currentActivity.comments));
				break;
			}
		}
		
	};
	
	//////
	
	$scope.generateProfile = function(){
		var ul = '../ws/rest/templateService/generatePdfForTemplate1/token/test';
		$http({
		    url: ul,
		    dataType: 'json',
		    method: 'POST',
		    data: $scope.template,
		    headers: {
		        "Content-Type": "application/json"
		    },
		    responseType : 'arraybuffer'
		}).success(function(response){
			console.log(' response got');
			var file = new Blob([response], {type: 'application/pdf'});
			//var fileURL = URL.createObjectURL(file);
			 saveAs(file, 'filename.pdf');
			//var fileURL = URL.createObjectURL(file);
			//$scope.content = $sce.trustAsResourceUrl(fileURL);
			//$window.localStorage.setItem("pdfUrl", $scope.content);
			//$window.location.href = 'pdfEmbed.html';
			
		}).error(function(error){
		    $scope.error = error;
		});
	};
	
	$scope.logOff = function() { 
		
		$window.localStorage.setItem("loggedInUser", null);
		  // $window.localStorage.setItem("loggedIn", 'no');
		   $window.location.href = 'index.html';

		  };
	

});	



app.controller( 'pdfController',  function($scope, $http, $window, $filter, $sce) {
	
	$scope.content = $window.localStorage.getItem("pdfUrl");
		
		$scope.logOff = function() { 

			   $window.localStorage.setItem("loggedIn", 'no');
			   $window.location.href = 'login.html';

			  };
		
		
 });


app.controller( 'loginController',  function($scope, $http, $window, $filter) {
	
$scope.user = '';
$scope.pwd = '';
		
		$scope.logOff = function() { 
			
			$window.localStorage.setItem("loggedInUser", 'no');
			   $window.localStorage.setItem("loggedIn", 'no');
			   $window.location.href = 'CandidateLogin.html';

			  };
			  
			  

			$scope.logIn = function() { 
				
					if($scope.user == '' || $scope.user == null){
						alert('Enter a Name')
						$window.location.href = 'CandidateLogin.html';
					}
					
					if($scope.pwd == '' || $scope.pwd == null){
						alert('Enter Password')
						$window.location.href = 'CandidateLogin.html';
					}
					
					
					if($scope.pwd != 'secret' ){
						alert('Wrong Credentials');
						$window.location.href = 'CandidateLogin.html';
					}
					
					$window.localStorage.setItem("loggedInUser", $scope.user);
					$window.localStorage.setItem("loggedIn", 'yes');
					$window.location.href = 'template1FormWizard.html';

			};	  
		
		
 });
           
           
        	
		     