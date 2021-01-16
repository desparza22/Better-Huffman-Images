
public class HuffmanGroup {
  private int leafNumber;
  private int frequency;
  private int numLeaves;
  private BinaryEncoding encodingForLeaf;
  private HuffmanGroup leftChildForNode;
  private HuffmanGroup rightChildForNode;
  private boolean isLeaf;

  public HuffmanGroup(int leafNumber, int leafFrequency) {
    this.leafNumber = leafNumber;
    this.frequency = leafFrequency;
    this.encodingForLeaf = new BinaryEncoding();
    this.isLeaf = true;
    this.numLeaves = 1;
  }

  public HuffmanGroup(HuffmanGroup leftChild, HuffmanGroup rightChild) {
    this.leftChildForNode = leftChild;
    this.rightChildForNode = rightChild;
    this.isLeaf = false;
    this.frequency = leftChild.frequency() + rightChild.frequency();
    this.numLeaves = leftChild.numLeaves() + rightChild.numLeaves();
  }

  public int frequency() {
    return frequency;
  }

  public int numLeaves() {
    return numLeaves;
  }

  public static HuffmanGroup merge(HuffmanGroup a, HuffmanGroup b) {
    a.addToFrontOfEncoding(false);
    b.addToFrontOfEncoding(true);
    return new HuffmanGroup(a, b);
  }

  private void addToFrontOfEncoding(boolean bit) {
    if(this.isLeaf) {
      this.encodingForLeaf.addFirst(bit);
    } else {
      this.leftChildForNode.addToFrontOfEncoding(bit);
      this.rightChildForNode.addToFrontOfEncoding(bit);
    }
  }

  public BinaryEncoding toBinaryEncoding(FenwickTree fw) {
    if(this.isLeaf) {
      return binaryEncodingForLeaf(fw);
    } else {
      return binaryEncodingForNode(fw);
    }
  }

  private BinaryEncoding binaryEncodingForLeaf(FenwickTree fw) {
    boolean signifyLeafNode = true;
    BinaryEncoding encodeThisLeaf = new BinaryEncoding();
    int remainingValues = fw.spotsUnmarked();
    int remainingValuesLessThanLeaf = leafNumber - fw.valuesMarkedBelow(leafNumber); //ex, leaf number of 2 would default to 2 values below it (0 and 1)
    encodeThisLeaf.add(signifyLeafNode);
    encodeThisLeaf.addNumberWithCap(remainingValuesLessThanLeaf, remainingValues);
  }

  private BinaryEncoding binaryEncodingForNode(FenwickTree fw) {
    BinaryEncoding encodeLeftChild = leftChildForNode.toBinaryEncoding();
    boolean signifyParentNode = false;
    encodeLeftChild.addFirst(signifyParentNode);
    BinaryEncoding encodeRightChild = rightChildForNode.toBinaryEncoding();
    return BinaryEncoding.link(encodeLeftChild, encodeRightChild);
  }
}
