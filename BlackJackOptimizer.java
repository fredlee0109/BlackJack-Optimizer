/**
 * @author Fred Lee
 *
 * Influenced by the movie 21
 *
 * BlackJackOptimizer follows the basic BlackJack Strategy
 * Assumes the game is played under Single Deck, American Style,
 * Dealer Stands on Soft 17, Double After Split is Allowed, and 
 * No Surrender.
 * 
 * For the future: 
 * Create a GUI, 
 * save history, 
 * ask if Double is allowed
 * use Serializable to only do init() once.
 * create a list of commands
 * allow for different # of decks
 * allow for different blackjack styles
 * allow for different strategies, such as counting.
 * ask user if they want to have "counting" in the system
 * show statistics of winning with the using strategy
 */
import java.util.HashMap;
import java.util.Scanner;

public class BlackJackOptimizer {
	public static HashMap<String, String> hard = new HashMap();
	public static HashMap<String, String> soft = new HashMap();
	public static HashMap<String, String> pair = new HashMap();
	public HashMap<String, Integer> newCards1 = new HashMap(); 
	// should use linkedlist instead
	public HashMap<String, Integer> newCards2 = new HashMap();
	public int dealerCard; //from 1 to 11, with 11 = Ace
	public int playerCard1;
	public int playerCard2;
	public int total = 0;
	public int total1 = 0;
	public int total2 = 0;
	public int bsoft = 0;
	public boolean bpair = false;
	public boolean initialized = true;
	/**
	 * BlackJackOptimizer Constructor
	 * @param dealerCard : dealer's card number
	 * @param playerCards : player's card numbers
	 */
	private BlackJackOptimizer(int dealerCard, int playerCard1, int playerCard2) {
		this.dealerCard = dealerCard;
		this.playerCard1 = playerCard1;
		this.playerCard2 = playerCard2;
		if (playerCard1 == playerCard2) {
			bpair = true;
		} 
		if (playerCard1 == 11) {
			bsoft++;
		} 
		if (playerCard2 == 11) {
			bsoft++;
		}
		total = playerCard1 + playerCard2;
	}

	/**
	 * 'special' constructor to tell user to initalize the game first
	 */
	private BlackJackOptimizer() {
		initialized = false;
	}

