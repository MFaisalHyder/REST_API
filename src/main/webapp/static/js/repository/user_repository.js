'use strict';

App.factory('UserService', ['$http', '$q', function($http,$q) {
	
	return {
		
			getAllCustomers: function() {
					return $http.get('http://192.168.0.100:8080/Api/user')
						.then(
								function(response){
									return response.data;
								},
								function(errResponse){
									return $q.reject(errResponse);
								}					
						);			
			},
			
			createCustomer: function(user) {
				return $http.post('http://192.168.0.100:8080/Api/user/insertUser/', user)
					.then(
							function(response){
								return response.data;
							},
							function(errResponse){
								console.error('Error entering Customer record');
								return $q.reject(errResponse);
							}							
					);
			},
			
			updateCustomer: function(user, id) {
				return $http.put('http://192.168.0.100:8080/Api/user/updateUser/id/'+id, user)
					.then(
							function(response){
								return response.data;
							},
							function(errResponse){
								console.error('Error updating user record');
								return $q.reject(errResponse);
							}							
					);					
			},
			
			deleteCustomer: function(id) {
					return $http.delete('http://192.168.0.100:8080/Api/user/delete/id/'+id)
						.then(
								function(response){
									return response.data;
								},
								function(errResponse){
									console.error('Error deleting user record');
									return $q.reject(errResponse);
								}
						);				
			}
			
	};
}]);