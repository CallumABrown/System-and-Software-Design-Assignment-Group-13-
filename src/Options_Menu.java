import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Options_Menu extends JFrame{
    public Options_Menu(){
        setTitle("");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 50, 0, 30);
        JSlider slider1 = new JSlider(5, 15, 10);
        slider1.setMajorTickSpacing(1);
        slider1.setMinorTickSpacing(1);
        slider1.setPaintTrack(true);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);
        add(slider1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 50, 0, 30);
        JLabel label1 = new JLabel("Field Width (Number of cells): " + slider1.getValue());
        add(label1, gbc);

        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                label1.setText("Field Width (Number of cells): " + slider1.getValue());
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 50, 0, 30);
        JSlider slider2 = new JSlider(15, 30, 20);
        slider2.setMajorTickSpacing(1);
        slider2.setMinorTickSpacing(1);
        slider2.setPaintTrack(true);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        add(slider2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 50, 0, 30);
        JLabel label2 = new JLabel("Field Height (Number of cells): " + slider2.getValue());
        add(label2, gbc);

        slider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                label2.setText("Field Height (Number of cells): " + slider2.getValue());
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 50, 0, 30);
        JSlider slider3 = new JSlider(15, 30, 20);
        slider3.setMajorTickSpacing(1);
        slider3.setMinorTickSpacing(1);
        slider3.setPaintTrack(true);
        slider3.setPaintTicks(true);
        slider3.setPaintLabels(true);
        add(slider3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 50, 0, 30);
        JLabel label3 = new JLabel("Game Level: " + slider2.getValue());
        add(label3, gbc);

        slider3.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                label3.setText("Game Level: " + slider3.getValue());
            }
        });

        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.insets = new Insets(20, 50, 0, 30);
        JCheckBox checkBox1 = new JCheckBox();
        add(checkBox1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.insets = new Insets(20, 50, 0, 30);
        JLabel label4 = new JLabel("Extended Mode (On/Off): ");
        add(label4, gbc);

        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 50, 0, 30);
        JCheckBox checkBox2 = new JCheckBox();
        add(checkBox2, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 50, 0, 30);
        JLabel label5 = new JLabel("Music (On/Off): ");
        add(label5, gbc);

        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 50, 0, 30);
        JCheckBox checkBox3 = new JCheckBox();
        add(checkBox3, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 50, 0, 30);
        JLabel label6 = new JLabel("Sound effects (On/Off): ");
        add(label6, gbc);

        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 50, 0, 30);
        JCheckBox checkBox4 = new JCheckBox();
        add(checkBox4, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 50, 0, 30);
        JLabel label7 = new JLabel("AI play (On/Off): ");
        add(label7, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(20, 50, 0, 30);
        Button return_button = new Button("Return");
        return_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main_Menu();
                dispose();
            }
        });
        add(return_button, gbc);

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
