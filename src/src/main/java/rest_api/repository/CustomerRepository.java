package rest_api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import rest_api.model.Customer;

//@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
public interface CustomerRepository extends MongoRepository<Customer, String> {

	public List<Customer> findByFirstName(@Param("firstName") String firstName);

	public List<Customer> findByLastName(@Param("lastName") String lastName);
	 
	public List<Customer> findByAge(@Param("age") int age);
	
	public void deleteById(@Param("id") String id);
	
//	public void deleteByName(@Param("firstName") String name);

}
