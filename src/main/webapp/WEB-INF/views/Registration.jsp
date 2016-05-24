<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
    	<title>User Registration</title>
    	<style>
    	.userName.ng-valid {
        	  background-color: lightgreen;
     	 }
      	.userName.ng-dirty.ng-invalid-required {
       	   background-color: red;
      	}
      	.userName.ng-dirty.ng-invalid-minlength {
       	   background-color: red;
      	}    	
      	.firstName.ng-valid {
        	  background-color: lightgreen;
     	 }
      	.firstName.ng-dirty.ng-invalid-required {
       	   background-color: red;
      	}
      	.firstName.ng-dirty.ng-invalid-minlength {
       	  	background-color: red;
      	}      	
      	.lastName.ng-valid {
      		background-color: lightgreen;
      	}
      	.lastName.ng-dirty.ng-invalid-required {
      		background-color: red;
      	}
      	.lastName.ng-dirty.ng-invalid-minlength {
      		background-color:red;
      	}
      	.email.ng-valid {
          	background-color: lightgreen;
      	}
      	.email.ng-dirty.ng-invalid-required {
          	background-color: red;
      	}
      	.email.ng-dirty.ng-invalid-email {
          	background-color: yellow;
      	}
      	.age.ng-valid {
       	  	background-color: lightgreen;
      	}
      	.age.ng-dirty.ng-invalid-required {
       	   background-color: red;
      	}	
      	.age.ng-dirty.ng-invalid-maxlength {
       	   background-color: red;
      	}
      	.password.ng-dirty.ng-invalid-minlength {
      		background-color:red;
      	}
    	</style>
    	
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"></link>
    	<link href="<c:url value='/static/css/application.css' />" rel="stylesheet"></link>
	</head>
<body ng-app="regApp" class="ng-cloak" >
   <div class= "logo">   		
    	<img id="logoImage" src = "<c:url value='/static/images/logo_user.png' />"/>
    	<font id="logoText"> User registration</font>
   </div>
   
   <div class="shadow-below-header"></div>
   
   <div class="generic-container" ng-controller="UserController as ctrl">
          <div class="panel panel-default">
              <div class="panel-heading"><span class="lead">User Registration Form</span></div>
              <div class="formcontainer">
                  <form ng-submit="ctrl.submit()" name="appForm" class="form-horizontal">
                      
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="userName">User Name</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.user.userName" name="uname" class="userName form-control input-sm" placeholder="Enter User Name" required ng-minlength="5"/>
                                  <div class="has-error" ng-show="appForm.$dirty">
                                      <span ng-show="appForm.uname.$error.required">*</span>
                                      <span ng-show="appForm.uname.$error.minlength">Minimum length required is 5</span>
                                      <span ng-show="appForm.uname.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
                                            
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="firstName">First Name</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.user.firstName" name="fname" class="firstName form-control input-sm" placeholder="Enter First Name" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="appForm.$dirty">
                                      <span ng-show="appForm.fname.$error.required">*</span>
                                      <span ng-show="appForm.fname.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="appForm.fname.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
                        
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="lastName">Last Name</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.user.lastName" name ="lname" class="lastName form-control input-sm" placeholder="Enter your Last Name" required ng-minlength ="3"/>
                                  <div class="has-error" ng-show="appForm.$dirty">
                                  	  <span ng-show="appForm.lname.$error.required">*</span>
                                  	  <span ng-show="appForm.lname.$error.minlength">Minimum length required is 3</span>
                                  	  <span ng-show="appForm.lname.$invalid">This field is invalid</span>                                  
                                  </div>
                              </div>
                          </div>
                      </div>

					  <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="email">Email</label>
                              <div class="col-md-7">
                                  <input type="email" ng-model="ctrl.user.email" id="email" class="email form-control input-sm" placeholder="Enter e-mail" required/>
                                  <div class="has-error" ng-show="appForm.$dirty">
                                      <span ng-show="appForm.email.$error.required">This is a required field</span>
                                      <span ng-show="appForm.email.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
                      
                       <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="password">Password</label>
                              <div class="col-md-7">
                                  <input type="password" ng-model="ctrl.user.password" name="password" class="password form-control input-sm" placeholder="Enter Password" required ng-minlength="6"/>
                                  <div class="has-error" ng-show="appForm.$dirty">
                                      <span ng-show="appForm.password.$error.minlength">Minimum of 6 characters</span>
                                  </div>
                              </div>
                          </div>
                      </div>
                      
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="age">Age</label>
                              <div class="col-md-7">
                                  <input type="number" ng-model="ctrl.user.age" name="age" class="age form-control input-sm" placeholder="Enter Age" required ng-maxlength="3" 
                                    ng-pattern="/^([1-9][0-9]*)$/"/>
                                  <div class="has-error" ng-show="appForm.$dirty">
                                      <span ng-show="appForm.age.$error.required">*</span>
                                      <span ng-show="appForm.age.$error.pattern">Not valid age</span>
                                      <span ng-show="appForm.age.$error.maxlength">Max age in 3 digits</span>
                                      <span ng-show="appForm.age.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-actions floatRight">
                              <input type="submit"  value="Insert" class="btn btn-primary btn-sm" ng-disabled="appForm.$invalid"/>
                              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="appForm.$pristine">Reset Form</button>
                          </div>
                      </div>
                  </form>
                </div>                       
          </div>       
           <div id="login" >
				  	<a href="<c:url value='/login' />">Go to Login</a>
			   </div>     
      </div>
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
      <script src="<c:url value='/static/js/app.js' />"></script>
      <script src="<c:url value='/static/js/repository/user_repository.js' />"></script>
      <script src="<c:url value='/static/js/controller/user_controller.js' />"></script>
  </body>
</html>