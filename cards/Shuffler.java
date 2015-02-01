package cards;

import java.util.ArrayDeque;
import java.util.Random;

public class Shuffler {
	public static final int kCardsInDeck = 52;

	final String[] suits = new String[] { "Spade", "Heart", "Diamonds", "Clubs" };
	final String[] cards = new String[] { "Ace", "2", "3", "4", "5", "6", "7",
			"8", "9", "10", "Jack", "Queen", "King" };

	int currentCardIndex = 0;
	int numRecordsStored = 0;
	int[] records = new int[kCardsInDeck]; // Recording outputs numbers

	// Random seed, take UNIX date and time.
	Random rand = new Random(System.currentTimeMillis());

	// Pre-allocate tasks for performance gains.
	ArrayDeque<Range> tasks = new ArrayDeque<Range>(kCardsInDeck);

	public String getNextCard() {
		final int n = records[currentCardIndex];

		// i.e. if n= 29 => suitIndex = 2 && cardIndex = 3
		// so it'll be 4 of Diamonds
		final int suitIndex = n / 13;
		final int cardIndex = n % 13;

		StringBuilder cardBuilder = new StringBuilder("Your next card is the ");
		cardBuilder.append(cards[cardIndex]).append(" of ")
				.append(suits[suitIndex]);
		cardBuilder.append(System.lineSeparator());

		++currentCardIndex;

		return cardBuilder.toString();
	} // end getNextCard()

	public void reshuffle() {
		/*
		 * A multi-threaded approach would be trivial to implement (using a
		 * concurrent queue from the collections and having a counter of the
		 * threads that are working) but local tests showed that the cost of
		 * synchronization outweights the potential performance gains.
		 */

		System.out.println("Reshuffling...");

		numRecordsStored = 0;

		// Recursion avoided using a queue.
		tasks.add(new Range(0, kCardsInDeck - 1));

		while (!tasks.isEmpty()) {
			Range current = tasks.remove();

			if (current.left > current.right) {
				continue;
			}

			// Get random integer in range [left, right]
			final int n = rand.nextInt((current.right - current.left) + 1)
					+ current.left;

			records[numRecordsStored] = n;
			++numRecordsStored;

			// Make it completely random.
			if (current.left - n - 1 > n + 1 - current.right) {
				tasks.add(new Range(current.left, n - 1));
				tasks.add(new Range(n + 1, current.right));
			} else {
				tasks.add(new Range(n + 1, current.right));
				tasks.add(new Range(current.left, n - 1));
			}
		}

		// Reset card dealing.
		currentCardIndex = 0;
	}
}// End class Shuffler

class Range {
	int left;
	int right;

	Range(int pLeft, int pRight) {
		left = pLeft;
		right = pRight;
	}
} // End class Range