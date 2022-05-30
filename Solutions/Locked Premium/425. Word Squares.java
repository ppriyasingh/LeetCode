/*
Given an array of unique strings words, return all the word squares you can build from words. The same word from words can be used multiple times. 
You can return the answer in any order.

A sequence of strings forms a valid word square if the kth row and column read the same string, where 0 <= k < max(numRows, numColumns).

For example, the word sequence ["ball","area","lead","lady"] forms a word square because each word reads the same both horizontally and vertically.
 

Example 1:

Input: words = ["area","lead","wall","lady","ball"]
Output: [["ball","area","lead","lady"],["wall","area","lead","lady"]]
Explanation:
The output consists of two word squares. The order of output does not matter (just the order of words in each word square matters).
Example 2:

Input: words = ["abat","baba","atan","atal"]
Output: [["baba","abat","baba","atal"],["baba","abat","baba","atan"]]
Explanation:
The output consists of two word squares. The order of output does not matter (just the order of words in each word square matters).

*/

/*
Approach 1: Backtracking with HashTable
Intuition

As one might notice in the above backtracking algorithm, the bottleneck lies in the function getWordsWithPrefix() which is to find all words with the 
given prefix. At each invocation of the function, we were iterating through the entire input list of words, which is of linear time complexity O(N).

One of the ideas to optimize the getWordsWithPrefix() function would be to process the words beforehand and to build a data structure that could speed up 
the lookup procedure later.

As one might recall, one of the data structures that provide a fast lookup operation is called hashtable or dictionary. We could simply build a hashtable 
with all possible prefixes as keys and the words that are associated with the prefix as the values in the table. Later, given the prefix, we should be 
able to list all the words with the given prefix in constant time O(1).

Algorithm

We build upon the backtracking algorithm that we listed above, and tweak two parts.
In the first part, we add a new function buildPrefixHashTable(words) to build a hashtable out of the input words.
Then in the second part, in the function getWordsWithPrefix() we simply query the hashtable to retrieve all the words that possess the given prefix.
  
 */

class Solution {
  // T(O): O(N * 26^L)
  // S(O): O(N * L)
  
  int N = 0;
  String[] words = null;
  HashMap<String, List<String>> prefixHashTable = null;

  public List<List<String>> wordSquares(String[] words) {
    this.words = words;
    this.N = words[0].length();

    List<List<String>> results = new ArrayList<List<String>>();
    this.buildPrefixHashTable(words);

    for (String word : words) {
      LinkedList<String> wordSquares = new LinkedList<String>();
      wordSquares.addLast(word);
      this.backtracking(1, wordSquares, results);
    }
    return results;
  }

  protected void backtracking(int step, LinkedList<String> wordSquares,
                              List<List<String>> results) {
    if (step == N) {
      results.add((List<String>) wordSquares.clone());
      return;
    }

    StringBuilder prefix = new StringBuilder();
    for (String word : wordSquares) {
      prefix.append(word.charAt(step));
    }

    for (String candidate : this.getWordsWithPrefix(prefix.toString())) {
      wordSquares.addLast(candidate);
      this.backtracking(step + 1, wordSquares, results);
      wordSquares.removeLast();
    }
  }

  protected void buildPrefixHashTable(String[] words) {
    this.prefixHashTable = new HashMap<String, List<String>>();

    for (String word : words) {
      for (int i = 1; i < this.N; ++i) {
        String prefix = word.substring(0, i);
        List<String> wordList = this.prefixHashTable.get(prefix);
        if (wordList == null) {
          wordList = new ArrayList<String>();
          wordList.add(word);
          this.prefixHashTable.put(prefix, wordList);
        } else {
          wordList.add(word);
        }
      }
    }
  }

  protected List<String> getWordsWithPrefix(String prefix) {
    List<String> wordList = this.prefixHashTable.get(prefix);
    return (wordList != null ? wordList : new ArrayList<String>());
  }
}

// My Solution
class Solution {
  // T(O): O(N * 26^L)
  // S(O): O(N * L)
    Map<String, List<String>> dic= new HashMap();
    
