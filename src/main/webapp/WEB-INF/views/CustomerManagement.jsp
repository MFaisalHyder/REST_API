<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
    	<title>Cloud Data Service</title>
    	<style>
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
      	.age.ng-valid {
       	   background-color: lightgreen;
      	}
      	.age.ng-dirty.ng-invalid-required {
       	   background-color: red;
      	}	
      	.age.ng-dirty.ng-invalid-maxlength {
       	   background-color: red;
      	}

    	</style>
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    	<link href="<c:url value='/static/css/application.css' />" rel="stylesheet"></link>
	</head>
<body ng-app="myApp" class="ng-cloak">
   <div class="generic-container" ng-controller="CustomerController as ctrl">
          <div class="panel panel-default">
              <div class="panel-heading"><span class="lead">Customer Management Form </span></div>
              <div class="formcontainer">
                  <form ng-submit="ctrl.submit()" name="appForm" class="form-horizontal">
                      <input type="hidden" ng-model="ctrl.customer.id" />
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">First Name</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.customer.firstName" name="fname" class="firstName form-control input-sm" placeholder="Enter your First Name" required ng-minlength="3"/>
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
                              <label class="col-md-2 control-lable" for="file">Last Name</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.customer.lastName" name ="lname" class="lastName form-control input-sm" placeholder="Enter your Last Name" required ng-minlength ="3"/>
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
                              <label class="col-md-2 control-lable" for="file">Age</label>
                              <div class="col-md-7">
                                  <input type="number" ng-model="ctrl.customer.age" name="age" class="age form-control input-sm" placeholder="Enter your Age" required ng-maxlength="3"/>
                                  <div class="has-error" ng-show="appForm.$dirty">
                                      <span ng-show="appForm.age.$error.required">*</span>
                                      <span ng-show="appForm.age.$error.maxlength">Max age in 3 digits</span>
                                      <span ng-show="appForm.age.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-actions floatRight">
                              <input type="submit"  value="{{!ctrl.customer.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="appForm.$invalid">
                              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="appForm.$pristine">Reset Form</button>
                          </div>
                      </div>
                  </form>
              </div>
          </div>
          <div class="panel panel-default">
                <!-- Default panel contents -->
              <div class="panel-heading"><span class="lead">List of Customers </span></div>
              <div class="tablecontainer">
                  <table class="table table-hover">
                      <thead>
                          <tr>
                              <th>ID</th>
                              <th>First Name</th>
                              <th>Last Name</th>
                              <th>Age</th>
                              <th width="20%"></th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr ng-repeat="u in ctrl.customers">
                              <td><span ng-bind="u.id"></span></td>
                              <td><span ng-bind="u.firstName"></span></td>
                              <td><span ng-bind="u.lastName"></span></td>
                              <td><span ng-bind="u.age"></span></td>
                              <td>
                              <button type="button" ng-click="ctrl.edit(u.id)" class="btn btn-success custom-width">Edit</button>
                              <button type="button" ng-click="ctrl.remove(u.id)" class="btn btn-danger custom-width">Remove</button>
                              </td>
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
      
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
      <script src="<c:url value='/static/js/app.js' />"></script>
      <script src="<c:url value='/static/js/repository/customer_repository.js' />"></script>
      <script src="<c:url value='/static/js/controller/customer_controller.js' />"></script>
  </body>
</html>