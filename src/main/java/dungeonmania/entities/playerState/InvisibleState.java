package dungeonmania.entities.playerState;


public class InvisibleState implements PlayerState {
    public void setState(PlayerStateContext context) {
        context.setCurrentState(this);
    }

}
