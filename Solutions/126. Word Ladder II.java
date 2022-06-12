/*Explanation

The basic idea is:

1). Use BFS to find the shortest distance between start and end, tracing the distance of crossing nodes from start node to end node, and store node's next 
  level neighbors to HashMap;
2). Use DFS to output paths with the same distance as the shortest distance from distance HashMap: compare if the distance of the next level node equals 
  the distance of the current node + 1.
  */
public List<List<String>> findLadders(String start, String end, List<String> wordList) {
   HashSet<String> dict = new HashSet<String>(wordList);
   List<List<String>> res = new ArrayList<List<String>>();         
   HashMap<String, ArrayList<String>> nodeNeighbors = new HashMap<String, ArrayList<String>>();// Neighbors for every node
   HashMap<String, Integer> distance = new HashMap<String, Integer>();// Distance of every node from the start node
   ArrayList<String> solution = new ArrayList<String>();

   dict.add(start);          
   bfs(start, end, dict, nodeNeighbors, distance);                 
   dfs(start, end, dict, nodeNeighbors, distance, solution, res);   
   return res;
}

// BFS: Trace every node's distance from the start node (level by level).
private void bfs(String start, String end, Set<String> dict, HashMap<String, ArrayList<String>> nodeNeighbors, HashMap<String, Integer> distance) {
  for (String str : dict)
      nodeNeighbors.put(str, new ArrayList<String>());

  Queue<String> queue = new LinkedList<String>();
  queue.offer(start);
  distance.put(start, 0);

  while (!queue.isEmpty()) {
      int count = queue.size();
      boolean foundEnd = false;
      for (int i = 0; i < count; i++) {
          String cur = queue.poll();
          int curDistance = distance.get(cur);                
          ArrayList<String> neighbors = getNeighbors(cur, dict);

          for (String neighbor : neighbors) {
              nodeNeighbors.get(cur).add(neighbor);
              if (!distance.containsKey(neighbor)) {// Check if visited
                  distance.put(neighbor, curDistance + 1);
                  if (end.equals(neighbor))// Found the shortest path
                      foundEnd = true;
                  else
                      queue.offer(neighbor);
                  }
              }
          }

          if (foundEnd)
              break;
      }
  }

// Find all next level nodes.    
private ArrayList<String> getNeighbors(String node, Set<String> dict) {
  ArrayList<String> res = new ArrayList<String>();
  char chs[] = node.toCharArray();

  for (char ch ='a'; ch <= 'z'; ch++) {
      for (int i = 0; i < chs.length; i++) {
          if (chs[i] == ch) continue;
          char old_ch = chs[i];
          chs[i] = ch;
          if (dict.contains(String.valueOf(chs))) {
              res.add(String.valueOf(chs));
          }
          chs[i] = old_ch;
      }

  }
  return res;
}

// DFS: output all paths with the shortest distance.
private void dfs(String cur, String end, Set<String> dict, HashMap<String, ArrayList<String>> nodeNeighbors, HashMap<String, Integer> distance, ArrayList<String> solution, List<List<String>> res) {
    solution.add(cur);
    if (end.equals(cur)) {
       res.add(new ArrayList<String>(solution));
    } else {
       for (String next : nodeNeighbors.get(cur)) {            
            if (distance.get(next) == distance.get(cur) + 1) {
                 dfs(next, end, dict, nodeNeighbors, distance, solution, res);
            }
        }
    }           
   solution.remove(solution.size() - 1);
}

// Refactored
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<String> path = new ArrayList<>();
        List<List<String>> result = new ArrayList<List<String>>();
        HashMap<String, List<String>> graph = new HashMap<String, List<String>>();
        HashSet<String> dict = new HashSet<>(wordList);
        buildGraph(beginWord, endWord, graph, dict);
        dfs(beginWord, endWord, graph, path, result);
        return result;
    }
    
    private void buildGraph(String beginWord, String endWord, HashMap<String, List<String>> graph, HashSet<String> dict) {
        HashSet<String> visited = new HashSet<>();
        HashSet<String> toVisit = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        toVisit.add(beginWord);
        boolean foundEnd = false;
        
        while(!queue.isEmpty()) {
            visited.addAll(toVisit);
            toVisit.clear();
            int count = queue.size();
            
            for (int i = 0; i < count; i++) {
                String word = queue.poll();
                List<String> children = getNextLevel(word, dict);
                for (String child : children) {
                    if (child.equals(endWord)) foundEnd = true;
                    if (!visited.contains(child)) {
                        if (!graph.containsKey(word)) {
                            graph.put(word, new ArrayList<String>());
                        }
                        graph.get(word).add(child);
                    }
                    if (!visited.contains(child) && !toVisit.contains(child)) {
                        queue.offer(child);
                        toVisit.add(child);
                    }
                }
            }
            
            if (foundEnd) break;
        }
    }
    
    private List<String> getNextLevel(String word, HashSet<String> dict) {
        List<String> result = new ArrayList<>();
        char[] chs = word.toCharArray();
        
        for (int i = 0; i < chs.length; i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (chs[i] == c) continue;
                char t = chs[i];
                chs[i] = c;
                String target = String.valueOf(chs);
                if (dict.contains(target)) result.add(target);
                chs[i] = t;
            }
        }
        
        return result;
    }
    
    private void dfs(String curWord, String endWord, HashMap<String, List<String>> graph, List<String> path, List<List<String>> result) {
        path.add(curWord);
        
        if (curWord.equals(endWord)) result.add(new ArrayList<String>(path));
        else if (graph.containsKey(curWord)) {
            for (String nextWord : graph.get(curWord)) {
                dfs(nextWord, endWord, graph, path, result);
            }
        }

        path.remove(path.size()-1);
    }
}

