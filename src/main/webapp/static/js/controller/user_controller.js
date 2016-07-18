'use strict';

App.controller('UserController', ['$scope', 'UserService', '$window', function($scope, UserService,$window){	
		var selfCustomer = this;
		selfCustomer.customer={id:'', userName:'', firstName:'', lastName:'', email:'', password:'', age:''};
		selfCustomer.customers;		
		selfCustomer.predicate= 'age';				
		
		selfCustomer.getAllCustomers = function(){
			UserService.getAllCustomers()
			.then(
					function(response){
						selfCustomer.customers = response.users;
					},					
					function(errResponse){
						$window.alert('Error while fetching Users');
					}					
			);			
		};
		
		selfCustomer.createCustomer = function(customerObj){
			UserService.createCustomer(customerObj)
			.then(
					selfCustomer.getAllCustomers,
					function(errResponse){
						console.error('Error while creating User');
					}					
			);
		};
		
		selfCustomer.updateCustomer = function(customerObj, id){
			UserService.updateCustomer(customerObj, id)
			.then(
					selfCustomer.getAllCustomers,
					function(errResponse){
						console.error('Error while updating User');
					}
					
			);
		};
		
		selfCustomer.deleteCustomer = function(id){
			UserService.deleteCustomer(id)
			.then(
					selfCustomer.getAllCustomers,
					function(errResponse){
						$window.alert('Error while deleting User');
					}
			);
		};
	
		selfCustomer.getAllCustomers();
				
		selfCustomer.submit = function() {
             if(selfCustomer.customer.id==''){
                 console.log('Saving New User', selfCustomer.customer);    
                 selfCustomer.createCustomer(selfCustomer.customer);
             }else{
                 selfCustomer.updateCustomer(selfCustomer.customer, selfCustomer.customer.id);
                 console.log('User updated with id ', selfCustomer.customer.id);
             }
             selfCustomer.reset();
         };
             
        selfCustomer.edit = function(id){
             console.log('id to be edited', id);
             for(var i = 0; i < selfCustomer.customers.length; i++){
                 if(selfCustomer.customers[i].id == id) {
                    selfCustomer.customer = angular.copy(selfCustomer.customers[i]);
                    break;
                 }
             }
        };
            		
		selfCustomer.remove = function(id){
			console.log('User with id to be deleted', id);
			if(selfCustomer.customer.id === id){
				selfCustomer.reset();
			}
			selfCustomer.deleteCustomer(id);
		}
		
		selfCustomer.reset = function(){
			selfCustomer.customer={id:'', userName:'', firstName:'', lastName:'', email:'', password:'', age:''};
			$scope.appForm.$setPristine();
		};
	
		selfCustomer.sort = function(predicate) {
		      $scope.reverse = (selfCustomer.predicate === predicate) ? !$scope.reverse : false;
		      $scope.predicate = predicate;
		};
		
}]);
