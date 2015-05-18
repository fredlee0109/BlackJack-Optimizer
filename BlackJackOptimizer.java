/**
 * @author Fred Lee
 *
 * BlackJackOptimizer follows the basic BlackJack Strategy
 * Assumes the game is played under Single Deck, American Style,
 * Dealer Stands on Soft 17, Double After Split is Allowed, and 
 * No Surrender.
 * 
 * For the future: 
 * Create a GUI, 
 * save history, 
 * create a list of commands
 */
public class BlackJackOptimizer {
	int dealerCard; //from 1 to 11, with 11 = Ace
	int[] playerCards = new int[2]; //each from 1 to 11, with 11 = Ace.
	boolean soft = false;
	/**
	 * BlackJackOptimizer Constructor
	 * @param dealerCard : dealer's card number
	 * @param playerCards : player's card numbers
	 */
	public BlackJackOptimizer(int dealerCard, int[2] playerCards) {
		this.dealerCard = dealerCard;
		this.playerCards = playerCards;
		if (playerCards[0] == 11 || playerCards[1] == 11) {
			soft = true;
		} else {
			soft = false;
		}
	}

	/**
	 * calculates player card's total
	 * @return total
	 */
	public int total() {
		return playerCards[0] + playerCards[1];
	}

	public static void main(String[] args) {
		switch (command) {
			case "":
			System.out.println();
			break;
			default:
			break;
		}
	}
}