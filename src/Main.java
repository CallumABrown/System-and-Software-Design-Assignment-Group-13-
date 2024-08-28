
public class Main {
    public static void main(String[] args) {
        splashScreen splash1 = new splashScreen(2500);
        splash1.showSplash();

        Options_Menu.window_height = 20;
        Options_Menu.window_width = 10;
        new Main_Menu();
    }
}