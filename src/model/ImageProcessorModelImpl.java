package model;

import java.util.HashMap;
import java.util.Map;

import operations.Operation;

/**
 * ImageProcessorModel implementation. It has a directory of images, and allows for loading and operating on images.
 */
public class ImageProcessorModelImpl implements ImageProcessorModel {
  // Images are 2-dimensional array of integers in the map. With each row column pair representing a pixel (RGB/RGBA).
  // The row at the 0th index of each value in the directory is a "header row" that has metadata on the image
  private final Map<String, int[][]> directory;

  public ImageProcessorModelImpl() {
    this.directory = new HashMap<String, int[][]>();
  }

  @Override
  public void loadImage(String name, int[][] image) throws IllegalArgumentException {
    System.out.println(name);
    if (name == null || image == null) {
      throw new IllegalArgumentException("Image or name is null.");
    }

    this.directory.put(name, image);
  }


  @Override
  public int[][] getImage(String name) throws IllegalArgumentException {
    if (this.directory.get(name) == null) {
      throw new IllegalArgumentException("Image does not exist.");
    } else {

      int[][] image = this.directory.get(name);
      int[][] copy = new int[image.length][image[0].length];

      for (int i = 0; i < image.length; i++) {
        for (int j = 0; j < image[i].length; j++) {
          copy[i][j] = image[i][j];
        }
      }
      return copy;
    }
  }

  @Override
  public void doOperation(Operation op, String name, String dest) throws IllegalArgumentException {
    if (op == null || name == null || dest == null) {
      throw new IllegalArgumentException("Cannot perform operation: either operation, name, or destination is null");
    }
    
    this.directory.put(dest, op.execute(this, name));
  }
}
