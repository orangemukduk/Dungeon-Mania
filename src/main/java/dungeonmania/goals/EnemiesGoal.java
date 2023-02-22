package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.enemies.*;

public class EnemiesGoal extends GoalType {

    @Override
    public boolean achieved(Game game, Goal goal1, Goal goal2, int target) {
        return (game.getEnemiesKilled() >= target) && (game.countEntities(SpawnerAliveState.class) == 0);
    }

    @Override
    public String toString(Game game, Goal goal1, Goal goal2) {
        return ":enemies";
    }

}
