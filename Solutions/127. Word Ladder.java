// Approach 1: Breadth First Search
/**
We are given a beginWord and an endWord. Let these two represent start node and end node of a graph. We have to reach from the start node to the end node 
using some intermediate nodes/words. The intermediate nodes are determined by the wordList given to us. The only condition for every step we take on this 
ladder of words is the current word should change by just one letter.
We will essentially be working with an undirected and unweighted graph with words as nodes and edges between words which differ by just one letter. The 
problem boils down to finding the shortest path from a start node to a destination node, if there exists one. Hence it can be solved using Breadth First 
Search approach.

One of the most important step here is to figure out how to find adjacent nodes i.e. words which differ by one letter. To efficiently find the neighboring
nodes for any given word we do some pre-processing on the words of the given wordList. The pre-processing involves replacing the letter of a word by a 
non-alphabet say, *.
This pre-processing helps to form generic states to represent a single letter change.

For e.g. Dog ----> D*g <---- Dig

Both Dog and Dig map to the same intermediate or generic state D*g.

The preprocessing step helps us find out the generic one letter away nodes for any word of the word list and hence making it easier and quicker to get 
the adjacent nodes. Otherwise, for every word we will have to iterate over the entire word list and find words that differ by one letter. That would take 
a lot of time. This preprocessing step essentially builds the adjacency list first before beginning the breadth first search algorithm.

For eg. While doing BFS if we have to find the adjacent nodes for Dug we can first find all the generic states for Dug.

Dug => *ug
Dug => D*g
Dug => Du*
The second transformation D*g could then be mapped to Dog or Dig, since all of them share the same generic state. Having a common generic transformation 
means two words are connected and differ by one letter.

Intuition

Start from beginWord and search the endWord using BFS.

Algorithm

1. Do the pre-processing on the given wordList and find all the possible generic/intermediate states. Save these intermediate states in a dictionary with 
    key as the intermediate word and value as the list of words which have the same intermediate word.
2. Push a tuple containing the beginWord and 1 in a queue. The 1 represents the level number of a node. We have to return the level of the endNode as that 
    would represent the shortest sequence/distance from the beginWord.
3. To prevent cycles, use a visited dictionary.
4. While the queue has elements, get the front element of the queue. Let's call this word as current_word.
5. Find all the generic transformations of the current_word and find out if any of these transformations is also a transformation of other words in the
    word list. This is achieved by checking the all_combo_dict.
6. The list of words we get from all_combo_dict are all the words which have a common intermediate state with the current_word. These new set of words 
    will be the adjacent nodes/words to current_word and hence added to the queue.
7. Hence, for each word in this list of intermediate words, append (word, level + 1) into the queue where level is the level for the current_word.
8. Eventually if you reach the desired word, its level would represent the shortest transformation sequence length.
    Termination condition for standard BFS is finding the end word.
*/
// My Solution
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        HashSet<String> set = new HashSet<>(wordList);
        
        if(!set.contains(endWord)) {
            return 0;
        }

        Queue<String> queue= new LinkedList<>();
        
        queue.add(beginWord);
        int level= 1;
        
        while(!queue.isEmpty()) {
            int size = queue.size();
            
            for(int i=0; i <size; i++ ) {
                String s = queue.remove();        //'hit'
                char[] c = s.toCharArray();
                
                for(int j=0; j<c.length; j++) {
                    for( char k='a'; k<='z'; k++) {
                        char temp = c[j];
                        c[j] = k;
                        
                        String s2 = new String(c);
                        
                        if(set.contains(s2)) {
                            if(s2.equals(endWord)) return level+1;
                            queue.add(s2);
                            set.remove(s2);
                        }                    
                        c[j]=temp;
                    }
                }            
            }
            level++;
        }
        return 0;
    }
}

