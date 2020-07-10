class Solution {
    public int islandPerimeter(int[][] grid) {
        
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++){
                if( grid[i][j]==1)
                    return islandPerimeterUtil(grid, i, j);
            }
        }
        return 0;        
    }
    
    public int islandPerimeterUtil(int[][] grid, int i, int j) {
        
        if(i<0 || j<0 || i>=grid.length || j>=grid[0].length || grid[i][j]==0) 
            return 1;
        
        if(grid[i][j] ==2) return 0;
        
        grid[i][j] = 2;
        
        int count = islandPerimeterUtil(grid, i-1, j);
        count += islandPerimeterUtil(grid, i+1, j);
        count += islandPerimeterUtil(grid, i, j-1);
        count += islandPerimeterUtil(grid, i, j+1);
               
        return count;
    }
}