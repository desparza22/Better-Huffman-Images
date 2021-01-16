import java.util.LinkedList;
import java.util.Random;

public class BFGrouper extends Grouper {
  private int[] imageArray;
  private int[] groupArray;
  private int width;
  private int height;

  private int[] directions = new int[] {-1, 0, 1};

  private PickDistributer whereToPickFrom;

  private int maxRange;

  private int groups;

  public BFGrouper(int[] imageArray, int width, int maxRange) {
    this.imageArray = imageArray;
    this.groupArray = new int[imageArray.length];
    this.width = width;
    this.height = imageArray.length/width;
    this.whereToPickFrom = new PickDistributer(width, height);
    this.maxRange = maxRange;
    group();
  }

  public int[] getGroups() {
    return groupArray;
  }

  public int numGroups() {
    return groups;
  }

  private void group() {
    int[] pickCoordinates = new int[2];
    int[][] minMaxColors = new int[3][2];
    int group = 1;
    int[][] groupVisitedBy = new int[width][groupArray.length/width];
    while(whereToPickFrom.hasNext()) {
      // System.out.println("had next");
      whereToPickFrom.pick(pickCoordinates);
      int firstX = pickCoordinates[0];
      int firstY = pickCoordinates[1];
      int rgb = getRGB(firstX, firstY);
      int[] colors = new int[] {blueVal(rgb), greenVal(rgb), redVal(rgb)};
      assignGroup(firstX, firstY, colors, group++, minMaxColors, groupVisitedBy);
      // resetMinMax(minMaxColors);
    }
    // System.out.println(group);
    this.groups = group-1;
  }

  private void resetMinMax(int[][] minMaxColors) {
    for(int i = 0; i < minMaxColors.length; i++) {
      minMaxColors[i][0] = Integer.MAX_VALUE;
      minMaxColors[i][1] = Integer.MIN_VALUE;
    }
  }

  private void assignGroup(int x, int y, int[] colors, int group, int[][] minMaxColors, int[][] groupVisitedBy) {
    setGroup(x, y, group);
    setMinMax(colors, minMaxColors);
    groupVisitedBy[x][y] = group;
    // updateMinMax(colors, minMaxColors);

    LinkedList<Integer> coordinateRecursion = new LinkedList<Integer>();
    coordinateRecursion.add(x);
    coordinateRecursion.add(y);
    while(coordinateRecursion.size() != 0) {
      int visitX = coordinateRecursion.removeFirst();
      int visitY = coordinateRecursion.removeFirst();
      whereToPickFrom.markCoordinate(visitX, visitY);
      for(int i = 0; i < directions.length; i++) {
        int neighborX = visitX + directions[i];
        if(neighborX >= 0 && neighborX < width) {
          for(int j = 0; j < directions.length; j++) {
            int neighborY = visitY + directions[j];
            if(neighborY >= 0 && neighborY < height && getGroup(neighborX, neighborY) == 0 && groupVisitedBy[neighborX][neighborY] != group) {
              groupVisitedBy[neighborX][neighborY] = group;
              int rgb = getRGB(neighborX, neighborY);
              int[] nextColors = new int[] {blueVal(rgb), greenVal(rgb), redVal(rgb)};
              if(determineWithinRange(minMaxColors, nextColors)) {
                updateMinMax(colors, minMaxColors);
                setGroup(neighborX, neighborY, group);
                coordinateRecursion.add(neighborX);
                coordinateRecursion.add(neighborY);
              }
            }
          }
        }
      }
    }


  }

  private void setMinMax(int[] colors, int[][] minMaxColors) {
    for(int i = 0; i < colors.length; i++) {
      minMaxColors[i][0] = colors[i];
      minMaxColors[i][1] = colors[i];
    }
  }

  private boolean determineWithinRange(int[][] minMaxColors, int[] colors) {
    boolean withinRange = true;
    for(int i = 0; i < colors.length && withinRange; i++) {
      withinRange = determineWithinRange(colors[i], minMaxColors[i]);
    }
    return withinRange;
  }

  private boolean determineWithinRange(int color, int[] minMax) {
    return (color - minMax[0] <= maxRange) && (minMax[1] - color <= maxRange);
  }

  private void updateMinMax(int[] colors, int[][] minMaxColors) {
    for(int i = 0; i < colors.length; i++) {
      int color = colors[i];
      int[] minMax = minMaxColors[i];
      if(color > minMax[1]) {
        minMax[1] = color;
      }
      if(color < minMax[0]) {
        minMax[0] = color;
      }
    }
  }

  private void setGroup(int x, int y, int group) {
    groupArray[y*width + x] = group;
  }

  private int getGroup(int x, int y) {
    return groupArray[y*width + x];
  }

  private int getRGB(int x, int y) {
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
}
