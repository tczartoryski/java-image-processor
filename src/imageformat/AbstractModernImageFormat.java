package imageformat;



import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.UtilsImpl;

/**
 * Abstract image format that supports standard image formats, bmp, png, and jpg files.
 */
public abstract class AbstractModernImageFormat implements ImageFormat {
  @Override
  public abstract int[][] read(String path) throws IllegalArgumentException;

  @Override
  public abstract void save(String path, int[][] image) throws IllegalArgumentException;


  protected int[][] bmpAndPNGLoad(String path) throws IllegalArgumentException {
    BufferedImage image = null;

    try {
      image = ImageIO.read(new File(path));
    } catch (IOException e) {
      throw new IllegalArgumentException("File " + path + " does not exist!");
    }

    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();
    
    int[][] imagePixels = new int[imageHeight * imageWidth + 1][4]; 

    // store width and height inside the first array entry
    imagePixels[0][0] = imageWidth;
    imagePixels[0][1] = imageHeight;
    imagePixels[0][2] = 255;

    // current pixel we are on
    int pixelCount = 1;
    for (int r = 0; r < imageHeight; r++) {
      for (int c = 0; c < imageWidth; c++) {
        int pixelData = image.getRGB(c, r); // get rgb values from pixel
        Color colorTemp = new Color(pixelData, true);      // store color values in array
        imagePixels[pixelCount][0] = colorTemp.getRed();
        imagePixels[pixelCount][1] = colorTemp.getGreen();
        imagePixels[pixelCount][2] = colorTemp.getBlue();
        imagePixels[pixelCount][3] = colorTemp.getAlpha();
        pixelCount++;      // increment pixel count
      }
    }
    return imagePixels;
  }

  // creates a buffered image
  protected Image createBufferedImage(int[][] image) {
    return new UtilsImpl().createBufferedImage(image);
  }
}
