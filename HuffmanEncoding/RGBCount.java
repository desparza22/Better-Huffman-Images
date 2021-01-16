
public class RGBCount {
  private int[] redCounts = new int[256];
  private int[] greenCounts = new int[256];
  private int[] blueCounts = new int[256];
  private int[][] colorCounts = new int[][] {blueCounts, greenCounts, redCounts};
  private long[] colorSums = new long[3];
  private int size = 0;

  public RGBCount() {
  }

  public void add(int rgb) {
    int redVal = redVal(rgb);
    int greenVal = greenVal(rgb);
    int blueVal = blueVal(rgb);
    colorSums[2] += redVal;
    colorSums[1] += greenVal;
    colorSums[0] += blueVal;
    redCounts[redVal(rgb)]++;
    greenCounts[greenVal(rgb)]++;
    blueCounts[blueVal(rgb)]++;
    size++;
  }

  public int sumOfColorStrenghts(int color) {
    return colorSums[color];
  }

  public int pixelsInGroup() {
    return size;
  }

  public int getColor(int color, int index) {
    return colorCounts[color][index];
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
}
