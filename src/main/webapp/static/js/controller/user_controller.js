'use strict';

RegApp.controller('UserController', ['$scope', 'UserService', '$window', function($scope, UserService,$window){	
		var selfUser = this;
		selfUser.user={id:'', userName:'', firstName:'', lastName:'', email:'', password:'', age:''};
		
		selfUser.insertUser = function(userObj){
			UserService.insertUser(userObj).
				then(
						selfUser.reset,
					function(errResponse) {						
					}
				);
		};
		
		selfUser.submit = function() {
			console.log('Saving New User', selfUser.user);    
            selfUser.insertUser(selfUser.user);
         };
         		
		selfUser.reset = function(){
			selfUser.user={id:'', userName:'', firstName:'', lastName:'', email:'', password:'', age:''};
			$scope.appForm.$setPristine();
		};		
}]);
