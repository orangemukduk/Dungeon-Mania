package dungeonmania.entities.playerState;

public class InvincibleState implements PlayerState {
    public void setState(PlayerStateContext context) {
        context.setCurrentState(this);
    }
}
