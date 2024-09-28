import java.awt.*;
import java.awt.event.KeyEvent;

public class aiBot {

    int scoreAdjWalls = 500;    // Score for adjacent walls and the bottom
    int scoreHeight = 10000;        // Score for each additional height
    int scoreBubble = -1500;        // Score for each empty space covered
    int scoreRowClear = 10000000;    // Score for completing a row
    int scoreTetris = 1000000000;    // Score for making a tetris

    public static int col = Options_Menu.window_width;
    public static int row = Options_Menu.window_height;
    private aiBot aiBot;

    public int[] findBestPlacement(int[][] currentShape) {
        if (currentShape == null) {
            throw new IllegalStateException("Current shape is not set.");
        }

        int highestScore = Integer.MIN_VALUE;  // Initialize to the lowest possible value
        int bestRow = -1;
        int bestCol = -1;
        int[][] bestRotation = null;  // Track the best rotation
        int bestRotationNumber = -1;  // Track the best rotation number

        // Loop over all rotations (up to 4)
        for (int rotation = 0; rotation < 4; rotation++) {
            // Try placing in each column for the current rotation
            for (int col = 0; col <= aiRotations.getBoardDimension()[0].length - currentShape[0].length; col++) {
                int row = shapeDrop(currentShape, col);  // Get the row where the shape would land

                // Score the placement
                int score = shapeScoring(currentShape, row, col);

                // If this placement has the best score so far, record it
                if (score > highestScore) {
                    highestScore = score;
                    bestRow = row;
                    bestCol = col;
                    bestRotation = currentShape;  // Store the best rotation
                    bestRotationNumber = rotation;  // Track which rotation number gave the best score
                }
            }

            // Rotate the shape for the next iteration
            currentShape = rotateShape(currentShape);
        }

        //System.out.println("Best placement: Row = " + bestRow + ", Col = " + bestCol + ", Rotation = " + bestRotationNumber * 90 + " degrees");
        int[] location = {bestRow,bestCol,bestRotationNumber};
        placeShapeOnBoard(location);
        return new int[]{bestRow, bestCol,bestRotationNumber};  // Return the best row and column
    }

    // Helper method to rotate the shape
    private int[][] rotateShape(int[][] shape) {
        int[][] rotatedShape = transposeMatrix(shape);
        reverseRows(rotatedShape);
        return rotatedShape;
    }

