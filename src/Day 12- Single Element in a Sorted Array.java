class Solution {
    public int singleNonDuplicate(int[] nums) {
        // int res=0;
        if(nums.length==1) return nums[0];
        int l=0, h=nums.length-1;
        int m= l+(h-l)/2;
        
        while(l<h) {
            m= l+(h-l)/2;
            
            if((m%2==0 && nums[m]!=nums[m+1]) || (m%2==1 && nums[m-1]!=nums[m])) h=m;
            else l=m+1;
//             if(m%2==1) m--;            
//             if(nums[m]!=nums[m+1])  h=m;            
//             else l=m+2;
        }
        
        return nums[l];
        
        // for(int x: nums) {
        //     res^=x;
        // }
        
        // return res;
    }
}