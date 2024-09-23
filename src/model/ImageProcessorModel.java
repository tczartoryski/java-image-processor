package model;
import operations.Operation;

/**
 * Interface for a ImageProcessorModel
 */
public interface ImageProcessorModel extends ImageProcessorViewModel {
  /**
   * Load an image with the path.
   * @param name  the name of the image
   * @param image the image being loaded into the model, as an array of pixels [( red, green, blue) ...]
   * @throws IllegalArgumentException
   */
  void loadImage(String name, int[][] image) throws IllegalArgumentException;

  /**
   * Executes operation on the image.
   * @param op   operation being executed
   * @param name the current image name
   * @param dest the name of the new image being created
   * @throws IllegalArgumentException
   */
  void doOperation(Operation op, String name, String dest) throws IllegalArgumentException;
}
