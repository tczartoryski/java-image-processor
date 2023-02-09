
import org.junit.Test;


import model.Image;
import model.ImageImpl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests the image class and its methods.
 */
public class TestImage {

  @Test
  public void testLoadImage() {
    Image i1 = new ImageImpl();
    String filePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    i1.loadImage(filePath, "Buck");


    assertEquals(i1.getName(), "Buck");
    assertEquals(i1.getImageHeight(),512);
    assertEquals(i1.getImageWidth(), 512);
    String path =
            "File C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Fail.ppm not found!";
    try {
      Image i2 = new ImageImpl();
      String failFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Fail.ppm";
      i2.loadImage(failFilePath, "Fail");
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), path);
    }
  }

  @Test
  public void testGetAll() {
    Image m = new ImageImpl();
    String filePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    m.loadImage(filePath, "Buck");
    assertEquals(m.getImageHeight(), 512);
    assertEquals(m.getImageWidth(), 512);
    int [][][] data = m.getAllData();
    for (int i = 0 ; i < m.getImageHeight() ; i++) {
      for (int j = 0; j < m.getImageWidth(); j++) {
        assertArrayEquals(m.getDataAt(i,j),data[i][j]);
      }
    }
  }

  @Test
  public void testSetAll() {
    Image m = new ImageImpl();
    String filePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    m.loadImage(filePath, "Buck");
    assertEquals(m.getImageHeight(), 512);
    assertEquals(m.getImageWidth(), 512);
    assertEquals(m.getAllData().length, 512);
    int [][][] data = m.getAllData();
    for (int i = 0 ; i < m.getImageHeight() ; i++) {
      for (int j = 0; j < m.getImageWidth(); j++) {
        assertArrayEquals(m.getDataAt(i,j),data[i][j]);
      }
    }
    String newFilePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Snail.ppm";
    Image e = new ImageImpl();
    e.loadImage(newFilePath, "TestImage");
    m.setAllData(e);
    assertEquals(m.getName(), "Buck");
    assertEquals(m.getImageWidth(), 256);
    assertEquals(m.getImageHeight(), 256);
    assertEquals(m.getAllData().length, 256);

  }

  @Test
  public void testNameMethods() {
    Image m = new ImageImpl();
    assertEquals(m.getName(), "");
    m.changeName("Dog");
    assertEquals(m.getName(),"Dog");
    m.changeName("Cat");
    assertEquals(m.getName(),"Cat");
    assertFalse(m.getName().equals("Random name"));
    String filePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    m.loadImage(filePath, "Buck");
    assertEquals(m.getName(), "Buck");
  }

  @Test
  public void testGetAndSetData() {
    Image m = new ImageImpl();
    String filePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    m.loadImage(filePath, "Buck");

    int [] m1 = m.getDataAt(40,40);
    assertArrayEquals(m1,new int[]{0, 0, 0});
    m.setDataAt(40,40,0, 80);
    m1 = m.getDataAt(40,40);
    assertArrayEquals(m1,new int[]{80, 0, 0});
    m.setDataAt(40,40,1, 35);
    m1 = m.getDataAt(40,40);
    assertArrayEquals(m1,new int[]{80, 35, 0});
    m.setDataAt(40,40,2, 183);
    m1 = m.getDataAt(40,40);
    assertArrayEquals(m1,new int[]{80, 35, 183});
    m.loadImage(filePath, "Buck");
    m1 = m.getDataAt(40,40);
    assertArrayEquals(m1, new int[]{0,0,0});




    // test illegal calls to the set Method
    try {
      m.setDataAt(1, 8,2,-50);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid color value provided -50");
    }
    try {
      m.setDataAt(1, 8,2,800);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid color value provided 800");
    }
    try {
      m.setDataAt(-1, 8,2,50);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid index provided");
    }
    // test illegal calls to the set Method
    try {
      m.setDataAt(1, -8,2,50);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid index provided");
    }
    // test illegal calls to the set Method
    // test illegal calls to the set Method
    try {
      m.setDataAt(1, 8,4,50);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid color index provided");
    }
    // test illegal calls to the set Method
    try {
      m.setDataAt(1, 8,-1,50);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid color index provided");
    }
    try {
      m.setDataAt(2000, 8,2,50);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid index provided");
    }
    // test illegal calls to the set Method
    try {
      m.setDataAt(1, 2000,2,50);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid index provided");
    }



    // testing illegal calls to the get Method
    try {
      m.getDataAt(-1, 8);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid index provided ");
    }
    try {
      m.getDataAt(1, -8);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid index provided ");
    }
    try {
      m.getDataAt(2000, 8);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid index provided ");
    }
    try {
      m.getDataAt(8, 2000);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid index provided ");
    }
    try {
      m.getDataAt(-1, -8);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid index provided ");
    }
    try {
      m.getDataAt(2000, 2000);
    }
    catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid index provided ");
    }

  }



  @Test
  public void testHeightAndWidth() {
    Image m = new ImageImpl();
    String filePath = "C:\\Users\\user\\IdeaProjects\\group\\Assignment4\\res\\Buck.ppm";
    assertEquals(m.getImageWidth(), 0);
    assertEquals(m.getImageHeight(), 0);
    m.loadImage(filePath, "Buck");
    assertEquals(m.getImageHeight(),512);
    assertEquals(m.getImageWidth(), 512);
  }


}
