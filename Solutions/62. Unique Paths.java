class Solution {
    public int uniquePaths(int m, int n) {
        return uniquePathsUtil(m, n, new int[m+1][n+1]);
    }
    
    public int uniquePathsUtil(int m, int n, int[][] grid) {
        
        if(m==1 && n==1) return 1;
        if(m==0 || n==0) return 0;
        
        if(grid[m][n]!=0) return grid[m][n];
        
        
        return uniquePathsUtil(m-1, n, grid)+uniquePathsUtil(m, n-1, grid);
    }
}

