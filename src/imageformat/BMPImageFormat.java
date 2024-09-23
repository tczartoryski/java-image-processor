package imageformat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class for reading and saving bmp images.
 */
public class BMPImageFormat extends AbstractModernImageFormat {
  @Override
  public int[][] read(String path) throws IllegalArgumentException {
    return this.bmpAndPNGLoad(path);
  }

  @Override
  public void save(String path, int[][] image) throws IllegalArgumentException {

    if (image == null || path == null) {
      throw new IllegalArgumentException("Couldnt write to file.");
    } else if (!(path.substring(path.lastIndexOf('.') + 1)
            .equalsIgnoreCase("bmp"))) {
      throw new IllegalArgumentException("Image is trying to be saved as something "
              + "other than bmp.");
    }
    try {
      // first convert to buffered image
      // then save bufferedImage as a bmp file
      BufferedImage created = (BufferedImage) this.createBufferedImage(image);
      File output = new File(path);
      ImageIO.write(created, "BMP", output);
    } catch (IOException e) {
      throw new IllegalArgumentException("couldnt to write to file");
    }
  }
}