	/**
	 * Called once. Creates 3 HashMaps of the table.
	 * For Hard, Soft, Pair, respectively
	 * Key: PlayerCardTotal + DealerCard
	 * Val: H, D, S, P. (meaning Hit, Double, Stand, Split, respectively)
	 * src: http://wizardofodds.com/games/blackjack/strategy/calculator/
	 */
	private void init() {
		// constructs hard
		for (Integer i = 5; i <= 21; i++) {
			for (Integer j = 2; j <= 11; j++) {
				String tempKey = i.toString() + j.toString();
				if (i <= 8 && j >= 2 && j <= 4) {
					hard.put(tempKey, "H");
				} else if (i <= 7 && j >= 5 && j <= 6) {
					hard.put(tempKey, "H");
				} else if (i <= 9 && j >= 7) {
					hard.put(tempKey, "H");
				} else if (i == 10 && j >= 10) {
					hard.put(tempKey, "H");
				} else if (i >= 9 && i <= 11 && j <= 4) {
					hard.put(tempKey, "D");
				} else if (i >= 8 && i <= 11 && j >= 5 && j <= 6) {
					hard.put(tempKey, "D");
				} else if (i >= 10 && i <= 11 && j >= 7 && j <= 9) {
					hard.put(tempKey, "D");
				} else if (i == 11 && j >= 10) {
					hard.put(tempKey, "D");
				} else if (i == 12 && j <= 3) {
					hard.put(tempKey, "H");
				} else if (i >= 12 && i <= 16 && j >= 7) {
					hard.put(tempKey, "H");
				} else if (i == 12 && j >= 4 && j <= 6) {
					hard.put(tempKey, "S");
				} else if (i >= 13 && j <= 6) {
					hard.put(tempKey, "S");
				} else if (i >= 17 && j >= 7) {
					hard.put(tempKey, "S");
				} else {
					System.out.print("Error while hard on i = ");
					System.out.print(i);
					System.out.print("and j = ");
					System.out.println(j);
				}
			}
		}
		// constructs soft
		for (Integer i = 13; i <= 21; i++) {
			for (Integer j = 2; j <= 11; j++) {
				String tempKey = i.toString() + j.toString();
				if (i <= 16 && j <= 3) {
					soft.put(tempKey, "H");
				} else if (i <= 17 && j >= 7) {
					soft.put(tempKey, "H");
				} else if (i == 18 && j >= 9 && j <= 10) {
					soft.put(tempKey, "H");
				} else if (i <= 16 && j >= 4 && j <= 6) {
					soft.put(tempKey, "D");
				} else if (i == 17 && j <= 6) {
					soft.put(tempKey, "D");
				} else if (i == 18 && j >= 3 && j <= 6) {
					soft.put(tempKey, "D");
				} else if (i == 19 && j == 6) {
					soft.put(tempKey, "D");
				} else if (i >= 18 && j == 2) {
					soft.put(tempKey, "S");
				} else if (i >= 19 && j >= 3 && j <= 5) {
					soft.put(tempKey, "S");
				} else if (i >= 20 && j == 6) {
					soft.put(tempKey, "S");
				} else if (i >= 18 && ((j >= 7 && j <= 8) || j == 11) ) {
					soft.put(tempKey, "S"); 
				} else if (i >= 19 && j >= 9 && j <= 10) {
					soft.put(tempKey, "S");
				} else {
					System.out.print("Error while soft on i = ");
					System.out.print(i);
					System.out.print("and j = ");
					System.out.println(j);
				}
			}
		}
		// constructs pair
		for (Integer i = 2; i <= 11; i++) {
			for (Integer j = 2; j <= 11; j++) {
				String tempKey = i.toString() + j.toString();
				if (i == 2 && j <= 7) {
					pair.put(tempKey, "P");
				} else if (i == 3 && j <= 8) {
					pair.put(tempKey, "P");
				} else if (i == 4 && j >= 4 && j <= 6) {
					pair.put(tempKey, "P");
				} else if (i == 2 && j >= 8) {
					pair.put(tempKey, "H");
				} else if (i == 3 && j >= 9) {
					pair.put(tempKey, "H");
				} else if (i == 4 && (j <= 3 || j >= 7)) {
					pair.put(tempKey, "H");
				} else if (i == 5) {
					if (j <= 9) {
						pair.put(tempKey, "D");
					} else {
						pair.put(tempKey, "H");
					}
				} else if (i == 6) {
					if (j <= 7) {
						pair.put(tempKey, "P");
					} else {
						pair.put(tempKey, "H");
					}
				} else if (i == 7) {
					if (j <= 8) {
						pair.put(tempKey, "P");
					} else if (j == 10) {
						pair.put(tempKey, "S");
					} else {
						pair.put(tempKey, "H");
					}
				} else if (i == 8) {
					pair.put(tempKey, "P");
				} else if (i == 9) {
					if (j <= 6 || j == 8 || j == 9) {
						pair.put(tempKey, "P");
					} else {
						pair.put(tempKey, "S");
					}
				} else if (i == 10) {
					pair.put(tempKey, "S");
				} else if (i == 11) {
					pair.put(tempKey, "P");
				} else {
					System.out.print("Error while pair on i = ");
					System.out.print(i);
					System.out.print("and j = ");
					System.out.println(j);
				}
			}
		}
	}

	/**
	 * calculates player card's total
	 * @return total
	 */
	private Integer total() {
		return total;
	}

	/**
	 * Determines whether to Hit, Stand, Split, or Double.
	 * @return Hit, Stand, Split, or Double as String.
	 */
	private String decision() {
		String total = total().toString() + dealerCard;
		if (bpair) {
			Integer temp = playerCard1;
			total = temp.toString() + dealerCard;
			return pair.get(total);
		} else if (bsoft != 0) {
			if (this.total < 21) {
				return soft.get(total);
			} else {
				Integer k = total() - 10;
				String tempTotal = k.toString() + dealerCard;
				return hard.get(tempTotal);
			}
		} else {
			return hard.get(total);
		}
	}

