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
