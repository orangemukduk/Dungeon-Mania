package dungeonmania.mvp;


import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.*;

public class SunStoneTest {


    @Test
    @Tag("18-1")
    @DisplayName("Test player can pick up SunStone and add to inventory")
    public void pickUpSunStone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_pickUpSunStone", "c_sunStoneTest_pickUpSunStone");

        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // pick up sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());

    }

    @Test
    @Tag("18-2")
    @DisplayName("Test opening multiple doors with a Sunstone")
    public void openDoorsWithSunStone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_openDoorsWithSunStone",
        "c_sunStoneTest_openDoorsWithSunStone");

        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        // pick up sunstone
        res = dmc.tick(Direction.RIGHT);
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());

        //check player opens and walks through door

        res = dmc.tick(Direction.RIGHT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

        //check player opens next door and walks through
        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());



        }

    @Test
    @Tag("18-3")
    @DisplayName("Test building a shield with SunStone")
    public void buildShieldWithSunStone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_sunStoneTest_buildShieldWithSunStone", "c_sunStoneTest_buildShieldWithSunStone");
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Wood x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());

        // Pick up Sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Shield
        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());

        // Materials used in construction disappear from inventory except for sunstone
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }


@Test
    @Tag("18-4")
    @DisplayName("Testing Sunstone reaches Treasure Goal")
    public void sunstoneTreasureGoal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
            "d_sunStoneTest_sunStoneTreasureGoal", "c_sunStoneTest_sunStoneTreasureGoal");

        // collect sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }
}