    // Transpose the matrix (switch rows and columns)
    private int[][] transposeMatrix(int[][] matrix) {
        int[][] transposed = new int[matrix[0].length][matrix.length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                transposed[col][row] = matrix[row][col];
            }
        }
        return transposed;
    }

    // Reverse the rows to complete the 90-degree rotation
    private void reverseRows(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            int left = 0;
            int right = matrix[row].length - 1;
            while (left < right) {
                int temp = matrix[row][left];
                matrix[row][left] = matrix[row][right];
                matrix[row][right] = temp;
                left++;
                right--;
            }
        }
    }

    // Helper function to create a deep copy of the board
    private int[][] copyBoard(int[][] originalBoard) {
        int[][] newBoard = new int[originalBoard.length][originalBoard[0].length];
        for (int i = 0; i < originalBoard.length; i++) {
            System.arraycopy(originalBoard[i], 0, newBoard[i], 0, originalBoard[i].length);
        }
        return newBoard;
    }

    public void placeShapeOnBoard(int[] location) {
        //System.out.print("Location array: ");
        //for (int i = 0; i < location.length; i++) {
          //  System.out.print(location[i] + " ");
        //}
        //System.out.println();

        try {
            Robot robot = new Robot();
            int targetColumn = location[1]; // Target column where the piece should be placed
            int currentColumn = col/2-1;

            // If the shape needs to move to the right
            if (targetColumn > currentColumn) {
                int stepsRight = targetColumn - currentColumn;
                for (int i = 0; i < stepsRight; i++) {
                    //System.out.println("Moving right");
                    robot.keyPress(KeyEvent.VK_RIGHT);
                    robot.keyRelease(KeyEvent.VK_RIGHT);
                    Thread.sleep(100); // Adjust for appropriate delay
                }
            }
            // If the shape needs to move to the left (including moving to column 0)
            else if (targetColumn < currentColumn) {
                int stepsLeft = currentColumn - targetColumn;
                for (int i = 0; i < stepsLeft; i++) {
                    //System.out.println("Moving left" + stepsLeft);
                    robot.keyPress(KeyEvent.VK_LEFT);
                    robot.keyRelease(KeyEvent.VK_LEFT);
                    Thread.sleep(100); // Adjust for appropriate delay
                }
            }

            // If targetColumn is 0, no further movement needed, piece stays in place

            // Handle rotation if necessary
            for (int i = 0; i < location[2]; i++) {
                //System.out.println("Rotating");
                robot.keyPress(KeyEvent.VK_UP);
                robot.keyRelease(KeyEvent.VK_UP);
                Thread.sleep(100); // Adjust delay as necessary
            }

        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public int shapeScoring(int[][] currentShape, int row, int col) {
        int score = 0;

        // Increase the reward for clearing lines
        int clearedLines = checkClearedLines();
        //System.out.println("Cleared lines: " + clearedLines);
        if (clearedLines >= 4) {
            score += scoreTetris;  // Assuming this is a big reward
        } else if (clearedLines > 0) {
            score += scoreRowClear * clearedLines;
        }

        // Adjust bubble penalty (lowering the penalty)
        int bubbleCount = checkBubbles(col, currentShape);
        //System.out.println("Bubbles count: " + bubbleCount);
        score += scoreBubble * bubbleCount;  // If scoreBubble is negative, this penalizes the score

        // Reduce the height penalty significantly (make this less harsh)
        //System.out.println("Row height penalty: " + scoreHeight * row);
        score += scoreHeight * row;  // Lower the impact of row height

        // Check adjacency to walls and bottom, and score it
        int adjWallBottomScore = checkAdjacentWallsAndBottom(currentShape, row, col);
        score += adjWallBottomScore;

        //System.out.println("Adjacent Walls Score: " + adjWallBottomScore);

        // Final score output for debugging
        //System.out.println("Final score: " + score);
        return score;
    }

    private boolean shapeCollision(int[][] shape, int row, int col) {
        int[][] boardDimension = aiRotations.getBoardDimension();

        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] != 0) {  // Non-empty part of the shape
                    // Check if the shape is within board boundaries
                    if (row + r >= boardDimension.length || col + c >= boardDimension[0].length ||
                            row + r < 0 || col + c < 0) {
                        return true;  // Collision with board edges
                    }

                    // Check if there's already a block on the board at the target position
                    if (boardDimension[row + r][col + c] != 0) {
                        return true;  // Collision with another block
                    }
                }
            }
        }
        return false;  // No collision detected
    }


    private int shapeDrop(int[][] shape, int col) {
        int row = 0;
        while (row + shape.length <= aiRotations.getBoardDimension().length &&
                !shapeCollision(shape, row, col)) {
            row++;
        }
        // The valid row is the last non-colliding position
        if (row == 0 || shapeCollision(shape, row - 1, col)) {
            //System.out.println("No valid placement for column: " + col);
            return -1;
        }
        //System.out.println("Shape dropped at row: " + (row - 1) + " for column: " + col);
        return row - 1;
    }



    // Moved checkClearedLines to aiBot
    private int checkClearedLines() {
        int rowsCleared = 0;
        int[][] boardDimension = aiRotations.getBoardDimension();

        // Check the board from bottom to top to find full lines
        for (int row = boardDimension.length - 1; row >= 0; row--) {
            boolean fullLine = true;

            // Check if the current row is full
            for (int col = 0; col < boardDimension[0].length; col++) {
                if (boardDimension[row][col] == 0) {
                    fullLine = false;
                    break;
                }
            }

            // If a full line is found, increment rowsCleared
            if (fullLine) {
                rowsCleared++;
            }
        }
        return rowsCleared;
    }

    private int checkBubbles(int startCol, int[][] currentShape) {
        int bubbles = 0;

        int shapeWidth = currentShape[0].length;
        int endCol = startCol + shapeWidth - 1;

        // Iterate through the columns from startCol to endCol
        for (int c = startCol; c <= endCol; c++) {
            boolean blockAbove = false;

            // Iterate through the rows from bottom to top
            for (int r = aiRotations.getBoardDimension().length - 1; r >= 0; r--) {
                if (aiRotations.getBoardDimension()[r][c] != 0) {
                    blockAbove = true;
                } else if (blockAbove && aiRotations.getBoardDimension()[r][c] == 0) {
                    bubbles++;
                }
            }
        }
        return bubbles;
    }

    private int checkAdjacentWallsAndBottom(int[][] shape, int row, int col) {
        int score = 0;
        int[][] boardDimension = aiRotations.getBoardDimension();

        // Check adjacency to walls (left and right)
        for (int r = 0; r < shape.length; r++) {
            if (col == 0 || col + shape[0].length == boardDimension[0].length) {
                // Shape is adjacent to the left or right wall
                score += scoreAdjWalls;  // Award points for being adjacent to the wall
                //System.out.println("Adjacent to wall: " + (col == 0 ? "left" : "right"));
            }
        }

        // Check adjacency to the bottom of the board
        for (int c = 0; c < shape[0].length; c++) {
            if (row + shape.length == boardDimension.length || (row + shape.length < boardDimension.length && boardDimension[row + shape.length][col + c] != 0)) {
                // Shape is adjacent to the bottom or another block below
                score += scoreAdjWalls;  // Award points for being adjacent to the bottom
                //System.out.println("Adjacent to the bottom or another block below");
            }
        }

        return score;
    }

}


