
import org.junit.Test;


import model.Image;
import model.ImageImpl;
import model.ImageModel;
import model.ModelImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Tests the model and its methods.
 */
public class TestModel {


  @Test
  public void testAddImage() {

    Image buck = new ImageImpl();
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    buck.loadImage(buckFilePath, "Buck");
    Image snail = new ImageImpl();
    String snailFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Snail.ppm";
    snail.loadImage(snailFilePath, "Snail");
    ImageModel model = new ModelImpl();
    model.addImage(snail.getName(),snail);
    model.addImage(buck.getName(), buck);
    assertTrue(model.contains("Buck"));
    assertTrue(model.contains("Snail"));

    try {
      model.addImage("Buck", buck);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "There is already an image in the model with this name");
    }
    try {
      model.addImage(null, buck);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Either image or string is null");
    }
    try {
      model.addImage("Tree", null);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Either image or string is null");
    }
  }

  @Test
  public void testRemoveImage() {

    Image buck = new ImageImpl();
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    buck.loadImage(buckFilePath, "Buck");
    Image snail = new ImageImpl();
    String snailFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Snail.ppm";
    snail.loadImage(snailFilePath, "Snail");
    ImageModel model = new ModelImpl();
    model.addImage(snail.getName(),snail);
    model.addImage(buck.getName(), buck);
    assertTrue(model.contains("Snail"));
    model.removeImage("Snail");
    assertFalse(model.contains("Snail"));
    assertTrue(model.contains("Buck"));
    model.removeImage("Buck");
    assertFalse(model.contains("Buck"));


    try {
      model.getImage("Buck");
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "There is no image with the provided name Buck");
    }

    try {
      model.removeImage("Tree");
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "There is no such image contained in the model");
    }

  }

  @Test
  public void testContainsImage() {

    Image buck = new ImageImpl();
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    buck.loadImage(buckFilePath, "Buck");
    Image snail = new ImageImpl();
    String snailFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Snail.ppm";
    snail.loadImage(snailFilePath, "Snail");
    ImageModel model = new ModelImpl();
    model.addImage(snail.getName(),snail);
    model.addImage(buck.getName(), buck);
    assertTrue(model.contains("Buck"));
    assertTrue(model.contains("Snail"));
    assertFalse(model.contains("Tree"));
    assertFalse(model.contains(null));
  }

  @Test
  public void testGetImage() {

    Image buck = new ImageImpl();
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    buck.loadImage(buckFilePath, "Buck");
    Image snail = new ImageImpl();
    String snailFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Snail.ppm";
    snail.loadImage(snailFilePath, "Snail");
    ImageModel model = new ModelImpl();
    model.addImage(snail.getName(),snail);
    model.addImage(buck.getName(), buck);

    assertEquals(model.getImage("Snail"), snail);
    assertEquals(model.getImage("Buck"), buck);
    try {
      model.getImage("Tree");
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "There is no image with the provided name Tree");
    }
  }




}
