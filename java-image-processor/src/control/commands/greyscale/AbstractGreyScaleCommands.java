package control.commands.greyscale;

import java.util.Arrays;
import java.util.Collections;

import control.commands.ICommand;
import model.Image;
import model.ImageImpl;
import model.ImageModel;


/**
 * Abstract class for the grey scale commands used to abstract away the apply method.
 */
public abstract class AbstractGreyScaleCommands implements ICommand {
  @Override
  public void apply(ImageModel e, String[] args) throws IllegalArgumentException {
    if (args.length == 3) {
      if (!e.contains(args[1])) {
        throw new IllegalArgumentException(args[1] + " is not contained in this model");
      }

      Image m = new ImageImpl();
      m.setAllData(e.getImage(args[1]));

      String [] component = args[0].split("-");
      for (int i = 0; i < m.getImageHeight(); i++) {
        for (int j = 0; j < m.getImageWidth(); j++) {

          int value = setValues(m ,i,j, component[0]);

          if (value == - 1) {
            throw new IllegalArgumentException("A valid component was not provided");
          }
          m.setDataAt(i,j,0,value);
          m.setDataAt(i,j,1,value);
          m.setDataAt(i,j,2,value);
        }
      }
      m.changeName(args[2]);
      e.addImage(m.getName(),m);
    }
    else {
      throw new IllegalArgumentException("Invalid number of arguments present!");
    }
  }

  private int setValues(Image m,int i, int j, String component)
          throws IllegalArgumentException {
    int red = m.getDataAt(i,j)[0];
    int green = m.getDataAt(i,j)[1];
    int blue = m.getDataAt(i,j)[2];

    switch (component) {

      case "red" : return red;

      case "green" : return green;

      case "blue" : return blue;

      case "value" :
        return Collections.max(Arrays.asList(red,green,blue));

      case "intensity" :
        return (red + green + blue) / 3;

      case "luma" :
        return (int) Math.rint( (0.2126 * red + 0.7152 * green + 0.0722 * blue));

      default : return -1;
    }

  }


}
