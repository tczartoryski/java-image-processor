package operations;

import model.ImageProcessorModel;

/**
 * Interface for an operation on the image
 */
public interface Operation {
  /**
   *
   * @param model the model on which the Operation is being performed on
   * @param name  the name of the image in the model which the operation is being performed on
   */
  int[][] execute(ImageProcessorModel model, String name);
}
