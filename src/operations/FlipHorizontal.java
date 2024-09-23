package operations;

import model.ImageProcessorModel;

/**
 * Operation for flipping an image horizontally
 */
public class FlipHorizontal implements Operation {
  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int[][] copy = model.getImage(name);
    int totalCols = copy.length / copy[0][1];

    for (int i = 1; i < copy.length; i++) {
      int row = i / copy[0][0] + 1;
      int col = i % totalCols;
      this.swap(copy, i, (copy[0][0] * row) - (col - 1));

      // After reaching halfway of the row, skip to the beginning of the next row
      if (i % (copy[0][0] / 2) == 0) {
        if (totalCols % 2 == 0) {
          i += copy[0][0] / 2;
        }
        else if (totalCols % 2 == 1) {
          i += copy[0][0] / 2 + 1;
        }
      }
    }

    return copy;
  }

  /**
   * Swap the two items in an array.
   *
   * @param arr      the array where the swapping takes place
   * @param indexOne the first index being swapped
   * @param indexTwo the second index being swapped
   */
  private void swap(int[][] arr, int indexOne, int indexTwo) {
    int[] temp = arr[indexOne];
    arr[indexOne] = arr[indexTwo];
    arr[indexTwo] = temp;
  }
}
