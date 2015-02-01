package palindrome;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class PalindromeApp {
	public static void main(String[] args) {
		String input = null;
		System.out.print("Please enter a string: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			input = br.readLine();
		} catch (IOException ex) {
			System.out.println("Can't read string from stdin");

			return;
		}

		PalindromeChecker myChecker = new PalindromeChecker();

		if(!myChecker.setPalindrome(input)) {
			System.out.println("Error with input string. Please verify format.");
			
			return;
		}

		if (myChecker.isPalindrome()) {
			System.out.println("Your string is a palindrome, good job!");
		} else {
			System.out.println("Your string is NOT a palindrome, boo-hoo!");
		}

	}// end main()
} // end class PalindromeApp

