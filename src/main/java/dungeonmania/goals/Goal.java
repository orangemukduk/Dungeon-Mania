package dungeonmania.goals;

import dungeonmania.Game;

public class Goal {
    private GoalType type;
    private int target;
    private Goal goal1;
    private Goal goal2;

    public Goal(GoalType type) {
        this.type = type;
    }

    public Goal(GoalType type, int target) {
        this.type = type;
        this.target = target;
    }

    public Goal(GoalType type, Goal goal1, Goal goal2) {
        this.type = type;
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    /**
     * @return true if the goal has been achieved, false otherwise
     */
    public boolean achieved(Game game) {
        if (game.getPlayer() == null) return false;
        return type.achieved(game, goal1, goal2, target);
    }

    public String toString(Game game) {
        if (this.achieved(game)) return "";
        return type.toString(game, goal1, goal2);
    }

}
