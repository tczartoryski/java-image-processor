package view;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.BorderFactory;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;


/**
 * Implementation of a GUI view
 */
public class GUIViewImpl extends JFrame implements ImageProcessorGUIView {
  
  private final JButton visualizeLuma;
  private final JButton visualizeIntensity;
  private final JButton darken;
  private final JButton brighten;
  private final JButton flipHorizontal;
  private final JButton flipVertical;
  private final JButton sepia;
  private final JButton grayscale;
  private final JButton blur;
  private final JButton sharpen;
  private final JButton visualizeBlue;
  private final JButton visualizeGreen;
  private final JButton downscale;
  private final JLabel imagePicture;
  private final JButton load;
  private final JButton save;
  private final JButton visualizeRed;
  private final JButton visualizeValue;
  private final JLabel histogramPanel;

  /**
   * Constructs a gui view
   * @param caption the name of the frame
   */
  public GUIViewImpl(String caption) {
    super(caption);

    setSize(1000, 1000);
    setLocation(100, 0);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    JPanel operationPanel;

    // adding the panel that houses all operations
    operationPanel = new JPanel();
    operationPanel.setLayout(new GridLayout(2, 9));
    operationPanel.setSize(100, 100);
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);

    //load button
    this.load = new JButton("load");
    this.load.setActionCommand("load button");
    operationPanel.add(load);

    //save button
    this.save = new JButton("save");
    this.save.setActionCommand("save button");
    operationPanel.add(save);


    //flip-horizontal button
    this.flipHorizontal = new JButton("flip horizontal");
    this.flipHorizontal.setActionCommand("flip-horizontal button");
    operationPanel.add(flipHorizontal);

    //flip-vertical button
    this.flipVertical = new JButton("flip vertical");
    this.flipVertical.setActionCommand("flip-vertical button");
    operationPanel.add(flipVertical);

    //sepia button
    this.sepia = new JButton("sepia");
    this.sepia.setActionCommand("sepia button");
    operationPanel.add(sepia);

    //grayscale
    this.grayscale = new JButton("grayscale");
    this.grayscale.setActionCommand("grayscale button");
    operationPanel.add(grayscale);

    //blur button
    this.blur = new JButton("blur");
    this.blur.setActionCommand("blur button");
    operationPanel.add(blur);

    //sharpen button
    this.sharpen = new JButton("sharpen");
    this.sharpen.setActionCommand("sharpen button");
    operationPanel.add(sharpen);

    //visualizeBlue button
    this.visualizeBlue = new JButton("visualize blue");
    this.visualizeBlue.setActionCommand("visualize-blue button");
    operationPanel.add(visualizeBlue);

    //visualizeGreen button
    this.visualizeGreen = new JButton("visualize green");
    this.visualizeGreen.setActionCommand("visualize-green button");
    operationPanel.add(visualizeGreen);

    //visualizeRed button
    this.visualizeRed = new JButton("visualize red");
    this.visualizeRed.setActionCommand("visualize-red button");
    operationPanel.add(visualizeRed);

    //visualizeValue button
    this.visualizeValue = new JButton("visualize value");
    this.visualizeValue.setActionCommand("visualize-value button");
    operationPanel.add(visualizeValue);

    //visualizeLuma button
    this.visualizeLuma = new JButton("visualize luma");
    this.visualizeLuma.setActionCommand("visualize-luma button");
    operationPanel.add(visualizeLuma);

    //visualizeIntensity button
    this.visualizeIntensity = new JButton("visualize intensity");
    this.visualizeIntensity.setActionCommand("visualize intensity");
    operationPanel.add(visualizeIntensity);

    //darken button
    this.darken = new JButton("darken");
    this.darken.setActionCommand("darken button");
    operationPanel.add(darken);

    //brighten button
    this.brighten = new JButton("brighten");
    this.brighten.setActionCommand("brighten button");
    operationPanel.add(brighten);

    //downscale button
    this.downscale = new JButton("downscale");
    this.downscale.setActionCommand("downscale button");
    operationPanel.add(downscale);


    JPanel imageHousePanel;
    JPanel histogramHousePanel;


