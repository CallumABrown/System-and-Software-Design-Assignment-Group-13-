import javax.swing.*;
import java.awt.*;

public class Game_Screen extends JFrame {
    public static int WIDTH, HEIGHT;
    private Game_Area board;
    private JFrame window;

    public Game_Screen() {
        setTitle("Tetris");
        HEIGHT = (32 * Options_Menu.window_height);
        WIDTH = (47 * Options_Menu.window_width);
        setSize(WIDTH, HEIGHT + 25);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        board = new Game_Area();
        add(board, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setBackground(Color.BLACK);
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel footerLabel = new JLabel("Authors: Adam Filipczuk, Callum Brown, Gauruv Grover, Steve Drewery");
        footerLabel.setForeground(Color.WHITE);
        footer.add(footerLabel);

        add(footer, BorderLayout.SOUTH);

        setVisible(true);

        addKeyListener(board);
    }

    public static void main(String[] args) {
        new Game_Screen();
    }
}
