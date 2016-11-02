package blackjackGame;
import java.util.Scanner;
/**
 * This class provides static access to a method that requests user input in the form of
 * 	one of two answers to a passed-in query. 
 * @author dsaeva
 *
 */
public class Utility {
	
	/**
	 * This method requests an answer from the user to a provided query. Invalid answers
	 * 	are ignored, requesting a new answer until a valid answer is provided.
	 * @param query A String that contains question to be asked of the user
	 * @param ans A String array containing exactly two answers allowed for user reponse
	 * @return	true If the user response equals the first answer in the answer array
	 * 					false Otherwise
	 */
	public static boolean getUserInput(String query, String[] ans) {
		Scanner s = new Scanner(System.in);
		String user = "";
		
		if (ans.length !=2) {
			System.out.println("Invalid answer array input");
			System.exit(0);
		}
		
		while (s.hasNext()) {
			try {
				user = s.nextLine();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Please provide an answer, '"+ans[1]+"' or '"+ans[2]+"': ");
			}
			
			if (user.equals(ans[1]) || user.equals(ans[2])) {
				break;
			}
		}
		
		s.close();
		
		if (user.equals(ans[1])) {
				return true;
		} else {
			return false;
		}
	}
	
}
