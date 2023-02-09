package control.commands.filters;

import control.commands.ICommand;
import model.Image;
import model.ImageImpl;
import model.ImageModel;

/**
 * Abstract class from which different filters will extend from.
 * Usefull for applying a filter using matrix multiplication.
 */
public abstract class AbstractFilter implements ICommand {

  @Override
  public void apply(ImageModel e, String[] args) {
    if (args.length == 3) {
      if (!e.contains(args[1])) {
        throw new IllegalArgumentException(args[1] + " is not contained in this model");
      }

      Image m = new ImageImpl();
      m.setAllData(e.getImage(args[1]));
      String filterType = args[0];
      String newName = args[2];

      double[][] filter;

      switch (filterType) {
        case "sepia" :
          filter = new double [][] {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168},
              {0.272, 0.534, 0.131}};
          break;
        case "greyscale" :
          filter = new double[][] {{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722},
              {0.2126, 0.7152, 0.0722}};
          break;
        default :
          filter = null;
          break;
      }

      if (filter == null) {
        throw new IllegalArgumentException("Invalid filter was provided : " + filterType);
      }


      for (int i = 0; i < m.getImageHeight(); i++) {
        for (int j = 0; j < m.getImageWidth(); j++) {
          int [] answer = applyFilter(m.getDataAt(i,j), filter);
          for (int x = 0; x < 3; x++) {
            m.setDataAt(i,j,x,answer[x]);
          }
        }
      }

      m.changeName(newName);
      e.addImage(m.getName(),m);

    }
    else {
      throw new IllegalArgumentException("Invalid number of arguments present!");
    }
  }


  private int [] applyFilter(int [] originalColors, double [][] filter) {
    double [] dAnswer = new double[3];
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


}










