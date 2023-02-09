package control.commands;

import control.commands.ICommand;
import model.Image;
import model.ImageImpl;
import model.ImageModel;

/**
 * Flips the image on its horizontal axis (halfway point).
 */
public class HorizontalFlip implements ICommand {
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



    for (int i = 0 ; i < height; i++) {
      for (int j = 0; j < width / 2; j++) {
        int widthToSetAt = Math.abs(width / 2 - j) + width / 2 - 1;
        int [] leftHalfColors = m.getDataAt(i,j);
        int [] rightHalfColors = m.getDataAt(i, widthToSetAt);
        m.setDataAt(i,j,0,rightHalfColors[0]);
        m.setDataAt(i,j,1,rightHalfColors[1]);
        m.setDataAt(i,j,2,rightHalfColors[2]);
        m.setDataAt(i,widthToSetAt, 0, leftHalfColors[0]);
        m.setDataAt(i,widthToSetAt, 1, leftHalfColors[1]);
        m.setDataAt(i,widthToSetAt, 2, leftHalfColors[2]);
      }
    }
    m.changeName(args[2]);
    e.addImage(m.getName(), m);
  }
}
