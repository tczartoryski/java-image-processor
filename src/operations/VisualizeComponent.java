package operations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import model.ImageProcessorModel;

/**
 * Visualize a color or brightness component of an image
 */
public class VisualizeComponent implements Operation {

  /**
   * Enum of components that can be visualized in an image
   */
  public enum Component { Red, Green, Blue, Value, Intensity, Luma }

  // Map of components to integers so we can visualize that component using those values
  private final Map<Component, Function<int[], Integer>> components;

  private final Component c;

 
  public VisualizeComponent(Component c) {
    this.c = c;

    components = new HashMap<Component, Function<int[], Integer>>();

    
    this.components.put(Component.Red, pixel -> pixel[0]);
    this.components.put(Component.Green, pixel -> pixel[1]);
    this.components.put(Component.Blue, pixel -> pixel[2]);
    this.components.put(Component.Value,
        pixel -> Math.max(pixel[0], Math.max(pixel[1], pixel[2])));
    this.components.put(Component.Intensity,
        pixel -> (pixel[0] + pixel[1] + pixel[2]) / 3);
    this.components.put(Component.Luma,
        pixel -> (int) (0.2126 * pixel[0] + 0.7152 * pixel[1] + 0.0722 * pixel[2]));
  }

  @Override
  public int[][] execute(ImageProcessorModel model, String name) {
    int[][] copy = model.getImage(name);


    for (int i = 1; i < copy.length; i++) {

      Arrays.fill(copy[i], this.components.get(this.c).apply(copy[i]));
    }
    return copy;
  }
}