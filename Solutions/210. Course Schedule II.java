// Solution 1:
class Solution {
    public void topologicalSort(HashMap<Integer, List<Integer>> graph, boolean[] visited, Stack<Integer> stk, int node){
        visited[node]= true;
        for(int x: graph.getOrDefault(node, new ArrayList<Integer>()) ) {
            if(!visited[x]) {
                topologicalSort(graph, visited, stk, x);
            }
        }
        stk.push(node);
    }
    
    public boolean isCycle(HashMap<Integer, List<Integer>> graph, int node, boolean[] visited){
        if(visited[node]== true) return true;
        visited[node]= true;
        boolean flag= false;
        for(int x: graph.getOrDefault(node, new ArrayList<Integer>()) ) {
            // if(!visited[x]) {
                 flag=isCycle(graph, x, visited);
                if(flag) return true;
            // } 
        }
        visited[node]= false;
        return false;
    }
    
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        
        for(int[] x: prerequisites) {
            if(graph.containsKey(x[1])) {
                graph.get(x[1]).add(x[0]);
            } else {
                List<Integer> l= new LinkedList<>();
                l.add(x[0]);
                graph.put(x[1],l);
            }
        }
        boolean[] visited= new boolean[numCourses];
        boolean[] reached= new boolean[numCourses];
        
        for(int i=0; i<numCourses; i++) {
            if(isCycle(graph, i,visited )) return new int[0];
        }
        
        Stack<Integer> stk= new Stack<>();
        for(int i=0; i<numCourses; i++) {
            if(!visited[i]) topologicalSort(graph, visited, stk, i);
        }
        
        int z=0;
        int[] ans = new int[numCourses];

        while(!stk.isEmpty()) {
            ans[z++] = stk.pop();
        }
        return ans;
    }
}

// Solution 2:
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if(numCourses==0 ) return new int[0];
        
        int[] indegree = new int[numCourses];
        Queue<Integer> q= new LinkedList<Integer>();
        for(int[] x: prerequisites) {
            indegree[x[0]]++;
        }
        for(int i=0; i< numCourses; i++) {
            if(indegree[i]==0) q.add(i);
        }

        int k=0;
        int[] ans= new int[numCourses];
        while(!q.isEmpty()) {
            int x= q.poll();
            ans[k++] =x;
            
            for(int[] z: prerequisites) {
                if(z[1]==x) {
                    indegree[z[0]]--;
                    if(indegree[z[0]]==0) q.offer(z[0]);
                }
            }
        }
        
        if(k!=numCourses) return new int[0];
        return ans;
    }
}
