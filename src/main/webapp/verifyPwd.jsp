<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="com.v2.booksys.common.util.UserVerficationClient"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>

<html lang="en">
      <head>
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.1/angular.min.js"></script>
            <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.1/angular-resource.js"></script>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
            <script src="derived1/js/bootbox.js"></script>
            
            <script src="derived1/js/commonServices.js"></script>
	     <script src="derived1/js/verifyController.js?dummy=dummy"></script>
          
            <script src="http://www.w3schools.com/lib/w3data.js"></script>
	
      </head>
      <body id="verify" ng-app="myApp" ng-controller="verifyController" >
      
      

<%

	String user = "";
	String verString = request.getParameter("verificationString");
				String socialMediaType = request.getParameter("socialMediaType");
				
				String result = UserVerficationClient.verifyUser(verString, socialMediaType);
				result = result.replace("Msg-", "");
%>

<%
	if (result.startsWith("Success"))
					{
					
					user = result.replace("Success-", "");
					System.out.println(user);
%>


<h2>
	
	<b> You have successfully verified your credentials. Please click
		below to proceed. </b>
</h2>

<p>
<h2>
	<a href="" onClick="callverify()" >Click here and Proceed</a>
</h2>

</p>


<%
	}
				else
					{
%>

<h2>
	<b> Validation failed because of following reason - <%=result.replace("Msg-", "")%>.
		Please click below to proceed.
	</b>
</h2>
<p>
<h2>
	<a href="derived1/index.html" >Click here and Proceed</a>
</h2>

</p>


<%
	}
%>


</body>

<script>
		function callverify(){
			var usr =<%= user %>;
			 angular.element(document.getElementById('verify')).scope().goToHomePage(usr);
		}
	
	</script>
</html>