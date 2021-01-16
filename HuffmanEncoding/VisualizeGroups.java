import java.util.Random;

public class VisualizeGroups {
  private int[] groupsToColors;
  private int[] groupImageArray;
  private int fullAlpha = 255<<24;

  public VisualizeGroups(int[] groupArray, int numGroups) {
    System.out.println(numGroups);
    groupsToColors = new int[numGroups+1];
    fillGroupsToColors(numGroups);
    fillGroupImageArray(groupArray);
  }

  private void fillGroupsToColors(int numGroups) {
    for(int i = 1; i <= numGroups; i++) {
      groupsToColors[i] = calcPortion(i, numGroups);
    }
    for(int i = 1; i <= numGroups; i++) {
      groupsToColors[i] = randomColor();
    }
  }

  private int calcPortion(int groupNumber, int numGroups) {
    return 255*groupNumber / numGroups;
  }

  private int randomColor() {
    Random r = new Random();
    int randomBlue = r.nextInt(256);
    int randomGreen = r.nextInt(256);
    int randomRed = r.nextInt(256);
    return fullAlpha | (randomRed<<16) | (randomGreen<<8) | randomBlue;
  }

  private int grayScale(int value) {
    int shift = 8 * (value%3);
    return fullAlpha | (value<<shift);
  }

  private void fillGroupImageArray(int[] groupArray) {
    groupImageArray = new int[groupArray.length];
    for(int i = 0; i < groupImageArray.length; i++) {
      groupImageArray[i] = groupsToColors[groupArray[i]];
    }
  }

  public int[] groupImageArray() {
    return this.groupImageArray;
  }
}
