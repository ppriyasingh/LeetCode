/**
Given an integer n, return all the strobogrammatic numbers that are of length n. You may return the answer in any order.
A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

Input: n = 2
Output: ["11","69","88","96"]

Input: n = 1
Output: ["0","1","8"]
*/

// Solution 1:

class Solution {
    private static final char[][] PAIR = {{'0','0'}, {'1','1'}, {'6','9'}, {'8','8'}, {'9','6'}};
    
    private List<String> res = new ArrayList<>();
    public List<String> findStrobogrammatic(int n) {
        helper(0, n-1, new char[n]);
        return res;  
    }
    
    public void helper(int left, int right, char[] curr) {
        if (left > right) {
            res.add(new String(curr));
            return;
        } 
        
        for (char[] p : PAIR) {
            curr[left] = p[0];
            curr[right] = p[1];
            
            if (curr.length != 1 && curr[0] == '0') continue;
            if (left == right && p[0] != p[1]) continue;
            
            helper(left + 1, right - 1, curr);
        }
    }
}

/*
There might be various backtracking approaches. This solution stands out not only because its syntax is really clean. Also, it works particularly well in other more complex & tricky problems.

The key of all strobogrammatic number problem lies in the recursive function.

1. when to update result in Base Cases (left > right)
2. when to continue in Recursive Steps
2.1 number with leading char == 0 is not valid
2.2 when left == right, the char itself can only be {0, 1, 8}
It will be helpful to check out similar question to get a well-rounded understanding of this type of problem.

1088. Confusing Number II
247. Strobogrammatic Number II
248. Strobogrammatic Number III
788. Rotated Digits
*/


// Solution 2: 
//basic DFS will get TLE, to improve it:
//only iterate half of the string, because the other half is the "image" of the first half.

class Solution {
    
    public List<String> findStrobogrammatic(int n) {
        List<String> ls = new LinkedList<>();
        dfs(new char[n], ls, 0, n);
        return ls;
    }
    
    private char[] candidates = new char[]{'0', '1', '6', '8', '9'};
    
    private void dfs(char[] result, List<String> ls, int start, int n) {
        if (start == n/2) {
            if (n%2 == 0) {
                ls.add(new String(result));
            } else {
                for (char c : candidates) {
                    if (c == '6' || c == '9') continue;
                    result[start] = c;
                    ls.add(new String(result));
                }
            }
            return;
        }
        for (char c : candidates) {
            if (start == 0 && c == '0') { continue; }
            result[start] = c;
            result[n - 1 - start] = transform(c);
            dfs(result, ls, start + 1, n);
        }
    }
    
    private char transform(char c) {
        return c == '6' ? '9' : c == '9' ? '6' : c;
    }    
}