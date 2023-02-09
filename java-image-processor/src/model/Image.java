package model;

/**
 * Interface representing an Image.
 */
public interface Image {

  /**
   * Loads the image and sets the field equal to their respective values.
   * @param filepath is the path where the file can be located.
   * @param identifier is what the image will be called.
   */
  void loadImage(String filepath, String identifier);

  /**
   * Returns an integer array containing the colors at a certain index.
   * @param index1 is the x value for the 2d array.
   * @param index2 is the y value for the 2d array.
   * @return an integer array containing the RGB values.
   */
  int[] getDataAt(int index1, int index2) throws IllegalArgumentException;

  /**
   * Returns all the data contained in the image.
   * @return a three dimensional integer array containing all the color data.
   */
  int [][][] getAllData();

  /**
   * Sets all the data of an Image equal to the data from another Image.
   * @param e is the Image being passed through.
   */
  void setAllData(Image e) throws IllegalArgumentException;

  /**
   * Sets a certain color at a certain index to a value.
   * @param index1 is the x value for the 2d array.
   * @param index2 is the y value for the 2d array.
   * @param color is the color (Red, Green, Blue) being set.
   * @param val is the value it will be set to.
   */
  void setDataAt(int index1, int index2, int color, int val) throws IllegalArgumentException;

  /**
   * Returns the height of the image.
   * @return the image height as an integer.
   */
  int getImageHeight();

  /**
   * Returns the width of the image.
   * @return the width of the image as an integer.
   */
  int getImageWidth();


  /**
   * Changes the name of an image.
   * @param name the name it will be changed into.
   */
  void changeName(String name);

  /**
   * Returns the name of the image.
   * @return the name of the image as a string.
   */
  String getName();

}
