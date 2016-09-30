/**
 * Class BMICalculator - calculates BMI value from user-provided
 * 	height and weight values in inches and pounds, respectively.
 * 
 * @author David Saeva
 */

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class BMICalculator {
	
	public static void main(String[] args) {
		
		//initialize inch->meter and pound->kilogram conversion values;
		final double metersFromInches = 0.0254;
		final double kilogramsFromPounds = 0.45359237;
	
		//initialize Scanner to retrieve user inputs
		Scanner s = new Scanner(System.in);
	
		//request height and weight from user, cast to doubles for calculations
		System.out.println("\n");
		System.out.print("Please enter your height in inches: ");
    	int heightIn = s.nextInt();
    	double heightDouble = (double) heightIn;
    
    	System.out.println("\n");
    	System.out.print("Please enter your weight in pounds: ");
    	int weightIn = s.nextInt();
    	double weightDouble = (double) weightIn;
    	
    	s.close();
    	
    	System.out.println("\n");
    
    	//calculate height in meters and weight in kilograms;
    	heightDouble *= metersFromInches;
    	weightDouble *= kilogramsFromPounds;
    	
    	//calculate BMI
    	double bmi = weightDouble / (heightDouble*heightDouble);
    	
    	//set decimal places to 1, rounding method to round up,
    	// and round BMI to 1 decimal place (converts to String)
    	DecimalFormat df = new DecimalFormat("#.#");
    	df.setRoundingMode(RoundingMode.CEILING);
    	String roundedBMI = df.format(bmi);
    	
    	//print out rounded BMI and DHHS/NIH BMI guide
    	System.out.println("Body Mass Index (BMI) is a measure of health based on your height and weight.\n\n"
    					+"From your height and weight, your BMI has been calculated to be: " + roundedBMI + "\n\n"
    					+ "The following guide has been provided by the Department of Health & Human \n"
    					+ "Services and the National Institutes of Health for your reference.");
    	System.out.println("\n");
    	System.out.println("Underweight: less than 18.5 \n"
    					+ "Normal: 18.5 – 24.9 \n"
    					+ "Overweight: 25 – 29.9 \n" 
    					+ "Obese: 30 or greater");
    	
	}
	
}
