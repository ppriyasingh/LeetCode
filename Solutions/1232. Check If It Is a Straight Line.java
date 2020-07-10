class Solution {
    public boolean checkStraightLine(int[][] coordinates) {
        
        if(coordinates.length<=2) return true;
        
        
        for(int i=2; i<coordinates.length; i++) {
            
            if(((coordinates[i][1]- coordinates[1][1])*(coordinates[1][0]- coordinates[0][0]))!=((coordinates[i][0]- coordinates[1][0])*(coordinates[1][1]- coordinates[0][1])))
                return false;
        }
        
        return true;
    }
}