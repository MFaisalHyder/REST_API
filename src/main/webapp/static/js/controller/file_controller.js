'use strict';

App.controller('FileController', ['$scope', 'FileService', '$window', '$http', function($scope,FileService,$window,$http){
		var document = this;
		
		document.fileObj = {id:'', uploadDate:'', filename:'',length:''};
		document.filesObj;
		document.predicate = 'length';
		document.docs;
		document.docsField=[];
				
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
		
		document.uploadFile=function(){
			var file = document.docs;
			var formData=new FormData();
        	formData.append('file',file);
        	var url = "/Api/document/insert/";
        	console.log(formData);
        	$http.post(url, formData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
           .success(
        		   document.getAllDocuments,
        		   function(data, status) {        	   	   
	                    $window.alert("successfully uploaded");
			})
            .error(function(){
            });        	
        	document.reset();        	
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
				
		/*	document.downloadFile = function(id){
			
        	var url = "/Api/document/download/id/"+id;        	       	        	
        	$http({

                method: 'GET',
                url,
                responseType: 'arraybuffer'
            })
            .success(function(data, status){

                var blob = new Blob([data], {type: 'application/octet-stream'});
                saveAs(blob,'file');
            })
            .error(function(data, status){
            });
        	
        };       
        
        $http({
            url : '/Api/document/download/id/'+id,
            method : 'GET',
            params : {},
            headers : {
                'Content-type' : 'application/*',
            },
            responseType : 'arraybuffer'
        }).success(function(data, status, headers, config) {
            var file = new Blob([ data ], {
                type : 'application/octet-stream'
            });
            //trick to download store a file having its URL
            var fileURL = URL.createObjectURL(file);
            var a         = document.createElement('a');
            a.href        = fileURL; 
            a.target      = '_blank';
            a.download    = 'yourfilename.pdf';
            document.body.appendChild(a);
            a.click();
        }).error(function(data, status, headers, config) {

        });
    };
    */
        document.getAllDocuments();	
		
		document.remove = function(id){
			if(document.fileObj.id === id){ 
				document.reset();
			}
			document.deleteDocument(id);			
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