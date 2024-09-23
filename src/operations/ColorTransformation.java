package operations;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import model.ImageProcessorModel;

/**
 * A function objects that transforms the color of an image
 */
public class ColorTransformation implements Operation {

  /**
   * Enum for grayscale and sepia color changes.
   */
  public enum Transformation { Grayscale, Sepia }

  private final Map<Transformation, Function<int[], int[]>> transformations;

  //Transformation being used on the image.
  private final Transformation transform;

  /**
   * Constructer for ColorTransformation object with the specified transformation.
   * @param transform the type of transformation being done
   */
  public ColorTransformation(Transformation transform) {
    this.transform = transform;

    transformations = new HashMap<Transformation, Function<int[], int[]>>();

    
    // Map all the pixels of the image to its color transformation
    this.transformations.put(Transformation.Grayscale,
        pixel -> {
            int luma = (int) (0.2126 * pixel[0] + 0.7152 * pixel[1] + 0.0722 * pixel[2]);
            return new int[]{luma, luma, luma};
        });

    this.transformations.put(Transformation.Sepia,
        pixel -> new int[]{(int) (0.393 * pixel[0] + 0.769 * pixel[1] + 0.189 * pixel[2]),
            (int) (0.349 * pixel[0] + 0.686 * pixel[1] + 0.168 * pixel[2]),
            (int) (0.272 * pixel[0] + 0.534 * pixel[1] + 0.131 * pixel[2])});
  }

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int[][] copy = model.getImage(name);

    for (int i = 1; i < copy.length; i++) {
      copy[i] = this.transformations.get(this.transform).apply(copy[i]).clone();
      for (int j = 0; j < 3; j++) { 
        
        if (copy[i][j] > 255) { // clamp at 255 if color value is exceeded
          copy[i][j] = 255;
        }

      }
    }
    return copy;
  }
}
