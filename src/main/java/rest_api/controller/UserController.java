package rest_api.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rest_api.model.User;
import rest_api.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository mUserRepository;
	
	//============================================================================================//
	// All Users in DataBase
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getAllUsers() {
		List<User> objectUser = mUserRepository.findAll();
		Map<String, Object> response = new LinkedHashMap<String, Object>();

		if (objectUser.size() > 0) {
			response.put("totalUsers", objectUser.size());
			response.put("users", objectUser);
		} else {
			response.put("result", objectUser.size() + " customer found");
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	//============================================================================================//	
	
	//============================================================================================//
	//Get User given their User Name
	@RequestMapping(method=RequestMethod.GET, value = "/userName/{userName}")
	public Map<String,Object> getUserByUserName(@PathVariable("userName") String userName){		
		
		User searchedByUserName = mUserRepository.findByUserName(userName);
		Map<String,Object> response = new HashMap<String, Object>();
		
		if(searchedByUserName!=null){			
			response.put("user", searchedByUserName);
		}else{
			response.put("result", searchedByUserName + ", no such user found");	
		}
		return response;
	}
	//============================================================================================//
	
	
	//============================================================================================//
	// Get User given their First Name
	@RequestMapping(method = RequestMethod.GET, value = "/firstname/{firstName}")
	public Map<String,Object> getUserByFirstName(@PathVariable("firstName") String firstName) {
		List<User> searchedByFirstName = mUserRepository.findByFirstName(firstName);
		Map<String,Object> responseSearchedByFirstName = new HashMap<String, Object>();
		
		if(searchedByFirstName.size()>0){
			responseSearchedByFirstName.put("total", searchedByFirstName.size());
			responseSearchedByFirstName.put("users", searchedByFirstName);
		}else{
			responseSearchedByFirstName.put("result", searchedByFirstName.size() + " match found");			
		}
		
		return responseSearchedByFirstName;
	}
	//============================================================================================//
	
	
	//============================================================================================//
	//Get User given their Last Name
	@RequestMapping(method=RequestMethod.GET, value = "/lastname/{lastName}")
	public Map<String,Object> getUserByLastName(@PathVariable("lastName") String lastName){		
		
		List<User> searchedByLastName = mUserRepository.findByLastName(lastName);
		Map<String,Object> response = new HashMap<String, Object>();
		
		if(searchedByLastName.size()>0){
			response.put("total", searchedByLastName.size());
			response.put("users", searchedByLastName);
		}else{
			response.put("result", searchedByLastName.size() + " match found");	
		}
		return response;
	}
	//============================================================================================//	
	
	
	//============================================================================================//
	//Insert record
	@RequestMapping(method = RequestMethod.POST, value = "/insertUser/")
	public ResponseEntity<Void> newCustomer(@RequestBody User userMap) {

		String userName = userMap.getUserName().toString();

		User mUserName = mUserRepository.findByUserName(userName);

		if (mUserName != null) {
			// String searchedUserName = mUserName.getUserName().toString();
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		String firstName = userMap.getFirstName().toString();
		String lastName = userMap.getLastName().toString();
		String email = userMap.getEmail().toString();
		String pwd = userMap.getPassword().toString();
		int age = userMap.getAge();

		User mUser = new User(userName, firstName, lastName, email, pwd, age);

		mUserRepository.save(mUser); // CrudRepository

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	//============================================================================================//
	
	//============================================================================================//
	//Login		
	@RequestMapping(method = RequestMethod.POST, value = "/loginCredentials/", consumes = {"application/x-www-form-urlencoded" })
	public ModelAndView loginConfirm(HttpServletRequest request, HttpServletResponse res, RedirectAttributes rA) throws Exception {

		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("password");

		User userFound = mUserRepository.findByUserName(userName);

		if (userFound == null)
			throw new NullPointerException(userName+" not Found");

		if (!(userFound.equals(null))) {

			if (userFound.getUserName().equalsIgnoreCase("admin")) {
				if (userPassword.equals(userFound.getPassword())) {
					return new ModelAndView("redirect:/admin");
				} else {
					rA.addFlashAttribute("message", "Wrong password, sure you are Admin ?");
					return new ModelAndView("redirect:/login");
				}
			} else {
				if (userFound.getPassword().equals(userPassword)) {
					rA.addFlashAttribute("userName", userFound.getUserName());
					//return new ModelAndView("User", "userName", userFound.getUserName());
					return new ModelAndView("redirect:/userpanel");

				} else {
					// return new ModelAndView("Login","error", "Wrong password, sure you are " + userName +" ?");
					rA.addFlashAttribute("message", "Wrong password, sure you are " + userName + " ?");
					return new ModelAndView("redirect:/login");
				}
			}
		} else {
			return new ModelAndView("Login", "message", "User not found");
		}
	}		
	//============================================================================================//
	
	//============================================================================================//
	// Update record of a Customer given their ID
	@RequestMapping(value="/updateUser/id/{id}",  method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable ("id") String id, @RequestBody User user ){
		
		User userObj = mUserRepository.findOne(id);
		
		if(userObj==null){
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
		String uName = user.getUserName().toString();
		String firstName = user.getFirstName().toString();
		String lastName = user.getLastName().toString();
		String email = user.getEmail().toString();
		String password = user.getPassword().toString();
		int age = user.getAge();
		
		userObj = new User(uName, firstName,lastName,email, password, age);
		userObj.setId(id);
				
		mUserRepository.save(userObj);
		
		return new ResponseEntity<User>(HttpStatus.OK);
	}
	//============================================================================================//

	
	//============================================================================================//
	// Delete a User given his ID	
	@RequestMapping(value = "/delete/id/{id}", method = RequestMethod.DELETE )
	public Map<String,String> deleteUserById( @PathVariable("id") String id){
		mUserRepository.delete(id);
		
		Map<String,String> response = new HashMap<String,String>();
		response.put("report", "User removed");
		return response;
	}
	//============================================================================================//	
}
