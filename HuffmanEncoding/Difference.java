
public class Difference {
  private int difference;
  private Pixel pixel1;
  private Pixel pixel2;

  Difference[][] prevNext;
  boolean[] removed;

  public Difference(int difference, Pixel pixel1, Pixel pixel2) {
    this.difference = difference;
    this.pixel1 = pixel1;
    this.pixel2 = pixel2;
    this.prevNext = new Difference[3][2]; //overall, pixel1, pixel2
    this.removed = new boolean[3];
  }

  public Difference() {
    
  }

  public void remove(int whichList) {
    if(!removed[whichList]) {
      removed[whichList] = true;
      Difference previous = prevNext[whichList][0];
      Difference next = prevNext[whichList][1];
      previous.connect(next, whichList);
    }
  }

  public void connect(Difference next, int whichList) {
    this.prevNext[whichList][1] = next;
    next.prevNext[whichList][0] = this;
  }
}
