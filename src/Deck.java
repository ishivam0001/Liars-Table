import java.util.*;

public class Deck {

    // --- TOPIC W12: STACK IMPLEMENTATION ---
    // A deck is perfectly represented by a LIFO (Last-In-First-Out) Stack
    private Stack<Card> cards = new Stack<>();

    public Deck() {
        for(int i=0; i<6; i++) {
            cards.push(new NormalCard(CardType.KING));
            cards.push(new NormalCard(CardType.QUEEN));
            cards.push(new NormalCard(CardType.ACE));
        }

        cards.push(new NormalCard(CardType.JOKER));
        cards.push(new NormalCard(CardType.JOKER));

        cards.push(new SabotageCard(SabotageEffect.SWAP_HANDS));
        cards.push(new SabotageCard(SabotageEffect.REVERSE_TURN));

        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        // Stack uses pop() to remove and return the top item
        return cards.pop(); 
    }
}