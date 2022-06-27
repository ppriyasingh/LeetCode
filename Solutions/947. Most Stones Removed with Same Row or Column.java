class Solution {
    public int removeStones(int[][] stones) {
        
        int n= stones.length;
        int[] parents= new int[n];
        
        for(int i=0; i<n; i++) parents[i]= i;
        
        for(int i=0; i<n; i++) {
            for(int j=i+1; j<n; j++) {
                
                if(stones[i][0] == stones[j][0] || stones[i][1] == stones[j][1]) {
                    int rooti= find( i, parents);
                    int rootj= find( j, parents);
                    
                    if(rooti != rootj) union(rooti, rootj, parents);
                }
            }
        }
        
        int count=0;
        for(int i=0; i<parents.length; i++)  if(i==parents[i]) count++;
        return n-count;
    }
    
    public void union(int i, int j, int[] parents) {
        parents[j] = i;
    }
    public int find( int x, int[] parents) {
        if(x == parents[x]) return x;
        return find(parents[x], parents);
    }
}
