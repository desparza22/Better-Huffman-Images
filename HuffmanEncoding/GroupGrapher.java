import java.util.Random;

public class GroupGrapher {
  private int[] imageArray;
  private int[] groups;
  private int numGroups;

  public GroupGrapher(int[] imageArray, int[] groups, int numGroups) {
    this.imageArray = imageArray;
    this.groups = groups;
    this.numGroups = numGroups;
  }

  public void fileNGroups(int n, String fileName) {
    System.out.println("function \"fileNGroups\" uses a random process. Not recommended for producing and analyzing results that need to be reproduced later.");
    RGBCount[] counts = fillCounts(n, false);
    ImageGraph ig = new ImageGraph(fileName);
    writeColumnHeaders(ig, n);
    fillRows(ig, counts);
    fillLastRow(ig, counts);
    ig.close();
  }

  public void fileAllGroups(String fileName) {
    RGBCount[] counts = fillCounts(numGroups, true);
    ImageGraph ig = new ImageGraph(fileName);
    writeColumnHeaders(ig, numGroups);
    fillRows(ig, counts);
    fillLastRow(ig, counts);
    ig.close();
  }

  private void writeColumnHeaders(ImageGraph ig, int groupsSelected) {
    StringBuilder sb = new StringBuilder(5*groupsSelected);
    sb.append("Groups");
    char groupLabel = 'G';
    for(int i = 0; i < groupsSelected; i++) {
      sb.append(',');
      sb.append(groupLabel);
      sb.append(i);
    }
    ig.write(sb.toString());
    ig.newLine();
  }

  private void fillRows(ImageGraph ig, RGBCount[] counts) {
    char[] colors = new char[] {'B', 'G', 'R'};
    int estimatedCharactersPerRow = 4*counts.length + 4;
    for(int color = 0; color < colors.length; color++) {
      for(int i = 0; i <= 255; i++) {
        StringBuilder rowBuilder = new StringBuilder(estimatedCharactersPerRow);
        rowBuilder.append(colors[color]);
        rowBuilder.append(i);
        fillRow(rowBuilder, color, i, counts);
        ig.write(rowBuilder.toString());
        ig.newLine();
      }
      System.out.println("Third of file written.");
    }
  }

  private void fillLastRow(ImageGraph ig, RGBCount[] counts) {
    StringBuilder pixelCountsPerGroup = new StringBuilder(6*counts.length);
    pixelCountsPerGroup.append("PixelCounts");
    for(int i = 0; i < counts.length; i++) {
      RGBCount getPixelCount = counts[i];
      pixelCountsPerGroup.append(',');
      pixelCountsPerGroup.append(getPixelCount.pixelsInGroup());
    }
    ig.write(pixelCountsPerGroup.toString());
    ig.newLine();
  }

  private void fillRow(StringBuilder rowBuilder, int color, int index, RGBCount[] counts) {
    for(int i = 0; i < counts.length; i++) {
      rowBuilder.append(',');
      RGBCount getInfoFrom = counts[i];
      int countsOfColorAtIndex = getInfoFrom.getColor(color, index);
      rowBuilder.append(countsOfColorAtIndex);
    }
  }

  private RGBCount[] fillCounts(int n, boolean fileAllGroups) {
    int[] seen = new int[numGroups+1]; //0 for unseen, -1 for not chosen, else index + 1
    RGBCount[] counts = new RGBCount[n];
    if(fileAllGroups) {
      for(int i = 0; i < counts.length; i++) {
        counts[i] = new RGBCount();
      }
    }
    Random r = new Random();
    int groupsNotPicked = 0;
    int groupsPicked = 0;
    int groupsLeftToPick = n;

    for(int i = 0; i < imageArray.length; i++) {
      int group = groups[i];
      if(fileAllGroups) {
        counts[group-1].add(imageArray[i]);
      } else {
        int seenValue = seen[group];
        if(seenValue == 0) {
          boolean pick = shouldThisGroupBePicked(groupsNotPicked, groupsPicked, groupsLeftToPick, r);
          if(pick) {
            groupsLeftToPick--;
            RGBCount representGroupCounts = new RGBCount();
            representGroupCounts.add(imageArray[i]);
            counts[groupsPicked++] = representGroupCounts;
            seen[group] = groupsPicked;
          } else {
            groupsNotPicked++;
            seen[group] = -1;
          }
        } else if(seenValue != -1) {
          RGBCount representGroupCounts = counts[seenValue-1];
          representGroupCounts.add(imageArray[i]);
        }
      }
    }
    return counts;
  }

  private boolean shouldThisGroupBePicked(int groupsNotPicked, int groupsPicked, int groupsLeftToPick, Random r) {
    int pickingFrom = numGroups - groupsNotPicked - groupsPicked;
    int randomVal = r.nextInt(pickingFrom);
    return randomVal < groupsLeftToPick;
  }
}
