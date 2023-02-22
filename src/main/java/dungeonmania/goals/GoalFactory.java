package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoalFactory {
    public static Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        JSONArray subgoals;
        switch (jsonGoal.getString("goal")) {
        case "AND":
            subgoals = jsonGoal.getJSONArray("subgoals");
            AndGoal and = new AndGoal();
            return new Goal(
                and,
                createGoal(subgoals.getJSONObject(0), config),
                createGoal(subgoals.getJSONObject(1), config)
            );
        case "OR":
            subgoals = jsonGoal.getJSONArray("subgoals");
            OrGoal or = new OrGoal();
            return new Goal(
                or,
                createGoal(subgoals.getJSONObject(0), config),
                createGoal(subgoals.getJSONObject(1), config)
            );
        case "exit":
            ExitGoal exit = new ExitGoal();
            return new Goal(exit);
        case "boulders":
            BoulderGoal boulder = new BoulderGoal();
            return new Goal(boulder);
        case "treasure":
            TreasureGoal treasure = new TreasureGoal();
            int treasureGoal = config.optInt("treasure_goal", 1);
            return new Goal(treasure, treasureGoal);
        case "enemies":
            EnemiesGoal enemies = new EnemiesGoal();
            int enemiesGoal = config.optInt("enemy_goal", 1);
            return new Goal(enemies, enemiesGoal);
        default:
            return null;
        }
    }
}
