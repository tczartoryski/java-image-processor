package control;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import control.commands.Brighten;
import control.commands.HorizontalFlip;
import control.commands.ICommand;
import control.commands.Load;
import control.commands.Save;
import control.commands.VerticalFlip;
import control.commands.filters.GreyScale;
import control.commands.filters.Sepia;
import control.commands.greyscale.RedComponent;
import control.commands.greyscale.BlueComponent;
import control.commands.greyscale.GreenComponent;
import control.commands.greyscale.IntensityComponent;
import control.commands.greyscale.LumaComponent;
import control.commands.greyscale.ValueComponent;
import control.commands.operations.GaussianBlur;
import control.commands.operations.ImageSharpen;
import model.ImageModel;
import model.ModelImpl;


/**
 * Implementation of the Controller interface and its methods.
 */
public class ControllerImpl implements ImageController {

  private Readable r;
  private Appendable ap;
  private ImageModel m;

  private Map<String, ICommand> commandMap;

  /**
   * Constructor for the controller.
   * @param m an ImageModel.
   * @param r is a readable which is the input.
   * @param ap is the appendable which is the output.
   * @throws IllegalArgumentException if any of the parameters are null.
   */
  public ControllerImpl(ImageModel m, Readable r, Appendable ap)
          throws IllegalArgumentException {
    if (m == null || r == null || ap == null) {
      throw new
              IllegalArgumentException("Failed to instantiate the controller due to a null value");
    }
    this.m = m;
    this.r = r;
    this.ap = ap;
    registerCommands();
  }

  /**
   * Default constructor.
   */
  public ControllerImpl() {
    this.m =  new ModelImpl();
    this.r =  new InputStreamReader(System.in);
    this.ap = System.out;
    registerCommands();
  }

  /**
   * Start method is the main method that controls the program and facilitates its use.
   */
  public void start() {
    Scanner sc;
    if (r.toString().isEmpty()) {
      sc = new Scanner(r);
    }
    else {
      sc = new Scanner(System.in);
    }

    homeScreen(); //prompt for the instruction name
    boolean quit = false;
    while (!quit) { //continue until the user quits

      String[] args = new String[0];
      String userInstruction = "";
      userInstruction = sc.nextLine();
      System.out.println(userInstruction);
      args = userInstruction.split(" ");
      System.out.println("Read in args");
      //System.out.println(args[0] + args[1] + args[2]);

      if (args.length >= 1 && args[0].equals("quit") || args[0].equalsIgnoreCase("q")) {
        System.out.println("We are here5");
        quit = true;
        break;
      }
      else if (args.length == 2 && args[0].equalsIgnoreCase("read-from")) {
        try {
          System.out.println(args[1]);
          FileReader f = new FileReader(args[1]);
          Scanner r = new Scanner(f);
          while (r.hasNextLine()) {
            args = r.nextLine().split(" ");
            commandMap.get(args[0]).apply(m, args);
          }

        } catch (IOException e) {
          writeMessage(e.getMessage());
        }
      }
      else if (args.length >= 1 && commandMap.containsKey(args[0])) {
        try {
          System.out.println("We are here3");
          commandMap.get(args[0]).apply(m, args);
        } catch (IllegalArgumentException e) {
          writeMessage("Error: " + e.getMessage());
        }
      }
      else {
        writeMessage("Invalid command: " + userInstruction);
      }

    }
  }


  protected void writeMessage(String message) throws IllegalStateException {
    try {
      ap.append(message);
      System.out.println(message);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  private void registerCommands() {
    commandMap = new HashMap<String, ICommand>();
    commandMap.put("load", new Load());
    commandMap.put("save", new Save());
    commandMap.put("brighten", new Brighten());
    commandMap.put("vertical-flip", new VerticalFlip());
    commandMap.put("horizontal-flip", new HorizontalFlip());
    commandMap.put("red-component", new RedComponent());
    commandMap.put("green-component", new GreenComponent());
    commandMap.put("blue-component", new BlueComponent());
    commandMap.put("value-component", new ValueComponent());
    commandMap.put("luma-component", new LumaComponent());
    commandMap.put("intensity-component", new IntensityComponent());
    commandMap.put("sharpen", new ImageSharpen());
    commandMap.put("blur", new GaussianBlur());
    commandMap.put("greyscale", new GreyScale());
    commandMap.put("sepia", new Sepia());

  }

  private void homeScreen() {
    writeMessage("model.Image Processing Program\n"
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
            + "sharpen image-name dest-image-name\n"
            + "blur image-name dest-image-name\n"
            + "sepia image-name dest-image-name\n"
            + "greyscale image-name dest-image-name\n"
            + "Q to quit\n"
            + "Type Instruction : "); //prompt for the instruction name
  }

}