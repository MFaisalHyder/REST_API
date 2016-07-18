<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>		
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login page</title>
		
		<link href="<c:url value='static/css/bootstrap.css' />" rel="stylesheet"></link>
		<link href="<c:url value='static/css/loginpage.css' />" rel="stylesheet"></link>
		<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />
	</head>

	<body class="security-app">
		<div id="mainWrapper">
			<div class="login-container" >
				<div class="login-card">
					<div class="login-form">
						<form action = "/Api/user/loginCredentials/" method ="post"  name="appForm" class="form-horizontal">						
							
							<c:if test="${not empty message}">
								<div class="alert alert-danger">${message}</div>
							</c:if>
													
							<div class="input-group input-sm">
								<label class="input-group-addon" for="userName"><i class="fa fa-user"></i></label>
								<input type="text"  class="form-control" name="userName" placeholder="Enter Username" required>
							</div>
							
							<div class="input-group input-sm">
								<label class="input-group-addon" for="password"><i	class="fa fa-lock"></i></label>
								<input type="password"	class="form-control" name="password"	placeholder="Enter Password" required>
							</div>
							
							<div class="form-actions">
								<input type="submit" class="btn btn-block btn-primary btn-default" value="Log in">
							</div>

							<div id="register">
								<a href="<c:url value='/register' />">Register</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>

