package imageformat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * A class to read and save ppm files..
 */
public class PPMImageFormat implements ImageFormat {
  @Override
  public int[][] read(String path) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(path));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + path + " does not exist!");
    }

    StringBuilder builder = new StringBuilder();
    // read the file line by line, and put into a string
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      sc.close();
      throw new IllegalArgumentException("Not a valid ppm file.");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    int[][] arr = new int[width * height + 1][3];

    arr[0][0] = width;
    arr[0][1] = height;
    arr[0][2] = maxValue;


    for (int i = 1; i < height * width + 1; i++) {
      int r = sc.nextInt();
      int g = sc.nextInt();
      int b = sc.nextInt();

      arr[i][0] = r;
      arr[i][1] = g;
      arr[i][2] = b;
    }
    sc.close();
    return arr;
  }

  @Override
  public void save(String path, int[][] image) throws IllegalArgumentException {
    if (image == null || path == null) {
      throw new IllegalArgumentException("Unable to write to file.");
    }
    else if (!(path.substring(path.lastIndexOf('.') + 1).equalsIgnoreCase("ppm"))) {
      throw new IllegalArgumentException("Image is trying to be saved as something "
              + "other than ppm.");
    }

    try {
      FileWriter writer = new FileWriter(path);
      writer.write("P3 ");
      for (int[] pixel : image) {
        writer.write(pixel[0] + " " + pixel[1] + " " + pixel[2] + "\n");
      }
      writer.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to write to file.");
    }
  }
}
