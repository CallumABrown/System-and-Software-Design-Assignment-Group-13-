import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Options_Menu extends JFrame{
    public static Integer window_width;
    public static Integer window_height;
    public static Integer game_level;
    public static Boolean music;
    public static Boolean sound_effects;
    public static Boolean extend;
    public static String player1_type;
    public static String player2_type;
    public Options_Menu(){
        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        Panel mainPanel = new Panel(new GridBagLayout());

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(20, 50, 0, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JSlider slider1 = new JSlider(5, 15, window_width);
        slider1.setMajorTickSpacing(1);
        slider1.setMinorTickSpacing(1);
        slider1.setPaintTrack(true);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);
        mainPanel.add(slider1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 50, 0, 30);
        JLabel label1 = new JLabel("Field Width (Number of cells): " + slider1.getValue());
        mainPanel.add(label1, gbc);

        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                label1.setText("Field Width (Number of cells): " + slider1.getValue());
                window_width = slider1.getValue();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(20, 50, 0, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JSlider slider2 = new JSlider(15, 30, window_height);
        slider2.setMajorTickSpacing(1);
        slider2.setMinorTickSpacing(1);
        slider2.setPaintTrack(true);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        mainPanel.add(slider2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 50, 0, 30);
        JLabel label2 = new JLabel("Field Height (Number of cells): " + slider2.getValue());
        mainPanel.add(label2, gbc);

        slider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                label2.setText("Field Height (Number of cells): " + slider2.getValue());
                window_height = slider2.getValue();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(20, 50, 0, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JSlider slider3 = new JSlider(15, 30, 20);
        slider3.setMajorTickSpacing(1);
        slider3.setMinorTickSpacing(1);
        slider3.setPaintTrack(true);
        slider3.setPaintTicks(true);
        slider3.setPaintLabels(true);
        mainPanel.add(slider3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 50, 0, 30);
        JLabel label3 = new JLabel("Game Level: " + slider2.getValue());
        mainPanel.add(label3, gbc);

        slider3.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                label3.setText("Game Level: " + slider3.getValue());
            }
        });

        gbc.gridx = 4;
        gbc.gridy = 3;
        JLabel label4 = new JLabel("Extended Mode (On/Off): ");
        mainPanel.add(label4, gbc);

        gbc.gridy = 1;
        JLabel label5 = new JLabel("Music (On/Off): ");
        mainPanel.add(label5, gbc);

        gbc.gridy = 2;
        JLabel label6 = new JLabel("Sound effects (On/Off): ");
        mainPanel.add(label6, gbc);

        gbc.gridy = 4;
        JLabel label7 = new JLabel("Player One Type: ");
        mainPanel.add(label7, gbc);

        gbc.gridy = 5;
        JLabel label8= new JLabel("Player Two Type: ");
        mainPanel.add(label8, gbc);

        gbc.insets = new Insets(25, 230, 0, 0);
        gbc.gridx = 5;
        gbc.gridy = 1;
        JCheckBox checkBox1 = new JCheckBox();
        checkBox1.setSelected(music);
        mainPanel.add(checkBox1, gbc);

        checkBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox cb = (JCheckBox) event.getSource();
                if (checkBox1.isSelected()) {
                    music = true;
                } else {
                    music = false;
                }
            }
        });

        gbc.gridy = 2;
        JCheckBox checkBox2 = new JCheckBox();
        checkBox2.setSelected(sound_effects);
        mainPanel.add(checkBox2, gbc);

        checkBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox cb = (JCheckBox) event.getSource();
                if (checkBox2.isSelected()) {
                    sound_effects = true;
                } else {
                    sound_effects = false;
                }
            }
        });

        gbc.gridy = 3;
        JCheckBox checkBox3 = new JCheckBox();
        checkBox3.setSelected(extend);
        mainPanel.add(checkBox3, gbc);

        String[] user_types = {"Human", "AI", "External"};
        gbc.gridy = 4;
        JComboBox list1 = new JComboBox(user_types);
        list1.setSelectedItem(player1_type);
        mainPanel.add(list1, gbc);

        list1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player1_type = (String) list1.getSelectedItem();
            }
        });

        gbc.gridy = 5;
        JComboBox list2 = new JComboBox(user_types);
        list2.setEnabled(extend);
        list2.setSelectedItem(player2_type);
        mainPanel.add(list2, gbc);

        list2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player2_type = (String) list2.getSelectedItem();
            }
        });

        checkBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBox cb = (JCheckBox) event.getSource();
                if (checkBox3.isSelected()) {
                    extend = true;
                    list2.setEnabled(extend);
                } else {
                    extend = false;
                    list2.setEnabled(extend);
                }
            }
        });

        gbc.gridx = 3;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(50, 0, 0, 0);
        Button return_button = new Button("Return");
        return_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main_Menu();
                dispose();
            }
        });
        mainPanel.add(return_button, gbc);

        add(mainPanel, BorderLayout.CENTER);

        Panel footer = new Panel();
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        Label footerLabel = new Label("Authors: Adam Filipczuk, Callum Brown, Gauruv Grover, Steve Drewery");
        footer.add(footerLabel);

        add(footer, BorderLayout.SOUTH);

        setSize(800, 600);

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
