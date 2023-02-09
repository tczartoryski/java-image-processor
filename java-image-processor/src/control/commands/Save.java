package control.commands;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import model.Image;
import model.ImageModel;


/**
 * Saves a file with a specific name to a specific path.
 */
public class Save implements ICommand {

  /**
   * Loads supported image file extensions into this function object.
   */
  public Save() {
    supported.add("png");
    supported.add("jpg");
    supported.add("bmp");
  }

  List<String> supported = new ArrayList<>();
  byte[] pixelByteData;
  List<Byte> listB = new ArrayList<Byte>();

  @Override
  public void apply(ImageModel e, String[] args) throws IllegalArgumentException {
    if (args.length == 3) {
      if (! e.contains(args[2])) {
        throw new IllegalArgumentException(args[2] + " is not contained in the model");
      }
      if (! supported.contains(args[1].substring(args[1].length() - 3))
              && !args[1].substring(args[1].length() - 3).equals("ppm")) {
        throw new IllegalArgumentException("Unsupported save format");
      }
      else if (args[1].substring(args[1].length() - 3).equals("ppm")) {
        try {
          Image m = e.getImage(args[2]);
          pixelByteData = new byte[m.getImageHeight() * (m.getImageWidth() * 3)];

          List<Byte> listB = new ArrayList<Byte>();
          for (int i = 0; i < m.getImageHeight(); i++) {
            for (int j = 0; j < m.getImageWidth() * 3; j += 3) {
              for (int k = 0; k < 3; k++) {
                if (j <= m.getImageWidth() * 3) {
                  listB.add((byte)m.getDataAt(i, j / 3)[k]);
                }
              }
            }
          }
          for (int i = 0; i < listB.size(); i++) {
            pixelByteData[i] = listB.get(i);
          }
          File f = new File(args[1]);
          if (!f.exists()) {
            f.createNewFile();
          }

          FileOutputStream fos = new FileOutputStream(f);
          fos.write(pixelByteData);
          fos.flush();
          fos.close();

        } catch (IOException ex) {
          System.out.println(ex.getMessage());
        }
      }
      Image m = e.getImage(args[2]);
      BufferedImage image = new BufferedImage(
              m.getImageWidth(),
              m.getImageHeight(),
              BufferedImage.TYPE_INT_RGB);

      for (int i = 0; i < m.getImageHeight(); i++) {
        for (int j = 0; j < m.getImageWidth(); j++) {
          int r = m.getDataAt(i, j)[0];
          int g = m.getDataAt(i, j)[1];
          int b = m.getDataAt(i, j)[2];

          // bit shift magic
          int color = (r << 16) + (g << 8) + b;
          image.setRGB(j, i, color);
        }
      }
      try {
        File f = new File(args[1]);
        if (!f.exists()) {
          f.createNewFile();
        }
        ImageIO.write(image, args[1].substring(args[1].length() - 3), f);
      } catch (IOException ex) {
        System.out.println(ex.getMessage());
      }
    }
    else {
      throw new IllegalArgumentException("Invalid number of args presented!");
    }
  }

}
