import java.util.LinkedList;

public class PickDistributer {
  PickGrid[][] picks;
  LinkedList<PickGrid> pickOrder = new LinkedList<PickGrid>();

  public PickDistributer(int width, int height) {
    picks = new PickGrid[width][height];
    PickGrid firstPick = PickGrid.initializeFirst(width, height, picks);
    pickOrder.add(firstPick);
  }

  public boolean hasNext() {
    while(pickOrder.size() != 0) {
      PickGrid firstUp = pickOrder.peek();
      if(firstUp.isMarked()) {
        pickOrder.removeFirst();
        firstUp.breakUp(pickOrder);
      } else {
        break;
      }
    }
    return pickOrder.size() != 0;
  }

  public void pick(int[] coordinates) {
    PickGrid firstUp = pickOrder.peek();
    firstUp.getCoordinates(coordinates);
  }

  public void markCoordinate(int x, int y) {
    PickGrid basePick = picks[x][y];
    basePick.markUpwards();
  }
}
