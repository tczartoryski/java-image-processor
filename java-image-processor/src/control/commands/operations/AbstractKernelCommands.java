package control.commands.operations;

import control.commands.ICommand;
import model.Image;
import model.ImageImpl;
import model.ImageModel;

/**
 * Abstract class from which classes that apply kernel operations extend from.
 * Uses mathematics involving kernels and matrices.
 */
public abstract class AbstractKernelCommands implements ICommand {


  private int[] createNewColors(int row, int column, Image image, double [][] matrix) {
    int size = matrix.length;
    int[][][] kernel = new int[size][size][3];
    int offset = (int) Math.floor(size / 2.0);

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        int heightOffset = i + (row - offset);
        int widthOffset = j + (column - offset);
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
        for (int x = 0; x < 3; x++) {
          newPixel[x] += (int) Math.round((kernel[i][j][x] * matrix[i][j]));
        }
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





  private int[][][] applyFilter(Image image, double[][] filter) throws IllegalArgumentException {
    if (filter.length % 2 == 0) {
      throw new IllegalArgumentException("Error: given filter must have odd dimensions");
    }
    if (filter.length != filter[0].length) {
      throw new IllegalArgumentException("Error: given filter must have equal width and height");
    }

    int width = image.getImageWidth();
    int height = image.getImageHeight();
    int[][][] newImage = new int[height][width][3];



    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        newImage[row][column] = createNewColors(row,column, image, filter);
      }
    }
    return newImage;
  }



  @Override
  public void apply(ImageModel m, String[] args) {
    if (args.length == 3) {

      if (!m.contains(args[1])) {
        throw new IllegalArgumentException(args[1] + " is not contained in this model");
      }



      Image e = new ImageImpl();
      e.setAllData(m.getImage(args[1]));
      String operation = args[0];
      double[][] matrix;

      switch (operation) {
        case "blur":
          matrix = new double[][]{{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125},
              {0.0625, 0.125, 0.0625}};
          break;
        case "sharpen":
          matrix = new double[][]{{-0.125, -0.125, -0.125, -0.125, -0.125},
              {-0.125, 0.25, 0.25, 0.25, -0.125},
              {-0.125, 0.25, 1, 0.25, -0.125},
              {-0.125, 0.25, 0.25, 0.25, -0.125},
              {-0.125, -0.125, -0.125, -0.125, -0.125}};
          break;
        default:
          matrix = null;
          break;
      }

      if (matrix == null) {
        throw new IllegalArgumentException("Invalid operation provided");
      }

      int [][][] newImage = applyFilter(e,matrix);

      for (int i = 0; i < newImage.length; i++) {
        for (int j = 0; j < newImage[i].length; j++) {
          for (int x = 0; x < newImage[i][j].length; x++) {
            int color = newImage[i][j][x];
            e.setDataAt(i,j,x,color);
          }
        }
      }

      e.changeName(args[2]);
      m.addImage(e.getName(), e);

    }
    else {
      throw new IllegalArgumentException("Invalid number of arguments");
    }

  }
}
