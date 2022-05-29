class Solution {
    public boolean isPossible(int[] nums, int mid, int m) {
        int subArray=1, sum=0;
        
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
            
            if(sum > mid) {
                sum=nums[i];
                subArray++;
            }
        }
        return subArray<=m;
    }
    public int splitArray(int[] nums, int m) {
        if(nums.length < m) return -1;
        int max = 0, sum = 0;
        for(int n: nums) {
            max = Math.max(max, n);
            sum += n;
        }
        if(nums.length == m) return max;
        
        int l = max, h = sum, ans = 0;
        while(l <= h) {
            int mid= l + (h - l) / 2;
            
            if(isPossible(nums, mid, m)) {
                ans = mid;
                h = mid - 1;
            } else l = mid + 1;
        }
        return ans;
    }
}
