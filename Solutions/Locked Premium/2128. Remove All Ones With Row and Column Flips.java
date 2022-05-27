/*
 You are given an m x n binary matrix grid.

 In one operation, you can choose any row or column and flip each value in that row or column (i.e., changing all 0's to 1's, and all 1's to 0's).

 Return true if it is possible to remove all 1's from grid using any number of operations or false otherwise.

  

 Example 1:
 Input: grid = [[0,1,0],[1,0,1],[0,1,0]]
 Output: true
 Explanation: One possible way to remove all 1's from grid is to:
 - Flip the middle row
 - Flip the middle column

 Example 2:
 Input: grid = [[1,1,0],[0,0,0],[0,0,0]]
 Output: false
 Explanation: It is impossible to remove all 1's from grid.

 Example 3:
 Input: grid = [[0]]
 Output: true
 Explanation: There are no 1's in grid.
 */


 /*Solution 1:
 Both rows have to follow the same patterns for us to be able to flip them into all 0s.
 and by the same patterns, I mean for any two rows, they either have to be
 * Exactly the same
 * Exactly opposite
 If there exists a pair of two rows that do not meet the requirements above, we can't flip the table into all 0s.
 Reason being that we will want to perform column flip if there are two rows that don't meet the requirement above, but when we do col flip, all rows have 1 element flipped as well, so no matter what we do, it won't be doable.

 An easy way to check if two rows meet the pattern above is pick a fixed row, comparing the difference between the first element of the two rows, and all the other pairs of elements on these two rows have to have the same difference.
 */

 class Solution {
     public boolean removeOnes(int[][] grid) {

         for (int i=0; i<grid.length; i++){
             for (int j= 0; j<grid[0].length; j++){

                 if (Math.abs(grid[i][j] - grid[0][j]) != Math.abs(grid[i][0] - grid[0][0]))
                     return false;
             }
         }
         return true;
     }
 }

 /*
 Solution 2:
 // Read first ROW and if you find any value to be 1, then flip the whole COLUMN
 // Read first COLUMN and if you find any value to be 1, then flip the whole ROW
 // NOW, read all the grid[i][j] and if you find any value to be 1, that means we cannot get complete 0 grid because if we flip now, then some other 0 will flip to 1 and the cycle will continue

 // TC : O(M * N)
 // SC : O(1)
 */

 class Solution {
     public boolean removeOnes(int[][] grid) {
         int m = grid.length, n = grid[0].length;

         for(int i = 0; i < m; i++) if(grid[i][0] == 1) flip(grid, i, m, n, true);

         for(int j = 0; j < n; j++) if(grid[0][j] == 1) flip(grid, j, m, n, false);

         for(int i = 0; i < m; i++) {
             for(int j = 0; j < n; j++) {
                 if(grid[i][j] == 1) return false;
             }
         }

         return true;
     }

     private void flip(int[][] grid, int idx, int m, int n, boolean isRow) {
         if(isRow) {
             for(int i = 0; i < n; i++) grid[idx][i] = 1 - grid[idx][i];
         } else {
             for(int j = 0; j < m; j++) grid[j][idx] = 1 - grid[j][idx];
         }
     }
 }
