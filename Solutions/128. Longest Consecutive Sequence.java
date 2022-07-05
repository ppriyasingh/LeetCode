class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int longestStreak = 0;
        
        for(int n: nums) {
            set.add(n);
        }
        
        for(int n: nums) {
            if(!set.contains(n-1)) {
                int currNum = n;
                int tempLongestStreak = 1;
                
                while(set.contains(currNum+1)) {
                    tempLongestStreak++;
                    currNum++;
                }
                
                longestStreak = longestStreak < tempLongestStreak? tempLongestStreak : longestStreak;
            }
        }
        
        return longestStreak;
    }
}
