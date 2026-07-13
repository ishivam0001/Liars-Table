import java.util.*;

public class Player {

    protected String name;
    protected boolean bot;
    protected boolean alive = true;
    protected int insightTokens = 2; 
    
    // --- TOPIC W08 & W09: DOUBLE LINKED LIST ---
    private LinkedList<Card> hand = new LinkedList<>();
    
    protected Scanner scanner = new Scanner(System.in);
    protected Random random = new Random();

    public Player(String name, boolean bot) {
        this.name = name;
        this.bot = bot;
    }

    public void addCard(Card c) { hand.add(c); }
    public boolean isBot() { return bot; }
    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }
    public String getName() { return name; }
    public boolean hasCards() { return !hand.isEmpty(); }

    public int getInsightTokens() { return insightTokens; }
    public void useInsightToken() { insightTokens--; }

    public void takeTurn(Game game) {
        if(bot) botTurn(game);
        else humanTurn(game);
    }

    public List<Card> getCards() {
        return hand;
    }

    private void humanTurn(Game game) {
        System.out.println("Table card is: " + game.getTableCard());

        System.out.println("\nYour current cards:");
        for(int j=0; j<hand.size(); j++) {
            System.out.println(j + ": " + hand.get(j));
        }

        int maxPlay = Math.min(3, hand.size());
        System.out.println("\nHow many cards do you want to play? (1-" + maxPlay + ")");
        
        // --- CRASH-PROOF INPUT ---
        int amount = -1;
        while(true) {
            try {
                amount = Integer.parseInt(scanner.nextLine().trim());
                if(amount >= 1 && amount <= maxPlay) break;
                System.out.println("Invalid answer, Please Try Again!");
            } catch(Exception e) {
                System.out.println("Invalid answer, Please Try Again!");
            }
        }

        // --- TOPIC W13: QUEUE ---
        Queue<Card> played = new LinkedList<>();

        for(int i=0; i<amount; i++) {
            if (i > 0) {
                System.out.println("\nYour remaining cards:");
                for(int j=0; j<hand.size(); j++) {
                    System.out.println(j + ": " + hand.get(j));
                }
            }

            System.out.println("Choose card index (" + (i+1) + " of " + amount + "):");
            
            // --- CRASH-PROOF INPUT ---
            int index = -1;
            while(true) {
                try {
                    index = Integer.parseInt(scanner.nextLine().trim());
                    if(index >= 0 && index < hand.size()) {
                        Card c = hand.remove(index); 
                        played.offer(c); 
                        break;
                    } else {
                        System.out.println("Invalid answer, Please Try Again!");
                    }
                } catch(Exception e) {
                    System.out.println("Invalid answer, Please Try Again!");
                }
            }
        }

        System.out.println(name + " placed " + played.size() + " card(s) face down.");

        for(Card c : played)
            c.play(game,this);

        game.setLastPlay(this, played);
    }

    private void botTurn(Game game) {
        int maxPlay = Math.min(3, hand.size());
        int playCount = random.nextInt(maxPlay) + 1;

        Queue<Card> played = new LinkedList<>();

        for(int i = 0; i < playCount; i++) {
            if(hand.isEmpty()) break;
            int index = random.nextInt(hand.size());
            Card c = hand.remove(index);
            if(c != null) played.offer(c);
        }

        System.out.println(name + " placed " + played.size() + " card(s) face down.");

        for(Card c : played) c.play(game,this);

        game.setLastPlay(this, played);
    }
}