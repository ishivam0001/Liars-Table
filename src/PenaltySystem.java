import java.util.*;

public class PenaltySystem {

    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();
    
    // --- TOPIC W07: JAVA COLLECTION (HASHMAP) ---
    private Map<Player, List<Glass>> playerGlassesMap = new HashMap<>();

    private void resetGlassesForPlayer(Player player) {
        List<Glass> glasses = new ArrayList<>();
        int poison = random.nextInt(4);

        for(int i=0; i<4; i++) {
            glasses.add(new Glass(i, i==poison));
        }
        
        playerGlassesMap.put(player, glasses);
    }

    public void applyPenalty(Player player) {
        
        if (!playerGlassesMap.containsKey(player) || playerGlassesMap.get(player).isEmpty()) {
            resetGlassesForPlayer(player);
        }

        List<Glass> playerGlasses = playerGlassesMap.get(player);

        System.out.println("\nPENALTY for " + player.getName());

        for(int i = 0; i < playerGlasses.size(); i++) {
            System.out.println(i + ": Glass " + playerGlasses.get(i).getId());
        }

        int choice = -1;

        if(player.isBot()) {
            
            choice = random.nextInt(playerGlasses.size());
            System.out.println(player.getName() + " chooses index " + choice + " (Glass " + playerGlasses.get(choice).getId() + ")");

        } else {
            
            System.out.println("Choose a glass index (0 to " + (playerGlasses.size() - 1) + "):");
            
            // --- CRASH-PROOF INPUT ---
            while(true) {
                try {
                    choice = Integer.parseInt(scanner.nextLine().trim());
                    if(choice >= 0 && choice < playerGlasses.size()) break;
                    System.out.println("Invalid answer, Please Try Again!");
                } catch(Exception e) {
                    System.out.println("Invalid answer, Please Try Again!");
                }
            }
        }

        Glass chosen = playerGlasses.remove(choice);

        if(chosen.hasPoison()) {

            System.out.println("POISON! " + player.getName() + " died!");
            player.setAlive(false);
            
            resetGlassesForPlayer(player);      

        } else {

            System.out.println(player.getName() + " survived.");
            System.out.println(player.getName() + " now has only " + playerGlasses.size() + " glass(es) left on their personal table...");
        }
    }
}