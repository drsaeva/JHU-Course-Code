import java.util.Scanner;
 
public class TemperatureConversion {
	
  public static void main( String [] args ) {
    char userChoice = 'A';                       	// User selection 
    Scanner input = new Scanner( System.in );     // Create a Scanner to obtain user input
   
    while( userChoice != 'Q') {
      System.out.print("Please enter your preference for input temperature to be converted, "
      		+ "or to quit." + "\n" +"Fahrenheit" + "\n" + "Celsius" + "\n" + "Quit" + "\n");
      
      userChoice = input.next().charAt(0);              // Read user input: take 1st letter parsed in
      
      if (userChoice == 'F' || userChoice == 'C') {
      		System.out.print( "Enter your input temperature: " );
      		float inputTemp = input.nextFloat();
      	 
      		System.out.println(inputTemp + " degrees " +userChoice+" is " 
      				 + convertTemp(userChoice, inputTemp) +" degrees in the other system" );
      		break;
      } 
      
      if (userChoice == 'Q') {
      	System.out.println("Until next time.");
      	input.close();
      	break;
      	
      } else {
        System.out.println( "Invalid Data: You must enter Fahrenheit, Celsius, Quit, or some abbreviation of one of those three" ); // Invalid Data Entered 	
        break;
      }
      
    }
    
  }
  
  private static float convertTemp(char userChoice, float temp) {

  	if (userChoice == 'F') {
  		return (5f/9f) * (temp - 32);
  	} else {
  		return (9f/5f) * temp + 32;
  	}

  }
  
}
