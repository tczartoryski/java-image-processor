package operations;

import model.ImageProcessorModel;

/**
 * Operation for brightening or darkening an image.
 * A negative integer darkens, a positive integer brightens.
 */
public class BrightenOrDarken implements Operation {

  private int scale;

  /**
   * Construct BrightenOrDarken Operation with its scale.
   * @param scale the scale of this BrightenOrDarken , positive brightens, negative darkens
   */
  public BrightenOrDarken(int scale) {
    this.scale = scale;
  }

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {

    int[][] deepCopy = model.getImage(name);

    // 0th index contains header
    for (int i = 1; i < deepCopy.length; i++) {
      for (int j = 0; j < 3; j++) {
        deepCopy[i][j] += scale;
        if (deepCopy[i][j] < 0) {
          deepCopy[i][j] = 0;
        } else if (deepCopy[i][j] > deepCopy[0][2]) {
          deepCopy[i][j] = deepCopy[0][2];
        }
      }
    }
    
    return deepCopy;
  }
}
