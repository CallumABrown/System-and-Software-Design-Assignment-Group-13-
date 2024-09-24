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
