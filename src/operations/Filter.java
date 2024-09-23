package operations;


import java.util.HashMap;
import java.util.Map;

import model.ImageProcessorModel;

/**
 * Operation for blurring or sharpening an image
 */
public class Filter implements Operation {

  /**
   * Enum for the filters that can be applied
   */
  public enum Filters { Blur, Sharpen }

  ;

  // The filter that will be applied
  private final Filters filter;

  // Kernel for applying filter
  private final Map<Filters, double[][]> filtersMap;

  /**
   * Storage for filter operation.
   * @param filter the filter to be applied
   */
  public Filter(Filters filter) {
    this.filter = filter;
    this.filtersMap = new HashMap<Filters, double[][]>();
    // the kernel for applying that specific filter

    this.filtersMap.put(Filters.Blur, new double[][]{
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}});
    this.filtersMap.put(Filters.Sharpen, new double[][]{
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}});
  }

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int[][] copy = model.getImage(name);
    int numPixels = copy.length;
    int numPixelsPerRow = copy.length / copy[0][1];
    int kernelRows = this.filtersMap.get(this.filter).length;

    // Iterate through each pixel
    for (int i = 1; i < numPixels; i++) {
      // Iterate through each colr channel of the pixel
      for (int j = 0; j < 3; j++) {
        double newColor = 0;
        // Iterate through each row of the kernel
        for (int k = 0; k < kernelRows; k++) {
          int multiplicativeFactor = k - kernelRows / 2;
          int centerPixelInRowToChange = i + (multiplicativeFactor * numPixelsPerRow);
          // Iterate through each column of the current row in the kernel
          for (int l = 0; l < this.filtersMap.get(this.filter)[k].length; l++) {
            int numLeftOrRight = l - this.filtersMap.get(this.filter)[k].length / 2;
            int lastPixelInRow;

            if (i % numPixelsPerRow != 0) {
              lastPixelInRow = i + (numPixelsPerRow - (i % numPixelsPerRow));
            } else {
              lastPixelInRow = i;
            }

            if ((i + numLeftOrRight <= lastPixelInRow)
                    && !(i % numPixelsPerRow <= Math.abs(numLeftOrRight) && i % numPixelsPerRow != 0
                    && numLeftOrRight < 0)
                    && centerPixelInRowToChange + numLeftOrRight > 0
                    && centerPixelInRowToChange + numLeftOrRight < copy.length
                    && centerPixelInRowToChange > 0
                    && centerPixelInRowToChange < copy.length) {
              newColor += this.filtersMap.get(this.filter)[k][l]
                      * copy[centerPixelInRowToChange + numLeftOrRight][j];
            }
          }
        }
        if (newColor > 255) {
          newColor = 255;
        } else if (newColor < 0) {
          newColor = 0;
        }

        copy[i][j] = (int) newColor;
      }
    }
    return copy;
  }
}
