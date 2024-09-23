package controller;

import operations.Operation;

/**
 * Interface for a controller for a GUI-based view of the Image Processor
 */
public interface ImageProcessorGUIController extends ImageProcessorController {
  /**
   * Loads an image into the model.
   * @param filePath representing the path of the file to be loaded
   */
  void load(String filePath);

  /**
   * Saves an image to a path.
   * @param filePath representing the path the file is saved as
   */
  void save(String filePath);

  /**
   * Execute an Operation on the model.
   * @param op the operation to execute
   */
  void doOperation(Operation op);
}
