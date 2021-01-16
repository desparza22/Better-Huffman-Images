import java.awt.Image.*;
import java.awt.image.*;
import java.awt.Container.*;

import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;

import java.lang.Math;

public class ImageGrapher {
  BufferedImage bi = null;
  int[] imageArray = null;

  public ImageGrapher(String imagePath) {
    assignImage(imagePath);
  }

  private void assignImage(String imagePath) {
    try {
      bi = ImageIO.read(new File(imagePath));
      imageArray = bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), null, 0, bi.getWidth());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void createCSV(String fileName, int rowFactor, int columnFactor) {
    ImageGraph ig = new ImageGraph(fileName);
    createColumns(ig, rowFactor, columnFactor);
    RGBCount[][] counts = createCounts(rowFactor, columnFactor);
    char[] colors = new char[] {'B', 'G', 'R'};
    int numGrids = rowFactor*columnFactor;
    for(int color = 0; color < 3; color++) {
      for(int index = 0; index < 256; index++) {
        String rowStart = colors[color] + Integer.toString(index);
        StringBuilder rowData = new StringBuilder(rowStart.length() + 6*numGrids);
        rowData.append(rowStart);
        for(int i = 0; i < rowFactor; i++) {
          for(int j = 0; j < columnFactor; j++) {
            rowData.append(',');
            rowData.append(counts[i][j].getColor(color, index));
          }
        }
        ig.write(rowData.toString());
        ig.newLine();
      }
    }
    ig.close();
  }

  private void createColumns(ImageGraph ig, int rowFactor, int columnFactor) {
    String firstColumn = "Grids";
    int numGrids = rowFactor*columnFactor;
    int charactersPerGrid = 5;
    int charactersInColumns = firstColumn.length() + numGrids*charactersPerGrid;

    StringBuilder columns = new StringBuilder(charactersInColumns);
    columns.append(firstColumn);
    for(int i = 0; i < rowFactor; i++) {
      for(int j = 0; j < columnFactor; j++) {
        columns.append(',');
        columns.append('R');
        columns.append(i);
        columns.append('C');
        columns.append(j);
      }
    }

    ig.write(columns.toString());
    ig.newLine();
  }

  // private void addDataRow(int row, int column, RGBCount count, ImageGraph ig) {
  //   StringBuilder sb = new StringBuilder(6*256*3);
  //   sb.append('R');
  //   sb.append(row);
  //   sb.append('C');
  //   sb.append(column);
  //
  //
  //   for(int i = 0; i < 256; i++) {
  //     sb.append(',');
  //     sb.append(count.getRed(i));
  //   }
  //   for(int i = 0; i < 256; i++) {
  //     sb.append(',');
  //     sb.append(count.getGreen(i));
  //   }
  //   for(int i = 0; i < 256; i++) {
  //     sb.append(',');
  //     sb.append(count.getBlue(i));
  //   }
  //
  //   ig.write(sb.toString());
  //   ig.newLine();
  // }

  private RGBCount[][] createCounts(int rowFactor, int columnFactor) {
    int width = bi.getWidth();
    int height = bi.getHeight();
    int groupWidth = (width-1) / columnFactor + 1;
    int groupHeight = (height-1) / rowFactor + 1;
    RGBCount[][] counts = new RGBCount[rowFactor][columnFactor];

    for(int i = 0; i < rowFactor; i++) {
      for(int j = 0; j < columnFactor; j++) {
        int top = i*groupHeight;
        int bottom = Math.min(height, (i+1)*groupHeight);
        int left = j*groupWidth;
        int right = Math.min(width, (j+1)*groupWidth);
        RGBCount localCounts = createRGBCount(top, bottom, left, right);
        counts[i][j] = localCounts;
      }
    }

    return counts;
  }

  private RGBCount createRGBCount(int top, int bottom, int left, int right) {
    RGBCount count = new RGBCount();
    for(int y = top; y < bottom; y++) {
      for(int x = left; x < right; x++) {
        int rgb = getRGB(x, y);
        count.add(rgb);
      }
    }
    return count;
  }

  private int getRGB(int x, int y) {
    int width = bi.getWidth();

    return imageArray[y*width + x];
  }

  private int blueVal(int ARGBVal) {
    return (ARGBVal)&255;
  }
  private int greenVal(int ARGBVal) {
    return (ARGBVal>>8)&255;
  }
  private int redVal(int ARGBVal) {
    return (ARGBVal>>16)&255;
  }
  private int alphaVal(int ARGBVal) {
    return (ARGBVal>>24)&255;
  }

  public static void main(String[] args) {
    String imagePath = "Images/bird.jpg";
    ImageGrapher ic = new ImageGrapher(imagePath);
    String filePath = "ImageCSV/birdCSV.csv";
    ic.createCSV(filePath, 5, 6);
  }
}
