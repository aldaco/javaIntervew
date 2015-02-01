package cards;

public class ShufflerApp {
	public static void main(String[] args) {
		Shuffler myShuffler = new Shuffler();
		
		myShuffler.reshuffle();
		
		for(int i = 0; i < Shuffler.kCardsInDeck; ++i) {
			System.out.println(myShuffler.getNextCard());
		}
	}
}// End class