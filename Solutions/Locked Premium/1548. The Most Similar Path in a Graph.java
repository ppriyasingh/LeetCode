/**
We have n cities and m bi-directional roads where roads[i] = [ai, bi] connects city ai with city bi. Each city has a name consisting of exactly 3 upper-case English letters given in the string array names. Starting at any city x, you can reach any city y where y != x (i.e. the cities and the roads are forming an undirected connected graph).

You will be given a string array targetPath. You should find a path in the graph of the same length and with the minimum edit distance to targetPath.

You need to return the order of the nodes in the path with the minimum edit distance, The path should be of the same length of targetPath and should be valid (i.e. there should be a direct road between ans[i] and ans[i + 1]). If there are multiple answers return any one of them.

The edit distance is defined as follows:


Follow-up: If each node can be visited only once in the path, What should you change in your solution?

Input: n = 5, roads = [[0,2],[0,3],[1,2],[1,3],[1,4],[2,4]], names = ["ATL","PEK","LAX","DXB","HND"], targetPath = ["ATL","DXB","HND","LAX"]
Output: [0,2,4,2]
Explanation: [0,2,4,2], [0,3,0,2] and [0,3,1,2] are accepted answers.
[0,2,4,2] is equivalent to ["ATL","LAX","HND","LAX"] which has edit distance = 1 with targetPath.
[0,3,0,2] is equivalent to ["ATL","DXB","ATL","LAX"] which has edit distance = 1 with targetPath.
[0,3,1,2] is equivalent to ["ATL","DXB","PEK","LAX"] which has edit distance = 1 with targetPath.

Input: n = 4, roads = [[1,0],[2,0],[3,0],[2,1],[3,1],[3,2]], names = ["ATL","PEK","LAX","DXB"], targetPath = ["ABC","DEF","GHI","JKL","MNO","PQR","STU","VWX"]
Output: [0,1,0,1,0,1,0,1]
Explanation: Any path in this graph has edit distance = 8 with targetPath.

Input: n = 6, roads = [[0,1],[1,2],[2,3],[3,4],[4,5]], names = ["ATL","PEK","LAX","ATL","DXB","HND"], targetPath = ["ATL","DXB","HND","DXB","ATL","LAX","PEK"]
Output: [3,4,5,4,3,2,1]
Explanation: [3,4,5,4,3,2,1] is the only path with edit distance = 0 with targetPath.
It's equivalent to ["ATL","DXB","HND","DXB","ATL","LAX","PEK"]

*/

// Solution 1
class Solution {
    public List<Integer> mostSimilar(int n, int[][] roads, String[] names, String[] targetPath) {
        List<Integer> ans = new ArrayList<>();
        // states & initialization
        Map<Integer, List<Integer>> map = generateMap(n, roads);
        Integer[][] nextNodeForMinED = new Integer[names.length][targetPath.length];
        Integer[][] minED = new Integer[names.length][targetPath.length];
        // find the best start point
        int start = 0;
        int overallMinED = Integer.MAX_VALUE / 2;
        for (int i = 0; i < names.length; i++) {
            int currMinED = dfs(names, i, 0, map, nextNodeForMinED, minED, targetPath);
            if (currMinED < overallMinED) {
                overallMinED = currMinED;
                start = i;
            }
        }
        // generate the answer
        while (ans.size() < targetPath.length) {
            ans.add(start);
            start = nextNodeForMinED[start][ans.size() - 1];
        }
        return ans;
    }
    
    private int dfs(String[] names, int p, int q, Map<Integer, List<Integer>> map, 
                   Integer[][] nextNodeForMinED, Integer[][] minED, String[] targetPath) {
        if (q >= targetPath.length) {
            return 0;
        }
        if (minED[p][q] != null) {
            return minED[p][q];
        }
        minED[p][q] = targetPath[q].equals(names[p]) ? 0 : 1;
        int neiMinED = Integer.MAX_VALUE / 2;
        for (int nei : map.get(p)) {
            int res = dfs(names, nei, q + 1, map, nextNodeForMinED, minED, targetPath);
            if (res < neiMinED) {
                neiMinED = res;
                nextNodeForMinED[p][q] = nei;
            }
        }
        return minED[p][q] += neiMinED;
    }
    
    private Map<Integer, List<Integer>> generateMap(int n, int[][] roads) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(i, new ArrayList<>());
        }
        for (int[] road : roads) {
            map.get(road[0]).add(road[1]);
            map.get(road[1]).add(road[0]);
        }
        return map;
    }
}

// Solution 2:
    public List<Integer> mostSimilar(int n, int[][] roads, String[] names, String[] targetPath) {
        int[][] path = new int[n][n];
        Result dp[][] = new Result[n][targetPath.length+1];
        for (int i=0;i<roads.length;i++){
            path[roads[i][0]][roads[i][1]] = 1;
            path[roads[i][1]][roads[i][0]] = 1;
        }
        Result minDist = null;
        for (int i=0;i<n;i++){
            Result dis = dfs(targetPath.length,i,path,names,targetPath,dp);
            if (minDist == null || minDist.editDist > dis.editDist)
                minDist = dis;
        }
        return minDist.path;
    }

    public Result dfs(int pathLeft,int city,int [][]path,String []names,String []targetPath,Result dp[][]){
        if (pathLeft == 0){
            return null;
        }

        Result result = new Result(Integer.MAX_VALUE,new LinkedList<>());
        result.editDist =  editDist(targetPath,names,pathLeft,city);
        result.path.add(city);

        int cities[] = path[city];
        Result mindDist = new Result(Integer.MAX_VALUE,null);
        for (int j=0;j<cities.length;j++){
            if (path[city][j] == 0)
                continue;

            Result remainDist;
            if (dp[j][pathLeft-1] != null)
                remainDist = dp[j][pathLeft-1];
            else
                remainDist = dfs(pathLeft - 1,j,path,names,targetPath,dp);
          if (remainDist != null && remainDist.editDist < mindDist.editDist)
               mindDist = remainDist;
        }

        if (mindDist.editDist != Integer.MAX_VALUE) {
            result.editDist += mindDist.editDist;
            result.path.addAll(mindDist.path);
        }
        dp[city][pathLeft] = result;
        return result;
    }

    public int editDist(String []targetPath,String []names,int path,int city){
        if (!targetPath[targetPath.length - path].equals(names[city]))
            return 1;
        return 0;
    }

    static  class  Result{
        int editDist;
        List<Integer> path;
        public Result(int editDis,List<Integer> path){
            this.editDist = editDis;
            this.path = path;
        }
    }
	
