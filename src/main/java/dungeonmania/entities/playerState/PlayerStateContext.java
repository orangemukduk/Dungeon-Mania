package dungeonmania.entities.playerState;

public class PlayerStateContext {
    private PlayerState currentState;
    public PlayerStateContext(PlayerState currentState) {
        super();
        this.currentState = currentState;
    }

    public PlayerState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(PlayerState currentState) {
        this.currentState = currentState;
    }
}