// Another Solution
public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    // T: O(M^2*N) M- length of beginWord, N-length of the wordList
    // O: O(M*N)
    
    Set<String> set = new HashSet<>(wordList);
    Queue<String> queue = new LinkedList<>();
    queue.add(beginWord);
    // COUNT NUMBER OF WORDS TRANSFORMED
    int count = 1;
    while (!queue.isEmpty()) {
        int size = queue.size();
        // FOR ALL WORDS THIS ROUND
        for (int i = 0; i < size; i++) {                    // T(while(!queue.isEmpty()) ) + T(for(int i=0; i<size; i++)) = T(N), N-length of the wordList
            char[] current = queue.poll().toCharArray();
            // TRAVERSE CURRENT WORD
            for (int j = 0; j < current.length; j++) {      // T(O): M
                char tmp = current[j];
                // CHANGE ONE LETTER AT A TIME 
                for (char c = 'a'; c <= 'z'; c++) {
                    current[j] = c;
                    String next = new String(current);      // in library T(O): M
                    // WHEN NEXT WORD IS IN THE SET
                    if (set.contains(next)) {
                        if (next.equals(endWord))   // in worst case and library T(O): M
                            return count + 1;
                        queue.add(next);
                        set.remove(next);
                    }
                }
                // HAVE TO UNDO FOR NEXT CHANGE OF LETTER
                current[j] = tmp;
            }
        }
        // THIS ROUND ENDS WITH ONE LETTER CHANGED
        count++;
    }
    return 0;
}

// Approach 2: Bidirectional Breadth First Search
/**
Intuition

The graph formed from the nodes in the dictionary might be too big. The search space considered by the breadth first search algorithm depends upon the 
branching factor of the nodes at each level. If the branching factor remains the same for all the nodes, the search space increases exponentially along 
with the number of levels. Consider a simple example of a binary tree. With each passing level in a complete binary tree, the number of nodes increase 
in powers of 2.

We can considerably cut down the search space of the standard breadth first search algorithm if we launch two simultaneous BFS. One from the beginWord 
and one from the endWord. We progress one node at a time from both sides and at any point in time if we find a common node in both the searches, we stop 
the search. This is known as bidirectional BFS and it considerably cuts down on the search space and hence reduces the time and space complexity.

Algorithm
1. The algorithm is very similar to the standard BFS based approach we saw earlier.
2. The only difference is we now do BFS starting two nodes instead of one. This also changes the termination condition of our search.
3. We now have two visited dictionaries to keep track of nodes visited from the search starting at the respective ends.
4. If we ever find a node/word which is in the visited dictionary of the parallel search we terminate our search, since we have found the meet point of 
this bidirectional search. It's more like meeting in the middle instead of going all the way through.
    Termination condition for bidirectional search is finding a word which is already been seen by the parallel search.
5. The shortest transformation sequence is the sum of levels of the meet point node from both the ends. Thus, for every visited node we save its level as 
value in the visited dictionary.
*/
class Solution {
    // T: O(M^2*N) M- length of beginWord, N-length of the wordList
    // O: O(M*N)
    public List<String> findNeighbors(String s) {
        List<String> res= new LinkedList<>();
        char[] temp= s.toCharArray();
        
        for(int i=0; i<temp.length; i++) {  // T: O(M)
            char ch=temp[i];
            for(char j='a'; j<='z'; j++) {
                temp[i]=j;
                String neighbor= new String(temp);  // in library T: O(M)
                res.add(neighbor);
            }
            temp[i]=ch;
        }
        return res;
    }
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> set= new HashSet<>(wordList);
        if(!set.contains(endWord)) return 0;
        
        Set<String> beginSet= new HashSet<>();
        Set<String> endSet= new HashSet<>();
                
        set.remove(beginWord);
        beginSet.add(beginWord);
        endSet.add(endWord);
        int level=1;
        
        while(!beginSet.isEmpty() && !endSet.isEmpty()) {
            if(beginSet.size()>endSet.size()){
                Set<String> temp= beginSet;
                beginSet= endSet;
                endSet= temp;
            }
            Set<String> newBeginSet= new HashSet<>();
            for(String s:beginSet) {
                List<String> neighbors= findNeighbors(s);
                for(String neighbor: neighbors) {
                    if(endSet.contains(neighbor)) return level+1;
                    else if(set.contains(neighbor)) {
                        newBeginSet.add(neighbor);
                        set.remove(neighbor);
                    }
                }
            }
            beginSet= newBeginSet;
            level++;
        }
        return 0;
    }
}
