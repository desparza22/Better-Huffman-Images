import java.util.Random;

public class Pixel {
  Group group;
  public int roundAssignedColor = -1;
  public int groupRGB = -1;
  Random r;
  //^for usage in other classes^


  public int x;
  public int y;
  public int rgb;
  Pixel pointsTo;
  int groupSize = 1;
  int[][] groupMinsAndMaxes = new int[3][2];

  Difference headDifference = new Difference();
  Difference tailDifference = new Difference();

  public Pixel(int x, int y, int rgb) {
    this.x = x;
    this.y = y;
    this.rgb = rgb;
    this.pointsTo = this;
  }

  public void reposition() {
    this.group.reposition(this);
  }

  public void union(Pixel other) {
    Pixel thisHead = this.findHead();
    Pixel otherHead = other.findHead();
    if(thisHead != otherHead) {
      int bigGroupSize = thisHead.groupSize + otherHead.groupSize;
      if(thisHead.groupSize >= otherHead.groupSize) {
        otherHead.pointsTo = thisHead;
        thisHead.groupSize = bigGroupSize;
      } else {
        thisHead.pointsTo = otherHead;
        otherHead.groupSize = bigGroupSize;
      }
    }
  }

  public Pixel findHead() {
    if(this == this.pointsTo) {
      return this;
    } else {
      Pixel head = this.pointsTo.findHead();
      this.pointsTo = head;
      return head;
    }
  }

  public void randomRGB(int round) {
    roundAssignedColor = round;
    if(r == null) {
      r = new Random();
    }
    int blue = r.nextInt(100) + 155;
    int green = r.nextInt(100) + 155;
    int red = r.nextInt(100) + 155;
    groupRGB = Practice.ARGBVal(blue, green, red, 0);
  }
}
