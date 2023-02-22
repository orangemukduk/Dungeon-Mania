package dungeonmania.goals;

import dungeonmania.Game;

public class OrGoal extends GoalType {

    @Override
    public boolean achieved(Game game, Goal goal1, Goal goal2, int target) {
        return goal1.achieved(game) || goal2.achieved(game);
    }

    @Override
    public String toString(Game game, Goal goal1, Goal goal2) {
        if (goal1.achieved(game) || goal2.achieved(game)) return "";
                else return "(" + goal1.toString(game) + " OR " + goal2.toString(game) + ")";
    }

}
