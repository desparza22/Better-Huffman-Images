import java.util.*;

public class Group {
  private static int groupNumber = 0;
  public int number;

  public LinkedList<Pixel> pixels;

  public Group() {
    this.number = groupNumber++;
    pixels = new LinkedList<Pixel>();
  }

  public void reposition(Pixel toMove) {
    pixels.add(toMove);
  }

  public void union() {
    Pixel main = pixels.removeFirst();
    while(pixels.size() != 0) {
      main.union(pixels.removeFirst());
    }
  }

  public void split(int color, LinkedList<Group> groups, int difference) {
    Group assigning = this;
    groups.add(this);
    Pixel lastPixel = pixels.removeFirst();
    while(pixels.size() != 0) {
      Pixel next = pixels.removeFirst();
      int differenceCompare = difference(color, lastPixel, next);
      if(differenceCompare > difference) {
        assigning = new Group();
        groups.add(assigning);
      }
      next.group = assigning;
      lastPixel = next;
    }
  }

  private int difference(int color, Pixel left, Pixel right) {
    if(color == 0) {
      int leftVal = Practice.blueVal(left.rgb);
      int rightVal = Practice.blueVal(right.rgb);
      return rightVal - leftVal;
    } else if(color == 1) {
      int leftVal = Practice.greenVal(left.rgb);
      int rightVal = Practice.greenVal(right.rgb);
      return rightVal - leftVal;
    } else {
      int leftVal = Practice.redVal(left.rgb);
      int rightVal = Practice.redVal(right.rgb);
      return rightVal - leftVal;
    }
  }
}
