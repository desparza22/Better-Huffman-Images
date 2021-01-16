
public class Separator {
  private int[] imageArray;
  private int[][] colorImageArrays;
  private int fullAlpha = 255<<24;

  public Separator(int[] imageArray) {
    this.imageArray = imageArray;
    fillArrays();
    System.out.println(fullAlpha);
  }

  private void fillArrays() {
    int numColors = 3;
    colorImageArrays = new int[numColors][imageArray.length];
    for(int i = 0; i < imageArray.length; i++) {
      int rgb = imageArray[i];
      colorImageArrays[0][i] = extractBlue(rgb);
      colorImageArrays[1][i] = extractGreen(rgb);
      colorImageArrays[2][i] = extractRed(rgb);
    }
  }

  public int[] getColor(int color) {
    return colorImageArrays[color];
  }

  public int[][] getColors() {
    return colorImageArrays;
  }

  private int extractBlue(int ARGBVal) {
    return fullAlpha | ((ARGBVal)&255);
  }
  private int extractGreen(int ARGBVal) {
    return fullAlpha | ((ARGBVal)&(255<<8));
  }
  private int extractRed(int ARGBVal) {
    return fullAlpha | ((ARGBVal)&(255<<16));
  }
}
