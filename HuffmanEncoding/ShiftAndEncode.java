
public class ShiftAndEncode {
  private int[] imageArray;
  private int width;
  private Grouper grouper;
  private int[] groupArray;
  private int numGroups;
  private RGBCount[] counts;
  private int[][] colorCountsPostShift;

  private HuffmanEncode[] encodings;

  public ShiftAndEncode(int[] imageArray, int width, int groupingMethod, int[] groupingParameters) {
    this.imageArray = imageArray;
    this.width = width;
    this.grouper = assignGroups(groupingMethod, groupingParameters);
    this.groupArray = grouper.getGroups();
    this.numGroups = grouper.numGroups();
    this.counts = calcRGBCounts();
    this.colorCountsPostShift = calculateColorCounts();
    this.encodings = encodeColorCounts();
  }

  private HuffmanEncode[] encodeColorCounts() {
    HuffmanEncode[] encodings = new HuffmanEncode[colorCountsPostShift.length];
    for(int i = 0; i < encodings.length; i++) {
      encodings[i] = new HuffmanEncode(colorCountsPostShift[i]);
    }
    return encodings;
  }

  private int[][] calculateColorCounts() {
    int numColors = 3;
    int strengthsOfColors = 266;
    int[][] colorCountsPostShift = new int[numColors][strengthsOfColors];
    for(int i = 0; i < counts.length; i++) {
      shiftGroupAndUpdateCounts(counts[i], colorCountsPostShift);
    }
    return colorCountsPostShift;
  }

  private void shiftGroupAndUpdateCounts(RGBCount count, int[][] colorCountsPostShift) {
    int pixelsInGroup = count.pixelsInGroup();
    int maxColorStrength = 255;
    for(int color = 0; color < colorCountsPostShift.length; color++) {
      int shift = count.sumOfColorStrengths(color) / pixelsInGroup;
      int loopingPoint = maxColorStrength - shift + 1;
      for(int i = shift; i <= maxColorStrength; i++) {
        colorCountsPostShift[color][i-shift] += count.getColor(color, i);
      }
      for(int i = 0; i < shift; i++) {
        colorCountsPostShift[color][i+loopingPoint] += count.getColor(color, i);
      }
    }
  }

  private RGBCount[] calcRGBCounts() {
    RGBCount[] counts = new RGBCount[numGroups];
    for(int i = 0; i < counts.length; i++) {
      counts[i] = new RGBCount();
    }
    for(int i = 0; i < groupArray.length; i++) {
      int group = groupArray[i];
      counts[group-1].add(imageArray[i]);
    }
    return counts;
  }

  private Grouper assignGroups(int groupingMethod) {
    switch(groupingMethod) {
      case 1:
        return new BFGrouper(this.imageArray, this.width, this.groupingParameters[0]);
      default:
        System.out.println("Invalid grouping method passed to ShiftAndEncode");
        return null;
    }
  }
}
