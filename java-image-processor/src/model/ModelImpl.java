package model;

import java.util.HashMap;

/**
 * An implementation of the Model interface and its methods.
 */
public class ModelImpl implements ImageModel {

  private HashMap<String, Image> cameraRoll;

  /**
   * Constructor for the Model Implementation where the hashmap is created.
   */
  public ModelImpl() {
    this.cameraRoll = new HashMap<>();
  }



  @Override
  public Image getImage(String s) throws IllegalArgumentException {
    if (!this.contains(s)) {
      throw new IllegalArgumentException("There is no image with the provided name " + s);
    }
    return cameraRoll.get(s);
  }

  @Override
  public void addImage(String s, Image i) throws IllegalArgumentException {
    if (s == null || i == null) {
      throw new IllegalArgumentException("Either image or string is null");
    }
    if (this.contains(s)) {
      throw new IllegalArgumentException("There is already an image in the model with this name");
    }
    this.cameraRoll.put(s,i);
  }

  @Override
  public void removeImage(String s) throws IllegalArgumentException {
    if (!this.contains(s)) {
      throw new IllegalArgumentException("There is no such image contained in the model");
    }
    this.cameraRoll.remove(s);
  }

  @Override
  public boolean contains(String s) {
    if (s == null) {
      return false;
    }
    return cameraRoll.containsKey(s);
  }
}
