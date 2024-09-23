package utils;

import java.awt.Image;
import java.awt.Color;
import java.awt.image.BufferedImage;


public class UtilsImpl implements Utils {
  @Override
  public Image createBufferedImage(int[][] image) {
    int width = image[0][0];
    int height = image[0][1];
    
    // for bmp change to rgb instead of argb 
    BufferedImage newImage = new BufferedImage(image[0][0], image[0][1],
            BufferedImage.TYPE_INT_RGB);
    int pixelCount = 1;
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        Color temp = null;
        // convert 3 values to 1 RGB value
        if (image[pixelCount].length == 4) { //  rgba
          temp = new Color(image[pixelCount][0], image[pixelCount][1], image[pixelCount][2],
                  image[pixelCount][3]);
        } else if (image[pixelCount].length == 3) { //  rgb
          temp = new Color(image[pixelCount][0], image[pixelCount][1], image[pixelCount][2]);
        }

        int tempRGBColor = temp.getRGB();

        // set each pixel to the rgb value
        newImage.setRGB(c, r, tempRGBColor);
        pixelCount++;
      }
    }
    return newImage;
  }
}
