// Solution 1 (TLE):
class Solution {
//   T(O): O((N*M)*4^(N*M))
//   S(O): O(N*M), maximum recursive stack depth for a cell can be O(N*M) when maximum path is N*M.
    public int longestIncreasingPath(int[][] matrix, int i, int j) {
        if(i<0 || j<0 || i>=matrix.length || j>=matrix[0].length) return 0;
        int upCount = 0, bottomCount = 0, rightCount = 0, leftCount = 0;
        
        if(i+1<matrix.length && matrix[i][j] < matrix[i+1][j]) 
            upCount = 1 + longestIncreasingPath(matrix, i+1, j);
        
        if(i-1>=0 && matrix[i][j] < matrix[i-1][j]) 
            bottomCount = 1 + longestIncreasingPath(matrix, i-1, j);
        
        if(j+1<matrix[0].length && matrix[i][j] < matrix[i][j+1]) 
            rightCount = 1 + longestIncreasingPath(matrix, i, j+1);
        
        if(j-1>=0 && matrix[i][j] < matrix[i][j-1]) 
            leftCount = 1 + longestIncreasingPath(matrix, i, j-1);
        
        return Math.max(Math.max(upCount, bottomCount), Math.max(rightCount, leftCount));
    }
    public int longestIncreasingPath(int[][] matrix) {
        if(matrix.length == 0) return 0;
        
        int res = 0;
        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                int subLongest = 1 + longestIncreasingPath(matrix, i, j);
                if(res < subLongest) res = subLongest;
            }
        }
        
        return res;
    }
}
// Solution 2:
class Solution {
//     T(O): O(M*N)
//     S(O): O(M*N)
    public int longestIncreasingPath(int[][] matrix, int i, int j, int[][] cache, int prev) {
        if(i<0 || j<0 || i>=matrix.length || j>=matrix[0].length || prev>=matrix[i][j]) return 0;
        
        if(cache[i][j] != 0) return cache[i][j];
        
        int upCount = 0, bottomCount = 0, rightCount = 0, leftCount = 0;
        
        upCount = longestIncreasingPath(matrix, i+1, j, cache, matrix[i][j]);
        bottomCount = longestIncreasingPath(matrix, i-1, j, cache, matrix[i][j]);
        rightCount = longestIncreasingPath(matrix, i, j+1, cache, matrix[i][j]);
        leftCount = longestIncreasingPath(matrix, i, j-1, cache, matrix[i][j]);
        
        cache[i][j] = 1 + Math.max(Math.max(upCount, bottomCount), Math.max(rightCount, leftCount));
        return cache[i][j];
    }
    public int longestIncreasingPath(int[][] matrix) {
        if(matrix.length == 0) return 0;
        int[][] cache = new int[matrix.length][matrix[0].length];
        
        int res = 0;
        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                int subLongest = longestIncreasingPath(matrix, i, j, cache, -1);
                if(res < subLongest) res = subLongest;
            }
        }
        
        return res;
    }
}
