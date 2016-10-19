package employeeModel;

public class Name {
	private String firstName;
	private String lastName;
	private String middleName;
	
	public Name(String[] args) {
		
		if (checkInputData(args)) System.exit(0);
		
		setLastName(args[0]);
		setFirstName(args[1]);
		
		if (args.length > 2) {
			setMiddleName(args[2]);
		}
	}
	
	private boolean checkInputData(String[] args) {
		if (args.length > 3 || args.length < 2 ) {
			System.out.println("Invalid number of name components - please enter the "
					+ "name as specified.");
			return true;
		}
		
		for (int i=0; i<args.length; i++) {
			if (args[i].equals("") || args[i].equals(" ")) {
				System.out.println("Invalid data provided - blank fields cannot be used. "
						+ "Please provide a full name as specified.");
				return true;
			}
		}
		
		return false;
	}
	
	private void setFirstName(String first) {
		this.firstName = first;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	private void setLastName(String last) {
		this.lastName = last;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	private void setMiddleName (String middle) {
		this.middleName = middle;
	}
	
	public String getMiddleName() {
		return middleName;
	}
}