    public void formDictionary(String[] words) {
        for(String word: words) {
            for(int i=0; i<word.length(); i++) {
                String key= word.substring(0,i);
                List<String> list = dic.getOrDefault(key, new ArrayList<>());
                list.add(word);
                dic.put(key, list);
            }
        }
    }
    public void backTrack(int ind, LinkedList<String> subResult, List<List<String>> result, int n) {
        if(subResult.size() == n) {
            result.add(new LinkedList<>(subResult));
            return;
        }
        StringBuilder sb = new StringBuilder();
        for(String word: subResult) {
            sb.append(word.charAt(ind));
        }
        String substr = sb.toString();
        List<String> potentialNextWords = dic.getOrDefault(substr, new ArrayList<>());
        
        for(String word: potentialNextWords) {
            subResult.add(word);
            backTrack(ind+1, subResult, result, word.length());
            subResult.removeLast();
        }
    }
    public List<List<String>> wordSquares(String[] words) {
        
        List<List<String>> result = new ArrayList<>();
        formDictionary(words);
        
        for(int i=0; i<words.length; i++) {
            LinkedList<String> subResult = new LinkedList<>();
            subResult.add(words[i]);
            backTrack(1, subResult, result, words[i].length());
        }
        return result;
    }
    
}


/*
// Approach 2: Backtracking with Trie
Intuition

Speaking about prefix, there is another data structure called Trie (also known as prefix tree), which could find its use in this problem.

In the above approach, we have reduce the time complexity of retrieving a list of words with a given prefix from the linear O(N) to the constant time O(1).
In exchange, we have to spend some extra space to store all the prefixes of each words.
The Trie data structure provides a compact and yet still fast way to retrieve words with a given prefix.
In the following graph, we show an example on how we can build a Trie from a list of words.
*/
class TrieNode {
  HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
  List<Integer> wordList = new ArrayList<Integer>();

  public TrieNode() {}
}


class Solution {
  // T(O): O(N * 26^L * L)
  // S(O): O(N * L)
  int N = 0;
  String[] words = null;
  TrieNode trie = null;

  public List<List<String>> wordSquares(String[] words) {
    this.words = words;
    this.N = words[0].length();

    List<List<String>> results = new ArrayList<List<String>>();
    this.buildTrie(words);

    for (String word : words) {
      LinkedList<String> wordSquares = new LinkedList<String>();
      wordSquares.addLast(word);
      this.backtracking(1, wordSquares, results);
    }
    return results;
  }

  protected void backtracking(int step, LinkedList<String> wordSquares,
                              List<List<String>> results) {
    if (step == N) {
      results.add((List<String>) wordSquares.clone());
      return;
    }

    StringBuilder prefix = new StringBuilder();
    for (String word : wordSquares) {
      prefix.append(word.charAt(step));
    }

    for (Integer wordIndex : this.getWordsWithPrefix(prefix.toString())) {
      wordSquares.addLast(this.words[wordIndex]);
      this.backtracking(step + 1, wordSquares, results);
      wordSquares.removeLast();
    }
  }

  protected void buildTrie(String[] words) {
    this.trie = new TrieNode();

    for (int wordIndex = 0; wordIndex < words.length; ++wordIndex) {
      String word = words[wordIndex];

      TrieNode node = this.trie;
      for (Character letter : word.toCharArray()) {
        if (node.children.containsKey(letter)) {
          node = node.children.get(letter);
        } else {
          TrieNode newNode = new TrieNode();
          node.children.put(letter, newNode);
          node = newNode;
        }
        node.wordList.add(wordIndex);
      }
    }
  }

  protected List<Integer> getWordsWithPrefix(String prefix) {
    TrieNode node = this.trie;
    for (Character letter : prefix.toCharArray()) {
      if (node.children.containsKey(letter)) {
        node = node.children.get(letter);
      } else {
        // return an empty list.
        return new ArrayList<Integer>();
      }
    }
    return node.wordList;
  }
}
