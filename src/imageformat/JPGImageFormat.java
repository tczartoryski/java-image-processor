package imageformat;



import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A class for reading and saving jpg files.
 */
public class JPGImageFormat extends AbstractModernImageFormat {
  @Override
  public int[][] read(String path) throws IllegalArgumentException {
    BufferedImage image = null;


    try {
      image = ImageIO.read(new File(path));
    } catch (IOException e) {
      throw new IllegalArgumentException("File " + path + " does not exist");
    }

    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();
    int[][] imagePixels = new int[imageWidth * imageHeight + 1][3];

    imagePixels[0][0] = imageWidth;
    imagePixels[0][1] = imageHeight;
    imagePixels[0][2] = 255; 

    // used to keep track of what pixel were on
    int pixelCount = 1;
    for (int r = 0; r < imageHeight; r++) {
      for (int c = 0; c < imageWidth; c++) {
        int pixelData = image.getRGB(c, r);
        // store color values in array
        Color colorTemp = new Color(pixelData, true);
        imagePixels[pixelCount][0] = colorTemp.getRed();
        imagePixels[pixelCount][1] = colorTemp.getGreen();
        imagePixels[pixelCount][2] = colorTemp.getBlue();
        pixelCount++;
      }
    }

    return imagePixels;
  }

  @Override
  public void save(String path, int[][] image) throws IllegalArgumentException {
    if (image == null || path == null) {
      throw new IllegalArgumentException("Failed to write to file.");
    } else if (!(path.substring(path.lastIndexOf('.') + 1)
            .equalsIgnoreCase("jpg"))) {
      throw new IllegalArgumentException("Image is trying to be saved as something "
              + "other than jpg.");
    }
    try {
     
      BufferedImage created = (BufferedImage) this.createBufferedImage(image);
      File output = new File(path);
      ImageIO.write(created, "jpg", output);
    } catch (IOException e) {
      
      throw new IllegalArgumentException("unable to write to file");
    }
  }
}
