import java.util.*;

public class Game {

    private List<Player> players = new ArrayList<>();
    private Deck deck = new Deck();
    private PenaltySystem penaltySystem = new PenaltySystem();
    private CardType tableCard;

    private boolean reversed = false;
    private boolean roundResetNeeded = false;

    private Player lastPlayer;
    private Queue<Card> lastCards; 

    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();

    public CardType getTableCard() {
        return tableCard;
    }

    public void swapHands(Player player) {
        if(players.size() < 2) return;

        Random rand = new Random();
        Player other;

        do {
            other = players.get(rand.nextInt(players.size()));
        } while(other == player || !other.isAlive());

        List<Card> temp = new ArrayList<>(player.getCards());

        player.getCards().clear();
        player.getCards().addAll(other.getCards());

        other.getCards().clear();
        other.getCards().addAll(temp);

        System.out.println(player.getName() + " swapped hands with " + other.getName());
    }

    public void setup() {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();

        players.add(new Player(name,false));

        System.out.println("How many bots? (1-3)");
        
        // --- CRASH-PROOF INPUT ---
        int bots = -1;
        while(true) {
            try {
                bots = Integer.parseInt(scanner.nextLine().trim());
                if(bots >= 1 && bots <= 3) break;
                System.out.println("Invalid answer, Please Try Again!");
            } catch(Exception e) {
                System.out.println("Invalid answer, Please Try Again!");
            }
        }

        for(int i=1;i<=bots;i++) {
            players.add(new Player("Bot"+i,true));
        }

        Collections.shuffle(players);

        System.out.println("\n=== OFFICIAL TURN ORDER ===");
        for(int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". " + players.get(i).getName());
        }
        System.out.println("===========================\n");

