/**
 * Class Date - container for employee hiring day, month, year, and full hiring date
 * @author David Saeva
 *
 */
package employeeModel;

public class Date {

	private int hireMonth;			// hire month in int format	
	private int hireDay;			// hire day in int format
	private int hireYear;			// hire year in YYYY int format
	private String fullHiringDate;	// full hire date in MM/DD/YYYY format
	
	/**
	 * Sets hiring date parameters based on the values in the passed-in int array
	 * @param args Int array containing month, date, and year values
	 */
	public Date(int[] args) {
		
		if (checkInputData(args)) System.exit(0);
		
		setHireMonth(args[0]);
		setHireDay(args[1]);
		setHireYear(args[2]);
		setHiringDate();
	}
	
	/**
	 * Checks input int array for proper amount, unitialized values. Prints error message in that scenario.
	 * @param args	Input int array
	 * @return	true If invalid input is found
	 * 			false Otherwise
	 */
	private boolean checkInputData(int[] args) {
		if (args.length != 3) {
			System.out.println("Invalid number of hiring date components - please enter the "
					+ "hiring date as specified.");
			return true;
		}
		
		for (int i=0; i<args.length; i++) {
			if (args[i] == 0) {
				System.out.println("Invalid date provided - blank fields cannot be used. "
						+ "Please provide a full hiring date as specified.");
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Generate hire date in String format from integer components for simple viewing
	 */
	private void setHiringDate() {
		String hireMonthStr = "";
		
		if (hireMonth < 10) {				// if hireMonth is less than 10, add a leading 0 to the corresponding String
			hireMonthStr = "0" + hireMonth;
		} else {
			hireMonthStr += hireMonth;
		}
		
		String hireDayStr = "";
		
		if (hireDay < 10) {					// if hireDay is less than 10, add a leading 0 to the corresponding String
			hireDayStr = "0" + hireDay;
		} else {
			hireDayStr += hireDay;
		}
		
		fullHiringDate = hireMonthStr+"/"+hireDayStr+"/"+hireYear;
	}
	
	// getter for full hiring date
	public String getFullHiringDate() {
		return fullHiringDate;
	}
	
	// setter for hire month, if month value is invalid print error message
	private void setHireMonth (int month) {
		if (month < 1 || month > 12) {
			System.out.println("Invalid month input - please provide a month between 1 and 12");
			System.exit(0);
		} else {
			this.hireMonth = month;
		}
	}
	
	// getter for hire month
	public int getHireMonth () {
		return hireMonth;
	}
	
	// setter for hire day, if day value is invalid print error message
	private void setHireDay (int day) {
		if (day < 1 || day > 31) {
			System.out.println("Invalid day input - please provide a day between 1 and 31");
			System.exit(0);
		} else {
			this.hireDay = day;			
		}
	}
	
	// getter for hire day 
	public int getHireDay () {
		return hireDay;
	}
	
	// setter for hire year, if  year value is unreasonable print error message 
	private void setHireYear (int year) {
		if (year < 1900 || year > 2200) {
			System.out.println("Unreasonable year input - please provide a year between 1990 and 2200");
			System.exit(0);
		} else {
			this.hireYear = year;
		}
	}
	
	// getter for hire year
	public int getHireYear () {
		return hireYear;
	}
}
