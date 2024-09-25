import java.util.Arrays;

public class PureGame {
    private int width;
    private int height;
    private int[][] cells; // Represents the board
    private int[][] currentShape;
    private int[][] nextShape; // Declare nextShape

    public int[][] getNextShape() {
        return nextShape; // Getter for nextShape
    }

    public PureGame(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new int[height][width]; // Assuming 0 for empty, 1 for filled
        this.currentShape = new int[0][0]; // Placeholder for current shape
        this.nextShape = new int[0][0]; // Placeholder for next shape
    }

    public void setCurrentShape(int[][] shape) {
        this.currentShape = shape;
        // Logic to position the shape on the board if needed
    }

    public void setNextShape(int[][] shape) {
        this.nextShape = shape;
        // Logic to position the shape on the board if needed
    }

    public int[][] getCells() {
        // Return a deep copy of the cells to avoid external modifications
        int[][] copy = new int[height][width];
        for (int i = 0; i < height; i++) {
            copy[i] = Arrays.copyOf(cells[i], width);
        }
        return copy;
    }

    public void setCells(int[][] newCells) {
        this.cells = newCells;
    }

    public void updateCellsFromBoard(int[][] boardDimension) {
        // Ensure that the boardDimension is the same size
        if (boardDimension.length == height && boardDimension[0].length == width) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    cells[i][j] = boardDimension[i][j]; // Copy over the state
                }
            }
        } else {
            System.out.println("Error: Board dimensions do not match.");
        }
    }

    @Override
    public String toString() {
        return "PureGame{" +
                "width=" + width +
                ", height=" + height +
                ", cells=" + Arrays.deepToString(cells) +
                ", currentShape=" + Arrays.deepToString(currentShape) +
                ", nextShape=" + Arrays.deepToString(nextShape) +
                '}';
    }
}
