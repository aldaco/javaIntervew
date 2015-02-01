package palindrome;

class PalindromeChecker {
	private String _input = null;

	public boolean setPalindrome(String input) {
		if (input == null)
			return false;

		// We clean the String from blank spaces and Upper case
		_input = input.toLowerCase().replaceAll("[^a-z]", "");
		if (input.length() == 0)
			return false;

		return true;
	} // end setPalindrome()

	public boolean isPalindrome() {
		if (_input == null || _input.length() == 0)
			return false;

		if (_input.length() == 1) {
			// All Strings with only one character are palindromes.
			return true;
		}

		final int n = _input.length();
		
		// check the cores available
		int numOfThreads = Runtime.getRuntime().availableProcessors();
		
		// helpful when dealing with odd String
		int m = n;
		
		/* Since we'll compare from left to right, we need to split the input
		 * into two Strings with the same size
		 * i.e. For "xyzzyx", string will be logically split into two, and a "mirror"
		 * comparison will be done (x and x, y and y, z and z).
		 */
		
		int isOdd = 0;
		if (n % 2 != 0) {
			// if length is odd
			m = n - 1;
			isOdd = 1;
		}

		if (m / 2 < numOfThreads) {
			/* Check if cores are more than String length.
			 * Having more threads than cores, usually, degrades performance so
			 * we have at most one thread per core.
			 */
			numOfThreads = m / 2;
		}

		int blockSize;

		if (isOdd == 1) {
			blockSize = (m / 2) / numOfThreads;
		} else {
			blockSize = (n / 2) / numOfThreads;
		}

		// variable for loadBalancing
		int mod = (m / 2) % numOfThreads;

		int index = 0;

		PalindromeCheckerRunnable[] runnables = new PalindromeCheckerRunnable[numOfThreads];

		Thread[] threads = new Thread[numOfThreads];

		for (int i = 0; i < numOfThreads; ++i) {
			int iterations = blockSize;

			if (mod > 0) {
				// These threads have a larger block size in order to divide the work as balanced as possible.
				++iterations;
				--mod;
			}

		    /* Assign a range of the input String to check if it is palindrome
			 * [index, index + iteration].
			 * Threads are given a range of contiguous elements in a block
			 * schedule manner, to maximize efficient cache usage.
			 */
			runnables[i] = new PalindromeCheckerRunnable(index, iterations, _input);

			threads[i] = new Thread(runnables[i]);

			threads[i].start();
			
			index += iterations;
		} // end for

		// Wait for all threads to finish.
		for (int i = 0; i < numOfThreads; ++i) {
			try {
				threads[i].join();
			} catch (InterruptedException ex) {
				System.out.println("Error while joining threads.");
				System.exit(1);
			}
		}

		// Check results.
		for (int i = 0; i < numOfThreads; ++i) {
			if (runnables[i].getResult() == false) {
				return false;
			}
		}

		return true;
	} // end isPalindrome()
} // end class PalindromeChecker