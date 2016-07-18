'use strict';

App.controller('UserFileController', ['$scope', 'UserFileService', '$window', '$http', function($scope,UserFileService,$window,$http){
		var name;		
		var document = this;
		
		document.fileObj = {id:'', uploadDate:'', filename:'',length:''};
		document.filesObj;
		document.predicate = 'length';
		document.docs;
		document.docsField=[];
		document.username;		
		
		document.init = function(){
			//console.log('inside init with $scope- ',document.userName);
		    name = document.userName;
		    //console.log('inside init - name : ',name);
		    document.username = name;
		    //console.log(document.beast);
		    document.getAllDocuments(name);
		};
		 	
			
		document.getAllDocuments = function(userKaName){			
			//console.log('1st get with $scope',document.userName);			
			//console.log('1st get with var - userKaName',userKaName);			
			UserFileService.getAllDocuments(userKaName)
				.then(
						function(response){
							document.filesObj = response.files;
						},
						function(errResponse){
							$window.alert('Error while fetching Files from DataBase');
						}
				);
		};
		
		document.uploadFile=function(){
			var file = document.docs;			
			var fileOwner = document.ownerName;			
			var formData=new FormData();			
			formData.append('userName',fileOwner);			
        	formData.append('file',file);
        	var url = "/Api/userdoc/insertdoc/";
        	console.log(formData);
        	$http.post(url, formData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
           .success(        		  
        		   document.init,
        		   function(data, status) {        			  
	                   $window.alert("successfully uploaded");}        		   
           )
            .error(function(){            	
            	 $window.alert("File upload error");
            });
        	document.reset();
        };				
 				
		document.deleteDocument = function(userName, docId){
			//console.log('calling remove method with ',userName, docId);
			UserFileService.deleteDocument(userName,docId)
			.then(
					//document.getAllDocuments(name),
					document.init,					
					function(errResponse){
						$window.alert('Error while deleting File');
					}
				);
		};
		
		document.remove = function(id){
			if(document.fileObj.id === id){
				document.reset();
			}
			//console.log(name, 'called when remove is clicked');
			document.deleteDocument(name,id);
		};
			
		document.reset = function(){			
			document.fileObj = {id:'', uploadDate:'', filename:'',length:''};
			angular.element("input[type='file']").val(null);
			$scope.fileForm.$setPristine();
		};
		
		document.sort = function(predicate) {
			$scope.reverse = (document.predicate === predicate) ? !$scope.reverse : false;
			$scope.predicate = predicate;
		};
}]);