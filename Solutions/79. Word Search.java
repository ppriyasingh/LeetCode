class Solution {
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
