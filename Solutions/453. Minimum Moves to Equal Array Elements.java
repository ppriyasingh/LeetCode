// Sol 1:
class Solution {

    //equal means, no difference between each element, it can do by increase or decrease numbers
    //increment n -1 elements, similar to reduce 1 element
    //thus minimual operation is to reduce each element to the smallest one in the array
    
    public int minMoves(int[] nums) {
        //edge case
        if (nums == null || nums.length <= 1) return 0;
        int min = Integer.MAX_VALUE;
      
        //find minimal element
        for(int num : nums){
            min = Math.min(num,min);
        }
        int ans = 0;
      
        //difference between number and minimal is the least operation for each num
        for(int num : nums){
            ans += num - min;
        }
        return ans;
    }
}

/*
In the give problem statement we are allowed to increment n-1 elements by 1 so that all the elements in the array become equal.
One approach to this problem is increment all the elements except the maximum element .However ,this takes O(n^2) time complexity.
Hence , the O(n) approach is decrement the maximum value till it becomes equal to the minimum value in the element.
Hence, instead of incrementing n-1 elements, we are decrementing 1 element.
Therefore, the optimal solution is :
Total iterations = (nums[0] - min) + (nums[1] - min)+ ... + (nums[n] - min)
*/
