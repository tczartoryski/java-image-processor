import controller.Features;
import controller.FeaturesImpl;
import controller.ImageProcessorController;
import controller.ImageProcessorControllerGUI;
import controller.ImageProcessorGUIController;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import view.GUIViewImpl;
import view.ImageProcessorView;

/**
 * This class represents a main method to run the image processor program.
 */
public class ImageProcessor {
  /**
   * A simple main method to run the image processor.
   *
   * @param args any command-line arguments necessary to run the image processor
   */
  public static void main(String[] args) throws IllegalStateException {
    // must be able to take in a text file of command arguments
    // if a valid script is provided, it should run it and then exit
    // a valid text will have q denoting when to quit
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorView view;
    ImageProcessorController controller = null;
    view = new GUIViewImpl("Image Processor");
    Features features = new FeaturesImpl();
    controller = new ImageProcessorControllerGUI(model, view, features);
    features.setController((ImageProcessorGUIController) controller);
    controller.execute();
  }
}
