package imageformat;
/**
 * An interface for reading and writing to specific image formats
 */
public interface ImageFormat {
  /**
   * Read an image with the given file path.
   *
   * @param path the file path of the image
   * @return image represented as a 2-dimensional array of integers
   * @throws IllegalArgumentException 
   *               
   */
  int[][] read(String path) throws IllegalArgumentException;


  /**
   * Save image to the file path.
   *
   * @param path  the file path to save the image to
   * @param image image being saved as a 2-dimensional array of integers
   * @throws IllegalArgumentException
   */
  void save(String path, int[][] image) throws IllegalArgumentException;
}
