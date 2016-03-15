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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import rest_api.model.Customer;
import rest_api.repository.CustomerRepository;


@RestController
//@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerRepository mCustomerRepository;
	
	 @RequestMapping(value="/Admin", method=RequestMethod.GET)
	    public ModelAndView panel(ModelAndView mav){
	    	return new ModelAndView("CustomerManagement");
	    	}
	//============================================================================================//
	// All Customers in DataBase
	/*@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getAllCustomers() {
		List<Customer> objectCustomer = mCustomerRepository.findAll();
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		if(objectCustomer.size()>0){
			response.put("totalCustomers", objectCustomer.size());
			response.put("customers", objectCustomer);
		}else{
			response.put("result", objectCustomer.size() + " customer found");
		}
		return response;
	}*/
	
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> objectCustomer = mCustomerRepository.findAll();
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		if(objectCustomer.size()>0){
			response.put("totalCustomers", objectCustomer.size());
			response.put("customers", objectCustomer);
		}else{
			response.put("result", objectCustomer.size() + " customer found");
		}
		return new ResponseEntity<List<Customer>>(objectCustomer,HttpStatus.OK);
	}
	//============================================================================================//

	
	//============================================================================================//
	// Get Customer with given ID
	@RequestMapping(method = RequestMethod.GET, value = "/customer/id/{customerID}")
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
	@RequestMapping(method = RequestMethod.GET, value = "/customer/firstname/{firstName}")
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
	@RequestMapping(method=RequestMethod.GET, value = "/customer/lastname/{lastName}")
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
	@RequestMapping(method=RequestMethod.GET, value = "/customer/age/{age}")
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
	@RequestMapping(method = RequestMethod.POST, path = "/customer/insert")
	public Map<String, Object> newCustomer(@RequestBody Map<String, Object> customerMap) {

		Customer mCustomer = new Customer(customerMap.get("firstName").toString(),
				customerMap.get("lastName").toString(), Integer.parseInt(customerMap.get("age").toString()));
		mCustomerRepository.save(mCustomer);

		Map<String, Object> successMessage = new LinkedHashMap<String, Object>();
		successMessage.put("message", "Developer hired Successfully");
		successMessage.put("customer", mCustomerRepository.save(mCustomer));

		return successMessage;
	}
	//===========================================================================================//

	//============================================================================================//
	// Update record of a Customer given their ID
	@RequestMapping(method = RequestMethod.PUT, value = "/customer/update/id/{customerID}")
	public Map<String, Object> updCustomer(@PathVariable("customerID") String customerID,
			@RequestBody Map<String, Object> customerMap) {
		Customer mCustomer = new Customer(customerMap.get("firstName").toString(),
				customerMap.get("lastName").toString(), Integer.parseInt(customerMap.get("age").toString()));
		mCustomer.setId(customerID);

		Map<String, Object> successMessage = new LinkedHashMap<String, Object>();
		successMessage.put("message", "Customer updated Successfully");
		successMessage.put("customer", mCustomerRepository.save(mCustomer));

		return successMessage;
	}
	//============================================================================================//

	
	//============================================================================================//
	// Delete a Customer given his ID	
	@RequestMapping(value = "/customer/delete/id/{id}", method =  {RequestMethod.DELETE, RequestMethod.GET} )
	@ResponseBody
	public Map<String,String> deleteCustomerById( @PathVariable("id") String id){
		mCustomerRepository.delete(id);
		
		Map<String,String> response = new HashMap<String,String>();
		response.put("report", "Customer removed");
		return response;
	}
	//============================================================================================//
	
	/*//============================================================================================//
		// Delete a Customer given his ID	
		@RequestMapping(method = RequestMethod.GET, value = "/delete/name/{firstNname}")
		public Map<String,String> deleteCustomerByName( @PathVariable("firstName") String name){
			mCustomerRepository.deleteByName(name);
			Map<String,String> message = new HashMap<String,String>();
			message.put("report", "Customer removed");
			return message;
			}
		//============================================================================================//
*/

}