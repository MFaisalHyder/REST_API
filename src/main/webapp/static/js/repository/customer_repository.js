'use strict';

App.factory('CustomerService', ['$http', '$q', function($http,$q) {
	
	return {
		
			getAllCustomers: function() {
					return $http.get('http://192.168.0.100:8080/Api/customer')
						.then(
								function(response){
									return response.data;
								},
								function(errResponse){
									console.error('Error retrieving Customers record')
									return $q.reject(errResponse);
								}					
						);			
			},
			
			createCustomer: function(customer) {
				return $http.post('http://192.168.0.100:8080/Api/customer/insert/', customer)
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
			
			updateCustomer: function(customer, id) {
				return $http.put('http://192.168.0.100:8080/Api/customer/update/id/'+id, customer)
					.then(
							function(response){
								return response.data;
							},
							function(errResponse){
								console.error('Error updating Customer record');
								return $q.reject(errResponse);
							}							
					);					
			},
			
			deleteCustomer: function(id) {
					return $http.delete('http://192.168.0.100:8080/Api/customer/delete/id/'+id)
						.then(
								function(response){
									return response.data;
								},
								function(errResponse){
									console.error('Error deleting Customer record');
									return $q.reject(errResponse);
								}
						);				
			}
			
	};
}]);