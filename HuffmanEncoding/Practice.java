import java.util.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.image.*;
import java.awt.Container.*;

import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;

import java.lang.Math;

public class Practice {
  public String[] paths = new String[] {"Images/bird.jpg", "Images/lion.jpg", "Images/amanda.jpg", "Images/cityDrawing.jpg", "Images/squarePattern.jpg"};
  public BufferedImage[] images = new BufferedImage[paths.length];
  int[][] imageArrays = new int[paths.length][];

  private JFrame jf = new JFrame();

  Random r = new Random();

  public Practice() {
    jf.setSize(new Dimension(640, 640));
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    processImages();
  }

  private void processImages() {
    assignFilePathsToImagesArray();
    assignRGBArrays();
  }

  private void assignRGBArrays() {
    for(int i = 0; i < images.length; i++) {
      BufferedImage image = images[i];
      imageArrays[i] = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
    }
  }

  private void assignFilePathsToImagesArray() {
    for(int i = 0; i < paths.length; i++) {
      images[i] = assignFile(paths[i]);
    }
  }

  private BufferedImage assignFile(String filePath) {
    BufferedImage fileImage = null;
    try {
      fileImage = ImageIO.read(new File(filePath));
      return fileImage;
    } catch(IOException e) {
      return fileImage;
    }
  }

  private void displayImage(int image) {
    BufferedImage bImage = images[image];
    JPanel jp = new JPanel();
    JLabel jl = new JLabel(new ImageIcon(bImage));
    jp.add(jl);
    jf.add(jp);
    jf.setVisible(true);
  }

  private void displayImage(BufferedImage image) {
    JPanel jp = new JPanel();
    JLabel jl = new JLabel(new ImageIcon(image));
    jp.add(jl);
    jf.add(jp);
    jf.setVisible(true);
  }

