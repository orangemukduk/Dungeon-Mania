package dungeonmania.entities.buildables;

// import dungeonmania.util.Position;

public class Sceptre extends Buildable {
    public static final int MIND_CONTROL_DURATION = 2;
    private int currentDuration;

    private int mindControlDuration = MIND_CONTROL_DURATION;
    public Sceptre(int mindControlDuration) {
        super(null);
        this.mindControlDuration = mindControlDuration;
    }

    public int getMindControlDuration() {
        return mindControlDuration;
    }

    public int getCurrentDuration() {
        return currentDuration;
    }

    public void setCurrentDuration(int currentDuration) {
        this.currentDuration = currentDuration;
    }
}
