package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;


/**
 * An implementation of the Image interface.
 */
public class ImageImpl implements Image {


  private String identifier;

  private int imageHeight;

  private int imageWidth;

  private String path;
  private int[][][] pixelData;

  ImageType type;
  BufferedImage image;

  private int maxValue;

  /**
   * Constructor that initializes fields.
   */
  public ImageImpl() {
    this.identifier = "";
    this.imageWidth = 0;
    this.imageHeight = 0;
    this.path = "";
    //this.pixelData = new int[imageWidth][imageHeight][3];
    this.maxValue = 255;
  }



  @Override
  public void loadImage(String filePath, String identifier) {
    this.path = filePath;
    this.identifier = identifier;
    System.out.println(filePath.substring(filePath.length() - 3, filePath.length()));
    if (filePath.startsWith("ppm", filePath.length() - 3)) {
      this.type = ImageType.PPM;
      loadPPM();
    } else if (filePath.startsWith("png", filePath.length() - 3)
            || filePath.startsWith("jpg", filePath.length() - 3)
            || filePath.startsWith("bmp", filePath.length() - 3)) {
      this.type = ImageType.ALT;
      loadAlternate();
    }
    else {
      throw new IllegalArgumentException("Unsupported image format!");
    }

  }

  private void loadPPM() {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(this.path));
    }
    catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + path + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      //System.out.println(s.charAt(0));
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }


    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    this.imageWidth = sc.nextInt();
    this.imageHeight = sc.nextInt();
    pixelData = new int[this.imageHeight][this.imageWidth][3];
    this.maxValue = sc.nextInt();
    //System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);
    // We need to add a check for if the max pixel value is over the max possible pixel value

    for (int i = 0; i < this.imageHeight; i++) {
      for (int j = 0; j < this.imageWidth; j++) {
        //Red
        pixelData[i][j][0] = sc.nextInt();
        //Green
        pixelData[i][j][1] = sc.nextInt();
        //Blue
        pixelData[i][j][2] = sc.nextInt();
      }
    }
    System.out.println("Successfully made ppm");
  }

  private void loadAlternate() {
    try {
      System.out.println("Entered loading");
      this.image = ImageIO.read(new File(this.path));
      imageWidth = image.getWidth();
      imageHeight = image.getHeight();
      pixelData = new int[imageHeight][imageWidth][3];
      int count = 0;
      for (int i = 0; i < imageHeight; i++) {
        for (int j = 0; j < imageWidth; j++) {
          int color = image.getRGB(j, i);
          Color c = new Color(color);
          pixelData[i][j][0] = c.getRed();
          pixelData[i][j][1] = c.getGreen();
          pixelData[i][j][2] = c.getBlue();
        }
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Failure to read image from: " + this.path);
    }

  }

  @Override
  public int[] getDataAt(int index1, int index2) throws IllegalArgumentException {
    if (index1 < 0 || index1 > this.imageHeight || index2 < 0 || index2 > this.imageWidth) {
      throw new IllegalArgumentException("Invalid index provided " );
    }
    try {
      return this.pixelData[index1][index2];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public int[][][] getAllData() {
    int [][][] allData = new int [this.getImageHeight()][this.getImageWidth()][3];
    for (int i = 0; i < this.getImageHeight(); i++) {
      for (int j = 0; j < this.getImageWidth(); j++) {
        for (int x = 0; x < 3; x++) {
          allData[i][j][x] = this.pixelData[i][j][x];
        }
      }
    }
    return allData;
  }

  @Override
  public void setAllData(Image e) throws IllegalArgumentException {
    int height = e.getImageHeight();
    int width = e.getImageWidth();
    int [][][] newData = new int [height][width][3];
    int [][][] oldData = e.getAllData();
    if (oldData[0][0].length != 3) {
      throw new IllegalArgumentException("Not in RGB format");
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int x = 0; x < 3; x++) {
          try {
            newData[i][j][x] = oldData[i][j][x];
          }
          catch (ArrayIndexOutOfBoundsException p) {
            throw new IllegalArgumentException();
          }
          if (oldData[i][j][x] < 0 || oldData[i][j][x] > 255 ) {
            throw new IllegalArgumentException();
          }

        }
      }
    }
    this.imageHeight = height;
    this.imageWidth = width;
    this.pixelData = newData;
  }


  @Override
  public void setDataAt(int index1, int index2,int color, int val) {
    if (index1 < 0 || index1 > this.imageHeight || index2 < 0 || index2 > this.imageWidth) {
      throw new IllegalArgumentException("Invalid index provided");
    }
    if (color < 0 || color > 2) {
      throw new IllegalArgumentException("Invalid color index provided");
    }
    if (val > this.maxValue || val < 0) {
      throw new IllegalArgumentException("Invalid color value provided " + val);
    }
    this.pixelData[index1][index2][color] = val;
  }

  @Override
  public int getImageHeight() {
    return this.imageHeight;
  }

  @Override
  public int getImageWidth() {
    return this.imageWidth;
  }

  @Override
  public void changeName(String name) {
    this.identifier = name;
  }

  @Override
  public String getName() {
    return this.identifier;
  }


}