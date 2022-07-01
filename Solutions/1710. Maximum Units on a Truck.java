class Solution {
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        int res=0;
        
        Arrays.sort(boxTypes, (a,b)->b[1]-a[1]);
        
        for(int i=0; i<boxTypes.length; i++) {
            
            if(truckSize >= boxTypes[i][0]) {
                res += boxTypes[i][0] * boxTypes[i][1];
                truckSize -= boxTypes[i][0];
            } else {
                res += truckSize * boxTypes[i][1];
                break;
            }
        }
        
        return res;
    }
}
