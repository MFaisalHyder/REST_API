'use strict';

App.factory('UserFileService', ['$http', '$q', function($http,$q){
		return {
			
			getAllDocuments : function(userName){
				return $http.get('http://192.168.0.100:8080/Api/userdoc/getUserFiles/' +userName)
					.then(
							function(response){
								return response.data;
							},
							function(errResponse){
								return $q.reject(errResponse);
							}
					);
			},
			
			deleteDocument : function(userName,docId){
				return $http.delete('http://192.168.0.100:8080/Api/userdoc/'+userName+'/delete/id/'+docId)
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