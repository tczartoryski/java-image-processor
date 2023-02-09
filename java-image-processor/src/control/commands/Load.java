package control.commands;


import model.Image;
import model.ImageImpl;
import model.ImageModel;

/**
 * Loads an image and then adds it to the HashMap in the model.
 */
public class Load implements ICommand {
  @Override
  public void apply(ImageModel e, String[] args)
          throws IllegalArgumentException {
    Image m = new ImageImpl();
    if (args.length == 3) {
      if (e.contains(args[1])) {
        throw new IllegalArgumentException(args[1] + " is already contained in the model");
      }
      m.loadImage(args[1], args[2]);
      e.addImage(m.getName(), m);
    }
    else {
      throw new IllegalArgumentException("Invalid number of arguments present!");
    }
  }
}
