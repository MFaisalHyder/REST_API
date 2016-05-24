package rest_api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import rest_api.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	public User findByUserName(@Param("userName") String userName);
	
	public List<User> findByFirstName(@Param("firstName") String firstName);

	public List<User> findByLastName(@Param("lastName") String lastName);
	 
	public List<User> findByAge(@Param("age") int age);
	
}