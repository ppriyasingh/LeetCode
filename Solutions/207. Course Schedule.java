// My Solution
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if(numCourses == 0) return true;
        
        int[] courses = new int[numCourses];
        boolean[] visited= new boolean[prerequisites.length];
        
        for(int[] p: prerequisites) {
            courses[p[1]]++;
        }
        boolean flag = true;
        
        while(flag) {
            flag = false;
            
            for(int i=0; i<prerequisites.length; i++) {
                
                if(!visited[i] && courses[prerequisites[i][0]]==0) {
                    flag = true;
                    visited[i] = true;
                    courses[prerequisites[i][1]]--;
                }
            }
        }
        for(int x: courses) if(x!=0) return false;
        return true;
    }
}
// BFS Topological Sorting, O(N + E)
public boolean canFinish(int n, int[][] prerequisites) {
        ArrayList<Integer>[] G = new ArrayList[n];
        int[] degree = new int[n];
        ArrayList<Integer> bfs = new ArrayList();
        for (int i = 0; i < n; ++i) G[i] = new ArrayList<Integer>();
        for (int[] e : prerequisites) {
            G[e[1]].add(e[0]);
            degree[e[0]]++;
        }
        for (int i = 0; i < n; ++i) if (degree[i] == 0) bfs.add(i);
        for (int i = 0; i < bfs.size(); ++i)
            for (int j: G[bfs.get(i)])
                if (--degree[j] == 0) bfs.add(j);
        return bfs.size() == n;
    }

// My Solution
class Solution {
    // T(O): O(N + E), where N is the number of courses, and E is the number of dependencies.
    // S(O): O(N + E)
    public boolean isCyclic(int i, boolean[] visited, boolean[] checked, Map<Integer, List<Integer>> adjList) {
        
        if(visited[i]) return true;
        if(checked[i]) return false;
        
        visited[i] = true;
        boolean flag = false;
        for(int x: adjList.getOrDefault(i, new ArrayList<>())) {
            if(isCyclic(x, visited, checked, adjList)) {
                flag = true;
                break;
            }
        }
        
        visited[i] = false;
        checked[i] = true;
        return flag;
    }
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> adjList= new HashMap<>();
        
        for(int[] p: prerequisites) {
            List<Integer> list= adjList.getOrDefault(p[1], new ArrayList<>());
            list.add(p[0]);
            adjList.put(p[1], list);
        }
        boolean[] visited = new boolean[numCourses];
        boolean[] checked = new boolean[numCourses];
        
        
        for(int i=0; i<numCourses; i++) {
            if(isCyclic(i, visited, checked, adjList)) return false;
        }
        return true;
    }
}
