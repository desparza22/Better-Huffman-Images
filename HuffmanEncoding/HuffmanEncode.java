import java.lang.Math;
import java.util.PriorityQueue;
import java.util.Iterator;

public class HuffmanEncode {
  private int numLetters;
  private int[] groupCounts;
  // private BinaryEncoding[] letterEncodings;
  private PriorityQueue<HuffmanGroup> treeNodes;
  private int bitsRequired;
  private HuffmanGroup treeRoot;

  public HuffmanEncode(int[] letterCounts) {
    this.numLetters = letterCounts.length;
    this.letterCounts = letterCounts;
    fillWithDefault();
    this.bitsRequired = encode();
  }

  public int bitsRequired() {
    return this.bitsRequired;
  }

  private void fillWithDefault() {
    // letterEncodings = new BinaryEncoding[numLetters];
    treeNodes = new PriorityQueue<HuffmanGroup>(numLetters, (HuffmanGroup a, HuffmanGroup b) -> a.frequency() - b.frequency());
    for(int i = 0; i < numLetters; i++) {
      // letterEncodings[i] = new BinaryEncoding();
      treeNodes.offer(new HuffmanGroup(i, letterCounts[i]));
    }
  }

  private int encode() {
    int bitsRequired = 0;
    while(thereIsEncodingLeftToDo()) {
      HuffmanGroup leastGroup = treeNodes.poll();
      HuffmanGroup secondLeastGroup = treeNodes.poll();
      // splitTree(leastGroup, secondLeastGroup);
      HuffmanGroup mergerOfNodes = HuffmanGroup.merge(leastGroup, secondLeastGroup);
      bitsRequired += mergerOfNodes.frequency();
      treeNodes.offer(mergerOfNodes);
    }
    this.treeRoot = treeNodes.poll();
    return bitsRequired;
  }

  private boolean thereIsEncodingLeftToDo() {
    return treeNodes.size() > 1;
  }

  // private void splitTree(HuffmanGroup left, HuffmanGroup right) {
  //   Iterator<Integer> leftLeaves = left.iterator();
  //   Iterator<Integer> rightLeaves = right.iterator();
  //   while(leftLeaves.hasNext()) {
  //     leftLeaf = leftLeaves.next();
  //     letterEncodings[leftLeaf].addFirst(false);
  //   }
  //   while(rightLeaves.hasNext()) {
  //     rightLeaf = rightLeaves.next();
  //     letterEncodings[rightLeaf].addFirst(true);
  //   }
  // }
}
