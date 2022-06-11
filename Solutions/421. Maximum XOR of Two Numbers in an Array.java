// Approach 1: Bitwise Prefixes in HashSet
class Solution {

  // T(O): O(N)
  // S(O): O(1)
  
  public int findMaximumXOR(int[] nums) {
    int maxNum = nums[0];
    for(int num : nums) maxNum = Math.max(maxNum, num);
    // length of max number in a binary representation
    int L = (Integer.toBinaryString(maxNum)).length();

    int maxXor = 0, currXor;
    Set<Integer> prefixes = new HashSet<>();
    for(int i = L - 1; i > -1; --i) {
      // go to the next bit by the left shift
      maxXor <<= 1;
      // set 1 in the smallest bit
      currXor = maxXor | 1;
      prefixes.clear();
      // compute all possible prefixes 
      // of length (L - i) in binary representation
      for(int num: nums) prefixes.add(num >> i);
      // Update maxXor, if two of these prefixes could result in currXor.
      // Check if p1^p2 == currXor, i.e. p1 == currXor^p2.
      for(int p: prefixes) {
        if (prefixes.contains(currXor^p)) {
          maxXor = currXor;
          break;
        }
      }
    }
    return maxXor;
  }
}

// Approach 2: Bitwise Trie
class TrieNode {
  HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
  public TrieNode() {}
}

class Solution {
  // T(O): O(N)
  // S(O): O(1)
  public int findMaximumXOR(int[] nums) {
    // Compute length L of max number in a binary representation
    int maxNum = nums[0];
    for(int num : nums) maxNum = Math.max(maxNum, num);
    int L = (Integer.toBinaryString(maxNum)).length();

    // zero left-padding to ensure L bits for each number
    int n = nums.length, bitmask = 1 << L;
    String [] strNums = new String[n];
    for(int i = 0; i < n; ++i) {
      strNums[i] = Integer.toBinaryString(bitmask | nums[i]).substring(1);
    }

    TrieNode trie = new TrieNode();
    int maxXor = 0;
    for (String num : strNums) {
      TrieNode node = trie, xorNode = trie;
      int currXor = 0;
      for (Character bit : num.toCharArray()) {
        // insert new number in trie  
        if (node.children.containsKey(bit)) {
          node = node.children.get(bit);
        } else {
          TrieNode newNode = new TrieNode();
          node.children.put(bit, newNode);
          node = newNode;
        }

        // compute max xor of that new number 
        // with all previously inserted
        Character toggledBit = bit == '1' ? '0' : '1';
        if (xorNode.children.containsKey(toggledBit)) {
          currXor = (currXor << 1) | 1;
          xorNode = xorNode.children.get(toggledBit);
        } else {
          currXor = currXor << 1;
          xorNode = xorNode.children.get(bit);
        }
      }
      maxXor = Math.max(maxXor, currXor);
    }

    return maxXor;
  }
}
