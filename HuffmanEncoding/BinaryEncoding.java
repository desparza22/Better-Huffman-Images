import java.util.LinkedList;
public class BinaryEncoding {
  private LinkedBit head = new LinkedBit();
  private LinkedBit tail = new LinkedBit();
  private int size = 0;

  public BinaryEncoding() {
    head.next = tail;
    tail.previous = head;
  }

  // public void set(int index, boolean value) {
  //   while(bits.size() <= index) {
  //     bits.add(false);
  //   }
  //   bits.add(value);
  // }

  // public boolean get(int index) {
  //   return bits.size() > index && bits.get(index);
  // }

  public static BinaryEncoding link(BinaryEncoding left, BinaryEncoding right) {
    LinkedBit leftSecondToLast = left.tail.previous;
    LinkedBit rightSecond = right.head.next;
    leftSecondToLast.next = rightSecond;
    rightSecond.previous = leftSecondToLast;
    left.increaseSizeBy(right.size());
    return left;
  }

  public void add(boolean value) {
    increaseSizeBy(1);
    LinkedBit secondToLast = tail.previous;
    LinkedBit inserting = new LinkedBit(value);
    secondToLast.next = inserting;
    inserting.next = tail;
    inserting.previous = secondToLast;
    tail.previous = inserting;
  }

  public void addFirst(boolean value) {
    increaseSizeBy(1);
    LinkedBit second = head.next;
    LinkedBit inserting = new LinkedBit(value);
    head.next = inserting;
    inserting.next = second;
    second.previous = inserting;
    inserting.previous = head;
  }

  public int size() {
    return size;
  }

  private void increaseSizeBy(int numberOfBitsToAdd) {
    size += numberOfBitsToAdd;
  }
}
