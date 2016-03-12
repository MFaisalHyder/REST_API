package rest_api.model;

import org.springframework.data.annotation.Id;
public class Customer {

	@Id
	private String id;	
	private String firstName;
	private String lastName;
	private int age;

	public Customer() {
	}

	public Customer(String fName, String lName, int age) {
		this.firstName = fName;
		this.lastName = lName;
		this.age = age;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	/*@Override
	public String toString() {
		return String.format("Customer [id=%s, firstName='%s', lastName='%s', age='%d']", id, firstName, lastName, age);
	}*/

}