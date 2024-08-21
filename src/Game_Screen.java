import javax.swing.*;
import java.awt.*;

public class Game_Screen extends JFrame {
    public static final int WIDTH = 445, HEIGHT = 640;
    private Game_Area board;
    private JFrame window;

    public Game_Screen() {
        setTitle("Game Screen");
        setSize(WIDTH, HEIGHT + 50);
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
//        setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//
//        gbc.ipadx = 57;
//        gbc.ipady = 10;
//
//        gbc.insets = new Insets(494, 271, 0, 30);
//        Button return_button = new Button("Return");
//        return_button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new Main_Menu();
//                dispose();
//            }
//        });
//
//        add(return_button, gbc);
//
//        setSize(400, 600);
//
//        centerWindow();
//
//        setVisible(true);
//
//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                dispose();
//            }
//        });
    }

    public static void main(String[] args) {
        new Game_Screen();
    }

//    static class Game_Area extends JPanel {
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            g.drawRect(50, 20, 210, 523);
//            g.drawRect(260, 20, 90, 90);
//            g.drawRect(260, 110, 105, 45);
//            g.drawRect(260, 155, 105, 45);
//        }
//    }

//    private void centerWindow() {
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Dimension screenSize = toolkit.getScreenSize();
//        int x = (screenSize.width - getWidth()) / 2;
//        int y = (screenSize.height - getHeight()) / 2;
//        setLocation(x, y);
//    }
}
