'use strict';

App.controller('FileController', ['$scope', 'FileService', '$window', '$http', function($scope,FileService,$window,$http){
		var document = this;
		document.fileObj = {id:'', uploadDate:'', filename:'',length:''};
		document.filesObj;
		document.predicate = 'length';
		
		
		document.getAllDocuments = function(){
			FileService.getAllDocuments()
				.then(
						function(response){
							document.filesObj = response.files;
						},
						function(errResponse){
							$window.alert('Error while fetching Files from DataBase');
						}
				);
		};
		
		document.deleteDocument = function(id){
			FileService.deleteDocument(id)
				.then(
						document.getAllDocuments,
						function(errResponse){
							$window.alert('Error while deleting File');
						}
				);
		};
		
		document.insertDocument = function(file){
			FileService.insertDocument(file)
			.then(
					document.getAllDocuments,
					function(errResponse){
						console.error('Error while inserting File');
					}					
			);
		};
		
		document.getAllDocuments();
	
		document.submit = function(){
			document.insertDocument(document.fileDocx);
			document.reset();
		};
		
		document.remove = function(id){
			if(document.fileObj.id === id){ 
				document.reset();
			}
			document.deleteDocument(id);			
		};
		
		document.reset = function(){
			document.fileObj = {id:'', uploadDate:'', filename:'',length:''};
			$scope.fileForm.$setPristine();
		};
		
		document.sort = function(predicate) {
			$scope.reverse = (document.predicate === predicate) ? !$scope.reverse : false;
			$scope.predicate = predicate;
		};
		
}]);