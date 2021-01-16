import java.util.List;

public class PickGrid {
  private PickGrid parent;
  private boolean marked = false;

  private PickGrid[] children = new PickGrid[4]; //topLeft, topRight, bottomLeft, bottomRight

  private int centerX;
  private int centerY;

  private PickGrid(int startX, int startY, int width, int height, PickGrid parent, PickGrid[][] picksToFill) {
    this.parent = parent;
    this.centerX = startX + width/2;
    this.centerY = startY + height/2;
    if(width == 1 && height == 1) {
      picksToFill[startX][startY] = this;
    } else if(width < 1 || height < 1) {
      marked = true;
    } else {
      int leftWidth = centerX - startX;
      int topHeight = centerY - startY;
      children[0] = new PickGrid(startX, startY, leftWidth, topHeight, this, picksToFill);
      children[1] = new PickGrid(centerX, startY, width-leftWidth, topHeight, this, picksToFill);
      children[2] = new PickGrid(startX, centerY, leftWidth, height-topHeight, this, picksToFill);
      children[3] = new PickGrid(centerX, centerY, width-leftWidth, height-topHeight, this, picksToFill);
    }
  }

  private PickGrid() {
    this.marked = true;
  }

  public static PickGrid initializeFirst(int width, int height, PickGrid[][] picksToFill) {
    PickGrid bigParent = new PickGrid();
    PickGrid firstToPickFrom = new PickGrid(0, 0, width, height, bigParent, picksToFill);
    return firstToPickFrom;
  }

  public boolean isMarked() {
    return this.marked;
  }

  public void markUpwards() {
    this.marked = true;
    if(!this.parent.isMarked()) {
      this.parent.markUpwards();
    }
  }

  public void breakUp(List<PickGrid> pickOrder) {
    if(children[0] != null) {
      for(PickGrid child : children) {
        pickOrder.add(child);
      }
    }
  }

  public void getCoordinates(int[] coordinates) {
    coordinates[0] = centerX;
    coordinates[1] = centerY;
  }
}
