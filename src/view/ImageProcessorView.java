package view;

import java.io.IOException;

/**
 * Interface for image processor view
 */
public interface ImageProcessorView {
  /**
   * Renders a message to the view.
   *
   * @param message to be rendered.
   */
  public void renderMessage(String message) throws IOException;

}