// Bi-directional Approach
  public List<List<String>> findLadders(String start, String end, Set<String> dict) {
    // hash set for both ends
    Set<String> set1 = new HashSet<String>();
    Set<String> set2 = new HashSet<String>();
    
    // initial words in both ends
    set1.add(start);
    set2.add(end);
    
    // we use a map to help construct the final result
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    
    // build the map
    helper(dict, set1, set2, map, false);
    
    List<List<String>> res = new ArrayList<List<String>>();
    List<String> sol = new ArrayList<String>(Arrays.asList(start));
    
    // recursively build the final result
    generateList(start, end, map, sol, res);
    
    return res;
  }
  
  boolean helper(Set<String> dict, Set<String> set1, Set<String> set2, Map<String, List<String>> map, boolean flip) {
    if (set1.isEmpty()) {
      return false;
    }
    
    if (set1.size() > set2.size()) {
      return helper(dict, set2, set1, map, !flip);
    }
    
    // remove words on current both ends from the dict
    dict.removeAll(set1);
    dict.removeAll(set2);
    
    // as we only need the shortest paths
    // we use a boolean value help early termination
    boolean done = false;
    
    // set for the next level
    Set<String> set = new HashSet<String>();
    
    // for each string in end 1
    for (String str : set1) {
      for (int i = 0; i < str.length(); i++) {
        char[] chars = str.toCharArray();
        
        // change one character for every position
        for (char ch = 'a'; ch <= 'z'; ch++) {
          chars[i] = ch;
          
          String word = new String(chars);
          
          // make sure we construct the tree in the correct direction
          String key = flip ? word : str;
          String val = flip ? str : word;
              
          List<String> list = map.containsKey(key) ? map.get(key) : new ArrayList<String>();
              
          if (set2.contains(word)) {
            done = true;
            
            list.add(val);
            map.put(key, list);
          } 
          
          if (!done && dict.contains(word)) {
            set.add(word);
            
            list.add(val);
            map.put(key, list);
          }
        }
      }
    }
    
    // early terminate if done is true
    return done || helper(dict, set2, set, map, !flip);
  }
  
  void generateList(String start, String end, Map<String, List<String>> map, List<String> sol, List<List<String>> res) {
    if (start.equals(end)) {
      res.add(new ArrayList<String>(sol));
      return;
    }
    
    // need this check in case the diff between start and end happens to be one
    // e.g "a", "c", {"a", "b", "c"}
    if (!map.containsKey(start)) {
      return;
    }
    
    for (String word : map.get(start)) {
      sol.add(word);
      generateList(word, end, map, sol, res);
      sol.remove(sol.size() - 1);
    }
  }

// Refactored
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList();
        Set<String> words = new HashSet(wordList);
        
        Set<String> start = new HashSet();
        start.add(beginWord);
        boolean found = false;
        
        // use hashMap to store all possible route ending at key
        Map<String, List<List<String>>> map = new HashMap();
        List<String> init = new ArrayList();
        init.add(beginWord);
        map.put(beginWord, new ArrayList());
        map.get(beginWord).add(init);
        
        
        while(!words.isEmpty() && !found && !start.isEmpty()){
            // eliminate all previous layer words from dict
            words.removeAll(start);
            // use another set to record next layers' words
            Set<String> newStart = new HashSet();
            
            // iterate through all new starts
            for(String s: start){
                char[] chs = s.toCharArray();
                List<List<String>> endPath  = map.get(s);
                for(int i=0;i<chs.length;i++){
                    // randomly change a character
                    for(char ch='a';ch<='z';ch++){
                        if(chs[i]==ch) continue;
                        char tmp = chs[i];
                        chs[i] = ch;
                        String word = new String(chs);
                        //check if it is in the dict, if so new start found, extending all routes
                        if(words.contains(word)){
                            newStart.add(word);
                            for(List<String> path:endPath){
                                List<String> nextPath = new ArrayList(path);
                                nextPath.add(word);
                                map.putIfAbsent(word, new ArrayList());
                                map.get(word).add(nextPath);
                                if(word.equals(endWord)){
                                    found = true;
                                    res.add(nextPath);
                                }
                            }

                        }
                        chs[i] = tmp;
                    }
                }
                map.remove(s);
            }
            // clear the previous layers words and update
            start.clear();
            start.addAll(newStart);
            
        }
        
        return res;
    }
    
}
