package dungeonmania.mvp;
import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
public class SceptreTest {
    @Test
    @Tag("19-1")
    @DisplayName("Test building Sceptre")
    public void buildSceptre() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_buildSceptre", "c_sceptreTest_buildSceptre");

        // Pick up Wood x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        //Pick up Key x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        //Pick up Sunstone x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
    }

    @Test
    @Tag("19-2")
    @DisplayName("Test mind control Assassin")
    public void mindControlAssassin() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_mindControlAssassin", "c_sceptreTest_mindControlAssassin");
        String assassinId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();
        // Pick up Wood x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        //Pick up Key x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        //Pick up Sunstone x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        res = assertDoesNotThrow(() -> dmc.interact(assassinId));
        //Since assassin next to player, check if battle occurs
        assertEquals(0, res.getBattles().size());

    }

    @Test
    @Tag("19-3")
    @DisplayName("Test mind control lasts certain duration")
    public void mindControlDuration() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest_mindControlDuration", "c_sceptreTest_mindControlDuration");
        String mercenaryId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        // Pick up Wood x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        //Pick up Key x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        //Pick up Sunstone x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // mind control mercenary
        res = assertDoesNotThrow(() -> dmc.interact(mercenaryId));
        res = dmc.tick(Direction.LEFT);

        //mind control effects have worn out
        //If player can interact with mercenary again, that means the mercenary had returned to enemy state
        res = assertDoesNotThrow(() -> dmc.interact(mercenaryId));
        //Since assassin next to player, check if battle occurs
        assertEquals(0, res.getBattles().size());
    }
}
