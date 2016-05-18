'use strict';

App.factory('FileService', ['$http', '$q', function($http,$q){
		return {
			
			getAllDocuments : function(){
				return $http.get('http://192.168.0.100:8080/Api/document')
					.then(
							function(response){
								return response.data;
							},
							function(errResponse){
								return $q.reject(errResponse);
							}
					);
			},
			
			deleteDocument : function(id){
				return $http.delete('http://192.168.0.100:8080/Api/document/delete/id/' +id)
					.then(
							function(response){
								return response.data;
							},
							function(err) {
								return $q.reject(err);
							}							
					);				
		}
		};
}]);