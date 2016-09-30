import java.util.Scanner;

public class CalendarByMonthYear {
	/**
	 * Declarations for globally-utilized whitespace strings
	 */
	public static String wsp2 = "  ";				
	public static String wsp3 = "   ";
	public static String wsp4 = wsp2+wsp2;
	public static String wsp5 = wsp2+wsp3;
	public static String wsp10 = wsp5+wsp5;
	
	/**
	 * Gets user input for month and year, handles invalid inputs, 
	 * 	and prints a calendar for the requested month and year
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Please enter the number representing the month you'd like a calendar for: ");
		Scanner sM = new Scanner(System.in);
		int month = 0;
		
		try {												// handle non-integer month value inputs and print a user-friendly error message
			month = sM.nextInt();
		} catch (java.util.InputMismatchException badMonth) {
			System.out.println("That's not an integer representing a month,"
					+ " and the program is mad at you now. Please restart the "
					+ "program and provide a number for a month of your choosing!");
			System.exit(0);
		}
		
		if (month == 0 || month > 12) {			// for an invalid integer month value input, print a message and set month to Jan	
			System.out.println("That's close, but still not a number representing "
					+ "a month - this program will default to a calendar for January.");
			month = 1;
		}
		
		System.out.println("Please enter the number representing the year you'd like a calendar for: ");
		Scanner sY = new Scanner(System.in);
		int year = 0; 
		
		try {												// handle non-integer year value inputs and print a user-friendly error message
			year = sY.nextInt();
		} catch (java.util.InputMismatchException badYear) {
			System.out.println("That's not an integer representing a year,"
					+ " and the program is mad at you now. Please restart the "
					+ "program and provide a number for a year of your choosing!");
			System.exit(0);
		}
		
		printMonthHeader (month, year);			// print the month calendar header
		printMonthBody(month, year);				// print the month calendar body
		sM.close();
		sY.close();

	}
	/**
	 * Prints the month body of the monthly calendar
	 * Sets the position of the first day as calculated by getStartDay
	 * Does not take a month day limit parameter - instead calls getNumDaysInMonth when needed
	 * 
	 * @param Month as corresponding to its order in the calendar year
	 * @param y Calendar year
	 */
	static void printMonthBody( int m, int y ) {
		String weekLine = "";																			// initialize empty line to represent each week
		System.out.println(" Sun Mon Tue Wed Thu Fri Sat ");			// week header line
		int d = 1;																								// initialize day counter for internal use
		
		while (d<=getNumDaysInMonth(m, y)) {											// Loop executing week string generation while day is under monthly amount of days
			for (int h=1; h<=7; h++) {															// Loop over number of days in a week to generate a string of days for each week
				if (d == 1) {																					// If 1st day, set position in week according to getStartDay
					h = getStartDay(m, d, y);
					
					for (int sp = 1; sp<h; sp++) {											// Loop over 'empty' days in week prior to month 1st day and add whitespace
						weekLine+=wsp4;
					}
					weekLine+=(wsp3+d);																	// Concatenate leading whitespace and 1st day to week string
					
				}	else if (h <= 7 && d <= getNumDaysInMonth(m, y)) {	// While under month amount of days and week counter
					if (d < 10) {																				// If day is less than 10, add 3 leading whitespaces and day
						weekLine+=(wsp3+d);
					} else {																						// When day is 10 or higher, add only 2 leading whitespaces and day
						weekLine+=(wsp2+d);
					}	
				}
				d++;																									// Increment day number
			}
			
			System.out.println(weekLine);														// When week day count 'h' hits 8, print out the weekLine and reassign it to an empty string
			weekLine = "";
		}
		
	}
	
	/**
	 * Prints the header for the monthly calendar corresponding to the given month/year
	 * 
	 * @param m Month as corresponding to its order in the calendar year
	 * @param y Calendar year
	 */
	static void printMonthHeader( int m, int y )  {
		
		System.out.println(wsp10 + getMonthName(m) + wsp2 + y + wsp10);
		System.out.println("-----------------------------");
	}
	
	/**
	 * Returns name of month corresponding to its order in the calendar year
	 * 
	 * @param m Month as corresponding to its order in the calendar year
	 * @return String name of month in calendar year
	 */
	static String getMonthName( int m ) {
		if (m == 1) {
			return "Jan";
		} else if (m == 2) {
			return "Feb";
		} else if (m == 3) {
			return "Mar";
		} else if (m == 4) {
			return "Apr";
		} else if (m == 5) {
			return "May";
		} else if (m == 6) {
			return "Jun";
		} else if (m == 7) {
			return "Jul";
		} else if (m == 8) {
			return "Aug";
		} else if (m == 9) {
			return "Sep";
		} else if (m == 10) {
			return "Oct";
		} else if (m == 11) {
			return "Nov";
		} else {
		return "Dec";
		}

	}

/**
 * Returns the day of the week upon which the first of a given month in a given year occurs
 * 
 * @param m Month as corresponding to its order in the calendar year
 * @param d Day number of first day in month, only works with '1'
 * @param y Calendar year
 * @return Day of the week in which that month begins on in 1-7 format
 */
	static int getStartDay( int m, int d, int y ) {
		// Adjust month number & year to fit Zeller's numbering system
		if (m < 3)  {
			m = m + 12;
			y = y - 1;
		}
        
		int k = y % 100;      // Calculate year within century
		int j = y / 100;      // Calculate century term
		int h = 0;            // Day number of first day in month 'm'
        
		h = ( d + ( 13 * ( m + 1 ) / 5 ) + k + ( k / 4 ) + ( j / 4 ) +
				( 5 * j ) ) % 7;
        
		// Convert Zeller's value to ISO value (1 = Mon, ... , 7 = Sun )
		int dayNum = ( ( h + 5 ) % 7 ) + 1;     
        
		return dayNum;
		
    }
	
  /**
   * Returns the amount of days in a given month in a given given
   * 
   * @param m Month as corresponding to its order in the calendar year
   * @param y Calendar year
   * @return	Amount of days in that month for that year
   */
	static int getNumDaysInMonth( int m, int y) {
		
		if (m == 1 || m == 3 || m == 5 || m == 7 
				|| m == 8 || m == 10 || m == 12) {	// Return 31 days for Jan, Mar, May, Jul, Aug, Oct, Dec
			return 31;
		
		} else if (m == 2 && !isLeapYear(y)) {	// Return 28 days for non-leap year Feb
			return 28;
			
		} else if (m == 2 && isLeapYear(y)) {		// Return 29 days for leap year Feb
			return 29;
			
		} else {																// Return 30 days for remaining months
			return 30;
			
		}
	}
    
/**
 * Returns true if year argument corresponds to a leap year
 * 
 * Leap years occur every 4 years on years that are a multiple of 4
 * @param y year value input
 * @return true if y mod 4 = 0 
 *         false otherwise
 */
	static boolean isLeapYear(int y) {	
		if (y%4 == 0) {
			return true;
    }
    	return false;
	}
	
}
