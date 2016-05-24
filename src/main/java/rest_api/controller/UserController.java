package rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import rest_api.model.User;
import rest_api.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository mUserRepository;
	
	//============================================================================================//
	//Insert record
		@RequestMapping(method = RequestMethod.POST, value = "/insertUser/")
		public ResponseEntity<Void> newCustomer(@RequestBody User userMap) {
					
			String userName = userMap.getUserName().toString();
			
			User mUserName = mUserRepository.findByUserName(userName);
			
			if(mUserName != null){
				//String searchedUserName = mUserName.getUserName().toString();
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
						
			String firstName = userMap.getFirstName().toString();
			String lastName = userMap.getLastName().toString();
			String email = userMap.getEmail().toString();
			String pwd = userMap.getPassword().toString();
			int age = userMap.getAge();		
			
			User mUser = new User(userName,firstName,lastName,email,pwd,age);
			
			mUserRepository.save(mUser); //CrudRepository

			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
	//============================================================================================//
		
	//============================================================================================//
	//Login
		
		@RequestMapping(method = RequestMethod.GET, value = "/loginCredentials")
		public ModelAndView loginConfirm(){
			return new ModelAndView("");
		}
		
	//============================================================================================//
		
		
		
}
