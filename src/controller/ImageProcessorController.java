package controller;

/**
 * A controller for the Image Processor.
 * The controller has a method to utilize various commands.
 * The controller allows for interaction with the model and the view.
 */
public interface ImageProcessorController {
  /**
   * Allows for sending of inputs to the model and outputs to the view enabling image processing.
   * @throws IllegalStateException if the controller fails to read from the Readable
   *                               or write to the Appendable.
   */
  public void execute() throws IllegalStateException;
}
