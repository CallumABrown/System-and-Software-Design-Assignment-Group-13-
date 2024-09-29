public class Main {
    public static void main(String[] args) {
        splashScreen splash1 = new splashScreen(2500);
        splash1.showSplash();

        Options_Menu.window_height = 20;
        Options_Menu.window_width = 10;
        Options_Menu.music = false;
        Options_Menu.sound_effects = false;
        Options_Menu.extend = false;
        Options_Menu.player1_type = "Human";
        Options_Menu.player2_type = "Human";
        Options_Menu.game_level = 1;
        new Main_Menu();
///place holder

    }
}