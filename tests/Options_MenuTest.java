import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Options_MenuTest {

    @BeforeEach
    void setUp() {
        // Reset static fields before each test
        Options_Menu.window_width = 10;
        Options_Menu.window_height = 20;
        Options_Menu.game_level = 1;
        Options_Menu.music = true;
        Options_Menu.sound_effects = true;
        Options_Menu.extend = false;
        Options_Menu.player1_type = "Human";
        Options_Menu.player2_type = "AI";
    }

    @AfterEach
    void tearDown() {
        // Clean up static fields if necessary
    }

    @Test
    void testDefaultValues() {
        // Create an instance of Options_Menu
        Options_Menu optionsMenu = new Options_Menu();

        // Assert default values
        assertEquals(10, Options_Menu.window_width);
        assertEquals(20, Options_Menu.window_height);
        assertEquals(1, Options_Menu.game_level);
        assertTrue(Options_Menu.music);
        assertTrue(Options_Menu.sound_effects);
        assertFalse(Options_Menu.extend);
        assertEquals("Human", Options_Menu.player1_type);
        assertEquals("AI", Options_Menu.player2_type);
    }
}
