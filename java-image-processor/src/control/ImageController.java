package control;

/**
 * Controller for the ImageProcessing program.
 */
public interface ImageController {

  /**
   * Starts the ImageProcessing program and its interface.
   * @throws IllegalStateException if it asks for something that is not possible.
   */
  void start() throws IllegalStateException;

}
