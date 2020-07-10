class Solution {
    public boolean checkInclusion(String p, String s) {
        
        int[] chars = new int[26];

    if (s == null || p == null || p.length() > s.length())
        return false;
        
    for (char c : p.toCharArray())
        chars[c-'a']++;

    int start = 0, end = 0, count = p.length();
        
    while (end < s.length()) {
        // If the char at start appeared in p, we increase count
        if (end - start == p.length() && chars[s.charAt(start++)-'a']++ >= 0)
            count++;
        // If the char at end appeared in p (since it's not -1 after decreasing), we decrease count
        if (--chars[s.charAt(end++)-'a'] >= 0)
            count--;
        if (count == 0)
            return true;
    }
    
    return false;
    }
}