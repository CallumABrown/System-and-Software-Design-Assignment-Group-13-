import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main_Menu extends Frame {
    public Main_Menu(){
        setTitle("");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //Play button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(110, 50, 0, 30); // Top padding to push down, right padding for space between buttons
        Button button1 = new Button("Play");
        button1.setPreferredSize(new Dimension(100, 50));
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Game_Screen();
                dispose();
            }
        });
        add(button1, gbc);

        //configuration button
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(110, 30, 0, 50); // Top padding to push down, left padding for space between buttons
        Button button2 = new Button("Configuration");
        button2.setPreferredSize(new Dimension(100, 50));
        add(button2, gbc);

        //High Scores button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 50, 0, 30); // Top padding for space between rows, right padding for space between buttons
        Button button3 = new Button("High Scores");
        button3.setPreferredSize(new Dimension(100, 50));
        add(button3, gbc);

        //Exit button
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 30, 0, 50); // Top padding for space between rows, left padding for space between buttons
        Button button4 = new Button("Exit");
        button4.setPreferredSize(new Dimension(100, 50));
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(button4, gbc);

        setSize(400, 600);

        centerWindow();

        setVisible(true);

        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                dispose();
            }
        });
    }

    private void centerWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }
}
