class Solution {
    public int lengthOfLongestSubstring(String s) {
        
        /*
        Time complexity: O(n)
        Space complexity: O(m.size())
        
        */
        
        if(s == null)
            return 0;
        
        Set<Character> set= new HashSet<>();
        int res=0, left=0, right=0;
        
        while(right<s.length()) {
            
            if(!set.add(s.charAt(right))) {
                set.remove(s.charAt(left));
                left++;
            } else {
                res = Math.max(res, set.size());
                right++;
            }
        }
        
        return res;
    }
}