package dungeonmania.mvp;


import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
public class MidnightArmourTest {
    @Test
    @Tag("20-1")
    @DisplayName("Test building Midnight Armour")
    public void buildMidnightArmour() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest_buildMidnightArmour",
        "c_midnightArmourTest_buildMidnightArmour");

        // Pick up Sword
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        //Pick up Sunstone x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Midnight Armour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());
    }

    @Test
    @Tag("20-2")
    @DisplayName("Test unable to build armour when zombies are in dungeon")
    public void buildArmourFailZombie() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest_buildArmourFailZombie",
        "c_midnightArmourTest_buildArmourFailZombie");

        // Pick up Sword
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        //Pick up Sunstone x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        //Zombie exists in dungeon
        List<EntityResponse> entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "zombie_toast"));
        // Fail Build Midnight Armour
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
    }

    @Test
    @Tag("20-3")
    @DisplayName("Test midnight armour increases attack damage")
    public void midnightArmourIncreasesDamage() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest_ArmourIncreasesDamage",
        "c_midnightArmourTest_ArmourIncreasesDamage");
        // Pick up Sword
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        //Pick up Sunstone x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Midnight Armour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        res = dmc.tick(Direction.RIGHT);

        // check if battle occurs
        assertEquals(1, res.getBattles().size());

        //with armour equipped, check if mercenary dies. mercenary attack = 12, player attack = 10, armour attack = 3
        List<EntityResponse> entities = res.getEntities();
        assertTrue(TestUtils.countEntityOfType(entities, "mercenary") == 0);
    }

    @Test
    @Tag("20-4")
    @DisplayName("Test midnight armour increases defence")
    public void midnightArmourIncreasesDefence() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest_ArmourIncreasesDefence",
        "c_midnightArmourTest_ArmourIncreasesDefence");

        // Pick up Sword
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        //Pick up Sunstone x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Midnight Armour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        res = dmc.tick(Direction.RIGHT);

        // check if battle occurs
        assertEquals(1, res.getBattles().size());

        //with armour equipped, check if player dies. mercenary attack = 12, player health = 10, armour defense = 3
        List<EntityResponse> entities = res.getEntities();
        assertTrue(TestUtils.countEntityOfType(entities, "player") == 1);
    }
}
