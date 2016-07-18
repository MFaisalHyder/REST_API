'use strict';

RegApp.factory('UserRegService', ['$http', '$q', '$window', function($http,$q,$window) {
	
	return {		
			insertUser: function(user)	{
				
				return $http.post('http://192.168.0.100:8080/Api/user/insertUser/', user)
				.then(
						function (response) {
							$window.alert('successfully registered');
							return response.data;
						},
						function (errResponse) {
							$window.alert('UserName already taken');
							return $q.reject(errResponse);
						}
				);
			},
			
			loginUser: function(userLoginData)	{
				return $http.post('http://192.168.0.100:8080/Api/user/loginCredentials/', userLoginData);
			}
	};
}]);