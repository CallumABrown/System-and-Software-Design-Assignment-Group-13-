import com.google.gson.annotations.SerializedName;

public class HighScoreClass {
    @SerializedName("name")
    public String name;

    @SerializedName("score")
    public int score;

    public HighScoreClass(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // Default constructor needed for Gson
    public HighScoreClass() {
    }
}
