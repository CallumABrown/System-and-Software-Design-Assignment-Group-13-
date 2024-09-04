import java.awt.*;

public class Shape {
    private int x = 4, y = 0;
    private int level;
    private int normal;
    private int fast = 50;
    private int delayTimeForMovement = normal;
    private long beginTime;

    private int deltaX = 0;
    private boolean collision = false;

    private int[][] coordinates;
    private Game_Area board;
    private Color color;

    int lines;
    public int score;

    public Shape(int[][] coordinates, Game_Area board, Color color) {
        this.coordinates = coordinates;
        this.board = board;
        this.color = color;
        this.level = board.level;
        this.normal = 600 - ((level - 1) * 50);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void reset() {
        this.x = 4;
        this.y = 0;
        collision = false;
    }

    public void update() {
        if (collision) {
            //Fill the color for board
            for (int row = 0; row < coordinates.length; row++) {
                for (int col = 0; col < coordinates[0].length; col++) {
                    if (coordinates[row][col] != 0) {
                        board.getBoard()[y + row][x + col] = color;
                    }
                }
            }
            checkLine();
            //Change Current Shape
            board.setCurrentShape();
            return;
        }
        // Check Horizontal Movement
        boolean moveX = true;
        if (!(x + deltaX + coordinates[0].length > Options_Menu.window_width) && !(x + deltaX < 0)) {
            for (int row = 0; row < coordinates.length; row++) {
                for (int col = 0; col < coordinates[row].length; col++) {
                    if (coordinates[row][col] != 0) {
                        if (board.getBoard()[y + row][x + deltaX + col] != null) {
                            moveX = false;
                            System.out.println("x:"+x);
                            System.out.println("deltaX:"+deltaX);
                            System.out.println("col:"+col);
                        }
                    }
                }
            }
            if (moveX) {
                x += deltaX;
                System.out.println(x);
            }
        }
        deltaX = 0;
        if (board.downPressed) {
            delayTimeForMovement = fast;
        } else {
            delayTimeForMovement = normal;
        }
        if (System.currentTimeMillis() - beginTime > delayTimeForMovement) {
            //Vertical Movement
            if (!((y + 1 + coordinates.length) > Game_Area.BOARD_HEIGHT)) {
                for (int row = 0; row < coordinates.length; row++) {
                    for (int col = 0; col < coordinates[row].length; col++) {
                        if (coordinates[row][col] != 0) {
                            if (board.getBoard()[y + 1 + row][x + deltaX + col] != null) {
                                collision = true;
                            }
                        }
                    }
                }
                if (!collision) {
                    y++;
                }
            } else {
                collision = true;
            }
            beginTime = System.currentTimeMillis();
        }
    }

    private void checkLine() {
        int bottomLine = board.getBoard().length - 1;
        for (int topLine = board.getBoard().length - 1; topLine > 0; topLine--) {
            int count = 0;
            for (int col = 0; col < board.getBoard()[0].length; col++) {
                if (board.getBoard()[topLine][col] != null) {
                    count++;
                }
                board.getBoard()[bottomLine][col] = board.getBoard()[topLine][col];
            }
            if (count < board.getBoard()[0].length) {
                bottomLine--;
                lines++;

//                if (lines % 10 == 0 && delayTimeForMovement > 1) {
//                    level++;
//                    if (delayTimeForMovement > 10) {
//                        delayTimeForMovement -= 10;
//                    } else {
//                        delayTimeForMovement -= 1;
//                    }
//                }
            }
        }
    }

    public void rotateShape() {
        int[][] rotatedShape = transposeMatrix(coordinates);
        reverseRows(rotatedShape);

        //Check for board edges
        if (x + rotatedShape[0].length > Game_Area.BOARD_WIDTH || y + rotatedShape[0].length > Game_Area.BOARD_HEIGHT) {
            return;
        }

        // Check for collision with other shapes
        for (int row = 0; row < rotatedShape.length; row++) {
            for (int col = 0; col < rotatedShape[row].length; col++) {
                if (rotatedShape[row][col] != 0) {
                    if (board.getBoard()[y + row][x + col] != null) {
                        return;
                    }
                }
            }
        }

        coordinates = rotatedShape;
    }

    private int[][] transposeMatrix(int[][] matrix) {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                temp[col][row] = matrix[row][col];
            }
        }
        return temp;
    }

    private void reverseRows(int[][] matrix) {
        int middle = matrix.length / 2;
        for (int row = 0; row < middle; row++) {
            int[] temp = matrix[row];
            matrix[row] = matrix[matrix.length - row - 1];
            matrix[matrix.length - row - 1] = temp;
        }
    }

    public void render(Graphics g) {
        // Draw the Shape
        for (int row = 0; row < coordinates.length; row++) {
            for (int col = 0; col < coordinates[0].length; col++) {
                if (coordinates[row][col] != 0) {
                    g.setColor(color);
                    g.fillRect(col * Game_Area.BLOCK_SIZE + x * Game_Area.BLOCK_SIZE, row * Game_Area.BLOCK_SIZE + y * Game_Area.BLOCK_SIZE, Game_Area.BLOCK_SIZE, Game_Area.BLOCK_SIZE);
                }
            }
        }
    }

    public void speedUp() {
        delayTimeForMovement = fast;
    }

    public void speedDown() {
        delayTimeForMovement = normal;
    }

    public void moveRight() {
        deltaX += 1;
    }

    public void moveLeft() {
        deltaX -= 1;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
