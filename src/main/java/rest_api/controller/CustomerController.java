package rest_api.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rest_api.model.Customer;
import rest_api.repository.CustomerRepository;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerRepository mCustomerRepository;
	
	//============================================================================================//
	// All Customers in DataBase
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getAllCustomers() {
		List<Customer> objectCustomer = mCustomerRepository.findAll();
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		if(objectCustomer.size()>0){
			response.put("totalCustomers", objectCustomer.size());
			response.put("customers", objectCustomer);
		}else{
			response.put("result", objectCustomer.size() + " customer found");
		}
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	//============================================================================================//
	
	//============================================================================================//
	// Get Customer with given ID
	@RequestMapping(method = RequestMethod.GET, value = "/id/{customerID}")
	public Map<String,Object> getCustomerDetails(@PathVariable("customerID") String customerID) {
		Customer searchedByID = mCustomerRepository.findOne(customerID);
		Map<String,Object> response = new HashMap<String,Object>();
		
		if(searchedByID!=null){
			response.put("customer",searchedByID);
		}else{
			response.put("result","Entry with "+ customerID +" not found");			
		}
		
		return response;
	}
	//============================================================================================//

	
	//============================================================================================//
	// Get Customer given their First Name
	@RequestMapping(method = RequestMethod.GET, value = "/firstname/{firstName}")
	public Map<String,Object> getCustomerByFirstName(@PathVariable("firstName") String firstName) {
		List<Customer> searchedByFirstName = mCustomerRepository.findByFirstName(firstName);
		Map<String,Object> responseSearchedByFirstName = new HashMap<String, Object>();
		
		if(searchedByFirstName.size()>0){
			responseSearchedByFirstName.put("total", searchedByFirstName.size());
			responseSearchedByFirstName.put("customers", searchedByFirstName);
		}else{
			responseSearchedByFirstName.put("result", searchedByFirstName.size() + " match found");			
		}
		
		return responseSearchedByFirstName;
	}
	//============================================================================================//
	
	
	//============================================================================================//
	//Get Customer given their Last Name
	@RequestMapping(method=RequestMethod.GET, value = "/lastname/{lastName}")
	public Map<String,Object> getCustomerByLastName(@PathVariable("lastName") String lastName){		
		
		List<Customer> searchedByLastName = mCustomerRepository.findByLastName(lastName);
		Map<String,Object> response = new HashMap<String, Object>();
		
		if(searchedByLastName.size()>0){
			response.put("total", searchedByLastName.size());
			response.put("customers", searchedByLastName);
		}else{
			response.put("result", searchedByLastName.size() + " match found");	
		}
		return response;
	}
	//============================================================================================//

	
	//============================================================================================//
	//Get Customer given their Age
	@RequestMapping(method=RequestMethod.GET, value = "/age/{age}")
	public Map<String,Object> getCustomerByAge(@PathVariable("age") int age){
		List<Customer> searchedByAge = mCustomerRepository.findByAge(age);		
		Map<String,Object> response = new HashMap<String, Object>();
		
		if(searchedByAge.size()>0){
			response.put("total", searchedByAge.size());
			response.put("customers", searchedByAge);
		}else{
			response.put("result", searchedByAge.size()+ " match found");
		}
		return response;
	}
	//============================================================================================//	
	
	//============================================================================================//
	//Insert record
	@RequestMapping(method = RequestMethod.POST, value = "/insert/")
	public ResponseEntity<Void> newCustomer(@RequestBody Customer customerMap) {
				 
		String firstName = customerMap.getFirstName().toString();
		String lastName = customerMap.getLastName().toString();
		int age = customerMap.getAge();
		Customer mCustomer = new Customer(firstName,lastName,age);
		
		mCustomerRepository.save(mCustomer);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	
	//===========================================================================================//

	//============================================================================================//
	// Update record of a Customer given their ID
	@RequestMapping(value="/update/id/{id}",  method = RequestMethod.PUT)
	public ResponseEntity<Customer> updateCustomer(@PathVariable ("id") String id, @RequestBody Customer customer ){
		
		Customer customerObj = mCustomerRepository.findOne(id);
		
		if(customerObj==null){
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
		
		String firstName = customer.getFirstName().toString();
		String lastName = customer.getLastName().toString();
		int age = customer.getAge();
		
		customerObj = new Customer(firstName,lastName,age);
		customerObj.setId(id);
				
		mCustomerRepository.save(customerObj);
		
		return new ResponseEntity<Customer>(HttpStatus.OK);
	}
	//============================================================================================//

	
	//============================================================================================//
	// Delete a Customer given his ID	
	@RequestMapping(value = "/delete/id/{id}", method = RequestMethod.DELETE )
	public Map<String,String> deleteCustomerById( @PathVariable("id") String id){
		mCustomerRepository.delete(id);
		
		Map<String,String> response = new HashMap<String,String>();
		response.put("report", "Customer removed");
		return response;
	}
	//============================================================================================//
}