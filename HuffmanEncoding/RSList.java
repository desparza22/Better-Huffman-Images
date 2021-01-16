import java.util.Random;
public class RSList {

  public RandomStart head = new RandomStart(-1, -1);
  public RandomStart tail = new RandomStart(-1, -1);
  RandomStart lastAdded = head;
  RandomStart[] starts;
  public int size = 0;

  int width;
  int height;

  public RSList(int width, int height) {
    starts = new RandomStart[width*height];
    this.width = width;
    this.height = height;
    addAll();
    shuffle();
  }

  public RandomStart getFirst() {
    RandomStart toReturn = head.next;
    remove(toReturn);
    return toReturn;
  }

  private void addAll() {
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        add(new RandomStart(i, j));
      }
    }
  }

  private void add(RandomStart adding) {
    starts[size++] = adding;
    lastAdded.next = adding;
    adding.prev = lastAdded;
    lastAdded = adding;
    if(size == starts.length) {
      finishAdding();
    }
  }

  private void finishAdding() {
    lastAdded.next = tail;
    tail.prev = lastAdded;
  }

  private void shuffle() {
    Random r = new Random();
    for(int i = 3; i < starts.length; i++) {
      RandomStart switchWith = starts[r.nextInt(i-2)];
      reorder(starts[i], switchWith);
    }
  }

  private void reorder(RandomStart switching, RandomStart other) {
    RandomStart otherPrev = other.prev;
    RandomStart otherNext = other.next;

    other.prev = switching.prev;
    other.next = switching.next;
    switching.prev.next = other;
    switching.next.prev = other;

    switching.prev = otherPrev;
    switching.next = otherNext;
    otherPrev.next = switching;
    otherNext.prev = switching;
  }

  public void remove(int x, int y) {
    int index = y * width + x;
    RandomStart removing = starts[index];
    remove(removing);
  }

  private void remove(RandomStart removing) {
    if(!removing.removed) {
      removing.prev.next = removing.next;
      removing.next.prev = removing.prev;
      removing.removed = true;
      size--;
    }
  }
}
