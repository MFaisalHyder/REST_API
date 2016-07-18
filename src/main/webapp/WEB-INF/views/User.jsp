<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>		
		<title>User HomePage</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"></link>
    	<link href="<c:url value='/static/css/application.css' />" rel="stylesheet"></link>
	</head>
	<body ng-app="myApp" class="ng-cloak" ng-controller="UserFileController as ctrl">
		<div class= "headerUser">   		
    		<%-- <font id="headerUserText"> Welcome, <b>${userName}</b></font> --%>
			<%-- <input type="hidden" name="userName" ng-init="ctrl.userName='${userName}';" ng-value="ctrl.userName" /> --%>
			<input type="hidden" name="userName" data-ng-init="ctrl.userName='${userName}'; ctrl.init();" ng-value="ctrl.userName" />
			
			<font id="headerUserText"><p> Welcome, <b>${userName}</b></p></font>
						    		
    		<a href="/Api/login">
   				<img id="logoLogoutUser" src="<c:url value='/static/images/logo_logout.png' />"/>
	   		</a>   	
   		</div>
   		
   		<div class="shadow-below-header"></div>
   		
   		<div class="generic-container" >	
			<div class="panel panel-default">			
            	<div class="panel-heading"><span class="lead">Document Insertion Form</span></div>
        			<div class="formcontainer">
        			
						<!-- <form method="post" enctype="multipart/form-data" action="/Api/document/insert/" name="fileForm" class="form-horizontal"> -->
						<form ng-submit="ctrl.uploadFile()" class="form-horizontal" name="fileForm">
													
							<input type="hidden" ng-show="false" name="ownerName" ng-init="ctrl.ownerName='${userName}';" ng-value="ctrl.ownerName" class="form-control input-sm"/>
							<input type="file" name="fileField" file-model="ctrl.docs" class="form-control input-sm" required/>							
							
							<div class="row">	
								<div class="form-actions floatRight">
									<input type="submit" value="Upload"  class="btn btn-primary btn-sm custom-width btnUpload"/>
									<button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm custom-width btnClear">Clear</button>
								</div>			
							</div>	
						</form>
					</div>
		 		</div>
		 	
		 	<div class="panel panel-default">
                <!-- Default panel contents -->
              <div class="panel-heading"><span class="lead">List of Documents</span>
              	<div class = "searchBar">
            	  	<form class="form-inline">
        				<input type="text" ng-model="search" class="form-control" placeholder="Search">        			
    				</form>
    			</div>              
              </div>
              <div class="tablecontainer">
                  <table class="table table-hover">
                      <thead>
                          <tr>
                             <!--  <th>ID</th> -->
                              <th>File Name</th>
                              <th>Upload Date</th>
                              <th ng-click="ctrl.sort('length')">Size
                              <span class = "sortingIcon" ng-show="predicate === 'length'" ng-class="{reverse:reverse}"></span>
                              </th>                              
                              <th width="20%"></th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr dir-paginate ="u in ctrl.filesObj| orderBy:predicate:reverse|filter:search|itemsPerPage:5">
                          	  <!--<td style="vertical-align: middle"><span ng-bind="u.id"></span></td>-->                        
                              <td style="vertical-align: middle"><span ng-bind="u.filename"></span></td>
                              <td style="vertical-align: middle"><span ng-bind="u.uploadDate"></span></td>
                              <td style="vertical-align: middle"><span ng-bind="u.length"></span> Bytes</td>
                              <td style="vertical-align: middle">     
                                 <a ng-href="<c:url value='/userdoc/downloadUserFiles/{{ctrl.username}}/id/{{u.id}}'/>" class="btn btn-success custom-width">Download</a>                
                              	 <!-- <button type="button" ng-click="ctrl.downloadFile(u.id)" class="btn btn-success custom-width">Download</button> -->
                              	
                              	<button type="button" ng-click="ctrl.remove(u.id)" class="btn btn-danger custom-width">Remove</button>
                              </td>
                          </tr>
                      </tbody>
                  </table>                  
              </div>                      
          </div>
          <div class="paginaterDiv">
              <dir-pagination-controls 
       			max-size="5"
       			direction-links="true"
       			boundary-links="true" >
    		  </dir-pagination-controls>              
    	  </div>
    	</div>
    	
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular.js"></script>    	
    	
    	<script src="<c:url value='/static/js/app.js' />"></script>
      	<script src="<c:url value='/static/js/dirPagination.js'/>"></script>
      	<script src="<c:url value='/static/js/repository/userfile_repository.js' />"></script>
      	<script src="<c:url value='/static/js/controller/userfile_controller.js' />"></script>
   		
	</body>
</html>