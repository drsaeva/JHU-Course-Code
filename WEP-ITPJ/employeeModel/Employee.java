/**
 * Contains data on an employee stored in Name, Address, and Date objects
 * @author David Saeva
 */
package employeeModel;

public abstract class Employee {
	private Name empName;		// child Name object
	private Address empAdd;		// child Address object
	private Date empHireDate;	// child Date object
	
	/**
	 * Constructor, requires arguments to be passed in to set Name/Address/Date objects
	 * @param name String array of name parameters
	 * @param address String array of address parameters
	 * @param date	Int array of date parameters
	 */
	public Employee(String[] name, String[] address, int[] date) {
		setEmployeeName(name);
		setEmployeeAddress(address);
		setEmployeeHireDate(date);
	}
	
	// abstract method for returning Salary Information from subclasses - implemented in each subclass
	abstract double[] getSalaryInfo();
	
	// Make new Name object tied to this Employee object, passing in an array as an argument to the constructor
	private void setEmployeeName(String[] name) {
		this.empName = new Name(name);
	}
	
	// getter for name object
	public Name getEmployeeName() {
		return empName;
	}
	
	// Make new Address object tied to this Employee object, passing in an array as an argument to the constructor
	private void setEmployeeAddress(String[] address) {
		this.empAdd = new Address(address);
	}
	
	// getter for Address object
	public Address getEmployeeAdress() {
		return empAdd;
	}
	
	// Make new Date object tied to this Employee object, passing in an array as an argument to the constructor
	private void setEmployeeHireDate(int[] date) {
		this.empHireDate = new Date(date);
	}
	
	// getter for Date object
	public Date getEmployeeHireDate() {
		return empHireDate;
	}
	
}
