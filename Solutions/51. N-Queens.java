/*
Same as: https://github.com/ppriyasingh/LeetCode/blob/master/Solutions/52.%20N-Queens%20II.java
Algorithm

We'll create a recursive function backtrack that takes a few arguments to maintain the board state. The first parameter is the row we're going to place a 
queen on next, and then we will have 3 sets that track which columns, diagonals, and anti-diagonals have already had queens placed on them. Additionally, 
we will store the actual board so that when we find a valid solution, we can include it in our answer. The function will work as follows:

1. If the current row we are considering is equal to n, then we have a solution. Add the current board state to a list of solutions, and return. We'll 
make use of a small helper function to get our board into the correct output format.

2. Iterate through the columns of the current row. At each column, we will attempt to place a queen at the square (row, col) - remember we are considering 
the current row through the function arguments.

2.a. Calculate the diagonal and anti-diagonal that the square belongs to. If a queen has not been placed in the column, diagonal, or anti-diagonal, then 
we can place a queen in this column, in the current row.
2.b. If we can't place the queen, skip this column (move on and try the next column).
    
3. If we were able to place a queen, then add the queen to the board and update our 3 sets (cols, diagonals, and antiDiagonals), and call the function 
again, but with row + 1.
4. The function call made in step 3 explores all valid board states with the queen we placed in step 2. Since we're done exploring that path, backtrack by 
removing the queen from the square - this includes removing the values we added to our sets on top of removing the "Q" from the board.
*/
class Solution {
  /*
  // T(O): N!, where N is the number of queens (which is the same as the width and height of the board). Unlike the brute force approach, we place a 
  queen only on squares that aren't attacked. For the first queen, we have NN options. For the next queen, we won't attempt to place it in the same 
  column as the first queen, and there must be at least one square attacked diagonally by the first queen as well. Thus, the maximum number of squares we 
  can consider for the second queen is N−2. For the third queen, we won't attempt to place it in 2 columns already occupied by the first 2 queens, and 
  there must be at least two squares attacked diagonally from the first 2 queens. Thus, the maximum number of squares we can consider for the third queen 
  is N−4. This pattern continues, giving an approximate time complexity of N! at the end.
  // S(O): N^2, While it costs O(N^2)to build each valid solution, the amount of valid solutions S(N) does not grow nearly as fast as N!, 
  so O(N! + S(N) * N^2) = O(N!)
  // Extra memory used includes the 3 sets used to store board state, as well as the recursion call stack. All of this scales linearly with the number of 
  // queens.
  */
    private int size;
    private List<List<String>> solutions = new ArrayList<List<String>>();
    
    public List<List<String>> solveNQueens(int n) {
        size = n;
        char emptyBoard[][] = new char[size][size];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                emptyBoard[i][j] = '.';
            }
        }

        backtrack(0, new HashSet<>(), new HashSet<>(), new HashSet<>(), emptyBoard);
        return solutions;
    }
    
    // Making use of a helper function to get the
    // solutions in the correct output format
    private List<String> createBoard(char[][] state) {
        List<String> board = new ArrayList<String>();
        for (int row = 0; row < size; row++) {
            String current_row = new String(state[row]);
            board.add(current_row);
        }
        
        return board;
    }
    
    private void backtrack(int row, Set<Integer> diagonals, Set<Integer> antiDiagonals, Set<Integer> cols, char[][] state) {
        // Base case - N queens have been placed
        if (row == size) {
            solutions.add(createBoard(state));
            return;
        }
        
        for (int col = 0; col < size; col++) {
            int currDiagonal = row - col;
            int currAntiDiagonal = row + col;
            // If the queen is not placeable
            if (cols.contains(col) || diagonals.contains(currDiagonal) || antiDiagonals.contains(currAntiDiagonal)) {
                continue;    
            }
            
            // "Add" the queen to the board
            cols.add(col);
            diagonals.add(currDiagonal);
            antiDiagonals.add(currAntiDiagonal);
            state[row][col] = 'Q';

            // Move on to the next row with the updated board state
            backtrack(row + 1, diagonals, antiDiagonals, cols, state);

            // "Remove" the queen from the board since we have already
            // explored all valid paths using the above function call
            cols.remove(col);
            diagonals.remove(currDiagonal);
            antiDiagonals.remove(currAntiDiagonal);
            state[row][col] = '.';
        }
    }
}

// My Solution
class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res= new LinkedList<>();
        backtrack(0, new int[n][n], new HashSet<>(), new HashSet<>(), new HashSet<>(), res);
        return res;
    }
    
    public void backtrack(int i, int[][] grid, Set<Integer> d1, Set<Integer> d2, Set<Integer> verticle, List<List<String>> res) {
        if(grid.length == i) {
            res.add(format(grid));
            return;
        }
        
        for(int j=0; j<grid.length; j++) {
            if(!verticle.contains(j) && !d1.contains(j-i) && !d2.contains(i+j)) {
                d1.add(j-i);
                d2.add(j+i);
                verticle.add(j);
                grid[i][j] = 1;
                backtrack(i+1, grid, d1, d2, verticle, res);
                grid[i][j] = 0;
                d1.remove(j-i);
                d2.remove(j+i);
                verticle.remove(j);
            }
        }
    }
    
    public List<String> format(int[][] grid) {
        List<String> res = new LinkedList<>();
        
        for(int i=0; i<grid.length; i++) {
            StringBuilder subRes = new StringBuilder();
            for(int j=0; j<grid[0].length; j++) {
                if(grid[i][j] == 1) subRes.append("Q");
                else subRes.append(".");
            }
            res.add(subRes.toString());
        }
        return res;
    }
}
