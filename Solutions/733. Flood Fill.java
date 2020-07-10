class Solution {
    public void floodFillUtil(int[][] image, int x, int y, int oldColor, int newColor) {
        
        if(x<0|| y<0 || x>=image.length || y>=image[0].length || image[x][y]!=oldColor) return;
        
        image[x][y]=newColor;
        floodFillUtil(image, x-1, y, oldColor, newColor);        
        floodFillUtil(image, x+1, y, oldColor, newColor);
        floodFillUtil(image, x, y-1, oldColor, newColor);
        floodFillUtil(image, x, y+1, oldColor, newColor);
        
    }
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        
        if(image[sr][sc]!= newColor) {
            floodFillUtil( image, sr, sc, image[sr][sc], newColor);
        }
        
        return image;
    }
}