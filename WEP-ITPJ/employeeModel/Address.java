/**
 * Contains data pertaining to an address for, but not limited to, use with an Employee object
 * @author David Saeva
 */
package employeeModel;

public class Address {
	
	private String streetAddress;	// street address
	private String city;			// city for address
	private String state;			// 2-letter state abbreviation for address
	private String zipCode;			// 5-digit zip code for address
	
	/**
	 * Constructor for Address, takes String array parameter in {streetAddress, city, state, zipCode} format
	 * @param args String array containing input parameters
	 */
	public Address(String[] args) {
		
		if (checkInputData(args)) System.exit(0);
		
		setStreetAddress(args[0]);
		setCity(args[1]);
		setState(args[2]);
		setZipCode(args[3]);
	}
	
	/**
	 * Checks input array to ensure it has the correct number of elements in order to generate a full Address object
	 * 	Prints error message if it lacks something.
	 * @param args	Input String array containing information required to generate Address object
	 * @return	true If the String array contains more or less than 4 elements, or if any fields are blank
	 * 			false Otherwise
	 */
	private boolean checkInputData(String[] args) {
		if (args.length != 4) {
			System.out.println("Invalid number of address components - please enter the "
					+ "address as specified.");
			return true;
		}
		
		for (int i=0; i<args.length; i++) {
			if (args[i].equals("") || args[i].equals(" ")) {
				System.out.println("Invalid data provided - blank fields cannot be used. "
						+ "Please provide a full address as specified.");
				return true;
			}
		}
		
		return false;
	}
	
	//setter for street address
	private void setStreetAddress(String address) {
		this.streetAddress = address;
	}
	
	//getter for street address
	public String getStreetAddress() {
		return streetAddress;
	}
	
	//setter for city
	private void setCity (String city) {
		this.city = city;
	}
	
	//getter for city
	public String getCity() {
		return city;
	}
	
	//setter for state
	private void setState (String state) {
		this.state = state;
	}
	
	//getter for stat
	public String getState() {
		return state;
	}
	
	//setter for zip code
	private void setZipCode (String zipCode) {
		this.zipCode = zipCode;
	}
	
	//getter for zip code
	public String getZipCode() {
		return zipCode;
	}
	
}