// Solution 3: 
// Dijkstra's Algorithm
// 65 ms, faster than 90.04%
class CityPathIndex {
  int city;
  int pathIndex;
  CityPathIndex(int city, int pathIndex) {
    this.city = city;
    this.pathIndex = pathIndex;
  }
}
class Solution {
  public List<Integer> mostSimilar(int n, int[][] roads, String[] names, String[] targetPath) {
    int p = targetPath.length;
    // min distance for each city index at path index
    int[][] dist = new int[n][p];
    // prev city for city index at path index
    int[][] path = new int[n][p];
    
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++)
      graph.add(new ArrayList<>());
    
    for (int[] road : roads) {
      graph.get(road[0]).add(road[1]);
      graph.get(road[1]).add(road[0]);
    }
    
    // sort by distance to city at specific path index
    PriorityQueue<CityPathIndex> pq = new PriorityQueue<>((a,b) -> Integer.compare(dist[a.city][a.pathIndex], dist[b.city][b.pathIndex]));
    
    for (int city = 0; city < n; city++) {
      Arrays.fill(dist[city], Integer.MAX_VALUE);
      dist[city][0] = targetPath[0].equals(names[city]) ? 0 : 1;
      pq.add(new CityPathIndex(city, 0));
    }
    
    while (!pq.isEmpty()) {
      CityPathIndex curr = pq.remove();
      int city = curr.city;
      int pathIndex = curr.pathIndex;
      int currDist = dist[city][pathIndex];
      if (pathIndex == p - 1)
        break;
      for (int adjCity : graph.get(city)) {
        int adjPathIndex = pathIndex + 1;
        int adjDist = targetPath[adjPathIndex].equals(names[adjCity]) ? currDist : currDist + 1;
        if (adjDist < dist[adjCity][adjPathIndex]) {
          dist[adjCity][adjPathIndex] = adjDist;
          path[adjCity][adjPathIndex] = city;
          pq.add(new CityPathIndex(adjCity,adjPathIndex));
        }
      }      
    }
    
    int cityOnPath = 0;
    int minDist = Integer.MAX_VALUE;
    for (int city = 0; city < n; city++) {
      if (dist[city][p-1] < minDist) {
        minDist = dist[city][p-1];
        cityOnPath = city;
      }
    }
    
    LinkedList<Integer> result = new LinkedList<>();
    for (int pathIndex = p - 1; pathIndex >= 0; pathIndex--) {
      result.addFirst(cityOnPath);
      cityOnPath = path[cityOnPath][pathIndex];
    }
    return result;
  }
}

// Solution 4: 
public List<Integer> mostSimilar(int n, int[][] roads, String[] names, String[] targetPath) {
	List<Integer> res = new ArrayList<>();
	Map<Integer, List<Integer>> map = new HashMap<>();
	for (int[] edge: roads) {
		map.putIfAbsent(edge[0], new ArrayList<>());
		map.get(edge[0]).add(edge[1]);
		map.putIfAbsent(edge[1], new ArrayList<>());
		map.get(edge[1]).add(edge[0]);
    }

    int m = targetPath.length;
    // [i, j] record the next best city for city j at round i
    int[][] nextNodeMemo = new int[m][n];
    // [i ,j]: record the min dist for city j at round i.
    int[][] minDistMemo = new int[m][n];
    for (int i = m - 1; i >= 0; i--) {
        for (int j = 0; j < n; j++) {
            int curIncrement = names[j].equals(targetPath[i]) ? 0 : 1;
            if (i == m - 1) {
                minDistMemo[i][j] = curIncrement;
            } else {
                List<Integer> neighbors = map.get(j);
                int nextMinDist = Integer.MAX_VALUE;
                int nextNode = -1;
                if (neighbors == null) {
                    minDistMemo[i][j] = Integer.MAX_VALUE;
                } else {
                    for (int neighbor: neighbors) {
                        if (minDistMemo[i + 1][neighbor] < nextMinDist) {
                            nextMinDist = minDistMemo[i + 1][neighbor];
                            nextNode = neighbor;
                        }
                    }
                    minDistMemo[i][j] = nextMinDist + curIncrement;
                    nextNodeMemo[i][j] = nextNode;
                }
            }
        }
    }
    int totalMinDist = Integer.MAX_VALUE;
    int startCity = -1;
    for (int j = 0; j < n; j++) {
        if (minDistMemo[0][j] < totalMinDist) {
            totalMinDist = minDistMemo[0][j];
            startCity = j;
        }
    }
    res.add(startCity);
    for (int i = 0; i < m - 1; i++) {
        res.add(nextNodeMemo[i][startCity]);
        startCity = nextNodeMemo[i][startCity];
    }
    return res;
}
// time: O (m * n * n)
// space: O (m * n)