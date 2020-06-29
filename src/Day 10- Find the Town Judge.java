class Solution {
    public int findJudge(int N, int[][] trust) {
        
        int[] inDegree= new int[N+1];//, outDegree= new int[N+1];
        
        for(int i=0; i<trust.length; i++) {
            
            inDegree[trust[i][0]]--;
            inDegree[trust[i][1]]++;
        }
        
        for(int i=1; i<=N; i++) {
            if(inDegree[i]==(N-1)) return i;
        }
        
        return -1;
    }
}