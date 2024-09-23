package utils;

import java.awt.Image;



public interface Utils {
  /**
   * Creates an Image given a 2d array of pixels.
   * @param image 2d array of pixels that hold rgb values.
   * @return an Image that has the corresponding pixels values from the given 2d array
   */
  Image createBufferedImage(int[][] image);
}
