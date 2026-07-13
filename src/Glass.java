public class Glass {

    private int id;
    private boolean poison;

    public Glass(int id, boolean poison) {
        this.id = id;
        this.poison = poison;
    }

    public int getId() {
        return id;
    }

    public boolean hasPoison() {
        return poison;
    }
}