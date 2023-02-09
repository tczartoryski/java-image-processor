package control.commands;

import control.commands.ICommand;
import model.Image;
import model.ImageImpl;
import model.ImageModel;

/**
 * Flips the image on its vertical axis (half point).
 */
public class VerticalFlip implements ICommand {
  @Override
  public void apply(ImageModel e, String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Invalid number of arguments present!");
    }
    if (!e.contains(args[1])) {
      throw new IllegalArgumentException(args[1] + " is not contained in this model");
    }
    Image m = new ImageImpl();
    m.setAllData(e.getImage(args[1]));
    int height = m.getImageHeight();
    int width = m.getImageWidth();

    for (int i = 0 ; i < height / 2; i++) {
      for (int j = 0; j < width; j++) {
        int heightToSetAt = Math.abs(height / 2 - i) + height / 2 - 1;
        int [] bottomHalfColors = m.getDataAt(i,j);
        int [] topHalfColors = m.getDataAt(heightToSetAt, j);
        m.setDataAt(i,j,0,topHalfColors[0]);
        m.setDataAt(i,j,1,topHalfColors[1]);
        m.setDataAt(i,j,2,topHalfColors[2]);
        m.setDataAt(heightToSetAt,j, 0, bottomHalfColors[0]);
        m.setDataAt(heightToSetAt,j, 1, bottomHalfColors[1]);
        m.setDataAt(heightToSetAt,j, 2, bottomHalfColors[2]);

      }
    }
    m.changeName(args[2]);
    e.addImage(m.getName(), m);
  }
}
