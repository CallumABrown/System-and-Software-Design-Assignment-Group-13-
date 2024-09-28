import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class High_Score_MenuTest {

    private High_Score_Menu menu; // Class member to hold the instance

    @BeforeEach
    void setUp() {
        menu = new High_Score_Menu(); // Initialize the menu
        menu.createDefaultHighScores(); // Create default high scores for testing
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAddHighScore() {
        menu.addHighScore("Player1", 300);
        menu.addHighScore("Player2", 100);
        menu.addHighScore("Player3", 200);

        // Assert that scores are sorted correctly
        assertEquals("Player1", menu.highScoresLeaderboard.get(0).name);
        assertEquals(300, menu.highScoresLeaderboard.get(0).score);
        assertEquals("Player3", menu.highScoresLeaderboard.get(1).name);
        assertEquals(200, menu.highScoresLeaderboard.get(1).score);
        assertEquals("Player2", menu.highScoresLeaderboard.get(2).name);
        assertEquals(100, menu.highScoresLeaderboard.get(2).score);
    }

    @Test
    void testResetHighScores() {
        menu.addHighScore("Player1", 300);
        menu.resetHighScores(); // Reset the high scores

        // Assert that high scores are reset to default
        assertEquals("No Record", menu.highScoresLeaderboard.get(0).name);
        assertEquals(0, menu.highScoresLeaderboard.get(0).score);
    }

    @Test
    void testLoadHighScores() {
        menu.loadHighScores(); // Load high scores

        // Assert that high scores are loaded correctly
        assertEquals(High_Score_Menu.HIGH_SCORES_COUNT, menu.highScoresLeaderboard.size());
        assertEquals("No Record", menu.highScoresLeaderboard.get(0).name);
        assertEquals(0, menu.highScoresLeaderboard.get(0).score);
    }

    @Test
    void testHighScoreSorting() {
        menu.addHighScore("Player1", 100);
        menu.addHighScore("Player2", 300);
        menu.addHighScore("Player3", 200);

        // Assert that high scores are sorted correctly
        assertEquals("Player2", menu.highScoresLeaderboard.get(0).name);
        assertEquals(300, menu.highScoresLeaderboard.get(0).score);
        assertEquals("Player3", menu.highScoresLeaderboard.get(1).name);
        assertEquals(200, menu.highScoresLeaderboard.get(1).score);
        assertEquals("Player1", menu.highScoresLeaderboard.get(2).name);
        assertEquals(100, menu.highScoresLeaderboard.get(2).score);
    }
}
