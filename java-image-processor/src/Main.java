
import control.commands.ICommand;
import control.commands.Save;
import control.commands.operations.GaussianBlur;

import model.Image;
import model.ImageImpl;
import model.ImageModel;
import model.ModelImpl;

/**
 * Main class from where program is run.
 */
public class Main {

  /**
   * Main method from where program is run.
   * @param args arguments passed to the command line.
   */
  public static void main(String [] args) {
    Image bmp = new ImageImpl();
    ImageModel m = new ModelImpl();
    String filePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Ladybug.bmp";
    String [] arguments = new String[] {"blur", "Ladybug", "BlurLadybug"};
    bmp.loadImage(filePath, "Ladybug");
    m.addImage("Ladybug", bmp);
    ICommand sharpen = new GaussianBlur();
    ICommand save = new Save();
    String newfilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\BlurLadybug.bmp";
    sharpen.apply(m, arguments);
    String [] saveArguments = new String[] {"save", newfilePath, "BlurLadybug"};
    save.apply(m, saveArguments);

  }
}
