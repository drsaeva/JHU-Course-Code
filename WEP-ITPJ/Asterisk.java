import java.util.Scanner;

public class Asterisk {
	
	public static void main(String[] args) {
		int max = 0;
		String ast = "";
		int aOrD = 0;
		
		System.out.println("Please provide the maximum number of asterisks you wish to see "
				+ "displayed on an output line: ");
		Scanner s = new Scanner(System.in);
		if (s.hasNext()) {
			max = Integer.parseInt(s.nextLine());
		}
		
		System.out.println("Please state whether the number of asterisks displayed should ascend "
				+ "or descend. Provide a '1' for ascending, or '2' for descending: ");
		Scanner t = new Scanner(System.in);
		if (t.hasNext()) {
			aOrD = Integer.parseInt(t.nextLine());
		}
		
		if (aOrD == 1) {
			for (int i = 1; i<=max; i++) {
				ast += "*";
				System.out.println(ast);
			}
		} else if (aOrD == 2) {
			for (int i = 1; i<=max; i++) {
				ast += "*";
			}
			
			for (int i = max; i>=1; i--) {
				System.out.println(ast);
				ast = ast.substring(0, i-1);
				
			}
		}

	}

}
