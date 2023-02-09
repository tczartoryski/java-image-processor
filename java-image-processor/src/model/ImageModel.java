package model;




/**
 * Interface representing the model for the ImageProcessing program.
 */
public interface ImageModel {

  /**
   * Returns an image based on its key (the name of the model.Image).
   * @param s is a String representing the name of the image.
   * @return the image that corresponds with the name from the Hashmap.
   */
  Image getImage(String s) throws IllegalArgumentException;

  /**
   * Adds an image to the hashmap.
   * @param s is the string name which will be used as the key in the hashmap.
   * @param i will be the actual image being stored in the hashmap.
   */
  void addImage(String s, Image i) throws IllegalArgumentException;

  /**
   * Removes an image from the hashmap using its key.
   * @param s is the String key that corresponds to the image being removed.
   */
  void removeImage(String s) throws IllegalArgumentException;

  /**
   * Returns true if the image is contained in the model.
   * @param s is the String name of the key which will be used in the hashmap.
   * @return true if the key is contained in the hashmap.
   */
  boolean contains(String s);
}
