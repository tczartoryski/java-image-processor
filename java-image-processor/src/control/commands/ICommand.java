package control.commands;

import model.ImageModel;

/**
 * Any command that can be applied to an image.
 */
public interface ICommand {

  /**
   * Applies a command to an image.
   * @param m is the model.Image model that contains a Hashmap of images.
   * @param args is the arguments containing relevant details.
   */
  void apply(ImageModel m, String[] args);
}
