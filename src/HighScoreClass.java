public class HighScoreClass {
    public String name;
    public int score;

    /**
     * Creates a new HighScore class instance which holds two values.
     * 
     * @param name  The name of the person who achieved the high score.
     * @param score The high score that the person achieved.
     */
    public HighScoreClass(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
