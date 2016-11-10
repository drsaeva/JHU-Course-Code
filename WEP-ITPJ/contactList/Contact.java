package contactList;

public class Contact {
	public String name;
	public String lastName;
	public String firstName;
	public String email;
	public String phoneNum;
	
	public Contact(String[] line) {
		this.name = line[0];
		this.phoneNum = line[1];
		this.email = line[2];
		makeFirstLast(this.name);
	}
	
	private void makeFirstLast(String name) {
		String[] fL = name.split(" ");
		this.lastName = fL[0];
		this.firstName = fL[1];
	}
}
