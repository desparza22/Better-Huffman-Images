public class FenwickTree {
  private int[] fenwickTree;
  private int size;
  private int spotsUnmarked;

  public FenwickTree(int size) {
    this.size = size;
    this.fenwickTree = new int[size+1];
    this.spotsUnmarked = size;
  }

  public int size() {
    return size;
  }

  public int spotsUnmarked() {
    return spotsUnmarked;
  }

  public int valuesMarkedBelow(int value) {
    value++;
    int valuesBelow = 0;
    while(value != 0) {
      valuesBelow += numberAt(value);
      value = flipLastBit(value);
    }
    return valuesBelow;
  }

  public void markValue(int value) {
    value++;
    spotsUnmarked--;
    while(value <= size) {
      incrementAt(value);
      value += leastSignificantBit(value);
    }
  }

  private void incrementAt(int value) {
    fenwickTree[value]++;
  }

  private int numberAt(int value) {
    return fenwickTree[value];
  }

  private int leastSignificantBit(int value) {
    return value - flipLastBit(value);
  }

  private int flipLastBit(int value) {
    return value & (value-1);
  }
}
