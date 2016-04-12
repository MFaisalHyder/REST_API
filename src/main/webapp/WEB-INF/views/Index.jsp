<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Service HomePage</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"></link>
<link href="<c:url value='/static/css/application.css' />" rel="stylesheet"></link>
</head>
<body>
	<div id = "header">
	<img src="<c:url value='/static/images/logo_cloudtech.png' />"/>
	<p> Cloud Tech Pvt. Limited </p>
	</div>
	<div class="shadow-below-header"></div>
	
	<h3 class = "indexHeader">Here we will provide you a brief walk in about our service</h3>
	
	<div class="dataCustomers">
		<img id="customersLogo" src = "<c:url value='/static/images/logo_customer.png' />"/>
		<input	id = "btnCustomers" type="button" onclick="location.href='/Api/panel'"	value="Customer Panel" class="btn btn-primary btn-sm">
	</div>
	
	<div class="dataDocuments">
		<img id="documentsLogo" src = "<c:url value='/static/images/logo_documents.png' />"/>
		<input id = "btnDocuments" type="button" onclick="location.href='/Api/file'"	value="Documents" class="btn btn-primary btn-sm">
	</div>	
	
	 <div id="footer">
	 <p>Owned by Cloud Tech Pvt. Limited </p>
	 
	 </div>
</body>
</html>