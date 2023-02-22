package dungeonmania.goals;

import dungeonmania.Game;

public class TreasureGoal extends GoalType {

    @Override
    public boolean achieved(Game game, Goal goal1, Goal goal2, int target) {
        return game.getInitialTreasureCount() - game.getCurrentTreasureCount() >= target;
    }

    @Override
    public String toString(Game game, Goal goal1, Goal goal2) {
        return ":treasure";
    }

}
