/**
Given two strings low and high that represent two integers low and high where low <= high, return the number of strobogrammatic numbers in the range [low, high].

A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

Input: low = "50", high = "100"
Output: 3

Input: low = "0", high = "0"
Output: 1
*/

// Solution 1:
class Solution {
    private static final char[][] PAIRS = {{'0', '0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};
    private int count = 0;
    private String low;
    private String high;
    
    public int strobogrammaticInRange(String low, String high) {
        this.low = low;
        this.high = high;
        
        for (int i = low.length(); i <= high.length(); i++) {
            dfs(0, i-1, new char[i]);
        }
        return count;
    }
    
    private void dfs(int left, int right, char[] c) {
        if (left > right) {
            String res = new String(c);
            if (!(res.length() == low.length() && res.compareTo(low) < 0) && 
                !(res.length() == high.length() && res.compareTo(high) > 0)) {
                count++;
            }
            return;
        }
        
        
        for (char[] p : PAIRS) { 
            c[left] = p[0];
            c[right] = p[1];
            
            if (c.length != 1 && c[0] == '0') continue;
            if (left == right && p[0] != p[1]) continue;
            
            dfs(left + 1, right - 1, c);
        }
    }     
}

/*
The key of all strobogrammatic number problem lies in the recursive function.

1. when to update result in Base Cases (left > right)
2. when to continue in Recursive Steps
2.1 number with leading char == 0 is not valid
2.2 when left == right, the char itself can only be {0, 1, 8}
This structure of code works very well in all similar problems:

1088. Confusing Number II
247. Strobogrammatic Number II
248. Strobogrammatic Number III
788. Rotated Digits
*/


// Solution 2: 
private static final char[][] pairs = {{'0', '0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};
    
    public int strobogrammaticInRange(String low, String high) {
        int[] count = {0};
        for (int len = low.length(); len <= high.length(); len++) {
            char[] c = new char[len];
            dfs(low, high, c, 0, len - 1, count);
        }
        return count[0];
    }
    
    public void dfs(String low, String high , char[] c, int left, int right, int[] count) {
        if (left > right) {
            String s = new String(c);
            if ((s.length() == low.length() && s.compareTo(low) < 0) || 
                (s.length() == high.length() && s.compareTo(high) > 0)) {
                return;
            }
            count[0]++;
            return;
        }
        for (char[] p : pairs) {
            c[left] = p[0];
            c[right] = p[1];
            if (c.length != 1 && c[0] == '0') continue;
            if (left == right && p[0] != p[1]) continue;
            dfs(low, high, c, left + 1, right - 1, count);
        }
    }

// Solution 3:
//Instead getting result from variable int[] count = {0}, let dfs() method return the count.
//Java naming convention. The static variable name should be upper case. pairs --> PAIRS. See detail below:
public class Solution {
    private static final char[][] PAIRS = new char[][] {
        {'0', '0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};
    public int strobogrammaticInRange(String low, String high) {
        if (low == null || high == null || low.length() > high.length()
            || (low.length() == high.length() && low.compareTo(high) > 0)) {
            return 0;
        }
        int count = 0;
        for (int len = low.length(); len <= high.length(); len++) {
            count += dfs(low, high, new char[len], 0, len - 1);
        }
        return count;
    }
    private int dfs(String low, String high, char[] ch, int left, int right) {
        if (left > right) {
            String s = new String(ch);
            if ((ch.length == low.length() && s.compareTo(low) < 0)
                || (ch.length == high.length() && s.compareTo(high) > 0)) {
                return 0;
            } else {
                return 1;
            }
        }
        int count = 0;
        for (char[] p : PAIRS) {
            ch[left] = p[0];
            ch[right] = p[1];
            if (ch.length != 1 && ch[0] == '0') {
                continue; //don't start with 0
            }
            if (left == right && (p[0] == '6' || p[0] == '9')) {
                continue; //don't put 6/9 at the middle of string.
            }
            count += dfs(low, high, ch, left + 1, right - 1);
        }
        return count;
    }
}

// Solution 4:
private static final char[][] pairs = {{'0', '0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};

public int strobogrammaticInRange(String low, String high) {
    int[] count = {0};
    for (int len = low.length(); len <= high.length(); len++) {
        char[] c = new char[len];
        dfs(low, high, c, 0, len - 1, count);
    }
    return count[0];
}

public void dfs(String low, String high , char[] c, int left, int right, int[] count) {
    if (left > right) {
        String s = new String(c);
        if ((s.length() == low.length() && s.compareTo(low) < 0) ||
            (s.length() == high.length() && s.compareTo(high) > 0)) {
            return;
        }
        count[0]++;
        return;
    }
    for (char[] p : pairs) {
        c[left] = p[0];
        c[right] = p[1];
        if (c.length != 1 && c[0] == '0') {
            continue;
        }
        if (left == right && p[0] != p[1]) {
            continue;
        }
        dfs(low, high, c, left + 1, right - 1, count);
    }
}