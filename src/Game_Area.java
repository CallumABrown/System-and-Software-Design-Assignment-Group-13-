import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Random;
import java.net.Socket;
import com.google.gson.Gson;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.AWTException;


public class Game_Area extends JPanel {
    private PureGame pureGame;
    private High_Score_Menu highScoreMenu;


    private static int FPS = 200;
    private static int delay = 1000 / FPS;

    public static int BOARD_WIDTH = Options_Menu.window_width;
    public static int BOARD_HEIGHT = Options_Menu.window_height;
    public static final int BLOCK_SIZE = 30;
    private Timer looper;
    private Color[][] board;
    public Shape currentShape;


//    private Random random;

    private Color[] colors = {Color.decode("#ed1c24"), Color.decode("#ff7f27"), Color.decode("#fff200"),
            Color.decode("#22b14c"), Color.decode("#00a2e8"), Color.decode("#a349a4"), Color.decode("#3f48cc")};

    int level = Options_Menu.game_level;
    int score = 0;
    int rowsCompleted = 0;
    public boolean downPressed;


    private int[][] shapeParameters = {
            {1, 1, 1},
            {0, 1, 0},
    };

    private Shape[] shapes = new Shape[7];
    private Shape nextShape;

    private Game_Screen gameScreen;

    public Game_Area(Game_Screen gameScreen) {
        aiRotations.aiBoard();
//        aiRotations.drawBoard();
        this.gameScreen = gameScreen;
        setLayout(null);
        pureGame = new PureGame(BOARD_WIDTH, BOARD_HEIGHT);

//        JButton sendStateButton = new JButton("Send Game State");
//        sendStateButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                 // Method to send the game state
//            }
//        });
//
//        add(sendStateButton);

        BOARD_WIDTH = Options_Menu.window_width;
        BOARD_HEIGHT = Options_Menu.window_height;

        board = new Color[BOARD_HEIGHT][BOARD_WIDTH];

//        random = new Random();

        shapes[0] = new Shape(new int[][]{
                {1, 1, 1, 1} // I shape;
        }, this, colors[0]);

        shapes[1] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 1, 0} // T shape;
        }, this, colors[1]);

        shapes[2] = new Shape(new int[][]{
                {1, 1, 1},
                {1, 0, 0} // L shape;
        }, this, colors[2]);

        shapes[3] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 0, 1} // J shape;
        }, this, colors[3]);

        shapes[4] = new Shape(new int[][]{
                {0, 1, 1},
                {1, 1, 0} // S Shape
        }, this, colors[4]);

        shapes[5] = new Shape(new int[][]{
                {1, 1, 0},
                {0, 1, 1} // Z Shape
        }, this, colors[5]);

        shapes[6] = new Shape(new int[][]{
                {1, 1},
                {1, 1} // O Shape
        }, this, colors[6]);

        currentShape = shapes[0];


        looper = new Timer(delay, new ActionListener() {
            int n = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        looper.start();

        JButton returnButton = new JButton("Return");
        returnButton.setPreferredSize(new Dimension(100, 30));
        returnButton.setBounds(getWidth() - 120, getHeight() - 40, 100, 30);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameScreen.state == Constants.GAME_STATE_PAUSE) {
                    int response = JOptionPane.showConfirmDialog(
                            Game_Area.this,
                            "Are you sure you want to return to the main menu?",
                            "Confirm Return",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (response == JOptionPane.YES_OPTION) {
                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Game_Area.this);
                        gameScreen.state = Constants.GAME_STATE_OVER;
                        topFrame.dispose();
                        gameScreen.music.stop();
                        board = null;
                        new Main_Menu();
                    } else {
                        requestFocusInWindow();
                    }
                } else {
                    gameScreen.state = Constants.GAME_STATE_PAUSE;
                    int response = JOptionPane.showConfirmDialog(
                            Game_Area.this,
                            "Are you sure you want to return to the main menu?",
                            "Confirm Return",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (response == JOptionPane.YES_OPTION) {
                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Game_Area.this);
                        gameScreen.state = Constants.GAME_STATE_OVER;
                        gameScreen.music.stop();
                        topFrame.dispose();
                        new Main_Menu();
                    } else {
                        requestFocusInWindow();
                        gameScreen.state = Constants.GAME_STATE_PLAY;
                    }
                }
            }
        });

        add(returnButton);

        setFocusable(true);
    }

    private void sendGameStateToServer() {
        // Use the existing pureGame instance
        // Ensure PureGame has a proper constructor and methods to retrieve state

        // Populate the PureGame object with current game state
        pureGame.setCurrentShape(currentShape.getCoordinates()); // Assuming this returns the current shape

        pureGame.setNextShape(new int[][]{{1, 1, 1, 1}}); // Example assignment

        pureGame.updateCellsFromBoard(aiRotations.boardDimension);

        //int[][] boardCopy = getBoardState(); // Use your existing method to get the current board state
       // pureGame.setCells(boardCopy);

        // Send the game state to the server
        try (Socket socket = new Socket("localhost", 3000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Convert PureGame object to JSON
            Gson gson = new Gson();
            String jsonGameState = gson.toJson(pureGame);
            out.println(jsonGameState);
            System.out.println("Sent game state to server: " + jsonGameState);

            // Wait for the server's response
            String response = in.readLine();
            System.out.println("Received response from server: " + response);

            // Convert the JSON response to an OpMove object
            OpMove move = gson.fromJson(response, OpMove.class);
            System.out.println("Optimal Move: X=" + move.opX() + ", Rotations=" + move.opRotate());

            // Apply the move based on opX and opRotate values
            applyMove(move);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void applyMove(OpMove move) {

        // Implement logic to apply the optimal move to the current game state
        if (move.opX() == 5) {
            System.out.println("Do nothing.");
        } else {
            System.out.println("Move the piece to X=" + move.opX());
            int start = BOARD_WIDTH/2 - move.opX();
            try {
                if (move.opX() > 5) {
                    try {
                        Robot robot = new Robot();
                        System.out.println(start);
                        for (int i = 0; i < move.opX()-(BOARD_WIDTH/2); i++) {
                            // Simulate pressing the UP key
                            System.out.println("right");
                            robot.keyPress(KeyEvent.VK_RIGHT);
                            robot.keyRelease(KeyEvent.VK_RIGHT);
                            // Optional: Add a small delay between key presses
                            Thread.sleep(100); // Adjust delay as necessary
                        }
                    } catch (AWTException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if (move.opX < 5) {
                    try {
                        Robot robot = new Robot();
                        for (int i = 0; i < (BOARD_WIDTH/2)-move.opX(); i++) {
                            System.out.println("left");
                            // Simulate pressing the UP key
                            robot.keyPress(KeyEvent.VK_LEFT);
                            robot.keyRelease(KeyEvent.VK_LEFT);
                            // Optional: Add a small delay between key presses
                            Thread.sleep(100); // Adjust delay as necessary
                        }
                    } catch (AWTException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Robot robot = new Robot();
                for (int i = 0; i < move.opX; i++) {
                    // Simulate pressing the UP key
                    robot.keyPress(KeyEvent.VK_UP);
                    robot.keyRelease(KeyEvent.VK_UP);
                    // Optional: Add a small delay between key presses
                    Thread.sleep(100); // Adjust delay as necessary
                }
            } catch (AWTException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (move.opRotate() == 0) {
            System.out.println("No rotation needed.");
        } else {
            System.out.println("Rotate the piece " + move.opRotate() + " times.");
            try {
                Robot robot = new Robot();
                for (int i = 0; i < move.opRotate(); i++) {
                    // Simulate pressing the UP key
                    robot.keyPress(KeyEvent.VK_UP);
                    robot.keyRelease(KeyEvent.VK_UP);
                    // Optional: Add a small delay between key presses
                    Thread.sleep(100); // Adjust delay as necessary
                }
            } catch (AWTException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class OpMove {
        private int opX;
        private int opRotate;

        public OpMove(int opX, int opRotate) {
            this.opX = opX;
            this.opRotate = opRotate;
        }

        public int opX() {
            return opX;
        }

        public int opRotate() {
            return opRotate;
        }
    }

    @Override
    public void doLayout() {
        super.doLayout();
        // Adjust button position on layout
        JButton returnButton = (JButton) getComponent(0);
        if (returnButton != null) {
            returnButton.setBounds(Options_Menu.window_width * BLOCK_SIZE + 40, getHeight() - 40, 100, 30);
        }
    }

    private void update() {
        if (gameScreen.state == Constants.GAME_STATE_PLAY) {
            currentShape.update();
        }
    }

    public void setCurrentShape() {
        currentShape = shapes[gameScreen.random.nextInt(shapes.length)];
//        aiRotations.drawPiece(currentShape.getCoordinates());
//        currentShape = shapes[0];
        currentShape.reset();

        pureGame.setCurrentShape(currentShape.getCoordinates());
        if (Options_Menu.player1_type == "External") {
            sendGameStateToServer();
        }
        checkGameOver();
    }

    public Shape getCurrentShape() {
        return currentShape;
    }

    private void submitScore(int score) {
        String playerName = enterPlayerName(); // Get player's name
        High_Score_Menu highScoreMenu = new High_Score_Menu(); // Create new instance here
        highScoreMenu.addHighScore(playerName, score); // Use entered name for the high score

        // After saving the score, return to the main menu
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose(); // Close the current game window
    }


    private String enterPlayerName() {
        // Prompt the user for their name
        String playerName = JOptionPane.showInputDialog(this, "Enter your name:", "High Score Entry", JOptionPane.PLAIN_MESSAGE);
        // Validate the input (optional)
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player"; // Default name if no input
        }
        return playerName;
    }


    private void checkGameOver() {
        int[][] coordinates = currentShape.getCoordinates();
        for (int row = 0; row < coordinates.length; row++) {
            for (int col = 0; col < coordinates[0].length; col++) {
                if (coordinates[row][col] != 0) {
                    if (board[(int) (row + currentShape.getY())][col + currentShape.getX()] != null) {
                        if (gameScreen.state != Constants.GAME_STATE_OVER) { // Ensure we only submit score once
                            gameScreen.state = Constants.GAME_STATE_OVER;
                            submitScore(score);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        int TEXT_LOCATION = Options_Menu.window_width * BLOCK_SIZE + 40;
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the Board
        g.setColor(Color.WHITE);
        for (int row = 0; row <= BOARD_HEIGHT; row++) {
            g.drawLine(0, BLOCK_SIZE * row, BLOCK_SIZE * BOARD_WIDTH, BLOCK_SIZE * row);
        }

        for (int col = 0; col < BOARD_WIDTH + 1; col++) {
            g.drawLine(col * BLOCK_SIZE, 0, col * BLOCK_SIZE, BLOCK_SIZE * BOARD_HEIGHT);
        }

        if (gameScreen.state == Constants.GAME_STATE_PAUSE) {
            g.setColor(Color.white);
            g.drawString("GAME PAUSED", TEXT_LOCATION, 200);
            g.drawString("PRESS P TO PLAY", TEXT_LOCATION, 250);
        }
        if (gameScreen.state == Constants.GAME_STATE_OVER) {
            g.setColor(Color.white);
            g.drawString("GAME OVER", TEXT_LOCATION, 200);
        }

        // Draw score window

        int x = TEXT_LOCATION;
        int y = BOARD_HEIGHT;
        g.drawRect(x, y, 140, 130);
        x += 20;
        y += 40;
        g.drawString("Level: " + level, x, y);
        y += 30;
        g.drawString("Score: " + score, x, y);
        y += 30;
        g.drawString("Rows Completed: " + rowsCompleted, x, y);
        y += 30;

        // Draw next window
        x -= 20;
        y += 200;
        g.drawRect(x, y, 100, 100);
        g.drawString("NEXT", x + 20, y + 30);

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != null) {
                    g.setColor(board[row][col]);
                    g.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        currentShape.render(g);
    }

    public Color[][] getBoard() {
        return board;
    }

    //    @Override
//    public void keyTyped(KeyEvent e) {
//
//    }
//    @Override
//    public void keyPressed(KeyEvent e) {
//        if (playerId == 1) {
//            switch (e.getKeyCode()) {
//                case KeyEvent.VK_UP:
//                    currentShape.rotateShape();
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_DOWN:
//                    downPressed = true;
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_LEFT:
//                    currentShape.moveLeft();
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_RIGHT:
//                    currentShape.moveRight();
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_P:
//                    if (state == GAME_STATE_PLAY) {
//                        state = GAME_STATE_PAUSE;
//                    } else if (state == GAME_STATE_PAUSE) {
//                        state = GAME_STATE_PLAY;
//                    }
//                    break;
//                case KeyEvent.VK_M:
//                    if (Options_Menu.music) {
//                        music.stop();
//                        Options_Menu.music = false;
//                    } else {
//                        music.start();
//                        Options_Menu.music = true;
//                    }
//            }
//        }
//
//        // Player 2 controls with W A S D
//        else {
//            switch (e.getKeyCode()) {
//                case KeyEvent.VK_W: // Rotate
//                    currentShape.rotateShape();
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_S: // Down
//                    downPressed = true;
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_A: // Left
//                    currentShape.moveLeft();
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_D: // Right
//                    currentShape.moveRight();
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//            }
//        }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//            downPressed = false;
////            currentShape.speedDown();
//        }
//    }
//    class Player1KeyListener implements KeyListener {
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//            switch (e.getKeyCode()) {
//                case KeyEvent.VK_UP:
//                    currentShape.rotateShape();
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_DOWN:
//                    downPressed = true;
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_LEFT:
//                    currentShape.moveLeft();
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_RIGHT:
//                    currentShape.moveRight();
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_P:
//                    if (state == GAME_STATE_PLAY) {
//                        state = GAME_STATE_PAUSE;
//                    } else if (state == GAME_STATE_PAUSE) {
//                        state = GAME_STATE_PLAY;
//                    }
//                    break;
//                case KeyEvent.VK_M:
//                    if (Options_Menu.music) {
//                        music.stop();
//                        Options_Menu.music = false;
//                    } else {
//                        music.start();
//                        Options_Menu.music = true;
//                    }
//            }
//        }
//
//        @Override
//        public void keyReleased(KeyEvent e) {
//            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//                downPressed = false;
//                //currentShape.speedDown();
//            }
//        }
//
//        @Override
//        public void keyTyped(KeyEvent e) {
//        }
//    }
//
//    class Player2KeyListener implements KeyListener {
//        @Override
//        public void keyTyped(KeyEvent e) {
//
//        }
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//            switch (e.getKeyCode()) {
//                case KeyEvent.VK_W: // Rotate
//                    currentShape.rotateShape();
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_S: // Down
//                    downPressed = true;
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_A: // Left
//                    currentShape.moveLeft();
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//                case KeyEvent.VK_D: // Right
//                    currentShape.moveRight();
//                    playSound("resources/rotate_and_move.wav");
//                    break;
//            }
//        }
//
//        @Override
//        public void keyReleased(KeyEvent e) {
//        }
//    }
}