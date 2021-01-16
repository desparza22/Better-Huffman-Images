
public class RandomStart {
  int x;
  int y;

  RandomStart prev;
  RandomStart next;

  boolean removed = false;

  public RandomStart(int x, int y) {
    this.x = x;
    this.y = y;
  }
}
