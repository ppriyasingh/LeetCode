// Solution 1:
class Solution {
    public int wiggleMaxLength(int[] nums) {
        if(nums.length <= 1) return nums.length;
        int count = 1;
        int prev = 0;
        
        for(int i=1; i<nums.length; i++) {
            
            int diff = nums[i] - nums[i-1];
            if(prev == 0 && diff < 0) {
                prev = -1;
                count++;
            } else if(prev == 0 && diff > 0) {
                prev = +1;
                count++;
            } else if(prev == -1 && diff > 0) {
                prev = +1;
                count++;
            } else if(prev == 1 && diff < 0) {
                prev = -1;
                count++;
            }
        }
        return count;
    }
}

// Solution 2:
class Solution {
    public int wiggleMaxLength(int[] nums) {
        if(nums.length < 2) return nums.length;
        int count = 1;
        int sign = 0;
        
        for(int i=1; i<nums.length; i++) {
            if(sign != -1 && nums[i] < nums[i-1]) {
                sign = -1;
                count++;
            } else if(sign != 1 && nums[i] > nums[i-1]) {
                sign = +1;
                count++;
            }
        }
        return count;
    }
}

// Solution 3: (Best one)
class Solution {
    public int wiggleMaxLength(int[] nums) {
        if(nums.length < 2) return nums.length;
        int up = 1, down = 1;
        
        for(int i=1; i<nums.length; i++) {
            if(nums[i-1] < nums[i]) {
                up = down + 1;
            } else if(nums[i-1] > nums[i]) {
                down = up + 1;
            }
        }
        return up > down ? up : down;
    }
}

// Solution 4: ( DP ) 
public class Solution {
    public int wiggleMaxLength(int[] nums) {
        if (nums.length < 2)
            return nums.length;
        int[] up = new int[nums.length];
        int[] down = new int[nums.length];
        for (int i = 1; i < nums.length; i++) {
            for(int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    up[i] = Math.max(up[i],down[j] + 1);
                } else if (nums[i] < nums[j]) {
                    down[i] = Math.max(down[i],up[j] + 1);
                }
            }
        }
        return 1 + Math.max(down[nums.length - 1], up[nums.length - 1]);
    }
}
