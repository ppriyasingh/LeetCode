class Solution {
    /*
    // T(O): O(N*3^L) where N is the number of cells in the board and L is the length of the word to be matched. For the backtracking function, initially 
    we could have at most 4 directions to explore, but further the choices are reduced into 3 (since we won't go back to where we come from). As a result, 
    the execution trace after the first step could be visualized as a 3-ary tree, each of the branches represent a potential exploration in the 
    corresponding direction. Therefore, in the worst case, the total number of invocation would be the number of nodes in a full 3-nary tree, which is 
    about 3^L. We iterate through the board for backtracking, i.e. there could be N times invocation for the backtracking function in the worst case.
    As a result, overall the time complexity of the algorithm would be O(N*3^L)

    // S(O): O(L) where L is the length of the word to be matched.
    */
    public boolean dfs(char[][] board, String word, int ind, int i, int j, int n, int m) {
        if(ind == word.length()) return true;
        boolean doesExist= false;
        
        if(i<n && j<m && i>=0 && j>=0 && word.charAt(ind)==board[i][j]) {
            char ch= board[i][j];
            board[i][j] = '*';
            
            doesExist= dfs(board, word, ind+1, i+1, j, n, m) || dfs(board, word, ind+1, i-1, j, n, m) ||
                dfs(board, word, ind+1, i, j+1, n, m) || dfs(board, word, ind+1, i, j-1, n, m);
            
            board[i][j] = ch;
        }
        return doesExist;
    }
    public boolean exist(char[][] board, String word) {
        int n= board.length, m= board[0].length;
        
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(board[i][j] == word.charAt(0) && dfs(board, word, 0, i, j, n, m)) {
                    return true;
                }
            }
        }
        return false;
    }
}
