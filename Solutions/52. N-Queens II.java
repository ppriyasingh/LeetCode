/*
Overview
A brute force solution would involve generating all possible board states with N queens. There are N^2 possible squares to place the first queen, N^2 - 1
to place the second and so on. This leads to a time complexity of O(N^N), which is far too slow. The actual number of solutions is way smaller than the 
number of possible board states, so we should aim to minimize our consideration of invalid board states.

Imagine if we tried to generate all board states by placing queens down one by one. For 8 queens on a normal chessboard, let's say we place the first 
queen on the top left (index (0, 0), or, if you are familiar with Chess, a8). Then, we place the second queen to its right (index (0, 1), or b8).

// Approach: Backtracking
Intuition

We can still follow the strategy of generating board states, but we should never place a queen on an attacked square. This is a perfect problem for 
backtracking - place the queens one by one, and when all possibilities are exhausted, backtrack by removing a queen and placing it elsewhere.

If you're not familiar with backtracking, check out the backtracking section of our Recursion II Explore Card.

Given a board state, and a possible placement for a queen, we need a smart way to determine whether or not that placement would put the queen under 
attack. A queen can be attacked if another queen is in any of the 4 following positions: on the same row, on the same column, on the same diagonal, or on 
the same anti-diagonal.
Recall that to implement backtracking, we implement a backtrack function that makes some changes to the state, calls itself again, and then when that 
call returns it undoes those changes (this last part is why it's called "backtracking").

Each time our backtrack function is called, we can encode state in the following manner:
To make sure that we only place 1 queen per row, we will pass an integer argument row into backtrack, and will only place one queen during each call. 
Whenever we place a queen, we'll move onto the next row by calling backtrack again with the parameter value row + 1.

To make sure we only place 1 queen per column, we will use a set. Whenever we place a queen, we can add the column index to this set.
The diagonals are a little trickier - but they have a property that we can use to our advantage.
For each square on a given diagonal, the difference between the row and column indexes (row - col) will be constant. Think about the diagonal that starts 
from (0, 0) - the ith square has coordinates (i, i), so the difference is always 0.
For each square on a given anti-diagonal, the sum of the row and column indexes (row + col) will be constant. If you were to start at the highest square 
in an anti-diagonal and move downwards, the row index increments by 1 (row + 1), and the column index decrements by 1 (col - 1). These cancel each other 
out.
Whenever we place a queen, we should calculate the diagonal and the anti-diagonal value it belongs to. In the same way we had a set for the column, we 
should also have a set for both the diagonals and anti-diagonals. Then, we can put the values for this queen into the corresponding sets.

Algorithm

We'll create a recursive function backtrack that takes 4 arguments to maintain the board state. The first parameter is the row we're going to place a 
queen on next, and the other 3 are sets that track which columns, diagonals, and anti-diagonals have already had queens placed on them. The function will 
work as follows:

1. If the current row we are considering is greater than n, then we have a solution. Return 1.
2. Initiate a local variable solutions = 0 that represents all the possible solutions that can be obtained from the current board state.
3. Iterate through the columns of the current row. At each column, we will attempt to place a queen at the square (row, col) - remember we are considering
the current row through the function arguments.

3.a. Calculate the diagonal and anti-diagonal that the square belongs to. If there has been no queen placed yet in the column, diagonal, or anti-diagonal, 
then we can place a queen in this column, in the current row.
3.b. If we can't place the queen, skip this column (move on to try with the next column).
4. If we were able to place a queen, then update our 3 sets (cols, diagonals, and antiDiagonals), and call the function again, but with row + 1.
5. The function call made in step 4 explores all valid board states with the queen we placed in step 3. Since we're done exploring that path, backtrack by 
removing the queen from the square - this just means removing the values we added to our sets.
*/

class Solution {
  /*
  // T(O): N!, where N is the number of queens (which is the same as the width and height of the board). Unlike the brute force approach, we place a 
  queen only on squares that aren't attacked. For the first queen, we have NN options. For the next queen, we won't attempt to place it in the same 
  column as the first queen, and there must be at least one square attacked diagonally by the first queen as well. Thus, the maximum number of squares we 
  can consider for the second queen is N−2. For the third queen, we won't attempt to place it in 2 columns already occupied by the first 2 queens, and 
  there must be at least two squares attacked diagonally from the first 2 queens. Thus, the maximum number of squares we can consider for the third queen 
  is N−4. This pattern continues, giving an approximate time complexity of N! at the end.
  // S(O): N
  // Extra memory used includes the 3 sets used to store board state, as well as the recursion call stack. All of this scales linearly with the number of 
  // queens.
  */
    private int size;
    
    public int totalNQueens(int n) {
        size = n;
        return backtrack(0, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }
    
    private int backtrack(int row, Set<Integer> diagonals, Set<Integer> antiDiagonals, Set<Integer> cols) {
        // Base case - N queens have been placed
        if (row == size) {
            return 1;
        }
        
        int solutions = 0;
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

            // Move on to the next row with the updated board state
            solutions += backtrack(row + 1, diagonals, antiDiagonals, cols);

            // "Remove" the queen from the board since we have already
            // explored all valid paths using the above function call
            cols.remove(col);
            diagonals.remove(currDiagonal);
            antiDiagonals.remove(currAntiDiagonal); 
        }
        
        return solutions;
    }
}

// My Solution
class Solution {
    public int backtrack(int i, int n, Set<Integer> d1, Set<Integer> d2, Set<Integer> verticle) {
        if(n == i) {
            return 1;
        }
        
        int count = 0;
        for(int j=0; j<n; j++) {
            if(!verticle.contains(j) && !d1.contains(j-i) && !d2.contains(i+j)) {
                d1.add(j-i);
                d2.add(j+i);
                verticle.add(j);
                count += backtrack(i+1, n, d1, d2, verticle);
                d1.remove(j-i);
                d2.remove(j+i);
                verticle.remove(j);
            }
        }
        return count;
    }
    
    public int totalNQueens(int n) {
        return backtrack(0, n, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }
}
