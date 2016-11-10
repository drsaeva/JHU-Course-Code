package contactList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class represents the driver for the MyContacts program. Please see the main method's javadoc comment for
 * 	a description of the execution of the code at runtime.
 * 
 * @author David Saeva
 * @param contactList Hashmap of Contact objects keyed to a String (contact name in "lastname firstname" format)
 * @param orderOfContacts LinkedList of Strings (contact name in "lastname firstname" format)
 *
 */
public class MyContacts {
	
	static HashMap<String, Contact> contactList;
	static LinkedList<String> orderOfContacts;
	
	/**
	 * Main method for MyContacts program. 
	 * Calls getFileFromUser in order to retrieve the file path of the '.csv'-formatted file in which 
	 * 	contact information is stored. 
	 * 
	 * Reads file via a BufferedReader adding each line's data to the contactList HashMap and the name 
	 * 	from that data (in "lastname firstname" format) to the orderOfContacts LinkedList. Once all 
	 * 	contacts have been added, the LinkedList is sorted via Collections.Sort() method, which sorts a 
	 * 	LinkedList<String> collection alphabetically. 
	 *  
	 * Next, user input is taken on whether to add a new contact, remove an existing contact, or print 
	 *  the current contact list. Invalid inputs are ignored, querying the user for a new input until a 
	 *  valid one is provided. Adding a contact queries the user to provide information for that new contact
	 *  in order requested, providing errors and program closure for invalid input. Removing a contact
	 *  queries a user for the contact's name, with similar input checking. Printing the current contact list
	 *  prints all current contacts in alphabetical order by last name to standard out.
	 *  
	 * Finally, if the user modified the contact list, the contact list file is overwritten with the new data.
	 *  
	 * @param args unused
	 */
	public static void main(String[] args) {
		BufferedReader r = null;
		Scanner s = new Scanner(System.in);
		File f = getFileFromUser(s);
		try {
			r = new BufferedReader(new FileReader(f));
			String line;
			while ((line = r.readLine()) != null) {
				String[] lineArr = line.split(",");
				contactList.put(lineArr[0], new Contact(lineArr));
				orderOfContacts.add(lineArr[0]);
			}
			
			Collections.sort(orderOfContacts);
			r.close();
			
			String user = "";
			System.out.println("Please state whether you'd like to add a new contact, "
					+ "remove an existing contact, or display your contacts (A/R/D): ");
				while (s.hasNext()) {
					try {
						user = s.nextLine();
					} catch (java.util.InputMismatchException e) {
						System.out.println("Please provide an answer, 'A' or 'R' or 'D': ");
					}
					
					if (user.equals("A") || user.equals("R") || user.equals("D")) {
						break;
					} else {
						System.out.println("Please provide an answer, 'A' or 'R' or 'D': ");
					}
				}
				
				if (user.equals("A")) {
						Contact c = addNewContact(s);
						contactList.put(c.name, c);
						orderOfContacts.add(c.name);
						Collections.sort(orderOfContacts);
				} else if (user.equals("R")) {
					String name = getNameForRemoval(s);
					for (int i=0; i<orderOfContacts.size(); i++) {
						if (orderOfContacts.get(i).equals(name)) {
							contactList.remove(name);
							orderOfContacts.remove(i);
						} else {
							System.out.println("Invalid input - name not found in contacts.");
							System.exit(0);
						}
					}
				} else {
					printContacts();
				}
			
			if (user.equals("R") || user.equals("A")) {
				BufferedWriter w = new BufferedWriter(new FileWriter(f));
				for (int i=0; i<orderOfContacts.size(); i++) {
					Contact c = contactList.get(orderOfContacts.get(i));
					if (c != null) {
						w.write(c.name + "," + c.phoneNum + "," + c.email);
					}
					
				}
				w.close();
			}
				
			System.out.println("Contacts program ending. If you updated the contact list, your file is now updated. Thank you!");
				
				
		} catch (java.io.IOException e) {
			System.out.println("invalid input, please provide a valid input file location and contents");
		}
	}
	
