class Solution {
    public int missingNumber(int[] nums) {
		
		/*
			Time complexity: O(n)
			Space complexity: O(1)
		*/ 
		
        int len = nums.length;
        int totalSum = (len*(len+1))/2, sum=0;
        
        for(int num: nums) {
            sum += num;
        }
        
        return (totalSum-sum);        // 6-4 = 2
        
    }
}