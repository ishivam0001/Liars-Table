public class SabotageCard extends Card {

    private SabotageEffect effect;

    public SabotageCard(SabotageEffect effect) {
        super(CardType.SABOTAGE);
        this.effect = effect;
    }

    @Override
    public void play(Game game, Player player) {

        System.out.println(player.getName() + " played SABOTAGE: " + effect);

        switch(effect) {

            case SWAP_HANDS:
                game.swapHands(player);
                break;

            case REVERSE_TURN:
                game.reverseTurn();
                break;
        }
    }

    public String toString() {
        return "SABOTAGE - " + effect;
    }
}