import javax.swing.*;
import java.awt.*;

public class Game_Screen extends JFrame {
    public static int WIDTH, HEIGHT;
    private Game_Area board;
    private Game_Area board2;

    public Game_Screen() {
        setTitle("Tetris");
        HEIGHT = (32 * Options_Menu.window_height);
        WIDTH = (int) (31.5 * Options_Menu.window_width) + 200;
        if (WIDTH < 420) {
            WIDTH = 420;
        }
        if (Options_Menu.extend) {
            WIDTH *= 2;
        }
        setSize(WIDTH, HEIGHT + 25);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Set BorderLayout for the main frame
        setLayout(new BorderLayout());

        // Create a panel for the game boards with GridLayout
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(1, 2)); // Two columns for the game boards

        board = new Game_Area(1);
        gamePanel.add(board); // Add the first board

        if (Options_Menu.extend) {
            board2 = new Game_Area(2);
            gamePanel.add(board2); // Add the second board side-by-side
        }

        // Add the gamePanel to the center of the frame
        add(gamePanel, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setBackground(Color.BLACK);
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel footerLabel = new JLabel("Authors: Adam Filipczuk, Callum Brown, Gauruv Grover, Steve Drewery");
        footerLabel.setForeground(Color.WHITE);
        footer.add(footerLabel);

        // Add footer to the bottom of the frame
        add(footer, BorderLayout.SOUTH);

        setVisible(true);

        addKeyListener(board);
        if (board2 != null) {
            addKeyListener(board2);
        }
    }

    public static void main(String[] args) {
        new Game_Screen();
    }
}
