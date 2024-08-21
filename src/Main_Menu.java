import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main_Menu extends JFrame {
    public Main_Menu(){
        setTitle("Main Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        Panel mainPanel = new Panel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        mainPanel.setLayout(new GridBagLayout());

        //Play button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(110, 50, 0, 30);
        Button button1 = new Button("Play");
        button1.setPreferredSize(new Dimension(100, 50));
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Game_Screen();
                dispose();
            }
        });
        mainPanel.add(button1, gbc);

        //option button
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(110, 30, 0, 50);
        Button button2 = new Button("Options");
        button2.setPreferredSize(new Dimension(100, 50));
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Options_Menu();
                dispose();
            }
        });
        mainPanel.add(button2, gbc);

        //High Scores button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 50, 0, 30);
        Button button3 = new Button("High Scores");
        button3.setPreferredSize(new Dimension(100, 50));
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new High_Score_Menu();
                dispose();
            }
        });
        mainPanel.add(button3, gbc);

        //Exit button
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 30, 0, 50);
        Button button4 = new Button("Exit");
        button4.setPreferredSize(new Dimension(100, 50));
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        mainPanel.add(button4, gbc);

        add(mainPanel, BorderLayout.CENTER);

        Panel footer = new Panel();
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        Label footerLabel = new Label("Authors: Adam Filipczuk, Callum Brown, Gauruv Grover, Steve Drewery");
        footer.add(footerLabel);

        add(footer, BorderLayout.SOUTH);

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
