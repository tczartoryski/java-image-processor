package controller;

import java.awt.Image;
import java.io.IOException;

import utils.UtilsImpl;
import model.ImageProcessorModel;
import operations.Operation;
import view.ImageProcessorGUIView;
import view.ImageProcessorView;

/**
 * This class represents a controller that works with a GUI-based view.
 */
public class ImageProcessorControllerGUI extends AbstractImageProcessorController
        implements ImageProcessorGUIController {
  private final Features features;

  /**
   * Creates a ImageProcessorControllerGUI that supports the given features for the given
   * model and view.
   *
   * @param model the model that this controller will control
   * @param view the view that this controller will control
   * @param features the features that this controller supports
   * @throws IllegalArgumentException if any of the parameters are null or if the view is not a GUI
   *                                  view
   */
  public ImageProcessorControllerGUI(ImageProcessorModel model, ImageProcessorView view,
                                     Features features) throws IllegalArgumentException {
    super(model, view);

    if (features == null) {
      throw new IllegalArgumentException("None of the parameters can be null.");
    }
    if (!(view instanceof ImageProcessorGUIView)) {
      throw new IllegalArgumentException("View must be a GUI view.");
    }
    this.features = features;
    // sets the controller of the features to this controller
    this.features.setController(this);
  }

  @Override
  public void execute() {
    // Add features to the gui view
    ((ImageProcessorGUIView) this.view).addFeatures(features);
  }

  @Override
  public void load(String filePath) {
    String fileFormat = filePath.substring(filePath.lastIndexOf('.') + 1);

    if (this.formatDirectory.get(fileFormat) != null) {
      try {
        // loads in image
        this.model.loadImage("image", this.formatDirectory.get(fileFormat).read(filePath));
        Image image = this.produceBufferedImage("image");
        ((ImageProcessorGUIView) this.view).refresh(image);
      } catch (IllegalArgumentException e) {
        try {
          this.view.renderMessage("Image unable to be loaded.");
        } catch (IOException io) {
        }
      }
    } else {
      try {
        this.view.renderMessage("The file loaded must be one of the following types .ppm, .png, .bmp, or .jpg");
      } catch (IOException f) {
      }
    }

  }

  @Override
  public void save(String filePath) {
    String fileFormat = filePath.substring(filePath.lastIndexOf('.') + 1);

    if (this.formatDirectory.get(fileFormat) != null) {
      try {
        this.formatDirectory.get(fileFormat).save(filePath, this.model.getImage("image"));
      } catch (IllegalArgumentException i) {
        try {
          this.view.renderMessage("Load an image before saving.");
        } catch (IOException f) {
        }
      }
    } else {
      try {
        this.view.renderMessage("Image must be saved as one of the following formats .jpg, .ppm, .png, or .bmp");
      } catch (IOException f) {
      }
    }
  }

  @Override
  public void doOperation(Operation op) {
    try {
      this.model.doOperation(op, "image", "image");
      Image image = this.produceBufferedImage("image");
      ((ImageProcessorGUIView) this.view).refresh(image);
    } catch (IllegalArgumentException b) {
      try {
        this.view.renderMessage("Load an image perform an operation.");
      } catch (IOException f) {
      }
    }
  }

  /**
   * Produce a BufferedImage from the image in the model.
   *
   * @param imageName the name of the image
   * @return a BufferedImage representing the image in the model.
   */
  private Image produceBufferedImage(String imageName) {
    return new UtilsImpl().createBufferedImage(this.model.getImage(imageName));
  }
}
