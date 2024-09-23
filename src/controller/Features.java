package controller;

/**
 * Interface of features that trigger events in the app.
 */
public interface Features {

  /**
   * Downscales an image, by a certain height and width percentage.
   */
  void downscale(int widthPercent, int heightPercent);

  /**
   * Loads an image into the app with a specific file path.
   * @param filePath the path of the file that to be loaded.
   */
  void load(String filePath);

  /**
   * Saves an file that is in the program.
   * @param filePath the path of the file to be saved.
   */
  void save(String filePath);


  /**
   * Set Controller of this Features to the controller passed in.
   * @param controller the controller that will be set
   */
  void setController(ImageProcessorGUIController controller);

  /**
   * Brightens or darkens an image by a certain amount.
   * @param input the amount to brighten/darken by.
   */
  void brightenOrDarken(int input);

  /**
   * Grayscales an image.
   */
  void grayscale();

  /**
   * Applies a sepia tone to the image.
   */
  void sepia();

  /**
   * Sharpens the image.
   */
  void sharpen();

  /**
   * Blurs an image.
   */
  void blur();

  
  /**
   * Flips an image horizontally.
   */
  void flipHorizontal();

  /**
   * Flips an image vertically.
   */
  void flipVertical();

  /**
   * Visualizes the red components of an image.
   */
  void visualizeRed();

  /**
   * Visualizes the green components of an image.
   */
  void visualizeGreen();

  /**
   * Visualizes the blue components of an image.
   */
  void visualizeBlue();

  /**
   * Visualizes the intensity of an image.
   */
  void visualizeIntensity();

  /**
   * Visualizes the value of an image's pixels.
   */
  void visualizeValue();


  /**
   * Visualizes the luma of an image.
   */
  void visualizeLuma();

}
