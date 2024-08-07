import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game_Screen extends JFrame {
    public Game_Screen(){

        setContentPane(new Game_Area());

        setSize(400, 600);

        centerWindow();

        setVisible(true);

        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                dispose();
            }
        });
    }

    static class Game_Area extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawRect(50, 20, 210, 523);
            g.drawRect(260, 20, 90, 90);
            g.drawRect(260, 110, 105, 45);
            g.drawRect(260, 155, 105, 45);
        }
    }
    private void centerWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }
}
