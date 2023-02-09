package control.commands;

import control.commands.ICommand;
import model.Image;
import model.ImageImpl;
import model.ImageModel;

/**
 * Takes an image and either brightens it or darkens it by adding a set increment to each color.
 */
public class Brighten implements ICommand {
  @Override
  public void apply(ImageModel e, String [] args)
          throws IllegalArgumentException, NumberFormatException {

    if (args.length == 4) {
      // String[0] is the original command string[1] would be the increment
      // retrieve image name being changed
      if (! e.contains(args[2])) {
        throw new IllegalArgumentException(args[2] + " is not contained in this model");
      }

      int increment;
      try {
        increment = Integer.valueOf(args[1]);
      }
      catch (NumberFormatException p) {
        throw new NumberFormatException(args[1] + " is not a number!");
      }
      Image m = new ImageImpl();
      m.setAllData(e.getImage(args[2]));

      for (int i = 0; i < m.getImageHeight(); i++) {
        for (int j = 0; j < m.getImageWidth(); j++) {
          int [] colorVals = m.getDataAt(i,j);
          for (int x = 0; x <= 2; x++) {
            if (colorVals[x] + increment > 255) {
              m.setDataAt(i,j,x,255);
            }
            else if (colorVals[x] + increment < 0) {
              m.setDataAt(i,j,x,0);
            }
            else {
              m.setDataAt(i,j,x,colorVals[x] + increment);
            }
          }
        }
      }
      m.changeName(args[3]);
      e.addImage(m.getName(), m);
    }
    else {
      throw new IllegalArgumentException("Invalid number of arguments present!");
    }
  }
}
