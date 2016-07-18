package rest_api.model;

import org.springframework.data.annotation.Id;
public class User {

	@Id
	private String id;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private int age;
	
	public User() {
	}

	public User(String uName, String fName, String lName, String email, String password, int age) {
		this.userName = uName;
		this.firstName = fName;
		this.lastName = lName;
		this.email = email;
		this.password = password;
		this.age = age;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}
}