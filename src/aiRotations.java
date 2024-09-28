public class aiRotations {

    public static int[][] boardDimension;
    public static int row = Options_Menu.window_width;
    public static int col = Options_Menu.window_height;
    private static int[][] currentShape;

    //keep this to draw board/////////////////////
    public static void aiBoard()
    {
        if (boardDimension == null) {
            boardDimension = new int[col][row];
        }
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                boardDimension[i][j]=0;
            }
        }
    }
    public static int[][] getBoardDimension() {
        return boardDimension;
    }
////////////////////////////////////

    public static void drawBoard() {
        // Ensure the boardDimension array is populated
        if (boardDimension == null) {
            System.out.println("Board is not initialized.");
            return;
        }

        // Print the boardDimension array
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                System.out.print(boardDimension[i][j] + " ");  // Print the value in the matrix
            }
            System.out.println();
        }
    }

    public static void drawPiece(int[][] currentShape)
    {
        for (int[] row : currentShape) {
            for (int val : row) {
                System.out.print((val == 1 ? "*" : " ") + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void aiBoardUpdate(int x, double y, int[][] shape) {
        // Ensure the boardDimension array is populated
        if (boardDimension == null) {
            System.out.println("Board is not initialized.");
            return;
        }

        // Cast y to an int for indexing
        int intY = (int) Math.floor(y); // Use floor to handle downward placement

        // Place the shape on the boardDimension
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != 0) {
                    // Check boundaries to avoid ArrayIndexOutOfBoundsException
                    if (intY + row >= 0 && intY + row < boardDimension.length && x + col >= 0 && x + col < boardDimension[0].length) {
                        boardDimension[intY + row][x + col] = shape[row][col];
                    }
                }
            }
        }


    // After placing the shape, check for full lines and clear them
        checkAiBoardLines();
        drawBoard(); // Draw the board after update

    }

    private static void checkAiBoardLines() {
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

            // If a full line is found, clear it and shift the rows down
            if (fullLine) {
                // Clear the current row
                for (int col = 0; col < boardDimension[0].length; col++) {
                    boardDimension[row][col] = 0;
                }

                // Shift all rows above the current one down by one
                for (int r = row; r > 0; r--) {
                    for (int col = 0; col < boardDimension[0].length; col++) {
                        boardDimension[r][col] = boardDimension[r - 1][col];
                    }
                }

                // Set the top row to empty (since it's now shifted down)
                for (int col = 0; col < boardDimension[0].length; col++) {
                    boardDimension[0][col] = 0;
                }

                // Recheck the same row after shifting
                row++;
            }
        }
    }
    // Method to set the current shape
    public static void setCurrentShape(int[][] shape) {
        currentShape = shape;
        System.out.println("done ");
    }

    // Method to get the current shape
    public static int[][] getCurrentShape() {
        System.out.println("done ");
        return currentShape;
    }
}


