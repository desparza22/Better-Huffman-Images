
public class DifferenceMapper {
  private int[] imageArray;
  private int[][] colorDifferences;
  private int[][] grayscaleDifferences;
  private int width;
  private int height;
  private int fullAlpha = 255<<24;

  public DifferenceMapper(int[] imageArray, int width) {
    this.imageArray = imageArray;
    this.width = width;
    this.height = imageArray.length/width;
    fillDifferences();
  }

  public int[][] getDifferencesGrayscale() {
    return grayscaleDifferences;
  }

  public int[][] getDifferences() {
    return colorDifferences;
  }

  private void fillDifferences() {
    colorDifferences = new int[3][imageArray.length];
    grayscaleDifferences = new int[3][imageArray.length];
    int index = 0;
    for(int y = 0; y < height; y++) {
      for(int x = 0; x < width; x++) {
        addDifferences(colorDifferences, grayscaleDifferences, x, y, index++);
      }
    }
  }

  private void addDifferences(int[][] colorDifferences, int[][] grayscaleDifferences, int x, int y, int index) {
    int neighbors = 0;
    int blueDiff = 0;
    int greenDiff = 0;
    int redDiff = 0;

    int rgb = getRGB(x, y);
    int blueVal = blueVal(rgb);
    int greenVal = greenVal(rgb);
    int redVal = redVal(rgb);

    if(x > 0) {
      neighbors++;
      int rgbNeighbor = getRGB(x-1, y);
      int blueNeighbor = blueVal(rgbNeighbor);
      int greenNeighbor = greenVal(rgbNeighbor);
      int redNeighbor = redVal(rgbNeighbor);
      blueDiff += Math.abs(blueNeighbor - blueVal);
      greenDiff += Math.abs(greenNeighbor - greenVal);
      redDiff += Math.abs(redNeighbor - redVal);
    }
    if(y > 0) {
      neighbors++;
      int rgbNeighbor = getRGB(x, y-1);
      int blueNeighbor = blueVal(rgbNeighbor);
      int greenNeighbor = greenVal(rgbNeighbor);
      int redNeighbor = redVal(rgbNeighbor);
      blueDiff += Math.abs(blueNeighbor - blueVal);
      greenDiff += Math.abs(greenNeighbor - greenVal);
      redDiff += Math.abs(redNeighbor - redVal);
    }
    if(x + 1 < width) {
      neighbors++;
      int rgbNeighbor = getRGB(x+1, y);
      int blueNeighbor = blueVal(rgbNeighbor);
      int greenNeighbor = greenVal(rgbNeighbor);
      int redNeighbor = redVal(rgbNeighbor);
      blueDiff += Math.abs(blueNeighbor - blueVal);
      greenDiff += Math.abs(greenNeighbor - greenVal);
      redDiff += Math.abs(redNeighbor - redVal);
    }
    if(y + 1 < height) {
      neighbors++;
      int rgbNeighbor = getRGB(x, y+1);
      int blueNeighbor = blueVal(rgbNeighbor);
      int greenNeighbor = greenVal(rgbNeighbor);
      int redNeighbor = redVal(rgbNeighbor);
      blueDiff += Math.abs(blueNeighbor - blueVal);
      greenDiff += Math.abs(greenNeighbor - greenVal);
      redDiff += Math.abs(redNeighbor - redVal);
    }
    if(x > 0 && y > 0) {
      neighbors++;
      int rgbNeighbor = getRGB(x-1, y-1);
      int blueNeighbor = blueVal(rgbNeighbor);
      int greenNeighbor = greenVal(rgbNeighbor);
      int redNeighbor = redVal(rgbNeighbor);
      blueDiff += Math.abs(blueNeighbor - blueVal);
      greenDiff += Math.abs(greenNeighbor - greenVal);
      redDiff += Math.abs(redNeighbor - redVal);
    }
    if(x > 0 && y + 1 < height) {
      neighbors++;
      int rgbNeighbor = getRGB(x-1, y+1);
      int blueNeighbor = blueVal(rgbNeighbor);
      int greenNeighbor = greenVal(rgbNeighbor);
      int redNeighbor = redVal(rgbNeighbor);
      blueDiff += Math.abs(blueNeighbor - blueVal);
      greenDiff += Math.abs(greenNeighbor - greenVal);
      redDiff += Math.abs(redNeighbor - redVal);
    }
    if(x + 1 < width && y > 0) {
      neighbors++;
      int rgbNeighbor = getRGB(x+1, y-1);
      int blueNeighbor = blueVal(rgbNeighbor);
      int greenNeighbor = greenVal(rgbNeighbor);
      int redNeighbor = redVal(rgbNeighbor);
      blueDiff += Math.abs(blueNeighbor - blueVal);
      greenDiff += Math.abs(greenNeighbor - greenVal);
      redDiff += Math.abs(redNeighbor - redVal);
    }
    if(x + 1 < width && y + 1 < height) {
      neighbors++;
      int rgbNeighbor = getRGB(x+1, y+1);
      int blueNeighbor = blueVal(rgbNeighbor);
      int greenNeighbor = greenVal(rgbNeighbor);
      int redNeighbor = redVal(rgbNeighbor);
      blueDiff += Math.abs(blueNeighbor - blueVal);
      greenDiff += Math.abs(greenNeighbor - greenVal);
      redDiff += Math.abs(redNeighbor - redVal);
    }

    blueDiff /= neighbors;
    grayscaleDifferences[0][index] = fullAlpha | (blueDiff + (blueDiff<<8) + (blueDiff<<16));
    blueDiff = fullAlpha | blueDiff;

    greenDiff /= neighbors;
    grayscaleDifferences[1][index] = fullAlpha | (greenDiff + (greenDiff<<8) + (greenDiff<<16));
    greenDiff <<= 8;
    greenDiff = fullAlpha | greenDiff;

    redDiff /= neighbors;
    grayscaleDifferences[2][index] = fullAlpha | (redDiff + (redDiff<<8) + (redDiff<<16));
    redDiff <<= 16;
    redDiff = fullAlpha | redDiff;

    colorDifferences[0][index] = blueDiff;
    colorDifferences[1][index] = greenDiff;
    colorDifferences[2][index] = redDiff;
  }

  private int getRGB(int x, int y) {
    return imageArray[y*width + x];
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

}
