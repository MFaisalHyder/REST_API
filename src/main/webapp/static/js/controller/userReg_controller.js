'use strict';

RegApp.controller('UserRegController', ['$scope', 'UserRegService', '$window', function($scope, UserRegService,$window){	
		var selfUser = this;		
		selfUser.user={userName:'', firstName:'', lastName:'', email:'', password:'', age:''};
		
		selfUser.userLogin={userName:'', password:''};		
		
		selfUser.insertUser = function(userObj){
			UserRegService.insertUser(userObj).
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
			selfUser.user={userName:'', firstName:'', lastName:'', email:'', password:'', age:''};
			
			
			selfUser.userLogin={userName:'', password:''};
			$scope.appForm.$setPristine();
		};
		
		selfUser.login = function(){
			console.log('logging in', selfUser.userLogin.userName);
			selfUser.loginUser(selfUser.userLogin);
		};
		
		selfUser.loginUser = function(userLoginObj){
			UserRegService.loginUser(userLoginObj);				
		};
}]);
