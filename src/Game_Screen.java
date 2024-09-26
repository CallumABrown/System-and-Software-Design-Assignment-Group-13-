import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Game_Screen extends JFrame {
    public static int WIDTH, HEIGHT;
    private Game_Area board;
    private Game_Area board2;
    public int state = Constants.GAME_STATE_PLAY;

    public Clip music;

    public Random random = new Random();
    public int[] tetrominoGenerator = new int[10000];

    public static void playSound(String soundFilePath) {
        try {
            if (Options_Menu.sound_effects) {
                File soundFile = new File(soundFilePath);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    void MusicAndSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File background_music = new File("resources/8-bit-arcade.wav");
        AudioInputStream music_input = AudioSystem.getAudioInputStream(background_music);
        music = AudioSystem.getClip();
        music.open(music_input);
        music.loop(Clip.LOOP_CONTINUOUSLY);
        if (Options_Menu.music) {
            music.start();
        } else {
            music.stop();
        }
    }

    public Game_Screen() {
        for (int i = 0; i < tetrominoGenerator.length; i++) {
            int randomNumber = new Random().nextInt(7);
            tetrominoGenerator[i] = randomNumber;
        }
        setTitle("Tetris");
        HEIGHT = (32 * Options_Menu.window_height);
        WIDTH = (int) (31.5 * Options_Menu.window_width) + 200;
        if (WIDTH < 420) {
            WIDTH = 420;
        }
        if (Options_Menu.extend) {
            WIDTH *= 2;
        }
        setSize(WIDTH, HEIGHT + 25);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Set BorderLayout for the main frame
        setLayout(new BorderLayout());

        // Create a panel for the game boards with GridLayout
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(1, 2)); // Two columns for the game boards


        if (Options_Menu.extend) {
            board = new Game_Area(this);
            board.addKeyListener(new Player2KeyListener(board));

            gamePanel.add(board); // Add the first board

            board2 = new Game_Area(this);
            board.addKeyListener(new Player1KeyListener(board2));// Add the second board side-by-side
            gamePanel.add(board2);
        } else {
            board = new Game_Area(this);
            board.addKeyListener(new Player1KeyListener(board));

            gamePanel.add(board); // Add the first board
        }

        // Add the gamePanel to the center of the frame
        add(gamePanel, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setBackground(Color.BLACK);
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel footerLabel = new JLabel("Authors: Adam Filipczuk, Callum Brown, Gauruv Grover, Steve Drewery");
        footerLabel.setForeground(Color.WHITE);
        footer.add(footerLabel);

        // Add footer to the bottom of the frame
        add(footer, BorderLayout.SOUTH);

        setVisible(true);

        try {
            MusicAndSound();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Game_Screen();
    }

    class Player1KeyListener implements KeyListener {
        Game_Area board;

        Player1KeyListener(Game_Area board) {
            this.board = board;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    board.getCurrentShape().rotateShape();
//                    aiRotations.drawPiece(board.getCurrentShape().getCoordinates());
                    playSound("resources/rotate_and_move.wav");
                    break;
                case KeyEvent.VK_DOWN:
                    board.downPressed = true;
                    playSound("resources/rotate_and_move.wav");
                    break;
                case KeyEvent.VK_LEFT:
                    board.getCurrentShape().moveLeft();
                    playSound("resources/rotate_and_move.wav");
                    break;
                case KeyEvent.VK_RIGHT:
                    board.getCurrentShape().moveRight();
                    playSound("resources/rotate_and_move.wav");
                    break;
                case KeyEvent.VK_P:
                    if (state == Constants.GAME_STATE_PLAY) {
                        state = Constants.GAME_STATE_PAUSE;
                    } else if (state == Constants.GAME_STATE_PAUSE) {
                        state = Constants.GAME_STATE_PLAY;
                    }
                    break;
                case KeyEvent.VK_M:
                    if (Options_Menu.music) {
                        music.stop();
                        Options_Menu.music = false;
                    } else {
                        music.start();
                        Options_Menu.music = true;
                    }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                board.downPressed = false;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }

    class Player2KeyListener implements KeyListener {
        Game_Area board;

        Player2KeyListener(Game_Area board) {
            this.board = board;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    board.getCurrentShape().rotateShape();
                    playSound("resources/rotate_and_move.wav");
                    break;
                case KeyEvent.VK_S:
                    board.downPressed = true;
                    playSound("resources/rotate_and_move.wav");
                    break;
                case KeyEvent.VK_A:
                    board.getCurrentShape().moveLeft();
                    playSound("resources/rotate_and_move.wav");
                    break;
                case KeyEvent.VK_D:
                    board.getCurrentShape().moveRight();
                    playSound("resources/rotate_and_move.wav");
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_S) {
                board.downPressed = false;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }
}
