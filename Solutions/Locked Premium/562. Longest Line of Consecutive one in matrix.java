/**
* Given an m x n binary matrix mat, return the length of the longest line of consecutive one in the matrix.
* The line could be horizontal, vertical, diagonal, or anti-diagonal.
* Input: mat = [[0,1,1,0],[0,1,1,0],[0,0,0,1]]
* Output: 3
* Input: mat = [[1,1,1,1],[0,1,1,0],[0,0,0,1]]
* Output: 4
*/

// Approach 1: Brute Force
// Time complexity: O(m*n)
// Space Complexity: O(1)
class Solution {
  public int longestLine(int[][] M) {
    if (M.length == 0) return 0;
    int ones = 0;
    // horizontal
    for (int i = 0; i < M.length; i++) {
      int count = 0;
      for (int j = 0; j < M[0].length; j++) {
        if (M[i][j] == 1) {
          count++;
          ones = Math.max(ones, count);
        } else count = 0;
      }
    }
    // vertical
    for (int i = 0; i < M[0].length; i++) {
      int count = 0;
      for (int j = 0; j < M.length; j++) {
        if (M[j][i] == 1) {
          count++;
          ones = Math.max(ones, count);
        } else count = 0;
      }
    }
    // upper diagonal
    for (int i = 0; i < M[0].length || i < M.length; i++) {
      int count = 0;
      for (int x = 0, y = i; x < M.length && y < M[0].length; x++, y++) {
        if (M[x][y] == 1) {
          count++;
          ones = Math.max(ones, count);
        } else count = 0;
      }
    }
    // lower diagonal
    for (int i = 0; i < M[0].length || i < M.length; i++) {
      int count = 0;
      for (int x = i, y = 0; x < M.length && y < M[0].length; x++, y++) {
        if (M[x][y] == 1) {
          count++;
          ones = Math.max(ones, count);
        } else count = 0;
      }
    }
    // upper anti-diagonal
    for (int i = 0; i < M[0].length || i < M.length; i++) {
      int count = 0;
      for (int x = 0, y = M[0].length - i - 1; x < M.length && y >= 0; x++, y--) {
        if (M[x][y] == 1) {
          count++;
          ones = Math.max(ones, count);
        } else count = 0;
      }
    }
    // lower anti-diagonal
    for (int i = 0; i < M[0].length || i < M.length; i++) {
      int count = 0;
      for (int x = i, y = M[0].length - 1; x < M.length && y >= 0; x++, y--) {
        // System.out.println(x+" "+y);
        if (M[x][y] == 1) {
          count++;
          ones = Math.max(ones, count);
        } else count = 0;
      }
    }
    return ones;
  }
}
//Approach 2: Using 3D Dynamic Programming
// Time complexity: O(m*n)
// Space Complexity: O(m*n)
class Solution {
  public int longestLine(int[][] M) {
    if (M.length == 0) return 0;
    int ones = 0;
    int[][][] dp = new int[M.length][M[0].length][4];
    for (int i = 0; i < M.length; i++) {
      for (int j = 0; j < M[0].length; j++) {
        if (M[i][j] == 1) {
          dp[i][j][0] = j > 0 ? dp[i][j - 1][0] + 1 : 1;
          dp[i][j][1] = i > 0 ? dp[i - 1][j][1] + 1 : 1;
          dp[i][j][2] = (i > 0 && j > 0) ? dp[i - 1][j - 1][2] + 1 : 1;
          dp[i][j][3] = (i > 0 && j < M[0].length - 1) ? dp[i - 1][j + 1][3] + 1 : 1;
          ones =
              Math.max(
                  ones,
                  Math.max(Math.max(dp[i][j][0], dp[i][j][1]), Math.max(dp[i][j][2], dp[i][j][3])));
        }
      }
    }
    return ones;
  }
}
//Approach 3: Using 2D Dynamic Programming
// Time complexity: O(m*n)
// Space Complexity: O(n)
class Solution {
  public int longestLine(int[][] M) {
    if (M.length == 0) return 0;
    int ones = 0;
    int[][] dp = new int[M[0].length][4];
    for (int i = 0; i < M.length; i++) {
      int old = 0;
      for (int j = 0; j < M[0].length; j++) {
        if (M[i][j] == 1) {
          dp[j][0] = j > 0 ? dp[j - 1][0] + 1 : 1;
          dp[j][1] = i > 0 ? dp[j][1] + 1 : 1;
          int prev = dp[j][2];
          dp[j][2] = (i > 0 && j > 0) ? old + 1 : 1;
          old = prev;
          dp[j][3] = (i > 0 && j < M[0].length - 1) ? dp[j + 1][3] + 1 : 1;
          ones =
              Math.max(ones, Math.max(Math.max(dp[j][0], dp[j][1]), Math.max(dp[j][2], dp[j][3])));
        } else {
          old = dp[j][2];
          dp[j][0] = dp[j][1] = dp[j][2] = dp[j][3] = 0;
        }
      }
    }
    return ones;
  }
}
//Approach 4
class Solution {
    public int longestLine(int[][] mat) {
        int rows= mat.length, cols= mat[0].length, max= 0;
        int[][] count= new int[4][cols+rows];
        for(int i=0; i<rows; i++)
            for(int j=0; j<cols; j++)
                if(mat[i][j]==0){
                    count[0][i]= count[1][j]= count[2][j+i]= count[3][j+rows-i]= 0;
                }else{
                    max= Math.max(max, ++count[0][i]);          // rows
                    max= Math.max(max, ++count[1][j]);          // cols
                    max= Math.max(max, ++count[2][j+i]);        // ascending diagonals
                    max= Math.max(max, ++count[3][j+rows-i]);   // descending diagonals
                }
        return max;
    }
}