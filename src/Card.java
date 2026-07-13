public abstract class Card implements Playable {

    protected CardType type;

    public Card(CardType type) { 
        this.type = type;
    }

    public CardType getType() {
        return type;
    }

    // The abstract method required by the Playable Interface
    public abstract void play(Game game, Player player);

    public String toString() {
        return type.toString();
    }
}