  // private void drawOnImage() {
  //   Graphics2D g2D = (Graphics2D) birdImage.getGraphics();
  //   g2D.setStroke(new BasicStroke(3));
  //   g2D.setColor(Color.YELLOW);
  //   g2D.drawRect(birdImage.getWidth() - 10, birdImage.getHeight() - 30, 5, 25);
  // }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch(InterruptedException e) {
    }
  }

  // private void colorBackgroundGreaterThanBoundary(int boundary, int color) {
  //   LinkedList<Integer> indeces = new LinkedList<Integer>();
  //   indeces.add(0);
  //   indeces.add(0);
  //   color = ARGBVal(0, 0, 0, 0);
  //   setRGB(0, 0, color, 1);
  //   int width = lionImage.getWidth();
  //   int height = lionImage.getHeight();
  //   int pixelsProcessed = 0;
  //   while(indeces.size() != 0) {
  //     int x = indeces.removeFirst();
  //     int y = indeces.removeFirst();
  //     if(x > 0) {
  //       int pixelAdj = getRGB(x-1, y, 1);
  //       int blue = blueVal(pixelAdj);
  //       int green = greenVal(pixelAdj);
  //       int red = redVal(pixelAdj);
  //       if(blue >= boundary && green >= boundary && red >= boundary) {
  //         color = ARGBVal((x-1)%256, 0, y%256, 0);
  //         setRGB(x-1, y, color, 1);
  //         indeces.add(x-1);
  //         indeces.add(y);
  //       }
  //     }
  //     if(x + 1 < width) {
  //       int pixelAdj = getRGB(x+1, y, 1);
  //       int blue = blueVal(pixelAdj);
  //       int green = greenVal(pixelAdj);
  //       int red = redVal(pixelAdj);
  //       if(blue >= boundary && green >= boundary && red >= boundary) {
  //         color = ARGBVal((x+1)%256, 0, y%256, 0);
  //         setRGB(x+1, y, color, 1);
  //         indeces.add(x+1);
  //         indeces.add(y);
  //       }
  //     }
  //     if(y > 0) {
  //       int pixelAdj = getRGB(x, y-1, 1);
  //       int blue = blueVal(pixelAdj);
  //       int green = greenVal(pixelAdj);
  //       int red = redVal(pixelAdj);
  //       if(blue >= boundary && green >= boundary && red >= boundary) {
  //         color = ARGBVal(x%256, 0, (y-1)%256, 0);
  //         setRGB(x, y-1, color, 1);
  //         indeces.add(x);
  //         indeces.add(y-1);
  //       }
  //     }
  //     if(y + 1 < height) {
  //       int pixelAdj = getRGB(x, y+1, 1);
  //       int blue = blueVal(pixelAdj);
  //       int green = greenVal(pixelAdj);
  //       int red = redVal(pixelAdj);
  //       if(blue >= boundary && green >= boundary && red >= boundary) {
  //         color = ARGBVal(x%256, 0, (y+1)%256, 0);
  //         setRGB(x, y+1, color, 1);
  //         indeces.add(x);
  //         indeces.add(y+1);
  //       }
  //     }
  //     pixelsProcessed++;
  //     if(pixelsProcessed % 200 == 0) {
  //       updateImage(1);
  //       displayImage(1);
  //     }
  //   }
  // }

  private void helpFrom(int x, int y, int width, int height, int boundary, int color) {
    setRGB(x, y, color, 1);
    if(x > 0) {
      int pixelAdj = getRGB(x-1, y, 1);
      int blue = blueVal(pixelAdj);
      int green = greenVal(pixelAdj);
      int red = redVal(pixelAdj);
      if(blue >= boundary && green >= boundary && red >= boundary) {
        helpFrom(x-1, y, width, height, boundary, color);
      }
    }
    if(x + 1 < width) {
      int pixelAdj = getRGB(x+1, y, 1);
      int blue = blueVal(pixelAdj);
      int green = greenVal(pixelAdj);
      int red = redVal(pixelAdj);
      if(blue >= boundary && green >= boundary && red >= boundary) {
        helpFrom(x+1, y, width, height, boundary, color);
      }
    }
    if(y > 0) {
      int pixelAdj = getRGB(x, y-1, 1);
      int blue = blueVal(pixelAdj);
      int green = greenVal(pixelAdj);
      int red = redVal(pixelAdj);
      if(blue >= boundary && green >= boundary && red >= boundary) {
        helpFrom(x, y-1, width, height, boundary, color);
      }
    }
    if(y + 1 < height) {
      int pixelAdj = getRGB(x, y+1, 1);
      int blue = blueVal(pixelAdj);
      int green = greenVal(pixelAdj);
      int red = redVal(pixelAdj);
      if(blue >= boundary && green >= boundary && red >= boundary) {
        helpFrom(x, y+1, width, height, boundary, color);
      }
    }
  }

  public static int ARGBVal(int blue, int green, int red, int alpha) {
    return (alpha<<24) + (red<<16) + (green<<8) + blue;
  }

  public static int blueVal(int ARGBVal) {
    return (ARGBVal)&255;
  }
  public static int greenVal(int ARGBVal) {
    return (ARGBVal>>8)&255;
  }
  public static int redVal(int ARGBVal) {
    return (ARGBVal>>16)&255;
  }
  public static int alphaVal(int ARGBVal) {
    return (ARGBVal>>24)&255;
  }

  private int getRGB(int x, int y, int image) {
    int[] array = imageArrays[image];
    int width = images[image].getWidth();

    return array[y*width + x];
  }

  private void setRGB(int x, int y, int color, int image) {
    int[] array = imageArrays[image];
    int width = images[image].getWidth();


    array[y*width + x] = color;
  }

  private void updateImage(int image) {
    BufferedImage bImage = images[image];;
    int[] array = imageArrays[image];
    int width = bImage.getWidth();
    int height = bImage.getHeight();

    bImage.setRGB(0, 0, width, height, array, 0, width);
  }

  private void cycleGroups(int image, int difference, int horizontalGroups, int verticalGroups) {
    int[] array = imageArrays[image];
    int width = images[image].getWidth();
    int height = images[image].getHeight();

    ArrayList<Pixel> forSorting = new ArrayList<Pixel>(width*height);
    Pixel[] pixels = new Pixel[array.length];
    initPixels(pixels, forSorting, width, height, array);

    int groupWidth = width/horizontalGroups;
    int groupHeight = height/verticalGroups;
    int[] leftStarts = new int[] {0, -groupWidth/2};
    int[] topStarts = new int[] {0, -groupHeight/2};
    for(int spaceShift = 0; spaceShift < leftStarts.length; spaceShift++) {
      LinkedList<Group> groups = fillGroups(pixels, spaceShift, leftStarts, topStarts, width, height, groupWidth, groupHeight);
      partitionGroups(groups, forSorting, difference);
      unifyGroups(groups, pixels);
    }

    colorByGroup(pixels, image);
  }

  private void colorByGroup(Pixel[] pixels, int image) {
    // Pixel lastHead = null;
    for(int i = 0; i < pixels.length; i++) {
      Pixel recolor = pixels[i];
      Pixel head = recolor.findHead();
      // if(head != lastHead) {
      //   System.out.println(head);
      //   lastHead = head;
      // }
      if(head.roundAssignedColor != 1) {
        head.randomRGB(1);
      }
      int color = head.groupRGB;
      setRGB(recolor.x, recolor.y, color, image);
    }
  }

  public LinkedList<Group> fillGroups(Pixel[] pixels, int spaceShift, int[] leftStarts, int[] topStarts, int width, int height, int groupWidth, int groupHeight) {
    LinkedList<Group> groups = new LinkedList<Group>();
    for(int left = leftStarts[spaceShift]; left < width; left+=groupWidth) {
      for(int top = topStarts[spaceShift]; top < height; top+=groupHeight) {
        Group spatialGroup = new Group();
        int groupLeft = Math.max(left, 0);
        int groupTop = Math.max(top, 0);
        int groupRight = Math.min(left + groupWidth, width);
        int groupBottom = Math.min(top + groupHeight, height);
        // System.out.println(groupLeft + " " + groupRight + " " + groupTop + " " + groupBottom);
        for(int i = groupLeft; i < groupRight; i++) {
          for(int j = groupTop; j < groupBottom; j++) {
            Pixel addToGroup = pixels[j*width + i];
            addToGroup.group = spatialGroup;
          }
        }
        groups.add(spatialGroup);
      }
    }
    return groups;
  }

  public void initPixels(Pixel[] pixels, ArrayList<Pixel> forSorting, int width, int height, int[] array) {
    for(int i = 0; i < pixels.length; i++) {
      int x = i%width;
      int y = i/width;
      Pixel pixel = new Pixel(x, y, array[i]);
      forSorting.add(pixel);
      pixels[i] = pixel;
    }
  }

  public void partitionGroups(LinkedList<Group> groups, ArrayList<Pixel> forSorting, int difference) {
    for(int color = 0; color < 3; color++) {
      sort(forSorting, color);
      for(int i = 0; i < forSorting.size(); i++) {
        Pixel positionWithinGroup = forSorting.get(i);
        positionWithinGroup.reposition();
      }
      int numGroups = groups.size();
      for(int i = 0; i < numGroups; i++) {
        Group partition = groups.removeFirst();
        partition.split(color, groups, difference);
      }
    }
  }

  public void unifyGroups(LinkedList<Group> groups, Pixel[] pixels) {
    for(int i = 0; i < pixels.length; i++) {
      pixels[i].reposition();
    }
    while(groups.size() != 0) {
      Group unify = groups.removeFirst();
      unify.union();
    }
  }

  public void sort(ArrayList<Pixel> forSorting, int color) {
    if(color == 0) {
      Collections.sort(forSorting, (Pixel a, Pixel b) -> blueVal(a.rgb) - blueVal(b.rgb));
    } else if(color == 1) {
      Collections.sort(forSorting, (Pixel a, Pixel b) -> greenVal(a.rgb) - greenVal(b.rgb));
    } else {
      Collections.sort(forSorting, (Pixel a, Pixel b) -> redVal(a.rgb) - redVal(b.rgb));
    }
  }

  public void doCycles(int image) {
    for(int difference = 3; difference < 10; difference++) {
      for(int numGroups = 100; numGroups <= 250; numGroups+=5) {
        displayImage(image);
        cycleGroups(image, difference, numGroups, numGroups);
        updateImage(image);
      }
    }
    displayImage(image);
  }

  public void mash(int imageOne, int imageTwo, int width) {
    BufferedImage lowerImage = images[imageOne];
    BufferedImage upperImage = images[imageTwo];

    int lWidth = Math.min(lowerImage.getWidth(), upperImage.getWidth());
    int lHeight = Math.min(lowerImage.getHeight(), upperImage.getHeight());

    RSList starts = new RSList(lWidth, lHeight);

    int[][] canceled = new int[lWidth][lHeight];
    for(int i = 0; i < canceled.length; i++) {
      canceled[i][0] = 1;
      canceled[i][canceled[0].length-1] = 1;
    }
    for(int i = 0; i < canceled[0].length; i++) {
      canceled[0][i] = 1;
      canceled[canceled.length-1][i] = 1;
    }
    int iterations = 0;
    while(/*starts.size > 0*/iterations < 200000) {
      iterations++;
      RandomStart startFrom = starts.getFirst();
      overlap(startFrom.x, startFrom.y, canceled, imageOne, imageTwo, starts);
      System.out.println(starts.size);
    }
  }

  private void overlap(int x, int y, int[][] canceled, int imageOne, int imageTwo, RSList starts) {
    if(x == 0 || x == canceled.length-1 || y == 0 || y == canceled[0].length-1) {
      return;
    }
    int direction = r.nextInt(4);
    if((direction & 1) == 0) {
      int xChange = 1;
      if(direction == 2) {
        xChange = -1;
      }
      canceled[x-xChange][y]++;
      canceled[x-xChange][y-1]++;
      canceled[x-xChange][y+1]++;
      starts.remove(x-xChange, y);
      starts.remove(x-xChange, y-1);
      starts.remove(x-xChange, y+1);
      while(canceled[x][y] == 0) {
        setRGB(x, y, getRGB(x, y, imageTwo), imageOne);
        canceled[x][y] = 1;
        canceled[x][y+1]++;
        canceled[x][y-1]++;
        starts.remove(x, y);
        starts.remove(x, y+1);
        starts.remove(x, y-1);
        x += xChange;
      }
    } else {
      int yChange = 1;
      if(direction == 3) {
        yChange = -1;
      }
      canceled[x][y-yChange]++;
      canceled[x-1][y-yChange]++;
      canceled[x+1][y-yChange]++;
      starts.remove(x, y-yChange);
      starts.remove(x-1, y-yChange);
      starts.remove(x+1, y-yChange);
      while(canceled[x][y] == 0) {
        setRGB(x, y, getRGB(x, y, imageTwo), imageOne);
        canceled[x][y] = 1;
        canceled[x+1][y]++;
        canceled[x-1][y]++;
        starts.remove(x, y);
        starts.remove(x+1, y);
        starts.remove(x-1, y);
        y += yChange;
      }
    }
  }

  public void sinWave(int pixelsPerWave, int image) {
    BufferedImage bImage = images[image];
    int width = bImage.getWidth();
    int height = bImage.getHeight();

    int[] downShifts = new int[pixelsPerWave];
    double pi = 3.14159;
    for(int i = 0; i < pixelsPerWave; i++) {
      double xVal = pi * ((double) i) / pixelsPerWave;
      int yShift = sinFunct(xVal, pixelsPerWave);
      downShifts[i] = yShift;
    }

    for(int i = 0; i < width; i++) {
      int pixelInWave = i % pixelsPerWave;
      int downShift = downShifts[pixelInWave];
      int shifted = 0;
      for(int yVal = 0; yVal < height && shifted < height; yVal++) {
        int currentYIdx = (downShift + yVal)%height;
        int currentVal = getRGB(i, currentYIdx, image);
        int baseRGB = getRGB(i, yVal, image);
        setRGB(i, currentYIdx, baseRGB, image);
        shifted++;
        while(currentYIdx != yVal) {
          int nextYIdx = (currentYIdx + downShift)%height;
          int nextVal = getRGB(i, nextYIdx, image);
          setRGB(i, nextYIdx, currentVal, image);
          currentYIdx = nextYIdx;
          currentVal = nextVal;
          shifted++;
        }
      }
    }
  }

  private int sinFunct(double xVal, int pixelsPerWave) {
    double sinVal = Math.sin(xVal);
    double square = sinVal * sinVal;
    return (int) (square * pixelsPerWave);
  }

  private void tint(int blueTint, int greenTint, int redTint, int image) {
    int width = images[image].getWidth();
    int height = images[image].getHeight();
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        int newRGB = applyTint(blueTint, greenTint, redTint, getRGB(i, j, image));
        setRGB(i, j, newRGB, image);
      }
    }
  }

  private int applyTint(int blue, int green, int red, int rgb) {
    int prevBlue = blueVal(rgb);
    int prevGreen = greenVal(rgb);
    int prevRed = redVal(rgb);
    prevBlue = capColor(prevBlue + blue);
    prevGreen = capColor(prevGreen + green);
    prevRed = capColor(prevRed + red);
    int prevAlpha = alphaVal(rgb);
    return ARGBVal(prevBlue, prevGreen, prevRed, prevAlpha);
  }

  private int capColor(int color) {
    if(color < 0) {
      return 0;
    } else if(color > 255) {
      return 255;
    }
    return color;
  }

  private void displayColors(int image) {
    int[] originalArray = imageArrays[image];
    int width = images[image].getWidth();
    Separator separateByColor = new Separator(originalArray);

    int[][] imageColorArrays = separateByColor.getColors();
    BufferedImage[] singleColorImages = new BufferedImage[3];
    for(int i = 0; i < 3; i++) {
      BufferedImage singleColorImage = createBufferedImage(imageColorArrays[i], width);
      singleColorImages[i] = singleColorImage;
    }
    displaySideBySide(singleColorImages);
    // sleep(2000);
    // displayImage(singleColorImages[0]);
    // sleep(5000);
    // displayImage(singleColorImages[1]);
    // sleep(5000);
    // displayImage(singleColorImages[2]);
  }

  private void displayColorDifferences(int image) {
    int[] originalArray = imageArrays[image];
    int width = images[image].getWidth();
    DifferenceMapper mapDifferences = new DifferenceMapper(originalArray, width);
    int[][] colorDifferences = mapDifferences.getDifferences();
    BufferedImage[] singleColorDifferences = new BufferedImage[3];
    for(int i = 0; i < 3; i++) {
      BufferedImage singleColorDifference = createBufferedImage(colorDifferences[i], width);
      singleColorDifferences[i] = singleColorDifference;
    }
    // displaySideBySide(singleColorDifferences);
    displayImage(singleColorDifferences[0]);
    sleep(10000);
    displayImage(singleColorDifferences[1]);
    sleep(8000);
    displayImage(singleColorDifferences[2]);
  }

  private void displayGrayscaleDifferences(int image) {
    int[] originalArray = imageArrays[image];
    int width = images[image].getWidth();
    DifferenceMapper mapDifferences = new DifferenceMapper(originalArray, width);
    int[][] colorDifferences = mapDifferences.getDifferencesGrayscale();
    BufferedImage[] singleColorDifferences = new BufferedImage[3];
    for(int i = 0; i < 3; i++) {
      BufferedImage singleColorDifference = createBufferedImage(colorDifferences[i], width);
      singleColorDifferences[i] = singleColorDifference;
    }
    // displaySideBySide(singleColorDifferences);
    sleep(2000);
    displayImage(singleColorDifferences[0]);
    sleep(8000);
    displayImage(singleColorDifferences[1]);
    sleep(8000);
    displayImage(singleColorDifferences[2]);
  }

  private void displaySideBySide(BufferedImage[] singleColorImages) {
    String[] borderLayouts = new String[] {BorderLayout.EAST, BorderLayout.SOUTH, BorderLayout.WEST};
    for(int i = 0; i < 3; i++) {
      JPanel jp = new JPanel();
      JLabel jl = new JLabel(new ImageIcon(singleColorImages[i]));
      jp.add(jl);
      jf.add(jp, borderLayouts[i]);
    }
    jf.setVisible(true);
  }

  private BufferedImage createBufferedImage(int[] colorArray, int width) {
    int height = colorArray.length/width;
    BufferedImage singleColorImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    final int[] newImageArray = ( (DataBufferInt) singleColorImage.getRaster().getDataBuffer() ).getData();
    System.arraycopy(colorArray, 0, newImageArray, 0, colorArray.length);
    return singleColorImage;
  }

  private void displayGroups(int image, int range) {
    int[] imageArray = imageArrays[image];
    int width = images[image].getWidth();
    BFGrouper groupImage = new BFGrouper(imageArray, width, range);
    int[] imageGroups = groupImage.getGroups();
    int numGroups = groupImage.numGroups();
    VisualizeGroups visualizer = new VisualizeGroups(imageGroups, numGroups);
    int[] groupImageArray = visualizer.groupImageArray();
    BufferedImage imageOfGroups = createBufferedImage(groupImageArray, width);
    displayImage(imageOfGroups);
  }


  public static void main(String[] args) {
    Practice p = new Practice();
    int bird = 0;
    int lion = 1;
    int amanda = 2;
    int city = 3;
    int squares = 4;
    // p.displayImage(lionImage);
    // p.sleep(3000);
    // int aRGB = p.ARGBVal(0, 0, 0, 0);
    // p.colorBackgroundGreaterThanBoundary(75, aRGB);
    // p.updateImage(lionImage);
    // p.displayImage(lionImage);
    // p.doCycles(birdImage);
    // p.sinWave(8, image);
    // int tint = ARGBVal(60, 0, 0, 0);
    // p.tint(30, -30, -30, image);
    // p.mash(image2, image, 1);
    // p.updateImage(image2);
    // p.displayImage(image2);
    // int maxRange = 40;
    // p.displayGroups(city, maxRange);

    // int range = 60;
    // int image = lion;
    // String imageName = "lion";
    // // int groupsToGraph = 200;
    //
    // int[] imageArray = p.imageArrays[image];
    // int width = p.images[image].getWidth();
    // BFGrouper groupImage = new BFGrouper(imageArray, width, range);
    // int[] imageGroups = groupImage.getGroups();
    // int numGroups = groupImage.numGroups();
    // // groupsToGraph = numGroups; // groupsToGraph = Math.min(groupsToGraph, numGroups);
    // GroupGrapher gg = new GroupGrapher(imageArray, imageGroups, numGroups);
    // String fileName = "ImageCSV/" + imageName + "BFRange" + range + "Sizes.csv";
    // gg.fileAllGroups(fileName);
    // // gg.fileNGroups(groupsToGraph, fileName);

    // int range = 80;
    // int image = lion;
    // int groupToOutline = 4;
    // int colorToOutlineWith = Practice.ARGBVal(0, 255, 0, 255);
    //
    // int[] imageArray = p.imageArrays[image];
    // int width = p.images[image].getWidth();
    // BFGrouper groupImage = new BFGrouper(imageArray, width, range);
    // int[] imageGroups = groupImage.getGroups();
    // GroupOutliner go = new GroupOutliner(imageArray, imageGroups, width);
    // int[] outlinedImage = go.outlineGroup(groupToOutline, colorToOutlineWith);
    // BufferedImage bi = p.createBufferedImage(outlinedImage, width);
    // p.displayImage(bi);
  }
}