    imageHousePanel = new JPanel();
    histogramHousePanel = new JPanel();
    imageHousePanel.setLayout(new BoxLayout(imageHousePanel, BoxLayout.PAGE_AXIS));
    histogramHousePanel.setLayout(new BoxLayout(histogramHousePanel, BoxLayout.PAGE_AXIS));
  
    ImageIcon icon = new ImageIcon();
    imagePicture = new JLabel(icon);

    JScrollPane imagePictureScrollPane = new JScrollPane() {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(900, 450);
      }
    };

    imagePictureScrollPane.setViewportView(imagePicture);
    imageHousePanel.add(imagePictureScrollPane);


    // setting the JLabel containing the histogram
    ImageIcon icon1 = new ImageIcon();
    histogramPanel = new JLabel(icon1);

    JScrollPane histogramScrollPane = new JScrollPane() {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(900, 150);
      }
    };

    histogramScrollPane.setViewportView(histogramPanel);
    histogramHousePanel.add(histogramScrollPane);



    imageHousePanel.setBorder(BorderFactory.createTitledBorder("Image"));
    histogramHousePanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    add(imageHousePanel);
    add(histogramHousePanel);
    add(operationPanel);

    pack();
    setVisible(true);
  }



  @Override
  public void addFeatures(Features features) {
    this.save.addActionListener(evt -> {
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "PPM, JPG, PNG, & BMP Images", "jpg", "ppm", "png", "bmp");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showSaveDialog(GUIViewImpl.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        features.save(f.getPath());
      }
    });

    this.load.addActionListener(evt -> {
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "PPM, JPG, PNG, & BMP Images", "jpg", "ppm", "png", "bmp");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(GUIViewImpl.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        features.load(f.getPath());
      }
    });
  

    this.brighten.addActionListener(evt -> {
      String scale = JOptionPane.showInputDialog("Please enter the quantity that you want to  "
              + "brighten this image by (note that the maximum value a pixel can "
              + "have is 255).");
      try {
        int scaleInt = Integer.parseInt(scale);
        features.brightenOrDarken(scaleInt);
      }
      catch (NumberFormatException e) {
        this.renderMessage("Please enter a valid integer to brighten the image by.");
      }
    });

    this.darken.addActionListener(evt -> {
      String scale = JOptionPane.showInputDialog("Please enter the quantity that you want to "
              + "darken this image by (note that the maximum value a pixel can "
              + "have is 255, so darkening by 255 will mean the image is as dark as possible).");
      try {
        int scaleInt = Integer.parseInt(scale);
        features.brightenOrDarken(-1 * scaleInt);
      }
      catch (NumberFormatException e) {
        this.renderMessage("Please enter a valid integer to darken the image by.");
      }
    });
    this.flipVertical.addActionListener(evt -> features.flipVertical());
    this.flipHorizontal.addActionListener(evt -> features.flipHorizontal());
    this.visualizeRed.addActionListener(evt -> features.visualizeRed());
    this.visualizeBlue.addActionListener(evt -> features.visualizeBlue());
    this.visualizeGreen.addActionListener(evt -> features.visualizeGreen());
    this.visualizeIntensity.addActionListener(evt -> features.visualizeIntensity());
    this.visualizeLuma.addActionListener(evt -> features.visualizeLuma());
    this.visualizeValue.addActionListener(evt -> features.visualizeValue());
    this.grayscale.addActionListener(evt -> features.grayscale());
    this.sepia.addActionListener(evt -> features.sepia());
    this.blur.addActionListener(evt -> features.blur());
    this.sharpen.addActionListener(evt -> features.sharpen());

    this.downscale.addActionListener(evt -> {
      String width = JOptionPane.showInputDialog("Please enter the percentage that you "
              + "wish to decrease the image width to.");
      String height = JOptionPane.showInputDialog("Please enter the percentage that you "
              + "wish to decrease the image height to.");
      try {
        int widthInt = Integer.parseInt(width);
        int heightInt = Integer.parseInt(height);
        if (widthInt < 100 && heightInt < 100) {
          features.downscale(widthInt, heightInt);
        }
        else {
          this.renderMessage("Please downscale the image to a percent less than 100.");
        }
      }
      catch (NumberFormatException e) {
        this.renderMessage("Please enter valid integers for the percentage to reduce the width and "
                + "height to.");
      }
    });
  }

  @Override
  public void refresh(Image i) {
    ImageIcon image = new ImageIcon(i);
    this.imagePicture.setIcon(image); 

    Image histogram = this.createHistogram(i);
    ImageIcon icon = new ImageIcon(histogram);
    histogramPanel.setIcon(icon);
  }

  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  /**
   * Visualizes the image histogram of the image.
   * @param image the image for the histogram
   * @return histogram for the color distribution of the image
   */
  private Image createHistogram(Image image) {
    int[] redCounts = new int[256];
    int[] greenCounts = new int[256];
    int[] blueCounts = new int[256];
    int[] intensityCounts = new int[256];

    Arrays.fill(redCounts, 0);
    Arrays.fill(greenCounts, 0);
    Arrays.fill(blueCounts, 0);
    Arrays.fill(intensityCounts, 0);

    if (image instanceof BufferedImage) {
      BufferedImage buffered = (BufferedImage) image;

      for (int i = 0; i < buffered.getWidth(); i++) {
        for (int j = 0; j < buffered.getHeight(); j++) {
          Color color = new Color(buffered.getRGB(i, j));
          redCounts[color.getRed()]++;
          greenCounts[color.getGreen()]++;
          blueCounts[color.getBlue()]++;
          intensityCounts[(color.getRed() + color.getGreen() + color.getBlue()) / 3]++;
        }
      }
    }

    BufferedImage imageRed = new BufferedImage(256, 200, BufferedImage.TYPE_INT_ARGB);
    BufferedImage imageGreen = new BufferedImage(256, 200, BufferedImage.TYPE_INT_ARGB);
    BufferedImage imageBlue = new BufferedImage(256, 200, BufferedImage.TYPE_INT_ARGB);
    BufferedImage imageIntensity = new BufferedImage(256,
            200, BufferedImage.TYPE_INT_ARGB);

    int maxValue = Math.max(Arrays.stream(redCounts).max().getAsInt(),
            Math.max(Arrays.stream(greenCounts).max().getAsInt(),
                    Math.max(Arrays.stream(blueCounts).max().getAsInt(),
                            Arrays.stream(intensityCounts).max().getAsInt())));

    int minValue = Math.min(Arrays.stream(redCounts).min().getAsInt(),
            Math.min(Arrays.stream(greenCounts).min().getAsInt(),
                    Math.min(Arrays.stream(blueCounts).min().getAsInt(),
                            Arrays.stream(intensityCounts).min().getAsInt())));

    int range = maxValue - minValue;

    for (int i = 0; i < 256; i++) {
      for (int j = 0; j < (int) ((redCounts[i] - minValue) / ((double) range) * 200); j++) {
        imageRed.setRGB(i, 199 - j, new Color(100, 0, 0, 75).getRGB());
      }

      for (int j = 0; j < (int) ((greenCounts[i] - minValue) / ((double) range) * 200); j++) {
        imageGreen.setRGB(i, 199 - j, new Color(0, 100, 0, 75).getRGB());
      }

      for (int j = 0; j < (int) ((blueCounts[i] - minValue) / ((double) range) * 200); j++) {
        imageBlue.setRGB(i, 199 - j, new Color(0, 0, 100, 75).getRGB());
      }

      for (int j = 0; j < (int) ((intensityCounts[i] - minValue) / ((double) range) * 200); j++) {
        imageIntensity.setRGB(i, 199 - j, new Color(100, 100, 100, 20).getRGB());
      }
    }

    Image histogram = new BufferedImage(350, 250, BufferedImage.TYPE_INT_ARGB);

    Graphics g = histogram.getGraphics();
    g.drawImage(imageRed, 80, 10, null);
    g.drawImage(imageGreen, 80, 10, null);
    g.drawImage(imageBlue, 80, 10, null);
    g.drawImage(imageIntensity, 80, 10, null);

    g.setFont(new Font("SansSerif", Font.BOLD, 12 ));
    g.setColor(Color.black);
    g.drawString("Pixel Value", 175, 225);
    g.drawString("Frequency", 0, 125);

    return histogram;
  }

}
