import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Scanner;

public class High_Score_Menu extends JFrame {
    private static final String HIGH_SCORE_FILE_PATH = "../data/highscores.dat";
    private static final int HIGH_SCORES_COUNT = 5;
    private HighScoreClass[] highScoresLeaderboard;

    public High_Score_Menu() {
        setTitle("High Scores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadHighScores();

        setLayout(new BorderLayout());

        Panel mainPanel = new Panel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // ArrayList<String> names = new ArrayList<String>(Arrays.asList("Alice", "Bob",
        // "Charlie", "Phil", "Grace", "Mark", "William", "Billy", "Jessie", "Lona"));

        // ArrayList<String> scores = new ArrayList<String>(Arrays.asList("10450",
        // "9865", "8735", "8550", "8005", "6500", "5005", "5002", "4875", "4405"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        Label label_1 = new Label("High Scores:");
        mainPanel.add(label_1, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);

        // for (int i = 0; i < 10; i++) {
        // gbc.gridx = 0;
        // gbc.gridy = 1 + i;
        // gbc.anchor = GridBagConstraints.WEST;
        // Label name = new Label(names.get(i));
        // mainPanel.add(name, gbc);

        // gbc.gridx = 2;
        // gbc.anchor = GridBagConstraints.EAST;
        // Label score = new Label(scores.get(i));
        // mainPanel.add(score, gbc);
        // }

        for (int i = 0; i < highScoresLeaderboard.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = 1 + i;
            gbc.anchor = GridBagConstraints.WEST;
            Label name = new Label(highScoresLeaderboard[i].name);
            mainPanel.add(name, gbc);

            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.EAST;
            Label score = new Label(String.valueOf(highScoresLeaderboard[i].score));
            mainPanel.add(score, gbc);
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
        mainPanel.add(return_button, gbc);

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
        highScoresLeaderboard = new HighScoreClass[HIGH_SCORES_COUNT];
        for (int i = 0; i < HIGH_SCORES_COUNT; i++) {
            highScoresLeaderboard[i] = new HighScoreClass("", 0);
        }

        File highScoreFile = new File(HIGH_SCORE_FILE_PATH);

        if (!highScoreFile.exists()) {
            System.out.println("High score file does not exist at: " + HIGH_SCORE_FILE_PATH);
            // Optionally, initialize highScoresLeaderboard with default values or handle
            // the case accordingly
            for (int i = 0; i < HIGH_SCORES_COUNT; i++) {
                highScoresLeaderboard[i] = new HighScoreClass("No Record", 0);
            }
            return; // Exit the method if the file does not exist
        }
        try (Scanner fileReader = new Scanner(highScoreFile)) {
            int totalLinesRead = 0;
            while (fileReader.hasNextLine() && totalLinesRead < HIGH_SCORES_COUNT) {
                String highScoreLine = fileReader.nextLine();

                // Skip over empty lines and lines that are comments
                if (highScoreLine.isEmpty() || highScoreLine.startsWith("--")) {
                    continue;
                }

                String[] lineTokens = highScoreLine.split("\\|");
                if (lineTokens.length >= 2) {
                    highScoresLeaderboard[totalLinesRead] = new HighScoreClass(lineTokens[0],
                            Integer.parseInt(lineTokens[1]));
                    totalLinesRead++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveHighScores() {
        try (FileWriter fileWriter = new FileWriter(new File(HIGH_SCORE_FILE_PATH))) {
            fileWriter.write("-- This file stores the high scores for the game.\n");
            fileWriter
                    .write("-- MODIFYING THIS FILE CAN RESULT IN DATA CORRUPTION / UNEXPECTED PROGRAM BEHAVIOUR.\n\n");

            for (int i = 0; i < highScoresLeaderboard.length; i++) {
                fileWriter.write(highScoresLeaderboard[i].name + "|" + highScoresLeaderboard[i].score
                        + (i == highScoresLeaderboard.length - 1 ? "" : "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
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
