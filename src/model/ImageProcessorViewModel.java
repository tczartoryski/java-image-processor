package model;


/**
 * Interface for a view-model with accessor methods (cannot mutate the model).
 */
public interface ImageProcessorViewModel {
  /**
   * Get a copy of the image and return it.
   *
   * @param name the name of the image we want to get
   * @return the image with that name
   * @throws IllegalArgumentException
   */
  int[][] getImage(String name) throws IllegalArgumentException;
}
