'use strict';

App.controller('CustomerController', ['$scope', 'CustomerService', function($scope, CustomerService){	
		var selfCustomer = this;
		selfCustomer.customer={id:null, firstName:'', lastName:'', age:''};
		selfCustomer.customers=[];
		
		selfCustomer.getAllCustomers = function(){
			CustomerService.getAllCustomers()
			.then(
					function(d){
						selfCustomer.customers = d;						
					},
					function(errResponse){
						console.error('Error while fetching Customers');
					}					
			);			
		};
		
		selfCustomer.createCustomer = function(customer){
			CustomerService.createCustomer(customer)
			.then(
					selfCustomer.getAllCustomers,
					function(errResponse){
						console.error('Error while creating Customer');
					}					
			);
		};
		
		selfCustomer.updateCustomer = function(id){
			CustomerService.updateCustomer(id)
			.then(
					selfCustomer.getAllCustomers,
					function(errResponse){
						console.error('Error while updating Customer');
					}
					
			);
		};
		
		selfCustomer.deleteCustomer = function(id){
			CustomerService.deleteCustomer(id)
			.then(
					selfCustomer.getAllCustomers,
					function(errResponse){
						console.error('Error while deleting Customer');
					}
			);
		};
	
		selfCustomer.getAllCustomers();
		
		selfCustomer.submit = function() {
             if(selfCustomer.customer.id==null){
                 console.log('Saving New Customer', selfCustomer.customer);    
                 selfCustomer.createCustomer(selfCustomer.customer);
             }else{
                 selfCustomer.updateCustomer(selfCustomer.customer.id);
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
			console.log('Customer with id to be deleted', id);
			if(selfCustomer.customer.id === id){
				selfCustomer.reset();
			}
			selfCustomer.deleteCustomer(id);
		}
		
		selfCustomer.reset = function(){
			selfCustomer.customer={id:null, firstName:'', lastName:'', age:''};
			$scope.appForm.$setPristine();
		};
}]);
