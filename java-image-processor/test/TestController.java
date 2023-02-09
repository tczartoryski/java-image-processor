import org.junit.Test;


import java.io.StringReader;


import control.ControllerImpl;
import control.ImageController;
import model.ImageModel;
import model.ModelImpl;

import static org.junit.Assert.assertEquals;

/**
 * Testsing class for the Controller and its methods.
 */
public class TestController {



  @Test
  public void readFrom() {
    Appendable out = new StringBuilder();
    String path = "read-from"
            + " C:UsersarmanDesktopIntelliJProjectsCS3500GroupGroupProjectAssignment4\n"
            + "esScript.txt";
    Readable in = new StringReader(path);
    ImageModel m = new ModelImpl();
    ImageController controller = new ControllerImpl(m, in, out);
    controller.start();
    assertEquals(true, m.contains("dogSepia"));
  }

  @Test
  public void testWelcomeMessage() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("q");
    ImageModel m = new ModelImpl();
    ImageController controller = new ControllerImpl(m, in, out);
    controller.start();
    String welcome = "Image Processing Program\n"
            + "load image-path image-name\n"
            + "save image-path image-name\n"
            + "red-component image-name dest-image-name\n"
            + "green-component image-name dest-image-name\n"
            + "blue-component image-name dest-image-name\n"
            + "value-component image-name dest-image-name\n"
            + "intensity-component image-name dest-image-name\n"
            + "luma-component image-name dest-image-name\n"
            + "horizontal-flip image-name dest-image-name\n"
            + "vertical-flip image-name dest-image-name\n"
            + "brighten increment image-name dest-image-name\n"
            + "Q to quit\n"
            + "Type Instruction : ";
    assertEquals(out.toString(),welcome);
  }

}
