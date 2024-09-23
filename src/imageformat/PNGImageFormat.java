package imageformat;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A class to read and save png files.
 */
public class PNGImageFormat extends AbstractModernImageFormat {

  @Override
  public int[][] read(String path) throws IllegalArgumentException {
    return this.bmpAndPNGLoad(path);
  }

  @Override
  public void save(String path, int[][] image) throws IllegalArgumentException {
    if (image == null || path == null) {
      throw new IllegalArgumentException("Unable to write to file.");
    } else if (!(path.substring(path.lastIndexOf('.') + 1)
            .equalsIgnoreCase("png"))) {
      throw new IllegalArgumentException("Image is trying to be saved as something "
              + "other than png.");
    }


    try {
      BufferedImage created = (BufferedImage) this.createBufferedImage(image);
      File output = new File(path);
      ImageIO.write(created, "png", output);
    } catch (IOException e) {
      throw new IllegalArgumentException("unable to write to file");
    }
  }
}
