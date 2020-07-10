class Solution {
    public int maxSubarraySumCircular(int[] A) {
        int minSum=Integer.MAX_VALUE, maxSum= Integer.MIN_VALUE, total=0, csmax=0, csmin=0; 
        
        for(int x: A) {
            csmax= Math.max(csmax+x, x);
            maxSum= Math.max(maxSum , csmax);
            
            csmin= Math.min(csmin+x, x);
            minSum= Math.min(minSum, csmin);
             
            total+=x;
        }
        
        return (maxSum>0)? Math.max(maxSum, total-minSum): maxSum;
    }
}