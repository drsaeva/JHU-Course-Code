package employeeModel;

public class EmployeePayModeling {
	
	public static void main(String[] args) {
	
		Employee[] newEmployees = new Employee[3];	// Employee array for 3 different employee pay tests
		
		//Salaried employee info
		String[] salName = {"Arbuckle", "John", "T"};
		String[] salAdd = {"123 Main St", "Washington", "DC", "20001"};
		int[] salHire = {10, 21, 2010};
		double sal = 62500.00;
		newEmployees[0] = new SalariedEmployee(salName, salAdd, salHire, sal);
			
		//Overtime employee info
		String[] otName = {"Morgensen", "Tammy"};
		String[] otAdd = {"567 Fake Blvd", "Baltimore", "MD", "21217"};
		int[] otHire = {3, 7, 2012};
		double[] otRateHours = {22.75, 48.5};
		newEmployees[1] = new HourlyEmployee(otName, otAdd, otHire, otRateHours);
			
		//Non-OT hourly employee info
		String[] hrName = {"Pettibaker", "Steve", "Wilson"};
		String[] hrAdd = {"9 Major Ave", "Dulles", "VA", "20104" };
		int[] hrHire = {9, 30, 2015};
		double[] hrRateHours = {13.50, 28};
		newEmployees[2] = new HourlyEmployee(hrName, hrAdd, hrHire, hrRateHours);

		System.out.println("\nLets welcome our new employees! \n");
		
		for (int i=0; i<newEmployees.length; i++) {				// for each of the new employees...
			int empNum = i+1;
			System.out.println("Employee #"+empNum +": " );
			printEmployeeName(newEmployees[i]);				// print name 
			printEmployeeAddress(newEmployees[i]);		// print address 
			printEmployeeHireDate(newEmployees[i]);		// print hire date 
			printEmployeeSalaryInfo(newEmployees[i]);
		}
		
	}
	
	/**
	 * Encapsulates print code from priorly-implemented EmployeeModelingProgram class
	 * Stores Name object for the passed-in Employee locally, and prints their name with
	 * 	a middle name if it exists
	 * @param e Employee
	 */
	private static void printEmployeeName(Employee e) {
		Name a = e.getEmployeeName();				// temp assign Name object for shorter code referencing
		if (a.getMiddleName() == null) {						// if the middle name param is null, print only first/last
			System.out.println(a.getFirstName()+" "
					+a.getLastName());
		} else {												// otherwise print the name including the middle name/initial
			System.out.println(a.getFirstName()+" "
					+ a.getMiddleName()+" "+a.getLastName());
		}
	}
	
	/**
	 * Encapsulates print code from priorly-implemented EmployeeModelingProgram class
	 * Stores Address object for the passed-in Employee locally, and prints their address 
	 * @param e Employee
	 */
	private static void printEmployeeAddress(Employee e) {
		Address b = e.getEmployeeAdress();		// temp assign Address object for quick code ref
		System.out.println(b.getStreetAddress()+"\n" 			// print out Employee address in postal format
				+b.getCity()+", "+b.getState()+" "+b.getZipCode());
	}
	
	/**
	 * Encapsulates print code from priorly-implemented EmployeeModelingProgram class
	 * Stores Date object for the passed-in Employee locally, and prints their date of hire
	 * @param e Employee
	 */
	private static void printEmployeeHireDate(Employee e) {
		Date c = e.getEmployeeHireDate();			// temp assign Date object for quick code ref
		System.out.println("Hired on: "							// print out Employee hire date in mm/dd/yyyy format
				+c.getFullHiringDate()+"\n");
	}
	
	/**
	 * Acquires salary info and Name object from passed in Employee, storing both locally
	 * Prints yearly salary for a SalariedEmployee, and yearly salary, hours worked, hourly 
	 * 	rate, and weekly salary for an HourlyEmployee
	 * @param e Employee
	 */
	private static void printEmployeeSalaryInfo(Employee e) {
		double[] salInfo = e.getSalaryInfo();			// temp assign Employee's salary info to double array
		Name a = e.getEmployeeName();							// temp assign Name object for shorter code referencing
		
		if (salInfo.length > 1) {									// salary info array longer than 1 (not just yearly salary), print hourly info
			System.out.println(a.getFirstName() + " makes " + salInfo[0] + " per year, assuming they work all 51 weeks.");
			System.out.println("They work " + salInfo[1] + " hours per week, at a rate of " + salInfo[2] + " per hour\n" 
					+ "for a net weekly salary of " + salInfo[3] + ". Hours over 40 are paid at 1.5 the normal rate.");
			
		} else {																	// salary info array only contains yearly salary
			System.out.println(a.getFirstName() + " makes " + salInfo[0] + " per year - they are on salary, so they get\n"
					+ "paid at this rate no matter how many hours they work per week");
		}
		System.out.println("\n");
	}
	
	
}