	/**
	 * player HIT a card. Update player's total
	 * @param card : the hit-ted card
	 */
	private void hit(int card) {
		total += card;
	}

	/**
	 * player HIT a card to 1st splitted card
	 * @param card : the hit-ted card
	 */
	private void hit1(int card) {
		total1 += card;
	}

 	/**
	 * player HIT a card to 2nd splitted card
	 * @param card : the hit-ted card
	 */
	private void hit2(int card) {
		total2 += card;
	}

	/**
	 * main method
	 * @param args : user's inputs
	 */
	public static void main(String[] args) {
		System.out.println("  -----------------------------------------");
		System.out.println("       Welcome to BlackJackOptimizer.");
		System.out.println("Remember this only works for American Style.");
		System.out.println("     Type: 'help' for list of commands");
		System.out.println("             @author Fred Lee");
		System.out.println("  -----------------------------------------");
		System.out.println("Type : [Dealer's card] [Your 1st Card] [2nd Card]");
		System.out.println("to start a new game.");
		System.out.println("Type : 'end' to stop program.");

		BlackJackOptimizer bjo = new BlackJackOptimizer();
		bjo.init();

		Scanner in = new Scanner(System.in);
		String userInput = in.nextLine();
		Boolean splitted = false;
		Boolean on1stSplit = true;
		int splitted1 = 0;
		int splitted2 = 0;
		while (!userInput.equals("end")) {
			String[] numbers = userInput.split("\\s+");
			if (numbers.length != 3 && numbers.length != 1) {
				System.out.println("Unrecognized Command.");
				break;
			} else {
				if (numbers.length == 3) {
					bjo.newCards1.clear();
					bjo.newCards2.clear();
					splitted = false;
					boolean one = false;
					boolean two = false;
					boolean three = false;
					Integer[] numbersInt = new Integer[3];
					for (int o = 0; o < numbersInt.length; o++) {
						if (numbers[o].equals("A") || numbers[o].equals("a")) {
							numbersInt[o] = 11;
						} else if (numbers[o].equals("K") || numbers[o].equals("k") 
							|| numbers[o].equals("Q") || numbers[o].equals("q")
							|| numbers[o].equals("J") || numbers[o].equals("j")) {
							numbersInt[o] = 10;
						} else {
							numbersInt[o] = Integer.parseInt(numbers[o]);
						}
					}
					bjo = new BlackJackOptimizer(numbersInt[0], numbersInt[1],
						numbersInt[2]);
					if (bjo.total() == 21) {
						System.out.println("Black Jack! You Win!");
					} else {
						System.out.println();
						System.out.print("Dealer: ");
						System.out.print(numbers[0]);
						System.out.print(" Player: ");
						if (bjo.total() > 21 && bjo.bsoft != 0) {
							bjo.total -= 10;
						}
						System.out.print(bjo.total());
						if (bjo.bsoft != 0 && bjo.total() - 10 <= 21) {
							System.out.print(" or ");
							System.out.print(bjo.total() - 10);
						}
						System.out.println();
						String decision = bjo.decision();
						if (decision.equals("H")) {
							System.out.println("I recommend HIT");
							System.out.println("Type new card.");
						} else if (decision.equals("S")) {
							System.out.println("I recommend STAND");
						} else if (decision.equals("P")) {
							splitted = true;
							splitted1 = bjo.playerCard1;
							splitted2 = bjo.playerCard2;
							System.out.print("I recommend SPLIT and HIT to: ");
							System.out.print(splitted1);
							System.out.print(" and ");
							System.out.println(splitted2);
							System.out.println("Type new card for 1st split");
						} else if (decision.equals("D")) {
							System.out.println("I recommend DOUBLE");
							System.out.println("Type new card.");
						}
					}
				} else { // length == 1
					if (!bjo.initialized) {
						System.out.println("Please start a new game first.");
					} else {
						if (!splitted) {
							if (bjo.newCards1.containsKey(numbers[0])) {
								int val = bjo.newCards1.get(numbers[0]);
								bjo.newCards1.put(numbers[0], val++);
							} else {
								bjo.newCards1.put(numbers[0], 1);
							}
							if (numbers[0].equals("A") || numbers[0].equals("a")) {
								bjo.hit(11);
								bjo.bsoft++;
							} else if (numbers[0].equals("K") || numbers[0].equals("k")
								|| numbers[0].equals("Q") || numbers[0].equals("q")
								|| numbers[0].equals("J") || numbers[0].equals("j")) {
								bjo.hit(10);
							} else {
								bjo.hit(Integer.parseInt(numbers[0]));
							}
						} else if (on1stSplit) {
							if (bjo.newCards1.containsKey(numbers[0])) {
								int val = bjo.newCards1.get(numbers[0]);
								bjo.newCards1.put(numbers[0], val++);
							} else {
								bjo.newCards1.put(numbers[0], 1);
							}
							if (numbers[0].equals("A") || numbers[0].equals("a")) {
								bjo.hit1(11);
								bjo.bsoft++;
							} else if (numbers[0].equals("K") || numbers[0].equals("k")
								|| numbers[0].equals("Q") || numbers[0].equals("q")
								|| numbers[0].equals("J") || numbers[0].equals("j")) {
								bjo.hit1(10);
							} else {
								bjo.hit1(Integer.parseInt(numbers[0]));
							}
							bjo.hit1(Integer.parseInt(numbers[0]));
						} else {
							if (bjo.newCards2.containsKey(numbers[0])) {
								int val = bjo.newCards2.get(numbers[0]);
								bjo.newCards2.put(numbers[0], val++);
							} else {
								bjo.newCards2.put(numbers[0], 1);
							}
							if (numbers[0].equals("A") || numbers[0].equals("a")) {
								bjo.hit2(11);
								bjo.bsoft++;
							} else if (numbers[0].equals("K") || numbers[0].equals("k")
								|| numbers[0].equals("Q") || numbers[0].equals("q")
								|| numbers[0].equals("J") || numbers[0].equals("j")) {
								bjo.hit2(10);
							} else {
								bjo.hit2(Integer.parseInt(numbers[0]));
							}
							bjo.hit2(Integer.parseInt(numbers[0]));
						}
						System.out.println();
						System.out.print("Dealer: ");
						System.out.print(bjo.dealerCard);
						System.out.print(" Player: ");
						if (bjo.total() > 21 && bjo.bsoft != 0) {
							bjo.total -= 10;
							bjo.bsoft--;
						}
						System.out.print(bjo.total());
						if (bjo.bsoft != 0 && bjo.total() - 10 <= 21) {
							System.out.print(" or ");
							System.out.print(bjo.total() - 10);
						}
						System.out.println();
						if (bjo.bsoft != 0 && bjo.total - 10 > 21) {
							bjo.total -= 10;
						}
						if (bjo.bsoft == 0 && bjo.total() > 21) {
							System.out.println("You lost! :(");
						} else if (bjo.bsoft != 0 && 
							(bjo.total() > 21 && bjo.total() - 10 > 21)) {
							System.out.println("You lost! :(");
						} else {
							String decision = bjo.decision();
							if (decision.equals("H")) {
								System.out.println("I recommend HIT");
								System.out.println("Type new card.");
							} else if (decision.equals("S")) {
								System.out.println("I recommend STAND");
							} else if (decision.equals("P")) {
								splitted = true;
								splitted1 = bjo.playerCard1;
								splitted2 = bjo.playerCard2;
								System.out.print("I recommend SPLIT and HIT to: ");
								System.out.print(splitted1);
								System.out.print(" and ");
								System.out.println(splitted2);
								System.out.println("Type new card for 1st split");
							} else if (decision.equals("D")) {
								System.out.println("I recommend DOUBLE");
								System.out.println("Type new card.");
							}
						}
					}
				}
				in = new Scanner(System.in);
				userInput = in.nextLine();
			}
		}			
	}
}
