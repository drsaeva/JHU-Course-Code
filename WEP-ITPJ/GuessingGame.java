import java.util.Scanner;

public class GuessingGame {

	public static void main(String[] args) {
		int range = 0;
		int maxGuesses = 0;
		String playAgain = "Y";
		
		System.out.println("Welcome to the guessing game! This program will randomly choose"
				+ " a secret number within a range of '1' and a value of your choosing. You"
				+ " get to decide how many guesses you will have. \n\n");
		
		if (playAgain == "Y") {
		
			System.out.println("Please provide the maximum possible value for the secret number: ");
			Scanner s = new Scanner(System.in);
			if (s.hasNext()) {
				range = Integer.parseInt(s.nextLine());
			}
		
			System.out.println("Please provide the number of guesses you wish to have: ");
			Scanner t = new Scanner(System.in);
			if (t.hasNext()) {
				maxGuesses = Integer.parseInt(t.nextLine());
			}
			
			 

		} else {
			System.out.println("Thanks for playing. Until next time!");
		}
	}
}
