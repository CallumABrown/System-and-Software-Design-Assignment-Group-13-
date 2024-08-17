import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class High_Score_Menu extends Frame {
    public High_Score_Menu(){

        setTitle("");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        ArrayList<String> names = new ArrayList<String>();
        for (int x = 0; x < 10; x++){
            names.add(String.valueOf(x + 10000));
        }
        ArrayList<String> scores = new ArrayList<String>();
        for (int x = 10; x < 20; x++){
            scores.add(String.valueOf(x));
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 0, 20, 0); // Add some padding around the label
        gbc.anchor = GridBagConstraints.CENTER;
        Label label_1 = new Label("High Scores:");
        add(label_1, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);

        for (int i = 0; i < 10; i++){
            gbc.gridx = 0;
            gbc.gridy = 1 + i;
            gbc.anchor = GridBagConstraints.WEST;
            Label name = new Label(names.get(i));
            add(name, gbc);

            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.EAST;
            Label score = new Label(scores.get(i));
            add(score, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        Button return_button = new Button("Return");
        return_button.setPreferredSize(new Dimension(200, 20));
        return_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main_Menu();
                dispose();
            }
        });
        add(return_button, gbc);

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