        for(int i=0;i<5;i++) {
            for(Player p : players) {
                p.addCard(deck.drawCard());
            }
        }
    }

    public void start() {
        CardType[] types = {CardType.KING, CardType.QUEEN, CardType.ACE};
        tableCard = types[random.nextInt(types.length)];

        System.out.println("\nTABLE CARD TYPE: " + tableCard);
        System.out.println("All players must claim to play this card type.");

        int turn = 0;

        while(true) {
            
            boolean cardsLeft = false;
            for(Player p : players) {
                if(p.isAlive() && p.hasCards()) {
                    cardsLeft = true;
                    break;
                }
            }

            if(!cardsLeft) {
                System.out.println("\n*** ALL PLAYERS ARE OUT OF CARDS! REDEALING HANDS ***");
                deck = new Deck(); 
                for(Player p : players) {
                    if(p.isAlive()) {
                        for(int i = 0; i < 5; i++) {
                            p.addCard(deck.drawCard());
                        }
                    }
                }
            }

            Player current = players.get(Math.floorMod(turn,players.size()));

            if(current.isAlive() && current.hasCards()) {
                System.out.println("\n----- "+current.getName()+" TURN -----");

                current.takeTurn(this);

                Player nextPlayer = getNextAlivePlayer(turn);

                if (nextPlayer != null && nextPlayer != current) {
                    System.out.println("\n>>> " + nextPlayer.getName() + " is deciding whether to challenge " + current.getName() + "...");
                    
                    if(!nextPlayer.isBot()) {
                        humanBluffPhase(nextPlayer, current);
                    } else {
                        botBluffPhase(nextPlayer, current);
                    }
                }

                if(checkWinner()) return; 

                if (roundResetNeeded) {
                    resetRound();
                    roundResetNeeded = false; 
                }
            }

            if(reversed) turn--;
            else turn++;
        }
    }

    private void resetRound() {
        System.out.println("\n*** ROUND RESET! Clearing table and redealing... ***");
        
        lastPlayer = null;
        lastCards = null;

        CardType[] types = {CardType.KING, CardType.QUEEN, CardType.ACE};
        tableCard = types[random.nextInt(types.length)];
        System.out.println("\nNEW TABLE CARD TYPE: " + tableCard);
        System.out.println("All players must now claim to play this card type.");

        deck = new Deck(); 
        for(Player p : players) {
            p.getCards().clear(); 
            if(p.isAlive()) {
                for(int i = 0; i < 5; i++) {
                    p.addCard(deck.drawCard());
                }
            }
        }
    }

    private Player getNextAlivePlayer(int currentTurn) {
        int step = reversed ? -1 : 1;
        int checkTurn = currentTurn + step;
        
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(Math.floorMod(checkTurn, players.size()));
            if (p.isAlive()) {
                return p;
            }
            checkTurn += step; 
        }
        return null;
    }

    public void setLastPlay(Player p, Queue<Card> cards) {
        lastPlayer = p;
        lastCards = cards;
    }

    private void humanBluffPhase(Player human, Player target) {
        if(lastCards == null || lastCards.isEmpty()) return;

        System.out.println("\nLast player: " + target.getName() + " placed " + lastCards.size() + " card(s).");
        
        if (human.getInsightTokens() > 0) {
            System.out.println("You have " + human.getInsightTokens() + " Insight Token(s).");
            System.out.println("Do you want to use 1 token to peek at one of their cards? (1=yes 0=no)");
            
            // --- CRASH-PROOF INPUT ---
            int useToken = -1;
            while(true) {
                try {
                    useToken = Integer.parseInt(scanner.nextLine().trim());
                    if(useToken == 0 || useToken == 1) break;
                    System.out.println("Invalid answer, Please Try Again!");
                } catch(Exception e) {
                    System.out.println("Invalid answer, Please Try Again!");
                }
            }

            if (useToken == 1) {
                human.useInsightToken();
                Card[] playedCardsArray = lastCards.toArray(new Card[0]);
                Card peekedCard = playedCardsArray[random.nextInt(playedCardsArray.length)];
                
                System.out.println("\n>>> [INSIGHT ACTIVE] <<<");
                System.out.println("You secretly peek at one of the cards...");
                System.out.println("The card is a: " + peekedCard.getType());
                System.out.println(">>> ---------------- <<< \n");
            }
        }

        System.out.println("Call bluff on " + target.getName() + "? (1=yes 0=no)");
        
        // --- CRASH-PROOF INPUT ---
        int choice = -1;
        while(true) {
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                if(choice == 0 || choice == 1) break;
                System.out.println("Invalid answer, Please Try Again!");
            } catch(Exception e) {
                System.out.println("Invalid answer, Please Try Again!");
            }
        }

        if(choice == 1) {
            callBluff(human, target);
        } else {
            System.out.println(human.getName() + " decides not to challenge " + target.getName() + ".");
        }
    }

    private void botBluffPhase(Player bot, Player target) {
        if(lastCards == null || lastCards.isEmpty()) return;

        boolean botUsedInsight = false;
        boolean caughtLie = false;

        if (bot.getInsightTokens() > 0 && random.nextInt(100) < 30) {
            bot.useInsightToken();
            botUsedInsight = true;
            
            System.out.println("\n>>> [INSIGHT ACTIVE] <<<");
            System.out.println(bot.getName() + " used an Insight Token to secretly peek at " + target.getName() + "'s cards!");
            System.out.println(">>> ---------------- <<< \n");
            
            Card[] playedCardsArray = lastCards.toArray(new Card[0]);
            Card peekedCard = playedCardsArray[random.nextInt(playedCardsArray.length)];
            
            if (peekedCard.getType() != tableCard && peekedCard.getType() != CardType.SABOTAGE && peekedCard.getType() != CardType.JOKER) {
                caughtLie = true; 
            }
        }

        if (botUsedInsight) {
            if (caughtLie) {
                callBluff(bot, target);
            } else {
                System.out.println(bot.getName() + " decides not to challenge " + target.getName() + ".");
            }
        } else {
            if(random.nextInt(5) == 0) {
                callBluff(bot, target);
            } else {
                System.out.println(bot.getName() + " decides not to challenge " + target.getName() + ".");
            }
        }
    }

    public void callBluff(Player caller, Player target) {
        if(lastCards == null || lastCards.isEmpty()) {
            System.out.println("No cards to call bluff on.");
            return;
        }

        System.out.println(caller.getName() + " calls BLUFF on " + target.getName() + "!");

        boolean liar = false;

        for(Card c : lastCards) {
            if(c.getType() != tableCard && c.getType() != CardType.SABOTAGE && c.getType() != CardType.JOKER) {
                liar = true;
                break;
            }
        }

        if(liar) {
            System.out.println(target.getName() + " WAS LYING!");
            penaltySystem.applyPenalty(target);
        } else {
            System.out.println(target.getName() + " told the truth!");
            penaltySystem.applyPenalty(caller);
        }
        
        roundResetNeeded = true;
    }

    public void reverseTurn() {
        reversed = !reversed;
        System.out.println("TURN ORDER REVERSED!");
    }

    private boolean checkWinner() {
        int aliveCount = 0;
        Player winner = null;
        boolean humanIsAlive = false;

        for(Player p : players) {
            if(p.isAlive()) {
                aliveCount++;
                winner = p;
                if(!p.isBot()) {
                    humanIsAlive = true; 
                }
            }
        }

        if(!humanIsAlive) {
            System.out.println("\n==============================================");
            System.out.println("      YOU DRANK THE POISON! GAME OVER.      ");
            System.out.println("==============================================");
            return true; 
        }

        if(aliveCount == 1) {
            System.out.println("\n==============================================");
            System.out.println("   WINNER: " + winner.getName().toUpperCase() + " SURVIVED!");
            System.out.println("==============================================");
            return true; 
        }

        return false;
    }
}