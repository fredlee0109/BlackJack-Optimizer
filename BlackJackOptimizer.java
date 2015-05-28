/**
 * @author Fred Lee
 * @link https://github.com/fredlee0109/BlackJack-Optimizer
 * Please read README.md
 *
 * Influenced by the movie 21
 *
 * BlackJackOptimizer follows the basic BlackJack Strategy
 * Assumes the game is played under Single Deck, American Style,
 * Dealer Stands on Soft 17, Double After Split is Allowed, and 
 * No Surrender.
 * 
 * Known Bugs #: 1
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
 * percentage of winning after stand?
 */
import java.util.HashMap;
import java.util.Scanner;

public class BlackJackOptimizer {
    public static HashMap<String, String> hard = new HashMap();
    public static HashMap<String, String> soft = new HashMap();
    public static HashMap<String, String> pair = new HashMap();
    public HashMap<String, Integer> newCards1 = new HashMap(); 
    // card to count
    public HashMap<String, Integer> newCards2 = new HashMap();
    // card to count
    public int dealerCard; //from 1 to 11, with 11 = Ace
    public int playerCard1;
    public int playerCard2;
    public int total = 0;
    public int total1 = 0;
    public int total2 = 0;
    public int bsoft = 0;
    public int bsoft1 = 0;
    public int bsoft2 = 0;
    public boolean bpair = false;
    public boolean initialized = true;
    public boolean splitted = false;
    public boolean on1stSplit = true;
    public boolean first1 = true;

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
     * Val: H, Dh, Ds, S, P. (meaning Hit, Double or hit, Double or stand,
     * Stand, Split, respectively)
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
                    hard.put(tempKey, "Dh");
                } else if (i >= 8 && i <= 11 && j >= 5 && j <= 6) {
                    hard.put(tempKey, "Dh");
                } else if (i >= 10 && i <= 11 && j >= 7 && j <= 9) {
                    hard.put(tempKey, "Dh");
                } else if (i == 11 && j >= 10) {
                    hard.put(tempKey, "Dh");
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
                    soft.put(tempKey, "Dh");
                } else if (i == 17 && j <= 6) {
                    soft.put(tempKey, "Dh");
                } else if (i == 18 && j >= 3 && j <= 6) {
                    soft.put(tempKey, "Ds");
                } else if (i == 19 && j == 6) {
                    soft.put(tempKey, "Ds");
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
                        pair.put(tempKey, "Dh");
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
     * @return total in Integer
     */
    private Integer total() {
        return total;
    }

    /**
     * calculates player card's total1 in Split
     * @return total1 in Integer
     */
    private Integer total1() {
        return total1;
    }

    /**
     * calculates player card's total2 in Split
     * @return total2 in Integer
     */
    private Integer total2() {
        return total2;
    }

    /**
     * Determines whether to Hit, Stand, Split, or Double.
     * @return Hit, Stand, Split, or Double as String.
     */
    private String decision() {
        if (splitted) {
            if (on1stSplit) {
                String decis = total1().toString() + dealerCard;
                if (bsoft1 != 0) {
                    if (total1() <= 21) {
                        return soft.get(decis);
                    } else {
                        int tempSoft= bsoft1;
                        while (tempSoft != 0) {
                            Integer tempTotal = total1() - 10;
                            if (tempTotal <= 21 && tempTotal >= 0) {
                                return soft.get(tempTotal.toString() + dealerCard);
                            }
                            tempSoft--;
                        }
                    }
                } else {
                    return hard.get(decis);
                }
            } else { // on 2nd split
                String decis = total2().toString() + dealerCard;
                if (bsoft2 != 0) {
                    if (total2() <= 21 && total2() >= 0) {
                        return soft.get(decis);
                    } else {
                        int tempSoft= bsoft2;
                        while (tempSoft != 0) {
                            Integer tempTotal = total2() - 10;
                            if (tempTotal <= 21 && tempTotal >= 0) {
                                return soft.get(tempTotal.toString() + dealerCard);
                            }
                            tempSoft--;
                        }
                    }
                } else {
                    return hard.get(decis);
                }
            }
        } // not splitted
        String decis = total().toString() + dealerCard;
        if (bpair) {
            Integer temp = playerCard1;
            return pair.get(temp.toString() + dealerCard);
        } else if (bsoft != 0) {
            if (total() <= 21 && total() >= 0) {
                return soft.get(decis);
            }
            int tempSoft = bsoft;
            while (tempSoft != 0) {
                Integer tempTotal = total() - 10;
                if (tempTotal <= 21 && tempTotal >= 0) {
                    return soft.get(tempTotal.toString() + dealerCard);
                }
                tempSoft--;
            }
        }
        return hard.get(decis);
    }

    /**
     * player HIT a card. Update player's total
     * @param card : the hit-ted card
     */
    private void hit(int card) {
        total += card;
        if (card == 11) {
            bsoft++;
        }
    }

    /**
     * player HIT a card to 1st splitted card
     * @param card : the hit-ted card
     */
    private void hit1(int card) {
        total1 += card;
        if (card == 11) {
            bsoft1++;
        }
    }

     /**
     * player HIT a card to 2nd splitted card
     * @param card : the hit-ted card
     */
    private void hit2(int card) {
        total2 += card;
        if (card == 11) {
            bsoft2++;
        }
    }

    /**
     * Shows status of the game.
     */
    private void status() {
        Boolean printed = false;
        System.out.println();
        System.out.print("Dealer: ");
        System.out.print(dealerCard);
        System.out.print(" Player: ");
        if (total <= 21 && total >= 0 && !splitted) {
            System.out.print(total);
            printed = true;
        }
        if (splitted) {
            System.out.print("Splitted 1: ");
            if (total1 <= 21 && total1 >= 0) {
                System.out.print(total1);
                printed = true;
            }
            int temp = bsoft1;
            int tempTotal = total1;
            while (temp != 0) {
                tempTotal -= 10;
                if (tempTotal <= 21 && tempTotal >= 0) {
                    if (!printed) {
                        System.out.print(tempTotal);
                        printed = true;
                    } else {
                        System.out.print(" or ");
                        System.out.print(tempTotal);
                    }
                }
                temp--;
            }
            System.out.print(" Splitted 2: ");
            System.out.print(total2);
            temp = bsoft2;
            tempTotal = total2;
            while (temp != 0) {
                tempTotal -= 10;
                if (tempTotal <= 21 && tempTotal >= 0) {
                    if (!printed) {
                        System.out.print(tempTotal);
                        printed = true;
                    } else {
                        System.out.print(" or ");
                        System.out.print(tempTotal);
                    }
                }
                temp--;
            }
        }
        else if (bsoft != 0) {
            int temp = bsoft;
            int tempTotal = total;
            while (temp != 0) {
                tempTotal -= 10;
                if (tempTotal <= 21 && tempTotal >= 0) {
                    if (!printed) {
                        System.out.print(tempTotal);
                        printed = true;
                    } else {
                        System.out.print(" or ");
                        System.out.print(tempTotal);
                    }
                }
                temp--;
            }
        }
        System.out.println();
    }

    /**
     * Starts a new game.
     */
    private void first() {
        String decision = decision();
        if (decision.equals("H")) {
            System.out.println("I recommend HIT");
            System.out.println("Type new card.");
        } else if (decision.equals("S")) {
            System.out.println("I recommend STAND");
            //System.out.println("Type Dealer's cards.")
        } else if (decision.equals("P")) {
            splitted = true;
            on1stSplit = true;
            bpair = false;
            total1 = playerCard1;
            total2 = playerCard2; //should equal playerCard1
            System.out.println("I recommend SPLIT and HIT");
            System.out.println("Type new card for 1st split.");
        } else if (decision.equals("Dh") || decision.equals("Ds")) {
            System.out.println("I recommend DOUBLE");
            System.out.println("Type new card.");
        }
    }

    /**
     * runs after player hits
     */
    private void next() {
        String decision = decision();
        if (decision.equals("H")) {
            if (splitted) {
                first1 = false;
            }
            System.out.println("I recommend HIT");
            System.out.println("Type new card.");
        } else if (decision.equals("S")) {
            System.out.println("I recommend STAND");
            if (splitted && on1stSplit) {
                on1stSplit = false;
                first1 = false;
                System.out.println("Continue for 2nd Splitted Card.");
            }
            // } else if (splitted && !on1stSplit) {
            //     System.out.println("Type Dealer's cards.");
            // }
        } else if (decision.equals("Ds")) {
            if (splitted && on1stSplit && first1) {
                System.out.println("I recommend DOUBLE on 1st Split");
            } else if (splitted && !on1stSplit) {
                System.out.println("I recommend DOUBLE on 2nd Split");
            } else {
                System.out.println("I recommend STAND");
            }
        } else if (decision.equals("Dh")) {
            if (splitted && on1stSplit && first1) {
                System.out.println("I recommend DOUBLE on 1st Split");
            } else if (splitted && !on1stSplit) {
                System.out.println("I recommend DOUBLE on 2nd Split");
            } else {
                System.out.println("I recommend HIT");
            }
        }
    }

    /**
     * Welcoming screen. Shows once at the start of the program.
     */
    private static void welcome() {
        System.out.println("  -----------------------------------------");
        System.out.println("       Welcome to BlackJackOptimizer.");
        System.out.println("Remember this only works for American Style.");
        System.out.println("     Type: 'help' for list of commands");
        System.out.println("             @author Fred Lee");
        System.out.println("  -----------------------------------------");
        System.out.println("Type : [Dealer's card] [Your 1st Card] [2nd Card]");
        System.out.println("to start a new game.");
        System.out.println("Type : 'end' to stop program.");
    }

    /**
     * main method
     * @param args : user's inputs
     */
    public static void main(String[] args) {
        BlackJackOptimizer.welcome();

        BlackJackOptimizer bjo = new BlackJackOptimizer();
        bjo.init();

        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();
        while (!userInput.toLowerCase().equals("end")) {
            String[] numbers = userInput.split("\\s+");
            if (numbers.length != 3 && numbers.length != 1) {
                System.out.println("Unrecognized Command.");
                break;
            } else {
                if (numbers.length == 3) {
                    bjo.newCards1.clear();
                    bjo.newCards2.clear();
                    Integer[] numbersInt = new Integer[3];
                    for (int o = 0; o < numbersInt.length; o++) {
                        if (numbers[o].equals("A") || numbers[o].equals("a")
                            || numbers[o].equals("1")) {
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
                        bjo.status();
                        bjo.first();
                    }
                    // else
                    // 1) hit: hit/stand
                    // 2) split: hit/double/stand > hit/stand. for both.
                    // 3) stand: done.
                } else { // length == 1
                    if (!bjo.initialized) {
                        System.out.println("Please start a new game first.");
                    } else {
                        if (!bjo.splitted) {
                            if (bjo.newCards1.containsKey(numbers[0])) {
                                int val = bjo.newCards1.get(numbers[0]);
                                bjo.newCards1.put(numbers[0], val++);
                            } else {
                                bjo.newCards1.put(numbers[0], 1);
                            }
                            if (numbers[0].equals("A") || numbers[0].equals("a")
                                || numbers[0].equals("1")) {
                                bjo.hit(11);
                                bjo.bsoft++;
                            } else if (numbers[0].equals("K") || numbers[0].equals("k")
                                || numbers[0].equals("Q") || numbers[0].equals("q")
                                || numbers[0].equals("J") || numbers[0].equals("j")) {
                                bjo.hit(10);
                            } else {
                                bjo.hit(Integer.parseInt(numbers[0]));
                            }
                        } else if (bjo.on1stSplit) {
                            if (bjo.newCards1.containsKey(numbers[0])) {
                                int val = bjo.newCards1.get(numbers[0]);
                                bjo.newCards1.put(numbers[0], val++);
                            } else {
                                bjo.newCards1.put(numbers[0], 1);
                            }
                            if (numbers[0].equals("A") || numbers[0].equals("a")
                                || numbers[0].equals("1")) {
                                bjo.hit1(11);
                                bjo.bsoft++;
                            } else if (numbers[0].equals("K") || numbers[0].equals("k")
                                || numbers[0].equals("Q") || numbers[0].equals("q")
                                || numbers[0].equals("J") || numbers[0].equals("j")) {
                                bjo.hit1(10);
                            } else {
                                bjo.hit1(Integer.parseInt(numbers[0]));
                            }
                        } else {
                            if (bjo.newCards2.containsKey(numbers[0])) {
                                int val = bjo.newCards2.get(numbers[0]);
                                bjo.newCards2.put(numbers[0], val++);
                            } else {
                                bjo.newCards2.put(numbers[0], 1);
                            }
                            if (numbers[0].equals("A") || numbers[0].equals("a")
                                || numbers[0].equals("1")) {
                                bjo.hit2(11);
                                bjo.bsoft++;
                            } else if (numbers[0].equals("K") || numbers[0].equals("k")
                                || numbers[0].equals("Q") || numbers[0].equals("q")
                                || numbers[0].equals("J") || numbers[0].equals("j")) {
                                bjo.hit2(10);
                            } else {
                                bjo.hit2(Integer.parseInt(numbers[0]));
                            }
                        }
                        if (bjo.total() > 21 && bjo.bsoft != 0) {
                            bjo.total -= 10;
                            bjo.bsoft--;
                        }
                        bjo.status();
                        if (bjo.bsoft != 0 && bjo.total > 21) {
                            bjo.total -= 10;
                            bjo.bsoft--;
                        }
                        if (bjo.bsoft1 != 0 && bjo.total1 > 21) {
                            bjo.total1 -= 10;
                            bjo.bsoft1--;
                        }
                        if (bjo.bsoft2 != 0 && bjo.total2 > 21) {
                            bjo.total2 -= 10;
                            bjo.bsoft2--;
                        }
                        if (!bjo.splitted && bjo.bsoft == 0 && bjo.total() > 21) {
                            System.out.println("You lost! :(");
                        } else if (bjo.bsoft != 0 && 
                            (bjo.total() > 21 && bjo.total() - 10 > 21)) {
                            System.out.println("You lost! :(");
                        } else if (bjo.splitted && bjo.total1 > 21 && bjo.total2 > 21) {
                            System.out.println("You lost! :(");
                        } else {
                            bjo.next();
                        }
                    }
                }
                in = new Scanner(System.in);
                userInput = in.nextLine();
            }
        }            
    }
}
