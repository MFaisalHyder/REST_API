/*package rest_api.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rest_api.Application;
import rest_api.model.Customer;
import rest_api.repository.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class CustomerControllerTest {

	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Autowired
	public CustomerRepository mCustomerRepository;

	private RestTemplate testTemplate = new TestRestTemplate();

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateCustomerApi() throws JsonProcessingException {

		Map<String, Object> requestBody = new HashMap<String,Object>();
		requestBody.put("firstName", "Muhammad Faisal");
		requestBody.put("lastName", "Hyder");
		requestBody.put("age", 21);

		HttpHeaders requestHeader = new HttpHeaders();
		requestHeader.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody),requestHeader);
		
		Map<String, Object> apiResponse = 
				testTemplate.postForObject("http://192.168.0.100:8888/customer/insert",httpEntity,Map.class,Collections.EMPTY_MAP);
		assertNotNull(apiResponse);
		
		String message = apiResponse.get("message").toString();
		assertEquals("Developer hired Successfully",message);
		
		String customerId = ((Map<String,Object>) apiResponse.get("customer")).get("id").toString();
		//String customerId = ((HashMap<String,Object>) apiResponse.get("customer")).get("id").toString();
		assertNotNull(customerId);
		
		Customer customerFromDb = mCustomerRepository.findOne(customerId);
	    assertEquals("Muhammad Faisal", customerFromDb.getFirstName());
	    assertEquals("Hyder", customerFromDb.getLastName());	    
	    assertTrue(21 == customerFromDb.getAge());
	    
		mCustomerRepository.delete(customerId);
	}
	
	@Test
	public void testGetCustomerDetailsApi(){
		Customer mCustomer = new Customer("Syed Muhammad Saqib", "Haider", 29);
		mCustomerRepository.save(mCustomer);
		
		//String cId = mCustomer.getId();
		String firstName = mCustomer.getFirstName();
		
		Customer apiResponse = testTemplate.getForObject("http://192.168.0.100:8888/customer/name/"+firstName, Customer.class);
		
		assertNotNull(apiResponse);
		assertEquals(mCustomer.getId(),apiResponse.getId());
		assertEquals(mCustomer.getFirstName(),apiResponse.getFirstName());
		assertEquals(mCustomer.getLastName(),apiResponse.getLastName());
		assertTrue(mCustomer.getAge()==apiResponse.getAge());
		
		mCustomerRepository.delete(firstName);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testUpdateCustomerApi() throws JsonProcessingException{
		Customer mCustomer = new Customer("Syed Muhammad", "Sami Uddin", 19);
		mCustomerRepository.save(mCustomer);
		
		String cId = mCustomer.getId();
		
		Map<String, Object> requestBody = new HashMap<String,Object>();
		requestBody.put("firstName", "Markus");
		requestBody.put("lastName", "Damien");
		requestBody.put("age", 32);
		
		HttpHeaders requestHeader = new HttpHeaders();
		requestHeader.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> mHttpEntity = new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody),requestHeader);
		
		Map<String, Object> apiResponse =
				(Map)testTemplate.exchange("http://192.168.0.100:8888/customer/update/id/"+cId,HttpMethod.PUT,mHttpEntity,
						Map.class,Collections.EMPTY_MAP).getBody();
		
		assertNotNull(apiResponse);
		assertTrue(!apiResponse.isEmpty());
		
		String message = apiResponse.get("message").toString();
		assertEquals("Customer updated Successfully", message);
		
		Customer customerFromDB = mCustomerRepository.findOne(cId);		
		assertEquals(customerFromDB.getFirstName(),requestBody.get("firstName"));
		assertEquals(customerFromDB.getLastName(),requestBody.get("lastName"));
		assertTrue(customerFromDB.getAge()==Integer.parseInt(requestBody.get("age").toString()));
		
		mCustomerRepository.delete(cId);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteCustomerApi(){
		Customer mCustomer = new Customer("Travis", "Corneille", 34);
		mCustomerRepository.save(mCustomer);
		
		String cId = mCustomer.getId();
		
		testTemplate.delete("http://192.168.0.100:8888/customer/delete/id/"+cId,Collections.EMPTY_MAP);
		//Customer customerFromDB = mCustomerRepository.findOne(cId);
		//assertNull(customerFromDB);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllCustomersApi(){
		Customer mCustomer1 = new Customer("Robert", "Brownee", 52);
		mCustomerRepository.save(mCustomer1);
		Customer mCustomer2= new Customer("Travis", "Corneille", 34);
		mCustomerRepository.save(mCustomer2);
		Customer mCustomer3 = new Customer("Syed Muhammad", "Sami Uddin", 19);
		mCustomerRepository.save(mCustomer3);
		
		Map<String, Object> apiResponse = testTemplate.getForObject("http://192.168.0.100:8888/customer", Map.class);
		
		int totalCustomers = Integer.parseInt(apiResponse.get("totalCustomers").toString());
		assertTrue(totalCustomers==3);
		
		List<Map<String,Object>> customersList = (List<Map<String,Object>>)apiResponse.get("customers");
		assertTrue(customersList.size()==3);
		
		mCustomerRepository.delete(mCustomer1.getId());
		mCustomerRepository.delete(mCustomer2.getId());
		mCustomerRepository.delete(mCustomer3.getId());
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	  public void testGetAllCustomersApi(){
	    //Add some test data for the API
	    Customer mCustomer1 = new Customer("Mark Travis", "Browne", 32);
	    mCustomerRepository.save(mCustomer1);
	    
	    Customer mCustomer2 = new Customer("Chris Gayle", "Carribean", 26);
	    mCustomerRepository.save(mCustomer2);
	    
	    //Invoke the API
		Map<String, Object> apiResponse = testTemplate.getForObject("http://192.168.0.100:8888/customer", Map.class);
	    
	    //Assert the response from the API
	    int totalCustomer = Integer.parseInt(apiResponse.get("totalCustomers").toString());
	    assertTrue(totalCustomer == mCustomerRepository.count());
	    
	    List<Map<String, Object>> customersList = (List<Map<String, Object>>)apiResponse.get("customers");
	    assertTrue(customersList.size() == mCustomerRepository.count());
	    
	    //Delete the test data created
	    mCustomerRepository.delete(mCustomer1.getId());
	    mCustomerRepository.delete(mCustomer2.getId());
	  }
	  

}*/