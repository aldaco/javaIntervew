package palindrome;

import java.lang.Runnable;

class PalindromeCheckerRunnable implements Runnable {
	private boolean result;

	int _index;
	int _iterations;
	String _input;
	
	public boolean getResult() {
		return result;
	} // end getResult()

	public PalindromeCheckerRunnable(int index, int iterations, String input) {
		_index = index;
		_iterations = iterations;
		_input = input;
	} // end PalindromeCheckerRunnable()

	public void run() {
		result = checker(_index, _iterations);
	} // end run()

	boolean checker(final int index, final int iteration) {
		for (int i = index; i < (index + iteration); ++i) {
			final int leftIndex = index;
			final int rightIndex = _input.length() - 1 - index;

			if (_input.charAt(leftIndex) != _input.charAt(rightIndex))
				return false;
		} // end for
		
		return true;
	} // end checker()
} // end class PalindromeCheckerRunnable