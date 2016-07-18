<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	 	<title>Application HomePage</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"></link>
		<link href="<c:url value='static/css/homePage.css' />" rel="stylesheet"></link>
	</head>

	<body class="security-app">
	
		<div class="lc-block">	
			<div class="details">		
				<h2>Welcome to the Home Page</h2>
				<div class="logInBox">
					<img id="loginLogo" class="animateImg" src = "<c:url value='static/images/icon_login.png' />"/>
				</div>
				<a href="/Api/login" class="button green small">LogIN</a>
			</div>			
		</div>
	</body>
</html>
