// Approach 1: Brute Force
class Solution {
// T(O): O(N^2)
// S(O): O(1)
    public int maxProduct(int[] nums) {
        if (nums.length == 0) return 0;

        int result = nums[0];

        for (int i = 0; i < nums.length; i++) {
            int accu = 1;
            for (int j = i; j < nums.length; j++) {
                accu *= nums[j];
                result = Math.max(result, accu);
            }
        }

        return result;
    }
}


// Approach 2: Dynamic Programming
class Solution {
// T(O): O(N)
// S(O): O(1)
    public int maxProduct(int[] nums) {
        if (nums.length == 0) return 0;

        int max_so_far = nums[0];
        int min_so_far = nums[0];
        int result = max_so_far;

        for (int i = 1; i < nums.length; i++) {
            int curr = nums[i];
            int temp_max = Math.max(curr, Math.max(max_so_far * curr, min_so_far * curr));
            min_so_far = Math.min(curr, Math.min(max_so_far * curr, min_so_far * curr));

            max_so_far = temp_max;

            result = Math.max(max_so_far, result);
        }

        return result;
    }
}

// My Solution
class Solution {
    public int maxProduct(int[] nums) {
        if(nums.length == 1) return nums[0];
        
        int max_product = nums[0], min_product = nums[0], ans= nums[0];
        
        for(int i=1; i<nums.length; i++) {
            
            int temp= max_product;
            
            max_product= Math.max(Math.max(max_product*nums[i], min_product*nums[i]), nums[i]);
            min_product= Math.min(Math.min(temp*nums[i], min_product*nums[i]), nums[i]);
            
            if(max_product > ans) ans = max_product;
        }
        return ans;
    }
}