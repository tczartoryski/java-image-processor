import org.junit.Test;



import control.commands.Brighten;
import control.commands.ICommand;
import control.commands.VerticalFlip;
import control.commands.filters.GreyScale;
import control.commands.HorizontalFlip;
import control.commands.Load;
import control.commands.Save;
import control.commands.filters.Sepia;
import control.commands.greyscale.BlueComponent;
import control.commands.greyscale.GreenComponent;
import control.commands.greyscale.IntensityComponent;
import control.commands.greyscale.LumaComponent;
import control.commands.greyscale.RedComponent;
import control.commands.greyscale.ValueComponent;
import control.commands.operations.GaussianBlur;
import control.commands.operations.ImageSharpen;
import model.Image;
import model.ImageImpl;
import model.ImageModel;
import model.ModelImpl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Testing class used to test all the commands.
 */
public class TestCommands {





  @Test
  public void testSepia() {

    ImageModel m = new ModelImpl();

    ICommand load = new Load();
    String filePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    String[] args = {"load", filePath, "Buck"};
    load.apply(m, args);
    ICommand sepia = new Sepia();
    String[] args2 = {"sepia", "Buck", "SepiaBuck"};
    sepia.apply(m, args2);
    Image sepiaBuck = m.getImage("SepiaBuck");
    Image originalBuck = new ImageImpl();
    originalBuck.loadImage(filePath, "OriginalBuck");

    for (int i = 0; i < originalBuck.getImageHeight(); i++) {
      for (int j = 0; j < originalBuck.getImageWidth(); j++) {
        int[] filterApplied = sepiaBuck.getDataAt(i, j);
        int[] testSepia = createSepia(originalBuck.getDataAt(i, j));
        assertArrayEquals(filterApplied, testSepia);
      }
    }


    // tests invalid number of arguments
    try {
      sepia = new Sepia();
      args2 = new String[]{"fail", "sepia", "Buck", "SepiaBuck"};
      sepia.apply(m, args2);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments present!");
    }

    // tests invalid argument
    try {
      sepia = new Sepia();
      args2 = new String[]{"fail", "NewBuck", "NewSepiaBuck"};
      Image buck = new ImageImpl();
      buck.loadImage(filePath, "NewBuck");
      m.addImage("NewBuck", buck);
      sepia.apply(m, args2);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid filter was provided : fail");
    }

    // tests model does not contain image
    try {
      sepia = new Sepia();
      args2 = new String[]{"sepia", "fail", "SepiaBuck"};
      Image buck = new ImageImpl();
      buck.loadImage(filePath, "New1Buck");
      m.addImage("New1Buck", buck);
      sepia.apply(m, args2);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "fail is not contained in this model");
    }


  }



  private int [] createSepia(int [] originalColors) {
    double [] dAnswer = new double[3];
    double [][] filter = new double [][] {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}};
    for (int i = 0; i < filter.length; i++) {
      for (int j = 0; j < filter.length; j++) {
        dAnswer[i] = dAnswer[i] + originalColors[j] * filter[i][j];
      }
    }
    int [] answer = new int[3];
    for (int x = 0; x < dAnswer.length; x++) {
      answer[x] = (int) Math.round(dAnswer[x]);
      if (answer[x] > 255) {
        answer[x] = 255;
      }
    }
    return answer;
  }


  @Test
  public void testGreyScale() {
    ImageModel m = new ModelImpl();
    ICommand load = new Load();
    String filePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    String[] args = {"load", filePath, "Buck"};
    load.apply(m, args);
    ICommand greyScale = new GreyScale();
    String[] args2 = {"greyscale", "Buck", "GreyScaleBuck"};
    greyScale.apply(m, args2);
    Image greyScaleBuck = m.getImage("GreyScaleBuck");
    Image lumaBuck = new ImageImpl();
    lumaBuck.loadImage(filePath, "LumaBuck");
    m.addImage(lumaBuck.getName(), lumaBuck);
    ICommand lumaComponent = new LumaComponent();
    String[] args3 = {"luma-component", "LumaBuck", "LumaBuck1"};
    lumaComponent.apply(m, args3);
    lumaBuck = m.getImage("LumaBuck1");
    assertArrayEquals(greyScaleBuck.getAllData(), lumaBuck.getAllData());


    // tests invalid number of arguments
    try {
      greyScale = new GreyScale();
      args2 = new String[]{"fail", "greyscale", "Buck", "GreyScaleBuck"};
      greyScale.apply(m, args2);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments present!");
    }

    // tests invalid argument
    try {
      greyScale = new GreyScale();
      args2 = new String[]{"fail", "Buck", "GreyScaleBuck"};
      m.removeImage("LumaBuck");
      lumaBuck = new ImageImpl();
      lumaBuck.loadImage(filePath, "LumaBuck");
      m.addImage("LumaBuck", lumaBuck);
      greyScale.apply(m, args2);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid filter was provided : fail");
    }

    // tests model does not contain image
    try {
      greyScale = new GreyScale();
      args2 = new String[]{"greyscale", "fail", "GreyScaleBuck"};
      m.removeImage("LumaBuck");
      lumaBuck = new ImageImpl();
      lumaBuck.loadImage(filePath, "LumaBuck");
      m.addImage("LumaBuck", lumaBuck);
      greyScale.apply(m, args2);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "fail is not contained in this model");
    }


  }

  @Test
  public void testBlur() {
    double[][] matrix = new double[][]{{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}};

    ImageModel m = new ModelImpl();
    ICommand load = new Load();
    String filePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Snail.ppm";
    String[] args = {"load", filePath, "Buck"};
    load.apply(m, args);

    ICommand blur = new GaussianBlur();
    String[] args2 = {"blur", "Buck", "BlurBuck"};
    blur.apply(m, args2);
    Image blurBuck = m.getImage("BlurBuck");

    Image buck = new ImageImpl();
    buck.loadImage(filePath, "Buck");
    int height = buck.getImageHeight();
    int width = buck.getImageWidth();


    int[][][] testData = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int [] colors = createNewColors(i,j,buck, matrix);
        testData[i][j] = colors;
      }
    }


    for (int i = 0; i < height; i ++) {
      for (int j = 0; j < width; j ++) {
        assertArrayEquals(blurBuck.getDataAt(i,j),testData[i][j]);
      }
    }

    // tests invalid number of arguments
    try {
      blur = new GaussianBlur();
      args2 = new String[]{"fail","blur", "Buck", "BlurBuck"};
      blur.apply(m, args2);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments");
    }

    // tests invalid argument
    try {
      m.removeImage("BlurBuck");
      m.removeImage("Buck");
      blur = new GaussianBlur();
      args2 = new String[]{"fail", "Buck", "BlurBuck"};
      buck = new ImageImpl();
      buck.loadImage(filePath, "Buck");
      m.addImage("Buck", buck);
      blur.apply(m, args2);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid operation provided");
    }

    // tests model does not contain image
    try {
      m.removeImage("Buck");
      blur = new GaussianBlur();
      args2 = new String[]{"blur", "fail", "BlurBuck"};
      buck = new ImageImpl();
      buck.loadImage(filePath, "Buck");
      m.addImage("Buck", buck);
      blur.apply(m, args2);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "fail is not contained in this model");
    }


  }

  @Test
  public void testSharpen() {
    double[][] matrix = new double[][]{{-0.125, -0.125, -0.125, -0.125, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, 0.25, 1, 0.25, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, -0.125, -0.125, -0.125, -0.125}};

    ImageModel m = new ModelImpl();
    ICommand load = new Load();
    String filePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    String[] args = {"load", filePath, "Buck"};
    load.apply(m, args);

    ICommand sharpen = new ImageSharpen();
    String[] args2 = {"sharpen", "Buck", "SharpenBuck"};
    sharpen.apply(m, args2);
    Image sharpenBuck = m.getImage("SharpenBuck");

    Image buck = new ImageImpl();
    buck.loadImage(filePath, "Buck");
    int height = buck.getImageHeight();
    int width = buck.getImageWidth();


    int[][][] testData = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int [] colors = createNewColors(i,j,buck, matrix);
        testData[i][j] = colors;
      }
    }


    for (int i = 0; i < height; i ++) {
      for (int j = 0; j < width; j ++) {
        assertArrayEquals(sharpenBuck.getDataAt(i,j),testData[i][j]);
      }
    }

    // tests invalid number of arguments
    try {
      sharpen = new ImageSharpen();
      args2 = new String[]{"fail","sharpen", "Buck", "SharpenBuck"};
      sharpen.apply(m, args2);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments");
    }


    // tests invalid argument
    try {
      m.removeImage("Buck");
      m.removeImage("SharpenBuck");
      sharpen = new ImageSharpen();
      args2 = new String[]{"fail", "Buck", "SharpenBuck"};
      buck = new ImageImpl();
      buck.loadImage(filePath, "Buck");
      m.addImage("Buck", buck);
      sharpen.apply(m, args2);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid operation provided");
    }

    // tests model does not contain image
    try {
      m.removeImage("Buck");
      sharpen = new ImageSharpen();
      args2 = new String[]{"sharpen", "fail", "SharpenBuck"};
      buck = new ImageImpl();
      buck.loadImage(filePath, "Buck");
      m.addImage("Buck", buck);
      sharpen.apply(m, args2);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "fail is not contained in this model");
    }



  }




  private int[] createNewColors(int row, int column, Image image, double [][] filter) {
    int size = filter.length;
    int[][][] kernel = new int[size][size][3];
    int bounds = (int) Math.floor(size / 2.0);

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        int heightOffset = i + (row - bounds);
        int widthOffset = j + (column - bounds);
        try {
          kernel[i][j] = image.getDataAt(heightOffset,widthOffset);
        } catch (IllegalArgumentException e) {
          kernel[i][j] = new int[] {0,0,0};
        }
      }
    }

    int[] newPixel = new int[3];
    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel.length; j++) {
        newPixel[0] += (int) Math.round((kernel[i][j][0] * filter[i][j]));
        newPixel[1] += (int) Math.round((kernel[i][j][1] * filter[i][j]));
        newPixel[2] += (int) Math.round((kernel[i][j][2] * filter[i][j]));
      }
    }
    for (int i = 0; i < 3; i++) {
      if (newPixel[i] > 255) {
        newPixel[i] = 255;
      }
      if (newPixel[i] < 0) {
        newPixel[i] = 0;
      }
    }
    return newPixel;


  }

  @Test
  public void testSave() {

    ImageModel m = new ModelImpl();
    ICommand save = new Save();
    String filePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\John.ppm";
    String [] args = {"save", filePath, "Buck"};
    try {
      save.apply(m,args);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Buck is not contained in the model");
    }

    filePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    Image buck = new ImageImpl();
    buck.loadImage(filePath,"Buck");
    m.addImage("Buck", buck);
    try {
      save.apply(m,args);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "File already exists");
    }


  }



  @Test
  public void testLoad() {
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    ImageModel m  = new ModelImpl();
    ICommand load = new Load();
    String[] args = {"load", buckFilePath, "Buck1"};
    load.apply(m, args);
    try {
      load.apply(m,args);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "There is already an image in the model with this name");
    }
    Image buck2 = new ImageImpl();
    buck2.loadImage(buckFilePath, "Buck2");
    Image buck1 = m.getImage("Buck1");
    assertTrue(m.contains("Buck1"));
    for (int i = 0 ; i < buck1.getImageHeight(); i++) {
      for (int j = 0; j < buck1.getImageWidth(); j++) {
        assertArrayEquals(buck1.getDataAt(i,j), buck2.getDataAt(i,j));
      }
    }


    try {
      load.apply(m, new String[1]);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments present!");
    }

  }

  @Test
  public void testVerticalFlip() {
    Image defalt = new ImageImpl();
    Image before = new ImageImpl();
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    before.loadImage(buckFilePath, "Before");
    defalt.loadImage(buckFilePath, "Defalt");

    ImageModel model = new ModelImpl();
    model.addImage("Before", before);

    ICommand verticalFlip = new VerticalFlip();
    String [] args = {"vertical-flip", "Before", "Flipped"};
    verticalFlip.apply(model, args);
    Image flippedBuck = model.getImage("Flipped");

    int height = flippedBuck.getImageHeight();
    //Math.abs(i - height/2) + height/2 - 1;


    // tests bottom half of flipped is equal to the top half of not flipped
    for (int i = 0; i < height / 2; i++) {
      for (int j = 0; j < flippedBuck.getImageWidth(); j++) {
        int [] bottomHalfOfFlipped = flippedBuck.getDataAt(i,j);
        int afterHeight = Math.abs(i - height / 2) + height / 2 - 1;
        int [] topHalfOfDefault = defalt.getDataAt(afterHeight,j);
        assertArrayEquals(bottomHalfOfFlipped, topHalfOfDefault);
      }
    }

    try {
      verticalFlip.apply(model, new String[1]);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments present!");
    }

    String [] failArgs = {"vertical-flip", "Saxophone", "Dog"};
    try {
      verticalFlip.apply(model, failArgs);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Saxophone is not contained in this model");
    }

  }


  @Test
  public void testHorizontalFlip() {
    Image defalt = new ImageImpl();
    Image before = new ImageImpl();
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    before.loadImage(buckFilePath, "Before");
    defalt.loadImage(buckFilePath, "Defalt");

    ImageModel model = new ModelImpl();
    model.addImage("Before", before);

    ICommand horizontalFlip = new HorizontalFlip();
    String [] args = {"horizontal-flip", "Before", "Flipped"};
    horizontalFlip.apply(model, args);
    Image buckFlipped = model.getImage("Flipped");

    int height = buckFlipped.getImageHeight();
    int width = buckFlipped.getImageWidth();


    // tests bottom half of flipped is equal to the top half of not flipped
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width / 2; j++) {
        int [] leftHalfOfFlipped = buckFlipped.getDataAt(i,j);
        int widthToSetAt = Math.abs(width / 2 - j) + width / 2 - 1;
        int [] rightHalfOfDefault = defalt.getDataAt(i, widthToSetAt);
        assertArrayEquals(leftHalfOfFlipped, rightHalfOfDefault);
      }
    }

    try {
      horizontalFlip.apply(model, new String[1]);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments present!");
    }

    String [] failArgs = {"horizontal-flip", "Saxophone", "Dog"};
    try {
      horizontalFlip.apply(model, failArgs);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Saxophone is not contained in this model");
    }


  }






  @Test
  public void testBrightness() {
    Image before = new ImageImpl();
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    before.loadImage(buckFilePath, "Before");
    Image brighten = new ImageImpl();
    brighten.loadImage(buckFilePath, "Brighten");
    Image darken = new ImageImpl();
    darken.loadImage(buckFilePath, "Darken");
    ICommand command = new Brighten();
    ImageModel m = new ModelImpl();
    m.addImage("Brighten", brighten);
    m.addImage("Darken", darken);
    String[] brightenArgs = {"brighten", "30", "Brighten", "BrightenAfter"};
    int brightenIncrement = Integer.valueOf(brightenArgs[1]);
    String[] darkenArgs = {"brighten ", "-30", "Darken", "DarkenAfter"};
    int darkenIncrement = Integer.valueOf(darkenArgs[1]);
    command.apply(m, brightenArgs);
    command.apply(m, darkenArgs);
    Image brightenAfter = m.getImage("BrightenAfter");
    Image darkenAfter = m.getImage("DarkenAfter");



    for (int i = 0; i < before.getImageHeight(); i++) {
      for (int j = 0; j < before.getImageWidth(); j++) {
        int[] beforeColors = before.getDataAt(i, j);
        int[] brightenedColors = brightenAfter.getDataAt(i, j);
        int[] darkenedColors = darkenAfter.getDataAt(i, j);
        for (int x = 0; x <= 2; x++) {
          int beforeColor = beforeColors[x];
          if (beforeColor + brightenIncrement > 255) {
            assertEquals(255, brightenedColors[x]);
          } else {
            assertEquals(beforeColors[x] + brightenIncrement, brightenedColors[x]);
          }
          if (beforeColor + darkenIncrement < 0) {
            assertEquals(0, darkenedColors[x]);
          } else {
            assertEquals(beforeColors[x] + darkenIncrement, darkenedColors[x]);
          }
        }
        // do the subtraction and compare to before
        // do the addition and compare to before
      }
    }

    try {
      command.apply(m, new String[1]);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments present!");
    }

    String [] failargsNum = {"brighten", "fail", "BrightenAfter", "BrightenAfter2"};
    try {
      command.apply(m, failargsNum);
    }
    catch (NumberFormatException e) {
      assertEquals(e.getMessage(), "fail is not a number!");
    }

    String [] failargsfrom = {"brighten", "30", "Saxophone", "BrightenAfter2"};
    try {
      command.apply(m, failargsfrom);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Saxophone is not contained in this model");
    }



  }


  @Test
  public void testRedComponent() {
    ImageModel m = new ModelImpl();
    Image buck = new ImageImpl();
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    buck.loadImage(buckFilePath, "Buck");
    ICommand redComponent = new RedComponent();
    m.addImage("Buck", buck);
    String[] args = {"red-component", "Buck", "RedBuck"};
    redComponent.apply(m, args);
    assertTrue(m.contains("Buck"));
    assertTrue(m.contains("RedBuck"));
    int height = buck.getImageHeight();
    int width = buck.getImageWidth();

    // put in actual arguments later
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int[] data = m.getImage("RedBuck").getDataAt(i, j);
        assertEquals(data[0], data[1]);
        assertEquals(data[0], data[2]);
      }
    }

    try {
      redComponent.apply(m, new String[1]);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments present!");
    }

    String [] failArgs = {"red-component", "Saxophone", "Dog"};
    try {
      redComponent.apply(m, failArgs);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Saxophone is not contained in this model");
    }

  }


  @Test
  public void testBlueComponent() {
    ImageModel m = new ModelImpl();
    Image buck = new ImageImpl();
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    buck.loadImage(buckFilePath, "Buck");
    ICommand blueComponent = new BlueComponent();
    m.addImage("Buck", buck);
    String[] args = {"blue-component", "Buck", "BlueBuck"};
    blueComponent.apply(m, args);
    // put in actual arguments later
    for (int i = 0; i < buck.getImageHeight(); i++) {
      for (int j = 0; j < buck.getImageWidth(); j++) {
        int[] data = m.getImage("BlueBuck").getDataAt(i, j);
        assertEquals(data[2], data[0]);
        assertEquals(data[2], data[1]);
      }
    }

    try {
      blueComponent.apply(m, new String[1]);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments present!");
    }

    String [] failArgs = {"blue-component", "Saxophone", "Dog"};
    try {
      blueComponent.apply(m, failArgs);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Saxophone is not contained in this model");
    }

  }


  @Test
  public void testGreenComponent() {
    ImageModel m = new ModelImpl();
    Image buck = new ImageImpl();
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    buck.loadImage(buckFilePath, "Buck");
    ICommand greenComponent = new GreenComponent();
    m.addImage("Buck", buck);
    String[] args = {"green-component", "Buck", "GreenBuck"};
    greenComponent.apply(m, args);
    // put in actual arguments later
    for (int i = 0; i < buck.getImageHeight(); i++) {
      for (int j = 0; j < buck.getImageWidth(); j++) {
        int[] data = m.getImage("GreenBuck").getDataAt(i, j);
        assertEquals(data[1], data[0]);
        assertEquals(data[1], data[2]);
      }
    }

    String [] failArgs = {"green-component", "Saxophone", "Dog"};
    try {
      greenComponent.apply(m, failArgs);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Saxophone is not contained in this model");
    }

    try {
      greenComponent.apply(m, new String[1]);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments present!");
    }

  }

  @Test
  public void testValueComponent() {
    ImageModel m = new ModelImpl();
    Image buck = new ImageImpl();
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    buck.loadImage(buckFilePath, "Buck");
    ICommand valueComponent = new ValueComponent();
    m.addImage("Buck", buck);
    String[] args = {"value-component", "Buck", "ValueBuck"};
    valueComponent.apply(m, args);
    // put in actual arguments later
    for (int i = 0; i < buck.getImageHeight(); i++) {
      for (int j = 0; j < buck.getImageWidth(); j++) {
        int[] data = m.getImage("ValueBuck").getDataAt(i, j);
        int max = data[0];
        if (max < data[1]) {
          max = data[1];
        }
        if (max < data[2]) {
          max = data[2];
        }
        assertEquals(data[0], max);
        assertEquals(data[1], max);
        assertEquals(data[2], max);
      }
    }

    String [] failArgs = {"value-component", "Saxophone", "Dog"};
    try {
      valueComponent.apply(m, failArgs);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Saxophone is not contained in this model");
    }

    try {
      valueComponent.apply(m, new String[1]);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments present!");
    }

  }


  @Test
  public void testIntensityComponent() {
    ImageModel m = new ModelImpl();
    Image buck = new ImageImpl();
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    buck.loadImage(buckFilePath, "Buck");
    ICommand intensityComponent = new IntensityComponent();
    m.addImage("Buck", buck);
    String[] args = {"intensity-component", "Buck", "IntensityBuck"};
    intensityComponent.apply(m, args);
    // put in actual arguments later
    for (int i = 0; i < buck.getImageHeight(); i++) {
      for (int j = 0; j < buck.getImageWidth(); j++) {
        int[] data = m.getImage("IntensityBuck").getDataAt(i, j);
        int average = (data[0] + data[1] + data[2]) / 3;
        assertEquals(data[0], average);
        assertEquals(data[1], average);
        assertEquals(data[2], average);
      }
    }

    String [] failArgs = {"intensity-component", "Saxophone", "Dog"};
    try {
      intensityComponent.apply(m, failArgs);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Saxophone is not contained in this model");
    }

    try {
      intensityComponent.apply(m, new String[1]);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments present!");
    }
  }



  @Test
  public void testLumaComponent() {
    ImageModel m = new ModelImpl();
    Image buck = new ImageImpl();
    String buckFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    buck.loadImage(buckFilePath, "Buck");
    ICommand lumaComponent = new LumaComponent();
    m.addImage("Buck", buck);
    String[] args = {"luma-component", "Buck", "LumaBuck"};
    lumaComponent.apply(m, args);
    // put in actual arguments later
    for (int i = 0; i < buck.getImageHeight(); i ++ ) {
      for (int j = 0; j < buck.getImageWidth(); j ++) {
        int [] data = m.getImage("LumaBuck").getDataAt(i,j);
        int luma = (int) Math.rint(((0.2126 * data[0]) + (0.7152 * data[1]) + (0.0722 * data[2])));
        assertEquals(data[0], luma);
        assertEquals(data[1], luma);
        assertEquals(data[2], luma);
      }
    }

    String [] failArgs = {"luma-component", "Saxophone", "Dog"};
    try {
      lumaComponent.apply(m, failArgs);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Saxophone is not contained in this model");
    }

    try {
      lumaComponent.apply(m, new String[1]);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid number of arguments present!");
    }

  }


}

