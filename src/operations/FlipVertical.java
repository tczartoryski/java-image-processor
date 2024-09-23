package operations;

import model.ImageProcessorModel;


/**
 * Operation to flip an image vertically.
 */
public class FlipVertical implements Operation {
  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    // create a 3d arraylist and then convert back to a 2d arraylist
    int[][] reference = model.getImage(name);

    int width = reference[0][0];
    int height = reference[0][1];
    int[][][] board = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = reference[((i * width) + j + 1)];
      }
    }

    this.flippedRows(board);

    // convert 3d board back to 2d board
    int[][] flippedDeepCopy = new int[reference.length][reference[0].length];
    flippedDeepCopy[0] = reference[0];
    int anotherCounter = 1;
    for (int row = 0; row < board.length; row++) {
      for (int column = 0; column < board[0].length; column++) {
        flippedDeepCopy[anotherCounter] = board[row][column];
        anotherCounter++;
      }
    }
    return flippedDeepCopy;
  }

  /**
   * Flip the rows of an array.
   * @param stub the array which rows are being flipped
   * @return array with rows flipped
   */
  private int[][][] flippedRows(int[][][] stub) {
    for (int i = 0; i < stub.length / 2; i++) {
      int[][] temp = stub[i];
      stub[i] = stub[stub.length - i - 1];
      stub[stub.length - i - 1] = temp;

    }
    return stub;
  }
}
