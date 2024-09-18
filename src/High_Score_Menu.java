import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class High_Score_Menu extends JFrame {
    private static final String HIGH_SCORE_FILE_PATH = "data/highscores.json";
    private static final int HIGH_SCORES_COUNT = 10;
    private List<HighScoreClass> highScoresLeaderboard;

    public High_Score_Menu() {
        setTitle("High Scores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadHighScores();

        setLayout(new BorderLayout());

        Panel mainPanel = new Panel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        Label label_1 = new Label("High Scores:");
        mainPanel.add(label_1, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Display high scores
        displayHighScores(mainPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = highScoresLeaderboard.size() + 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        Button return_button = new Button("Return");
        return_button.setPreferredSize(new Dimension(200, 20));
        return_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main_Menu();
                dispose();
            }
        });

        // Create reset button
        Button reset_button = new Button("Reset High Scores");
        reset_button.setPreferredSize(new Dimension(200, 20));
        reset_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetHighScores();
                displayHighScores(mainPanel, gbc); // Refresh display
            }
        });
        gbc.gridy = highScoresLeaderboard.size() + 2; // Position below return button
        mainPanel.add(reset_button, gbc);

        add(mainPanel, BorderLayout.CENTER);

        Panel footer = new Panel();
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        Label footerLabel = new Label("Authors: Adam Filipczuk, Callum Brown, Gauruv Grover, Steve Drewery");
        footer.add(footerLabel);

        add(footer, BorderLayout.SOUTH);

        setSize(400, 600);
        centerWindow();
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void loadHighScores() {
        highScoresLeaderboard = new ArrayList<>();
        File highScoreFile = new File(HIGH_SCORE_FILE_PATH);

        if (!highScoreFile.exists()) {
            System.out.println("High score file does not exist at: " + HIGH_SCORE_FILE_PATH);
            createDefaultHighScores(); // Create default if file doesn't exist
            return;
        }

        try (FileReader fileReader = new FileReader(highScoreFile)) {
            Gson gson = new Gson();
            java.lang.reflect.Type listType = new TypeToken<ArrayList<HighScoreClass>>() {}.getType();
            highScoresLeaderboard = gson.fromJson(fileReader, listType);

            // Ensure the list has the required number of scores
            while (highScoresLeaderboard.size() < HIGH_SCORES_COUNT) {
                highScoresLeaderboard.add(new HighScoreClass("No Record", 0));
            }
            highScoresLeaderboard.sort((hs1, hs2) -> Integer.compare(hs2.score, hs1.score));
            saveHighScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefaultHighScores() {
        highScoresLeaderboard = new ArrayList<>();
        for (int i = 0; i < HIGH_SCORES_COUNT; i++) {
            highScoresLeaderboard.add(new HighScoreClass("No Record", 0));
        }
        saveHighScores(); // Save the default scores to the file
    }

    private void saveHighScores() {
        try (FileWriter fileWriter = new FileWriter(new File(HIGH_SCORE_FILE_PATH))) {
            Gson gson = new Gson();
            gson.toJson(highScoresLeaderboard, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetHighScores() {
        createDefaultHighScores(); // Create a new default file
        loadHighScores(); // Reload the scores from the file
    }

    private void displayHighScores(Panel mainPanel, GridBagConstraints gbc) {
        // Clear previous high score labels before displaying
        mainPanel.removeAll(); // Only clear for the new scores, re-add other components later
        gbc.gridx = 0;
        gbc.gridy = 1;

        for (HighScoreClass highScore : highScoresLeaderboard) {
            gbc.anchor = GridBagConstraints.WEST;
            mainPanel.add(new Label(highScore.name), gbc);
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.EAST;
            mainPanel.add(new Label(String.valueOf(highScore.score)), gbc);
            gbc.gridx = 0;
            gbc.gridy++;
        }

        // Re-add buttons and other components
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;

        Button return_button = new Button("Return");
        return_button.setPreferredSize(new Dimension(200, 20));
        return_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main_Menu();
                dispose();
            }
        });
        mainPanel.add(return_button, gbc);

        gbc.gridy++;
        Button reset_button = new Button("Reset High Scores");
        reset_button.setPreferredSize(new Dimension(200, 20));
        reset_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetHighScores();
                displayHighScores(mainPanel, gbc);
            }
        });
        mainPanel.add(reset_button, gbc);

        mainPanel.revalidate(); // Refresh the layout
        mainPanel.repaint(); // Repaint the panel
    }

    private void centerWindow() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }
}