	public static void printContacts() {
		System.out.println("Last Name, First Name" + "\t" + "Phone Number" + "\t" + "Email Address");
		System.out.println("---------------------" + "\t" + "------------" + "\t" + "-------------");
		for (int i=0; i<orderOfContacts.size(); i++) {
			Contact c = contactList.get(orderOfContacts.get(i));
			System.out.println(c.lastName + ", " + c.firstName + "\t" + c.phoneNum + "\t" + c.email);
		}
	}
	
	/**
	 * Queries the user for a name to remove from the contact list, requesting last and full name individually.
	 * 	Concatenates those two strings into "LastName FirstName" format and returns the resulting String
	 * @param s Scanner getting user input
	 * @return String concatenation of name for removal
	 */
	public static String getNameForRemoval(Scanner s) {
		System.out.println("Please provide the information as requested for the contact you wish to remove. Invalid inputs"
				+ " will result the end of this program with no saved data.");
		
		System.out.println("Last Name (alphabetic characters only): ");
		String ln = s.nextLine();
		System.out.println("First Name (alphabetic characters only): ");
		String fn = s.nextLine();
		String fulln = ln + " " + fn;
		checkForInvalid(fulln, true);
		
		return fn;
	}
	
	/**
	 * Queries the user for data for a new contact being added to the list, returning that data in the form of a new
	 * 	Contact object. Invalid inputs (e.g. alphabetical characters in a phone number) are checked for with the 
	 * 	checkForInvalid() method, yielding error messages and program exits in those scenarios.
	 * @param s Scanner getting user input
	 * @return Contact contact data provided by user.
	 */
	public static Contact addNewContact(Scanner s) {
		System.out.println("Please provide the information as requested for your new contact. Invalid inputs"
				+ " will result the end of this program with no saved data.");
		
		System.out.println("Last Name (alphabetic characters only): ");
		String ln = s.nextLine();
		System.out.println("First Name (alphabetic characters only): ");
		String fn = s.nextLine();
		String fulln = ln + " " + fn;
		checkForInvalid(fulln, true);
		
		System.out.println("Phone Number (9 digits no spaces/nonnumeric characters, OR 'none'): ");
		String pn = s.nextLine();
		checkForInvalid(pn, false);
		
		System.out.println("Email Address (email@email.com, OR 'none'): ");
		String em = s.nextLine();
		checkForInvalid(em, true);
		
		
		String[] details = {fulln, pn, em};
		return new Contact(details);
	}
	
	/**
	 * Queries the user the location of their contact information data file. Checks the file type
	 * 	to ensure that it is formatted as a '.csv' file, providing an error message and program exit
	 * 	in the event that it is not. Returns a file object otherwise for passage into reader/writer
	 * 	classes.
	 * @param s Scanner getting user input
	 * @return File object containing the user's contact list data
	 */
	public static File getFileFromUser(Scanner s) {
		System.out.println("Please provide the path to the contacts file you wish to access: ");
		String path = s.nextLine();
		if (!path.contains("csv")) {
			System.out.println("invalid file type input, your contacts file should be a '.csv' file");
			return null;
		} else {
		File f = new File(path);
		return f;
		}
	}
	
	/**
	 * Checks an input string to see if it contains invalid characters. Phone numbers should convert
	 * 	to an integer value of 9 digits and contain no alphabetical characters, while emails and names
	 * 	should not. Displays an error message and closes the program in the event an invalid input
	 * 	is detected.
	 * @param name Input String being checked
	 * @param alpha boolean parameter describing whether the input String contains alphabetical characters
	 */
	public static void checkForInvalid(String name, boolean alpha) {
		int i = 1098765432;
		try {
			i = Integer.valueOf(name);
		} catch(java.lang.NumberFormatException e) {}
		
		if (i == 1098765432 && !alpha && !name.equals("none")) {
			System.out.println("Invalid phone number input, exiting program.");
			System.exit(0);
		} else if (i < 1098765432 && alpha) {
			System.out.println("Invalid name or email input, exiting program.");
			System.exit(0);
		}
	}
	
}
